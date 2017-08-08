package com.nutricampus.app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nutricampus.app.R;
import com.nutricampus.app.entities.Proprietario;

import java.util.List;

/**
 * Created by Felipe on 01/08/2017.
 * For project NutriCampus.
 * Contact: <felipeguimaraes540@gmail.com>
 */

public class ListaProprietariosAdapter extends BaseAdapter {

    private final List<Proprietario> proprietarios;
    private final Context context;

    public ListaProprietariosAdapter(List<Proprietario> proprietarios, Context context) {
        this.proprietarios = proprietarios;
        this.context = context;
    }

    @Override
    public int getCount() {
        return proprietarios.size();
    }

    @Override
    public Object getItem(int i) {
        return proprietarios.get(i);
    }

    @Override
    public long getItemId(int i) {
        return proprietarios.get(i).getId();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        final View linha = LayoutInflater.from(context).inflate(R.layout.item_lista_proprietarios, null);
        final Proprietario proprietario = proprietarios.get(position);

        TextView nome = (TextView) linha.findViewById(R.id.lista_proprietario_nome);
        TextView fone = (TextView) linha.findViewById(R.id.lista_proprietario_fone);

        nome.setText(proprietario.getNome());
        fone.setText(proprietario.getTelefone());


        return linha;
    }
}
