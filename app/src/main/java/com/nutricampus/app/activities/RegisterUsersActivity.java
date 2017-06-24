package com.nutricampus.app.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.nutricampus.app.R;
import com.nutricampus.app.model.Mask;
import com.nutricampus.app.entities.Usuario;

/**
 * Created by Felipe on 23/06/2017.
 * For project NutriCampus.
 * Contact: <felipeguimaraes540@gmail.com>
 */

public class RegisterUsersActivity extends AppCompatActivity {

    EditText edtName,
             edtCpf,
             edtRegister,
             edtEmail,
             edtPassword;

    String name,
           cpf,
           register,
           email,
           password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_users);

        edtName = (EditText) findViewById(R.id.edtName);
        edtCpf = (EditText) findViewById(R.id.edtCpf);
        edtRegister = (EditText) findViewById(R.id.edtRegister);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtPassword = (EditText) findViewById(R.id.edtPassword);

        edtCpf.addTextChangedListener(Mask.insert(Mask.CPF_MASK, edtCpf));
    }

    public void cancelCreateUser(View v) {
        carregarLogin();
    }

    public void carregarLogin() {
        Intent it = new Intent(this, LoginActivity.class);
        startActivity(it);
        this.finish();
    }

    public void createUser(View v) {
        if(!validaDados()) {
            Toast.makeText(getBaseContext(), "Campos inválidos", Toast.LENGTH_LONG).show();
            return;
        }

        if(!Mask.validateCpf(cpf)) {
            Toast.makeText(getBaseContext(), getString(R.string.msg_erro_cpf_2), Toast.LENGTH_LONG).show();
            return;
        }

        Usuario user = new Usuario(cpf, email, register, name, register);

        //Caixa de Dialogo
        AlertDialog.Builder dialog = new AlertDialog.Builder(RegisterUsersActivity.this);
        dialog.setTitle("Cadastro");
        dialog.setMessage(String.format(
                "Usuário %s cadastrado com sucesso !", user.getNome()));
        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                carregarLogin();
            }
        });
        dialog.show();
    }


    public boolean validaDados() {

        boolean valido = true;

        name = this.edtName.getText().toString();
        cpf = this.edtCpf.getText().toString();
        register = this.edtRegister.getText().toString();
        email = this.edtEmail.getText().toString();
        password = this.edtPassword.getText().toString();

        if (name.isEmpty()) {
            this.edtName.setError(getString(R.string.msg_erro_campo));
            valido = false;
        } else {
            this.edtName.setError(null);
        }

        if (cpf.isEmpty()) {
            this.edtName.setError(getString(R.string.msg_erro_campo));
            valido = false;
        } else if (cpf.length() < 14) {
            this.edtCpf.setError(getString(R.string.msg_erro_cpf_1));
            valido = false;
        } else {
            this.edtCpf.setError(null);
        }

        if (register.isEmpty()) {
            this.edtRegister.setError(getString(R.string.msg_erro_campo));
            valido = false;
        } else {
            this.edtRegister.setError(null);
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edtEmail.setError(getString(R.string.msg_erro_email));
            valido = false;
        }

        if (password.isEmpty()) {
            this.edtPassword.setError(getString(R.string.msg_erro_campo));
            valido = false;
        } else if (password.length() < 5) {
            this.edtPassword.setError(getString(R.string.msg_erro_senha));
            valido = false;
        } else {
            this.edtPassword.setError(null);
        }

        return valido;
    }
}
