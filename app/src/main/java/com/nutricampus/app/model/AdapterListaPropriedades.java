package com.nutricampus.app.model;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nutricampus.app.R;
import com.nutricampus.app.entities.Propriedade;

import java.util.List;

/**
 * Created by kellison on 05/07/17.
 */

public class AdapterListaPropriedades extends BaseAdapter {

    private final List<Propriedade> propriedades;
    private final Activity act;

    public AdapterListaPropriedades(List<Propriedade> propriedades, Activity act) {
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
    public View getView(int indice, View convertView, ViewGroup viewGroup) {
        View view = act.getLayoutInflater()
                .inflate(R.layout.lista_propriedade_personalizada, viewGroup, false);
        Propriedade propriedade = propriedades.get(indice);

        //pegando as referências das Views
        TextView id = (TextView)
                view.findViewById(R.id.lista_propriedade_id);
        TextView nome = (TextView)
                view.findViewById(R.id.lista_propriedade_nome);
        TextView cidade = (TextView)
                view.findViewById(R.id.lista_propriedade_cidade);
        TextView estado = (TextView)
                view.findViewById(R.id.lista_propriedade_estado);

        //populando as Views
        id.setText(String.valueOf(propriedade.getId()));
        nome.setText(propriedade.getNome());
        cidade.setText(propriedade.getCidade());
        estado.setText(propriedade.getEstado());

        return view;
    }
}
