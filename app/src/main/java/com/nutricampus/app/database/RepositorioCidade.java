package com.nutricampus.app.database;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.nutricampus.app.entities.Cidade;
import com.nutricampus.app.model.CidadeNaoEncontradaException;
import com.nutricampus.app.model.LeitorJSON;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RepositorioCidade {

    private List<Cidade> lista;
    private final Context activity;

    public RepositorioCidade(Context activity) {
        this.lista = new ArrayList<>();
        this.activity = activity;
        preencheLista("cidades.json");
    }

    public RepositorioCidade(Activity activity, String arquivo) {
        this.lista = new ArrayList<>();
        this.activity = activity;
        preencheLista(arquivo);
    }

    public List<Cidade> getLista() {
        return lista;
    }

    public List<Cidade> getListaPorEstado(int idEstado) {
        List<Cidade> cidades = new ArrayList<>();
        for (Cidade cidade : this.getLista()) {
            if (cidade.getIdEstado() == idEstado)
                cidades.add(cidade);
        }

        return cidades;
    }

    public int getIdPeloNome(String cidade, int estado) throws Exception {
        int cont = 0;
        while (cont < this.getLista().size()) {
            if (this.getLista().get(cont).getNome().equals(cidade) &&
                    (this.getLista().get(cont).getIdEstado() == estado))
                return this.getLista().get(cont).getId();
            cont++;

        }

        throw new CidadeNaoEncontradaException();
    }

    public List<String> getListaDeNomes() {
        List<String> nomes = new ArrayList<>();

        for (Cidade cidade : getLista()) {
            nomes.add(cidade.getNome());
        }

        return nomes;
    }

    private void preencheLista(String arquivo) {

        try {
            JSONObject obj = new JSONObject(LeitorJSON.carregaJSONAssets(arquivo, activity));
            JSONArray familiaAray = obj.getJSONArray("cidades");

            for (int i = 0; i < familiaAray.length(); i++) {
                JSONObject dadosJSON = familiaAray.getJSONObject(i);
                String id = dadosJSON.getString("ID");
                String idEstado = dadosJSON.getString("Estado");
                String nome = dadosJSON.getString("Nome");

                Cidade cidade = new Cidade(Integer.valueOf(id), "", nome, Integer.valueOf(idEstado));

                this.lista.add(cidade);
            }

        } catch (JSONException e) {
            Log.i("JSONException", String.valueOf(e));
        }
    }

}
