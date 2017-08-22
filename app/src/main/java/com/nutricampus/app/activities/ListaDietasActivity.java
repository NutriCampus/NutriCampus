package com.nutricampus.app.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nutricampus.app.R;
import com.nutricampus.app.adapters.ListaDietaAdapter;
import com.nutricampus.app.database.RepositorioDieta;
import com.nutricampus.app.database.SharedPreferencesManager;
import com.nutricampus.app.entities.Dieta;

import java.util.List;

/**
 * Created by Paulo Mateus on 20/08/17.
 * For project NutriCampus.
 * Contact: <paulomatew@gmail.com>
 */
@SuppressWarnings({"squid:S1172", "squid:MaximumInheritanceDepth"})
public class ListaDietasActivity extends AbstractListComPesquisa {

    private ListView listDietas;
    private TextView mensagemQuantidade;
    private View linha;
    private List<Dieta> dietasDB;

    private void init() {
        listDietas = (ListView) findViewById(R.id.listaDietas);
        mensagemQuantidade = (TextView) findViewById(R.id.text_quantidade_encontrados);
        linha = findViewById(R.id.linha);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new SharedPreferencesManager(getApplicationContext()).checkLogin();

        setContentView(R.layout.activity_lista_dietas);

        init();
        listDietas.setEmptyView(findViewById(android.R.id.empty));

        registerForContextMenu(listDietas);
        carregarListView();

        listDietas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listDietas.showContextMenuForChild(view);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.btn_add_dieta);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListaDietasActivity.this, CadastrarDietaActivity.class);
                startActivity(intent);

            }
        });
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_contexto_dieta, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Dieta dieta;
        switch (item.getItemId()) {
            case R.id.menu_opc_cont_visualizar_dieta:
                if (info != null)
                    /**/
                    abreTelaVisualizar(info.position);
                    /**/
                return true;
            case R.id.menu_opc_cont_editar:
                if (info != null)
                    abreTelaEditar(info.position);
                return true;
            case R.id.menu_opc_cont_excluir:
                dieta = (Dieta) listDietas.getItemAtPosition(info.position);
                confirmarExcluir(dieta);
                return true;
            case R.id.menu_opc_cont_Relatorio:
                dieta = (Dieta) listDietas.getItemAtPosition(info.position);
                gerarRelatorio(dieta);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarListView();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        mSearchAction = menu.findItem(R.id.action_search);
        return super.onPrepareOptionsMenu(menu);
    }

    private void confirmarExcluir(final Dieta dieta) {
        new AlertDialog.Builder(this)
                .setMessage(getString(R.string.msg_excluir_confirmar_dieta, "\"" + dieta.identificador + "\""))
                .setCancelable(false)
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        RepositorioDieta repositorioDieta = new RepositorioDieta(ListaDietasActivity.this);

                        repositorioDieta.removerDieta(dieta);
                        Toast.makeText(ListaDietasActivity.this,
                                getString(R.string.msg_excluir_dieta_sucesso), Toast.LENGTH_LONG).show();
                        carregarListView();

                    }
                })
                .setNegativeButton("Não", null)
                .show();
    }

    public void carregarListView() {
        carregarListView(new ResultadoPesquisa(0, ""));
    }

    @Override
    protected void carregarListView(ResultadoPesquisa resultadoPesquisa) {
        dietasDB = this.buscarDieta(resultadoPesquisa.getString());
        ListaDietaAdapter adapter = new ListaDietaAdapter(dietasDB, this);

        listDietas.setAdapter(adapter);

        if (dietasDB.isEmpty()) {
            linha.setVisibility(View.GONE);
            mensagemQuantidade.setVisibility(View.GONE);
        } else {
            linha.setVisibility(View.VISIBLE);
            mensagemQuantidade.setVisibility(View.VISIBLE);
            mensagemQuantidade.setText(getResources().getQuantityString(
                    R.plurals.msg_registros_encontrados,
                    adapter.getCount(),
                    adapter.getCount()));
        }

    }

    public List<Dieta> buscarDieta(String nome) {
        RepositorioDieta repositorioDieta = new RepositorioDieta(this);
        return repositorioDieta.buscarTodosDietas(nome);
    }

    private Intent getIntent(Dieta dieta, boolean isEdit) {
        Intent intent;
        if (isEdit) {
            intent = new Intent(this, EditarDietaActivity.class);
        } else {
            intent = new Intent(this, VisualizarDietaActivity.class);
        }

        intent.putExtra("id", dieta.id);
        return intent;
    }

    private void abreTelaEditar(int posicao) {
        Dieta item = (Dieta) listDietas.getItemAtPosition(posicao);
        startActivity(getIntent(item, true));
    }


    private void abreTelaVisualizar(int position) {
        Dieta item = (Dieta) listDietas.getItemAtPosition(position);
        startActivity(getIntent(item, false));
    }

    private void gerarRelatorio(Dieta dieta) {
        Intent intent = new Intent(ListaDietasActivity.this, GerarPDFActivity.class);
        GerarPDFActivity.getDieta(dieta);

        startActivity(intent);
    }
}
