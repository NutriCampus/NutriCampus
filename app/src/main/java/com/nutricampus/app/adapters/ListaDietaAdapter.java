package com.nutricampus.app.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nutricampus.app.R;
import com.nutricampus.app.entities.CompostosAlimentares;
import com.nutricampus.app.entities.Dieta;

import java.util.List;


public class ListaDietaAdapter extends BaseAdapter {

    private final List<Dieta> dietas;
    private final Activity act;

    public ListaDietaAdapter(List<Dieta> dietas, Activity act) {
        this.dietas = dietas;
        this.act = act;
    }

    @Override
    public int getCount() {
        return dietas.size();
    }

    @Override
    public Object getItem(int indice) {
        return dietas.get(indice);
    }

    @Override
    public long getItemId(int indice) {
        return 0;
    }

    @Override
    public View getView(int indice, final View convertView, ViewGroup viewGroup) {
        final View view = act.getLayoutInflater()
                .inflate(R.layout.lista_dieta_personalizada, viewGroup, false);
        final Dieta dieta = this.dietas.get(indice);

        //pegando as referências das Views
        TextView id = (TextView) view.findViewById(R.id.lista_dieta_id);
        TextView nome = (TextView) view.findViewById(R.id.lista_dieta_nome);
        TextView propriedade = (TextView) view.findViewById(R.id.lista_dieta_propriedade);
        TextView pb = (TextView) view.findViewById(R.id.lista_dieta_pb);

        //populando as Views
        id.setText(String.valueOf(dieta.id));
        nome.setText(dieta.identificador);
        propriedade.setText(dieta.propriedade.getNome());
        pb.setText(String.valueOf(dieta.proteinaBruta) + "%");

        return view;
    }

}
