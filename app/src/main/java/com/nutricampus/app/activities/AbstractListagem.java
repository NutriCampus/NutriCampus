package com.nutricampus.app.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.nutricampus.app.R;
import com.nutricampus.app.database.SharedPreferencesManager;
import com.nutricampus.app.entities.Animal;
import com.nutricampus.app.fragments.DadosAnimalFragment;
import com.nutricampus.app.utils.Conversor;

import java.util.Calendar;

import butterknife.BindView;

/**
 * Created by kellison on 01/08/17.
 */


/*
Explicação para a supressão de warnings:
 - "squid:MaximumInheritanceDepth" = herança extendida em muitos niveis (mais que 5), permitido aqui já
 que refere-se a herança das classes das activities Android
 - "squid:S1172" = erro do sonarqube para os parametros "view" não utilizados
*/
@java.lang.SuppressWarnings({"squid:S1172", "squid:MaximumInheritanceDepth"})
abstract class AbstractListagem extends AppCompatActivity {
    @BindView(R.id.text_quantidade_encontrados)
    TextView mensagemQuantidade;
    @BindView(R.id.linha)
    View linha;
    @BindView(R.id.spinner_meses)
    Spinner spinnerMeses;
    @BindView(R.id.spinner_anos)
    Spinner spinnerAnos;

    protected ListView listView;

    SharedPreferencesManager session;

    Class cadastro;

    protected int idAnimal;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new SharedPreferencesManager(getApplicationContext());
        session.checkLogin();

        if (this.getIntent() != null)
            this.idAnimal = ((Animal) getIntent().getSerializableExtra(DadosAnimalFragment.EXTRA_ANIMAL)).getId();

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_contexto_crud, menu);
    }

    @Override
    protected void onResume() {
        super.onResume();
        filtrarRegistros(new View(this));
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.menu_opc_cont_adicionar:
                Intent intent = new Intent(this, cadastro);
                startActivity(intent);
                return true;
            case R.id.menu_opc_cont_editar:
                if (info != null) {
                    startActivity(getIntent(listView.getItemAtPosition(info.position)));
                }
                return true;
            case R.id.menu_opc_cont_excluir:
                confirmarExcluir(listView.getItemAtPosition(info.position));
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    public void filtrarRegistros(View view) {

        int mes = Conversor.mesStringParaInt(String.valueOf(spinnerMeses.getSelectedItem()));
        int ano = Integer.parseInt(String.valueOf(spinnerAnos.getSelectedItem()));


        carregaListView(mes, ano);
    }

    protected void carregaListView() {
        Calendar cal = Calendar.getInstance();
        spinnerMeses.setSelection(cal.get(Calendar.MONTH));

        this.carregaListView(cal.get(Calendar.MONTH), cal.get(Calendar.YEAR));
    }

    protected void confirmarExcluir(final Object objeto) {
        new AlertDialog.Builder(this)
                .setMessage(getString(R.string.msg_excluir_confirmar) + " ?")
                .setCancelable(false)
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        excluirRegistro(objeto);
                    }
                })
                .setNegativeButton("Não", null)
                .show();
    }

    protected abstract void carregaListView(int mes, int ano);

    protected abstract Intent getIntent(Object objeto);

    protected abstract void excluirRegistro(Object objeto);

}
