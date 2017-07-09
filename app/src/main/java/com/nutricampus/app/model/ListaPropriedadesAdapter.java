package com.nutricampus.app.model;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nutricampus.app.R;
import com.nutricampus.app.activities.EditarPropriedadeActivity;
import com.nutricampus.app.activities.ListaPropriedadesActivity;
import com.nutricampus.app.entities.Propriedade;

import java.util.List;

/**
 * Created by kellison on 05/07/17.
 */

public class ListaPropriedadesAdapter extends BaseAdapter {

    private final List<Propriedade> propriedades;
    private final Activity act;

    public ListaPropriedadesAdapter(List<Propriedade> propriedades, Activity act) {
        this.propriedades = propriedades;
        this.act = act;
    }

    @Override
    public int getCount() {
        return propriedades.size();
    }

    @Override
    public Object getItem(int indice) {
        return propriedades.get(indice);
    }

    @Override
    public long getItemId(int indice) {
        return 0;
    }

    @Override
    public View getView(int indice, final View convertView, ViewGroup viewGroup) {
        final View view = act.getLayoutInflater()
                .inflate(R.layout.lista_propriedade_personalizada, viewGroup, false);
        final Propriedade propriedade = propriedades.get(indice);

        //pegando as referências das Views
        TextView id = view.findViewById(R.id.lista_propriedade_id);
        TextView nome = view.findViewById(R.id.lista_propriedade_nome);
        TextView cidade = view.findViewById(R.id.lista_propriedade_cidade);
        TextView estado = view.findViewById(R.id.lista_propriedade_estado);

        //populando as Views
        id.setText(String.valueOf(propriedade.getId()));
        nome.setText(propriedade.getNome());
        cidade.setText(propriedade.getCidade());
        estado.setText(propriedade.getEstado());

        /*view.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View v) {
                act.startActivity(getIntent(propriedade));
            }
        });*/

        //act.registerForContextMenu(view);

        return view;
    }

    public Intent getIntent(Propriedade propriedade){
        Intent intent = new Intent(act, EditarPropriedadeActivity.class);
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
