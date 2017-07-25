package com.nutricampus.app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nutricampus.app.R;
import com.nutricampus.app.entities.DadosComplAnimal;
import com.nutricampus.app.utils.Conversor;

import java.util.List;

/**
 * Created by Felipe on 21/07/2017.
 * For project NutriCampus.
 * Contact: <felipeguimaraes540@gmail.com>
 */

public class ListaDadosComplAdapter extends BaseAdapter {

    Context context;
    List<DadosComplAnimal> dados;

    public ListaDadosComplAdapter(Context context, List<DadosComplAnimal> dados) {
        this.context = context;
        this.dados = dados;
    }

    @Override
    public int getCount() {
        return dados.size();
    }

    @Override
    public Object getItem(int i) {
        return dados.get(i);
    }

    @Override
    public long getItemId(int i) {
        return dados.get(i).getId();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        final View linha = LayoutInflater.from(context).inflate(R.layout.item_lista_dados_compl, null);
        final DadosComplAnimal dadosComplAnimal = dados.get(position);

<<<<<<< HEAD
        TextView id = (TextView) linha.findViewById(R.id.lista_dados_id);
        TextView data = (TextView) linha.findViewById(R.id.lista_dados_data);
=======
        TextView id = linha.findViewById(R.id.lista_dados_id);
        TextView data = linha.findViewById(R.id.lista_dados_data);
>>>>>>> origin/pmateus

        id.setText(String.valueOf(dadosComplAnimal.getId()));
        data.setText(Conversor.dataFormatada(dadosComplAnimal.getData()));

        return linha;
    }
}
