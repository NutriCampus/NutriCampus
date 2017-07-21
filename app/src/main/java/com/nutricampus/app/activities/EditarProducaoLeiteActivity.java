package com.nutricampus.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.nutricampus.app.R;
import com.nutricampus.app.database.RepositorioProducaoDeLeite;
import com.nutricampus.app.entities.ProducaoDeLeite;

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
        inicializaCampoData();
    }

    @Override
    public void salvar(View view) {
        if (!validarDados()) {
            Toast.makeText(EditarProducaoLeiteActivity.this, R.string.msg_erro_cadastro_geral, Toast.LENGTH_LONG).show();
            return;
        }

        ProducaoDeLeite producao = getObjetoProducao();

        RepositorioProducaoDeLeite repositorioProducaoDeLeite = new RepositorioProducaoDeLeite(getBaseContext());
        boolean result = repositorioProducaoDeLeite.atualizarProducaoDeLeite(producao);

        if (result) {
            Toast.makeText(EditarProducaoLeiteActivity.this, getString(R.string.msg_sucesso_atualizar_registro),
                    Toast.LENGTH_LONG).show();
            Intent it = new Intent(EditarProducaoLeiteActivity.this, ListaProducaoLeiteActivity.class);
            startActivity(it);
            this.finish();
        } else {
            Toast.makeText(EditarProducaoLeiteActivity.this, getString(R.string.msg_erro_atualizar_registro), Toast.LENGTH_LONG).show();
        }

    }
}