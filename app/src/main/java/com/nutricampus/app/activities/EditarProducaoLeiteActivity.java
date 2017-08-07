package com.nutricampus.app.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.nutricampus.app.R;
import com.nutricampus.app.database.RepositorioProducaoDeLeite;
import com.nutricampus.app.entities.ProducaoDeLeite;


/*
Explicação para a supressão de warnings:
 - "squid:MaximumInheritanceDepth" = herança extendida em muitos niveis (mais que 5), permitido aqui já
 que refere-se a herança das classes das activities Android
 - "squid:S1172" = erro do sonarqube para os parametros "view" não utilizados
*/
@java.lang.SuppressWarnings({"squid:S1172", "squid:MaximumInheritanceDepth"})
public class EditarProducaoLeiteActivity extends CadastroProducaoLeiteActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        inicializaCampos();
    }

    private void inicializaCampos() {
        inputId.setText(getIntent().getStringExtra("id"));
        inputGordura.setText(getIntent().getStringExtra("gordura"));
        inputLactose.setText(getIntent().getStringExtra("lactose"));
        inputProteinaBruta.setText(getIntent().getStringExtra("proteinaBruta"));
        inputProteinaVerdadeira.setText(getIntent().getStringExtra("proteinaVerdadeira"));
        inputQuantidadeLeite.setText(getIntent().getStringExtra("quantidade"));
        inputData.setText(getIntent().getStringExtra("data"));

        buttonSalvar.setText(R.string.atualizar);
        inicializaCampoData(inputData);
    }

    @Override
    public void salvar(View view) {
        if (!validarDados()) {
            Toast.makeText(EditarProducaoLeiteActivity.this, R.string.msg_erro_cadastro_geral, Toast.LENGTH_LONG).show();
            return;
        }

        ProducaoDeLeite producao = getObjetoProducao();
        Log.w("PRDO", producao.toString());
        RepositorioProducaoDeLeite repositorioProducaoDeLeite = new RepositorioProducaoDeLeite(getBaseContext());
        boolean result = repositorioProducaoDeLeite.atualizarProducaoDeLeite(producao);

        if (result) {
            Toast.makeText(EditarProducaoLeiteActivity.this, getString(R.string.msg_sucesso_atualizar_registro),
                    Toast.LENGTH_LONG).show();
            this.onBackPressed();
        } else {
            Toast.makeText(EditarProducaoLeiteActivity.this, getString(R.string.msg_erro_atualizar_registro), Toast.LENGTH_LONG).show();
        }

    }
}