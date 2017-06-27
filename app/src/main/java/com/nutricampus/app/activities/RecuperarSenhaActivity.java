package com.nutricampus.app.activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nutricampus.app.R;
import com.nutricampus.app.database.RepositorioUsuario;
import com.nutricampus.app.entities.Usuario;
import com.nutricampus.app.services.SendMailTask;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecuperarSenhaActivity extends AppCompatActivity  {

    @BindView(R.id.input_usuario_recupera) EditText _usuarioText;
    @BindView(R.id.btn_recuperar) Button _recuperaBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_senha);

        ButterKnife.bind(this);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

    }

    @OnClick(R.id.btn_recuperar)
    public void recuperaSenha(){

        if (!validaDados()) {
            return;
        }

        _recuperaBtn.setEnabled(false);

        String usuarioValor = this._usuarioText.getText().toString();
        Usuario usuarioDados = buscaUsuario(usuarioValor);

        if( usuarioDados != null){
            enviaEmail(usuarioDados);
            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            Toast.makeText(getBaseContext(),"Senha enviada por e-mail",Toast.LENGTH_LONG).show();

                        }
                    }, 3000);
        }
        else{
            Toast.makeText(getBaseContext(),
                    getString(R.string.msg_dados_login_invalidos).substring(0, 1).toUpperCase() +
                            getString(R.string.msg_dados_login_invalidos).substring(1),
                    Toast.LENGTH_LONG).show();
        }
        _recuperaBtn.setEnabled(true);
    }


    public boolean validaDados() {
        boolean valido = true;

        String usuario = this._usuarioText.getText().toString();

        if (usuario.isEmpty()){
            this._usuarioText.setError(getString(R.string.msg_erro_campo));
            valido = false;}
        else if(usuario.length() < 4) {
            this._usuarioText.setError(getString(R.string.msg_erro_crz));
            valido = false;
        } else {
            this._usuarioText.setError(null);
        }

        return valido;
    }

    public Usuario buscaUsuario(String usuarioValor){

        RepositorioUsuario repositorioUsuario = new RepositorioUsuario(getBaseContext());
        Usuario usuario = repositorioUsuario.buscarUsuario(usuarioValor);

        return usuario;
    }

    public void enviaEmail(Usuario usuario){
        String corpoEmail = "Olá, " + usuario.getNome() + ".<br><br>" +
                "Você está recebendo este e-mail por que solicitou sua senha " +
                "através do nosso aplicativo.<br>"+
                "Sua senha é: " + usuario.getSenha() + "<br><br>" +
                "Atenciosamente,<br>NutriCampus App.";

        String assuntoEmail = "NutriCampusApp - Recuperação de Senha";

        new SendMailTask(RecuperarSenhaActivity.this).execute("nutricampusapp@gmail.com",
                "nutcam0511",
                Arrays.asList(usuario.getEmail().split("\\s*,\\s*")),
                assuntoEmail, corpoEmail);

    }

}
