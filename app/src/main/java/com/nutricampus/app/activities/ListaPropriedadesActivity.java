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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nutricampus.app.R;
import com.nutricampus.app.adapters.ListaPropriedadesAdapter;
import com.nutricampus.app.database.RepositorioAnimal;
import com.nutricampus.app.database.RepositorioPropriedade;
import com.nutricampus.app.database.RepositorioProprietario;
import com.nutricampus.app.database.SharedPreferencesManager;
import com.nutricampus.app.entities.Animal;
import com.nutricampus.app.entities.Propriedade;
import com.nutricampus.app.entities.Proprietario;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/*
Explicação para a supressão de warnings:
 - "squid:MaximumInheritanceDepth" = herança extendida em muitos niveis (mais que 5), permitido aqui já
 que refere-se a herança das classes das activities Android
 - "squid:S1172" = erro do sonarqube para os parametros "view" não utilizados
*/
@java.lang.SuppressWarnings({"squid:S1172", "squid:MaximumInheritanceDepth"})
public class ListaPropriedadesActivity extends AbstractListComPesquisa {

    public static final String EXTRA_PROPRIEDADE = "propriedade";

    @BindView(R.id.listaPropriedades)
    ListView listPropriedades;
    @BindView(R.id.text_quantidade_encontrados)
    TextView mensagemQuantidade;
    @BindView(R.id.linha)
    View linha;
    @BindView(R.id.fabList)
    FloatingActionButton fabList;
    @BindView(R.id.fabPropriedade)
    FloatingActionButton fabPropriedade;
    @BindView(R.id.fabProprietario)
    FloatingActionButton fabProprietario;
    @BindView(R.id.layoutPropriedade)
    LinearLayout layoutPropriedade;
    @BindView(R.id.layoutProprietario)
    LinearLayout layoutPropritario;

    SharedPreferencesManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        session = new SharedPreferencesManager(getApplicationContext());
        session.checkLogin();

        setContentView(R.layout.activity_lista_propriedades);
        ButterKnife.bind(this);

        listPropriedades.setEmptyView(findViewById(android.R.id.empty));

        registerForContextMenu(listPropriedades);
        carregarListView();

