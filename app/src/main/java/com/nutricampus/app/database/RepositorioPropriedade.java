package com.nutricampus.app.database;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.nutricampus.app.entities.Propriedade;
import com.nutricampus.app.entities.Proprietario;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Felipe on 05/07/2017.
 * For project NutriCampus.
 * Contact: <felipeguimaraes540@gmail.com>
 */

public class RepositorioPropriedade {

    private SQLiteManager gerenciador;
    private SQLiteDatabase bancoDados;
    Context context;

    public RepositorioPropriedade(Context context) {
        this.context = context;
        gerenciador = new SQLiteManager(context);
    }

    public int inserirPropriedade(Propriedade propriedade) {
        bancoDados = gerenciador.getWritableDatabase();

        ContentValues dados = new ContentValues();
        dados.put(SQLiteManager.PROPRIEDADE_COL_NOME, propriedade.getNome());
        dados.put(SQLiteManager.PROPRIEDADE_COL_TELEFONE, propriedade.getTelefone());
        dados.put(SQLiteManager.PROPRIEDADE_COL_LOGRADOURO, propriedade.getLogradouro());
        dados.put(SQLiteManager.PROPRIEDADE_COL_NUMERO, propriedade.getNumero());
        dados.put(SQLiteManager.PROPRIEDADE_COL_BAIRRO, propriedade.getBairro());
        dados.put(SQLiteManager.PROPRIEDADE_COL_CIDADE, propriedade.getCidade());
        dados.put(SQLiteManager.PROPRIEDADE_COL_ESTADO, propriedade.getEstado());
        dados.put(SQLiteManager.PROPRIEDADE_COL_CEP, propriedade.getCep());
        dados.put(SQLiteManager.PROPRIEDADE_COL_ID_PROPRIETARIO, propriedade.getIdProprietario());


        long retorno = bancoDados.insert(SQLiteManager.TABELA_PROPRIEDADE, null, dados);
        bancoDados.close();

        // retorna o id do elemento inserido
        return (int) retorno;
    }

    public Propriedade buscarPropriedade(String nome) {
        return buscarPropriedade(nome, null);
    }

    public Propriedade buscarPropriedade(String nome, String bairro) {
        bancoDados = gerenciador.getReadableDatabase();

        String colunas_where = SQLiteManager.PROPRIEDADE_COL_NOME + "= ?";
        String[] valores_where = new String[]{String.valueOf(nome)};

        if (bairro != null) {
            colunas_where += " AND " + SQLiteManager.PROPRIEDADE_COL_BAIRRO + "= ?";
            valores_where = new String[]{String.valueOf(nome), String.valueOf(bairro)};
        }

        Cursor cursor = bancoDados.query(SQLiteManager.TABELA_PROPRIEDADE, new String[]{
                        SQLiteManager.PROPRIEDADE_COL_ID,
                        SQLiteManager.PROPRIEDADE_COL_NOME,
                        SQLiteManager.PROPRIEDADE_COL_TELEFONE,
                        SQLiteManager.PROPRIEDADE_COL_LOGRADOURO,
                        SQLiteManager.PROPRIEDADE_COL_NUMERO,
                        SQLiteManager.PROPRIEDADE_COL_BAIRRO,
                        SQLiteManager.PROPRIEDADE_COL_CIDADE,
                        SQLiteManager.PROPRIEDADE_COL_ESTADO,
                        SQLiteManager.PROPRIEDADE_COL_CEP,},
                colunas_where,
                valores_where, null, null, null, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            return new Propriedade(
                    cursor.getInt(cursor.getColumnIndex(SQLiteManager.PROPRIEDADE_COL_ID)),
                    cursor.getString(cursor.getColumnIndex(SQLiteManager.PROPRIEDADE_COL_NOME)),
                    cursor.getString(cursor.getColumnIndex(SQLiteManager.PROPRIEDADE_COL_TELEFONE)),
                    cursor.getString(cursor.getColumnIndex(SQLiteManager.PROPRIEDADE_COL_LOGRADOURO)),
                    cursor.getString(cursor.getColumnIndex(SQLiteManager.PROPRIEDADE_COL_BAIRRO)),
                    cursor.getString(cursor.getColumnIndex(SQLiteManager.PROPRIEDADE_COL_CEP)),
                    cursor.getString(cursor.getColumnIndex(SQLiteManager.PROPRIEDADE_COL_CIDADE)),
                    cursor.getString(cursor.getColumnIndex(SQLiteManager.PROPRIEDADE_COL_ESTADO)),
                    cursor.getString(cursor.getColumnIndex(SQLiteManager.PROPRIEDADE_COL_NUMERO)),
                    cursor.getInt(cursor.getColumnIndex(SQLiteManager.PROPRIEDADE_COL_ID_PROPRIETARIO)));
        }
        cursor.close();
        return null;
    }

