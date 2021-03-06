package com.nutricampus.app.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Felipe on 25/06/2017.
 * For project NutriCampus.
 * Contact: <felipeguimaraes540@gmail.com>
 */
@SuppressWarnings("squid:S2068") // Nome do campo senha
public class SQLiteManager extends SQLiteOpenHelper {

    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS ";
    private static final String KEY_COLUMN = " INTEGER PRIMARY KEY AUTOINCREMENT,";
    private static final String TEXT_NOT_NULL = "TEXT NOT NULL";
    private static final String REAL_NOT_NULL = "REAL NOT NULL";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS ";
    private static final String FOREIGN_KEY_INIT = "FOREIGN KEY(";
    private static final String FOREIGN_KEY_REF = ") REFERENCES ";
    public static final String INTEGER_NOT_NULL = "INTEGER NOT NULL";

    public static final String SELECT_TODOS = "SELECT * FROM ";
    public static final String ORDER_BY = " ORDER BY ";

    /* Nome do Banco de Dados */
    private static final String NOME_BANCO = "NutriCampusBD";
    private static final int VERSAO_BANCO = 8;

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

    //Animal
    public static final String TABELA_ANIMAL = "animal";
    public static final String ANIMAL_COL_ID = "_id";
    public static final String ANIMAL_COL_IDENTIFICADOR = "identificador";
    public static final String ANIMAL_COL_ID_PROPRIEDADE = "id_propriedade";
    public static final String ANIMAL_COL_DATA_NASCIMENTO = "data_nascimento";
    public static final String ANIMAL_COL_IS_ATIVO = "isAtivo";
    public static final String ANIMAL_COL_ID_USUARIO = "id_usuario";

    //Dados Complementares Animal
    public static final String TABELA_DADOS_COMPL = "dadosComplAnimal";
    public static final String DADOS_COMPL_COL_ID = "_id";
    public static final String DADOS_COMPL_COL_DATA = "data";
    public static final String DADOS_COMPL_COL_ID_ANIMAL = "id_animal";
    public static final String DADOS_COMPL_COL_PESO_VIVO = "pesoVivo";
    public static final String DADOS_COMPL_COL_EEC = "eec";
    public static final String DADOS_COMPL_COL_CAMINHADA_HORIZONTAL = "caminhadaHorizontal";
    public static final String DADOS_COMPL_COL_CAMINHADA_VERTICAL = "caminhadaVertical";
    public static final String DADOS_COMPL_COL_SEMANA_LACTACAO = "semanaLactacao";
    public static final String DADOS_COMPL_COL_ID_GRUPO = "id_grupo";

    //Producao de leite
    public static final String TABELA_PRODUCAO_DE_LEITE = "producaoDeLeite";
    public static final String PRODUCAO_DE_LEITE_ID = "_id";
    public static final String PRODUCAO_DE_LEITE_ID_ANIMAL = "id_animal";
    public static final String PRODUCAO_DE_LEITE_DATA = "data";
    public static final String PRODUCAO_DE_LEITE_QNT_PRODUZIDA = "qnt_produzida";
    public static final String PRODUCAO_DE_LEITE_PCT_LACTOSE = "pct_lactose";
    public static final String PRODUCAO_DE_LEITE_PCT_PROTEINA_VERDADEIRA = "pct_proteina_verdadeira";
    public static final String PRODUCAO_DE_LEITE_PCT_PROTEINA_BRUTA = "pct_proteina_bruta";
    public static final String PRODUCAO_DE_LEITE_GORDURA = "gordura";

    //Prole
    public static final String TABELA_PROLE = "prole";
    public static final String PROLE_ID = "_id";
    public static final String PROLE_ID_MATRIZ = "id_matriz";
    public static final String PROLE_DATA_DE_NASCIMENTO = "dataDeNascimento";
    public static final String PROLE_PESO_DE_NASCIMENTO = "pesoDeNascimento";
    public static final String PROLE_IS_NATIMORTO = "isNatimorto";

