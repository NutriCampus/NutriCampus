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
import android.widget.TextView;
import android.widget.Toast;

import com.nutricampus.app.R;
import com.nutricampus.app.adapters.ListaGrupoAdapter;
import com.nutricampus.app.database.RepositorioGrupo;
import com.nutricampus.app.database.SharedPreferencesManager;
import com.nutricampus.app.entities.Grupo;

import java.util.ArrayList;
import java.util.List;


/*
Explicação para a supressão de warnings:
 - "squid:MaximumInheritanceDepth" = herança extendida em muitos niveis (mais que 5), permitido aqui já
 que refere-se a herança das classes das activities Android
 - "squid:S1172" = erro do sonarqube para os parametros "view" não utilizados
*/
@SuppressWarnings({"squid:S1172", "squid:MaximumInheritanceDepth"})
public class ListaGrupoActivity extends AppCompatActivity {

    private ListView listViewGrupo;

    private List<Grupo> listaDeGrupos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_grupo);

        listViewGrupo = (ListView) findViewById(R.id.listaGrupo);

        atualizaLista();

        registerForContextMenu(listViewGrupo);

        listViewGrupo.setEmptyView(findViewById(android.R.id.empty));

        listViewGrupo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                abrirTelaEditar(i);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.btn_add_grupo);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ListaGrupoActivity.this, CadastrarGrupoActivity.class);
                startActivity(intent);
                //ListaGrupoActivity.this.finish();

            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();
        this.atualizaLista();
    }

    private void atualizaLista() {
        RepositorioGrupo repositorioGrupo = new RepositorioGrupo(ListaGrupoActivity.this);

        if (listaDeGrupos == null)
            listaDeGrupos = new ArrayList<>();

        int idUsuario = Integer.parseInt(new SharedPreferencesManager(this).getIdUsuario());

        List<Grupo> list = repositorioGrupo.buscarPorUsuario(idUsuario);
        if (list.isEmpty()) {
          repositorioGrupo.inserirGrupo(new Grupo("Pastando", "", idUsuario));
          repositorioGrupo.inserirGrupo(new Grupo("Lactação", "", idUsuario));
          repositorioGrupo.inserirGrupo(new Grupo("Cio", "", idUsuario));
          repositorioGrupo.inserirGrupo(new Grupo("Gestante", "", idUsuario));
        }

        listaDeGrupos = repositorioGrupo.buscarPorUsuario(idUsuario);

        ListaGrupoAdapter adapter = new ListaGrupoAdapter(this, listaDeGrupos);
        listViewGrupo.setAdapter(adapter);


        TextView mensagemQuantidade = (TextView) findViewById(R.id.text_quantidade_encontrados);
        View linha = findViewById(R.id.linha);

        mensagemQuantidade.setText(getResources().getQuantityString(
                R.plurals.msg_registros_encontrados,
                adapter.getCount(),
                adapter.getCount()));

        if (listaDeGrupos.isEmpty())
            linha.setVisibility(View.GONE);
        else
            linha.setVisibility(View.VISIBLE);
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
                    abrirTelaEditar(info.position);
                return true;
            case R.id.menu_opc_cont_excluir:
                confirmarExcluir((Grupo) listViewGrupo.getItemAtPosition(info.position));
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void excluirRegistro(Grupo objeto) {
        RepositorioGrupo repositorioGrupo = new RepositorioGrupo(ListaGrupoActivity.this);

        int result = repositorioGrupo.removerGrupo(objeto);

        if (result > 0) {
            Toast.makeText(ListaGrupoActivity.this,
                    getString(R.string.msg_sucesso_remover_registro), Toast.LENGTH_LONG).show();

            this.atualizaLista();
        } else {
            Toast.makeText(ListaGrupoActivity.this,
                    getString(R.string.msg_erro_atualizar_registro), Toast.LENGTH_LONG).show();
        }
    }

    private void confirmarExcluir(final Grupo objeto) {
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

    private void abrirTelaEditar(int i) {
        Grupo grupo = (Grupo) listViewGrupo.getItemAtPosition(i);
        Intent intent = new Intent(ListaGrupoActivity.this, EditarGrupoActivity.class);
        intent.putExtra("grupo", grupo);
        startActivity(intent);
    }


}
