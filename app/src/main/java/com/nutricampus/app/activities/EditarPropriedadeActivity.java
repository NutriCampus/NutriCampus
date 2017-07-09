package com.nutricampus.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.nutricampus.app.R;
import com.nutricampus.app.database.RepositorioPropriedade;
import com.nutricampus.app.database.RepositorioProprietario;
import com.nutricampus.app.entities.Propriedade;
import com.nutricampus.app.entities.Proprietario;
import com.nutricampus.app.model.ListaPropriedadesAdapter;

public class EditarPropriedadeActivity extends CadastrarPropriedadeActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        inicializaCampos();
    }

    protected void inicializaCampos(){

        int id = getIntent().getIntExtra("id",0);
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
        inputIdProprietario.setText(String.valueOf(idProprietario));

        preencherSpinnerListaProprietario();


        buttonSalvar.setText("Atualizar");
    }

    @Override
    protected void salvar(View view) {
        if (!validaDados()) {
            Toast.makeText(EditarPropriedadeActivity.this, R.string.msg_erro_cadastro_geral, Toast.LENGTH_LONG).show();
            return;
        }
        Proprietario proprietario = (Proprietario) spinnerProprietario.getSelectedItem();

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
                ((Proprietario) spinnerProprietario.getSelectedItem()).getId());

        propriedade.setProprietario(proprietario);

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
