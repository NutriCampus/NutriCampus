package com.nutricampus.app.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.nutricampus.app.entities.Usuario;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Felipe on 25/06/2017.
 * For project NutriCampus.
 */

public class RepositorioUsuario {

    private SQLiteManager gerenciador;
    private SQLiteDatabase bancoDados;

    public RepositorioUsuario(Context context) {
        gerenciador = new SQLiteManager(context);
    }

    public boolean inserirUsuario(Usuario usuario) {
        bancoDados = gerenciador.getWritableDatabase();

        ContentValues dados = new ContentValues();
        dados.put(SQLiteManager.USUARIO_COL_CRMV, usuario.getCRMV());
        dados.put(SQLiteManager.USUARIO_COL_CPF, usuario.getCpf());
        dados.put(SQLiteManager.USUARIO_COL_NOME, usuario.getNome());
        dados.put(SQLiteManager.USUARIO_COL_EMAIL, usuario.getEmail());
        dados.put(SQLiteManager.USUARIO_COL_SENHA, usuario.getSenha());


        long retorno = bancoDados.insert(SQLiteManager.TABELA_USUARIO, null, dados);
        bancoDados.close();

        if(retorno == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Usuario buscarUsuario(String crmv) {
        return buscarUsuario(crmv,null);
    }

    public Usuario buscarUsuario(String crmv, String senha) {
        bancoDados = gerenciador.getReadableDatabase();

        String colunas_where = SQLiteManager.USUARIO_COL_CRMV + "= ?";
        String[] valores_where = new String[] { String.valueOf(crmv)};

        if(senha != null){
            colunas_where += " AND " +SQLiteManager.USUARIO_COL_SENHA + "= ?";
            valores_where = new String[] { String.valueOf(crmv),String.valueOf(senha)};
        }

        Cursor cursor = bancoDados.query(SQLiteManager.TABELA_USUARIO, new String[] {
                        SQLiteManager.USUARIO_COL_ID,
                        SQLiteManager.USUARIO_COL_CRMV,
                        SQLiteManager.USUARIO_COL_NOME,
                        SQLiteManager.USUARIO_COL_CPF,
                        SQLiteManager.USUARIO_COL_EMAIL,
                        SQLiteManager.USUARIO_COL_SENHA},
                        colunas_where,
                        valores_where, null, null, null, null);

        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            Usuario usuario = new Usuario(
                    cursor.getInt(cursor.getColumnIndex(SQLiteManager.USUARIO_COL_ID)),
                    cursor.getString(cursor.getColumnIndex(SQLiteManager.USUARIO_COL_CRMV)),
                    cursor.getString(cursor.getColumnIndex(SQLiteManager.USUARIO_COL_CPF)),
                    cursor.getString(cursor.getColumnIndex(SQLiteManager.USUARIO_COL_NOME)),
                    cursor.getString(cursor.getColumnIndex(SQLiteManager.USUARIO_COL_EMAIL)),
                    cursor.getString(cursor.getColumnIndex(SQLiteManager.USUARIO_COL_SENHA)));

            return usuario;
        }
        cursor.close();
        return null;
    }

    public ArrayList<Usuario> buscarTodosUsuarios() {

        bancoDados = gerenciador.getReadableDatabase();

        ArrayList<Usuario> usuarios = new ArrayList<>();
        String getPessoas = "SELECT * FROM " + SQLiteManager.TABELA_USUARIO;

        try {
            Cursor c = bancoDados.rawQuery(getPessoas, null);

            if (c.moveToFirst()) {
                do {
                    Usuario u = new Usuario();
                    u.setId(c.getInt(c.getColumnIndex(SQLiteManager.USUARIO_COL_ID)));
                    u.setCRMV(c.getString(c.getColumnIndex(SQLiteManager.USUARIO_COL_CRMV)));
                    u.setCpf(c.getString(c.getColumnIndex(SQLiteManager.USUARIO_COL_CPF)));
                    u.setNome(c.getString(c.getColumnIndex(SQLiteManager.USUARIO_COL_NOME)));
                    u.setEmail(c.getString(c.getColumnIndex(SQLiteManager.USUARIO_COL_EMAIL)));
                    u.setSenha(c.getString(c.getColumnIndex(SQLiteManager.USUARIO_COL_SENHA)));
                    usuarios.add(u);
                } while (c.moveToNext());
                c.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            bancoDados.close();
        }

        return usuarios;
    }



    public boolean atualizarUsuario(Usuario usuario) {
        bancoDados = gerenciador.getWritableDatabase();

        ContentValues dados = new ContentValues();

        dados.put(SQLiteManager.USUARIO_COL_NOME, usuario.getNome());
        dados.put(SQLiteManager.USUARIO_COL_EMAIL, usuario.getEmail());
        dados.put(SQLiteManager.USUARIO_COL_SENHA, usuario.getSenha());

        int retorno = bancoDados.update(SQLiteManager.TABELA_USUARIO,
                dados, SQLiteManager.USUARIO_COL_ID + " = ?",
                new String[]{String.valueOf(usuario.getId())});

        bancoDados.close();
        if(retorno > 0) {
            return true;
        } else {
            return false;
        }

    }

    public void removerUsuario(Usuario usuario) {
        bancoDados = gerenciador.getWritableDatabase();
        bancoDados.delete(SQLiteManager.TABELA_USUARIO,
                SQLiteManager.USUARIO_COL_ID + " = ? ",
                new String[]{String.valueOf(usuario.getId())});

        bancoDados.close();
    }

}