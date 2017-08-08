package com.nutricampus.app.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.nutricampus.app.R;
import com.nutricampus.app.database.RepositorioProducaoDeLeite;
import com.nutricampus.app.entities.ProducaoDeLeite;
import com.nutricampus.app.utils.ValidaFormulario;

import java.util.ArrayList;
import java.util.List;

/*
Explicação para a supressão de warnings:
 - "squid:MaximumInheritanceDepth" = herança extendida em muitos niveis (mais que 5), permitido aqui já
 que refere-se a herança das classes das activities Android
 - "squid:S1172" = erro do sonarqube para os parametros "view" não utilizados
*/
@java.lang.SuppressWarnings({"squid:S1172", "squid:MaximumInheritanceDepth"})
public class CadastroProducaoLeiteActivity extends AbstractDataPickerActivity {

    protected EditText inputId;
    protected EditText inputLactose;
    protected EditText inputQuantidadeLeite;
    protected EditText inputGordura;
    protected EditText inputProteinaBruta;
    protected EditText inputProteinaVerdadeira;

    protected Button buttonSalvar;

    protected int idAnimal;

    protected void init() {
        inputId = (EditText) findViewById(R.id.input_id_producao);
        inputLactose = (EditText) findViewById(R.id.input_lactose);
        inputQuantidadeLeite = (EditText) findViewById(R.id.input_quantidade_leite);
        inputGordura = (EditText) findViewById(R.id.input_gordura);
        inputProteinaBruta = (EditText) findViewById(R.id.input_proteina_bruta);
        inputProteinaVerdadeira = (EditText) findViewById(R.id.input_proteina_verdadeira);
        buttonSalvar = (Button) findViewById(R.id.btn_salvar_producao);
        inputData = (EditText) findViewById(R.id.input_data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_producao_leite);

        init();

        inicializaCampoData(inputData);

        if (this.getIntent() != null)
            idAnimal = getIntent().getIntExtra("idAnimal", 0);
    }


    public ProducaoDeLeite getObjetoProducao() {
        int id = inputId.getText().toString().equals("") ? 0 : Integer.valueOf(inputId.getText().toString());
        return new ProducaoDeLeite(id,
                data,
                idAnimal,
                Float.valueOf(inputQuantidadeLeite.getText().toString()),
                Float.valueOf(inputLactose.getText().toString()),
                Float.valueOf(inputProteinaVerdadeira.getText().toString()),
                Float.valueOf(inputProteinaBruta.getText().toString()),
                Float.valueOf(inputGordura.getText().toString()));
    }

    public void salvar(View view) {
        if (!validarDados()) {
            Toast.makeText(CadastroProducaoLeiteActivity.this, R.string.msg_erro_cadastro_geral, Toast.LENGTH_LONG).show();
            return;
        }

        ProducaoDeLeite producao = getObjetoProducao();

        RepositorioProducaoDeLeite repositorio = new RepositorioProducaoDeLeite(this);
        int result = repositorio.inserirProducaoDeLeite(producao);


        if (result > 0) {
            Toast.makeText(CadastroProducaoLeiteActivity.this, R.string.msg_cadastro_salvo, Toast.LENGTH_LONG).show();

            this.onBackPressed();
        } else {
            Toast.makeText(CadastroProducaoLeiteActivity.this, R.string.msg_erro_cadastro, Toast.LENGTH_LONG).show();
        }

    }

    public void cancelarOnClick(View view) {
        this.onBackPressed();
    }

    public boolean validarDados() {
        boolean valido = true;

        List<TextView> campos = new ArrayList<>();
        campos.add(inputData);
        campos.add(inputQuantidadeLeite);
        campos.add(inputProteinaVerdadeira);
        campos.add(inputProteinaBruta);
        campos.add(inputLactose);
        campos.add(inputGordura);

        for (TextView view : ValidaFormulario.camposTextosVazios(campos)) {
            view.setError(getString(R.string.msg_erro_campo));
            valido = false;
        }


        return valido;
    }

}
