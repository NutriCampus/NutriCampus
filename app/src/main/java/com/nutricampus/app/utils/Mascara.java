package com.nutricampus.app.utils;

/*
  Created by Felipe on 23/06/2017.
  For project NutriCampus.
  Contact: <felipeguimaraes540@gmail.com>
 */

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.Calendar;

public final class Mascara {
    public static final String CPF_MASK       = "###.###.###-##";
    public static final String CELULAR_MASK   = "(##) ##### ####";
    public static final String CEP_MASK       = "#####-###";

    private Mascara() {
        throw new IllegalStateException("Classe de utilidades");
    }

    public static String unmask(String s) {
        return s.replaceAll("[.]", "").replaceAll("[-]", "")
                .replaceAll("[/]", "").replaceAll("[(]", "")
                .replaceAll("[)]", "").replaceAll(" ", "")
                .replaceAll(",", "");
    }

    public static boolean isSinal(char c) {
        return (c == '.' || c == '-' || c == '/' || c == '(' || c == ')' || c == ',' || c == ' ');
    }

    public static String mascarar(String mask, String text) {
        int i = 0;
        StringBuilder mascara = new StringBuilder();
        for (char m : mask.toCharArray()) {
            if (m != '#') {
                mascara.append(m);
                continue;
            }
            try {
                mascara.append(text.charAt(i));
            } catch (Exception e) {
                break;
            }
            i++;
        }

        return String.valueOf(mascara);
    }

    public static TextWatcher insert(final String mask, final EditText ediTxt) {
        return new TextWatcher() {
            boolean isUpdating;
            String old = "";

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str = Mascara.unmask(s.toString());
                StringBuilder mascara = new StringBuilder();
                if (isUpdating) {
                    old = str;
                    isUpdating = false;
                    return;
                }

                int index = 0;
                for (int i = 0; i < mask.length(); i++) {
                    char m = mask.charAt(i);
                    if (m != '#') {
                        if ((index == str.length()) && (str.length() < old.length())) {
                            continue;
                        }
                        mascara.append(m);
                        continue;
                    }

                    try {
                        mascara.append(str.charAt(index));
                    } catch (Exception e) {
                        break;
                    }

                    index++;
                }

                if (mascara.length() > 0) {
                    char last_char = mascara.charAt(mascara.length() - 1);
                    boolean hadSign = false;
                    while (isSinal(last_char) && str.length() == old.length()) {
                        mascara = new StringBuilder(mascara.substring(0, mascara.length() - 1));
                        last_char = mascara.charAt(mascara.length() - 1);
                        hadSign = true;
                    }

                    if (mascara.length() > 0 && hadSign) {
                        mascara = new StringBuilder(mascara.substring(0, mascara.length() - 1));
                    }
                }

                isUpdating = true;
                ediTxt.setText(mascara.toString());
                ediTxt.setSelection(mascara.length());
            }


            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Implementada por ser requerida pela interface, mas não necessária no contexto atual
            }

            public void afterTextChanged(Editable s) {
                // Implementada por ser requerida pela interface, mas não necessária no contexto atual
            }
        };
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