package com.nutricampus.app.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nutricampus.app.R;
import com.nutricampus.app.database.RepositorioPropriedade;
import com.nutricampus.app.database.RepositorioProprietario;
import com.nutricampus.app.entities.Propriedade;
import com.nutricampus.app.entities.Proprietario;
import com.nutricampus.app.model.ListaPropriedadesAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListaPropriedadesActivity extends AppCompatActivity{

    @BindView(R.id.listaPropriedades) ListView listPropriedades;
    @BindView(R.id.text_quantidade_encontrados)
    TextView mensagemQuantidade;
    @BindView(R.id.linha)
    View linha;
    @BindView(R.id.input_pesquisar_propriedade)
    EditText inputPesquisaPropriedade;

    public  static int quantidadeItemSelecionados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_lista_propriedades);
        ButterKnife.bind(this);
        registerForContextMenu(listPropriedades);
        carregaListView();

        listPropriedades.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                abreTelaEditar(position);
            }
        });
    }

    private void abreTelaEditar(int posicao){
        Propriedade item = (Propriedade) listPropriedades.getItemAtPosition(posicao);
        startActivity(getIntent(item));
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

    private void confirmarExcluir(final Propriedade propriedade){
        new AlertDialog.Builder(this)
                .setMessage(getString(R.string.msg_excluir_confirmar) + " a \"" + propriedade.getNome() + "\" ?" )
                .setCancelable(false)
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        RepositorioProprietario repositorioProprietario = new RepositorioProprietario(getBaseContext());
                        RepositorioPropriedade repositorioPropriedade = new RepositorioPropriedade(getBaseContext());

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

                            carregaListView();
                        }
                        else{
                            Toast.makeText(ListaPropriedadesActivity.this,
                                    getString(R.string.msg_excluir_propriedade_sucesso), Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .setNegativeButton("Não", null)
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_crud, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_opc_adicionar:
                Intent intent = new Intent(this, CadastrarPropriedadeActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public  static void count(){
        quantidadeItemSelecionados++;
        Log.i("QuantItemSelec", String.valueOf(quantidadeItemSelecionados));
    }

    public void atualizaListaPropriedades(View view){
        carregaListView();
    }

    private void carregaListView() {
        List<Propriedade> lista = buscarPropriedades();

        ListaPropriedadesAdapter adapter =
                new ListaPropriedadesAdapter(buscarPropriedades(), this);

        listPropriedades.setAdapter(adapter);

        mensagemQuantidade.setText(lista.size() + " " + getString(R.string.campo_texto_lista_encontrados));

        if (lista.size() == 0) {
            mensagemQuantidade.setVisibility(View.VISIBLE);
            linha.setVisibility(View.GONE);
        } else{
            mensagemQuantidade.setVisibility(View.VISIBLE);
            linha.setVisibility(View.VISIBLE);
        }

    }


    public List<Propriedade> buscarPropriedades(){
        String nome = inputPesquisaPropriedade.getText().toString();
        RepositorioPropriedade repositorioPropriedade = new RepositorioPropriedade(getBaseContext());
        List<Propriedade> propriedades = repositorioPropriedade.buscarPropriedadesPorNome(nome);
        return propriedades;

    }

    public Intent getIntent(Propriedade propriedade){
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
}
