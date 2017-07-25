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
import com.nutricampus.app.database.RepositorioAnimal;
import com.nutricampus.app.database.RepositorioDadosComplAnimal;
import com.nutricampus.app.database.RepositorioPropriedade;
import com.nutricampus.app.database.RepositorioProprietario;
import com.nutricampus.app.database.RepositorioUsuario;
import com.nutricampus.app.database.SharedPreferencesManager;
import com.nutricampus.app.entities.Animal;
import com.nutricampus.app.entities.DadosComplAnimal;
import com.nutricampus.app.entities.Propriedade;
import com.nutricampus.app.entities.Proprietario;
import com.nutricampus.app.entities.Usuario;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

@java.lang.SuppressWarnings("squid:S1172") // Ignora o erro do sonarqube para os parametros "view"
public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    SharedPreferencesManager session;

    // Pega a referência para as views

    @BindView(R.id.input_usuario)
    EditText editTextUsuario;
    @BindView(R.id.input_senha)
    EditText editTextSenha;
    @BindView(R.id.btn_login)
    Button buttonEntrar;
    @BindView(R.id.link_cadastro)
    TextView linkCadastro;
    @BindView(R.id.link_esqueceu_senha)
    TextView linkEsqueceuSeha;


    /**
     * Método para criar usuário automaticamente sem necessidade de cadastrar todas as vezes
     */
    private void criarUsuarioDefault() {
        String admin = "admin";
        String nome1 = "nomeAdmin proprietario", nome2 = "nomeAdmin proprietario";
        String email1 = "email@proprietario1.com", email2 = "email@proprietario2.com";
        String cpf1 = "000.000.000-000", cpf2 = "999.999.999-99";
        String prop1 = "propriedadeAdminUM", prop2 = "propriedadeAdminDOIS";
        String animal1 = "animalAdmin UM", animal2 = "animalAdmin DOIS";
        String tel1 = "(87) 00000 0000", tel2 = "(87) 99999 9999";
        String rua1 = "rua1", rua2 = "rua2";
        String bairro1 = "bairro1", bairro2 = "bairro2";
        String cep1 = "00000-000", cep2 = "99999-999";

        RepositorioUsuario repo = new RepositorioUsuario(this);
        if (repo.buscarUsuario(admin, admin) == null) {
            repo.inserirUsuario(new Usuario(1, admin, "", admin, "admin@mail.com", admin));
        }
        RepositorioProprietario repositorioProprietario = new RepositorioProprietario(this);
        if (repositorioProprietario.buscarProprietario(cpf1) == null) {
            repositorioProprietario.inserirProprietario(new Proprietario(1, cpf1, nome1, email1, tel1));
            repositorioProprietario.inserirProprietario(new Proprietario(2, cpf2, nome2, email2, tel2));
        }
        RepositorioPropriedade repositorioPropriedade = new RepositorioPropriedade(this);
        if (repositorioPropriedade.buscarPropriedade(prop1) == null) {
            repositorioPropriedade.inserirPropriedade(new Propriedade(1, prop1, tel1, rua1, bairro1, cep1, "Garanhuns", "Pernambuco", "000", 1, 1));
            repositorioPropriedade.inserirPropriedade(new Propriedade(2, prop2, tel2, rua2, bairro2, cep2, "Caruaru", "Pernambuco", "999", 2, 1));
        }
        RepositorioAnimal repoAnimal = new RepositorioAnimal(this);
        if (repoAnimal.buscarAnimal(animal1, 1) == null) {
            repoAnimal.inserirAnimal(new Animal(1, animal1, 1, Calendar.getInstance(), true));
            repoAnimal.inserirAnimal(new Animal(2, animal2, 2, Calendar.getInstance(), true));

            RepositorioDadosComplAnimal repositorioDadosComplAnimal = new RepositorioDadosComplAnimal(this);
            repositorioDadosComplAnimal.inserirDadosComplAnimal(new DadosComplAnimal(
                    Calendar.getInstance(), 1, 100, 150, 50, 60, 5, true, true, true, true
            ));
            repositorioDadosComplAnimal.inserirDadosComplAnimal(new DadosComplAnimal(
                    Calendar.getInstance(), 2, 100, 150, 50, 60, 5, true, true, true, true
            ));
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        criarUsuarioDefault();

        session = new SharedPreferencesManager(this);

        if (session.isLoggedIn()) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            Toast.makeText(LoginActivity.this, getString(R.string.msg_bem_vindo), Toast.LENGTH_LONG).show();
            this.finish();
        }

        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    public void cadastroOnClick(View view) {
        Intent intent = new Intent(this, CadastrarUsuarioActivity.class);

        startActivity(intent);
    }

    public void recuperarSenhaOnClick(View view) {
        Intent intent = new Intent(this, RecuperarSenhaActivity.class);
        startActivity(intent);
    }

    public void entrarOnClick(View view) {
        Log.d(TAG, "Login");

        if (!validaDados()) {
            falhaLogin("");
            return;
        }

        buttonEntrar.setEnabled(false);

        String usuario = this.editTextUsuario.getText().toString();
        String senha = this.editTextSenha.getText().toString();
        Usuario usuarioLogado = buscarUsuario(usuario, senha);

        if (usuarioLogado != null) {
            session.createLoginSession(usuarioLogado.getId(), usuarioLogado.getNome(), usuarioLogado.getEmail(), usuarioLogado.getSenha());
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            Toast.makeText(LoginActivity.this, getString(R.string.msg_bem_vindo), Toast.LENGTH_LONG).show();
            this.finish();
        } else {
            falhaLogin(getString(R.string.msg_dados_login_invalido));
            buttonEntrar.setEnabled(true);
        }

    }

    public Usuario buscarUsuario(String usuarioValor, String senhaValor) {
        RepositorioUsuario repositorioUsuario = new RepositorioUsuario(getBaseContext());

        return repositorioUsuario.buscarUsuario(usuarioValor, senhaValor);
    }

    public void falhaLogin(String mensagem) {
        String msg = mensagem.isEmpty() ? "" : (", " + mensagem);
        Toast.makeText(LoginActivity.this, getString(R.string.msg_falha_login) + msg, Toast.LENGTH_LONG).show();

        buttonEntrar.setEnabled(true);
    }

    public boolean validaDados() {
        boolean valido = true;

        String usuario = this.editTextUsuario.getText().toString();
        String password = this.editTextSenha.getText().toString();

        if (usuario.isEmpty()) {
            this.editTextUsuario.setError(getString(R.string.msg_erro_campo));
            valido = false;
        } else if (usuario.length() < 4) {
            this.editTextUsuario.setError(getString(R.string.msg_erro_crz));
            valido = false;
        } else {
            this.editTextUsuario.setError(null);
        }

        if (password.isEmpty()) {
            this.editTextSenha.setError(getString(R.string.msg_erro_campo));
            valido = false;
        } else if (password.length() < 5) {
            this.editTextSenha.setError(getString(R.string.msg_erro_senha));
            valido = false;
        } else {
            this.editTextSenha.setError(null);
        }

        return valido;
    }


}