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
import com.nutricampus.app.fragments.DadosAnimalFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Felipe on 19/07/2017.
 * For project NutriCampus.
 * Contact: <felipeguimaraes540@gmail.com>
 */

/*
Explicação para a supressão de warnings:
 - "squid:MaximumInheritanceDepth" = herança extendida em muitos niveis (mais que 5), permitido aqui já
 que refere-se a herança das classes das activities Android
 - "squid:S1172" = erro do sonarqube para os parametros "view" não utilizados
*/
@java.lang.SuppressWarnings({"squid:S1172", "squid:MaximumInheritanceDepth"})
public class ListaAnimaisActivity extends AppCompatActivity
        implements AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {

    @BindView(R.id.spinnerPropriedade)
    Spinner spinnerPropriedade;
    @BindView(R.id.listaAnimais)
    ListView listAnimais;
    @BindView(R.id.text_quantidades_encontrados)
    TextView registrosEncontrados;
    @BindView(R.id.linha)
    View linha;
    @BindView(R.id.input_id_propriedade)
    EditText inputIdPropriedade;

    private Propriedade propriedade;

    private MenuItem mSearchAction;
    private boolean isSearchOpened = false;
    private EditText inputPesquisa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_animais);

        ButterKnife.bind(this);

        listAnimais.setEmptyView(findViewById(android.R.id.empty));
        listAnimais.setOnItemClickListener(this);

        registerForContextMenu(listAnimais);

        propriedade = (Propriedade) getIntent().getSerializableExtra(ListaPropriedadesActivity.EXTRA_PROPRIEDADE);

        if (propriedade == null) {
            carregarListView(0, "");
        } else {
            inputIdPropriedade.setText(String.valueOf(propriedade.getId()));
            carregarListView(propriedade.getId(), "");
        }

        spinnerPropriedade.setOnItemSelectedListener(this);
        preencherSpinnerListaPropriedade();

        listAnimais.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                abreTelaEditar(position, EditarAnimalActivity.class);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.btn_add_animais);
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
        mSearchAction = menu.findItem(R.id.action_search);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_contexto_animal, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.menu_opc_cont_historico:
                if (info != null)
                    chamarActivity(info.position, ListaDadosComplActivity.class);
                return true;
            case R.id.menu_opc_cont_producao:
                if (info != null)
                    chamarActivity(info.position, ListaProducaoLeiteActivity.class);
                return true;

            case R.id.menu_opc_cont_prole:
                if (info != null)
                    chamarActivity(info.position, ListaProleActivity.class);
                return true;

            case R.id.menu_opc_cont_editar:
                if (info != null)
                    chamarActivity(info.position, EditarAnimalActivity.class);
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
                .setMessage(getString(R.string.msg_excluir_confirmar_animal) + " \"" + animal.getIndentificador() + "\" ?")
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

                            carregarListView(0, "");

                        } else {
                            Toast.makeText(ListaAnimaisActivity.this,
                                    getString(R.string.msg_erro_deletar_registro), Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .setNegativeButton("Não", null)
                .show();
    }

    private void abreTelaEditar(int posicao, Class classe) {
        chamarActivity(posicao, classe);
    }

    private void chamarActivity(int posicao, Class classe) {
        Animal animal = (Animal) listAnimais.getItemAtPosition(posicao);
        Intent intent = new Intent(ListaAnimaisActivity.this, classe);
        intent.putExtra(DadosAnimalFragment.EXTRA_ANIMAL, animal);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        preencherSpinnerListaPropriedade();
    }

    private void carregarListView(int idPropriedade, String idenficador) {
        RepositorioAnimal repositorioAnimal = new RepositorioAnimal(ListaAnimaisActivity.this);
        List<Animal> animais;

        if (idPropriedade == 0)
            animais = repositorioAnimal.buscarTodosAnimais(idenficador);
        else
            animais = repositorioAnimal.buscarPorIdentificador(idPropriedade, idenficador);

        ListaAnimaisAdapter adapter =
                new ListaAnimaisAdapter(this, animais);

        listAnimais.setAdapter(adapter);
        spinnerPropriedade.setVisibility(View.VISIBLE);

        if (animais.isEmpty()) {
            linha.setVisibility(View.GONE);
            registrosEncontrados.setVisibility(View.GONE);
        } else {
            linha.setVisibility(View.VISIBLE);
            registrosEncontrados.setVisibility(View.VISIBLE);
            registrosEncontrados.setText(getResources().getQuantityString(
                    R.plurals.quatidade_registros,
                    adapter.getCount(),
                    adapter.getCount()));
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        // Existência necessária devido a herança, mas não implementada.
    }

    public void preencherSpinnerListaPropriedade() {

        RepositorioPropriedade repositorioPropriedade = new RepositorioPropriedade(getBaseContext());
        List<Propriedade> todasPropriedades = repositorioPropriedade.buscarTodasPropriedades();

        if (!(todasPropriedades.isEmpty())) {

            // Adiciona a msg de "Selecione..." no spinner da propriedade
            Propriedade posZero = new Propriedade(0, getString(R.string.msg_spinner_propriedade), "", "", "", "", "", "", "", 1, 1);
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
            Propriedade prop = new Propriedade(0, "<< " + getString(R.string.msg_cadastre_propriedade) + " >>", "", "", "", "", "", "", "", 1, 1);
            todasPropriedades.add(prop);
            ArrayAdapter<Propriedade> spinnerPropriedadeAdapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_item, todasPropriedades);

            spinnerPropriedade.setAdapter(spinnerPropriedadeAdapter);

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

    private void gerenciaFuncaoPesquisar() {
        ActionBar action = getSupportActionBar(); //get the actionbar
        int id = 0;

        if (isSearchOpened) { //test if the search is open

            if (action != null) {
                action.setDisplayShowCustomEnabled(false); //disable a custom view inside the actionbar
                action.setDisplayShowTitleEnabled(true); //show the title in the action bar

                carregarListView(id, "");
            }
            //hides the keyboard
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(inputPesquisa.getWindowToken(), 0);

            //add the search icon in the action bar
            mSearchAction.setIcon(R.drawable.ic_search_light);

            isSearchOpened = false;
        } else { //open the search entry

            if (action != null) {
                action.setDisplayShowCustomEnabled(true); //enable it to display a
                // custom view in the action bar.
                action.setCustomView(R.layout.barra_pesquisa);//add the custom view
                action.setDisplayShowTitleEnabled(false); //hide the title

                inputPesquisa = (EditText) action.getCustomView().findViewById(R.id.input_pesquisa); //the text editor


                //this is a listener to do a search when the user clicks on search button
                inputPesquisa.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                        if (i == EditorInfo.IME_ACTION_SEARCH) {
                            carregarListView(0, inputPesquisa.getText().toString());
                            return true;
                        }
                        return false;
                    }

                });


                inputPesquisa.requestFocus();

                //open the keyboard focused in the edtSearch
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(inputPesquisa, InputMethodManager.SHOW_IMPLICIT);


                //add the close icon
                mSearchAction.setIcon(R.drawable.ic_close);

                isSearchOpened = true;
            }
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {

        if ((parent != null) && (parent.getItemAtPosition(position) instanceof Propriedade)) {
            propriedade = (Propriedade) parent.getItemAtPosition(position);
            inputIdPropriedade.setText(String.valueOf(propriedade.getId()));
            carregarListView(propriedade.getId(), "");
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        // Método herdado apenas pelo motivo de ser necessáiro pelo uso da
        // interface OnItemSelectedListener
    }
}