    //Compostos Alimentares
    public static final String TABELA_COMPOSTOS_ALIMENTARES = "compostosAlimentares";
    public static final String COMPOSTOS_ALIMENTARES_ID = "_id";
    public static final String COMPOSTOS_ALIMENTARES_TIPO = "tipo";
    public static final String COMPOSTOS_ALIMENTARES_IDENTIFICADOR = "identificador";
    public static final String COMPOSTOS_ALIMENTARES_MS = "MS";
    public static final String COMPOSTOS_ALIMENTARES_FDN = "FDN";
    public static final String COMPOSTOS_ALIMENTARES_EE = "EE";
    public static final String COMPOSTOS_ALIMENTARES_MM = "MM";
    public static final String COMPOSTOS_ALIMENTARES_CNF = "CNF";
    public static final String COMPOSTOS_ALIMENTARES_PB = "PB";
    public static final String COMPOSTOS_ALIMENTARES_NDT = "NDT";
    public static final String COMPOSTOS_ALIMENTARES_FDA = "FDA";
    public static final String COMPOSTOS_ALIMENTARES_DESCRICAO = "descricao";

    //Grupo
    public static final String TABELA_GRUPO = "grupos";
    public static final String GRUPO_COL_ID = "_id";
    public static final String GRUPO_COL_IDENTIFICADOR = "identificador";
    public static final String GRUPO_COL_OBSERVACAO = "observacao";
    public static final String GRUPO_COL_ID_USUARIO = "id_usuario";

    //Dieta
    public static final String TABELA_DIETA = "dieta";
    public static final String DIETA_ID = "_id";
    public static final String DIETA_IDENTIFICADOR = "identificador";
    public static final String DIETA_PB = "pb";
    public static final String DIETA_PROPRIEDADE_ID = "propriedade";
    //Dieta ANIMAIS
    public static final String TABELA_DIETA_ANIMAL = "dietaAnimais";
    public static final String DIETA_ANIMAL_ID_FK = "idDieta";
    public static final String DIETA_ANIMAL_ID = "idAnimal";
    //Dieta COMPOSTOS
    public static final String TABELA_DIETA_COMPOSTO = "dietaCompostos";
    public static final String DIETA_COMPOSTO_ID_FK = "idDieta";
    public static final String DIETA_COMPOSTO_ID = "idComposto";
    public static final String DIETA_COMPOSTO_PORCENTAGEM = "porcentagem";

    /* SQL de criação de tabelas. */
    private static final String SQL_CREATE_TABELA_ANIMAL = CREATE_TABLE + TABELA_ANIMAL + "(" +
            ANIMAL_COL_ID + KEY_COLUMN +
            ANIMAL_COL_IDENTIFICADOR + " " + TEXT_NOT_NULL + ", " +
            ANIMAL_COL_ID_PROPRIEDADE + " " + INTEGER_NOT_NULL + ", " +
            ANIMAL_COL_DATA_NASCIMENTO + " " + TEXT_NOT_NULL + ", " +
            ANIMAL_COL_IS_ATIVO + " " + TEXT_NOT_NULL + ", " +
            ANIMAL_COL_ID_USUARIO + " " + INTEGER_NOT_NULL + ", " +
            "FOREIGN KEY(" + ANIMAL_COL_ID_PROPRIEDADE + ") REFERENCES " + TABELA_PROPRIEDADE + "(" + PROPRIEDADE_COL_ID + ")" +
            "FOREIGN KEY(" + ANIMAL_COL_ID_USUARIO + ") REFERENCES " + TABELA_USUARIO + "(" + USUARIO_COL_ID + "));";

    private static final String SQL_CREATE_TABELA_PROLE = CREATE_TABLE + TABELA_PROLE + "(" +
            PROLE_ID + KEY_COLUMN +
            PROLE_ID_MATRIZ + " " + TEXT_NOT_NULL + " ," +
            PROLE_DATA_DE_NASCIMENTO + " " + TEXT_NOT_NULL + " ," +
            PROLE_PESO_DE_NASCIMENTO + " " + TEXT_NOT_NULL + " ," +
            PROLE_IS_NATIMORTO + " INT NOT NULL," +
            FOREIGN_KEY_INIT + PROLE_ID_MATRIZ + FOREIGN_KEY_REF + TABELA_ANIMAL + "(" + ANIMAL_COL_ID + "));";


