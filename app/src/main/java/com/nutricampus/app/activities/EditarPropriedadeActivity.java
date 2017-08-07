package com.nutricampus.app.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.nutricampus.app.R;
import com.nutricampus.app.database.RepositorioPropriedade;
import com.nutricampus.app.entities.Propriedade;


/*
Explicação para a supressão de warnings:
 - "squid:MaximumInheritanceDepth" = herança extendida em muitos niveis (mais que 5), permitido aqui já
 que refere-se a herança das classes das activities Android
 - "squid:S1172" = erro do sonarqube para os parametros "view" não utilizados
*/
@java.lang.SuppressWarnings({"squid:S1172", "squid:MaximumInheritanceDepth"})
public class EditarPropriedadeActivity extends CadastrarPropriedadeActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        inicializaCampos();
    }

    private void inicializaCampos() {

        int id = getIntent().getIntExtra("id", 0);

        inputId.setText(String.valueOf(id));

        inputNome.setText(getIntent().getStringExtra("nome"));
        inputRua.setText(getIntent().getStringExtra("rua"));
        inputBairro.setText(getIntent().getStringExtra("bairro"));
        inputNumero.setText(getIntent().getStringExtra("numero"));
        inputCep.setText(getIntent().getStringExtra("cep"));
        inputCidade.setText(getIntent().getStringExtra("cidade"));
        inputEstado.setText(getIntent().getStringExtra("estado"));
        inputTelefone.setText(getIntent().getStringExtra("telefone"));

        int idProprietario = getIntent().getIntExtra("idProprietario", 0);

        inputIdProprietario.setText(String.valueOf(idProprietario));

        preencherSpinnerListaProprietario();


        buttonSalvar.setText(R.string.atualizar);
    }

    @Override
    protected void salvar(View view) {
        if (!validaDados()) {
            Toast.makeText(EditarPropriedadeActivity.this, R.string.msg_erro_cadastro_geral, Toast.LENGTH_LONG).show();
            return;
        }

        Propriedade propriedade = new Propriedade(
                Integer.parseInt(inputId.getText().toString()),
                inputNome.getText().toString(),
                inputTelefone.getText().toString(),
                inputRua.getText().toString(),
                inputBairro.getText().toString(),
                inputCep.getText().toString(),
                inputCidade.getText().toString(),
                inputEstado.getText().toString(),
                inputNumero.getText().toString(),
                Integer.parseInt(inputIdProprietario.getText().toString()),
                Integer.parseInt(session.getIdUsuario()));


        RepositorioPropriedade repositorioPropriedade = new RepositorioPropriedade(getBaseContext());
        boolean result = repositorioPropriedade.atualizarPropriedade(propriedade);

        if (result) {
            Toast.makeText(EditarPropriedadeActivity.this, getString(R.string.msg_sucesso_atualizar, "Propriedade", propriedade.getNome()),
                    Toast.LENGTH_LONG).show();
            this.onBackPressed();
        } else {
            Toast.makeText(EditarPropriedadeActivity.this, "Erro ao gravar Propriedade", Toast.LENGTH_LONG).show();
        }

    }
}