    public List<Propriedade> buscarTodasPropriedades() {

        bancoDados = gerenciador.getReadableDatabase();

        ArrayList<Propriedade> propriedades = new ArrayList<>();
        String getPropriedades = "SELECT * FROM " + SQLiteManager.TABELA_PROPRIEDADE;

        try {
            Cursor c = bancoDados.rawQuery(getPropriedades, null);

            if (c.moveToFirst()) {
                do {
                    Propriedade p = new Propriedade();
                    p.setId(c.getInt(c.getColumnIndex(SQLiteManager.PROPRIEDADE_COL_ID)));
                    p.setNome(c.getString(c.getColumnIndex(SQLiteManager.PROPRIEDADE_COL_NOME)));
                    p.setTelefone(c.getString(c.getColumnIndex(SQLiteManager.PROPRIEDADE_COL_TELEFONE)));
                    p.setLogradouro(c.getString(c.getColumnIndex(SQLiteManager.PROPRIEDADE_COL_LOGRADOURO)));
                    p.setNumero(c.getString(c.getColumnIndex(SQLiteManager.PROPRIEDADE_COL_NUMERO)));
                    p.setBairro(c.getString(c.getColumnIndex(SQLiteManager.PROPRIEDADE_COL_BAIRRO)));
                    p.setCidade(c.getString(c.getColumnIndex(SQLiteManager.PROPRIEDADE_COL_CIDADE)));
                    p.setEstado(c.getString(c.getColumnIndex(SQLiteManager.PROPRIEDADE_COL_ESTADO)));
                    p.setCep(c.getString(c.getColumnIndex(SQLiteManager.PROPRIEDADE_COL_CEP)));
                    p.setIdProprietario(c.getInt(c.getColumnIndex(SQLiteManager.PROPRIEDADE_COL_ID_PROPRIETARIO)));
                    Log.d("KK", c.getString(c.getColumnIndex(SQLiteManager.PROPRIEDADE_COL_ID)));

                    //Verificar o atributo id_proprietario
                    propriedades.add(p);
                } while (c.moveToNext());
                c.close();
            }

        } catch (Exception e) {
            Log.i("RepositorioPropriedade", e.toString());
            return Collections.emptyList();
        } finally {
            bancoDados.close();
        }

        return propriedades;
    }

