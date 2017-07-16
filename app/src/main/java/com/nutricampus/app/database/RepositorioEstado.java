package com.nutricampus.app.database;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.nutricampus.app.entities.Estado;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class RepositorioEstado {

    private List<Estado> lista;
    private final Context activity;

    public RepositorioEstado(Context activity) {
        this.lista = new ArrayList<>();
        this.activity = activity;
        preencheLista("estados.json");
    }

    public RepositorioEstado(Activity activity, String arquivo) {
        this.lista = new ArrayList<>();
        this.activity = activity;
        preencheLista(arquivo);
    }

    public List<Estado> getLista() {
        return lista;
    }

    public List<String> getListaDeNomes() {
        List<String> nomes = new ArrayList<>();

        for (Estado estado : getLista()) {
            nomes.add(estado.getNome());
        }

        return nomes;
    }


    public int getIdPeloNome(String estado) throws Exception {
        int cont = 0;
        while (cont < this.getLista().size()) {
            if (this.getLista().get(cont).getNome().equals(estado))
                return this.getLista().get(cont).getId();
            cont++;
        }

        throw new Exception("Nenhum estado encontrada");
    }

    private void preencheLista(String arquivo) {

        try {
            JSONObject obj = new JSONObject(carregaJSONAssets(arquivo));
            JSONArray familiaAray = obj.getJSONArray("estados");

            for (int i = 0; i < familiaAray.length(); i++) {
                JSONObject dadosJSON = familiaAray.getJSONObject(i);
                String id = dadosJSON.getString("ID");
                String nome = dadosJSON.getString("Nome");
                String sigla = dadosJSON.getString("Sigla");

                Estado estado = new Estado(Integer.valueOf(id), sigla, nome);

                this.lista.add(estado);
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
