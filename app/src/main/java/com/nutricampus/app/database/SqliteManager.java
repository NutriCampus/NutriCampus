package com.nutricampus.app.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.nutricampus.app.entities.Session;
import com.nutricampus.app.entities.Usuario;

import java.util.ArrayList;

/**
 * Created by Mateus on 14/06/2017.
 * For project NutriCampus.
 * Contact: <paulomatew@gmail.com>
 */
public class SqliteManager {
    /*
    http://randomkeygen.com/
    TODO trocar keys a cada versão do app
     */
    /* Nome do Banco de Dados */
    private final String UNIQ_KEY = "5q7TA#Y";
    private final String DATABASE_NAME = UNIQ_KEY + "Sig@Viewer";
    /*Entidades do BD*/
    private final String table_name_user = "usuario";

    /* Modo de acesso ao banco de dados
     *
     * Configura as permissões de acesso ao banco de dados.
     *
     * 0 - Modo privado (apenas essa aplicação pode usar o banco).
     * 1 - Modo leitura para todos (outras aplicações podem usar o banco).
     * 2 - Modo escrita para todos (outras aplicações podem usar o banco). */
    private final int DATABASE_ACESS = 0;


    /* SQL de criação de tabelas. */
    private final String user_col_id = "user_id", user_col_cpf = "user_cpf",
            user_col_email = "user_email", user_col_senha = "user_email", user_col_nome = "user_cpf",
            user_col_registro = "user_registro";
    private final String SQL_CREATE_TABLE_USER = "CREATE TABLE IF NOT EXISTS " + table_name_user + "( " +
            user_col_id + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            user_col_cpf + " TEXT UNIQUE, " +
            user_col_senha + " TEXT, " +
            user_col_nome + " TEXT, " +
            user_col_email + " TEXT, " +
            user_col_registro + " TEXT );";

    /* Classe com métodos para executar os comandos SQL e manipular o banco de dados. */
    private SQLiteDatabase banco;
    private Context _context;

    public SqliteManager(Context context) {
        this._context = context;
        removerBdVersoesAnteriores();

        this.banco = context.openOrCreateDatabase(DATABASE_NAME, DATABASE_ACESS, null);
        this.banco.execSQL(SQL_CREATE_TABLE_USER);
    }

    /**
     * NUNCA remover essa chamada. Isso fara com que seja apagado o bd anterior do usuário.
     * Isso pq o usuário pode não ter a versão atual. Quando tiver, o bd anterior sera limpado.
     */
    public void removerBdVersoesAnteriores() {
        final String bd1 = "sigaViewer";
        final String bd2 = "y=03p*48$4Zay;.9Sig@Viewer";

        if (DATABASE_NAME.equals(bd1)) {
            this._context.deleteDatabase(bd1);
        } else if (DATABASE_NAME.equals(bd2)) {
            this._context.deleteDatabase(bd2);
        }
    }

    public void close() {
        banco.close();
        banco = null;
    }

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

