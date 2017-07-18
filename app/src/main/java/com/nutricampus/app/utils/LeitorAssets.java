package com.nutricampus.app.utils;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;


public class LeitorAssets {
    private LeitorAssets() {
        throw new IllegalStateException("Classe de utilidades");
    }

    public static String carregaJSONAssets(String arquivo, Context context) {
        String ext = arquivo.substring(arquivo.length() - 5, arquivo.length());

        if (!ext.equals(".json"))
            throw new IllegalArgumentException("Arquivo deve ser do tipo JSON");

        return lerArquivo(arquivo, context);

    }

    private static String lerArquivo(String arquivo, Context context) {
        String conteudo = "";

        try {
            InputStream is = context.getAssets().open(arquivo);
            int tamanho = is.available();

            byte[] buffer = new byte[tamanho];

            if (is.read(buffer) > 0)
                conteudo = new String(buffer, "UTF-8");

            is.close();
        } catch (IOException ex) {
            Log.i("IOException", ex.toString());
            return null;
        }

        return conteudo;
    }
}