    private static final String SQL_CREATE_TABELA_DADOS_COMPL = CREATE_TABLE + TABELA_DADOS_COMPL + "(" +
            DADOS_COMPL_COL_ID + KEY_COLUMN +
            DADOS_COMPL_COL_DATA + " " + TEXT_NOT_NULL + " ," +
            DADOS_COMPL_COL_ID_ANIMAL + " " + TEXT_NOT_NULL + " ," +
            DADOS_COMPL_COL_PESO_VIVO + " " + REAL_NOT_NULL + ", " +
            DADOS_COMPL_COL_EEC + " TEXT," +
            DADOS_COMPL_COL_CAMINHADA_HORIZONTAL + " TEXT, " +
            DADOS_COMPL_COL_CAMINHADA_VERTICAL + " TEXT, " +
            DADOS_COMPL_COL_SEMANA_LACTACAO + " INTEGER, " +
            DADOS_COMPL_COL_ID_GRUPO + " " + INTEGER_NOT_NULL + ", " +
            "FOREIGN KEY(" + DADOS_COMPL_COL_ID_ANIMAL + ") REFERENCES " + TABELA_ANIMAL + "(" + ANIMAL_COL_ID + ")" +
            "FOREIGN KEY(" + DADOS_COMPL_COL_ID_GRUPO + ") REFERENCES " + TABELA_GRUPO + "(" + GRUPO_COL_ID + "));";

    private static final String SQL_CREATE_TABELA_PRODUCAO_DE_LEITE = CREATE_TABLE + TABELA_PRODUCAO_DE_LEITE + "(" +
            PRODUCAO_DE_LEITE_ID + KEY_COLUMN +
            PRODUCAO_DE_LEITE_ID_ANIMAL + " " + TEXT_NOT_NULL + " ," +
            PRODUCAO_DE_LEITE_DATA + " " + TEXT_NOT_NULL + " ," +
            PRODUCAO_DE_LEITE_QNT_PRODUZIDA + " " + TEXT_NOT_NULL + " ," +
            PRODUCAO_DE_LEITE_PCT_LACTOSE + " " + TEXT_NOT_NULL + " ," +
            PRODUCAO_DE_LEITE_PCT_PROTEINA_VERDADEIRA + " " + TEXT_NOT_NULL + " ," +
            PRODUCAO_DE_LEITE_PCT_PROTEINA_BRUTA + " " + TEXT_NOT_NULL + " ," +
            PRODUCAO_DE_LEITE_GORDURA + " " + TEXT_NOT_NULL + " ," +
            FOREIGN_KEY_INIT + PRODUCAO_DE_LEITE_ID_ANIMAL + FOREIGN_KEY_REF + TABELA_ANIMAL + "(" + ANIMAL_COL_ID + "));";

    private static final String SQL_CREATE_TABELA_USUARIO = CREATE_TABLE + TABELA_USUARIO + "(" +
            USUARIO_COL_ID + KEY_COLUMN +
            USUARIO_COL_CRMV + " " + TEXT_NOT_NULL + " UNIQUE, " +
            USUARIO_COL_CPF + " " + TEXT_NOT_NULL + " UNIQUE," +
            USUARIO_COL_NOME + " " + TEXT_NOT_NULL + " , " +
            USUARIO_COL_EMAIL + " " + TEXT_NOT_NULL + " , " +
            USUARIO_COL_SENHA + " " + TEXT_NOT_NULL + " );";

