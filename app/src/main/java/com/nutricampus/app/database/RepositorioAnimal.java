package com.nutricampus.app.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.nutricampus.app.entities.Animal;
import com.nutricampus.app.utils.Conversor;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * Created by Diego Bezerra on 14/06/17.
 * For project NutriCampus.
 * Contact: <diego.defb@gmail.com>
 */

public class RepositorioAnimal {

    private SQLiteManager gerenciador;
    private SQLiteDatabase bancoDados;

    public RepositorioAnimal(Context context) {
        gerenciador = new SQLiteManager(context);
    }

    public int inserirAnimal(Animal animal) {
        bancoDados = gerenciador.getWritableDatabase();

        ContentValues dados = this.getContentValues(animal);

        long retorno = bancoDados.insert(SQLiteManager.TABELA_ANIMAL, null, dados);
        bancoDados.close();

        // retorna o id do elemento inserido
        return (int) retorno;
    }

    private List<Animal> getListaAnimais(String query) {
        bancoDados = gerenciador.getReadableDatabase();

        ArrayList<Animal> animais = new ArrayList<>();
        try {
            Cursor c = bancoDados.rawQuery(query, null);

            if (c.moveToFirst()) {
                do {
                    Animal a = getDadosFromCursor(c);
                    animais.add(a);
                } while (c.moveToNext());
                c.close();
            }

        } catch (Exception e) {
            Log.i("RepositorioGrupo", e.toString());
            return Collections.emptyList();
        } finally {
            bancoDados.close();
        }

        return animais;
    }

    public Animal buscarAnimal(String identificador, int idPropriedade) {
        bancoDados = gerenciador.getReadableDatabase();

        String colunasWhere = SQLiteManager.ANIMAL_COL_IDENTIFICADOR + "= ? AND " +
                SQLiteManager.ANIMAL_COL_ID_PROPRIEDADE + "= ?";
        String[] valoresWhere = new String[]{identificador, String.valueOf(idPropriedade)};


        Cursor cursor = bancoDados.query(SQLiteManager.TABELA_ANIMAL, new String[]{
                        SQLiteManager.ANIMAL_COL_ID,
                        SQLiteManager.ANIMAL_COL_IDENTIFICADOR,
                        SQLiteManager.ANIMAL_COL_ID_PROPRIEDADE,
                        SQLiteManager.ANIMAL_COL_DATA_NASCIMENTO,
                        SQLiteManager.ANIMAL_COL_IS_ATIVO,
                        SQLiteManager.ANIMAL_COL_ID_USUARIO},
                colunasWhere,
                valoresWhere, null, null, null, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            return getDadosFromCursor(cursor);
        }
        cursor.close();
        return null;
    }

    public Animal buscarAnimalId(int idAnimal) {
        bancoDados = gerenciador.getReadableDatabase();

        String colunasWhere = SQLiteManager.ANIMAL_COL_ID + " = ?";
        String[] valoresWhere = new String[]{String.valueOf(idAnimal)};


        Cursor cursor = bancoDados.query(SQLiteManager.TABELA_ANIMAL, new String[]{
                        SQLiteManager.ANIMAL_COL_ID,
                        SQLiteManager.ANIMAL_COL_IDENTIFICADOR,
                        SQLiteManager.ANIMAL_COL_ID_PROPRIEDADE,
                        SQLiteManager.ANIMAL_COL_DATA_NASCIMENTO,
                        SQLiteManager.ANIMAL_COL_IS_ATIVO,
                        SQLiteManager.ANIMAL_COL_ID_USUARIO},
                colunasWhere,
                valoresWhere, null, null, null, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            return getDadosFromCursor(cursor);
        }
        cursor.close();
        return null;
    }

    public boolean atualizarAnimal(Animal animal) {
        bancoDados = gerenciador.getWritableDatabase();

        int retorno = bancoDados.update(SQLiteManager.TABELA_ANIMAL,
                getContentValues(animal), SQLiteManager.ANIMAL_COL_ID + " = ?",
                new String[]{String.valueOf(animal.getId())});

        bancoDados.close();

        return (retorno > 0);
    }

