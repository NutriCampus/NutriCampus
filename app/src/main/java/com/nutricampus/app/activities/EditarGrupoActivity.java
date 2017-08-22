package com.nutricampus.app.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.nutricampus.app.R;
import com.nutricampus.app.database.RepositorioGrupo;
import com.nutricampus.app.entities.Grupo;


/*
Explicação para a supressão de warnings:
 - "squid:MaximumInheritanceDepth" = herança extendida em muitos niveis (mais que 5), permitido aqui já
 que refere-se a herança das classes das activities Android
 - "squid:S1172" = erro do sonarqube para os parametros "view" não utilizados
*/
@SuppressWarnings({"squid:S1172", "squid:MaximumInheritanceDepth"})
public class EditarGrupoActivity extends CadastrarGrupoActivity {

    private Grupo grupo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        grupo = (Grupo) getIntent().getSerializableExtra("grupo");

        inicializaCampos();
    }

    @Override
    public void salvar(View view) {
        if (!validarDados()) {
            Toast.makeText(EditarGrupoActivity.this, R.string.msg_erro_cadastro_geral, Toast.LENGTH_LONG).show();
            return;
        }

        String nomeAtual = inputNome.getText().toString();
        if ( !isNomeUnico(nomeAtual) ) {
            inputNome.setError(getString(R.string.msg_nome_existente));
            Toast.makeText(EditarGrupoActivity.this, R.string.msg_nome_existente, Toast.LENGTH_LONG).show();

            return;
        }


        Grupo grupoEditado = getObjeto();

        RepositorioGrupo repositorio = new RepositorioGrupo(this);
        boolean result = repositorio.atualizarGrupo(grupoEditado);

        if (result) {
            Toast.makeText(EditarGrupoActivity.this, R.string.msg_sucesso_atualizar_registro, Toast.LENGTH_LONG).show();
            this.onBackPressed();
        } else {
            Toast.makeText(EditarGrupoActivity.this, R.string.msg_erro_atualizar_registro, Toast.LENGTH_LONG).show();
        }
    }


    protected void inicializaCampos() {

        inputId.setText(String.valueOf(grupo.getId()));
        inputNome.setText(grupo.getIdentificador());
        inputObservacao.setText(grupo.getObservacao());

    }
}
