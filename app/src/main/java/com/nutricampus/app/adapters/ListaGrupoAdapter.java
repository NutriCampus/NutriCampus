package com.nutricampus.app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nutricampus.app.R;
import com.nutricampus.app.entities.Grupo;

import java.util.List;


public class ListaGrupoAdapter extends BaseAdapter {

    private final Context context;
    private final List<Grupo> dados;

    public ListaGrupoAdapter(Context context, List<Grupo> dados) {
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
        final View linha = LayoutInflater.from(context).inflate(R.layout.item_lista_grupos, null);
        final Grupo grupoItem = dados.get(position);

        TextView id = (TextView) linha.findViewById(R.id.lista_grupo_id);
        TextView nome = (TextView) linha.findViewById(R.id.lista_grupo_nome);

        id.setText(String.valueOf(grupoItem.getId()));
        nome.setText(grupoItem.getIdentificador());

        return linha;
    }
}
