package com.nutricampus.app.utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Diego Bezerra on 15/06/17.
 * For project NutriCampus.
 * Contact: <diego.defb@gmail.com>
 */

public final class Conversor {
    private Conversor() {
        throw new IllegalStateException("Classe de utilidades");
    }

    public static int booleanToInt(boolean bol) {
        return ( bol ? 1 : 0);
    }

    public static boolean intToBoolean(int i) {
        return ( i == 1 );
    }

    public static boolean StringToBoolean(String s) { return (s.equals("true"));}

    public static Date StringToDate(String string) {
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            date = sdf.parse(string);
        } catch (ParseException e) {
            Log.getStackTraceString(e);
        }
        return date;
    }


    public static String dataFormatada(Calendar calendario) {
        return (colocaZeroEsquerda(calendario.get(Calendar.DATE)) + "/" +
                colocaZeroEsquerda(calendario.get(Calendar.MONTH) + 1) + "/" +
                calendario.get(Calendar.YEAR));
    }

    private static String colocaZeroEsquerda(int valor) {
        return ((valor < 10) ? ("0" + String.valueOf(valor)) : String.valueOf(valor));
    }

    public static int mesStringParaInt(String mes) {
        HashMap<String, Integer> meses = new HashMap<>();
        meses.put("janeiro", 0);
        meses.put("fevereiro", 1);
        meses.put("marco", 2);
        meses.put("abril", 3);
        meses.put("maio", 4);
        meses.put("junho", 5);
        meses.put("julho", 6);
        meses.put("agosto", 7);
        meses.put("setembro", 8);
        meses.put("outubro", 9);
        meses.put("novembro", 10);
        meses.put("dezembro", 11);

        int valor = (meses.get(String.valueOf(mes).toLowerCase()) != null) ? meses.get(String.valueOf(mes).toLowerCase()) : 12;
        return valor;
    }

}
