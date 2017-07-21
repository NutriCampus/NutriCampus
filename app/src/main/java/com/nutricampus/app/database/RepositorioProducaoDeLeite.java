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

    public int inserirProducaoDeLeite(ProducaoDeLeite producaoDeLeite) {
        bancoDados = gerenciador.getWritableDatabase();

        long retorno = bancoDados.insert(SQLiteManager.TABELA_PRODUCAO_DE_LEITE, null, getContentValues(producaoDeLeite));
        bancoDados.close();

        // retorna o id do elemento inserido
        return (int) retorno;
    }

    public ProducaoDeLeite buscarProducao(int id) {
        bancoDados = gerenciador.getReadableDatabase();

        String colunas_where = SQLiteManager.PRODUCAO_DE_LEITE_ID + "= ?";
        String[] valores_where = new String[]{String.valueOf(id)};

        Cursor cursor = bancoDados.query(SQLiteManager.TABELA_PRODUCAO_DE_LEITE, new String[]{
                        SQLiteManager.PRODUCAO_DE_LEITE_ID,
                        SQLiteManager.PRODUCAO_DE_LEITE_ID_ANIMAL,
                        SQLiteManager.PRODUCAO_DE_LEITE_DATA,
                        SQLiteManager.PRODUCAO_DE_LEITE_QNT_PRODUZIDA,
                        SQLiteManager.PRODUCAO_DE_LEITE_PCT_LACTOSE,
                        SQLiteManager.PRODUCAO_DE_LEITE_PCT_PROTEINA_VERDADEIRA,
                        SQLiteManager.PRODUCAO_DE_LEITE_PCT_PROTEINA_BRUTA,
                        SQLiteManager.PRODUCAO_DE_LEITE_GORDURA},
                colunas_where,
                valores_where, null, null, null, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            Calendar data = Calendar.getInstance();
            data.setTimeInMillis(Long.valueOf(cursor.getString(cursor.getColumnIndex(SQLiteManager.PRODUCAO_DE_LEITE_DATA))));

            return new ProducaoDeLeite(
                    data,
                    cursor.getInt(cursor.getColumnIndex(SQLiteManager.PRODUCAO_DE_LEITE_ID)),
                    cursor.getFloat(cursor.getColumnIndex(SQLiteManager.PRODUCAO_DE_LEITE_QNT_PRODUZIDA)),
                    cursor.getFloat(cursor.getColumnIndex(SQLiteManager.PRODUCAO_DE_LEITE_PCT_LACTOSE)),
                    cursor.getFloat(cursor.getColumnIndex(SQLiteManager.PRODUCAO_DE_LEITE_PCT_PROTEINA_VERDADEIRA)),
                    cursor.getFloat(cursor.getColumnIndex(SQLiteManager.PRODUCAO_DE_LEITE_PCT_PROTEINA_BRUTA)),
                    cursor.getFloat(cursor.getColumnIndex(SQLiteManager.PRODUCAO_DE_LEITE_GORDURA)));
        }
        cursor.close();
        return null;
    }

    public List<ProducaoDeLeite> buscarPorAnimal(int idAnimal) {

        bancoDados = gerenciador.getReadableDatabase();

        ArrayList<ProducaoDeLeite> producaoDeLeite = new ArrayList<>();
        String getProducao = SQLiteManager.SELECT_TODOS +
                SQLiteManager.TABELA_PRODUCAO_DE_LEITE +
                " WHERE id_animal = " + idAnimal;

        try {
            Cursor c = bancoDados.rawQuery(getProducao, null);

            if (c.moveToFirst()) {
                do {

                    Calendar data = Calendar.getInstance();
                    data.setTimeInMillis(Long.valueOf(c.getString(c.getColumnIndex(SQLiteManager.PRODUCAO_DE_LEITE_DATA))));

                    ProducaoDeLeite p = new ProducaoDeLeite();
                    p.setId(c.getInt(c.getColumnIndex(SQLiteManager.PRODUCAO_DE_LEITE_ID)));
                    p.setAnimal(c.getInt(c.getColumnIndex(SQLiteManager.PRODUCAO_DE_LEITE_ID_ANIMAL)));
                    p.setData(data);
                    p.setQntProduzida(c.getFloat(c.getColumnIndex(SQLiteManager.PRODUCAO_DE_LEITE_QNT_PRODUZIDA)));
                    p.setPctLactose(c.getFloat(c.getColumnIndex(SQLiteManager.PRODUCAO_DE_LEITE_PCT_LACTOSE)));
                    p.setPctProteinaVerdadeira(c.getFloat(c.getColumnIndex(SQLiteManager.PRODUCAO_DE_LEITE_PCT_PROTEINA_VERDADEIRA)));
                    p.setPctProteinaBruta(c.getFloat(c.getColumnIndex(SQLiteManager.PRODUCAO_DE_LEITE_PCT_PROTEINA_BRUTA)));
                    p.setGordura(c.getFloat(c.getColumnIndex(SQLiteManager.PRODUCAO_DE_LEITE_GORDURA)));

                    producaoDeLeite.add(p);
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
                " WHERE id_animal = " + idAnimal;

        try {
            Cursor c = bancoDados.rawQuery(getProducao, null);

            if (c.moveToFirst()) {
                do {

                    Calendar data = Calendar.getInstance();
                    data.setTimeInMillis(Long.valueOf(c.getString(c.getColumnIndex(SQLiteManager.PRODUCAO_DE_LEITE_DATA))));

                    int month = data.get(Calendar.MONTH);
                    int year = data.get(Calendar.YEAR);

                    if ((month == mes) && (year == ano)) {
                        ProducaoDeLeite p = new ProducaoDeLeite();
                        p.setId(c.getInt(c.getColumnIndex(SQLiteManager.PRODUCAO_DE_LEITE_ID)));
                        p.setAnimal(c.getInt(c.getColumnIndex(SQLiteManager.PRODUCAO_DE_LEITE_ID_ANIMAL)));
                        p.setData(data);
                        p.setQntProduzida(c.getFloat(c.getColumnIndex(SQLiteManager.PRODUCAO_DE_LEITE_QNT_PRODUZIDA)));
                        p.setPctLactose(c.getFloat(c.getColumnIndex(SQLiteManager.PRODUCAO_DE_LEITE_PCT_LACTOSE)));
                        p.setPctProteinaVerdadeira(c.getFloat(c.getColumnIndex(SQLiteManager.PRODUCAO_DE_LEITE_PCT_PROTEINA_VERDADEIRA)));
                        p.setPctProteinaBruta(c.getFloat(c.getColumnIndex(SQLiteManager.PRODUCAO_DE_LEITE_PCT_PROTEINA_BRUTA)));
                        p.setGordura(c.getFloat(c.getColumnIndex(SQLiteManager.PRODUCAO_DE_LEITE_GORDURA)));

                        producaoDeLeite.add(p);
                    }
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
}
