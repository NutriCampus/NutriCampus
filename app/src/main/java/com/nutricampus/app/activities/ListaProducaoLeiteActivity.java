package com.nutricampus.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.nutricampus.app.R;
import com.nutricampus.app.adapters.ListaProducaoAdapter;
import com.nutricampus.app.database.RepositorioProducaoDeLeite;
import com.nutricampus.app.entities.ProducaoDeLeite;
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
public class ListaProducaoLeiteActivity extends AbstractListagem {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_lista_producao);
        listView = (ListView) findViewById(R.id.lista_producao);
        cadastro = CadastroProducaoLeiteActivity.class;
        ButterKnife.bind(this);

        registerForContextMenu(listView);
        carregaListView();

        listView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listView.showContextMenuForChild(view);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.btn_add_producao);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListaProducaoLeiteActivity.this, CadastroProducaoLeiteActivity.class);
                intent.putExtra("idAnimal", idAnimal);
                startActivity(intent);

            }
        });
    }

    @Override
    protected void carregaListView(int mes, int ano) {

        RepositorioProducaoDeLeite repositorio = new RepositorioProducaoDeLeite(getBaseContext());
        List<ProducaoDeLeite> lista;

        // Se o mes escolhido fro TODOS (dezembro equivale a 11)
        if (mes == 12)
            lista = repositorio.buscarPorAnimal(idAnimal);
        else
            lista = repositorio.buscarPorAnimalPeriodo(idAnimal, mes, ano);

        ListaProducaoAdapter adapter = new ListaProducaoAdapter(lista, this);

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
        RepositorioProducaoDeLeite repositorio = new RepositorioProducaoDeLeite(ListaProducaoLeiteActivity.this);

        int result = repositorio.removerProducaoDeLeite((ProducaoDeLeite) objeto);

        if (result > 0) {
            Toast.makeText(ListaProducaoLeiteActivity.this,
                    getString(R.string.msg_sucesso_remover_registro), Toast.LENGTH_LONG).show();

            filtrarRegistros(new View(ListaProducaoLeiteActivity.this));
        } else {
            Toast.makeText(ListaProducaoLeiteActivity.this,
                    getString(R.string.msg_erro_deletar_registro), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected Intent getIntent(Object objeto) {
        ProducaoDeLeite producaoDeLeite = (ProducaoDeLeite) objeto;
        Intent intent = new Intent(this, EditarProducaoLeiteActivity.class);
        intent.putExtra("id", String.valueOf(producaoDeLeite.getId()));
        intent.putExtra("data", Conversor.dataFormatada(producaoDeLeite.getData()));
        intent.putExtra("quantidade", String.valueOf(producaoDeLeite.getQntProduzida()));
        intent.putExtra("lactose", String.valueOf(producaoDeLeite.getPctLactose()));
        intent.putExtra("proteinaBruta", String.valueOf(producaoDeLeite.getPctProteinaBruta()));
        intent.putExtra("proteinaVerdadeira", String.valueOf(producaoDeLeite.getPctProteinaVerdadeira()));
        intent.putExtra("gordura", String.valueOf(producaoDeLeite.getGordura()));

        intent.putExtra("idAnimal", producaoDeLeite.getAnimal());
        return intent;
    }

}
