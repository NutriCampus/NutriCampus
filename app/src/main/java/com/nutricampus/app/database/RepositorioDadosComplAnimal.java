package com.nutricampus.app.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.nutricampus.app.entities.DadosComplAnimal;
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

public class RepositorioDadosComplAnimal {

    private SQLiteManager gerenciador;
    private SQLiteDatabase bancoDados;

    public RepositorioDadosComplAnimal(Context context) {
        gerenciador = new SQLiteManager(context);
    }

    public int inserirDadosComplAnimal(DadosComplAnimal dadosComplAnimal) {
        bancoDados = gerenciador.getWritableDatabase();

        ContentValues dados = this.getContentValues(dadosComplAnimal);

        long retorno = bancoDados.insert(SQLiteManager.TABELA_DADOS_COMPL, null, dados);
        bancoDados.close();

        // retorna o id do elemento inserido
        return (int) retorno;
    }

    private List<DadosComplAnimal> getListaDadosComplAnimal(String query) {
        bancoDados = gerenciador.getReadableDatabase();

        ArrayList<DadosComplAnimal> dadosComplAnimais = new ArrayList<>();
        try {
            Cursor c = bancoDados.rawQuery(query, null);

            if (c.moveToFirst()) {
                do {
                    dadosComplAnimais.add(getDadosFromCursor(c));
                } while (c.moveToNext());
                c.close();
            }

        } catch (Exception e) {
            Log.i("RepositDadosComplAnimal", e.toString());
            return Collections.emptyList();
        } finally {
            bancoDados.close();
        }

        return dadosComplAnimais;
    }

    public List<DadosComplAnimal> buscarTodosDadosCompl(int idAnimal) {
        return this.getListaDadosComplAnimal(SQLiteManager.SELECT_TODOS + SQLiteManager.TABELA_DADOS_COMPL +
                " WHERE (" + SQLiteManager.DADOS_COMPL_COL_ID_ANIMAL + " = " + idAnimal + ")");
    }


    public DadosComplAnimal buscarDadosComplAnimal(int idAnimal) {
        bancoDados = gerenciador.getReadableDatabase();

        String colunasWhere = SQLiteManager.DADOS_COMPL_COL_ID_ANIMAL + " = ? ";
        String[] valoresWhere = new String[]{String.valueOf(idAnimal)};

        Cursor cursor = bancoDados.query(SQLiteManager.TABELA_DADOS_COMPL, new String[]{
                        SQLiteManager.DADOS_COMPL_COL_ID,
                        SQLiteManager.DADOS_COMPL_COL_DATA,
                        SQLiteManager.DADOS_COMPL_COL_PESO_VIVO,
                        SQLiteManager.DADOS_COMPL_COL_ID_ANIMAL,
                        SQLiteManager.DADOS_COMPL_COL_EEC,
                        SQLiteManager.DADOS_COMPL_COL_CAMINHADA_HORIZONTAL,
                        SQLiteManager.DADOS_COMPL_COL_CAMINHADA_VERTICAL,
                        SQLiteManager.DADOS_COMPL_COL_SEMANA_LACTACAO,
                        SQLiteManager.DADOS_COMPL_COL_ID_GRUPO},
                colunasWhere,
                valoresWhere, null, null, null, null);

        if (cursor.getCount() > 0) {
            cursor.moveToLast();
            return getDadosFromCursor(cursor);
        }
        cursor.close();
        return null;
    }

    public boolean atualizarDadosCompl(DadosComplAnimal dadosComplAnimal) {
        bancoDados = gerenciador.getWritableDatabase();

        int retorno = bancoDados.update(SQLiteManager.TABELA_DADOS_COMPL,
                getContentValues(dadosComplAnimal), SQLiteManager.DADOS_COMPL_COL_ID + " = ?",
                new String[]{String.valueOf(dadosComplAnimal.getId())});

        bancoDados.close();

        return (retorno > 0);
    }

