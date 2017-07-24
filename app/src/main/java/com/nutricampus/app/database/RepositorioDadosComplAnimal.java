package com.nutricampus.app.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.nutricampus.app.entities.DadosComplAnimal;
import com.nutricampus.app.utils.Conversor;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;


/**
 * Created by Diego Bezerra on 15/06/17.
 * For project NutriCampus.
 * Contact: <diego.defb@gmail.com>
 */

public class RepositorioDadosComplAnimal {

    private SQLiteManager gerenciador;
    private SQLiteDatabase bancoDados;

    public RepositorioDadosComplAnimal(Context context) {
        gerenciador = new SQLiteManager(context);
    }

    public int inserirDadosComplAnimal(DadosComplAnimal dadosComplAnimal) {
        bancoDados = gerenciador.getWritableDatabase();

        ContentValues dados = this.getContentValues(dadosComplAnimal);

        long retorno = bancoDados.insert(SQLiteManager.TABELA_DADOS_COMPL, null, dados);
        bancoDados.close();

        // retorna o id do elemento inserido
        return (int) retorno;
    }

    private List<DadosComplAnimal> getListaDadosComplAnimal(String query) {
        bancoDados = gerenciador.getReadableDatabase();

        ArrayList<DadosComplAnimal> dadosComplAnimais = new ArrayList<>();
        try {
            Cursor c = bancoDados.rawQuery(query, null);

            if (c.moveToFirst()) {
                do {
                    Calendar data = Calendar.getInstance();
                    data.setTimeInMillis(Long.valueOf(c.getString(c.getColumnIndex(SQLiteManager.DADOS_COMPL_COL_DATA))));

                    DadosComplAnimal d = new DadosComplAnimal();
                    d.setId(c.getInt(c.getColumnIndex(SQLiteManager.DADOS_COMPL_COL_ID)));
                    d.setData(data);
                    d.setAnimal(c.getInt(c.getColumnIndex(SQLiteManager.DADOS_COMPL_COL_ID_ANIMAL)));
                    d.setPesoVivo(c.getInt(c.getColumnIndex(SQLiteManager.DADOS_COMPL_COL_PESO_VIVO)));
                    d.setEEC(c.getInt(c.getColumnIndex(SQLiteManager.DADOS_COMPL_COL_EEC)));
                    d.setCaminadaHorizontal(c.getFloat(c.getColumnIndex(SQLiteManager.DADOS_COMPL_COL_CAMINHADA_HORIZONTAL)));
                    d.setCaminhadaVertical(c.getFloat(c.getColumnIndex(SQLiteManager.DADOS_COMPL_COL_CAMINHADA_VERTICAL)));
                    d.setSemanaLactacao(c.getInt(c.getColumnIndex(SQLiteManager.DADOS_COMPL_COL_SEMANA_LACTACAO)));
                    d.setPastando(Conversor.intToBoolean(Integer.parseInt(c.getString(c.getColumnIndex(SQLiteManager.DADOS_COMPL_COL_IS_PASTANDO)))));
                    d.setLactacao(Conversor.intToBoolean(Integer.parseInt(c.getString(c.getColumnIndex(SQLiteManager.DADOS_COMPL_COL_IS_LACTACAO)))));
                    d.setGestante(Conversor.intToBoolean(Integer.parseInt(c.getString(c.getColumnIndex(SQLiteManager.DADOS_COMPL_COL_IS_GESTANTE)))));
                    d.setCio(Conversor.intToBoolean(Integer.parseInt(c.getString(c.getColumnIndex(SQLiteManager.DADOS_COMPL_COL_IS_CIO)))));
                    dadosComplAnimais.add(d);
                    Log.e("EF ", " IDs >>> " + d.getId());
                } while (c.moveToNext());
                Log.e("EF", " isEmpty " + dadosComplAnimais.isEmpty());
                c.close();
            }

        } catch (Exception e) {
            Log.i("RepositDadosComplAnimal", e.toString());
            return Collections.emptyList();
        } finally {
            bancoDados.close();
        }

        for(DadosComplAnimal d : dadosComplAnimais)
            Log.e("EF ", " <<< IDs " + d.getId());


        return dadosComplAnimais;
    }

