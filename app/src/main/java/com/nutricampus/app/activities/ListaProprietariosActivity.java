package com.nutricampus.app.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nutricampus.app.R;
import com.nutricampus.app.adapters.ListaProprietariosAdapter;
import com.nutricampus.app.database.RepositorioAnimal;
import com.nutricampus.app.database.RepositorioPropriedade;
import com.nutricampus.app.database.RepositorioProprietario;
import com.nutricampus.app.entities.Animal;
import com.nutricampus.app.entities.Propriedade;
import com.nutricampus.app.entities.Proprietario;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Felipe on 01/08/2017.
 * For project NutriCampus.
 * Contact: <felipeguimaraes540@gmail.com>
 */

public class ListaProprietariosActivity extends AppCompatActivity {

    public static final String EXTRA_PROPRIETARIO = "proprietario";
    public static final String EXTRA_VOLTAR_PROPRIETARIOS = "voltar";

    @BindView(R.id.listaProprietarios)
    ListView listaProprietarios;
    @BindView(R.id.text_quantidade_encontrados)
    TextView mensagemQuantidade;
    @BindView(R.id.linha)
    View linha;

    private MenuItem mSearchAction;
    private boolean isSearchOpened = false;
    private EditText inputPesquisaProprietarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_proprietarios);

        ButterKnife.bind(this);
        registerForContextMenu(listaProprietarios);

        listaProprietarios.setEmptyView(findViewById(android.R.id.empty));
        carregarListView("");

        listaProprietarios.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                chamarActivity(position, EditarProprietarioActivity.class);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_add_Proprietario);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //-1 para controle no metodo chamarActivity(int, Class)
                chamarActivity(-1, CadastrarProprietarioActivity.class);
            }
        });
    }

    private void carregarListView(String nome) {
        RepositorioProprietario repositorioProprietario = new RepositorioProprietario(getBaseContext());
        List<Proprietario> lista = repositorioProprietario.buscarTodosProprietarios(nome);

        ListaProprietariosAdapter adapter =
                new ListaProprietariosAdapter(lista, ListaProprietariosActivity.this);

        listaProprietarios.setAdapter(adapter);

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

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        mSearchAction = menu.findItem(R.id.action_search);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {

        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_contexto_proprietario, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.menu_opc_cont_adicionar_propriedade:
                if (info != null)
                    chamarActivity(info.position, CadastrarPropriedadeActivity.class);
                return true;
            case R.id.menu_opc_cont_editar:
                if (info != null)
                    chamarActivity(info.position, EditarProprietarioActivity.class);
                return true;
            case R.id.menu_opc_cont_excluir:
                Proprietario proprietario = (Proprietario) listaProprietarios.getItemAtPosition(info.position);
                confirmarExcluir(proprietario);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void chamarActivity(int posicao, Class classe) {
        Proprietario proprietario = null;
        Intent intent = new Intent(ListaProprietariosActivity.this, classe);

        //if para controlar a chamada ao cadastro de proprietário
        if(posicao != -1) {
            proprietario = (Proprietario) listaProprietarios.getItemAtPosition(posicao);
            intent.putExtra(ListaProprietariosActivity.EXTRA_PROPRIETARIO, proprietario);
        }

        intent.putExtra(ListaProprietariosActivity.EXTRA_VOLTAR_PROPRIETARIOS, 1);
        startActivity(intent);
    }

    private void confirmarExcluir(final Proprietario proprietario) {
        new AlertDialog.Builder(this)
                .setMessage(getString(R.string.msg_excluir_confirmar_propritario,"\"" + proprietario.getNome() + "\"" ))
                .setCancelable(false)
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        RepositorioProprietario repositorioProprietario = new RepositorioProprietario(ListaProprietariosActivity.this);
                        RepositorioPropriedade repositorioPropriedade = new RepositorioPropriedade(ListaProprietariosActivity.this);
                        RepositorioAnimal repositorioAnimal = new RepositorioAnimal(ListaProprietariosActivity.this);


                        List<Animal> listAnimal;

                        ArrayList<Propriedade> propriedades = (ArrayList<Propriedade>)
                                repositorioPropriedade.PropriedadesOfProprietario(proprietario.getId());

                        int result = repositorioProprietario.removerProprietario(proprietario);

                        if (result > 0) {
                            Toast.makeText(ListaProprietariosActivity.this,
                                    getString(R.string.msg_excluir_proprietario_sucesso), Toast.LENGTH_LONG).show();

                            if (!(propriedades.isEmpty())) {
                                for (Propriedade p : propriedades) {
                                    listAnimal = repositorioAnimal.buscarPorPropridade(p.getId());
                                    for(Animal a : listAnimal)
                                        repositorioAnimal.removerAnimal(a);

                                    repositorioPropriedade.removerPropriedade(p);
                                }
                            }

                            carregarListView("");

                        }
                        else{
                            Toast.makeText(ListaProprietariosActivity.this,
                                    getString(R.string.msg_excluir_proprietario_falha), Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .setNegativeButton("Não", null)
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    private void gerenciaFuncaoPesquisar() {
        ActionBar action = getSupportActionBar(); //get the actionbar

        //Testa se a busca está aberta
        if(isSearchOpened){

            if (action != null) {
                action.setDisplayShowCustomEnabled(false); //disable a custom view inside the actionbar
                action.setDisplayShowTitleEnabled(true); //show the title in the action bar
                carregarListView("");
            }
            //Esconde o teclado
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(inputPesquisaProprietarios.getWindowToken(), 0);

            //Adiciona o icone de pesquisa na action bar
            mSearchAction.setIcon(R.drawable.ic_search_light);

            isSearchOpened = false;

        } else { //Abre a entreda da pesquisa

            if (action != null) {
                //Habilita o display
                action.setDisplayShowCustomEnabled(true);
                // Personaliza a view na action bar.
                action.setCustomView(R.layout.barra_pesquisa);//Adiciona a personalização da view
                action.setDisplayShowTitleEnabled(false); //Esconde o titulo

                inputPesquisaProprietarios = (EditText) action.getCustomView().findViewById(R.id.input_pesquisa);

                inputPesquisaProprietarios.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                        if (i == EditorInfo.IME_ACTION_SEARCH) {
                            carregarListView(inputPesquisaProprietarios.getText().toString());
                            return true;
                        }
                        return false;
                    }

                });

                inputPesquisaProprietarios.requestFocus();

                //Abri o teclado para editar o search
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(inputPesquisaProprietarios, InputMethodManager.SHOW_IMPLICIT);

                //Add o icone de 'fechar: X'
                mSearchAction.setIcon(R.drawable.ic_close);

                isSearchOpened = true;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarListView("");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                gerenciaFuncaoPesquisar();
                break;
            case android.R.id.home:
                Intent it = new Intent(ListaProprietariosActivity.this, ListaPropriedadesActivity.class);
                startActivity(it);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed(){
        Intent it = new Intent(ListaProprietariosActivity.this, ListaPropriedadesActivity.class);
        startActivity(it);
        finish();
    }

}
