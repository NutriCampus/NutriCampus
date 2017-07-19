package com.nutricampus.app.utils;

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

}
