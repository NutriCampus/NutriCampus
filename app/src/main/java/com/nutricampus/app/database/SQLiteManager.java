package com.nutricampus.app.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Felipe on 25/06/2017.
 * For project NutriCampus.
 * Contact: <felipeguimaraes540@gmail.com>
 */
public class SQLiteManager extends SQLiteOpenHelper {

    /* Nome do Banco de Dados */
    private static final String NOME_BANCO = "NutriCampusBD";
    private static final int VERSAO_BANCO = 4;

    /* Modo de acesso ao banco de dados
     *
     * Configura as permissões de acesso ao banco de dados.
     *
     * 0 - Modo privado (apenas essa aplicação pode usar o banco).
     * 1 - Modo leitura para todos (outras aplicações podem usar o banco).
     * 2 - Modo escrita para todos (outras aplicações podem usar o banco). */
    private final int DATABASE_ACESS = 0;


    /* Constantes para criação de tabelas */
    protected static Context context;

    //Usuário
    public static final String TABELA_USUARIO = "usuario";
    public static final String USUARIO_COL_ID = "_id";
    public static final String USUARIO_COL_CRMV = "crmv";
    public static final String USUARIO_COL_CPF = "cpf";
    public static final String USUARIO_COL_NOME = "nome";
    public static final String USUARIO_COL_EMAIL = "email";
    public static final String USUARIO_COL_SENHA = "senha";

    //Proprietário
    public static final String TABELA_PROPRIETARIO = "proprietario";
    public static final String PROPRIETARIO_COL_ID = "_id";
    public static final String PROPRIETARIO_COL_NOME = "nome";
    public static final String PROPRIETARIO_COL_CPF = "cpf";
    public static final String PROPRIETARIO_COL_EMAIL = "email";
    public static final String PROPRIETARIO_COL_TELEFONE = "telefone";

    //Propriedade
    public static final String TABELA_PROPRIEDADE = "propriedade";
    public static final String PROPRIEDADE_COL_ID = "_id";
    public static final String PROPRIEDADE_COL_NOME = "nome";
    public static final String PROPRIEDADE_COL_TELEFONE = "telefone";
    public static final String PROPRIEDADE_COL_LOGRADOURO = "logradouro";
    public static final String PROPRIEDADE_COL_NUMERO = "numero";
    public static final String PROPRIEDADE_COL_BAIRRO = "bairro";
    public static final String PROPRIEDADE_COL_CIDADE = "cidade";
    public static final String PROPRIEDADE_COL_ESTADO = "estado";
    public static final String PROPRIEDADE_COL_CEP = "cep";
    public static final String PROPRIEDADE_COL_ID_PROPRIETARIO = "id_proprietario";
    public static final String PROPRIEDADE_COL_ID_USUARIO = "id_usuario";

    /* SQL de criação de tabelas. */
    private static final String SQL_CREATE_TABELA_USUARIO = "CREATE TABLE IF NOT EXISTS " + TABELA_USUARIO + "(" +
            USUARIO_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            USUARIO_COL_CRMV + " TEXT NOT NULL UNIQUE, " +
            USUARIO_COL_CPF + " TEXT NOT NULL UNIQUE," +
            USUARIO_COL_NOME + " TEXT NOT NULL, " +
            USUARIO_COL_EMAIL + " TEXT NOT NULL, " +
            USUARIO_COL_SENHA + " TEXT NOT NULL);";

    private static final String SQL_CREATE_TABELA_PROPRIETARIO = "CREATE TABLE " + TABELA_PROPRIETARIO + "(" +
            PROPRIETARIO_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            PROPRIETARIO_COL_CPF + " TEXT NOT NULL UNIQUE," +
            PROPRIETARIO_COL_NOME + " TEXT NOT NULL, " +
            PROPRIETARIO_COL_EMAIL + " TEXT NOT NULL, " +
            PROPRIETARIO_COL_TELEFONE + " TEXT NOT NULL);";

    private static final String SQL_CREATE_TABELA_PROPRIEDADE = "CREATE TABLE IF NOT EXISTS " + TABELA_PROPRIEDADE + "(" +
            PROPRIEDADE_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            PROPRIEDADE_COL_NOME + " TEXT NOT NULL, " +
            PROPRIEDADE_COL_TELEFONE + " TEXT NOT NULL, " +
            PROPRIEDADE_COL_LOGRADOURO + " TEXT NOT NULL, " +
            PROPRIEDADE_COL_NUMERO + " TEXT NOT NULL, " +
            PROPRIEDADE_COL_BAIRRO + " TEXT NOT NULL, " +
            PROPRIEDADE_COL_CIDADE + " TEXT NOT NULL, " +
            PROPRIEDADE_COL_ESTADO + " TEXT NOT NULL, " +
            PROPRIEDADE_COL_CEP + " TEXT NOT NULL, " +
            PROPRIEDADE_COL_ID_PROPRIETARIO + " INTEGER NOT NULL, " +
            PROPRIEDADE_COL_ID_USUARIO + " INTEGER NOT NULL, " +
            "FOREIGN KEY(" + PROPRIEDADE_COL_ID_PROPRIETARIO + ") REFERENCES " + TABELA_PROPRIETARIO + "(" + PROPRIETARIO_COL_ID + ")" +
            "FOREIGN KEY(" + PROPRIEDADE_COL_ID_USUARIO + ") REFERENCES " + TABELA_USUARIO + "(" + USUARIO_COL_ID + "));";



    public SQLiteManager(Context context) {
        super(context, NOME_BANCO, null, VERSAO_BANCO);
    }

    /* SQL de criação de tabelas. */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_TABELA_USUARIO);
        sqLiteDatabase.execSQL(SQL_CREATE_TABELA_PROPRIETARIO);
        sqLiteDatabase.execSQL(SQL_CREATE_TABELA_PROPRIEDADE);
        Log.d("FGP >>>>>>>>>>> ", "Entrou: vrs " + VERSAO_BANCO);
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table usuario");
        this.onCreate(sqLiteDatabase);
        // A implementar
    }
}