package com.nutricampus.app.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.nutricampus.app.entities.ProducaoDeLeite;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * Created by Diego Bezerra on 15/06/17.
 * For project NutriCampus.
 * Contact: <diego.defb@gmail.com>
 */

public class RepositorioProducaoDeLeite {

    private SQLiteManager gerenciador;
    private SQLiteDatabase bancoDados;

    public RepositorioProducaoDeLeite(Context context) {
        gerenciador = new SQLiteManager(context);
    }

    public int inserirProducaoDeLeite(ProducaoDeLeite producaoDeLeite) {
        bancoDados = gerenciador.getWritableDatabase();

        long retorno = bancoDados.insert(SQLiteManager.TABELA_PRODUCAO_DE_LEITE, null, getContentValues(producaoDeLeite));
        bancoDados.close();

        // retorna o id do elemento inserido
        return (int) retorno;
    }


    public List<ProducaoDeLeite> buscarPorAnimal(int idAnimal) {

        bancoDados = gerenciador.getReadableDatabase();

        ArrayList<ProducaoDeLeite> producaoDeLeite = new ArrayList<>();
        String getProducao = SQLiteManager.SELECT_TODOS +
                SQLiteManager.TABELA_PRODUCAO_DE_LEITE +
                " WHERE " + SQLiteManager.PRODUCAO_DE_LEITE_ID_ANIMAL + " = " + idAnimal;

        try {
            Cursor c = bancoDados.rawQuery(getProducao, null);

            if (c.moveToFirst()) {
                do {
                    producaoDeLeite.add(getDadosFromCursor(c));
                } while (c.moveToNext());
                c.close();
            }

        } catch (Exception e) {
            Log.i("RepositorioProdDeLeite", e.toString());
            return Collections.emptyList();
        } finally {
            bancoDados.close();
        }

        return producaoDeLeite;
    }

    /**
     * Buscar os dados de um animal em um periodo de tempo (mes, ano)
     *
     * @param idAnimal Id do registro da tabela do animal
     * @param mes      Inteiro de 0 a 11
     * @param ano      Numero qualquer
     * @return Lista de ProducaoDeLeite
     */
    public List<ProducaoDeLeite> buscarPorAnimalPeriodo(int idAnimal, int mes, int ano) {
        bancoDados = gerenciador.getReadableDatabase();

        ArrayList<ProducaoDeLeite> producaoDeLeite = new ArrayList<>();
        String getProducao = SQLiteManager.SELECT_TODOS +
                SQLiteManager.TABELA_PRODUCAO_DE_LEITE +
                " WHERE " + SQLiteManager.PRODUCAO_DE_LEITE_ID_ANIMAL + " = " + idAnimal;

        try {
            Cursor c = bancoDados.rawQuery(getProducao, null);

            if (c.moveToFirst()) {
                do {

                    Calendar data = Calendar.getInstance();
                    data.setTimeInMillis(Long.valueOf(c.getString(c.getColumnIndex(SQLiteManager.PRODUCAO_DE_LEITE_DATA))));

                    int month = data.get(Calendar.MONTH);
                    int year = data.get(Calendar.YEAR);

                    if ((month == mes) && (year == ano))
                        producaoDeLeite.add(getDadosFromCursor(c));

                } while (c.moveToNext());
                c.close();
            }

        } catch (Exception e) {
            Log.i("RepositorioProdDeLeite", e.toString());
            return Collections.emptyList();
        } finally {
            bancoDados.close();
        }

        return producaoDeLeite;
    }



    public boolean atualizarProducaoDeLeite(ProducaoDeLeite producaoDeLeite) {
        bancoDados = gerenciador.getWritableDatabase();

        int retorno = bancoDados.update(SQLiteManager.TABELA_PRODUCAO_DE_LEITE,
                getContentValues(producaoDeLeite), SQLiteManager.PRODUCAO_DE_LEITE_ID + " = ?",
                new String[]{String.valueOf(producaoDeLeite.getId())});

        bancoDados.close();

        return (retorno > 0);

    }

    public int removerProducaoDeLeite(ProducaoDeLeite producaoDeLeite) {
        bancoDados = gerenciador.getWritableDatabase();
        int result = bancoDados.delete(SQLiteManager.TABELA_PRODUCAO_DE_LEITE,
                SQLiteManager.PRODUCAO_DE_LEITE_ID + " = ? ",
                new String[]{String.valueOf(producaoDeLeite.getId())});

        bancoDados.close();
        return result;
    }


    private ContentValues getContentValues(ProducaoDeLeite producaoDeLeite) {
        ContentValues dados = new ContentValues();
        dados.put(SQLiteManager.PRODUCAO_DE_LEITE_ID_ANIMAL, producaoDeLeite.getAnimal());
        dados.put(SQLiteManager.PRODUCAO_DE_LEITE_DATA, producaoDeLeite.getData().getTimeInMillis());
        dados.put(SQLiteManager.PRODUCAO_DE_LEITE_QNT_PRODUZIDA, producaoDeLeite.getQntProduzida());
        dados.put(SQLiteManager.PRODUCAO_DE_LEITE_PCT_LACTOSE, producaoDeLeite.getPctLactose());
        dados.put(SQLiteManager.PRODUCAO_DE_LEITE_PCT_PROTEINA_VERDADEIRA, producaoDeLeite.getPctProteinaVerdadeira());
        dados.put(SQLiteManager.PRODUCAO_DE_LEITE_PCT_PROTEINA_BRUTA, producaoDeLeite.getPctProteinaBruta());
        dados.put(SQLiteManager.PRODUCAO_DE_LEITE_GORDURA, producaoDeLeite.getPctProteinaBruta());
        return dados;
    }

    private ProducaoDeLeite getDadosFromCursor(Cursor c) {
        Calendar data = Calendar.getInstance();
        data.setTimeInMillis(Long.valueOf(c.getString(c.getColumnIndex(SQLiteManager.PRODUCAO_DE_LEITE_DATA))));

        ProducaoDeLeite producao = new ProducaoDeLeite();
        producao.setId(c.getInt(c.getColumnIndex(SQLiteManager.PRODUCAO_DE_LEITE_ID)));
        producao.setAnimal(c.getInt(c.getColumnIndex(SQLiteManager.PRODUCAO_DE_LEITE_ID_ANIMAL)));
        producao.setData(data);
        producao.setQntProduzida(c.getFloat(c.getColumnIndex(SQLiteManager.PRODUCAO_DE_LEITE_QNT_PRODUZIDA)));
        producao.setPctLactose(c.getFloat(c.getColumnIndex(SQLiteManager.PRODUCAO_DE_LEITE_PCT_LACTOSE)));
        producao.setPctProteinaVerdadeira(c.getFloat(c.getColumnIndex(SQLiteManager.PRODUCAO_DE_LEITE_PCT_PROTEINA_VERDADEIRA)));
        producao.setPctProteinaBruta(c.getFloat(c.getColumnIndex(SQLiteManager.PRODUCAO_DE_LEITE_PCT_PROTEINA_BRUTA)));
        producao.setGordura(c.getFloat(c.getColumnIndex(SQLiteManager.PRODUCAO_DE_LEITE_GORDURA)));

        return producao;
    }
}