    public List<DadosComplAnimal> listarHistoricoDadosComplAnimais(int idAnimal) {
        return this.getListaDadosComplAnimal(
                SQLiteManager.SELECT_TODOS + SQLiteManager.TABELA_DADOS_COMPL + "WHERE " + SQLiteManager.DADOS_COMPL_COL_ID_ANIMAL + " = " + idAnimal);
    }

    public int removerDadosCompl(DadosComplAnimal dadosComplAnimal) {
        return excluirRegistros(dadosComplAnimal.getId(), 1);
    }

    public int removerDadosCompl(int idAnimal) {
        return excluirRegistros(idAnimal, 2);
    }


    private int excluirRegistros(int id, int tipo) {
        bancoDados = gerenciador.getWritableDatabase();
        String coluna;

        //tipo = 1 (ID DadosCompl) | tipo = 2 (ID Animal)
        if (tipo == 1)
            coluna = SQLiteManager.DADOS_COMPL_COL_ID;
        else
            coluna = SQLiteManager.DADOS_COMPL_COL_ID_ANIMAL;

        int result = bancoDados.delete(SQLiteManager.TABELA_DADOS_COMPL,
                coluna + " = ? ",
                new String[]{String.valueOf(id)});

        bancoDados.close();

        return result;
    }


    private DadosComplAnimal getDadosFromCursor(Cursor cursor) {

        Calendar data = Calendar.getInstance();
        data.setTimeInMillis(Long.valueOf(cursor.getString(cursor.getColumnIndex(SQLiteManager.DADOS_COMPL_COL_DATA))));

        return new DadosComplAnimal(
                cursor.getInt(cursor.getColumnIndex(SQLiteManager.DADOS_COMPL_COL_ID)),
                data,
                cursor.getInt(cursor.getColumnIndex(SQLiteManager.DADOS_COMPL_COL_ID_ANIMAL)),
                cursor.getFloat(cursor.getColumnIndex(SQLiteManager.DADOS_COMPL_COL_PESO_VIVO)),
                cursor.getInt(cursor.getColumnIndex(SQLiteManager.DADOS_COMPL_COL_EEC)),
                cursor.getFloat(cursor.getColumnIndex(SQLiteManager.DADOS_COMPL_COL_CAMINHADA_HORIZONTAL)),
                cursor.getFloat(cursor.getColumnIndex(SQLiteManager.DADOS_COMPL_COL_CAMINHADA_VERTICAL)),
                cursor.getInt(cursor.getColumnIndex(SQLiteManager.DADOS_COMPL_COL_SEMANA_LACTACAO)),
                cursor.getInt(cursor.getColumnIndex(SQLiteManager.DADOS_COMPL_COL_ID_GRUPO)));
    }

    private ContentValues getContentValues(DadosComplAnimal dadosComplAnimal) {
        ContentValues dados = new ContentValues();
        dados.put(SQLiteManager.DADOS_COMPL_COL_DATA, dadosComplAnimal.getData().getTimeInMillis());
        dados.put(SQLiteManager.DADOS_COMPL_COL_ID_ANIMAL, dadosComplAnimal.getAnimal());
        dados.put(SQLiteManager.DADOS_COMPL_COL_PESO_VIVO, dadosComplAnimal.getPesoVivo());
        dados.put(SQLiteManager.DADOS_COMPL_COL_EEC, dadosComplAnimal.getEec());
        dados.put(SQLiteManager.DADOS_COMPL_COL_CAMINHADA_HORIZONTAL, dadosComplAnimal.getCaminadaHorizontal());
        dados.put(SQLiteManager.DADOS_COMPL_COL_CAMINHADA_VERTICAL, dadosComplAnimal.getCaminhadaVertical());
        dados.put(SQLiteManager.DADOS_COMPL_COL_SEMANA_LACTACAO, dadosComplAnimal.getSemanaLactacao());
        dados.put(SQLiteManager.DADOS_COMPL_COL_ID_GRUPO, dadosComplAnimal.getIdGrupo());

        return dados;
    }
}
