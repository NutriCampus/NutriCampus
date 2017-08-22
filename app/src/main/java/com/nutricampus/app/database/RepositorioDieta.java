package com.nutricampus.app.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.nutricampus.app.entities.Animal;
import com.nutricampus.app.entities.CompostoComPorcentagem;
import com.nutricampus.app.entities.CompostosAlimentares;
import com.nutricampus.app.entities.Dieta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Paulo Mateus on 20/08/17.
 * For project NutriCampus.
 * Contact: <paulomatew@gmail.com>
 */
public class RepositorioDieta {

    private SQLiteManager gerenciador;
    private SQLiteDatabase bancoDados;
    private Context context;

    public RepositorioDieta(Context context) {

        this.context = context;
        this.gerenciador = new SQLiteManager(context);
    }

    public boolean inserirDieta(Dieta dieta) {
        SQLiteDatabase bancoDados = gerenciador.getWritableDatabase();

        ContentValues dados = new ContentValues();
        if (dieta.id != 0) {
            dados.put(SQLiteManager.DIETA_ID, dieta.id);
        }

        dados.put(SQLiteManager.DIETA_IDENTIFICADOR, dieta.identificador);
        dados.put(SQLiteManager.DIETA_PB, dieta.proteinaBruta);
        dados.put(SQLiteManager.DIETA_PROPRIEDADE_ID, dieta.propriedade.getId());


        long retorno = bancoDados.insert(SQLiteManager.TABELA_DIETA, null, dados);
        bancoDados.close();
        int ret = (int) retorno;

        if (ret != -1) {
            /*private static final String SQL_CREATE_TABELA_DIETA_ANIMAL = "CREATE TABLE IF NOT EXISTS " + TABELA_DIETA_ANIMAL + "(" +
                    DIETA_ANIMAL_ID_FK + " INTEGER NOT NULL, " +
                    DIETA_ANIMAL_ID + " INTEGER NOT NULL" + " );";

            private static final String SQL_CREATE_TABELA_DIETA_COMPOSTO = "CREATE TABLE IF NOT EXISTS " + TABELA_DIETA_COMPOSTO + "(" +
                    DIETA_COMPOSTO_ID_FK + " INTEGER NOT NULL, " +
                    DIETA_COMPOSTO_ID + " INTEGER NOT NULL" + "," +
                    DIETA_COMPOSTO_PORCENTAGEM + REAL_NOT_NULL + " );";*/

            //Inserir animais
            bancoDados = gerenciador.getWritableDatabase();
            for (int i = 0; i < dieta.arrayAnimais.size(); i++) {
                dados = new ContentValues();
                dados.put(SQLiteManager.DIETA_ANIMAL_ID_FK, ret);
                dados.put(SQLiteManager.DIETA_ANIMAL_ID, dieta.arrayAnimais.get(i).getId());
                bancoDados.insert(SQLiteManager.TABELA_DIETA_ANIMAL, null, dados);
            }
            bancoDados.close();

            //Inserir Compostos
            bancoDados = gerenciador.getWritableDatabase();
            for (int i = 0; i < dieta.arrayObjetoDieta.size(); i++) {
                dados = new ContentValues();
                dados.put(SQLiteManager.DIETA_COMPOSTO_ID_FK, ret);
                dados.put(SQLiteManager.DIETA_COMPOSTO_ID, dieta.arrayObjetoDieta.get(i).composto.getId());
                dados.put(SQLiteManager.DIETA_COMPOSTO_PORCENTAGEM, dieta.arrayObjetoDieta.get(i).porcentagem);
                bancoDados.insert(SQLiteManager.TABELA_DIETA_COMPOSTO, null, dados);
            }
            bancoDados.close();
        }

        return true;
    }

    private List<Dieta> getListaDietas(String query) {
        SQLiteDatabase bancoDados = gerenciador.getReadableDatabase();

        ArrayList<Dieta> arrDietas = new ArrayList<>();
        try {
            Cursor c = bancoDados.rawQuery(query, null);

            if (c.moveToFirst()) {
                do {
                    //Dieta
                    //Dieta d = getDietaFromCursor(c);
                    Dieta d = new Dieta();
                    d.id = c.getInt(c.getColumnIndex(SQLiteManager.DIETA_ID));
                    int idProp = c.getInt(c.getColumnIndex(SQLiteManager.DIETA_PROPRIEDADE_ID));
                    d.propriedade = new RepositorioPropriedade(context).buscarPropriedade(idProp);
                    d.identificador = c.getString(c.getColumnIndex(SQLiteManager.DIETA_IDENTIFICADOR));
                    d.proteinaBruta = c.getDouble(c.getColumnIndex(SQLiteManager.DIETA_PB));


                    //Animais
                    d.arrayAnimais = getListaDietasAnimais(d.id);
                    //Compostos com porcentagens
                    d.arrayObjetoDieta = getListaDietasCompostos(d.id);

                    //Compostos utilizados
                    ArrayList<CompostosAlimentares> arr = new ArrayList<>();
                    for (int i = 0; i < d.arrayObjetoDieta.size(); i++) {
                        arr.add(d.arrayObjetoDieta.get(i).composto);
                    }
                    d.arrayCompostosSelecionados = arr;
                    arrDietas.add(d);
                } while (c.moveToNext());
                c.close();
            }

        } catch (Exception e) {
            Log.i("RepositorioDieta", e.toString());
            return Collections.emptyList();
        } finally {
            bancoDados.close();
        }

        return arrDietas;
    }

    public List<Dieta> buscarTodosDietas(String identificador) {
        return this.getListaDietas("SELECT * FROM " + SQLiteManager.TABELA_DIETA +
                " WHERE " + SQLiteManager.DIETA_IDENTIFICADOR + " LIKE '%" + identificador + "%'");
    }

