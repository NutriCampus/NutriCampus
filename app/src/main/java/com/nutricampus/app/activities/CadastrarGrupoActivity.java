package com.nutricampus.app.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.nutricampus.app.R;
import com.nutricampus.app.database.RepositorioGrupo;
import com.nutricampus.app.database.SharedPreferencesManager;
import com.nutricampus.app.entities.Grupo;
import com.nutricampus.app.utils.ValidaFormulario;

import java.util.ArrayList;
import java.util.List;

public class CadastrarGrupoActivity extends AppCompatActivity {

    protected EditText inputId;
    protected EditText inputNome;
    protected EditText inputObservacao;

    private int idUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cadastrar_grupo);

        init();

    }

    protected void init() {
        inputNome = (EditText) findViewById(R.id.input_nome_grupo);
        inputId = (EditText) findViewById(R.id.input_id_grupo);
        inputObservacao = (EditText) findViewById(R.id.input_observacao);

        idUsuario = Integer.parseInt(new SharedPreferencesManager(this).getIdUsuario());
    }

    public void salvar(View view) {

        if (!validarDados()) {
            Toast.makeText(CadastrarGrupoActivity.this, R.string.msg_erro_cadastro_geral, Toast.LENGTH_LONG).show();
            return;
        }

        String nomeAtual = inputNome.getText().toString();
        if ( !isNomeUnico(nomeAtual) ) {
            inputNome.setError(getString(R.string.msg_nome_existente));
            Toast.makeText(CadastrarGrupoActivity.this, R.string.msg_nome_existente, Toast.LENGTH_LONG).show();

            return;
        }

        Grupo prole = getObjeto();

        RepositorioGrupo repositorio = new RepositorioGrupo(this);
        int result = repositorio.inserirGrupo(prole);

        if (result > 0) {
            Toast.makeText(CadastrarGrupoActivity.this, R.string.msg_cadastro_salvo, Toast.LENGTH_LONG).show();
            this.onBackPressed();
        } else {
            Toast.makeText(CadastrarGrupoActivity.this, R.string.msg_erro_cadastro, Toast.LENGTH_LONG).show();
        }


    }

    protected boolean isNomeUnico(String nome) {
        RepositorioGrupo repositorio = new RepositorioGrupo(this);
        return (repositorio.buscarGrupo(nome) == null);
    }

    public boolean validarDados() {
        boolean valido = true;

        List<TextView> campos = new ArrayList<>();
        campos.add(inputNome);

        for (TextView view : ValidaFormulario.camposTextosVazios(campos)) {
            view.setError(getString(R.string.msg_erro_campo));
            valido = false;
        }

        return valido;
    }


    public Grupo getObjeto() {
        int id = inputId.getText().toString().equals("") ? 0 : Integer.valueOf(inputId.getText().toString());

        return new Grupo(id,
                this.inputNome.getText().toString(),
                this.inputObservacao.getText().toString(),
                this.idUsuario);
    }

    public void cancelarOnClick(View view) {
        this.onBackPressed();
    }


}
