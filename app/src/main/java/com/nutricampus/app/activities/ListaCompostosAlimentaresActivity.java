package com.nutricampus.app.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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
import com.nutricampus.app.adapters.ListaCompostosAlimentaresAdapter;
import com.nutricampus.app.adapters.ListaPropriedadesAdapter;
import com.nutricampus.app.database.RepositorioAnimal;
import com.nutricampus.app.database.RepositorioCompostosAlimentares;
import com.nutricampus.app.database.RepositorioPropriedade;
import com.nutricampus.app.database.RepositorioProprietario;
import com.nutricampus.app.database.SharedPreferencesManager;
import com.nutricampus.app.entities.Animal;
import com.nutricampus.app.entities.CompostosAlimentares;
import com.nutricampus.app.entities.Propriedade;
import com.nutricampus.app.entities.Proprietario;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressWarnings("squid:S1172") // Ignora o erro do sonarqube para os parametros "view"
public class ListaCompostosAlimentaresActivity extends AppCompatActivity {

    public static final String EXTRA_PROPRIEDADE = "propriedade";

    @BindView(R.id.listaCompostosAlimentares)
    ListView listCompostosAlimentares;
    @BindView(R.id.text_quantidade_encontrados)
    TextView mensagemQuantidade;
    @BindView(R.id.linha)
    View linha;

    SharedPreferencesManager session;

    private MenuItem mSearchAction;
    private boolean isSearchOpened = false;
    private EditText inputPesquisaComposto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        session = new SharedPreferencesManager(getApplicationContext());
        session.checkLogin();

        setContentView(R.layout.activity_lista_compostos_alimentares);
        ButterKnife.bind(this);

        listCompostosAlimentares.setEmptyView(findViewById(android.R.id.empty));

        registerForContextMenu(listCompostosAlimentares);
        carregaListView("", true);

        listCompostosAlimentares.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //abreTelaEditar(position);
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
    protected void onResume() {
        super.onResume();
        carregaListView("", true);

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
        inflater.inflate(R.menu.menu_contexto_composto, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.menu_opc_cont_visualizar_composto:
                if (info != null)
                    //abreTelaComposto(info.position, ListaAnimaisActivity.class);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_search) {
            gerenciaFuncaoPesquisar();
            return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (isSearchOpened) {
            gerenciaFuncaoPesquisar();
            return;
        }
        super.onBackPressed();
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

                            carregaListView("", true);
                        } else {
                            Toast.makeText(ListaCompostosAlimentaresActivity.this,
                                    getString(R.string.msg_excluir_propriedade_falha), Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .setNegativeButton("Não", null)
                .show();
    }

    protected void gerenciaFuncaoPesquisar() {
        ActionBar action = getSupportActionBar(); //get the actionbar

        if (isSearchOpened) { //test if the search is open

            if (action != null) {
                action.setDisplayShowCustomEnabled(false); //disable a custom view inside the actionbar
                action.setDisplayShowTitleEnabled(true); //show the title in the action bar
                carregaListView("", true);
            }
            //hides the keyboard
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(inputPesquisaComposto.getWindowToken(), 0);

            //add the search icon in the action bar
            mSearchAction.setIcon(R.drawable.ic_search_light);

            isSearchOpened = false;
        } else { //open the search entry

            if (action != null) {
                action.setDisplayShowCustomEnabled(true); //enable it to display a
                // custom view in the action bar.
                action.setCustomView(R.layout.barra_pesquisa);//add the custom view
                action.setDisplayShowTitleEnabled(false); //hide the title

                inputPesquisaComposto = (EditText) action.getCustomView().findViewById(R.id.input_pesquisa); //the text editor


                //this is a listener to do a search when the user clicks on search button
                inputPesquisaComposto.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                        if (i == EditorInfo.IME_ACTION_SEARCH) {
                            carregaListView(inputPesquisaComposto.getText().toString(), false);
                            return true;
                        }
                        return false;
                    }

                });


                inputPesquisaComposto.requestFocus();

                //open the keyboard focused in the edtSearch
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(inputPesquisaComposto, InputMethodManager.SHOW_IMPLICIT);


                //add the close icon
                mSearchAction.setIcon(R.drawable.ic_close);

                isSearchOpened = true;
            }
        }
    }

    private void carregaListView(String nome, boolean tudo) {
        List<CompostosAlimentares> lista = null;
        ListaCompostosAlimentaresAdapter adapter = null;
        //if (!tudo) {
        lista = this.buscarComposto(nome);

        adapter = new ListaCompostosAlimentaresAdapter(lista, this);

        listCompostosAlimentares.setAdapter(adapter);
        /*} else {
            RepositorioCompostosAlimentares repositorioPropriedade = new RepositorioCompostosAlimentares(this);
            lista = repositorioPropriedade.buscarTodosCompostosAlimentares();
            adapter = new ListaCompostosAlimentaresAdapter(lista, this);


        }*/
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
        //List<CompostosAlimentares> arr = new ArrayList<>();
        //arr.add(repositorioPropriedade.buscarCompostoAlimentar(nome));
        return repositorioPropriedade.buscarTodosCompostos(nome);
    }

    private Intent getIntent(CompostosAlimentares compostosAlimentares) {
        Intent intent = new Intent(this, EditarCompostoActivity.class);

        intent.putExtra("id", compostosAlimentares.getId());
        intent.putExtra("tipo", compostosAlimentares.getTipo());
        intent.putExtra("identificador", compostosAlimentares.getIdentificador());
        intent.putExtra("ms", compostosAlimentares.getMS());
        intent.putExtra("fdn", compostosAlimentares.getFDN());
        intent.putExtra("ee", compostosAlimentares.getEE());
        intent.putExtra("mm", compostosAlimentares.getMM());
        intent.putExtra("cnf", compostosAlimentares.getCNF());
        intent.putExtra("pb", compostosAlimentares.getPB());
        intent.putExtra("ndt", compostosAlimentares.getNDT());
        intent.putExtra("fda", compostosAlimentares.getFDA());
        intent.putExtra("descricao", compostosAlimentares.getDescricao());
        return intent;
    }

    private void abreTelaEditar(int posicao) {
        CompostosAlimentares item = (CompostosAlimentares) listCompostosAlimentares.getItemAtPosition(posicao);
        startActivity(getIntent(item));
    }

    /*private void abreTelaAnimal(int posicao, Class activity) {
        CompostosAlimentares propriedade = (CompostosAlimentares) listPropriedades.getItemAtPosition(posicao);
        Intent intent = new Intent(ListaCompostosAlimentaresActivity.this, activity);
        intent.putExtra(EXTRA_PROPRIEDADE, propriedade);
        startActivity(intent);
        this.finish();
    }*/
}
