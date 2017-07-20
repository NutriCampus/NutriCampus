package com.nutricampus.app.utils;

import java.util.Calendar;

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

    public static int[] DataStringToArray(String string) {
        String array[] = string.split("/");
        int i = 0;

        int valores[] = new int[3];
        for (String str : array) {
            valores[i++] = Integer.valueOf(str);
        }

        return valores;
    }


    public static String dataFormatada(Calendar calendario) {
        return (colocaZeroEsquerda(calendario.get(Calendar.DATE)) + "/" +
                colocaZeroEsquerda(calendario.get(Calendar.MONTH) + 1) + "/" +
                calendario.get(Calendar.YEAR));
    }

    private static String colocaZeroEsquerda(int valor) {
        return ((valor < 10) ? ("0" + String.valueOf(valor)) : String.valueOf(valor));
    }

}