        listPropriedades.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                abreTelaEditar(position);
            }
        });

        //Animações para o FAB
        final Animation mShowLayout = AnimationUtils.loadAnimation(ListaPropriedadesActivity.this, R.anim.show_layout);
        final Animation mHideLayout = AnimationUtils.loadAnimation(ListaPropriedadesActivity.this, R.anim.hide_layout);

        fabList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (layoutPropriedade.getVisibility() == View.VISIBLE &&
                        layoutPropritario.getVisibility() == View.VISIBLE) {

                    layoutPropriedade.setVisibility(View.GONE);
                    layoutPropritario.setVisibility(View.GONE);
                    layoutPropriedade.startAnimation(mHideLayout);
                    layoutPropritario.startAnimation(mHideLayout);
                } else {
                    layoutPropriedade.setVisibility(View.VISIBLE);
                    layoutPropritario.setVisibility(View.VISIBLE);
                    layoutPropriedade.startAnimation(mShowLayout);
                    layoutPropritario.startAnimation(mShowLayout);
                }
            }
        });

        fabPropriedade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layoutPropriedade.setVisibility(View.GONE);
                layoutPropritario.setVisibility(View.GONE);
                layoutPropriedade.startAnimation(mHideLayout);
                layoutPropritario.startAnimation(mHideLayout);
                Intent intent = new Intent(ListaPropriedadesActivity.this, CadastrarPropriedadeActivity.class);
                startActivity(intent);

                ListaPropriedadesActivity.this.finish();
            }
        });

        fabProprietario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layoutPropriedade.setVisibility(View.GONE);
                layoutPropritario.setVisibility(View.GONE);
                layoutPropriedade.startAnimation(mHideLayout);
                layoutPropritario.startAnimation(mHideLayout);
                Intent intent = new Intent(ListaPropriedadesActivity.this, ListaProprietariosActivity.class);
                startActivity(intent);
                ListaPropriedadesActivity.this.finish();
            }
        });
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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_contexto_propriedade, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.menu_opc_cont_adicionar_animal:
                if (info != null)
                    abreTelaAnimal(info.position, CadastrarAnimalActivity.class);
                return true;
            case R.id.menu_opc_cont_visualizar_animais:
                if (info != null)
                    abreTelaAnimal(info.position, ListaAnimaisActivity.class);
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
        if (isSearchOpened) {
            gerenciaFuncaoPesquisar();
            return;
        }
        super.onBackPressed();
    }

    private void confirmarExcluir(final Propriedade propriedade) {
        new AlertDialog.Builder(this)
                .setMessage(getString(R.string.msg_excluir_confirmar_propriedade, "\"" + propriedade.getNome() + "\""))
                .setCancelable(false)
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        RepositorioProprietario repositorioProprietario = new RepositorioProprietario(ListaPropriedadesActivity.this);
                        RepositorioPropriedade repositorioPropriedade = new RepositorioPropriedade(ListaPropriedadesActivity.this);
                        RepositorioAnimal repositorioAnimal = new RepositorioAnimal(ListaPropriedadesActivity.this);

                        List<Animal> listAnimal = repositorioAnimal.buscarPorPropridade(propriedade.getId());
                        for (Animal a : listAnimal)
                            repositorioAnimal.removerAnimal(a);

                        Proprietario proprietario = repositorioProprietario.buscarProprietario(propriedade.getIdProprietario());


                        boolean isDeleteProprietario = repositorioPropriedade.
                                PropriedadesOfProprietario(proprietario.getId()).size() > 1;

                        int result = repositorioPropriedade.removerPropriedade(propriedade);

                        if (result > 0) {
                            Toast.makeText(ListaPropriedadesActivity.this,
                                    getString(R.string.msg_excluir_propriedade_sucesso), Toast.LENGTH_LONG).show();

                            /*Excluirá o proprietário da propriedade por ultimo excluída,
                            caso ele não possua mais nenhuma propriedade*/
                            if (!isDeleteProprietario) {
                                repositorioProprietario.removerProprietario(proprietario);
                            }

                            carregarListView();
                        } else {
                            Toast.makeText(ListaPropriedadesActivity.this,
                                    getString(R.string.msg_excluir_propriedade_falha), Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .setNegativeButton("Não", null)
                .show();
    }

    private void carregarListView() {
        carregarListView(new ResultadoPesquisa(0, ""));
    }

    @Override
    protected void carregarListView(ResultadoPesquisa resultadoPesquisa) {
        List<Propriedade> lista = this.buscarPropriedades(resultadoPesquisa.getString());

        ListaPropriedadesAdapter adapter =
                new ListaPropriedadesAdapter(lista, this);

        listPropriedades.setAdapter(adapter);

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

    public List<Propriedade> buscarPropriedades(String nome) {
        int id = session.getIdUsuario().equals("") ? 0 : Integer.parseInt(session.getIdUsuario());
        RepositorioPropriedade repositorioPropriedade = new RepositorioPropriedade(getBaseContext());
        return repositorioPropriedade.buscarPropriedadesPorNome(nome, id);
    }

    private Intent getIntent(Propriedade propriedade) {
        Intent intent = new Intent(this, EditarPropriedadeActivity.class);
        intent.putExtra("id", propriedade.getId());
        intent.putExtra("nome", propriedade.getNome());
        intent.putExtra("telefone", propriedade.getTelefone());
        intent.putExtra("rua", propriedade.getLogradouro());
        intent.putExtra("bairro", propriedade.getBairro());
        intent.putExtra("numero", propriedade.getNumero());
        intent.putExtra("cep", propriedade.getCep());
        intent.putExtra("cidade", propriedade.getCidade());
        intent.putExtra("estado", propriedade.getEstado());
        intent.putExtra("idProprietario", propriedade.getIdProprietario());
        return intent;
    }

    private void abreTelaEditar(int posicao) {
        Propriedade item = (Propriedade) listPropriedades.getItemAtPosition(posicao);
        startActivity(getIntent(item));
    }

    private void abreTelaAnimal(int posicao, Class activity) {
        Propriedade propriedade = (Propriedade) listPropriedades.getItemAtPosition(posicao);
        Intent intent = new Intent(ListaPropriedadesActivity.this, activity);
        intent.putExtra(EXTRA_PROPRIEDADE, propriedade);
        startActivity(intent);
        this.finish();
    }
}
