package com.nutricampus.app.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.nutricampus.app.entities.ProducaoDeLeite;

import java.sql.Date;
import java.util.ArrayList;
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

        ContentValues dados = new ContentValues();
        dados.put(SQLiteManager.PRODUCAO_DE_LEITE_ID_ANIMAL, producaoDeLeite.getAnimal());
        dados.put(SQLiteManager.PRODUCAO_DE_LEITE_DATA, producaoDeLeite.getData().getTime());
        dados.put(SQLiteManager.PRODUCAO_DE_LEITE_QNT_PRODUZIDA, producaoDeLeite.getQntProduzida());
        dados.put(SQLiteManager.PRODUCAO_DE_LEITE_PCT_LACTOSE, producaoDeLeite.getPctLactose());
        dados.put(SQLiteManager.PRODUCAO_DE_LEITE_PCT_PROTEINA_VERDADEIRA, producaoDeLeite.getPctProteinaVerdadeira());
        dados.put(SQLiteManager.PRODUCAO_DE_LEITE_PCT_PROTEINA_BRUTA, producaoDeLeite.getPctProteinaBruta());

        long retorno = bancoDados.insert(SQLiteManager.TABELA_PRODUCAO_DE_LEITE, null, dados);
        bancoDados.close();

        // retorna o id do elemento inserido
        return (int) retorno;
    }

    public ProducaoDeLeite buscarProducaoDeLeite(int id_animal) {
        bancoDados = gerenciador.getReadableDatabase();

        String colunas_where = SQLiteManager.PRODUCAO_DE_LEITE_ID_ANIMAL + "= ?";
        String[] valores_where = new String[]{String.valueOf(id_animal)};

        Cursor cursor = bancoDados.query(SQLiteManager.TABELA_PRODUCAO_DE_LEITE, new String[]{
                        SQLiteManager.PRODUCAO_DE_LEITE_ID,
                        SQLiteManager.PRODUCAO_DE_LEITE_ID_ANIMAL,
                        SQLiteManager.PRODUCAO_DE_LEITE_DATA,
                        SQLiteManager.PRODUCAO_DE_LEITE_QNT_PRODUZIDA,
                        SQLiteManager.PRODUCAO_DE_LEITE_PCT_LACTOSE,
                        SQLiteManager.PRODUCAO_DE_LEITE_PCT_PROTEINA_VERDADEIRA,
                        SQLiteManager.PRODUCAO_DE_LEITE_PCT_PROTEINA_BRUTA},
                colunas_where,
                valores_where, null, null, null, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            return new ProducaoDeLeite(
                    cursor.getInt(cursor.getColumnIndex(SQLiteManager.PRODUCAO_DE_LEITE_ID)),
                    Date.valueOf(cursor.getString(cursor.getColumnIndex(SQLiteManager.PRODUCAO_DE_LEITE_DATA))),
                    cursor.getInt(cursor.getColumnIndex(SQLiteManager.PRODUCAO_DE_LEITE_ID_ANIMAL)),
                    cursor.getFloat(cursor.getColumnIndex(SQLiteManager.PRODUCAO_DE_LEITE_QNT_PRODUZIDA)),
                    cursor.getFloat(cursor.getColumnIndex(SQLiteManager.PRODUCAO_DE_LEITE_PCT_LACTOSE)),
                    cursor.getFloat(cursor.getColumnIndex(SQLiteManager.PRODUCAO_DE_LEITE_PCT_PROTEINA_VERDADEIRA)),
                    cursor.getFloat(cursor.getColumnIndex(SQLiteManager.PRODUCAO_DE_LEITE_PCT_PROTEINA_BRUTA)));
        }
        cursor.close();
        return null;
    }

    public List<ProducaoDeLeite> buscarTodaProducaoDeLeite() {

        bancoDados = gerenciador.getReadableDatabase();

        ArrayList<ProducaoDeLeite> producaoDeLeite = new ArrayList<>();
        String getProducao = "SELECT * FROM " + SQLiteManager.TABELA_PRODUCAO_DE_LEITE;

        try {
            Cursor c = bancoDados.rawQuery(getProducao, null);

            if (c.moveToFirst()) {
                do {
                    ProducaoDeLeite p = new ProducaoDeLeite();
                    p.setId(c.getInt(c.getColumnIndex(SQLiteManager.PRODUCAO_DE_LEITE_ID)));
                    p.setAnimal(c.getInt(c.getColumnIndex(SQLiteManager.PRODUCAO_DE_LEITE_ID_ANIMAL)));
                    p.setData(Date.valueOf(c.getString(c.getColumnIndex(SQLiteManager.PRODUCAO_DE_LEITE_DATA))));
                    p.setQntProduzida(c.getFloat(c.getColumnIndex(SQLiteManager.PRODUCAO_DE_LEITE_QNT_PRODUZIDA)));
                    p.setPctLactose(c.getFloat(c.getColumnIndex(SQLiteManager.PRODUCAO_DE_LEITE_PCT_LACTOSE)));
                    p.setPctProteinaVerdadeira(c.getFloat(c.getColumnIndex(SQLiteManager.PRODUCAO_DE_LEITE_PCT_PROTEINA_VERDADEIRA)));
                    p.setPctProteinaBruta(c.getFloat(c.getColumnIndex(SQLiteManager.PRODUCAO_DE_LEITE_PCT_PROTEINA_BRUTA)));

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

    public boolean atualizarProducaoDeLeite(ProducaoDeLeite producaoDeLeite) {
        bancoDados = gerenciador.getWritableDatabase();

        ContentValues dados = new ContentValues();
        dados.put(SQLiteManager.PRODUCAO_DE_LEITE_ID_ANIMAL, producaoDeLeite.getId());
        dados.put(SQLiteManager.PRODUCAO_DE_LEITE_DATA, producaoDeLeite.getData().getTime());
        dados.put(SQLiteManager.PRODUCAO_DE_LEITE_QNT_PRODUZIDA, producaoDeLeite.getQntProduzida());
        dados.put(SQLiteManager.PRODUCAO_DE_LEITE_PCT_LACTOSE, producaoDeLeite.getPctLactose());
        dados.put(SQLiteManager.PRODUCAO_DE_LEITE_PCT_PROTEINA_VERDADEIRA, producaoDeLeite.getPctProteinaVerdadeira());
        dados.put(SQLiteManager.PRODUCAO_DE_LEITE_PCT_PROTEINA_BRUTA, producaoDeLeite.getPctProteinaBruta());

        int retorno = bancoDados.update(SQLiteManager.TABELA_PRODUCAO_DE_LEITE,
                dados, SQLiteManager.PRODUCAO_DE_LEITE_ID + " = ?",
                new String[]{String.valueOf(producaoDeLeite.getId())});

        bancoDados.close();

        return (retorno > 0);

    }

    public void removerProducaoDeLeite(ProducaoDeLeite producaoDeLeite) {
        bancoDados = gerenciador.getWritableDatabase();
        bancoDados.delete(SQLiteManager.TABELA_PRODUCAO_DE_LEITE,
                SQLiteManager.PRODUCAO_DE_LEITE_ID + " = ? ",
                new String[]{String.valueOf(producaoDeLeite.getId())});

        bancoDados.close();
    }
}