    public List<DadosComplAnimal> buscarTodosDadosCompl(int idAnimal) {
        return this.getListaDadosComplAnimal(SQLiteManager.SELECT_TODOS + SQLiteManager.TABELA_DADOS_COMPL +
                " WHERE (" + SQLiteManager.DADOS_COMPL_COL_ID_ANIMAL + " = " + idAnimal + ")");
    }


    public DadosComplAnimal buscarDadosComplAnimal(int id_animal) {
        bancoDados = gerenciador.getReadableDatabase();

        String colunasWhere = SQLiteManager.DADOS_COMPL_COL_ID_ANIMAL + "= ?";
        String[] valoresWhere = new String[]{String.valueOf(id_animal)};

        Cursor cursor = bancoDados.query(SQLiteManager.TABELA_DADOS_COMPL, new String[]{
                        SQLiteManager.DADOS_COMPL_COL_ID,
                        SQLiteManager.DADOS_COMPL_COL_DATA,
                        SQLiteManager.DADOS_COMPL_COL_PESO_VIVO,
                        SQLiteManager.DADOS_COMPL_COL_ID_ANIMAL,
                        SQLiteManager.DADOS_COMPL_COL_EEC,
                        SQLiteManager.DADOS_COMPL_COL_CAMINHADA_HORIZONTAL,
                        SQLiteManager.DADOS_COMPL_COL_CAMINHADA_VERTICAL,
                        SQLiteManager.DADOS_COMPL_COL_SEMANA_LACTACAO,
                        SQLiteManager.DADOS_COMPL_COL_IS_PASTANDO,
                        SQLiteManager.DADOS_COMPL_COL_IS_LACTACAO,
                        SQLiteManager.DADOS_COMPL_COL_IS_GESTANTE,
                        SQLiteManager.DADOS_COMPL_COL_IS_CIO},
                colunasWhere,
                valoresWhere, null, null, null, null);

        if (cursor.getCount() > 0) {
            cursor.moveToLast();

            Calendar data = Calendar.getInstance();
            data.setTimeInMillis(Long.valueOf(cursor.getString(cursor.getColumnIndex(SQLiteManager.DADOS_COMPL_COL_DATA))));

            return new DadosComplAnimal(
                    cursor.getInt(cursor.getColumnIndex(SQLiteManager.DADOS_COMPL_COL_ID_ANIMAL)),
                    data,
                    cursor.getInt(cursor.getColumnIndex(SQLiteManager.DADOS_COMPL_COL_ID_ANIMAL)),
                    cursor.getFloat(cursor.getColumnIndex(SQLiteManager.DADOS_COMPL_COL_PESO_VIVO)),
                    cursor.getInt(cursor.getColumnIndex(SQLiteManager.DADOS_COMPL_COL_EEC)),
                    cursor.getFloat(cursor.getColumnIndex(SQLiteManager.DADOS_COMPL_COL_CAMINHADA_HORIZONTAL)),
                    cursor.getFloat(cursor.getColumnIndex(SQLiteManager.DADOS_COMPL_COL_CAMINHADA_VERTICAL)),
                    cursor.getInt(cursor.getColumnIndex(SQLiteManager.DADOS_COMPL_COL_SEMANA_LACTACAO)),
                    Conversor.intToBoolean(Integer.parseInt(cursor.getString(cursor.getColumnIndex(SQLiteManager.DADOS_COMPL_COL_IS_PASTANDO)))),
                    Conversor.intToBoolean(Integer.parseInt(cursor.getString(cursor.getColumnIndex(SQLiteManager.DADOS_COMPL_COL_IS_LACTACAO)))),
                    Conversor.intToBoolean(Integer.parseInt(cursor.getString(cursor.getColumnIndex(SQLiteManager.DADOS_COMPL_COL_IS_GESTANTE)))),
                    Conversor.intToBoolean(Integer.parseInt(cursor.getString(cursor.getColumnIndex(SQLiteManager.DADOS_COMPL_COL_IS_CIO)))));
        }
        cursor.close();
        return null;
    }

