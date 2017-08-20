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
import com.nutricampus.app.database.RepositorioProducaoDeLeite;
import com.nutricampus.app.database.RepositorioPropriedade;
import com.nutricampus.app.database.RepositorioProprietario;
import com.nutricampus.app.database.SharedPreferencesManager;
import com.nutricampus.app.entities.Animal;
import com.nutricampus.app.entities.DadosComplAnimal;
import com.nutricampus.app.entities.ProducaoDeLeite;
import com.nutricampus.app.entities.Propriedade;
import com.nutricampus.app.entities.Proprietario;
import com.nutricampus.app.fragments.DadosAnimalFragment;

import java.util.Calendar;
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
public class ListaAnimaisActivity extends AbstractListComPesquisa
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


    private RepositorioProducaoDeLeite repositorioProducaoDeLeite;
    private ProducaoDeLeite producaoDeLeite1;
    private ProducaoDeLeite producaoDeLeite2;

    public void preparaDados() {

        RepositorioPropriedade repositorioPropriedade = new RepositorioPropriedade(this);
        RepositorioProprietario repositorioProprietario = new RepositorioProprietario(this);
        RepositorioAnimal repositorioAnimal = new RepositorioAnimal(this);

        repositorioPropriedade.removerTodos();
        repositorioProprietario.removerTodos();
        repositorioAnimal.removerTodos();

        String nome1 = "nomeAdmin proprietario",
                nome2 = "nomeAdmin proprietario";
        String email1 = "email@proprietario1.com",
                email2 = "email@proprietario2.com";
        String cpf1 = "000.000.000-000",
                cpf2 = "999.999.999-99";
        String prop1 = "propriedadeAdminUM",
                prop2 = "propriedadeAdminDOIS";
        String animal1 = "animalAdmin UM",
                animal2 = "animalAdmin DOIS";
        String tel1 = "(87) 00000 0000",
                tel2 = "(87) 99999 9999";
        String rua1 = "rua1",
                rua2 = "rua2";
        String bairro1 = "bairro1",
                bairro2 = "bairro2";
        String cep1 = "00000-000",
                cep2 = "99999-999";

        int idAnimal1;
        int idAnimal2;
        int idPropriedade1;
        int idPropriedade2;
        int idProprietario1 = 0;
        int idProprietario2 = 0;

        if (repositorioProprietario.buscarProprietario(cpf1) == null) {
            idProprietario1 = repositorioProprietario.inserirProprietario(new Proprietario(1, cpf1, nome1, email1, tel1));
            idProprietario2 = repositorioProprietario.inserirProprietario(new Proprietario(2, cpf2, nome2, email2, tel2));
        }
        if (repositorioPropriedade.buscarPropriedade(prop1) == null) {
            idPropriedade1 = repositorioPropriedade.inserirPropriedade(new Propriedade(1, prop1, tel1, rua1, bairro1, cep1, "Garanhuns", "Pernambuco", "000", idProprietario1, 1));
            idPropriedade2 = repositorioPropriedade.inserirPropriedade(new Propriedade(2, prop2, tel2, rua2, bairro2, cep2, "Caruaru", "Pernambuco", "999", idProprietario2, 1));
        } else {
            idPropriedade1 = repositorioPropriedade.buscarPropriedade(prop1).getId();
            idPropriedade2 = repositorioPropriedade.buscarPropriedade(prop2).getId();
        }


        RepositorioAnimal repoAnimal = new RepositorioAnimal(this);
        int idUsuario = Integer.parseInt(new SharedPreferencesManager(this).getIdUsuario() + "0");

        if (repoAnimal.buscarAnimal(animal1, idPropriedade1) == null) {

            idAnimal1 = repoAnimal.inserirAnimal(new Animal(1, animal1, idPropriedade1, Calendar.getInstance(), true, idUsuario));
            idAnimal2 = repoAnimal.inserirAnimal(new Animal(2, animal2, idPropriedade2, Calendar.getInstance(), true, idUsuario));

            RepositorioDadosComplAnimal repositorioDadosComplAnimal = new RepositorioDadosComplAnimal(this);
            repositorioDadosComplAnimal.inserirDadosComplAnimal(new DadosComplAnimal(
                    Calendar.getInstance(), idAnimal1, 100, 150, 50, 60, 5
            ));
            repositorioDadosComplAnimal.inserirDadosComplAnimal(new DadosComplAnimal(
                    Calendar.getInstance(), idAnimal2, 100, 150, 50, 60, 5
            ));
        }
        idAnimal1 = repoAnimal.buscarAnimal(animal1, idPropriedade1).getId();
        Calendar calendarJunho = Calendar.getInstance();
        calendarJunho.set(2017, Calendar.JUNE, 1);
        Calendar calendarJulho = Calendar.getInstance();
        calendarJulho.set(2017, Calendar.JULY, 1);

        repositorioProducaoDeLeite = new RepositorioProducaoDeLeite(this);
        producaoDeLeite1 = new ProducaoDeLeite(1, calendarJunho, idAnimal1, 99, 99, 99, 99, 99);
        producaoDeLeite2 = new ProducaoDeLeite(2, calendarJulho, idAnimal1, 88, 88, 88, 88, 88);
        repositorioProducaoDeLeite.inserirProducaoDeLeite(producaoDeLeite1);
        repositorioProducaoDeLeite.inserirProducaoDeLeite(producaoDeLeite2);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preparaDados();
        setContentView(R.layout.activity_lista_animais);

        ButterKnife.bind(this);

        listAnimais.setEmptyView(findViewById(android.R.id.empty));
        listAnimais.setOnItemClickListener(this);

        registerForContextMenu(listAnimais);

        propriedade = (Propriedade) getIntent().getSerializableExtra(ListaPropriedadesActivity.EXTRA_PROPRIEDADE);

        if (propriedade == null) {
            carregarListView(new ResultadoPesquisa(0, ""));
        } else {
            inputIdPropriedade.setText(String.valueOf(propriedade.getId()));
            carregarListView(new ResultadoPesquisa(propriedade.getId(), ""));
        }

        spinnerPropriedade.setOnItemSelectedListener(this);
        preencherSpinnerListaPropriedade();

        listAnimais.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

                            carregarListView(new ResultadoPesquisa(0, ""));

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


    @Override
    protected void carregarListView(ResultadoPesquisa resultadoPesquisa) {
        RepositorioAnimal repositorioAnimal = new RepositorioAnimal(ListaAnimaisActivity.this);
        List<Animal> animais;

        if (resultadoPesquisa.getId() == 0)
            animais = repositorioAnimal.buscarTodosAnimais(resultadoPesquisa.getString());
        else
            animais = repositorioAnimal.buscarPorIdentificador(resultadoPesquisa.getId(), resultadoPesquisa.getString());

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

    private void preencherSpinnerListaPropriedade() {

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
            int id = inputIdPropriedade.getText().toString().isEmpty() ? 0 : Integer.parseInt(inputIdPropriedade.getText().toString());

            posicao = (id == 0) ? 0 : spinnerPropriedadeAdapter.getPosition(repositorioPropriedade.buscarPropriedade(id));


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
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {

        if ((parent != null) && (parent.getItemAtPosition(position) instanceof Propriedade)) {
            propriedade = (Propriedade) parent.getItemAtPosition(position);
            inputIdPropriedade.setText(String.valueOf(propriedade.getId()));
            carregarListView(new ResultadoPesquisa(propriedade.getId(), ""));
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        // Método herdado apenas pelo motivo de ser necessáiro pelo uso da
        // interface OnItemSelectedListener
    }
}
