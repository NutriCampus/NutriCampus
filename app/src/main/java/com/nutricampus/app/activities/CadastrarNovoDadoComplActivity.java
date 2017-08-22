package com.nutricampus.app.activities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.nutricampus.app.R;
import com.nutricampus.app.database.RepositorioDadosComplAnimal;
import com.nutricampus.app.database.RepositorioGrupo;
import com.nutricampus.app.database.SharedPreferencesManager;
import com.nutricampus.app.entities.Animal;
import com.nutricampus.app.entities.DadosComplAnimal;
import com.nutricampus.app.entities.Grupo;
import com.nutricampus.app.entities.Propriedade;
import com.nutricampus.app.fragments.DadosAnimalFragment;
import com.nutricampus.app.utils.ValidaFormulario;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Felipe on 19/07/2017.
 * For project NutriCampus.
 * Contact: <felipeguimaraes540@gmail.com>
 */

/*
Explicação para a supressão de warnings:
 - "squid:MaximumInheritanceDepth" = herança extendida em muitos niveis (mais que 5), permitido aqui já
 que refere-se a herança das classes das activities Android
 - "squid:S1172" = erro do sonarqube para os parametros "view" não utilizados
*/
@java.lang.SuppressWarnings({"squid:S1172", "squid:MaximumInheritanceDepth"})
public class CadastrarNovoDadoComplActivity extends AbstractDataPickerActivity
        implements DatePickerDialog.OnDateSetListener, AdapterView.OnItemSelectedListener, View.OnClickListener {

    protected EditText inputIdentificador;
    protected EditText inputPesoVivo;
    protected EditText inputCaminhadaVertical;
    protected EditText inputCaminhadaHorizontal;
    protected EditText inputSemanaLactacao;
    protected CheckBox ckbPastando;
    protected CheckBox ckbLactacao;
    protected CheckBox ckbCio;
    protected CheckBox ckbGestante;
    protected RadioGroup radioGroup;
    protected Button btnSalvar;
    protected EditText inputIdPropriedade;
    protected TextView txtGrupo;

    protected Animal animal;
    protected String grupoSelecionado = "";

    protected void init() {
        inputIdentificador = (EditText) findViewById(R.id.input_identificador);
        inputPesoVivo = (EditText) findViewById(R.id.input_peso_vivo);
        inputCaminhadaVertical = (EditText) findViewById(R.id.input_caminhada_vertical);
        inputCaminhadaHorizontal = (EditText) findViewById(R.id.input_caminhada_horizontal);
        inputSemanaLactacao = (EditText) findViewById(R.id.input_semana_lactacao);
        inputIdPropriedade = (EditText) findViewById(R.id.input_id_propriedade);
        radioGroup = (RadioGroup) findViewById(R.id.rgEec);
        btnSalvar = (Button) findViewById(R.id.btn_salvar);
        txtGrupo = (TextView) findViewById(R.id.txtGrupos);

        inputData = (EditText) findViewById(R.id.input_data_complementar);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_novo_dado_compl);

        this.init();

        Intent intent = getIntent();
        animal = (Animal) intent.getSerializableExtra("animal");

        txtGrupo.setOnClickListener(CadastrarNovoDadoComplActivity.this);

        inicializaCampoData(inputData);

        inputIdentificador.setText(animal.getIndentificador());
        inputIdentificador.setFocusable(false);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.txtGrupos) {
            escolherGrupo();
            return;
        }

    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {

        if ((parent != null) && (parent.getItemAtPosition(position) instanceof Propriedade)) {
            Propriedade propriedade = (Propriedade) parent.getItemAtPosition(position);
            inputIdPropriedade.setText(String.valueOf(propriedade.getId()));
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        // Implementação não necessária para a classe, override necessário deido a interface extendida
    }

    public void inserirData(View v) {
        showDatePickerDialogOnClick(v);
    }

    protected DadosComplAnimal getDadosComplAnimal() {
        float caminhadaHorizontal = "".equals(inputCaminhadaHorizontal.getText().toString()) ? 0.0f :
                Float.parseFloat(inputCaminhadaHorizontal.getText().toString());
        float caminhadaVertical = "".equals(inputCaminhadaVertical.getText().toString()) ? 0.0f :
                Float.parseFloat(inputCaminhadaVertical.getText().toString());

        //Atribuir o valor de EEC
        int idRadioButton = radioGroup.getCheckedRadioButtonId();
        RadioButton rb = (RadioButton) radioGroup.findViewById(idRadioButton);

        int eec;

        if (rb == null)
            eec = 0;
        else
            eec = Integer.parseInt(String.valueOf(rb.getText()));

        return new DadosComplAnimal(
                data,
                Float.parseFloat(inputPesoVivo.getText().toString()),
                eec,
                caminhadaHorizontal,
                caminhadaVertical,
                Integer.parseInt(inputSemanaLactacao.getText().toString()));
    }

    public void salvarHistoricoAnimal(View v) {
        if (!validaDados()) {
            Toast.makeText(CadastrarNovoDadoComplActivity.this, R.string.msg_erro_cadastro_geral, Toast.LENGTH_LONG).show();
            return;
        }

        DadosComplAnimal dadosComplAnimal = getDadosComplAnimal();

        int idGrupo = 1;
        int idUsuario = Integer.parseInt(new SharedPreferencesManager(CadastrarNovoDadoComplActivity.this).getIdUsuario());

        //Setar grupo em dadosCompl
        RepositorioGrupo repositorioGrupo = new RepositorioGrupo(CadastrarNovoDadoComplActivity.this);
        Grupo grupo = repositorioGrupo.buscarGrupo(grupoSelecionado);

        if (grupo == null && (repositorioGrupo.buscarPorUsuario(idUsuario).isEmpty())) {
            Toast.makeText(CadastrarNovoDadoComplActivity.this, getString(R.string.msg_erro_grupo), Toast.LENGTH_LONG).show();
            return;
        } else {
            if (grupo != null)
                idGrupo = grupo.getId();
        }

        dadosComplAnimal.setAnimal(animal.getId());
        dadosComplAnimal.setIdGrupo(
                ("".equals(grupoSelecionado) ? 1 : idGrupo)
        );

        RepositorioDadosComplAnimal repositorioDadosComplAnimal = new RepositorioDadosComplAnimal(CadastrarNovoDadoComplActivity.this);
        int idDadosCompl = repositorioDadosComplAnimal.inserirDadosComplAnimal(dadosComplAnimal);

        if (idDadosCompl > 0) {
            Toast.makeText(CadastrarNovoDadoComplActivity.this, R.string.msg_cadastro_salvo, Toast.LENGTH_LONG).show();
            dadosComplAnimal.setId(idDadosCompl);
            Intent it = new Intent(CadastrarNovoDadoComplActivity.this, ListaDadosComplActivity.class);
            it.putExtra(ListaDadosComplActivity.EXTRA_ANIMAL, animal);
            startActivity(it);
        } else {
            Toast.makeText(CadastrarNovoDadoComplActivity.this, R.string.msg_erro_cadastro, Toast.LENGTH_LONG).show();
        }

    }

    protected boolean validaDados() {
        boolean valido = true;

        List<TextView> camposTexto = new ArrayList<>();
        camposTexto.add(inputIdentificador);
        camposTexto.add(inputData);
        camposTexto.add(inputPesoVivo);
        camposTexto.add(inputCaminhadaHorizontal);
        camposTexto.add(inputCaminhadaVertical);
        camposTexto.add(inputSemanaLactacao);

        for (TextView view : camposTexto)
            view.setError(null);

        List<TextView> camposVazios = ValidaFormulario.camposTextosVazios(camposTexto);

        if (!camposVazios.isEmpty()) {
            for (TextView view : camposVazios)
                view.setError(getString(R.string.msg_erro_campo));

            valido = false;
        }

        return valido;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            Intent it = new Intent(CadastrarNovoDadoComplActivity.this, ListaDadosComplActivity.class);
            it.putExtra(DadosAnimalFragment.EXTRA_ANIMAL, animal);
            startActivity(it);
            finish();
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        Intent it = new Intent(CadastrarNovoDadoComplActivity.this, ListaDadosComplActivity.class);
        it.putExtra(DadosAnimalFragment.EXTRA_ANIMAL, animal);
        startActivity(it);
        finish();
    }

    public void escolherGrupo() {

        ArrayList<Grupo> listGrupos= new ArrayList<>();
        listGrupos.clear();

        //Adicionar grupos vindos do repositorio
        RepositorioGrupo repositorioGrupo = new RepositorioGrupo(CadastrarNovoDadoComplActivity.this);
        int idUsuario = Integer.parseInt(
                new SharedPreferencesManager(CadastrarNovoDadoComplActivity.this).getIdUsuario());

        if (repositorioGrupo.buscarPorUsuario(idUsuario).isEmpty()) {
            repositorioGrupo.inserirGrupo(new Grupo("Pastando", "", idUsuario));
            repositorioGrupo.inserirGrupo(new Grupo("Lactação", "", idUsuario));
            repositorioGrupo.inserirGrupo(new Grupo("Cio", "", idUsuario));
            repositorioGrupo.inserirGrupo(new Grupo("Gestante", "", idUsuario));
        }
        listGrupos.addAll((ArrayList)repositorioGrupo.buscarPorUsuario(idUsuario));

        final String[] grupos = new String[listGrupos.size()];

        for(int i = 0; i < grupos.length; i++) {
            grupos[i] = listGrupos.get(i).getIdentificador();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(CadastrarNovoDadoComplActivity.this);
        builder.setTitle("Selecione um grupo");
        int grupoChecked = -1;
        builder.setSingleChoiceItems(grupos, grupoChecked, new DialogInterface
                .OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                Toast.makeText(CadastrarNovoDadoComplActivity.this,
                        grupos[item], Toast.LENGTH_SHORT).show();

                grupoSelecionado = grupos[item];
            }
        });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                txtGrupo.setText("Grupo selecionado: " + grupoSelecionado);
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

}