    public boolean atualizarDadosCompl(DadosComplAnimal dadosComplAnimal) {
        bancoDados = gerenciador.getWritableDatabase();

        ContentValues dados = new ContentValues();

        dados.put(SQLiteManager.DADOS_COMPL_COL_DATA, dadosComplAnimal.getData().getTimeInMillis());
        dados.put(SQLiteManager.DADOS_COMPL_COL_ID_ANIMAL, dadosComplAnimal.getAnimal());
        dados.put(SQLiteManager.DADOS_COMPL_COL_PESO_VIVO, dadosComplAnimal.getPesoVivo());
        dados.put(SQLiteManager.DADOS_COMPL_COL_EEC, dadosComplAnimal.getEEC());
        dados.put(SQLiteManager.DADOS_COMPL_COL_CAMINHADA_HORIZONTAL, dadosComplAnimal.getCaminadaHorizontal());
        dados.put(SQLiteManager.DADOS_COMPL_COL_CAMINHADA_VERTICAL, dadosComplAnimal.getCaminhadaVertical());
        dados.put(SQLiteManager.DADOS_COMPL_COL_SEMANA_LACTACAO, dadosComplAnimal.getSemanaLactacao());
        dados.put(SQLiteManager.DADOS_COMPL_COL_IS_PASTANDO, dadosComplAnimal.isPastando());
        dados.put(SQLiteManager.DADOS_COMPL_COL_IS_LACTACAO, dadosComplAnimal.isLactacao());
        dados.put(SQLiteManager.DADOS_COMPL_COL_IS_GESTANTE, dadosComplAnimal.isGestante());
        dados.put(SQLiteManager.DADOS_COMPL_COL_IS_CIO, dadosComplAnimal.isCio());

        int retorno = bancoDados.update(SQLiteManager.TABELA_DADOS_COMPL,
                dados, SQLiteManager.DADOS_COMPL_COL_ID + " = ?",
                new String[]{String.valueOf(dadosComplAnimal.getId())});

        bancoDados.close();

        return (retorno > 0);
    }

    public List<DadosComplAnimal> listarHistoricoDadosComplAnimais(int id_animal) {
        return this.getListaDadosComplAnimal(
                SQLiteManager.SELECT_TODOS + SQLiteManager.TABELA_DADOS_COMPL + "WHERE " + SQLiteManager.DADOS_COMPL_COL_ID_ANIMAL + " = " + String.valueOf(id_animal));
    }

    public int removerDadosCompl(DadosComplAnimal dadosComplAnimal) {
        bancoDados = gerenciador.getWritableDatabase();
        int result = bancoDados.delete(SQLiteManager.TABELA_DADOS_COMPL,
                SQLiteManager.DADOS_COMPL_COL_ID + " = ? ",
                new String[]{String.valueOf(dadosComplAnimal.getId())});

        bancoDados.close();

        return result;
    }

    private ContentValues getContentValues(DadosComplAnimal dadosComplAnimal) {
        ContentValues dados = new ContentValues();
        dados.put(SQLiteManager.DADOS_COMPL_COL_DATA, dadosComplAnimal.getData().getTimeInMillis());
        dados.put(SQLiteManager.DADOS_COMPL_COL_ID_ANIMAL, dadosComplAnimal.getAnimal());
        dados.put(SQLiteManager.DADOS_COMPL_COL_PESO_VIVO, dadosComplAnimal.getPesoVivo());
        dados.put(SQLiteManager.DADOS_COMPL_COL_EEC, dadosComplAnimal.getEEC());
        dados.put(SQLiteManager.DADOS_COMPL_COL_CAMINHADA_HORIZONTAL, dadosComplAnimal.getCaminadaHorizontal());
        dados.put(SQLiteManager.DADOS_COMPL_COL_CAMINHADA_VERTICAL, dadosComplAnimal.getCaminhadaVertical());
        dados.put(SQLiteManager.DADOS_COMPL_COL_SEMANA_LACTACAO, dadosComplAnimal.getSemanaLactacao());
        dados.put(SQLiteManager.DADOS_COMPL_COL_IS_PASTANDO, dadosComplAnimal.isPastando());
        dados.put(SQLiteManager.DADOS_COMPL_COL_IS_LACTACAO, dadosComplAnimal.isLactacao());
        dados.put(SQLiteManager.DADOS_COMPL_COL_IS_GESTANTE, dadosComplAnimal.isGestante());
        dados.put(SQLiteManager.DADOS_COMPL_COL_IS_CIO, dadosComplAnimal.isCio());

        return dados;
    }
}