    public List<Propriedade> buscarPropriedadesPorNome(String nome) {

        bancoDados = gerenciador.getReadableDatabase();

        ArrayList<Propriedade> propriedades = new ArrayList<>();
        String getPropriedades = "SELECT * FROM " + SQLiteManager.TABELA_PROPRIEDADE +
                " WHERE " + SQLiteManager.PROPRIEDADE_COL_NOME + " LIKE '%" + nome + "%'";
        Log.i("L",getPropriedades);
        try {
            Cursor c = bancoDados.rawQuery(getPropriedades, null);

            if (c.moveToFirst()) {
                do {
                    Propriedade p = new Propriedade();
                    p.setId(c.getInt(c.getColumnIndex(SQLiteManager.PROPRIEDADE_COL_ID)));
                    p.setNome(c.getString(c.getColumnIndex(SQLiteManager.PROPRIEDADE_COL_NOME)));
                    p.setTelefone(c.getString(c.getColumnIndex(SQLiteManager.PROPRIEDADE_COL_TELEFONE)));
                    p.setLogradouro(c.getString(c.getColumnIndex(SQLiteManager.PROPRIEDADE_COL_LOGRADOURO)));
                    p.setNumero(c.getString(c.getColumnIndex(SQLiteManager.PROPRIEDADE_COL_NUMERO)));
                    p.setBairro(c.getString(c.getColumnIndex(SQLiteManager.PROPRIEDADE_COL_BAIRRO)));
                    p.setCidade(c.getString(c.getColumnIndex(SQLiteManager.PROPRIEDADE_COL_CIDADE)));
                    p.setEstado(c.getString(c.getColumnIndex(SQLiteManager.PROPRIEDADE_COL_ESTADO)));
                    p.setCep(c.getString(c.getColumnIndex(SQLiteManager.PROPRIEDADE_COL_CEP)));
                    p.setIdProprietario(c.getInt(c.getColumnIndex(SQLiteManager.PROPRIEDADE_COL_ID_PROPRIETARIO)));
                    Log.d("KK", c.getString(c.getColumnIndex(SQLiteManager.PROPRIEDADE_COL_ID)));

                    //Verificar o atributo id_proprietario
                    propriedades.add(p);
                } while (c.moveToNext());
                c.close();
            }

        } catch (Exception e) {
            Log.i("RepositorioPropriedade", e.toString());
            return Collections.emptyList();
        } finally {
            bancoDados.close();
        }

        return propriedades;
    }


    public boolean atualizarPropriedade(Propriedade propriedade) {
        bancoDados = gerenciador.getWritableDatabase();

        ContentValues dados = new ContentValues();
        dados.put(SQLiteManager.PROPRIEDADE_COL_ID,propriedade.getId());
        dados.put(SQLiteManager.PROPRIEDADE_COL_NOME, propriedade.getNome());
        dados.put(SQLiteManager.PROPRIEDADE_COL_TELEFONE, propriedade.getTelefone());
        dados.put(SQLiteManager.PROPRIEDADE_COL_LOGRADOURO, propriedade.getLogradouro());
        dados.put(SQLiteManager.PROPRIEDADE_COL_NUMERO, propriedade.getNumero());
        dados.put(SQLiteManager.PROPRIEDADE_COL_BAIRRO, propriedade.getBairro());
        dados.put(SQLiteManager.PROPRIEDADE_COL_CIDADE, propriedade.getCidade());
        dados.put(SQLiteManager.PROPRIEDADE_COL_ESTADO, propriedade.getEstado());
        dados.put(SQLiteManager.PROPRIEDADE_COL_CEP, propriedade.getCep());
        dados.put(SQLiteManager.PROPRIEDADE_COL_ID_PROPRIETARIO, propriedade.getIdProprietario());
        Log.i("I",propriedade.getId() + "  " + propriedade.getIdProprietario());

        int retorno = bancoDados.update(SQLiteManager.TABELA_PROPRIEDADE,
                dados, SQLiteManager.PROPRIEDADE_COL_ID + " = ?",
                new String[]{String.valueOf(propriedade.getId())});

        bancoDados.close();

        return (retorno > 0);

    }

    public void removerPropriedade(Propriedade propriedade) {
        bancoDados = gerenciador.getWritableDatabase();
        bancoDados.delete(SQLiteManager.TABELA_PROPRIEDADE,
                SQLiteManager.PROPRIEDADE_COL_ID + " = ? ",
                new String[]{String.valueOf(propriedade.getId())});

        bancoDados.close();
    }


}
