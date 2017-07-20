package com.nutricampus.app.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.nutricampus.app.entities.Animal;
import com.nutricampus.app.entities.ProducaoDeLeite;
import com.nutricampus.app.utils.Conversor;

import java.sql.Date;
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


        //Log.i("ADICIONAR ANIMAL", animal.getIdProprietario() + "");
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
                    Calendar data = Calendar.getInstance();
                    data.setTimeInMillis(Long.valueOf(c.getString(c.getColumnIndex(SQLiteManager.ANIMAL_COL_DATA_NASCIMENTO))));

                    Animal a = new Animal();
                    a.setId(c.getInt(c.getColumnIndex(SQLiteManager.ANIMAL_COL_ID)));
                    a.setIndentificador(c.getString(c.getColumnIndex(SQLiteManager.ANIMAL_COL_IDENTIFICADOR)));
                    a.setPropriedade(c.getInt(c.getColumnIndex(SQLiteManager.ANIMAL_COL_ID_PROPRIEDADE)));
                    a.setDataDeNascimento(data);
                    a.setAtivo(Conversor.StringToBoolean(c.getString(c.getColumnIndex(SQLiteManager.ANIMAL_COL_IS_ATIVO))));
                    animais.add(a);
                } while (c.moveToNext());
                c.close();
            }

        } catch (Exception e) {
            Log.i("RepositorioAnimal", e.toString());
            return Collections.emptyList();
        } finally {
            bancoDados.close();
        }

        return animais;
    }

    public Animal buscarAnimal(String identificador) {
        bancoDados = gerenciador.getReadableDatabase();

        String colunasWhere = SQLiteManager.ANIMAL_COL_IDENTIFICADOR + "= ?";
        String[] valoresWhere = new String[]{String.valueOf(identificador)};


        Cursor cursor = bancoDados.query(SQLiteManager.TABELA_ANIMAL, new String[]{
                        SQLiteManager.ANIMAL_COL_ID,
                        SQLiteManager.ANIMAL_COL_IDENTIFICADOR,
                        SQLiteManager.ANIMAL_COL_ID_PROPRIEDADE,
                        SQLiteManager.ANIMAL_COL_DATA_NASCIMENTO,
                        SQLiteManager.ANIMAL_COL_IS_ATIVO},
                colunasWhere,
                valoresWhere, null, null, null, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            Calendar data = Calendar.getInstance();
            data.setTimeInMillis(Long.valueOf(cursor.getString(cursor.getColumnIndex(SQLiteManager.ANIMAL_COL_DATA_NASCIMENTO))));

            return new Animal(
                    cursor.getInt(cursor.getColumnIndex(SQLiteManager.ANIMAL_COL_ID)),
                    cursor.getString(cursor.getColumnIndex(SQLiteManager.ANIMAL_COL_IDENTIFICADOR)),
                    cursor.getInt(cursor.getColumnIndex(SQLiteManager.ANIMAL_COL_ID_PROPRIEDADE)),
                    data,
                    Conversor.StringToBoolean(cursor.getString(cursor.getColumnIndex(SQLiteManager.ANIMAL_COL_IS_ATIVO))));
        }
        cursor.close();
        return null;
    }

    public boolean atualizarAnimal(Animal animal) {
        bancoDados = gerenciador.getWritableDatabase();

        ContentValues dados = new ContentValues();

        dados.put(SQLiteManager.ANIMAL_COL_IDENTIFICADOR, animal.getIndentificador());
        dados.put(SQLiteManager.ANIMAL_COL_ID_PROPRIEDADE, animal.getPropriedade());
        //Verificar...
        dados.put(SQLiteManager.ANIMAL_COL_DATA_NASCIMENTO, animal.getDataDeNascimento().getTimeInMillis());
        dados.put(SQLiteManager.ANIMAL_COL_IS_ATIVO, animal.isAtivo());

        int retorno = bancoDados.update(SQLiteManager.TABELA_ANIMAL,
                dados, SQLiteManager.ANIMAL_COL_ID + " = ?",
                new String[]{String.valueOf(animal.getId())});

        bancoDados.close();

        return (retorno > 0);
    }

    public List<Animal> buscarTodosAnimais() {
        return this.getListaAnimais("SELECT * FROM " + SQLiteManager.TABELA_ANIMAL);
    }

    public void removerAnimal(Animal animal) {
        bancoDados = gerenciador.getWritableDatabase();
        bancoDados.delete(SQLiteManager.TABELA_ANIMAL,
                SQLiteManager.ANIMAL_COL_ID + " = ? ",
                new String[]{String.valueOf(animal.getId())});

        bancoDados.close();
    }

    private ContentValues getContentValues(Animal animal) {
        ContentValues dados = new ContentValues();
        dados.put(SQLiteManager.ANIMAL_COL_IDENTIFICADOR, animal.getIndentificador());
        dados.put(SQLiteManager.ANIMAL_COL_ID_PROPRIEDADE, animal.getPropriedade());
        dados.put(SQLiteManager.ANIMAL_COL_DATA_NASCIMENTO, animal.getDataDeNascimento().getTimeInMillis());
        dados.put(SQLiteManager.ANIMAL_COL_IS_ATIVO, animal.isAtivo());
        dados.put(SQLiteManager.ANIMAL_COL_ID_USUARIO, animal.getId_usuario());

        return dados;
    }
}
