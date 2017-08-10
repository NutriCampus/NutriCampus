package com.nutricampus.app.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.nutricampus.app.entities.CompostosAlimentares;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Diego Bezerra on 02/08/17.
 * For project NutriCampus.
 * Contact: <diego.defb@gmail.com>
 */

public class RepositorioCompostosAlimentares {

    private SQLiteManager gerenciador;
    private SQLiteDatabase bancoDados;

    public RepositorioCompostosAlimentares(Context context) {
        gerenciador = new SQLiteManager(context);
    }

    public int inserirCompostoAlimentar(CompostosAlimentares compostoAlimentar) {
        bancoDados = gerenciador.getWritableDatabase();

        ContentValues dados = getContentValues(compostoAlimentar);

        long retorno = bancoDados.insert(SQLiteManager.TABELA_COMPOSTOS_ALIMENTARES, null, dados);
        bancoDados.close();

        // retorna o id do elemento inserido
        return (int) retorno;
    }

    private List<CompostosAlimentares> getListaCompostos(String query) {
        bancoDados = gerenciador.getReadableDatabase();

        ArrayList<CompostosAlimentares> compostosAlimentares = new ArrayList<>();
        try {
            Cursor c = bancoDados.rawQuery(query, null);

            if (c.moveToFirst()) {
                do {
                    CompostosAlimentares a = getDadosFromCursor(c);
                    compostosAlimentares.add(a);
                } while (c.moveToNext());
                c.close();
            }

        } catch (Exception e) {
            Log.i("RepositorioComspostos", e.toString());
            return Collections.emptyList();
        } finally {
            bancoDados.close();
        }

        return compostosAlimentares;
    }

    public List<CompostosAlimentares> buscarTodosCompostos(String identificador) {
        return this.getListaCompostos("SELECT * FROM " + SQLiteManager.TABELA_COMPOSTOS_ALIMENTARES +
                " WHERE " + SQLiteManager.COMPOSTOS_ALIMENTARES_IDENTIFICADOR + " LIKE '%" + identificador + "%'");
    }

    public CompostosAlimentares buscarCompostoAlimentar(String identificador) {
        bancoDados = gerenciador.getReadableDatabase();

        Cursor cursor = bancoDados.query(SQLiteManager.TABELA_COMPOSTOS_ALIMENTARES, new String[]{
                        SQLiteManager.COMPOSTOS_ALIMENTARES_ID,
                        SQLiteManager.COMPOSTOS_ALIMENTARES_TIPO,
                        SQLiteManager.COMPOSTOS_ALIMENTARES_IDENTIFICADOR,
                        SQLiteManager.COMPOSTOS_ALIMENTARES_MS,
                        SQLiteManager.COMPOSTOS_ALIMENTARES_FDN,
                        SQLiteManager.COMPOSTOS_ALIMENTARES_EE,
                        SQLiteManager.COMPOSTOS_ALIMENTARES_MM,
                        SQLiteManager.COMPOSTOS_ALIMENTARES_CNF,
                        SQLiteManager.COMPOSTOS_ALIMENTARES_PB,
                        SQLiteManager.COMPOSTOS_ALIMENTARES_NDT,
                        SQLiteManager.COMPOSTOS_ALIMENTARES_FDA,
                        SQLiteManager.COMPOSTOS_ALIMENTARES_DESCRICAO},
                SQLiteManager.COMPOSTOS_ALIMENTARES_IDENTIFICADOR + "= ?",
                new String[]{String.valueOf(identificador)}, null, null, null, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            return getDadosFromCursor(cursor);
        }

        cursor.close();
        return null;
    }

    public List<CompostosAlimentares> buscarCompostosAlimentares(int id) {

        bancoDados = gerenciador.getReadableDatabase();

        String sql = "SELECT * FROM " + SQLiteManager.TABELA_COMPOSTOS_ALIMENTARES +
                " WHERE " + SQLiteManager.COMPOSTOS_ALIMENTARES_ID + " = " + id;

        ArrayList<CompostosAlimentares> compostosAlimentares = new ArrayList<>();

        try {
            Cursor c = bancoDados.rawQuery(sql, null);

            if (c.moveToFirst()) {
                do {
                    compostosAlimentares.add(getDadosFromCursor(c));
                } while (c.moveToNext());
                c.close();
            }

        } catch (Exception e) {
            Log.i("RepCompostosAlimentares", e.toString());
            return Collections.emptyList();
        } finally {
            bancoDados.close();
        }

        return compostosAlimentares;
    }

