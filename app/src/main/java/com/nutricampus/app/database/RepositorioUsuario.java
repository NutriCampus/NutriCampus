package com.nutricampus.app.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;
import android.util.StringBuilderPrinter;

import com.nutricampus.app.entities.Session;
import com.nutricampus.app.entities.Usuario;

/**
 * Created by Diego Bezerra on 20/06/2017.
 * For project NutriCampus.
 * Contact: <diego.defb@gmail.com>
 */

public class RepositorioUsuario {

    public boolean inserirUsuario(Usuario usuario) {

        ContentValues dados = new ContentValues();

        dados.put(SQLiteManager.usuario_col_crmv, usuario.getCRMV());
        dados.put(SQLiteManager.usuario_col_cpf, usuario.getCpf());
        dados.put(SQLiteManager.usuario_col_nome, usuario.getNome());
        dados.put(SQLiteManager.usuario_col_email, usuario.getEmail());
        dados.put(SQLiteManager.usuario_col_senha, usuario.getSenha());

        long retorno;
        try {
            retorno = SQLiteManager.banco.insertOrThrow(SQLiteManager.tabelaUsuario, null, dados);
        } catch (Exception e) {
            retorno = 0;
        }

        if (retorno != 0 && retorno != -1) {
            if (Session.DEBBUG)
                Log.v(this.getClass().getName(), "inserirUsuario(): " +
                        "CRMV=" + usuario.getCRMV() +
                        " | Nome= " + usuario.getNome() +
                        " | E-mail= " + usuario.getEmail());

            System.out.println("Inserido com sucesso!");

            return true;

        } else {

            return false;
        }
    }

    public Usuario buscarUsuario(String crmv) {

        Cursor cursor = SQLiteManager.banco.query(
                SQLiteManager.tabelaUsuario, null, SQLiteManager.usuario_col_crmv + " = ?",
                new String[]{crmv}, null, null, null);

        Usuario usuario = null;
        if (cursor.moveToFirst()) {
            usuario = new Usuario(
                    cursor.getInt(cursor.getColumnIndex(SQLiteManager.usuario_col_id)),
                    cursor.getString(cursor.getColumnIndex(SQLiteManager.usuario_col_crmv)),
                    cursor.getString(cursor.getColumnIndex(SQLiteManager.usuario_col_cpf)),
                    cursor.getString(cursor.getColumnIndex(SQLiteManager.usuario_col_nome)),
                    cursor.getString(cursor.getColumnIndex(SQLiteManager.usuario_col_email)),
                    cursor.getString(cursor.getColumnIndex(SQLiteManager.usuario_col_senha)));
        }

        if (Session.DEBBUG) {
            Log.v(this.getClass().getName(), "buscarUsuario(): " +
                    "ID=" + usuario.getId() +
                    " | CRMV=" + usuario.getCRMV() +
                    " | CPF=" + usuario.getCpf() +
                    " | Nome= " + usuario.getNome() +
                    " | E-mail= " + usuario.getEmail());

            try {
                cursor.close();

            } catch (Exception e) {

            }

            return usuario;

        } else {
            try {
                cursor.close();

            } catch (Exception e) {

            }

            return null;

        }
    }

    public boolean atualizarUsuario(Usuario usuario) {
        ContentValues dados = new ContentValues();

        dados.put(SQLiteManager.usuario_col_nome, usuario.getNome());
        dados.put(SQLiteManager.usuario_col_email, usuario.getEmail());
        dados.put(SQLiteManager.usuario_col_senha, usuario.getSenha());

        long retorno = SQLiteManager.banco.update(SQLiteManager.tabelaUsuario,
                dados, SQLiteManager.usuario_col_id, new String[]{String.valueOf(usuario.getId())});

        if (retorno != 0 && retorno != -1) {

            if (Session.DEBBUG)
                Log.v(this.getClass().getName(), "atualizarUsuario(): " +
                        "ID=" + usuario.getId() +
                        " | CRMV= " + usuario.getCRMV() +
                        " | CPF= " + usuario.getCpf() +
                        " | Nome= " + usuario.getNome() +
                        " | E-mail= " + usuario.getEmail());

            return true;

        } else {

            return false;
        }

    }

    public void removerUsuario(Usuario usuario) {
        SQLiteManager.banco.delete(SQLiteManager.tabelaUsuario, SQLiteManager.usuario_col_id + " = ?",
                new String[]{String.valueOf(usuario.getId())});
    }

}
