package com.nutricampus.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.nutricampus.app.R;
import com.nutricampus.app.database.RepositorioPropriedade;
import com.nutricampus.app.entities.Propriedade;
import com.nutricampus.app.model.AdapterListaPropriedades;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListaPropriedadesActivity extends AppCompatActivity {

    @BindView(R.id.listaPropriedades) ListView listPropriedades;
    @BindView(R.id.text_quantidade_encontrados)
    TextView mensagemQuantidade;
    @BindView(R.id.linha)
    View linha;
    @BindView(R.id.input_pesquisar_propriedade)
    EditText inputPesquisaPropriedade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_propriedades);
        ButterKnife.bind(this);

        carregaListView();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_crud, menu);
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
            case R.id.menu_opc_excluir:

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    public void atualizaListaPropriedades(View view){
        carregaListView();
    }

    private void carregaListView() {
        List<Propriedade> lista = buscarPropriedades();

        AdapterListaPropriedades adapter =
                new AdapterListaPropriedades(buscarPropriedades(), this);

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





}
