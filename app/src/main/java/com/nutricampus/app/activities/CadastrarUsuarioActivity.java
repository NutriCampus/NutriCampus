package com.nutricampus.app.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.nutricampus.app.R;
import com.nutricampus.app.model.Mascara;
import com.nutricampus.app.entities.Usuario;

/**
 * Created by Felipe on 23/06/2017.
 * For project NutriCampus.
 * Contact: <felipeguimaraes540@gmail.com>
 */

public class RegisterUsersActivity extends AppCompatActivity {

    EditText edtNome,
             edtCpf,
             edtRegistro,
             edtEmail,
             edtSenha;

    String nome,
           cpf,
           registro,
           email,
           senha;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_users);

        edtNome = (EditText) findViewById(R.id.edtNome);
        edtCpf = (EditText) findViewById(R.id.edtCpf);
        edtRegistro = (EditText) findViewById(R.id.edtRegistro);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtSenha = (EditText) findViewById(R.id.edtSenha);

        edtCpf.addTextChangedListener(Mascara.insert(Mascara.CPF_MASK, edtCpf));
    }

    public void cancelarCriarUsuario(View v) {
        carregarLogin();
    }

    public void carregarLogin() {
        Intent it = new Intent(this, LoginActivity.class);
        startActivity(it);
        this.finish();
    }

    public void criarUsuario(View v) {
        if(!validaDados()) {
            Toast.makeText(getBaseContext(), "Campos inválidos", Toast.LENGTH_LONG).show();
            return;
        }

        if(!Mascara.validarCpf(cpf)) {
            Toast.makeText(getBaseContext(), getString(R.string.msg_erro_cpf_2), Toast.LENGTH_LONG).show();
            return;
        }

        Usuario usuario = new Usuario(cpf, email, registro, nome, registro);

        
    }


    public boolean validaDados() {

        boolean valido = true;

        nome = this.edtNome.getText().toString();
        cpf = this.edtCpf.getText().toString();
        registro = this.edtRegistro.getText().toString();
        email = this.edtEmail.getText().toString();
        senha = this.edtSenha.getText().toString();

        if (nome.isEmpty()) {
            this.edtNome.setError(getString(R.string.msg_erro_campo));
            valido = false;
        } else {
            this.edtNome.setError(null);
        }

        if (cpf.isEmpty()) {
            this.edtNome.setError(getString(R.string.msg_erro_campo));
            valido = false;
        } else if (cpf.length() < 14) {
            this.edtCpf.setError(getString(R.string.msg_erro_cpf_1));
            valido = false;
        } else {
            this.edtCpf.setError(null);
        }

        if (registro.isEmpty()) {
            this.edtRegistro.setError(getString(R.string.msg_erro_campo));
            valido = false;
        } else {
            this.edtRegistro.setError(null);
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edtEmail.setError(getString(R.string.msg_erro_email));
            valido = false;
        }

        if (senha.isEmpty()) {
            this.edtSenha.setError(getString(R.string.msg_erro_campo));
            valido = false;
        } else if (senha.length() < 5) {
            this.edtSenha.setError(getString(R.string.msg_erro_senha));
            valido = false;
        } else {
            this.edtSenha.setError(null);
        }

        return valido;
    }
}
