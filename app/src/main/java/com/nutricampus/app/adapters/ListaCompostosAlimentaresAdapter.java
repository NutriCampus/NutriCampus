package com.nutricampus.app.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nutricampus.app.R;
import com.nutricampus.app.entities.CompostosAlimentares;

import java.util.List;


public class ListaCompostosAlimentaresAdapter extends BaseAdapter {

    private final List<CompostosAlimentares> compostosAlimentares;
    private final Activity act;

    public ListaCompostosAlimentaresAdapter(List<CompostosAlimentares> compostosAlimentares, Activity act) {
        this.compostosAlimentares = compostosAlimentares;
        this.act = act;
    }

    @Override
    public int getCount() {
        return compostosAlimentares.size();
    }

    @Override
    public Object getItem(int indice) {
        return compostosAlimentares.get(indice);
    }

    @Override
    public long getItemId(int indice) {
        return 0;
    }

    @Override
    public View getView(int indice, final View convertView, ViewGroup viewGroup) {
        final View view = act.getLayoutInflater()
                .inflate(R.layout.lista_composto_personalizada, viewGroup, false);
        final CompostosAlimentares compostos = this.compostosAlimentares.get(indice);

        //pegando as referências das Views
        TextView id = (TextView) view.findViewById(R.id.lista_composto_id);
        TextView nome = (TextView) view.findViewById(R.id.lista_composto_nome);
        TextView tipo = (TextView) view.findViewById(R.id.lista_composto_tipo);
        TextView descricao = (TextView) view.findViewById(R.id.lista_composto_descricao);

        //populando as Views
        id.setText(String.valueOf(compostos.getId()));
        nome.setText(compostos.getIdentificador());
        tipo.setText(compostos.getTipo());
        descricao.setText(compostos.getDescricao());

        return view;
    }

}
