package com.nutricampus.app.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import com.nutricampus.app.adapters.ListaProleAdapter;
import com.nutricampus.app.database.RepositorioProle;
import com.nutricampus.app.database.SharedPreferencesManager;
import com.nutricampus.app.entities.Prole;
import com.nutricampus.app.utils.Conversor;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListaProleActivity extends AppCompatActivity {
    @BindView(R.id.lista_prole)
    ListView listaProle;
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

        if (this.getIntent() != null)
            this.idAnimal = getIntent().getIntExtra("idAnimal", 0);

        setContentView(R.layout.activity_lista_prole);
        ButterKnife.bind(this);

        registerForContextMenu(listaProle);
        carregaListView();

        listaProle.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                abreTelaEditar(position);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.btn_add_prole);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListaProleActivity.this, CadastroProleActivity.class);
                intent.putExtra("idAnimal", idAnimal);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        filtrarRegistros(new View(this));
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_contexto_crud, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.menu_opc_cont_adicionar:
                Intent intent = new Intent(this, CadastroProleActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_opc_cont_editar:
                if (info != null)
                    abreTelaEditar(info.position);
                return true;
            case R.id.menu_opc_cont_excluir:
                Prole prole = (Prole) listaProle.getItemAtPosition(info.position);
                confirmarExcluir(prole);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }


    private void abreTelaEditar(int posicao) {
        Prole item = (Prole) listaProle.getItemAtPosition(posicao);
        startActivity(getIntent(item));
    }

    private void confirmarExcluir(final Prole prole) {
        new AlertDialog.Builder(this)
                .setMessage(getString(R.string.msg_excluir_confirmar) + " ?")
                .setCancelable(false)
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        RepositorioProle repositorio = new RepositorioProle(ListaProleActivity.this);

                        int result = repositorio.removerProle(prole);

                        if (result > 0) {
                            Toast.makeText(ListaProleActivity.this,
                                    getString(R.string.msg_sucesso_remover_registro), Toast.LENGTH_LONG).show();

                            filtrarRegistros(new View(ListaProleActivity.this));
                        } else {
                            Toast.makeText(ListaProleActivity.this,
                                    getString(R.string.msg_erro_atualizar_registro), Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .setNegativeButton("Não", null)
                .show();
    }

    public void filtrarRegistros(View view) {

        int mes = Conversor.mesStringParaInt(String.valueOf(spinnerMeses.getSelectedItem()));
        int ano = Integer.valueOf(String.valueOf(spinnerAnos.getSelectedItem()));


        carregaListView(mes, ano);
    }

    private void carregaListView() {
        Calendar cal = Calendar.getInstance();
        spinnerMeses.setSelection(cal.get(Calendar.MONTH));

        this.carregaListView(cal.get(Calendar.MONTH), cal.get(Calendar.YEAR));
    }

    private void carregaListView(int mes, int ano) {
        Log.w("ID MATRZ", idAnimal + "");
        RepositorioProle repositorio = new RepositorioProle(getBaseContext());
        List<Prole> lista;

        // Se o mes escolhido fro TODOS (dezembro equivale a 11)
        if (mes == 12)
            lista = repositorio.buscarPorMatriz(this.idAnimal);
        else
            lista = repositorio.buscarProlesPorAnimalPeriodo(idAnimal, mes, ano);

        ListaProleAdapter adapter = new ListaProleAdapter(lista, this);

        listaProle.setAdapter(adapter);

        mensagemQuantidade.setText(getResources().getQuantityString(
                R.plurals.msg_registros_encontrados,
                adapter.getCount(),
                adapter.getCount()));

        if (lista.isEmpty())
            linha.setVisibility(View.GONE);
        else
            linha.setVisibility(View.VISIBLE);

    }

    private Intent getIntent(Prole prole) {
        Intent intent = new Intent(this, EditarProleActivity.class);
        intent.putExtra("id", String.valueOf(prole.getId()));
        intent.putExtra("data", Conversor.dataFormatada(prole.getDataDeNascimento()));
        intent.putExtra("peso", String.valueOf(prole.getPesoDeNascimento()));
        intent.putExtra("isNatimorto", prole.isNatimorto());
        intent.putExtra("idAnimalMatriz", prole.getMatriz());

        return intent;
    }
}
