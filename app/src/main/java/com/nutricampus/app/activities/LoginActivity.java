package com.nutricampus.app.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.nutricampus.app.R;
import com.nutricampus.app.database.RepositorioUsuario;
import com.nutricampus.app.database.SQLiteManager;
import com.nutricampus.app.entities.Usuario;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;

/**
 * Created by Mateus on 29/05/2017.
 * For project NutriCampus.
 * Contact: <paulomatew@gmail.com>
 */

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    // PEga a referência para as views
    @BindView(R.id.input_usuario) EditText _usuarioText;
    @BindView(R.id.input_senha) EditText _senhaText;
    @BindView(R.id.btn_login)   Button _entrarButton;
    @BindView(R.id.link_cadastro) TextView _cadastroLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

    }

    @OnClick(R.id.link_cadastro)
    public void cadastroOnClick(View view) {
        Intent intent = new Intent(this, RegisterUsersActivity.class);
        startActivity(intent);
        this.finish();
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
            this.finish();
        }
        else{
            falhaLogin("usuário não cadastrado");
        }

    }

    public boolean login(String usuarioValor, String senhaValor) {

        RepositorioUsuario repoUsuario = new RepositorioUsuario();
        Toast.makeText(getBaseContext(), "Fa", Toast.LENGTH_LONG).show();
        Usuario usuario = repoUsuario.buscarUsuario(usuarioValor);

        if(usuario != null)
            return true;
        else
            return false;
    }

    public void sucessoLogin() {
        _entrarButton.setEnabled(true);
        finish();
    }

    public void falhaLogin(String mensagem) {
        String msg = (mensagem.isEmpty()) ? "" : ("," + mensagem);
        Toast.makeText(getBaseContext(), "Falha no login" + msg, Toast.LENGTH_LONG).show();

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
        else if(password.length() < 6) {
            this._senhaText.setError("Senha deve ter no mínimo 6 caracteres");
            valido = false;
        } else {
            this._senhaText.setError(null);
        }

        return valido;
    }
}
