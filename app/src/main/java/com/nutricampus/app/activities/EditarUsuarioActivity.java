package com.nutricampus.app.activities;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.nutricampus.app.R;
import com.nutricampus.app.database.RepositorioUsuario;
import com.nutricampus.app.entities.Usuario;

import butterknife.ButterKnife;

/**
 * Created by jorge on 05/08/17.
 */
@java.lang.SuppressWarnings("squid:S1172")
public class EditarUsuarioActivity extends CadastrarUsuarioActivity {
    private Usuario usuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_conta);
        ButterKnife.bind(this);
        inicializaCampos();
    }
    private void inicializaCampos()
    {
        Intent it = getIntent();
        usuario = (Usuario)it.getSerializableExtra("usuario");

        edtNome.setText(usuario.getNome());
//        edtCpf.setText(usuario.getCpf());
//        edtRegistro.setText(usuario.getCrmv());
//        edtEmail.setText(usuario.getEmail());
//        edtSenha.setText(usuario.getSenha());
    }
    public void salvar(View v) {
        if (!validarDados()) {
            Toast.makeText(EditarUsuarioActivity.this, R.string.msg_erro_cadastro_geral, Toast.LENGTH_LONG).show();
            return;
        }
        usuario.setNome(edtNome.getText().toString());
        usuario.setEmail(edtRegistro.getText().toString());
        usuario.setSenha(edtSenha.getText().toString());


        String crmvDuplicado = edtRegistro.getText().toString();

        RepositorioUsuario repositorioUsuario = new RepositorioUsuario(getBaseContext());
        Usuario usuarioCpfDuplicado = repositorioUsuario.buscarUsuario(crmvDuplicado);

        if(usuarioCpfDuplicado != null &&
                (!crmvDuplicado.equals(usuario.getCrmv()))) {

            Toast.makeText(EditarUsuarioActivity.this,
                    getString(R.string.msg_erro_cadastro_usuario),
                    Toast.LENGTH_LONG).show();

            return;
        }
        boolean result = repositorioUsuario.atualizarUsuario(usuario);

        if (result) {
            Toast.makeText(EditarUsuarioActivity.this, getString(R.string.msg_sucesso_atualizar, "Usuario", usuario.getCrmv()),
                    Toast.LENGTH_LONG).show();
            this.onBackPressed();
        } else {
            Toast.makeText(EditarUsuarioActivity.this, "Erro ao gravar alterações do usuário", Toast.LENGTH_LONG).show();
        }
    }
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == android.R.id.home)
//            voltarActivity();
//
//        return true;
//    }
//
//    @Override
//    public void onBackPressed(){
//        voltarActivity();
//    }
//
//    private void voltarActivity() {
//        Intent it = new Intent(EditarProprietarioActivity.this, ListaProprietariosActivity.class);
//        startActivity(it);
//        finish();
//    }
//}

    public void cancelarEditarUsuario(View v) {
        carregarTelaInicial();
    }
    public void carregarTelaInicial() {
        Intent it = new Intent(this, MainActivity.class);
        startActivity(it);
        this.finish();
    }


}