    private ArrayList<Animal> getListaDietasAnimais(int id_dieta) {//id da dieta
        RepositorioAnimal repAnimal = new RepositorioAnimal(context);
        SQLiteDatabase bancoDados = gerenciador.getReadableDatabase();

        ArrayList<Animal> arrAnimal = new ArrayList<>();
        try {
            String query = "SELECT * FROM " + SQLiteManager.TABELA_DIETA_ANIMAL +
                    " WHERE " + SQLiteManager.DIETA_ANIMAL_ID_FK + "==" + id_dieta;
            Cursor c = bancoDados.rawQuery(query, null);

            if (c.moveToFirst()) {
                do {
                    int idAnimal = c.getInt(c.getColumnIndex(SQLiteManager.DIETA_ANIMAL_ID));
                    arrAnimal.add(repAnimal.buscarAnimalId(idAnimal));
                } while (c.moveToNext());
                c.close();
            }

        } catch (Exception e) {
            Log.i("RepositorioDieta_ANIMAL", e.toString());
            return new ArrayList<>();
        } finally {
            bancoDados.close();
        }

        return arrAnimal;
    }


    private ArrayList<CompostoComPorcentagem> getListaDietasCompostos(int id_dieta) {
        RepositorioCompostosAlimentares repCompostos = new RepositorioCompostosAlimentares(context);
        SQLiteDatabase bancoDados = gerenciador.getReadableDatabase();

        ArrayList<CompostoComPorcentagem> arrCompostos = new ArrayList<>();
        try {
            String query = "SELECT * FROM " + SQLiteManager.TABELA_DIETA_COMPOSTO +
                    " WHERE " + SQLiteManager.DIETA_COMPOSTO_ID_FK + "==" + id_dieta;
            Cursor c = bancoDados.rawQuery(query, null);

            if (c.moveToFirst()) {
                do {
                    int idDieta = c.getInt(c.getColumnIndex(SQLiteManager.DIETA_COMPOSTO_ID));
                    CompostoComPorcentagem cc = new CompostoComPorcentagem(
                            repCompostos.buscarCompostoAlimentarId(idDieta),
                            c.getDouble(c.getColumnIndex(SQLiteManager.DIETA_COMPOSTO_PORCENTAGEM))
                    );
                    arrCompostos.add(cc);
                } while (c.moveToNext());
                c.close();
            }

        } catch (Exception e) {
            Log.i("RepositorioDieta_COMP", e.toString());
            return new ArrayList<>();
        } finally {
            bancoDados.close();
        }

        return arrCompostos;
    }

    public void removerDieta(Dieta dieta) {
        SQLiteDatabase bancoDados = gerenciador.getWritableDatabase();
        bancoDados.delete(SQLiteManager.TABELA_DIETA,
                SQLiteManager.DIETA_ID + " = ? ",
                new String[]{String.valueOf(dieta.id)});

        bancoDados.delete(SQLiteManager.TABELA_DIETA_ANIMAL,
                SQLiteManager.DIETA_ANIMAL_ID_FK + " = ? ",
                new String[]{String.valueOf(dieta.id)});
        bancoDados.delete(SQLiteManager.TABELA_DIETA_COMPOSTO,
                SQLiteManager.DIETA_COMPOSTO_ID_FK + " = ? ",
                new String[]{String.valueOf(dieta.id)});

        bancoDados.close();

    }


    public Dieta buscarDietaById(int id) {
        return this.getListaDietas("SELECT * FROM " + SQLiteManager.TABELA_DIETA +
                " WHERE " + SQLiteManager.DIETA_ID + " == " + id).get(0);
    }

    public void atualizarDieta(Dieta dieta) {
        //Como podem haver muitas alterações, excluo tudo da parte animal/compostos
        removerDieta(dieta);
        inserirDieta(dieta);

        /*//Atualizando Dieta
        bancoDados = gerenciador.getWritableDatabase();
        ContentValues dados = new ContentValues();
        dados.put(SQLiteManager.DIETA_IDENTIFICADOR, dieta.identificador);
        dados.put(SQLiteManager.DIETA_PB, dieta.proteinaBruta);
        dados.put(SQLiteManager.DIETA_PROPRIEDADE_ID, dieta.propriedade.getId());
        int retorno = bancoDados.update(SQLiteManager.TABELA_DIETA,
                dados, SQLiteManager.DIETA_ID + " = ?",
                new String[]{String.valueOf(dieta.id)});

        bancoDados.close();

        //Inserindo animais
        bancoDados = gerenciador.getWritableDatabase();
        for (int i = 0; i < dieta.arrayAnimais.size(); i++) {
            dados = new ContentValues();
            dados.put(SQLiteManager.DIETA_ANIMAL_ID_FK, dieta.id);
            dados.put(SQLiteManager.DIETA_ANIMAL_ID, dieta.arrayAnimais.get(i).getId());
            bancoDados.insert(SQLiteManager.TABELA_DIETA_ANIMAL, null, dados);
        }
        bancoDados.close();

        //Inserindo compostos
        bancoDados = gerenciador.getWritableDatabase();
        for (int i = 0; i < dieta.arrayObjetoDieta.size(); i++) {
            dados = new ContentValues();
            dados.put(SQLiteManager.DIETA_COMPOSTO_ID_FK, dieta.id);
            dados.put(SQLiteManager.DIETA_COMPOSTO_ID, dieta.arrayObjetoDieta.get(i).composto.getId());
            dados.put(SQLiteManager.DIETA_COMPOSTO_PORCENTAGEM, dieta.arrayObjetoDieta.get(i).porcentagem);
            bancoDados.insert(SQLiteManager.TABELA_DIETA_COMPOSTO, null, dados);
        }
        bancoDados.close();*/

    }
}