    public List<Animal> buscarTodosAnimais(String identificador) {
        return this.getListaAnimais("SELECT * FROM " + SQLiteManager.TABELA_ANIMAL +
                " WHERE " + SQLiteManager.ANIMAL_COL_IDENTIFICADOR + " LIKE '%" + identificador + "%'");
    }

    public List<Animal> buscarPorPropridade(int idPropriedade) {
        return this.getListaAnimais(SQLiteManager.SELECT_TODOS + SQLiteManager.TABELA_ANIMAL +
                " WHERE (" + SQLiteManager.ANIMAL_COL_ID_PROPRIEDADE + " = " + idPropriedade + ")");
    }

    public List<Animal> buscarPorIdentificador(int idPropriedade, String identificador) {
        return this.getListaAnimais(SQLiteManager.SELECT_TODOS + SQLiteManager.TABELA_ANIMAL +
                " WHERE (" + SQLiteManager.ANIMAL_COL_ID_PROPRIEDADE + " = " + idPropriedade + ")" +
                " AND (" + SQLiteManager.ANIMAL_COL_IDENTIFICADOR + " LIKE '%" + identificador + "%')");
    }


    public int removerAnimal(Animal animal) {
        return excluirRegistros(animal.getId(), 1);
    }

    public int removerAnimalPropriedade(int idPropriedade) {
        return excluirRegistros(idPropriedade, 2);
    }

    private int excluirRegistros(int id, int tipo) {
        bancoDados = gerenciador.getWritableDatabase();
        String coluna;

        //tipo = 1 (ID animal) | tipo = 2 (ID Usuario)
        if (tipo == 1)
            coluna = SQLiteManager.ANIMAL_COL_ID;
        else
            coluna = SQLiteManager.ANIMAL_COL_ID_PROPRIEDADE;

        int result = bancoDados.delete(SQLiteManager.TABELA_ANIMAL,
                coluna + " = ? ",
                new String[]{String.valueOf(id)});

        bancoDados.close();

        return result;
    }

    public void removerTodos() {
        bancoDados = gerenciador.getWritableDatabase();
        bancoDados.delete(SQLiteManager.TABELA_ANIMAL,
                SQLiteManager.ANIMAL_COL_ID + " > ? ",
                new String[]{String.valueOf("-1")});

        bancoDados.close();

    }

    private Animal getDadosFromCursor(Cursor cursor) {
        Calendar data = Calendar.getInstance();
        data.setTimeInMillis(Long.valueOf(cursor.getString(cursor.getColumnIndex(SQLiteManager.ANIMAL_COL_DATA_NASCIMENTO))));

        return new Animal(
                cursor.getInt(cursor.getColumnIndex(SQLiteManager.ANIMAL_COL_ID)),
                cursor.getString(cursor.getColumnIndex(SQLiteManager.ANIMAL_COL_IDENTIFICADOR)),
                cursor.getInt(cursor.getColumnIndex(SQLiteManager.ANIMAL_COL_ID_PROPRIEDADE)),
                data,
                Conversor.intToBoolean(Integer.parseInt(cursor.getString(cursor.getColumnIndex(SQLiteManager.ANIMAL_COL_IS_ATIVO)))),
                cursor.getInt(cursor.getColumnIndex(SQLiteManager.ANIMAL_COL_ID_USUARIO)));
    }

    private ContentValues getContentValues(Animal animal) {
        ContentValues dados = new ContentValues();
        dados.put(SQLiteManager.ANIMAL_COL_IDENTIFICADOR, animal.getIndentificador());
        dados.put(SQLiteManager.ANIMAL_COL_ID_PROPRIEDADE, animal.getPropriedade());
        dados.put(SQLiteManager.ANIMAL_COL_DATA_NASCIMENTO, animal.getDataDeNascimento().getTimeInMillis());
        dados.put(SQLiteManager.ANIMAL_COL_IS_ATIVO, animal.isAtivo());
        dados.put(SQLiteManager.ANIMAL_COL_ID_USUARIO, animal.getIdUsuario());

        return dados;
    }
}
