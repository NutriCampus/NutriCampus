package com.nutricampus.app.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nutricampus.app.R;
import com.nutricampus.app.entities.ProducaoDeLeite;
import com.nutricampus.app.utils.Conversor;

import java.util.List;


public class ListaProducaoAdapter extends BaseAdapter {

    private final List<ProducaoDeLeite> producaoDeLeites;
    private final Activity act;

    public ListaProducaoAdapter(List<ProducaoDeLeite> producaoDeLeites, Activity act) {
        this.producaoDeLeites = producaoDeLeites;
        this.act = act;
    }

    @Override
    public int getCount() {
        return producaoDeLeites.size();
    }

    @Override
    public Object getItem(int indice) {
        return producaoDeLeites.get(indice);
    }

    @Override
    public long getItemId(int indice) {
        return 0;
    }

    @Override
    public View getView(int indice, final View convertView, ViewGroup viewGroup) {
        final View view = act.getLayoutInflater()
                .inflate(R.layout.lista_producao_personalizada, viewGroup, false);
        final ProducaoDeLeite producao = producaoDeLeites.get(indice);

        //pegando as referências das Views
<<<<<<< HEAD
        TextView id = (TextView) view.findViewById(R.id.lista_producao_id);
        TextView data = (TextView) view.findViewById(R.id.lista_producao_data);
        TextView leite = (TextView) view.findViewById(R.id.lista_producao_leite);
=======
        TextView id = view.findViewById(R.id.lista_producao_id);
        TextView data = view.findViewById(R.id.lista_producao_data);
        TextView leite = view.findViewById(R.id.lista_producao_leite);
>>>>>>> origin/pmateus

        //populando as Views
        id.setText(String.valueOf(producao.getId()));
        data.setText(Conversor.dataFormatada(producao.getData()));
        leite.setText(act.getString(R.string.unidade_medida_kg, "Peso", producao.getQntProduzida()));

        return view;
    }

}
