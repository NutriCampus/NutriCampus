package com.nutricampus.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.nutricampus.app.R;
import com.nutricampus.app.adapters.ListaProleAdapter;
import com.nutricampus.app.database.RepositorioProle;
import com.nutricampus.app.entities.Prole;
import com.nutricampus.app.utils.Conversor;

import java.util.List;

import butterknife.ButterKnife;

/*
Explicação para a supressão de warnings:
 - "squid:MaximumInheritanceDepth" = herança extendida em muitos niveis (mais que 5), permitido aqui já
 que refere-se a herança das classes das activities Android
 - "squid:S1172" = erro do sonarqube para os parametros "view" não utilizados
*/
@java.lang.SuppressWarnings({"squid:S1172", "squid:MaximumInheritanceDepth"})
public class ListaProleActivity extends AbstractListagem {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_lista_prole);

        listView = (ListView) findViewById(R.id.lista_prole);
        cadastro = CadastroProleActivity.class;

        ButterKnife.bind(this);

        registerForContextMenu(listView);
        carregaListView();

        listView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listView.showContextMenuForChild(view);
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
    protected void carregaListView(int mes, int ano) {

        RepositorioProle repositorio = new RepositorioProle(getBaseContext());
        List<Prole> lista;

        // Se o mes escolhido fro TODOS (dezembro equivale a 11)
        if (mes == 12)
            lista = repositorio.buscarPorMatriz(this.idAnimal);
        else
            lista = repositorio.buscarPorAnimalPeriodo(idAnimal, mes, ano);

        ListaProleAdapter adapter = new ListaProleAdapter(lista, this);

        listView.setAdapter(adapter);

        mensagemQuantidade.setText(getResources().getQuantityString(
                R.plurals.msg_registros_encontrados,
                adapter.getCount(),
                adapter.getCount()));

        if (lista.isEmpty())
            linha.setVisibility(View.GONE);
        else
            linha.setVisibility(View.VISIBLE);

    }

    @Override
    protected void excluirRegistro(Object objeto) {
        RepositorioProle repositorio = new RepositorioProle(ListaProleActivity.this);

        int result = repositorio.removerProle((Prole) objeto);

        if (result > 0) {
            Toast.makeText(ListaProleActivity.this,
                    getString(R.string.msg_sucesso_remover_registro), Toast.LENGTH_LONG).show();

            filtrarRegistros(new View(ListaProleActivity.this));
        } else {
            Toast.makeText(ListaProleActivity.this,
                    getString(R.string.msg_erro_deletar_registro), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected Intent getIntent(Object objeto) {
        Prole prole = (Prole) objeto;
        Intent intent = new Intent(this, EditarProleActivity.class);
        intent.putExtra("id", String.valueOf(prole.getId()));
        intent.putExtra("data", Conversor.dataFormatada(prole.getDataDeNascimento()));
        intent.putExtra("peso", String.valueOf(prole.getPesoDeNascimento()));
        intent.putExtra("isNatimorto", prole.isNatimorto());
        intent.putExtra("idAnimalMatriz", prole.getMatriz());

        return intent;
    }

}
