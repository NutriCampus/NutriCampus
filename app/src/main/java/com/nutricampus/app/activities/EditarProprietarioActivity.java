package com.nutricampus.app.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Toast;

import com.nutricampus.app.R;
import com.nutricampus.app.database.RepositorioProprietario;
import com.nutricampus.app.entities.Proprietario;
import com.nutricampus.app.utils.Mascara;

import butterknife.ButterKnife;

public class EditarProprietarioActivity extends CadastrarProprietarioActivity {

    private Proprietario proprietario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_proprietario);

        ButterKnife.bind(this);

        Intent it = getIntent();
        proprietario = (Proprietario) it.getSerializableExtra("proprietario");

        inputFoneProprietario.addTextChangedListener(Mascara.insert(Mascara.CELULAR_MASK, inputFoneProprietario));
        inicializarCampos();
    }

    public void inicializarCampos() {

        inputNomeProprietario.setText(proprietario.getNome());
        inputCpfProprietario.setText(proprietario.getCpf());
        inputEmailProprietario.setText(proprietario.getEmail());
        inputFoneProprietario.setText(proprietario.getTelefone());
    }

    public void atualizarProprietario(View v) {

        proprietario.setNome(inputNomeProprietario.getText().toString());
        proprietario.setEmail(inputEmailProprietario.getText().toString());
        proprietario.setTelefone(inputFoneProprietario.getText().toString());

        RepositorioProprietario repositorioProprietario = new RepositorioProprietario(getBaseContext());
        boolean f = repositorioProprietario.atualizarProprietario(proprietario);

        if (f) {
            //Caixa de Dialogo
            AlertDialog.Builder dialog = new AlertDialog.Builder(EditarProprietarioActivity.this);
            dialog.setTitle("Atualização");
            dialog.setMessage(getString(
                    R.string.msg_sucesso_atualizar, "Proprietário", proprietario.getNome()));
            dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    EditarProprietarioActivity.this.finish();
                }
            });
            dialog.show();
        } else {
            Toast.makeText(EditarProprietarioActivity.this, getString(R.string.msg_erro_atualizar_proprietario),
                    Toast.LENGTH_LONG).show();
        }
    }
}