    /**
     * @param usuario
     * @return true se inseriu com sucesso, false se não inseriu
     */
    /*//Log
    public boolean insertUsuario(Usuario usuario) {
        //Mapa com a coluna e os valores de cada coluna.
        ContentValues values = new ContentValues();
        values.put(this.user_col_cpf, usuario.cpf);
        values.put(this.user_col_email, usuario.email);
        values.put(this.user_col_senha, usuario.senha);
        values.put(this.user_col_nome, usuario.nome);
        values.put(this.user_col_registro, usuario.registro);

        long retorno;
        try {
            retorno = this.banco.insertOrThrow(this.table_name_user, null, values);
        } catch (Exception e) {
            retorno = 0;
        }

        if (retorno != 0 && retorno != -1) {
            if (Session.DEBBUG)
                Log.v(this.getClass().getName(), "insertUsuario(): " +
                        "Login=" + usuario.cpf +
                        " | Email= " + usuario.email +
                        " | Nome= " + usuario.nome +
                        " | Registro= " + usuario.registro);
            return true;
        } else
            return false;
    }

    //Log
    public Usuario selectUsuario(String cpf) {
        Cursor cursor = this.banco.query(
                this.table_name_user, null, user_col_cpf + " = ?", new String[]{cpf}, null, null, null
        );

        if (cursor.moveToFirst()) {
            Usuario usuario = new Usuario(cursor.getInt(cursor.getColumnIndex(this.user_col_id)),
                    cursor.getString(cursor.getColumnIndex(this.user_col_cpf)),
                    cursor.getString(cursor.getColumnIndex(this.user_col_email)),
                    cursor.getString(cursor.getColumnIndex(this.user_col_senha)),
                    cursor.getString(cursor.getColumnIndex(this.user_col_nome)),
                    cursor.getString(cursor.getColumnIndex(this.user_col_registro)));

            if (Session.DEBBUG)
                Log.v(this.getClass().getName(), "selectUsuario(): " +
                        "ID=" + usuario.id +
                        " | Login=" + usuario.cpf +
                        " | Email= " + usuario.email +
                        " | Nome= " + usuario.nome +
                        " | Registro= " + usuario.registro);
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

    //Log
    public boolean updateUsuarioInfo(Estudante est) {
        ContentValues values = new ContentValues();
        values.put(this.user_col_nome, est.nome);
        values.put(this.user_col_cpf, est.cpf);
        values.put(this.user_col_curso, est.curso);

        String where = this.user_col_login + " = ?";

        int retorno = this.banco.update(table_name_user, values, where, new String[]{est.login});

        if (retorno != 0 && retorno != -1) {

            if (Session.DEBUG)
                Log.v(this.getClass().getName(),
                        "updateUsuarioInfo(): " +
                                "Login=" + est.login +
                                " | Senha= " + est.senha +
                                " | Nome= " + est.nome +
                                " | cpf= " + est.cpf +
                                " | curso= " + est.curso +
                                " | Id= " + retorno);
            return true;
        } else
            return false;
    }

    //Log
    public boolean updateUsuario(String currentLogin, Estudante est) {
        ContentValues values = new ContentValues();
        values.put(this.user_col_login, est.login);
        values.put(this.user_col_senha, est.senha);

        int retorno = this.banco.update(table_name_user, values, this.user_col_login + " = ?", new String[]{currentLogin});

        if (retorno != 0 && retorno != -1) {
            if (Session.DEBUG)
                Log.v(this.getClass().getName(), "updateUsuario(): " + "Login=" + est.login + " | Senha= " + est.senha + " | Id= " + retorno);
            return true;
        } else
            return false;
    }

    //Log
    public ArrayList<Usuario> selectProprietarios() {
        Cursor cursor = this.banco.query(
                this.table_name_user, null, null, null, null, null, null
        );
        if (cursor.moveToFirst()) {
            ArrayList<Proprietario> estudantesArray = new ArrayList<>();
            do {
                Estudante est = new Estudante();
                est.id = String.valueOf(cursor.getInt(cursor.getColumnIndex(this.user_col_id)));
                est.login = cursor.getString(cursor.getColumnIndex(this.user_col_login));
                est.senha = cursor.getString(cursor.getColumnIndex(this.user_col_senha));
                est.nome = cursor.getString(cursor.getColumnIndex(this.user_col_nome));
                est.cpf = cursor.getString(cursor.getColumnIndex(this.user_col_cpf));
                est.curso = cursor.getString(cursor.getColumnIndex(this.user_col_curso));
                est.hasdata = cursor.getString(cursor.getColumnIndex(this.user_col_hasdata));

                if (Session.DEBBUG)
                    Log.v(this.getClass().getName(),
                            "selectAllUsuarios(): " +
                                    "Nome=" + est.nome +
                                    " | Curso=" + est.curso +
                                    " | Login=" + est.login +
                                    " | Senha= " + est.senha +
                                    " | cpf= " + est.cpf +
                                    " | Id= " + est.id);

                estudantesArray.add(est);

            } while (cursor.moveToNext());

            try {
                cursor.close();
            } catch (Exception e) {
            }
            return estudantesArray;
        } else {
            try {
                cursor.close();
            } catch (Exception e) {
            }
            return null;
        }
    }

    public void removeUsuario(Usuario usuario) {
        this.banco.delete(table_name_user, user_col_id + " = ?", new String[]{String.valueOf(usuario.id)});
    }

    public boolean deleteDataBase() {
        boolean tentativa;
        try {
            tentativa = this._context.deleteDatabase(DATABASE_NAME);
        } catch (Exception e) {
            tentativa = false;
        }
        return tentativa;
    }

    public boolean deleteTablesLessUsers() {
        boolean tentativa;
        try {
            this.banco.execSQL("DROP TABLE IF EXISTS " + table_name_user);
            tentativa = true;
        } catch (Exception e) {
            tentativa = false;
        }
        return tentativa;
    }*/
}
