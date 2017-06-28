package com.nutricampus.app.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Felipe on 25/06/2017.
 * For project NutriCampus.
 * Contact: <felipeguimaraes540@gmail.com>
 */
public class SQLiteManager extends SQLiteOpenHelper {

    /* Nome do Banco de Dados */
    private static final String NOME_BANCO = "NutriCampusBD";
    private static final int VERSAO_BANCO = 1;

    /* Modo de acesso ao banco de dados
     *
     * Configura as permissões de acesso ao banco de dados.
     *
     * 0 - Modo privado (apenas essa aplicação pode usar o banco).
     * 1 - Modo leitura para todos (outras aplicações podem usar o banco).
     * 2 - Modo escrita para todos (outras aplicações podem usar o banco). */
    private final int DATABASE_ACESS = 0;


    /* SQL de criação de tabelas. */
    protected static Context context;
    public static final String TABELA_USUARIO = "usuario";
    public static final String USUARIO_COL_ID = "_id";
    public static final String USUARIO_COL_CRMV = "crmv";
    public static final String USUARIO_COL_CPF = "cpf";
    public static final String USUARIO_COL_NOME = "nome";
    public static final String USUARIO_COL_EMAIL = "email";
    public static final String USUARIO_COL_SENHA = "senha";

    private final String SQL_CREATE_TABELA_USUARIO = "CREATE TABLE IF NOT EXISTS " + TABELA_USUARIO + "(" +
            USUARIO_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            USUARIO_COL_CRMV + " TEXT NOT NULL UNIQUE, " +
            USUARIO_COL_CPF + " TEXT NOT NULL UNIQUE," +
            USUARIO_COL_NOME + " TEXT NOT NULL, " +
            USUARIO_COL_EMAIL + " TEXT NOT NULL, " +
            USUARIO_COL_SENHA + " TEXT NOT NULL);";

    public SQLiteManager(Context context) {
        super(context, NOME_BANCO, null, VERSAO_BANCO);
    }

    /* SQL de criação de tabelas. */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_TABELA_USUARIO);
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // A implementar
    }
}
