package com.nutricampus.app.utils;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;


public final class LeitorAssets {
    private LeitorAssets() {
        throw new IllegalStateException("Classe de utilidades");
    }

    public static String carregaJSONAssets(String arquivo, Context context) throws IOException {
        String ext = arquivo.substring(arquivo.length() - 5, arquivo.length());

        if (!(".json".equals(ext)))
            throw new IllegalArgumentException("Arquivo deve ser do tipo JSON");

        return lerArquivo(arquivo, context);

    }

    private static String lerArquivo(String arquivo, Context context) throws IOException {
        String conteudo = "";
        InputStream is = null;
        try {
            is = context.getAssets().open(arquivo);
            int tamanho = is.available();

            byte[] buffer = new byte[tamanho];

            if (is.read(buffer) > 0)
                conteudo = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            Log.i("IOException", ex.toString());
            return null;
        } finally {
            if (is != null)
                is.close();

        }

        return conteudo;
    }
}
