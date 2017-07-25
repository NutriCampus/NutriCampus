package com.nutricampus.app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nutricampus.app.R;
import com.nutricampus.app.database.RepositorioPropriedade;
import com.nutricampus.app.entities.Animal;
import com.nutricampus.app.entities.Propriedade;

import java.util.List;

/**
 * Created by Felipe on 19/07/2017.
 * For project NutriCampus.
 * Contact: <felipeguimaraes540@gmail.com>
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

        TextView nome = linha.findViewById(R.id.lista_animal_nome);
        TextView ativo = linha.findViewById(R.id.lista_animal_ativo);
        TextView propriedade = linha.findViewById(R.id.lista_animal_propriedade);

        nome.setText(animal.getIndentificador());

        if (animal.isAtivo())
            ativo.setText("Ativo");
        else
            ativo.setText("Inativo");

        RepositorioPropriedade repositorioPropriedade = new RepositorioPropriedade(context);
        Propriedade p = repositorioPropriedade.buscarPropriedade(animal.getId_propriedade());
        propriedade.setText(p.getNome());

        return linha;

    }
}
