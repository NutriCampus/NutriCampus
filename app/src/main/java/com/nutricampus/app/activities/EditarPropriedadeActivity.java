package com.nutricampus.app.activities;

import android.os.Bundle;

public class EditarPropriedadeActivity extends CadastrarPropriedadeActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        inicializaCampos();
    }

    protected void inicializaCampos(){
        inputId.setText(getIntent().getStringExtra("id"));
        inputNome.setText(getIntent().getStringExtra("nome"));
        inputRua.setText(getIntent().getStringExtra("rua"));
        inputBairro.setText(getIntent().getStringExtra("bairro"));
        inputNumero.setText(getIntent().getStringExtra("numero"));
        inputCep.setText(getIntent().getStringExtra("cep"));
        inputCidade.setText(getIntent().getStringExtra("cidade"));
        inputEstado.setText(getIntent().getStringExtra("estado"));
        inputTelefone.setText(getIntent().getStringExtra("telefone"));

        buttonSalvar.setText("Atualizar");
    }
}
