package com.nutricampus.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import com.nutricampus.app.R;
import com.nutricampus.app.entities.Propriedade;
import com.nutricampus.app.model.AdapterListaPropriedades;

import java.util.ArrayList;
import java.util.List;

public class ListaPropriedadesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_propriedades);

        ListView lista = (ListView) findViewById(R.id.listaPropriedades);


        AdapterListaPropriedades adapter =
                new AdapterListaPropriedades(listaPropriedades(), this);

        lista.setAdapter(adapter);
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

    private List<Propriedade> listaPropriedades(){
        ArrayList<Propriedade> l = new ArrayList<>();
        Propriedade p = new Propriedade(1,"Chacara Paraiso","","","","","Garanhuns","Pernambuco","");
        l.add(p);
        p = new Propriedade(2,"Fazendo Bela Vida","","","","","Canhotinho","Pernambuco","");
        l.add(p);

        return l;
    }



}