    public boolean atualizarCompostosAlimentares(CompostosAlimentares compostosAlimentares) {
        bancoDados = gerenciador.getWritableDatabase();
        int retorno = 0;
        try {
            retorno = bancoDados.update(SQLiteManager.TABELA_COMPOSTOS_ALIMENTARES,
                    getContentValues(compostosAlimentares), SQLiteManager.COMPOSTOS_ALIMENTARES_ID + " = ?",
                    new String[]{String.valueOf(compostosAlimentares.getId())});

        } catch (Exception ex) {
            Log.i("RepCompostosAlimentares", ex.toString());
        }
        bancoDados.close();

        return (retorno > 0);
    }

    public int removerCompostoAlimentar(CompostosAlimentares compostoAlimentar) {
        bancoDados = gerenciador.getWritableDatabase();
        int rs = bancoDados.delete(SQLiteManager.TABELA_COMPOSTOS_ALIMENTARES,
                SQLiteManager.COMPOSTOS_ALIMENTARES_ID + " = ? ",
                new String[]{String.valueOf(compostoAlimentar.getId())});

        bancoDados.close();
        return rs;
    }

    private CompostosAlimentares getDadosFromCursor(Cursor c) {
        CompostosAlimentares compostosAlimentares = new CompostosAlimentares();

        compostosAlimentares.setId(c.getInt(c.getColumnIndex(SQLiteManager.COMPOSTOS_ALIMENTARES_ID)));
        compostosAlimentares.setTipo(c.getString(c.getColumnIndex(SQLiteManager.COMPOSTOS_ALIMENTARES_TIPO)));
        compostosAlimentares.setIdentificador(c.getString(c.getColumnIndex(SQLiteManager.COMPOSTOS_ALIMENTARES_IDENTIFICADOR)));
        compostosAlimentares.setMs(c.getFloat(c.getColumnIndex(SQLiteManager.COMPOSTOS_ALIMENTARES_MS)));
        compostosAlimentares.setFdn(c.getFloat(c.getColumnIndex(SQLiteManager.COMPOSTOS_ALIMENTARES_FDN)));
        compostosAlimentares.setEe(c.getFloat(c.getColumnIndex(SQLiteManager.COMPOSTOS_ALIMENTARES_EE)));
        compostosAlimentares.setMm(c.getFloat(c.getColumnIndex(SQLiteManager.COMPOSTOS_ALIMENTARES_MM)));
        compostosAlimentares.setCnf(c.getFloat(c.getColumnIndex(SQLiteManager.COMPOSTOS_ALIMENTARES_CNF)));
        compostosAlimentares.setPb(c.getFloat(c.getColumnIndex(SQLiteManager.COMPOSTOS_ALIMENTARES_PB)));
        compostosAlimentares.setNdt(c.getFloat(c.getColumnIndex(SQLiteManager.COMPOSTOS_ALIMENTARES_NDT)));
        compostosAlimentares.setFda(c.getFloat(c.getColumnIndex(SQLiteManager.COMPOSTOS_ALIMENTARES_FDA)));
        compostosAlimentares.setDescricao(c.getString(c.getColumnIndex(SQLiteManager.COMPOSTOS_ALIMENTARES_DESCRICAO)));

        return compostosAlimentares;
    }

    private ContentValues getContentValues(CompostosAlimentares compostosAlimentares) {
        ContentValues dados = new ContentValues();

        dados.put(SQLiteManager.COMPOSTOS_ALIMENTARES_TIPO, compostosAlimentares.getTipo());
        dados.put(SQLiteManager.COMPOSTOS_ALIMENTARES_IDENTIFICADOR, compostosAlimentares.getIdentificador());
        dados.put(SQLiteManager.COMPOSTOS_ALIMENTARES_MS, compostosAlimentares.getMs());
        dados.put(SQLiteManager.COMPOSTOS_ALIMENTARES_FDN, compostosAlimentares.getFdn());
        dados.put(SQLiteManager.COMPOSTOS_ALIMENTARES_EE, compostosAlimentares.getEe());
        dados.put(SQLiteManager.COMPOSTOS_ALIMENTARES_MM, compostosAlimentares.getMm());
        dados.put(SQLiteManager.COMPOSTOS_ALIMENTARES_CNF, compostosAlimentares.getCnf());
        dados.put(SQLiteManager.COMPOSTOS_ALIMENTARES_PB, compostosAlimentares.getPb());
        dados.put(SQLiteManager.COMPOSTOS_ALIMENTARES_NDT, compostosAlimentares.getNdt());
        dados.put(SQLiteManager.COMPOSTOS_ALIMENTARES_FDA, compostosAlimentares.getFda());
        dados.put(SQLiteManager.COMPOSTOS_ALIMENTARES_DESCRICAO, compostosAlimentares.getDescricao());

        return dados;
    }

}
