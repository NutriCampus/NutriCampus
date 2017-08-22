package com.nutricampus.app.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.nutricampus.app.R;
import com.nutricampus.app.database.RepositorioDadosComplAnimal;
import com.nutricampus.app.database.RepositorioGrupo;
import com.nutricampus.app.database.SharedPreferencesManager;
import com.nutricampus.app.entities.Animal;
import com.nutricampus.app.entities.DadosComplAnimal;
import com.nutricampus.app.entities.Grupo;
import com.nutricampus.app.fragments.DadosAnimalFragment;
import com.nutricampus.app.utils.Conversor;

import java.util.ArrayList;


/*
Explicação para a supressão de warnings:
 - "squid:MaximumInheritanceDepth" = herança extendida em muitos niveis (mais que 5), permitido aqui já
 que refere-se a herança das classes das activities Android
 - "squid:S1172" = erro do sonarqube para os parametros "view" não utilizados
*/
@java.lang.SuppressWarnings({"squid:S1172", "squid:MaximumInheritanceDepth"})
public class EditarDadosComplActivity extends CadastrarNovoDadoComplActivity {

    private DadosComplAnimal dadosComplAnimal;
    private RepositorioGrupo repositorioGrupo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        repositorioGrupo = new RepositorioGrupo(EditarDadosComplActivity.this);

        super.animal = (Animal) getIntent().getSerializableExtra(DadosAnimalFragment.EXTRA_ANIMAL);
        dadosComplAnimal = (DadosComplAnimal) getIntent().getSerializableExtra(ListaDadosComplActivity.EXTRA_DADOS_COMPL);
        Grupo grupo = repositorioGrupo.buscarGrupo(dadosComplAnimal.getIdGrupo());

        if (grupo != null)
            grupoSelecionado = grupo.getIdentificador();

        preencherCampos();
    }

    private void preencherCampos() {

        inputPesoVivo.setText(String.valueOf(dadosComplAnimal.getPesoVivo()));
        inputData.setText(Conversor.dataFormatada(dadosComplAnimal.getData()));
        inputCaminhadaHorizontal.setText(String.valueOf(dadosComplAnimal.getCaminadaHorizontal()));
        inputCaminhadaVertical.setText(String.valueOf(dadosComplAnimal.getCaminhadaVertical()));
        inputSemanaLactacao.setText(String.valueOf(dadosComplAnimal.getSemanaLactacao()));


        txtGrupo.setText("Grupo selecionado: " + repositorioGrupo.buscarGrupo(
                dadosComplAnimal.getIdGrupo()).getIdentificador());


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

        int idGrupo = 1;
        int idUsuario = Integer.parseInt(new SharedPreferencesManager(EditarDadosComplActivity.this).getIdUsuario());
        Grupo grupo = repositorioGrupo.buscarGrupo(grupoSelecionado);

        if (grupo == null && (repositorioGrupo.buscarPorUsuario(idUsuario).isEmpty())) {
            Toast.makeText(EditarDadosComplActivity.this, getString(R.string.msg_erro_grupo), Toast.LENGTH_LONG).show();
            return;
        } else {
            if(grupo != null)
                idGrupo = grupo.getId();
        }

        dadosComplementares.setIdGrupo(
                ("".equals(grupoSelecionado) ? 1 : idGrupo)
        );

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

    @Override
    public void escolherGrupo() {

        ArrayList<Grupo> listGrupos= new ArrayList<>();
        listGrupos.clear();

        //Adicionar grupos vindos do repositorio
        int idUsuario = Integer.parseInt(new SharedPreferencesManager(EditarDadosComplActivity.this).getIdUsuario());

        listGrupos.addAll((ArrayList)repositorioGrupo.buscarPorUsuario(idUsuario));

        final String[] grupos = new String[listGrupos.size()];

        for(int i = 0; i < grupos.length; i++) {
            grupos[i] = listGrupos.get(i).getIdentificador();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(EditarDadosComplActivity.this);
        builder.setTitle("Selecione um grupo");

        int grupoChecked;
        if(animal == null)
            grupoChecked = -1;
        else {
            grupoChecked = dadosComplAnimal.getIdGrupo();
        }

        builder.setSingleChoiceItems(grupos, grupoChecked - 1, new DialogInterface
                .OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                Toast.makeText(EditarDadosComplActivity.this,
                        grupos[item], Toast.LENGTH_SHORT).show();

                grupoSelecionado = grupos[item];
            }
        });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                txtGrupo.setText("Grupo selecionado: " + grupoSelecionado);
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }
}
