package com.nutricampus.app.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.nutricampus.app.R;
import com.nutricampus.app.database.RepositorioProle;
import com.nutricampus.app.entities.Prole;
import com.nutricampus.app.utils.ValidaFormulario;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/*
Explicação para a supressão de warnings:
 - "squid:MaximumInheritanceDepth" = herança extendida em muitos niveis (mais que 5), permitido aqui já
 que refere-se a herança das classes das activities Android
 - "squid:S1172" = erro do sonarqube para os parametros "view" não utilizados
*/
@java.lang.SuppressWarnings({"squid:S1172", "squid:MaximumInheritanceDepth"})
public class CadastroProleActivity extends AbstractDataPickerActivity {

    @BindView(R.id.input_id_prole)
    EditText inputId;

    @BindView(R.id.check_natimorto)
    CheckBox checkNatimorto;
    @BindView(R.id.input_peso_prole)
    EditText inputPeso;

    @BindView(R.id.btn_salvar_prole)
    Button buttonSalvar;

    protected int idAnimalMatriz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_prole);

        inputData = (EditText) findViewById(R.id.input_data_nascimento);

        ButterKnife.bind(this);

        inicializaCampoData(inputData);

        if (this.getIntent() != null)
            this.idAnimalMatriz = getIntent().getIntExtra("idAnimal", 0);
    }

    public void salvar(View view) {
        if (!validarDados()) {
            Toast.makeText(CadastroProleActivity.this, R.string.msg_erro_cadastro_geral, Toast.LENGTH_LONG).show();
            return;
        }

        Prole prole = getObjetoProducao();

        RepositorioProle repositorio = new RepositorioProle(this);
        int result = repositorio.inserirProle(prole);

        if (result > 0) {
            Toast.makeText(CadastroProleActivity.this, R.string.msg_cadastro_salvo, Toast.LENGTH_LONG).show();
            this.onBackPressed();
        } else {
            Toast.makeText(CadastroProleActivity.this, R.string.msg_erro_cadastro, Toast.LENGTH_LONG).show();
        }

    }

    public void onCheckNatimortoClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();

        if (checked) {
            inputPeso.setText("0");
            inputPeso.setFocusable(false);
            inputPeso.setEnabled(false);
            inputData.setEnabled(false);
        } else {
            inputPeso.setFocusableInTouchMode(true);
            inputPeso.setEnabled(true);
            inputData.setEnabled(true);
        }

    }

    public boolean validarDados() {
        boolean valido = true;

        List<TextView> campos = new ArrayList<>();
        campos.add(inputData);

        if (!checkNatimorto.isChecked())
            campos.add(inputPeso);

        for (TextView view : ValidaFormulario.camposTextosVazios(campos)) {
            view.setError(getString(R.string.msg_erro_campo));
            valido = false;
        }

        return valido;
    }

    public void cancelarOnClick(View view) {
        this.onBackPressed();
    }

    public Prole getObjetoProducao() {
        int id = inputId.getText().toString().equals("") ? 0 : Integer.valueOf(inputId.getText().toString());

        return new Prole(id,
                this.idAnimalMatriz,
                data,
                Float.valueOf(inputPeso.getText().toString()),
                checkNatimorto.isChecked());
    }

}
