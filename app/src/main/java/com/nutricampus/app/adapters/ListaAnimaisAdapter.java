package com.nutricampus.app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nutricampus.app.R;
import com.nutricampus.app.entities.Animal;

import java.util.List;

/**
 * Created by Felipe on 19/07/2017.
 */

public class ListaAnimaisAdapter extends BaseAdapter {

    Context context;
    List<Animal> animais;

    public ListaAnimaisAdapter(Context context, List<Animal> animais) {
        this.context = context;
        this.animais = animais;
    }


    @Override
    public int getCount() {
        return animais.size();
    }

    @Override
    public Object getItem(int i) {
        return animais.get(i);
    }

    @Override
    public long getItemId(int i) {
        return animais.get(i).getId();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        final View linha = LayoutInflater.from(context).inflate(R.layout.item_lista_animal, null);
        final Animal animal = animais.get(position);

        TextView id = linha.findViewById(R.id.lista_animal_id);
        TextView nome = linha.findViewWithTag(R.id.lista_animal_nome);
        TextView genero = linha.findViewById(R.id.lista_animal_genero);
        TextView peso = linha.findViewById(R.id.lista_animal_peso);

        id.setText(animal.getId());
        /*nome.setText(animal.getNome);
        genero.setText(animal.getGenero);
        peso.setText(animal.getPeso);*/

        return linha;

    }
}