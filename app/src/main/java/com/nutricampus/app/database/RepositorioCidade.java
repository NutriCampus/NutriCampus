package com.nutricampus.app.database;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.nutricampus.app.entities.Cidade;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
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
        List<Cidade> lista = new ArrayList<>();
        for (Cidade cidade : this.getLista()) {
            if (cidade.getIdEstado() == idEstado)
                lista.add(cidade);
        }

        return lista;
    }

    public int getIdPeloNome(String cidade, int estado) throws Exception {
        int cont = 0;
        while (cont < this.getLista().size()) {
            if (this.getLista().get(cont).getNome().equals(cidade) &&
                    (this.getLista().get(cont).getIdEstado() == estado))
                return this.getLista().get(cont).getId();
            cont++;

        }

        throw new Exception("Nenhuma cidade encontrada");
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
            JSONObject obj = new JSONObject(carregaJSONAssets(arquivo));
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

    private String carregaJSONAssets(String arquivo) {
        String json = "";

        try {
            InputStream is = activity.getAssets().open(arquivo);
            int tamanho = is.available();

            byte[] buffer = new byte[tamanho];

            if (is.read(buffer) > 0)
                json = new String(buffer, "UTF-8");

            is.close();
        } catch (IOException ex) {
            Log.i("IOException", ex.toString());
            return null;
        }

        return json;

    }


}
