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
import com.nutricampus.app.adapters.ListaPropriedadesAdapter;
import com.nutricampus.app.database.RepositorioPropriedade;
import com.nutricampus.app.database.RepositorioProprietario;
import com.nutricampus.app.database.SharedPreferencesManager;
import com.nutricampus.app.entities.Propriedade;
import com.nutricampus.app.entities.Proprietario;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

@java.lang.SuppressWarnings("squid:S1172") // Ignora o erro do sonarqube para os parametros "view"
public class ListaPropriedadesActivity extends AppCompatActivity{

    @BindView(R.id.listaPropriedades) ListView listPropriedades;
    @BindView(R.id.text_quantidade_encontrados) TextView mensagemQuantidade;
    @BindView(R.id.linha) View linha;

    SharedPreferencesManager session;

    private MenuItem mSearchAction;
    private boolean isSearchOpened = false;
    private EditText inputPesquisaPropriedades;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        session = new SharedPreferencesManager(getApplicationContext());
        session.checkLogin();

        setContentView(R.layout.activity_lista_propriedades);
        ButterKnife.bind(this);
        registerForContextMenu(listPropriedades);
        carregaListView("");

        listPropriedades.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                abreTelaEditar(position);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.btn_add_proprietario);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListaPropriedadesActivity.this, CadastrarPropriedadeActivity.class);
                startActivity(intent);
                //ListaPropriedadesActivity.this.finish();

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregaListView("");

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
        inflater.inflate(R.menu.menu_contexto_crud,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.menu_opc_cont_adicionar:
                Intent intent = new Intent(this, CadastrarPropriedadeActivity.class);
                startActivity(intent);
                this.finish();
                return true;
            case R.id.menu_opc_cont_editar:
                if (info != null)
                    abreTelaEditar(info.position);
                return true;
            case R.id.menu_opc_cont_excluir:
                Propriedade propriedade = (Propriedade) listPropriedades.getItemAtPosition(info.position);
                confirmarExcluir(propriedade);
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
        if(isSearchOpened) {
            gerenciaFuncaoPesquisar();
            return;
        }
        super.onBackPressed();
    }

    private void confirmarExcluir(final Propriedade propriedade){
        new AlertDialog.Builder(this)
                .setMessage(getString(R.string.msg_excluir_confirmar) + " a \"" + propriedade.getNome() + "\" ?" )
                .setCancelable(false)
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        RepositorioProprietario repositorioProprietario = new RepositorioProprietario(ListaPropriedadesActivity.this);
                        RepositorioPropriedade repositorioPropriedade = new RepositorioPropriedade(ListaPropriedadesActivity.this);

                        Proprietario proprietario = repositorioProprietario.buscarProprietario(propriedade.getIdProprietario());


                        boolean isdeleteProprietario = repositorioPropriedade.isPropriedadeProprietario(proprietario.getId());

                        int result = repositorioPropriedade.removerPropriedade(propriedade);

                        if (result > 0) {
                            Toast.makeText(ListaPropriedadesActivity.this,
                                    getString(R.string.msg_excluir_propriedade_sucesso), Toast.LENGTH_LONG).show();

                            /*Excluirá o proprietário da propriedade por ultimo excluída,
                            caso ele não possua mais nenhuma propriedade*/
                            if (!isdeleteProprietario) {
                                repositorioProprietario.removerProprietario(proprietario);
                            }

                            carregaListView("");
                        }
                        else{
                            Toast.makeText(ListaPropriedadesActivity.this,
                                    getString(R.string.msg_excluir_propriedade_falha), Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .setNegativeButton("Não", null)
                .show();
    }

    protected void gerenciaFuncaoPesquisar(){
        ActionBar action = getSupportActionBar(); //get the actionbar

        if(isSearchOpened){ //test if the search is open

            if (action != null) {
                action.setDisplayShowCustomEnabled(false); //disable a custom view inside the actionbar
                action.setDisplayShowTitleEnabled(true); //show the title in the action bar
                carregaListView("");
            }
            //hides the keyboard
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(inputPesquisaPropriedades.getWindowToken(), 0);

            //add the search icon in the action bar
            mSearchAction.setIcon(R.drawable.ic_search_light);

            isSearchOpened = false;
        } else { //open the search entry

            if (action != null) {
                action.setDisplayShowCustomEnabled(true); //enable it to display a
                // custom view in the action bar.
                action.setCustomView(R.layout.barra_pesquisa);//add the custom view
                action.setDisplayShowTitleEnabled(false); //hide the title

                inputPesquisaPropriedades = action.getCustomView().findViewById(R.id.input_pesquisa_propriedades); //the text editor

                //this is a listener to do a search when the user clicks on search button
                inputPesquisaPropriedades.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                        if (i == EditorInfo.IME_ACTION_SEARCH) {
                            carregaListView(inputPesquisaPropriedades.getText().toString());
                            return true;
                        }
                        return false;
                    }

                });


                inputPesquisaPropriedades.requestFocus();

                //open the keyboard focused in the edtSearch
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(inputPesquisaPropriedades, InputMethodManager.SHOW_IMPLICIT);


                //add the close icon
                mSearchAction.setIcon(R.drawable.ic_close);

                isSearchOpened = true;
            }
        }
    }

    private void carregaListView(String nome) {
        List<Propriedade> lista = this.buscarPropriedades(nome);

        ListaPropriedadesAdapter adapter =
                new ListaPropriedadesAdapter(lista, this);

        listPropriedades.setAdapter(adapter);

        mensagemQuantidade.setText(getResources().getQuantityString(
                R.plurals.msg_registros_encontrados,
                adapter.getCount(),
                adapter.getCount()));

        if (lista.isEmpty())
            linha.setVisibility(View.GONE);
        else
            linha.setVisibility(View.VISIBLE);

    }

    public List<Propriedade> buscarPropriedades(String nome){
        RepositorioPropriedade repositorioPropriedade = new RepositorioPropriedade(getBaseContext());
        return repositorioPropriedade.buscarPropriedadesPorNome(nome, Integer.parseInt(session.getIdNC()));
    }

    private Intent getIntent(Propriedade propriedade){
        Intent intent = new Intent(this, EditarPropriedadeActivity.class);
        intent.putExtra("id",propriedade.getId());
        intent.putExtra("nome",propriedade.getNome());
        intent.putExtra("telefone",propriedade.getTelefone());
        intent.putExtra("rua",propriedade.getLogradouro());
        intent.putExtra("bairro",propriedade.getBairro());
        intent.putExtra("numero",propriedade.getNumero());
        intent.putExtra("cep",propriedade.getCep());
        intent.putExtra("cidade",propriedade.getCidade());
        intent.putExtra("estado",propriedade.getEstado());
        intent.putExtra("idProprietario",propriedade.getIdProprietario());
        return intent;
    }

    private void abreTelaEditar(int posicao){
        Propriedade item = (Propriedade) listPropriedades.getItemAtPosition(posicao);
        startActivity(getIntent(item));
    }
}
