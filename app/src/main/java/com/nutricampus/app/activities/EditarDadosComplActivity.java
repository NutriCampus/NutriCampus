package com.nutricampus.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.nutricampus.app.R;
import com.nutricampus.app.database.RepositorioDadosComplAnimal;
import com.nutricampus.app.entities.Animal;
import com.nutricampus.app.entities.DadosComplAnimal;
import com.nutricampus.app.fragments.DadosAnimalFragment;
import com.nutricampus.app.utils.Conversor;

public class EditarDadosComplActivity extends CadastrarNovoDadoComplActivity {

    private DadosComplAnimal dadosComplAnimal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        super.animal = (Animal) getIntent().getSerializableExtra(DadosAnimalFragment.EXTRA_ANIMAL);
        dadosComplAnimal = (DadosComplAnimal) getIntent().getSerializableExtra(ListaDadosComplActivity.EXTRA_DADOS_COMPL);

        preencherCampos();
    }

    private void preencherCampos() {

        inputPesoVivo.setText(String.valueOf(dadosComplAnimal.getPesoVivo()));
        inputData.setText(Conversor.dataFormatada(dadosComplAnimal.getData()));
        inputCaminhadaHorizontal.setText(String.valueOf(dadosComplAnimal.getCaminadaHorizontal()));
        inputCaminhadaVertical.setText(String.valueOf(dadosComplAnimal.getCaminhadaVertical()));
        inputSemanaLactacao.setText(String.valueOf(dadosComplAnimal.getSemanaLactacao()));
        ckbPastando.setChecked(dadosComplAnimal.isPastando());
        ckbLactacao.setChecked(dadosComplAnimal.isLactacao());
        ckbGestante.setChecked(dadosComplAnimal.isGestante());
        ckbCio.setChecked(dadosComplAnimal.isCio());
        if(dadosComplAnimal.getEEC() == 0)
            ((RadioButton) radioGroup.getChildAt(dadosComplAnimal.getEEC())).setChecked(true);
        else
            ((RadioButton) radioGroup.getChildAt(dadosComplAnimal.getEEC() - 1)).setChecked(true);

        btnSalvar.setText(getString(R.string.campo_corrigir));
    }

    @Override
    public void salvarHistoricoAnimal(View v) {
        if (!validaDados()) {
            Toast.makeText(EditarDadosComplActivity.this, R.string.msg_erro_cadastro_geral, Toast.LENGTH_LONG).show();
            return;
        }

        float caminhadaHorizontal = inputCaminhadaHorizontal.getText().toString().equals("") ? 0.0f :
                Float.parseFloat(inputCaminhadaHorizontal.getText().toString());
        float caminhadaVertical = inputCaminhadaVertical.getText().toString().equals("") ? 0.0f :
                Float.parseFloat(inputCaminhadaVertical.getText().toString());

        //Atribuir o valor de EEC
        int idRadioButton = radioGroup.getCheckedRadioButtonId();
        RadioButton rb = (RadioButton) radioGroup.findViewById(idRadioButton);
        int eec;
        if(rb == null)
            eec = 0;
        else
            eec = Integer.parseInt(String.valueOf(rb.getText()));

        dadosComplAnimal.setData(data);
        dadosComplAnimal.setPesoVivo(Float.parseFloat(inputPesoVivo.getText().toString()));
        dadosComplAnimal.setEEC(eec);
        dadosComplAnimal.setCaminadaHorizontal(caminhadaHorizontal);
        dadosComplAnimal.setCaminhadaVertical(caminhadaVertical);
        dadosComplAnimal.setSemanaLactacao(Integer.parseInt(inputSemanaLactacao.getText().toString()));
        dadosComplAnimal.setPastando(ckbPastando.isChecked());
        dadosComplAnimal.setLactacao(ckbLactacao.isChecked());
        dadosComplAnimal.setGestante(ckbGestante.isChecked());
        dadosComplAnimal.setCio(ckbCio.isChecked());

        //dadosComplAnimal.setAnimal(animal.getId());

        RepositorioDadosComplAnimal repositorioDadosComplAnimal = new RepositorioDadosComplAnimal(EditarDadosComplActivity.this);
        boolean atualDadosCompl = repositorioDadosComplAnimal.atualizarDadosCompl(dadosComplAnimal);

        if(atualDadosCompl) {
            Toast.makeText(EditarDadosComplActivity.this, R.string.msg_sucesso_atualizar_registro, Toast.LENGTH_LONG).show();
            Intent it = new Intent(EditarDadosComplActivity.this, ListaDadosComplActivity.class);
            it.putExtra(ListaDadosComplActivity.EXTRA_ANIMAL, animal);
            startActivity(it);
        } else {
            Toast.makeText(EditarDadosComplActivity.this, R.string.msg_erro_cadastro, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                Intent it = new Intent(EditarDadosComplActivity.this, ListaDadosComplActivity.class);
                it.putExtra(DadosAnimalFragment.EXTRA_ANIMAL, super.animal);
                startActivity(it);
                finish();
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed(){
        Intent it = new Intent(EditarDadosComplActivity.this, ListaDadosComplActivity.class);
        it.putExtra(DadosAnimalFragment.EXTRA_ANIMAL, super.animal);
        startActivity(it);
        finish();
    }
}
