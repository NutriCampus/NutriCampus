package com.nutricampus.app.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.nutricampus.app.R;
import com.nutricampus.app.database.RepositorioCidade;
import com.nutricampus.app.database.RepositorioEstado;
import com.nutricampus.app.database.RepositorioPropriedade;
import com.nutricampus.app.database.RepositorioProprietario;
import com.nutricampus.app.database.SharedPreferencesManager;
import com.nutricampus.app.entities.Propriedade;
import com.nutricampus.app.entities.Proprietario;
import com.nutricampus.app.utils.Mascara;
import com.nutricampus.app.utils.ValidaFormulario;

import java.util.ArrayList;
import java.util.List;

import static com.nutricampus.app.fragments.DadosAnimalFragment.EXTRA_CAD_ANIMAL;

/*
Explicação para a supressão de warnings:
 - "squid:MaximumInheritanceDepth" = herança extendida em muitos niveis (mais que 5), permitido aqui já
 que refere-se a herança das classes das activities Android
 - "squid:S1172" = erro do sonarqube para os parametros "view" não utilizados
*/
@java.lang.SuppressWarnings({"squid:S1172", "squid:MaximumInheritanceDepth"})
public class CadastrarPropriedadeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static final String EXTRA_PROPRIEDADE = "propriedade";

    protected EditText inputId;
    protected EditText inputIdProprietario;
    protected EditText inputNome;
    protected EditText inputCep;
    protected EditText inputRua;
    protected EditText inputBairro;
    protected EditText inputNumero;
    protected EditText inputTelefone;
    protected AutoCompleteTextView inputCidade;
    protected AutoCompleteTextView inputEstado;
    protected Button buttonSalvar;
    protected Spinner spinnerProprietario;
    protected Button buttonAddProprietario;


    private int voltarCadAnimal;
    private int voltarProprietarios;
    protected SharedPreferencesManager session;

    protected void init() {
        inputId = (EditText) findViewById(R.id.input_id_propriedade);
        inputIdProprietario = (EditText) findViewById(R.id.input_id_proprietario);
        inputNome = (EditText) findViewById(R.id.input_nome_propriedade);
        inputCep = (EditText) findViewById(R.id.input_cep);
        inputRua = (EditText) findViewById(R.id.input_rua);
        inputBairro = (EditText) findViewById(R.id.input_bairro);
        inputNumero = (EditText) findViewById(R.id.input_numero);
        inputTelefone = (EditText) findViewById(R.id.input_telefone_propriedade);
        inputEstado = (AutoCompleteTextView) findViewById(R.id.input_estado);
        inputCidade = (AutoCompleteTextView) findViewById(R.id.input_cidade);
        spinnerProprietario = (Spinner) findViewById(R.id.spinner_proprietario);
        buttonSalvar = (Button) findViewById(R.id.btn_salvar_propriedade);
        buttonAddProprietario = (Button) findViewById(R.id.btn_add_proprietario);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        session = new SharedPreferencesManager(getApplicationContext());
        session.checkLogin();

        setContentView(R.layout.activity_cadastrar_propriedade);

        init();

        addAutoCompletes();

        inputTelefone.addTextChangedListener(Mascara.insert(Mascara.CELULAR_MASK, inputTelefone));
        inputCep.addTextChangedListener(Mascara.insert(Mascara.CEP_MASK, inputCep));

        spinnerProprietario.setOnItemSelectedListener(this);

        Intent it = getIntent();
        voltarCadAnimal = it.getIntExtra(EXTRA_CAD_ANIMAL, -1);
        voltarProprietarios = it.getIntExtra(ListaProprietariosActivity.EXTRA_VOLTAR_PROPRIETARIOS, -1);
        Proprietario proprietario = (Proprietario)
                it.getSerializableExtra(CadastrarProprietarioActivity.EXTRA_PROPRIETARIO);

        if (proprietario != null)
            inputIdProprietario.setText(String.valueOf(proprietario.getId()));

        // Loading spinner data from database
        preencherSpinnerListaProprietario();
        buttonAddProprietario.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                criarProprietario(view);
            }
        });

        buttonSalvar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                salvar(view);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        preencherSpinnerListaProprietario();
    }

    public void preencherSpinnerListaProprietario() {

        RepositorioProprietario repositorioProprietario = new RepositorioProprietario(getBaseContext());
        List<Proprietario> todosProprietarios = repositorioProprietario.buscarTodosProprietarios();

        if (!(todosProprietarios.isEmpty())) {

            // Adiciona a msg de "Selecione..." no spinner do proprietario
            Proprietario posZero = new Proprietario(0, "", getString(R.string.msg_spinner_proprietario), "", "");
            todosProprietarios.add(0, posZero);

            ArrayAdapter<Proprietario> spinnerProprietarioAdapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_item, todosProprietarios);

            spinnerProprietarioAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spinnerProprietario.setAdapter(spinnerProprietarioAdapter);

            int posicao;
            String idProprietario = inputIdProprietario.getText().toString();

            if (idProprietario.isEmpty())
                posicao = 0;
            else
                posicao = spinnerProprietarioAdapter.getPosition(repositorioProprietario.buscarProprietario(Integer.parseInt(idProprietario)));


            spinnerProprietario.setSelection(posicao);
        } else {
            Proprietario prop = new Proprietario(0, "", "<< " + getString(R.string.msg_cadastre_proprietario) + " >>", "", "");
            todosProprietarios.add(prop);
            ArrayAdapter<Proprietario> spinnerProprietarioAdapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_item, todosProprietarios);

            spinnerProprietario.setAdapter(spinnerProprietarioAdapter);
        }
    }

    private void addAutoCompletes() {

        RepositorioEstado repoEstado = new RepositorioEstado(CadastrarPropriedadeActivity.this);
        RepositorioCidade repoCidade = new RepositorioCidade(CadastrarPropriedadeActivity.this);

        // Autocomplete para o campo estado
        ArrayAdapter<String> adapterEstados = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, repoEstado.getListaDeNomes());
        inputEstado.setAdapter(adapterEstados);

        // Autocomplete para o campo cidade
        ArrayAdapter<String> adapterCidades = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, repoCidade.getListaDeNomes());
        inputCidade.setAdapter(adapterCidades);

    }

    protected void salvar(View view) {
        if (!validaDados()) {
            Toast.makeText(CadastrarPropriedadeActivity.this, R.string.msg_erro_cadastro_geral, Toast.LENGTH_LONG).show();
            return;
        }

        Propriedade propriedade = new Propriedade(
                inputNome.getText().toString(),
                inputTelefone.getText().toString(),
                inputRua.getText().toString(),
                inputBairro.getText().toString(),
                inputCep.getText().toString(),
                inputCidade.getText().toString(),
                inputEstado.getText().toString(),
                inputNumero.getText().toString(),
                Integer.parseInt(inputIdProprietario.getText().toString()),
                Integer.parseInt(session.getIdUsuario()));

        RepositorioPropriedade repositorioPropriedade = new RepositorioPropriedade(getBaseContext());

        int idPropriedade = repositorioPropriedade.inserirPropriedade(propriedade);

        if (idPropriedade > 0) {
            Toast.makeText(CadastrarPropriedadeActivity.this, R.string.msg_cadastro_salvo, Toast.LENGTH_LONG).show();
            propriedade.setId(idPropriedade);

            if (voltarCadAnimal == 1) {
                Intent it = new Intent(CadastrarPropriedadeActivity.this, CadastrarAnimalActivity.class);
                it.putExtra(EXTRA_PROPRIEDADE, propriedade);
                startActivity(it);
                this.finish();
            } else {
                Intent it = new Intent(CadastrarPropriedadeActivity.this, ListaPropriedadesActivity.class);
                startActivity(it);
                this.finish();
            }

        } else {
            Toast.makeText(CadastrarPropriedadeActivity.this, R.string.msg_erro_cadastro, Toast.LENGTH_LONG).show();
        }
    }

    public void criarProprietario(View view) {
        Intent it = new Intent(this, CadastrarProprietarioActivity.class);
        //Enviar voltarCadAnimal para o cadastro de proprietario para não se perder ao retornar para cadPropriedade
        it.putExtra(EXTRA_CAD_ANIMAL, voltarCadAnimal);
        startActivity(it);
    }

    protected boolean validaDados() {
        boolean valido = true;

        List<TextView> camposTexto = new ArrayList<>();
        camposTexto.add(inputNome);
        camposTexto.add(inputTelefone);
        camposTexto.add(inputRua);
        camposTexto.add(inputBairro);
        camposTexto.add(inputNumero);
        camposTexto.add(inputCep);
        camposTexto.add(inputCidade);
        camposTexto.add(inputEstado);

        for (TextView view : camposTexto)
            view.setError(null);

        List<TextView> camposVazios = ValidaFormulario.camposTextosVazios(camposTexto);

        if (!camposVazios.isEmpty()) {
            for (TextView view : camposVazios)
                view.setError(getString(R.string.msg_erro_campo));

            valido = false;
        }


        if (!ValidaFormulario.isSelecaoValida(spinnerProprietario.getSelectedItemPosition(), 0)) {
            TextView text = (TextView) spinnerProprietario.getSelectedView();
            text.setTextColor(Color.RED);
            text.setError("");
            valido = false;
        } else {
            TextView text = (TextView) spinnerProprietario.getSelectedView();
            text.setError(null);
            text.setTextColor(Color.BLACK);
        }

        if (!ValidaFormulario.isTelefoneValido(inputTelefone.getText().toString())) {
            inputTelefone.setError(getString(R.string.msg_erro_telefone_incompleto));
            valido = false;
        } else
            inputTelefone.setError(null);


        if (!ValidaFormulario.isCEPValido(inputCep.getText().toString())) {
            inputCep.setError(getString(R.string.msg_erro_cep_incompleto));
            valido = false;
        } else
            inputCep.setError(null);


        return valido;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {

        if ((parent != null) && (parent.getItemAtPosition(position) instanceof Proprietario)) {
            Proprietario proprietario = (Proprietario) parent.getItemAtPosition(position);
            inputIdProprietario.setText(String.valueOf(proprietario.getId()));
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        // Implementação necessário por causa da Interface usada nesta classe
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            voltarActivity();
        return true;
    }

    @Override
    public void onBackPressed() {
        voltarActivity();
    }

    private void voltarActivity() {
        Intent it;

        if (voltarCadAnimal == 1)
            it = new Intent(CadastrarPropriedadeActivity.this, CadastrarAnimalActivity.class);
        else if (voltarProprietarios == 1)
            it = new Intent(CadastrarPropriedadeActivity.this, ListaProprietariosActivity.class);
        else
            it = new Intent(CadastrarPropriedadeActivity.this, ListaPropriedadesActivity.class);

        startActivity(it);
        finish();
    }
}