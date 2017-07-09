package com.nutricampus.app.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.nutricampus.app.R;
import com.nutricampus.app.database.RepositorioProprietario;
import com.nutricampus.app.entities.Proprietario;
import com.nutricampus.app.model.Mascara;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CadastrarProprietarioActivity extends AppCompatActivity {

    @BindView(R.id.input_nome_proprietario) EditText inputNomeProprietario;
    @BindView(R.id.input_cpf_proprietario) EditText inputCpfProprietario;
    @BindView(R.id.input_email_proprietario) EditText inputEmailProprietario;
    @BindView(R.id.input_fone_proprietario) EditText inputFoneProprietario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_proprietario);

        ButterKnife.bind(this);

        inputCpfProprietario.addTextChangedListener(Mascara.insert(Mascara.CPF_MASK, inputCpfProprietario));
        inputFoneProprietario.addTextChangedListener(Mascara.insert(Mascara.CELULAR_MASK, inputFoneProprietario));

    }

    public void criarProprietario(View v) {
        if (!validaDados()) {
            Toast.makeText(CadastrarProprietarioActivity.this,R.string.msg_erro_cadastro_geral, Toast.LENGTH_LONG).show();
            return;
        }

        if (!Mascara.validarCpf(inputCpfProprietario.getText().toString())) {
            Toast.makeText(CadastrarProprietarioActivity.this, getString(R.string.msg_erro_cpf_2), Toast.LENGTH_LONG).show();
            return;
        }

        Proprietario proprietario = new Proprietario(
                inputCpfProprietario.getText().toString(),
                inputNomeProprietario.getText().toString(),
                inputEmailProprietario.getText().toString(),
                inputFoneProprietario.getText().toString());


        RepositorioProprietario repositorioProprietario = new RepositorioProprietario(getBaseContext());
        int idRetorno = repositorioProprietario.inserirProprietario(proprietario);

        if(idRetorno > -1) {
            proprietario.setId(idRetorno);

            //Caixa de Dialogo
            AlertDialog.Builder dialog = new AlertDialog.Builder(CadastrarProprietarioActivity.this);
            dialog.setTitle("Cadastro");
            dialog.setMessage(getString(
                    R.string.msg_sucesso, "Proprietário", proprietario.getNome()));
            dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    CadastrarProprietarioActivity.this.finish();
                }
            });
            dialog.show();

        } else {
            Toast.makeText(CadastrarProprietarioActivity.this, getString(R.string.msg_erro_cadastro_proprietario), Toast.LENGTH_LONG).show();
        }



    }

    private boolean validaDados(){
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
}
