package com.nutricampus.app.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.nutricampus.app.entities.Prole;
import com.nutricampus.app.utils.Conversor;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * Created by Diego Bezerra on 15/06/17.
 * For project NutriCampus.
 * Contact: <diego.defb@gmail.com>
 */

public class RepositorioProle {

    private SQLiteManager gerenciador;
    private SQLiteDatabase bancoDados;

    public RepositorioProle(Context context) {
        gerenciador = new SQLiteManager(context);
    }

    public int inserirProle(Prole prole) {
        bancoDados = gerenciador.getWritableDatabase();

        ContentValues dados = getContentValues(prole);

        long retorno = bancoDados.insert(SQLiteManager.TABELA_PROLE, null, dados);
        bancoDados.close();

        // retorna o id do elemento inserido
        return (int) retorno;
    }

    public List<Prole> buscarPorMatriz(int idMatriz) {

        bancoDados = gerenciador.getReadableDatabase();

        String sql = "SELECT * FROM " + SQLiteManager.TABELA_PROLE +
                " WHERE " + SQLiteManager.PROLE_ID_MATRIZ + " = " + idMatriz;

        ArrayList<Prole> proles = new ArrayList<>();
        String getProles = sql;

        try {
            Cursor c = bancoDados.rawQuery(getProles, null);

            if (c.moveToFirst()) {
                do {
                    proles.add(getDadosFromCursor(c));
                } while (c.moveToNext());
                c.close();
            }

        } catch (Exception e) {
            Log.i("RepositorioProle", e.toString());
            return Collections.emptyList();
        } finally {
            bancoDados.close();
        }

        return proles;
    }

    public List<Prole> buscarPorAnimalPeriodo(int idAnimal, int mes, int ano) {

        bancoDados = gerenciador.getReadableDatabase();

        ArrayList<Prole> proles = new ArrayList<>();
        String getProles = "SELECT * FROM " + SQLiteManager.TABELA_PROLE +
                " WHERE " + SQLiteManager.PROLE_ID_MATRIZ + " = " + idAnimal;

        try {
            Cursor c = bancoDados.rawQuery(getProles, null);

            if (c.moveToFirst()) {
                do {
                    Calendar data = Calendar.getInstance();
                    data.setTimeInMillis(Long.valueOf(c.getString(c.getColumnIndex(SQLiteManager.PROLE_DATA_DE_NASCIMENTO))));

                    int month = data.get(Calendar.MONTH);
                    int year = data.get(Calendar.YEAR);

                    if ((month == mes) && (year == ano))
                        proles.add(getDadosFromCursor(c));

                } while (c.moveToNext());
                c.close();
            }

        } catch (Exception e) {
            Log.i("RepositorioProle", e.toString());
            return Collections.emptyList();
        } finally {
            bancoDados.close();
        }

        return proles;
    }

    public boolean atualizarProle(Prole prole) {
        bancoDados = gerenciador.getWritableDatabase();

        ContentValues dados = getContentValues(prole);

        int retorno = bancoDados.update(SQLiteManager.TABELA_PROLE,
                dados, SQLiteManager.PROLE_ID + " = ? ",
                new String[]{String.valueOf(prole.getId())});

        bancoDados.close();

        return (retorno > 0);

    }

    /*
    public int removerProle(Prole prole) {
        bancoDados = gerenciador.getWritableDatabase();
        int result = bancoDados.delete(SQLiteManager.TABELA_PROLE,
                SQLiteManager.PROLE_ID + " = ? ",
                new String[]{String.valueOf(prole.getId())});

        bancoDados.close();
        return result;
    }*/

    public int removerProle(Prole prole) {
        return excluirRegistros(prole.getId(), 1);
    }

    public int removerProle(int idAnimal) {
        return excluirRegistros(idAnimal, 2);
    }


    private int excluirRegistros(int id, int tipo) {
        bancoDados = gerenciador.getWritableDatabase();
        String coluna;

        //tipo = 1 (ID DadosCompl) | tipo = 2 (ID Animal)
        if (tipo == 1)
            coluna = SQLiteManager.PROLE_ID;
        else
            coluna = SQLiteManager.PROLE_ID_MATRIZ;

        int result = bancoDados.delete(SQLiteManager.TABELA_PROLE,
                coluna + " = ? ",
                new String[]{String.valueOf(id)});

        bancoDados.close();

        return result;
    }


    private ContentValues getContentValues(Prole prole) {
        ContentValues dados = new ContentValues();
        dados.put(SQLiteManager.PROLE_ID_MATRIZ, prole.getMatriz());
        dados.put(SQLiteManager.PROLE_DATA_DE_NASCIMENTO, prole.getDataDeNascimento().getTimeInMillis());
        dados.put(SQLiteManager.PROLE_PESO_DE_NASCIMENTO, prole.getPesoDeNascimento());
        dados.put(SQLiteManager.PROLE_IS_NATIMORTO, Conversor.booleanToInt(prole.isNatimorto()));

        return dados;
    }

    private Prole getDadosFromCursor(Cursor c) {
        Calendar data = Calendar.getInstance();
        Prole prole = new Prole();

        data.setTimeInMillis(Long.valueOf(c.getString(c.getColumnIndex(SQLiteManager.PROLE_DATA_DE_NASCIMENTO))));

        prole.setId(c.getInt(c.getColumnIndex(SQLiteManager.PROLE_ID)));
        prole.setMatriz(c.getInt(c.getColumnIndex(SQLiteManager.PROLE_ID_MATRIZ)));
        prole.setDataDeNascimento(data);
        prole.setPesoDeNascimento(c.getFloat(c.getColumnIndex(SQLiteManager.PROLE_PESO_DE_NASCIMENTO)));
        prole.setNatimorto(Conversor.intToBoolean(c.getInt(c.getColumnIndex(SQLiteManager.PROLE_IS_NATIMORTO))));

        return prole;
    }

}


