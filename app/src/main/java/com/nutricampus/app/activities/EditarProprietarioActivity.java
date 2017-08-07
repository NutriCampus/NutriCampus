package com.nutricampus.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.nutricampus.app.R;
import com.nutricampus.app.database.RepositorioProprietario;
import com.nutricampus.app.entities.Proprietario;
import com.nutricampus.app.utils.ValidaFormulario;


/**
 * Created by Felipe on 01/08/2017.
 * For project NutriCampus.
 * Contact: <felipeguimaraes540@gmail.com>
 */

/*
Explicação para a supressão de warnings:
 - "squid:MaximumInheritanceDepth" = herança extendida em muitos niveis (mais que 5), permitido aqui já
 que refere-se a herança das classes das activities Android
 - "squid:S1172" = erro do sonarqube para os parametros "view" não utilizados
*/
@java.lang.SuppressWarnings({"squid:S1172", "squid:MaximumInheritanceDepth"})
public class EditarProprietarioActivity extends CadastrarProprietarioActivity {

    private Proprietario proprietario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        inicializarCampos();
    }

    public void inicializarCampos() {

        Intent it = getIntent();
        proprietario = (Proprietario) it.getSerializableExtra("proprietario");

        inputNomeProprietario.setText(proprietario.getNome());
        inputCpfProprietario.setText(proprietario.getCpf());
        inputEmailProprietario.setText(proprietario.getEmail());
        inputFoneProprietario.setText(proprietario.getTelefone());

        btnSalvar.setText("Atualizar");
    }


    @Override
    public void salvar(View v) {

        if (!validaDados()) {
            Toast.makeText(EditarProprietarioActivity.this,R.string.msg_erro_cadastro_geral, Toast.LENGTH_LONG).show();
            return;
        }

        if (!ValidaFormulario.validarCpf(inputCpfProprietario.getText().toString())) {
            Toast.makeText(EditarProprietarioActivity.this, getString(R.string.msg_erro_cpf_2), Toast.LENGTH_LONG).show();
            return;
        }


        proprietario.setNome(inputNomeProprietario.getText().toString());
        proprietario.setEmail(inputEmailProprietario.getText().toString());
        proprietario.setTelefone(inputFoneProprietario.getText().toString());

        String cpfDuplicado = inputCpfProprietario.getText().toString();

        RepositorioProprietario repositorioProprietario = new RepositorioProprietario(getBaseContext());
        Proprietario proprietarioCpfDuplicado = repositorioProprietario.buscarProprietario(cpfDuplicado);
        if(proprietarioCpfDuplicado != null &&
            (!cpfDuplicado.equals(proprietario.getCpf()))) {

            Toast.makeText(EditarProprietarioActivity.this,
                    getString(R.string.msg_erro_cadastro_proprietario),
                    Toast.LENGTH_LONG).show();

            return;
        }


        boolean f = repositorioProprietario.atualizarProprietario(proprietario);

        if (f) {

            Toast.makeText(EditarProprietarioActivity.this,
                    getString(R.string.msg_sucesso_atualizar, "Proprietário", ""),
                    Toast.LENGTH_LONG).show();

            EditarProprietarioActivity.this.finish();

        } else {
            Toast.makeText(EditarProprietarioActivity.this, getString(R.string.msg_erro_atualizar_proprietario),
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            voltarActivity();

        return true;
    }

    @Override
    public void onBackPressed(){
        voltarActivity();
    }

    private void voltarActivity() {
        Intent it = new Intent(EditarProprietarioActivity.this, ListaProprietariosActivity.class);
        startActivity(it);
        finish();
    }
}
