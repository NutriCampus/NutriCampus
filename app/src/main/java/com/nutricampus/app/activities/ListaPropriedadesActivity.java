package com.nutricampus.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.nutricampus.app.R;
import com.nutricampus.app.database.RepositorioPropriedade;
import com.nutricampus.app.entities.Propriedade;
import com.nutricampus.app.entities.Proprietario;
import com.nutricampus.app.model.AdapterListaPropriedades;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListaPropriedadesActivity extends AppCompatActivity {

    @BindView(R.id.listaPropriedades) ListView listPropriedades;
    @BindView(R.id.input_pesquisar_propriedade)
    EditText inputPesquisaPropriedade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_propriedades);
        ButterKnife.bind(this);

        AdapterListaPropriedades adapter =
                new AdapterListaPropriedades(buscarPropriedades(), this);

        listPropriedades.setAdapter(adapter);

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

        AdapterListaPropriedades adapter =
                new AdapterListaPropriedades(buscarPropriedades(), this);

        listPropriedades.setAdapter(adapter);
    }


    public List<Propriedade> buscarPropriedades(){
        String nome = inputPesquisaPropriedade.getText().toString();
        RepositorioPropriedade repositorioPropriedade = new RepositorioPropriedade(getBaseContext());
        List<Propriedade> propriedades = repositorioPropriedade.buscarPropriedadesPorNome(nome);
        return propriedades;

    }





}
