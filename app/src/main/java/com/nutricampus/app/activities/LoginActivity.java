package com.nutricampus.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nutricampus.app.R;
import com.nutricampus.app.database.RepositorioAnimal;
import com.nutricampus.app.database.RepositorioCompostosAlimentares;
import com.nutricampus.app.database.RepositorioDadosComplAnimal;
import com.nutricampus.app.database.RepositorioGrupo;
import com.nutricampus.app.database.RepositorioPropriedade;
import com.nutricampus.app.database.RepositorioProprietario;
import com.nutricampus.app.database.RepositorioUsuario;
import com.nutricampus.app.database.SharedPreferencesManager;
import com.nutricampus.app.entities.Animal;
import com.nutricampus.app.entities.CompostosAlimentares;
import com.nutricampus.app.entities.DadosComplAnimal;
import com.nutricampus.app.entities.Grupo;
import com.nutricampus.app.entities.Propriedade;
import com.nutricampus.app.entities.Proprietario;
import com.nutricampus.app.entities.Usuario;

import java.util.Calendar;


/*
Explicação para a supressão de warnings:
 - "squid:MaximumInheritanceDepth" = herança extendida em muitos niveis (mais que 5), permitido aqui já
 que refere-se a herança das classes das activities Android
 - "squid:S1172" = erro do sonarqube para os parametros "view" não utilizados
*/
@java.lang.SuppressWarnings({"squid:S1172", "squid:MaximumInheritanceDepth"})
public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    private SharedPreferencesManager session;

    private EditText editTextUsuario;
    private EditText editTextSenha;
    private Button buttonEntrar;

    /**
     * Método para criar usuário automaticamente sem necessidade de cadastrar todas as vezes
     */
    private void criarUsuarioDefault() {
        String admin = "admin";

        RepositorioUsuario repo = new RepositorioUsuario(this);
        if (repo.buscarUsuario(admin, admin) == null) {
            repo.inserirUsuario(new Usuario(1, admin, "", admin, "admin@mail.com", admin));


            RepositorioProprietario REPproprietario = new RepositorioProprietario(this);
            RepositorioPropriedade REPpropriedade = new RepositorioPropriedade(this);
            RepositorioAnimal REPAnimal = new RepositorioAnimal(this);
            RepositorioCompostosAlimentares REPCompAliment = new RepositorioCompostosAlimentares(this);
            RepositorioDadosComplAnimal REPdados = new RepositorioDadosComplAnimal(this);
            RepositorioGrupo REPgrupo = new RepositorioGrupo(this);


            Proprietario proprietario1 = new Proprietario(1, "111.111.111-11", "Proprietario1", "email1", "1111");
            Proprietario proprietario2 = new Proprietario(2, "222.222.222-22", "Proprietario2", "email2", "2222");
            REPproprietario.inserirProprietario(proprietario1);
            REPproprietario.inserirProprietario(proprietario2);
            Propriedade propriedade1 = new Propriedade(1, "Propriedade1", "1111-1111", "Rua 111", "Bairo111", "Cep111", "Garanhuns", "Pernambuco", "11.111", 1, 1);
            Propriedade propriedade2 = new Propriedade(2, "Propriedade2", "2222-2222", "Rua 222", "Bairo222", "Cep222", "Caruaru", "Pernambuco", "22.222", 2, 1);
            REPpropriedade.inserirPropriedade(propriedade1);
            REPpropriedade.inserirPropriedade(propriedade2);
            Animal animal1 = new Animal(1, "vaca1", 1, Calendar.getInstance(), true, 1);
            Animal animal2 = new Animal(2, "vaca2", 1, Calendar.getInstance(), true, 1);
            Animal animal3 = new Animal(3, "vaca3", 1, Calendar.getInstance(), true, 1);
            Animal animal4 = new Animal(4, "vaca4", 1, Calendar.getInstance(), true, 1);
            Animal animal5 = new Animal(6, "_mimosa1", 2, Calendar.getInstance(), true, 1);
            Animal animal6 = new Animal(7, "_mimosa2", 2, Calendar.getInstance(), true, 1);
            Animal animal7 = new Animal(8, "_mimosa3", 2, Calendar.getInstance(), true, 1);
            Animal animal8 = new Animal(9, "_mimosa4", 2, Calendar.getInstance(), true, 1);
            REPAnimal.inserirAnimal(animal1);
            REPAnimal.inserirAnimal(animal2);
            REPAnimal.inserirAnimal(animal3);
            REPAnimal.inserirAnimal(animal4);
            REPAnimal.inserirAnimal(animal5);
            REPAnimal.inserirAnimal(animal6);
            REPAnimal.inserirAnimal(animal7);
            REPAnimal.inserirAnimal(animal8);
            DadosComplAnimal dadosComplAnimal1 = new DadosComplAnimal(1, Calendar.getInstance(), 1, 1000, 1, 10, 100, 4, 1);
            DadosComplAnimal dadosComplAnimal2 = new DadosComplAnimal(2, Calendar.getInstance(), 2, 2000, 1, 20, 200, 4, 1);
            DadosComplAnimal dadosComplAnimal3 = new DadosComplAnimal(3, Calendar.getInstance(), 3, 3000, 2, 30, 300, 4, 2);
            DadosComplAnimal dadosComplAnimal4 = new DadosComplAnimal(4, Calendar.getInstance(), 4, 4000, 2, 40, 400, 4, 2);
            DadosComplAnimal dadosComplAnimal5 = new DadosComplAnimal(5, Calendar.getInstance(), 5, 5000, 3, 50, 500, 4, 3);
            DadosComplAnimal dadosComplAnimal6 = new DadosComplAnimal(6, Calendar.getInstance(), 6, 6000, 3, 60, 600, 4, 3);
            DadosComplAnimal dadosComplAnimal7 = new DadosComplAnimal(7, Calendar.getInstance(), 7, 7000, 4, 70, 700, 4, 4);
            DadosComplAnimal dadosComplAnimal8 = new DadosComplAnimal(8, Calendar.getInstance(), 8, 8000, 4, 80, 800, 4, 4);
            REPdados.inserirDadosComplAnimal(dadosComplAnimal1);
            REPdados.inserirDadosComplAnimal(dadosComplAnimal2);
            REPdados.inserirDadosComplAnimal(dadosComplAnimal3);
            REPdados.inserirDadosComplAnimal(dadosComplAnimal4);
            REPdados.inserirDadosComplAnimal(dadosComplAnimal5);
            REPdados.inserirDadosComplAnimal(dadosComplAnimal6);
            REPdados.inserirDadosComplAnimal(dadosComplAnimal7);
            REPdados.inserirDadosComplAnimal(dadosComplAnimal8);
            CompostosAlimentares compostosAlimentares1 = new CompostosAlimentares(1, "tipo1", "Farelo Soja", 1, 1, 1, 1, 1, 46.0, 1, 1, "descricao1");
            CompostosAlimentares compostosAlimentares2 = new CompostosAlimentares(2, "tipo2", "Fubá", 1, 1, 1, 1, 1, 9.0, 1, 1, "descricao2");
            CompostosAlimentares compostosAlimentares3 = new CompostosAlimentares(3, "tipo3", "Farinha de Trigo", 1, 1, 1, 1, 1, 16.0, 1, 1, "descricao3");
            CompostosAlimentares compostosAlimentares4 = new CompostosAlimentares(4, "tipo4", "Cama de Frango", 1, 1, 1, 1, 1, 25.0, 1, 1, "descricao4");
            REPCompAliment.inserirCompostoAlimentar(compostosAlimentares1);
            REPCompAliment.inserirCompostoAlimentar(compostosAlimentares2);
            REPCompAliment.inserirCompostoAlimentar(compostosAlimentares3);
            REPCompAliment.inserirCompostoAlimentar(compostosAlimentares4);

            REPgrupo.inserirGrupo(new Grupo(1, "Pastando", "", 1));
            REPgrupo.inserirGrupo(new Grupo(2, "Lactação", "", 1));
            REPgrupo.inserirGrupo(new Grupo(3, "Cio", "", 1));
            REPgrupo.inserirGrupo(new Grupo(4, "Gestante", "", 1));
        }

    }

    private void init() {
        editTextSenha = (EditText) findViewById(R.id.input_senha);
        editTextUsuario = (EditText) findViewById(R.id.input_usuario);
        buttonEntrar = (Button) findViewById(R.id.btn_login);
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
        init();
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

        this.buttonEntrar.setEnabled(false);

        String usuario = this.editTextUsuario.getText().toString();
        String senha = this.editTextSenha.getText().toString();
        Usuario usuarioLogado = buscarUsuario(usuario, senha);

        if (usuarioLogado != null) {
            session.createLoginSession(
                    usuarioLogado.getId(), usuarioLogado.getNome(), usuarioLogado.getEmail(), usuarioLogado.getSenha(), usuarioLogado.getCrmv());
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            Toast.makeText(LoginActivity.this, getString(R.string.msg_bem_vindo), Toast.LENGTH_LONG).show();
            this.finish();
        } else {
            falhaLogin(getString(R.string.msg_dados_login_invalido));
            buttonEntrar.setEnabled(true);
        }

    }

    private Usuario buscarUsuario(String usuarioValor, String senhaValor) {
        RepositorioUsuario repositorioUsuario = new RepositorioUsuario(getBaseContext());
        return repositorioUsuario.buscarUsuario(usuarioValor, senhaValor);

    }

    private void falhaLogin(String mensagem) {
        String msg = mensagem.isEmpty() ? "" : (", " + mensagem);
        Toast.makeText(LoginActivity.this, getString(R.string.msg_falha_login) + msg, Toast.LENGTH_LONG).show();

        buttonEntrar.setEnabled(true);
    }

    private boolean validaDados() {
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