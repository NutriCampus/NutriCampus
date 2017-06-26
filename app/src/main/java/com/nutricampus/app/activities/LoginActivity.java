package com.nutricampus.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.nutricampus.app.R;
import com.nutricampus.app.database.RepositorioUsuario;
import com.nutricampus.app.entities.Usuario;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Kellison on 21/06/2017.
 * For project NutriCampus.
 */

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    // Pega a referência para as views
    @BindView(R.id.input_usuario) EditText _usuarioText;
    @BindView(R.id.input_senha) EditText _senhaText;
    @BindView(R.id.btn_login)   Button _entrarButton;
    @BindView(R.id.link_cadastro) TextView _cadastroLink;
    @BindView(R.id.link_esqueceu_senha) TextView _esqueceuLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.link_cadastro)
    public void cadastroOnClick(View view) {
        Intent intent = new Intent(this, CadastrarUsuarioActivity.class);
        startActivity(intent);
        this.finish();
    }

    @OnClick(R.id.link_esqueceu_senha)
    public void recuperarSenhaOnClick(View view) {
        Intent intent = new Intent(this, RecuperarSenhaActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_login)
    void entrarOnClick() {
        Log.d(TAG, "Login");

        if (!validaDados()) {
            falhaLogin("");
            return;
        }

        _entrarButton.setEnabled(false);

        String usuario = this._usuarioText.getText().toString();
        String senha = this._senhaText.getText().toString();

        if(login(usuario, senha)){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            Toast.makeText(getBaseContext(), getString(R.string.msg_bem_vindo), Toast.LENGTH_LONG).show();
            this.finish();
        }
        else{
            falhaLogin(getString(R.string.msg_usuario_nao_cadastrado));
        }

    }

    public boolean login(String usuarioValor, String senhaValor) {

        RepositorioUsuario repositorioUsuario = new RepositorioUsuario(getBaseContext());
        Usuario usuario = repositorioUsuario.buscarUsuario(usuarioValor,senhaValor);

        if(usuario != null)
            return true;
        else
            return false;
    }

    public void falhaLogin(String mensagem) {
        String msg = (mensagem.isEmpty()) ? "" : (", " + mensagem);
        Toast.makeText(getBaseContext(), getString(R.string.msg_falha_login) + msg, Toast.LENGTH_LONG).show();

        _entrarButton.setEnabled(true);
    }

    public boolean validaDados() {
        boolean valido = true;

        String usuario = this._usuarioText.getText().toString();
        String password = this._senhaText.getText().toString();

        if (usuario.isEmpty()){
            this._usuarioText.setError(getString(R.string.msg_erro_campo));
            valido = false;}
        else if(usuario.length() < 4) {
            this._usuarioText.setError(getString(R.string.msg_erro_crz));
            valido = false;
        } else {
            this._usuarioText.setError(null);
        }

        if (password.isEmpty()){
            this._senhaText.setError(getString(R.string.msg_erro_campo));
            valido = false;
        }
        else if(password.length() < 5) {
            this._senhaText.setError(getString(R.string.msg_erro_senha));
            valido = false;
        } else {
            this._senhaText.setError(null);
        }

        return valido;
    }
}