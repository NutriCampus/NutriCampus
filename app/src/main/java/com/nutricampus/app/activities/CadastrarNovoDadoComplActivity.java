package com.nutricampus.app.activities;

import android.app.DatePickerDialog;
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
import com.nutricampus.app.entities.Animal;
import com.nutricampus.app.entities.DadosComplAnimal;
import com.nutricampus.app.entities.Propriedade;
import com.nutricampus.app.fragments.DadosAnimalFragment;
import com.nutricampus.app.utils.ValidaFormulario;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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
        implements DatePickerDialog.OnDateSetListener, AdapterView.OnItemSelectedListener {

    @BindView(R.id.input_identificador)
    EditText inputIdentificador;
    @BindView(R.id.input_peso_vivo)
    EditText inputPesoVivo;
    @BindView(R.id.input_caminhada_vertical)
    EditText inputCaminhadaVertical;
    @BindView(R.id.input_caminhada_horizontal)
    EditText inputCaminhadaHorizontal;
    @BindView(R.id.input_semana_lactacao)
    EditText inputSemanaLactacao;
    @BindView(R.id.ckb_pastando)
    CheckBox ckbPastando;
    @BindView(R.id.ckb_lactacao)
    CheckBox ckbLactacao;
    @BindView(R.id.ckb_cio)
    CheckBox ckbCio;
    @BindView(R.id.ckb_gestante)
    CheckBox ckbGestante;
    @BindView(R.id.rgEec)
    RadioGroup radioGroup;
    @BindView(R.id.btn_salvar)
    Button btnSalvar;
    @BindView(R.id.input_id_propriedade)
    EditText inputIdPropriedade;

    protected Animal animal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_novo_dado_compl);

        inputData = (EditText) findViewById(R.id.input_data_complementar);

        ButterKnife.bind(CadastrarNovoDadoComplActivity.this);

        Intent intent = getIntent();
        animal = (Animal) intent.getSerializableExtra("animal");

        inicializaCampoData(inputData);

        inputIdentificador.setText(animal.getIndentificador());
        inputIdentificador.setFocusable(false);

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


    public void salvarHistoricoAnimal(View v) {
        if (!validaDados()) {
            Toast.makeText(CadastrarNovoDadoComplActivity.this, R.string.msg_erro_cadastro_geral, Toast.LENGTH_LONG).show();
            return;
        }

        float caminhadaHorizontal = inputCaminhadaHorizontal.getText().toString().equals("") ? 0.0f :
                Float.parseFloat(inputCaminhadaHorizontal.getText().toString());
        float caminhadaVertical = inputCaminhadaVertical.getText().toString().equals("") ? 0.0f :
                Float.parseFloat(inputCaminhadaVertical.getText().toString());

        //Atribuir o valor de EEC
        int idRadioButton = radioGroup.getCheckedRadioButtonId();
        RadioButton rb = (RadioButton) radioGroup.findViewById(idRadioButton);

        int eec;

        if (rb == null)
            eec = 0;
        else
            eec = Integer.parseInt(String.valueOf(rb.getText()));

        DadosComplAnimal dadosComplAnimal = new DadosComplAnimal(
                data,
                Float.parseFloat(inputPesoVivo.getText().toString()),
                eec,
                caminhadaHorizontal,
                caminhadaVertical,
                Integer.parseInt(inputSemanaLactacao.getText().toString()),
                ckbPastando.isChecked(),
                ckbLactacao.isChecked(),
                ckbGestante.isChecked(),
                ckbCio.isChecked()
        );

        dadosComplAnimal.setAnimal(animal.getId());

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


}