    private static final String SQL_CREATE_TABELA_PROPRIETARIO = CREATE_TABLE + TABELA_PROPRIETARIO + "(" +
            PROPRIETARIO_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            PROPRIETARIO_COL_CPF + " " + TEXT_NOT_NULL + " UNIQUE," +
            PROPRIETARIO_COL_NOME + " " + TEXT_NOT_NULL + " , " +
            PROPRIETARIO_COL_EMAIL + " " + TEXT_NOT_NULL + " , " +
            PROPRIETARIO_COL_TELEFONE + " " + TEXT_NOT_NULL + " );";

    private static final String SQL_CREATE_TABELA_PROPRIEDADE = CREATE_TABLE + TABELA_PROPRIEDADE + "(" +
            PROPRIEDADE_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            PROPRIEDADE_COL_NOME + " " + TEXT_NOT_NULL + " , " +
            PROPRIEDADE_COL_TELEFONE + " " + TEXT_NOT_NULL + " , " +
            PROPRIEDADE_COL_LOGRADOURO + " " + TEXT_NOT_NULL + " , " +
            PROPRIEDADE_COL_NUMERO + " " + TEXT_NOT_NULL + " , " +
            PROPRIEDADE_COL_BAIRRO + " " + TEXT_NOT_NULL + " , " +
            PROPRIEDADE_COL_CIDADE + " " + TEXT_NOT_NULL + " , " +
            PROPRIEDADE_COL_ESTADO + " " + TEXT_NOT_NULL + " , " +
            PROPRIEDADE_COL_CEP + " " + TEXT_NOT_NULL + " , " +
            PROPRIEDADE_COL_ID_PROPRIETARIO + " INTEGER NOT NULL, " +
            PROPRIEDADE_COL_ID_USUARIO + " INTEGER NOT NULL, " +
            FOREIGN_KEY_INIT + PROPRIEDADE_COL_ID_PROPRIETARIO + FOREIGN_KEY_REF + TABELA_PROPRIETARIO + "(" + PROPRIETARIO_COL_ID + ")" +
            FOREIGN_KEY_INIT + PROPRIEDADE_COL_ID_USUARIO + FOREIGN_KEY_REF + TABELA_USUARIO + "(" + USUARIO_COL_ID + "));";

    private static final String SQL_CREATE_TABELA_COMPOSTOS_ALIMENTARES = "CREATE TABLE IF NOT EXISTS " + TABELA_COMPOSTOS_ALIMENTARES + "(" +
            COMPOSTOS_ALIMENTARES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COMPOSTOS_ALIMENTARES_TIPO + " " + TEXT_NOT_NULL + "," +
            COMPOSTOS_ALIMENTARES_IDENTIFICADOR + " " + TEXT_NOT_NULL + " UNIQUE, " +
            COMPOSTOS_ALIMENTARES_MS + " " + REAL_NOT_NULL + " , " +
            COMPOSTOS_ALIMENTARES_FDN + " " + REAL_NOT_NULL + " , " +
            COMPOSTOS_ALIMENTARES_EE + " " + REAL_NOT_NULL + " , " +
            COMPOSTOS_ALIMENTARES_MM + " " + REAL_NOT_NULL + " , " +
            COMPOSTOS_ALIMENTARES_CNF + " " + REAL_NOT_NULL + " , " +
            COMPOSTOS_ALIMENTARES_PB + " " + REAL_NOT_NULL + " , " +
            COMPOSTOS_ALIMENTARES_NDT + " " + REAL_NOT_NULL + " , " +
            COMPOSTOS_ALIMENTARES_FDA + " " + REAL_NOT_NULL + " , " +
            COMPOSTOS_ALIMENTARES_DESCRICAO + " " + TEXT_NOT_NULL + " );";

    private static final String SQL_CREATE_TABELA_GRUPO = "CREATE TABLE IF NOT EXISTS " + TABELA_GRUPO + "(" +
            GRUPO_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            GRUPO_COL_IDENTIFICADOR + " " + TEXT_NOT_NULL + "," +
            GRUPO_COL_OBSERVACAO + " TEXT, " +
            GRUPO_COL_ID_USUARIO + " " + INTEGER_NOT_NULL + ", " +
            "FOREIGN KEY(" + GRUPO_COL_ID_USUARIO + ") REFERENCES " + TABELA_USUARIO + "(" + USUARIO_COL_ID + "));";

