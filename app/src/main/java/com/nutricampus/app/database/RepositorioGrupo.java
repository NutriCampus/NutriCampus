package com.nutricampus.app.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.nutricampus.app.entities.Grupo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Felipe on 18/08/2017.
 * For project NutriCampus.
 * Contact: <felipeguimaraes540@gmail.com>
 */

public class RepositorioGrupo {

    private SQLiteManager gerenciador;
    private SQLiteDatabase bancoDados;

    public RepositorioGrupo(Context context) {
        gerenciador = new SQLiteManager(context);
    }

    public int inserirGrupo(Grupo grupo) {
        bancoDados = gerenciador.getWritableDatabase();

        ContentValues dados = this.getContentValues(grupo);

        long retorno = bancoDados.insert(SQLiteManager.TABELA_GRUPO, null, dados);
        bancoDados.close();

        // retorna o id do elemento inserido
        return (int) retorno;
    }

    private List<Grupo> getListaGrupos(String query) {
        bancoDados = gerenciador.getReadableDatabase();

        ArrayList<Grupo> grupos = new ArrayList<>();
        try {
            Cursor c = bancoDados.rawQuery(query, null);

            if (c.moveToFirst()) {
                do {
                    Grupo g = getDadosFromCursor(c);
                    grupos.add(g);
                } while (c.moveToNext());
                c.close();
            }

        } catch (Exception e) {
            Log.i("RepositorioGrupo", e.toString());
            return Collections.emptyList();
        } finally {
            bancoDados.close();
        }

        return grupos;
    }

    public Grupo buscarGrupo(int id) {
        bancoDados = gerenciador.getReadableDatabase();

        String colunasWhere = SQLiteManager.GRUPO_COL_ID + "= ?";
        String[] valoresWhere = new String[]{String.valueOf(id)};

        Cursor cursor = bancoDados.query(SQLiteManager.TABELA_GRUPO, new String[]{
                        SQLiteManager.GRUPO_COL_ID,
                        SQLiteManager.GRUPO_COL_IDENTIFICADOR,
                        SQLiteManager.GRUPO_COL_OBSERVACAO,
                        SQLiteManager.GRUPO_COL_ID_USUARIO},
                colunasWhere,
                valoresWhere, null, null, null, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            return getDadosFromCursor(cursor);
        }
        cursor.close();
        return null;
    }

    public Grupo buscarGrupo(String identificador) {
        bancoDados = gerenciador.getReadableDatabase();

        String colunasWhere = SQLiteManager.GRUPO_COL_IDENTIFICADOR + "= ?";
        String[] valoresWhere = new String[]{identificador};

        Cursor cursor = bancoDados.query(SQLiteManager.TABELA_GRUPO, new String[]{
                        SQLiteManager.GRUPO_COL_ID,
                        SQLiteManager.GRUPO_COL_IDENTIFICADOR,
                        SQLiteManager.GRUPO_COL_OBSERVACAO,
                        SQLiteManager.GRUPO_COL_ID_USUARIO},
                colunasWhere,
                valoresWhere, null, null, null, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            return getDadosFromCursor(cursor);
        }
        cursor.close();
        return null;
    }

    public boolean atualizarGrupo(Grupo grupo) {
        bancoDados = gerenciador.getWritableDatabase();

        int retorno = bancoDados.update(SQLiteManager.TABELA_GRUPO,
                getContentValues(grupo), SQLiteManager.GRUPO_COL_ID + " = ?",
                new String[]{String.valueOf(grupo.getId())});

        bancoDados.close();

        return (retorno > 0);
    }

    public List<Grupo> buscarTodosGrupos() {
        return this.getListaGrupos("SELECT * FROM " + SQLiteManager.TABELA_GRUPO);
    }

    public List<Grupo> buscarPorUsuario(int idUsuario) {
        return this.getListaGrupos(SQLiteManager.SELECT_TODOS + SQLiteManager.TABELA_GRUPO +
                " WHERE (" + SQLiteManager.GRUPO_COL_ID_USUARIO + " = " + idUsuario + ")");
    }

    public int removerGrupo(Grupo grupo) {
        return excluirRegistros(grupo.getId(), 1);
    }

    public int removerGrupoUsuario(int idUsuario) {
        return excluirRegistros(idUsuario, 2);
    }

    private int excluirRegistros(int id, int tipo) {
        bancoDados = gerenciador.getWritableDatabase();
        String coluna;

        //tipo = 1 (ID Grupo) | tipo = 2 (ID Usuario)
        if(tipo == 1)
            coluna = SQLiteManager.GRUPO_COL_ID;
        else
            coluna = SQLiteManager.GRUPO_COL_ID_USUARIO;

        int result = bancoDados.delete(SQLiteManager.TABELA_GRUPO,
                coluna + " = ? ",
                new String[]{String.valueOf(id)});

        bancoDados.close();

        return result;
    }

    private Grupo getDadosFromCursor(Cursor cursor) {

        return new Grupo(
                cursor.getInt(cursor.getColumnIndex(SQLiteManager.GRUPO_COL_ID)),
                cursor.getString(cursor.getColumnIndex(SQLiteManager.GRUPO_COL_IDENTIFICADOR)),
                cursor.getString(cursor.getColumnIndex(SQLiteManager.GRUPO_COL_OBSERVACAO)),
                cursor.getInt(cursor.getColumnIndex(SQLiteManager.GRUPO_COL_ID_USUARIO)));

    }

    private ContentValues getContentValues(Grupo grupo) {
        ContentValues dados = new ContentValues();
        dados.put(SQLiteManager.GRUPO_COL_IDENTIFICADOR, grupo.getIdentificador());
        dados.put(SQLiteManager.GRUPO_COL_OBSERVACAO, grupo.getObservacao());
        dados.put(SQLiteManager.GRUPO_COL_ID_USUARIO, grupo.getIdUsuario());

        return dados;
    }

}
