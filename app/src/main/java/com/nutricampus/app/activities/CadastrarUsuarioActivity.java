package com.nutricampus.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.nutricampus.app.R;
import com.nutricampus.app.database.RepositorioUsuario;
import com.nutricampus.app.entities.Usuario;
import com.nutricampus.app.utils.Mascara;
import com.nutricampus.app.utils.ValidaFormulario;

/**
 * Created by Felipe on 23/06/2017.
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
public class CadastrarUsuarioActivity extends AppCompatActivity {

    private EditText edtNome;
    private EditText edtCpf;
    private EditText edtRegistro;
    private EditText edtEmail;
    private EditText edtSenha;

    private String nome;
    private String cpf;
    private String registro;
    private String email;
    private String senha;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_usuario);

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
        if (!validarDados()) {
            Toast.makeText(CadastrarUsuarioActivity.this, "Campos inválidos", Toast.LENGTH_LONG).show();
            return;
        }

        if (!ValidaFormulario.validarCpf(cpf)) {
            Toast.makeText(CadastrarUsuarioActivity.this, getString(R.string.msg_erro_cpf_2), Toast.LENGTH_LONG).show();
            return;
        }

        Usuario usuario = new Usuario(registro, cpf, nome, email, senha);

        RepositorioUsuario repositorioUsuario = new RepositorioUsuario(getBaseContext());
        int idUsuario = repositorioUsuario.inserirUsuario(usuario);


        if (idUsuario > -1) {
            usuario.setId(idUsuario);
            Toast.makeText(CadastrarUsuarioActivity.this,
                    getString(R.string.msg_sucesso_cadastro, usuario.getNome()),
                    Toast.LENGTH_LONG).show();

            CadastrarUsuarioActivity.this.finish();

        } else {
            Toast.makeText(CadastrarUsuarioActivity.this, getString(R.string.msg_erro_cadastro_usuario), Toast.LENGTH_SHORT).show();
        }

    }


    public boolean validarDados() {

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
        } else if (registro.length() < 5) {
            this.edtRegistro.setError(getString(R.string.msg_erro_CRZ));
            valido = false;
        } else {
            this.edtRegistro.setError(null);
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
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