    private static final String SQL_CREATE_TABELA_DIETA = "CREATE TABLE IF NOT EXISTS " + TABELA_DIETA + "(" +
            DIETA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            DIETA_IDENTIFICADOR + " INTEGER NOT NULL," +
            DIETA_PROPRIEDADE_ID + " INTEGER NOT NULL," +
            DIETA_PB + " " + REAL_NOT_NULL + " );";

    private static final String SQL_CREATE_TABELA_DIETA_ANIMAL = "CREATE TABLE IF NOT EXISTS " + TABELA_DIETA_ANIMAL + "(" +
            DIETA_ANIMAL_ID_FK + " INTEGER NOT NULL, " +
            DIETA_ANIMAL_ID + " INTEGER NOT NULL" + " );";

    private static final String SQL_CREATE_TABELA_DIETA_COMPOSTO = "CREATE TABLE IF NOT EXISTS " + TABELA_DIETA_COMPOSTO + "(" +
            DIETA_COMPOSTO_ID_FK + " INTEGER NOT NULL, " +
            DIETA_COMPOSTO_ID + " INTEGER NOT NULL" + "," +
            DIETA_COMPOSTO_PORCENTAGEM + " " + REAL_NOT_NULL + " );";


    public SQLiteManager(Context context) {
        super(context, NOME_BANCO, null, VERSAO_BANCO);
    }

    /* SQL de criação de tabelas. */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_TABELA_USUARIO);
        sqLiteDatabase.execSQL(SQL_CREATE_TABELA_PROPRIETARIO);
        sqLiteDatabase.execSQL(SQL_CREATE_TABELA_PROPRIEDADE);
        sqLiteDatabase.execSQL(SQL_CREATE_TABELA_ANIMAL);
        sqLiteDatabase.execSQL(SQL_CREATE_TABELA_DADOS_COMPL);
        sqLiteDatabase.execSQL(SQL_CREATE_TABELA_PROLE);
        sqLiteDatabase.execSQL(SQL_CREATE_TABELA_PRODUCAO_DE_LEITE);
        sqLiteDatabase.execSQL(SQL_CREATE_TABELA_COMPOSTOS_ALIMENTARES);
        sqLiteDatabase.execSQL(SQL_CREATE_TABELA_GRUPO);
        sqLiteDatabase.execSQL(SQL_CREATE_TABELA_DIETA);
        sqLiteDatabase.execSQL(SQL_CREATE_TABELA_DIETA_ANIMAL);
        sqLiteDatabase.execSQL(SQL_CREATE_TABELA_DIETA_COMPOSTO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DROP_TABLE + TABELA_USUARIO);
        sqLiteDatabase.execSQL(DROP_TABLE + TABELA_PROPRIETARIO);
        sqLiteDatabase.execSQL(DROP_TABLE + TABELA_PROPRIEDADE);
        sqLiteDatabase.execSQL(DROP_TABLE + TABELA_DADOS_COMPL);
        sqLiteDatabase.execSQL(DROP_TABLE + TABELA_PROLE);
        sqLiteDatabase.execSQL(DROP_TABLE + TABELA_PRODUCAO_DE_LEITE);
        sqLiteDatabase.execSQL(DROP_TABLE + TABELA_ANIMAL);
        sqLiteDatabase.execSQL(DROP_TABLE + TABELA_COMPOSTOS_ALIMENTARES);

        sqLiteDatabase.execSQL(DROP_TABLE + TABELA_GRUPO);

        sqLiteDatabase.execSQL(DROP_TABLE + TABELA_DIETA);
        sqLiteDatabase.execSQL(DROP_TABLE + TABELA_DIETA_ANIMAL);
        sqLiteDatabase.execSQL(DROP_TABLE + TABELA_DIETA_COMPOSTO);
        this.onCreate(sqLiteDatabase);
    }

}