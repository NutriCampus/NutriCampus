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


/*
Explicação para a supressão de warnings:
 - "squid:MaximumInheritanceDepth" = herança extendida em muitos niveis (mais que 5), permitido aqui já
 que refere-se a herança das classes das activities Android
 - "squid:S1172" = erro do sonarqube para os parametros "view" não utilizados
*/
@java.lang.SuppressWarnings({"squid:S1172", "squid:MaximumInheritanceDepth"})
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

        if (dadosComplAnimal.getEec() == 0)
            ((RadioButton) radioGroup.getChildAt(dadosComplAnimal.getEec())).setChecked(true);
        else
            ((RadioButton) radioGroup.getChildAt(dadosComplAnimal.getEec() - 1)).setChecked(true);

        btnSalvar.setText(getString(R.string.atualizar));
    }

    @Override
    public void salvarHistoricoAnimal(View v) {
        if (!validaDados()) {
            Toast.makeText(EditarDadosComplActivity.this, R.string.msg_erro_cadastro_geral, Toast.LENGTH_LONG).show();
            return;
        }

        DadosComplAnimal dadosComplementares = getDadosComplAnimal();
        dadosComplementares.setAnimal(dadosComplAnimal.getAnimal());
        dadosComplementares.setId(dadosComplAnimal.getId());

        RepositorioDadosComplAnimal repositorioDadosComplAnimal = new RepositorioDadosComplAnimal(EditarDadosComplActivity.this);
        boolean atualDadosCompl = repositorioDadosComplAnimal.atualizarDadosCompl(dadosComplementares);

        if (atualDadosCompl) {
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

        if (item.getItemId() == android.R.id.home) {
            Intent it = new Intent(EditarDadosComplActivity.this, ListaDadosComplActivity.class);
            it.putExtra(DadosAnimalFragment.EXTRA_ANIMAL, super.animal);
            startActivity(it);
            finish();
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        Intent it = new Intent(EditarDadosComplActivity.this, ListaDadosComplActivity.class);
        it.putExtra(DadosAnimalFragment.EXTRA_ANIMAL, super.animal);
        startActivity(it);
        finish();
    }
}
