package com.nutricampus.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nutricampus.app.R;
import com.nutricampus.app.database.RepositorioProprietario;
import com.nutricampus.app.entities.Proprietario;
import com.nutricampus.app.fragments.DadosAnimalFragment;
import com.nutricampus.app.utils.Mascara;
import com.nutricampus.app.utils.ValidaFormulario;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Felipe on 01/08/2017.
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
public class CadastrarProprietarioActivity extends AppCompatActivity {

    public static final String EXTRA_PROPRIETARIO = "proprietario";


    @BindView(R.id.input_nome_proprietario) EditText inputNomeProprietario;
    @BindView(R.id.input_cpf_proprietario) EditText inputCpfProprietario;
    @BindView(R.id.input_email_proprietario) EditText inputEmailProprietario;
    @BindView(R.id.input_fone_proprietario) EditText inputFoneProprietario;
    @BindView(R.id.btn_salvar_cadastro) Button btnSalvar;

    private int voltarProprietarios;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_proprietario);

        ButterKnife.bind(this);

        inputCpfProprietario.addTextChangedListener(Mascara.insert(Mascara.CPF_MASK, inputCpfProprietario));
        inputFoneProprietario.addTextChangedListener(Mascara.insert(Mascara.CELULAR_MASK, inputFoneProprietario));

        voltarProprietarios = getIntent().getIntExtra(ListaProprietariosActivity.EXTRA_VOLTAR_PROPRIETARIOS, -1);

    }

    public void salvar(View v) {
        if (!validaDados()) {
            Toast.makeText(CadastrarProprietarioActivity.this, R.string.msg_erro_cadastro_geral, Toast.LENGTH_LONG).show();
            return;
        }

        if (!ValidaFormulario.validarCpf(inputCpfProprietario.getText().toString())) {
            Toast.makeText(CadastrarProprietarioActivity.this, getString(R.string.msg_erro_cpf_2), Toast.LENGTH_LONG).show();
            return;
        }

        final Proprietario proprietario = new Proprietario(
                inputCpfProprietario.getText().toString(),
                inputNomeProprietario.getText().toString(),
                inputEmailProprietario.getText().toString(),
                inputFoneProprietario.getText().toString());


        RepositorioProprietario repositorioProprietario = new RepositorioProprietario(getBaseContext());
        int idRetorno = repositorioProprietario.inserirProprietario(proprietario);

        if (idRetorno > -1) {
            proprietario.setId(idRetorno);

            Toast.makeText(CadastrarProprietarioActivity.this,
                    getString(R.string.msg_sucesso_cadastro, proprietario.getNome()),
                    Toast.LENGTH_LONG).show();


            Intent intent;
            if(voltarProprietarios == 1) {
                intent = new Intent(CadastrarProprietarioActivity.this, ListaProprietariosActivity.class);
            } else {
                intent = new Intent(CadastrarProprietarioActivity.this, CadastrarPropriedadeActivity.class);
                intent.putExtra(EXTRA_PROPRIETARIO, proprietario);
                intent.putExtra(DadosAnimalFragment.EXTRA_CAD_ANIMAL,
                        getIntent().getIntExtra(DadosAnimalFragment.EXTRA_CAD_ANIMAL, -1));
            }

            startActivity(intent);
            CadastrarProprietarioActivity.this.finish();

        } else {
            Toast.makeText(CadastrarProprietarioActivity.this, getString(R.string.msg_erro_cadastro_proprietario), Toast.LENGTH_LONG).show();
        }
    }


    protected boolean validaDados(){

        boolean valido = true;

        if (inputNomeProprietario.getText().toString().isEmpty()) {
            inputNomeProprietario.setError(getString(R.string.msg_erro_campo));
            valido = false;
        } else {
            inputNomeProprietario.setError(null);
        }

        if (inputCpfProprietario.getText().toString().isEmpty()) {
            this.inputCpfProprietario.setError(getString(R.string.msg_erro_campo));
            valido = false;
        } else if (inputCpfProprietario.getText().toString().length() < 14) {
            this.inputCpfProprietario.setError(getString(R.string.msg_erro_cpf_1));
            valido = false;
        } else {
            this.inputCpfProprietario.setError(null);
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(inputEmailProprietario.getText().toString()).matches()) {
            inputEmailProprietario.setError(getString(R.string.msg_erro_email));
            valido = false;
        }

        if (inputFoneProprietario.getText().toString().isEmpty()) {
            inputFoneProprietario.setError(getString(R.string.msg_erro_campo));
            valido = false;
        } else if (inputFoneProprietario.getText().toString().length() < 14) {
            inputFoneProprietario.setError(getString(R.string.msg_erro_telefone_incompleto));
            valido = false;
        } else {
            inputFoneProprietario.setError(null);
        }
        return valido;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home)
            voltarActivity();

        return true;
    }

    @Override

    public void onBackPressed(){
        voltarActivity();
    }

    private void voltarActivity() {
        Intent it;

        if(voltarProprietarios == 1)
            it = new Intent(CadastrarProprietarioActivity.this, ListaProprietariosActivity.class);
        else
            it = new Intent(CadastrarProprietarioActivity.this, CadastrarPropriedadeActivity.class);

        startActivity(it);
        finish();
    }
}
