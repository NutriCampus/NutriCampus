package com.nutricampus.app.model;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by kellison on 16/07/17.
 */

public abstract class LeitorJSON {
    public static String carregaJSONAssets(String arquivo, Context context) {
        String json = "";

        try {
            InputStream is = context.getAssets().open(arquivo);
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
