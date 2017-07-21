package com.nutricampus.app.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.nutricampus.app.R;
import com.nutricampus.app.adapters.ListaAnimaisAdapter;
import com.nutricampus.app.database.RepositorioAnimal;
import com.nutricampus.app.database.RepositorioDadosComplAnimal;
import com.nutricampus.app.database.RepositorioPropriedade;
import com.nutricampus.app.entities.Animal;
import com.nutricampus.app.entities.DadosComplAnimal;
import com.nutricampus.app.entities.Propriedade;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Felipe on 19/07/2017.
 * For project NutriCampus.
 * Contact: <felipeguimaraes540@gmail.com>
 */

public class ListaAnimaisActivity extends AppCompatActivity
        implements AdapterView.OnItemClickListener{

    @BindView(R.id.spinnerPropriedade) Spinner spinnerPropriedade;
    @BindView(R.id.listaAnimais) ListView listAnimais;
    @BindView(R.id.text_quantidades_encontrados) TextView registrosEncontrados;
    @BindView(R.id.linha) View linha;
    @BindView(R.id.input_id_propriedade) EditText inputIdPropriedade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_animais);

        ButterKnife.bind(this);

        listAnimais.setEmptyView(findViewById(android.R.id.empty));
        listAnimais.setOnItemClickListener(this);

        registerForContextMenu(listAnimais);
        carregarListView("");

        preencherSpinnerListaPropriedade();

        listAnimais.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                abreTelaEditar(position);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.btn_add_proprietario);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListaAnimaisActivity.this, CadastrarAnimalActivity.class);
                startActivity(intent);
                ListaAnimaisActivity.this.finish();

            }
        });

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        //mSearchAction = menu.findItem(R.id.action_search);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_contexto_animal,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.menu_opc_cont_historico:
                if (info != null)
                    abreTelaHistorico(info.position);
                return true;
            case R.id.menu_opc_cont_producao:
                if (info != null)
                    abreTelaComAnimal(info.position, ListaProducaoLeiteActivity.class);
                return true;

            case R.id.menu_opc_cont_prole:
                if (info != null)
                    abreTelaComAnimal(info.position, ListaProleActivity.class);
                return true;

            case R.id.menu_opc_cont_editar:
                if (info != null)
                    abreTelaEditar(info.position);
                return true;

            case R.id.menu_opc_cont_excluir:
                Animal animal = (Animal) listAnimais.getItemAtPosition(info.position);
                confirmarExcluir(animal);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void confirmarExcluir(final Animal animal) {
        new AlertDialog.Builder(this)
                .setMessage(getString(R.string.msg_excluir_confirmar_animal) + " \"" + animal.getIndentificador() + "\" ?" )
                .setCancelable(false)
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        RepositorioAnimal repositorioAnimal = new RepositorioAnimal(ListaAnimaisActivity.this);
                        RepositorioDadosComplAnimal repositorioDadosComplAnimal = new RepositorioDadosComplAnimal(ListaAnimaisActivity.this);

                        int idAnimal = animal.getId();
                        int resultAnimal = repositorioAnimal.removerAnimal(animal);

                        DadosComplAnimal dadosComplAnimal = repositorioDadosComplAnimal.buscarDadosComplAnimal(idAnimal);
                        int resultDadosCompl = repositorioDadosComplAnimal.removerDadosCompl(dadosComplAnimal);

                        if (resultAnimal > 0 && resultDadosCompl > 0) {
                            Toast.makeText(ListaAnimaisActivity.this,
                                    getString(R.string.msg_excluir_animal_sucesso), Toast.LENGTH_LONG).show();

                            carregarListView("");
                        }
                        else{
                            Toast.makeText(ListaAnimaisActivity.this,
                                    getString(R.string.msg_excluir_propriedade_falha), Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .setNegativeButton("Não", null)
                .show();
    }

    private void abreTelaEditar(int position) {

    }

    private void abreTelaHistorico(int position) {
        Animal animal = (Animal) listAnimais.getItemAtPosition(position);
        Intent intent = new Intent(ListaAnimaisActivity.this, AtualizarHistoricoAnimalActivity.class);
        intent.putExtra("animal", animal);
        startActivity(intent);
    }

    private void abreTelaComAnimal(int position, Class classe) {
        int idAnimal = ((Animal) listAnimais.getItemAtPosition(position)).getId();
        Intent intent = new Intent(ListaAnimaisActivity.this, classe);
        intent.putExtra("idAnimal", idAnimal);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        preencherSpinnerListaPropriedade();
    }

    private void carregarListView(String s) {
        RepositorioAnimal repositorioAnimal = new RepositorioAnimal(ListaAnimaisActivity.this);
        List<Animal> animais = repositorioAnimal.buscarTodosAnimais();

        ListaAnimaisAdapter adapter =
                new ListaAnimaisAdapter(this, animais);

        listAnimais.setAdapter(adapter);

        if (animais.isEmpty()) {
            linha.setVisibility(View.GONE);
            spinnerPropriedade.setVisibility(View.GONE);
        } else {
            spinnerPropriedade.setVisibility(View.VISIBLE);
            linha.setVisibility(View.VISIBLE);
            registrosEncontrados.setText(getResources().getQuantityString(
                    R.plurals.quatidade_registros,
                    adapter.getCount(),
                    adapter.getCount()));
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    public void preencherSpinnerListaPropriedade() {

        RepositorioPropriedade repositorioPropriedade= new RepositorioPropriedade(getBaseContext());
        List<Propriedade> todasPropriedades = repositorioPropriedade.buscarTodasPropriedades();

        if (!(todasPropriedades.isEmpty())) {

            // Adiciona a msg de "Selecione..." no spinner da propriedade
            Propriedade posZero = new Propriedade(0, getString(R.string.msg_spinner_propriedade), "", "", "", "", "", "", "", 1,1);
            todasPropriedades.add(0, posZero);

            ArrayAdapter<Propriedade> spinnerPropriedadeAdapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_item, todasPropriedades);

            spinnerPropriedadeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spinnerPropriedade.setAdapter(spinnerPropriedadeAdapter);

            int posicao;
            String idPropriedade = inputIdPropriedade.getText().toString();

            if (idPropriedade.isEmpty())
                posicao = 0;
            else
                posicao = spinnerPropriedadeAdapter.getPosition(repositorioPropriedade.buscarPropriedade(
                        Integer.parseInt(idPropriedade)));


            spinnerPropriedade.setSelection(posicao);
        } else {
            Propriedade prop = new Propriedade(0, "<< " + getString(R.string.msg_cadastre_propriedade) + " >>", "", "", "", "", "", "", "", 1,1);
            todasPropriedades.add(prop);
            ArrayAdapter<Propriedade> spinnerPropriedadeAdapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_item, todasPropriedades);

            spinnerPropriedade.setAdapter(spinnerPropriedadeAdapter);

        }
    }
}
