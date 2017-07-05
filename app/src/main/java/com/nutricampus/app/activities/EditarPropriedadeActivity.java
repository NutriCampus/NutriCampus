package com.nutricampus.app.activities;

import android.os.Bundle;
import android.view.View;

public class EditarPropriedadeActivity extends CadastrarPropriedadeActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        inicializaCampos();

    }

    protected void inicializaCampos(){
        inputNome.setText("Exemplo");

        buttonSalvar.setText("Atualizar");
    }

    @Override
    protected void salvar(View view) {

    }
}
