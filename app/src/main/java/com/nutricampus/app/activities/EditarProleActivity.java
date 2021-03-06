package com.nutricampus.app.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.nutricampus.app.R;
import com.nutricampus.app.database.RepositorioProle;
import com.nutricampus.app.entities.Prole;


/*
Explicação para a supressão de warnings:
 - "squid:MaximumInheritanceDepth" = herança extendida em muitos niveis (mais que 5), permitido aqui já
 que refere-se a herança das classes das activities Android
 - "squid:S1172" = erro do sonarqube para os parametros "view" não utilizados
*/
@java.lang.SuppressWarnings({"squid:S1172", "squid:MaximumInheritanceDepth"})
public class EditarProleActivity extends CadastroProleActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inicializaCampos();
    }

    @Override
    public void salvar(View view) {
        if (!validarDados()) {
            Toast.makeText(EditarProleActivity.this, R.string.msg_erro_cadastro_geral, Toast.LENGTH_LONG).show();
            return;
        }

        Prole prole = getObjetoProducao();

        RepositorioProle repositorio = new RepositorioProle(this);
        boolean result = repositorio.atualizarProle(prole);

        if (result) {
            Toast.makeText(EditarProleActivity.this, R.string.msg_sucesso_atualizar_registro, Toast.LENGTH_LONG).show();
            this.onBackPressed();
        } else {
            Toast.makeText(EditarProleActivity.this, R.string.msg_erro_atualizar_registro, Toast.LENGTH_LONG).show();
        }
    }


    protected void inicializaCampos() {

        inputId.setText(getIntent().getStringExtra("id"));
        inputPeso.setText(getIntent().getStringExtra("peso"));
        idAnimalMatriz = getIntent().getIntExtra("idAnimalMatriz", 0);
        inputData.setText(getIntent().getStringExtra("data"));
        checkNatimorto.setChecked(getIntent().getBooleanExtra("isNatimorto", false));

        onCheckNatimortoClicked(checkNatimorto);
        inicializaCampoData(inputData);
    }
}
