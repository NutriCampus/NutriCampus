package com.nutricampus.app.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
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
import com.nutricampus.app.adapters.ListaCompostosAlimentaresAdapter;
import com.nutricampus.app.database.RepositorioCompostosAlimentares;
import com.nutricampus.app.database.SharedPreferencesManager;
import com.nutricampus.app.entities.CompostosAlimentares;

import java.util.List;

@java.lang.SuppressWarnings({"squid:S1172", "squid:MaximumInheritanceDepth"})
public class ListaCompostosAlimentaresActivity extends AbstractListComPesquisa {

    private ListView listCompostosAlimentares;
    private TextView mensagemQuantidade;
    private View linha;

    private void init() {
        listCompostosAlimentares = (ListView) findViewById(R.id.listaCompostosAlimentares);
        mensagemQuantidade = (TextView) findViewById(R.id.text_quantidade_encontrados);
        linha = findViewById(R.id.linha);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new SharedPreferencesManager(getApplicationContext()).checkLogin();

        setContentView(R.layout.activity_lista_compostos_alimentares);

        init();
        listCompostosAlimentares.setEmptyView(findViewById(android.R.id.empty));

        registerForContextMenu(listCompostosAlimentares);
        carregarListView();

        listCompostosAlimentares.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listCompostosAlimentares.showContextMenuForChild(view);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.btn_add_composto_alimentar);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListaCompostosAlimentaresActivity.this, CadastrarCompostosAlimentaresActivity.class);
                startActivity(intent);

            }
        });
    }



    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_contexto_composto, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.menu_opc_cont_visualizar_composto:
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
                CompostosAlimentares compostosAlimentares = (CompostosAlimentares) listCompostosAlimentares.getItemAtPosition(info.position);
                confirmarExcluir(compostosAlimentares);
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

    private void confirmarExcluir(final CompostosAlimentares compostosAlimentares) {
        new AlertDialog.Builder(this)
                .setMessage(getString(R.string.msg_excluir_confirmar_composto_alimentar, "\"" + compostosAlimentares.getIdentificador() + "\""))
                .setCancelable(false)
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        RepositorioCompostosAlimentares repositorioCompostosAlimentares = new RepositorioCompostosAlimentares(ListaCompostosAlimentaresActivity.this);

                        int result = repositorioCompostosAlimentares.removerCompostoAlimentar(compostosAlimentares);
                        if (result > 0) {
                            Toast.makeText(ListaCompostosAlimentaresActivity.this,
                                    getString(R.string.msg_excluir_composto_sucesso), Toast.LENGTH_LONG).show();
                            carregarListView();
                        } else {
                            Toast.makeText(ListaCompostosAlimentaresActivity.this,
                                    getString(R.string.msg_excluir_composto_falha), Toast.LENGTH_LONG).show();
                        }
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
        List<CompostosAlimentares> lista = this.buscarComposto(resultadoPesquisa.getString());
        ListaCompostosAlimentaresAdapter adapter = new ListaCompostosAlimentaresAdapter(lista, this);

        listCompostosAlimentares.setAdapter(adapter);

        if (lista.isEmpty()) {
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

    public List<CompostosAlimentares> buscarComposto(String nome) {
        RepositorioCompostosAlimentares repositorioPropriedade = new RepositorioCompostosAlimentares(this);
        return repositorioPropriedade.buscarTodosCompostos(nome);
    }

    private Intent getIntent(CompostosAlimentares compostosAlimentares, boolean isView) {
        Intent intent;
        if (isView) {
            intent = new Intent(this, VisualizarCompostoActivity.class);
        } else {
            intent = new Intent(this, EditarCompostoActivity.class);
        }

        intent.putExtra("id", compostosAlimentares.getId());
        intent.putExtra("tipo", compostosAlimentares.getTipo());
        intent.putExtra("identificador", compostosAlimentares.getIdentificador());
        intent.putExtra("ms", compostosAlimentares.getMs());
        intent.putExtra("fdn", compostosAlimentares.getFdn());
        intent.putExtra("ee", compostosAlimentares.getEe());
        intent.putExtra("mm", compostosAlimentares.getMm());
        intent.putExtra("cnf", compostosAlimentares.getCnf());
        intent.putExtra("pb", compostosAlimentares.getPb());
        intent.putExtra("ndt", compostosAlimentares.getNdt());
        intent.putExtra("fda", compostosAlimentares.getFda());
        intent.putExtra("descricao", compostosAlimentares.getDescricao());
        return intent;
    }

    private void abreTelaEditar(int posicao) {
        CompostosAlimentares item = (CompostosAlimentares) listCompostosAlimentares.getItemAtPosition(posicao);
        startActivity(getIntent(item, false));
    }


    private void abreTelaVisualizar(int position) {
        CompostosAlimentares item = (CompostosAlimentares) listCompostosAlimentares.getItemAtPosition(position);
        startActivity(getIntent(item, true));
    }
}
