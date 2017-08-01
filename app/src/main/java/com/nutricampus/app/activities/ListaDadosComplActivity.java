package com.nutricampus.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.nutricampus.app.R;
import com.nutricampus.app.adapters.ListaDadosComplAdapter;
import com.nutricampus.app.database.RepositorioDadosComplAnimal;
import com.nutricampus.app.entities.Animal;
import com.nutricampus.app.entities.DadosComplAnimal;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/*
Explicação para a supressão de warnings:
 - "squid:MaximumInheritanceDepth" = herança extendida em muitos niveis (mais que 5), permitido aqui já
 que refere-se a herança das classes das activities Android
 - "squid:S1172" = erro do sonarqube para os parametros "view" não utilizados
*/
@java.lang.SuppressWarnings({"squid:S1172", "squid:MaximumInheritanceDepth"})
public class ListaDadosComplActivity extends AppCompatActivity {

    public static final String EXTRA_ANIMAL = "animal";
    public static final String EXTRA_DADOS_COMPL = "dadosCompl";

    @BindView(R.id.listDadosCompl)
    ListView listDadosCompl;

    DadosComplAnimal dadosComplAnimal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_dados_compl);

        ButterKnife.bind(this);

        RepositorioDadosComplAnimal repositorioDadosComplAnimal = new RepositorioDadosComplAnimal(ListaDadosComplActivity.this);
        Intent intent = getIntent();
        final Animal animal = (Animal) intent.getSerializableExtra(EXTRA_ANIMAL);

        ArrayList<DadosComplAnimal> listDados = (ArrayList<DadosComplAnimal>)
                repositorioDadosComplAnimal.buscarTodosDadosCompl(animal.getId());

        registerForContextMenu(listDadosCompl);
        ListaDadosComplAdapter adapter =
                new ListaDadosComplAdapter(this, listDados);

        listDadosCompl.setAdapter(adapter);

        listDadosCompl.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                dadosComplAnimal = (DadosComplAnimal) listDadosCompl.getItemAtPosition(i);
                Intent intent = new Intent(ListaDadosComplActivity.this, EditarDadosComplActivity.class);
                intent.putExtra(EXTRA_ANIMAL, animal);
                intent.putExtra(EXTRA_DADOS_COMPL, dadosComplAnimal);
                startActivity(intent);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.btn_add_dados_compl);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ListaDadosComplActivity.this, CadastrarNovoDadoComplActivity.class);
                intent.putExtra(EXTRA_ANIMAL, animal);
                startActivity(intent);
                ListaDadosComplActivity.this.finish();

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            Intent it = new Intent(ListaDadosComplActivity.this, ListaAnimaisActivity.class);
            startActivity(it);
            finish();
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        Intent it = new Intent(ListaDadosComplActivity.this, ListaAnimaisActivity.class);
        startActivity(it);
        finish();
    }
}
