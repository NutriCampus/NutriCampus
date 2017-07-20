package com.nutricampus.app.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import android.widget.Toast;

import com.nutricampus.app.R;
import com.nutricampus.app.adapters.ListaProducaoAdapter;
import com.nutricampus.app.database.RepositorioProducaoDeLeite;
import com.nutricampus.app.database.SharedPreferencesManager;
import com.nutricampus.app.entities.ProducaoDeLeite;
import com.nutricampus.app.utils.Conversor;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressWarnings("squid:S1172") // Ignora o erro do sonarqube para os parametros "view"
public class ListaProducaoLeiteActivity extends AppCompatActivity {

    @BindView(R.id.lista_producao)
    ListView listaProducao;
    @BindView(R.id.text_quantidade_encontrados)
    TextView mensagemQuantidade;
    @BindView(R.id.linha)
    View linha;
    @BindView(R.id.spinner_meses)
    Spinner spinnerMeses;
    @BindView(R.id.spinner_anos)
    Spinner spinnerAnos;

    SharedPreferencesManager session;

    private int idAnimal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        session = new SharedPreferencesManager(getApplicationContext());
        session.checkLogin();

        idAnimal = 1;

        setContentView(R.layout.activity_lista_producao);
        ButterKnife.bind(this);

        registerForContextMenu(listaProducao);
        carregaListView();

        listaProducao.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                abreTelaEditar(position);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.btn_add_producao);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListaProducaoLeiteActivity.this, CadastroProducaoLeiteActivity.class);
                startActivity(intent);

            }
        });
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_contexto_crud,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.menu_opc_cont_adicionar:
                Intent intent = new Intent(this, CadastrarPropriedadeActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_opc_cont_editar:
                if (info != null)
                    abreTelaEditar(info.position);
                return true;
            case R.id.menu_opc_cont_excluir:
                ProducaoDeLeite producao = (ProducaoDeLeite) listaProducao.getItemAtPosition(info.position);
                confirmarExcluir(producao);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }


    private void confirmarExcluir(final ProducaoDeLeite producaoDeLeite) {
        new AlertDialog.Builder(this)
                .setMessage(getString(R.string.msg_excluir_confirmar) + " ?")
                .setCancelable(false)
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        RepositorioProducaoDeLeite repositorio = new RepositorioProducaoDeLeite(ListaProducaoLeiteActivity.this);

                        int result = repositorio.removerProducaoDeLeite(producaoDeLeite);

                        if (result > 0) {
                            Toast.makeText(ListaProducaoLeiteActivity.this,
                                    getString(R.string.msg_sucesso_remover_registro), Toast.LENGTH_LONG).show();

                            carregaListView();
                        }
                        else{
                            Toast.makeText(ListaProducaoLeiteActivity.this,
                                    getString(R.string.msg_erro_atualizar_registro), Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .setNegativeButton("Não", null)
                .show();
    }

    public void filtrarRegistros(View view) {

        int mes = Conversor.mesParaNumero(String.valueOf(spinnerMeses.getSelectedItem()));
        int ano = Integer.valueOf(String.valueOf(spinnerAnos.getSelectedItem()));


        carregaListView(mes, ano);
    }

    private void carregaListView() {
        Calendar cal = Calendar.getInstance();
        spinnerMeses.setSelection(cal.get(Calendar.MONTH));

        this.carregaListView(cal.get(Calendar.MONTH), cal.get(Calendar.YEAR));
    }

    private void carregaListView(int mes, int ano) {

        RepositorioProducaoDeLeite repositorio = new RepositorioProducaoDeLeite(getBaseContext());
        List<ProducaoDeLeite> lista = repositorio.buscarPorAnimalPeriodo(idAnimal, mes, ano);

        ListaProducaoAdapter adapter =
                new ListaProducaoAdapter(lista, this);

        listaProducao.setAdapter(adapter);

        mensagemQuantidade.setText(getResources().getQuantityString(
                R.plurals.msg_registros_encontrados,
                adapter.getCount(),
                adapter.getCount()));

        if (lista.isEmpty())
            linha.setVisibility(View.GONE);
        else
            linha.setVisibility(View.VISIBLE);

    }

    public List<ProducaoDeLeite> buscarProducao(String nome) {
        RepositorioProducaoDeLeite repositorio = new RepositorioProducaoDeLeite(getBaseContext());
        return repositorio.buscarPorAnimal(1);
    }


    private void abreTelaEditar(int posicao) {
        ProducaoDeLeite item = (ProducaoDeLeite) listaProducao.getItemAtPosition(posicao);
        startActivity(getIntent(item));
    }


    private Intent getIntent(ProducaoDeLeite producaoDeLeite) {
        Intent intent = new Intent(this, EditarProducaoLeiteActivity.class);
        intent.putExtra("id", String.valueOf(producaoDeLeite.getId()));
        intent.putExtra("data", Conversor.dataFormatada(producaoDeLeite.getData()));
        intent.putExtra("quantidade", String.valueOf(producaoDeLeite.getQntProduzida()));
        intent.putExtra("lactose", String.valueOf(producaoDeLeite.getPctLactose()));
        intent.putExtra("proteinaBruta", String.valueOf(producaoDeLeite.getPctProteinaBruta()));
        intent.putExtra("proteinaVerdadeira", String.valueOf(producaoDeLeite.getPctProteinaVerdadeira()));
        intent.putExtra("gordura", String.valueOf(producaoDeLeite.getGordura()));
        return intent;
    }

}
