package com.nutricampus.app.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.nutricampus.app.R;
import com.nutricampus.app.adapters.ListaDadosComplAdapter;
import com.nutricampus.app.database.RepositorioDadosComplAnimal;
import com.nutricampus.app.entities.Animal;
import com.nutricampus.app.entities.DadosComplAnimal;

import java.util.ArrayList;
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
public class ListaDadosComplActivity extends AppCompatActivity {

    public static final String EXTRA_ANIMAL = "animal";
    public static final String EXTRA_DADOS_COMPL = "dadosCompl";

    @BindView(R.id.listDadosCompl)
    ListView listDadosCompl;

    private Animal animal;
    private List<DadosComplAnimal> listDados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_dados_compl);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        animal = (Animal) intent.getSerializableExtra(EXTRA_ANIMAL);

        atualizaLista();

        registerForContextMenu(listDadosCompl);

        listDadosCompl.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                abreTelaEditar(i);
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
    protected void onResume() {
        super.onResume();
        this.atualizaLista();
    }

    private void atualizaLista() {
        RepositorioDadosComplAnimal repositorioDadosComplAnimal = new RepositorioDadosComplAnimal(ListaDadosComplActivity.this);

        if (listDados == null)
            listDados = new ArrayList<>();

        listDados = repositorioDadosComplAnimal.buscarTodosDadosCompl(animal.getId());

        ListaDadosComplAdapter adapter = new ListaDadosComplAdapter(this, listDados);
        listDadosCompl.setAdapter(adapter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_contexto_crud, menu);
        menu.getItem(0).setVisible(false);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.menu_opc_cont_editar:
                if (info != null)
                    abreTelaEditar(info.position);
                return true;
            case R.id.menu_opc_cont_excluir:
                confirmarExcluir((DadosComplAnimal) listDadosCompl.getItemAtPosition(info.position));
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void excluirRegistro(DadosComplAnimal objeto) {
        RepositorioDadosComplAnimal repositorio = new RepositorioDadosComplAnimal(ListaDadosComplActivity.this);

        int result = repositorio.removerDadosCompl(objeto);

        if (result > 0) {
            Toast.makeText(ListaDadosComplActivity.this,
                    getString(R.string.msg_sucesso_remover_registro), Toast.LENGTH_LONG).show();

            this.atualizaLista();
        } else {
            Toast.makeText(ListaDadosComplActivity.this,
                    getString(R.string.msg_erro_atualizar_registro), Toast.LENGTH_LONG).show();
        }
    }

    private void confirmarExcluir(final DadosComplAnimal objeto) {
        new AlertDialog.Builder(this)
                .setMessage(getString(R.string.msg_excluir_confirmar) + " ?")
                .setCancelable(false)
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        excluirRegistro(objeto);
                    }
                })
                .setNegativeButton("Não", null)
                .show();
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

    private void abreTelaEditar(int posicao) {
        DadosComplAnimal dadosComplAnimal = (DadosComplAnimal) listDadosCompl.getItemAtPosition(posicao);
        Intent intent = new Intent(ListaDadosComplActivity.this, EditarDadosComplActivity.class);
        intent.putExtra(EXTRA_ANIMAL, animal);
        intent.putExtra(EXTRA_DADOS_COMPL, dadosComplAnimal);
        startActivity(intent);
    }
}
