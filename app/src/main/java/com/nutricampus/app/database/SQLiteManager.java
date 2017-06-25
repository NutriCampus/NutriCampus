package com.nutricampus.app.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Mateus on 14/06/2017.
 * For project NutriCampus.
 * Contact: <paulomatew@gmail.com>
 */
public class SQLiteManager {
    /*
    http://randomkeygen.com/
    TODO trocar keys a cada versão do app
     */

    /* Nome do Banco de Dados */
    private final String UNIQ_KEY = "5q7TA#Y";
    private final String DATABASE_NAME = UNIQ_KEY + "NutriCampusDB";

    /* Entidades do BD */
    protected static final String tabelaUsuario = "Usuario";

    /* Modo de acesso ao banco de dados
     *
     * Configura as permissões de acesso ao banco de dados.
     *
     * 0 - Modo privado (apenas essa aplicação pode usar o banco).
     * 1 - Modo leitura para todos (outras aplicações podem usar o banco).
     * 2 - Modo escrita para todos (outras aplicações podem usar o banco). */
    private final int DATABASE_ACESS = 0;


    /* SQL de criação de tabelas. */

    /* SQL de criação da tabela Usuario. */
    protected static final String usuario_col_id = "ID", usuario_col_crmv = "CRMV",
            usuario_col_cpf = "CPF", usuario_col_nome = "Nome", usuario_col_email = "Email",
            usuario_col_senha = "Senha";

    private final String SQL_CREATE_TABELA_USUARIO = "CREATE TABLE IF NOT EXISTS " + tabelaUsuario + "( " +
            usuario_col_id + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            usuario_col_crmv + " TEXT UNIQUE, " +
            usuario_col_cpf + " TEXT, UNIQUE" +
            usuario_col_nome + " TEXT NOT NULL, " +
            usuario_col_email + " TEXT NOT NULL, " +
            usuario_col_senha + " TEXT NOT NULL );";

    /* Classe com métodos para executar os comandos SQL e manipular o banco de dados. */
    protected static SQLiteDatabase banco;
    protected static Context _context;

    public SQLiteManager(Context context) {
        this._context = context;
        removerBdVersoesAnteriores();

        this.banco = context.openOrCreateDatabase(DATABASE_NAME, DATABASE_ACESS, null);
        this.banco.execSQL(SQL_CREATE_TABELA_USUARIO);
    }

    /**
     * NUNCA remover essa chamada. Isso fara com que seja apagado o bd anterior do usuário.
     * Isso pq o usuário pode não ter a versão atual. Quando tiver, o bd anterior sera limpado.
     */
    public void removerBdVersoesAnteriores() {
        final String bd1 = "nutriCampusDB";
        final String bd2 = "y=03p*48$4Zay;.9NutriCampusDB";

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

}
