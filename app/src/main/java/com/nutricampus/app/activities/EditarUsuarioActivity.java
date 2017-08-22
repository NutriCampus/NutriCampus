package com.nutricampus.app.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import com.nutricampus.app.R;
import com.nutricampus.app.database.RepositorioAnimal;
import com.nutricampus.app.database.RepositorioDadosComplAnimal;
import com.nutricampus.app.database.RepositorioProducaoDeLeite;
import com.nutricampus.app.database.RepositorioProle;
import com.nutricampus.app.database.RepositorioPropriedade;
import com.nutricampus.app.database.RepositorioProprietario;
import com.nutricampus.app.database.RepositorioUsuario;
import com.nutricampus.app.database.SharedPreferencesManager;
import com.nutricampus.app.entities.Animal;
import com.nutricampus.app.entities.Propriedade;
import com.nutricampus.app.entities.Usuario;
import com.nutricampus.app.utils.ValidaFormulario;

import java.util.ArrayList;
import java.util.List;

@java.lang.SuppressWarnings("squid:S1172")
public class EditarUsuarioActivity extends CadastrarUsuarioActivity {

    private SharedPreferencesManager session;
    private RepositorioUsuario repositorioUsuario;
    private Usuario usuario;
    private boolean isLogoff = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        session = new SharedPreferencesManager(this);
        repositorioUsuario = new RepositorioUsuario(EditarUsuarioActivity.this);
        usuario = repositorioUsuario.buscarUsuario(session.getCrmvNC(), session.getSenha());

        inicializarCampos();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_delete_usuario, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home)
            voltarActivity();
        else if (item.getItemId() == R.id.acao_delete)
            deletarUsuario();


        return super.onOptionsItemSelected(item);
    }

    private void deletarUsuario() {

        new AlertDialog.Builder(this)
                .setMessage("Excluir usuário")
                .setCancelable(false)
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        RepositorioProprietario repositorioProprietario = new RepositorioProprietario(EditarUsuarioActivity.this);
                        RepositorioPropriedade repositorioPropriedade = new RepositorioPropriedade(EditarUsuarioActivity.this);
                        RepositorioAnimal repositorioAnimal = new RepositorioAnimal(EditarUsuarioActivity.this);
                        RepositorioDadosComplAnimal repositorioDadosComplAnimal = new RepositorioDadosComplAnimal(EditarUsuarioActivity.this);
                        RepositorioProducaoDeLeite repositorioProducaoDeLeite = new RepositorioProducaoDeLeite(EditarUsuarioActivity.this);
                        RepositorioProle repositorioProle = new RepositorioProle(EditarUsuarioActivity.this);

                        ArrayList<Propriedade> propriedades = (ArrayList<Propriedade>)
                                repositorioPropriedade.buscarPropriedadesPorUsuario(usuario.getId());

                        int result = repositorioUsuario.removerUsuario(usuario);
                        List<Animal> listAnimal;

                        if (result > 0) {
                            Toast.makeText(EditarUsuarioActivity.this,
                                    getString(R.string.msg_sucesso_excluir_usuario),
                                    Toast.LENGTH_LONG).show();

                            if (!(propriedades.isEmpty())) {
                                for (Propriedade p : propriedades) {

                                    int idProprietario = p.getIdProprietario();
                                    listAnimal = repositorioAnimal.buscarPorPropridade(p.getId());
                                    for(Animal a : listAnimal) {
                                        Log.e("FGP", ">>>> " + a);
                                        repositorioDadosComplAnimal.removerDadosCompl(a.getId());
                                        repositorioProle.removerProle(a.getId());
                                        repositorioProducaoDeLeite.removerProducaoDeLeite(a.getId());
                                        repositorioAnimal.removerAnimal(a);
                                    }

                                    if(repositorioPropriedade.propriedadesOfProprietario(idProprietario).size() <= 1) {
                                        repositorioProprietario.removerProprietario(idProprietario);
                                    }
                                    //repositorioPropriedade.removerPropriedadePorProprietario(idProprietario);

                                    repositorioPropriedade.removerPropriedade(p);
                                }
                            }

                            EditarUsuarioActivity.this.finish();
                            session.logoutUser();

                        } else {
                            Toast.makeText(EditarUsuarioActivity.this,
                                    "Não foi possivel excuir", Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .setNegativeButton("Não", null)
                .show();
    }

    private void inicializarCampos() {

        edtNome.setText(usuario.getNome());
        edtCpf.setText(usuario.getCpf());
        edtRegistro.setText(usuario.getCrmv());
        edtEmail.setText(usuario.getEmail());
        edtSenha.setText(usuario.getSenha());

        edtCpf.setEnabled(false);
        edtRegistro.setEnabled(false);
        txtAlterSenha.setVisibility(View.VISIBLE);
        edtSenha.setInputType(EditorInfo.TYPE_CLASS_TEXT);
        btnSalvar.setText(R.string.atualizar);

    }

    @Override
    public void criarUsuario(View v) {
        if (!validarDados()) {
            Toast.makeText(EditarUsuarioActivity.this, "Campos inválidos", Toast.LENGTH_LONG).show();
            return;
        }

        if (!(senha.equals(usuario.getSenha())))
            isLogoff = true;

        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setSenha(senha);

        boolean isAtualizado = repositorioUsuario.atualizarUsuario(usuario);

        if (isAtualizado) {
            Toast.makeText(EditarUsuarioActivity.this,
                    getString(R.string.msg_sucesso_atualizar_usuario),
                    Toast.LENGTH_LONG).show();

            if (isLogoff)
                session.logoutUser();
            else
                EditarUsuarioActivity.this.finish();

        } else {
            Toast.makeText(EditarUsuarioActivity.this,
                    getString(R.string.msg_erro_atualizar_registro), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean validarDados() {
        boolean valido = true;

        nome = this.edtNome.getText().toString();
        email = this.edtEmail.getText().toString();
        senha = this.edtSenha.getText().toString();

        List<TextView> campos = new ArrayList<>();
        campos.add(edtNome);
        campos.add(edtEmail);
        campos.add(edtSenha);

        for (TextView view : ValidaFormulario.camposTextosVazios(campos)) {
            view.setError(getString(R.string.msg_erro_campo));
            valido = false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edtEmail.setError(getString(R.string.msg_erro_email));
            valido = false;
        }

        if (senha.length() < 5) {
            this.edtSenha.setError(getString(R.string.msg_erro_senha));
            valido = false;
        } else {
            this.edtSenha.setError(null);
        }

        return valido;
    }

    @Override
    public void cancelarCriarUsuario(View v) {
        startActivity(new Intent(
                EditarUsuarioActivity.this, ConfigActivity.class));
    }


    @Override
    public void onBackPressed() {
        voltarActivity();
    }

    private void voltarActivity() {
        Intent it = new Intent(
                EditarUsuarioActivity.this, ConfigActivity.class);

        startActivity(it);
        finish();
    }
}
