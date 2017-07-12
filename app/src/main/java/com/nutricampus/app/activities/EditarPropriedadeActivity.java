package com.nutricampus.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.nutricampus.app.R;
import com.nutricampus.app.database.RepositorioPropriedade;
import com.nutricampus.app.entities.Propriedade;

public class EditarPropriedadeActivity extends CadastrarPropriedadeActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentView(R.layout.activity_editar_propriedade);
        inicializaCampos();
    }

    private void inicializaCampos() {

        int id = getIntent().getIntExtra("id",0);
        Log.i("ID _ INT", String.valueOf(id));
        inputId.setText(String.valueOf(id));

        inputNome.setText(getIntent().getStringExtra("nome"));
        inputRua.setText(getIntent().getStringExtra("rua"));
        inputBairro.setText(getIntent().getStringExtra("bairro"));
        inputNumero.setText(getIntent().getStringExtra("numero"));
        inputCep.setText(getIntent().getStringExtra("cep"));
        inputCidade.setText(getIntent().getStringExtra("cidade"));
        inputEstado.setText(getIntent().getStringExtra("estado"));
        inputTelefone.setText(getIntent().getStringExtra("telefone"));

        int idProprietario = getIntent().getIntExtra("idProprietario",0);
        Log.i("IDPROPRIETARIO", idProprietario + "");
        inputIdProprietario.setText(String.valueOf(idProprietario));

        preencherSpinnerListaProprietario();


        buttonSalvar.setText(R.string.atualizar);
    }

    protected void salvar(View view) {
        if (!validaDados()) {
            Toast.makeText(EditarPropriedadeActivity.this, R.string.msg_erro_cadastro_geral, Toast.LENGTH_LONG).show();
            return;
        }

        Propriedade propriedade = new Propriedade(
                Integer.parseInt( inputId.getText().toString()),
                inputNome.getText().toString(),
                inputTelefone.getText().toString(),
                inputRua.getText().toString(),
                inputBairro.getText().toString(),
                inputCep.getText().toString(),
                inputCidade.getText().toString(),
                inputEstado.getText().toString(),
                inputNumero.getText().toString(),
                Integer.parseInt(inputIdProprietario.getText().toString()),
                Integer.parseInt(session.getIdNC()));


        RepositorioPropriedade repositorioPropriedade = new RepositorioPropriedade(getBaseContext());
        boolean result = repositorioPropriedade.atualizarPropriedade(propriedade);

        if(result) {
            Toast.makeText(EditarPropriedadeActivity.this, getString(R.string.msg_sucesso_atualizar, "Propriedade", propriedade.getNome()),
                    Toast.LENGTH_LONG).show();
            Intent it = new Intent(EditarPropriedadeActivity.this, ListaPropriedadesActivity.class);
            startActivity(it);
        } else {
            Toast.makeText(EditarPropriedadeActivity.this, "Erro ao gravar Propriedade", Toast.LENGTH_LONG).show();
        }

    }
}
