package com.nutricampus.app.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nutricampus.app.R;
import com.nutricampus.app.entities.Prole;
import com.nutricampus.app.utils.Conversor;

import java.util.List;


public class ListaProleAdapter extends BaseAdapter {

    private final List<Prole> prole;
    private final Activity act;

    public ListaProleAdapter(List<Prole> prole, Activity act) {
        this.prole = prole;
        this.act = act;
    }

    @Override
    public int getCount() {
        return prole.size();
    }

    @Override
    public Object getItem(int indice) {
        return prole.get(indice);
    }

    @Override
    public long getItemId(int indice) {
        return 0;
    }

    @Override
    public View getView(int indice, final View convertView, ViewGroup viewGroup) {
        final View view = act.getLayoutInflater()
                .inflate(R.layout.lista_prole_personalizada, viewGroup, false);
        final Prole itemProle = this.prole.get(indice);

        //pegando as referências das View

        TextView id = (TextView) view.findViewById(R.id.lista_prole_id);
        TextView data = (TextView) view.findViewById(R.id.lista_prole_data);
        TextView peso = (TextView) view.findViewById(R.id.lista_prole_peso);

        //populando as Views
        id.setText(String.valueOf(itemProle.getId()));
        data.setText(Conversor.dataFormatada(itemProle.getDataDeNascimento()));

        String str;
        if (!itemProle.isNatimorto())
            str = act.getString(R.string.unidade_medida_kg, "Peso:", itemProle.getPesoDeNascimento());
        else
            str = act.getString(R.string.campo_natimorto);

        peso.setText(str);

        return view;
    }

}
