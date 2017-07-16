package com.nutricampus.app.model;

/*
  Created by Felipe on 23/06/2017.
  For project NutriCampus.
  Contact: <felipeguimaraes540@gmail.com>
 */

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public abstract class Mascara {
    public static final String CPF_MASK       = "###.###.###-##";
    public static final String CELULAR_MASK   = "(##) ##### ####";
    public static final String CEP_MASK       = "#####-###";

    private static String unmask(String s) {
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
                mascara.append(text.charAt(m));
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

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void afterTextChanged(Editable s) {}
        };
    }

    public static boolean validarCpf(String cpfComplete) {
        StringBuilder cpf = new StringBuilder();

        for (char c : cpfComplete.toCharArray()) {
            if (Character.isDigit(c)) {
                cpf.append(c);
            }
        }

        int digitoCpf;
        int somaD1 = 0;
        int somaD2 = 0;
        int peso = 12;
        int digito1 = 0;
        int digito2 = 0;
        int resto = 0;
        String digVerificar = "";
        String digResultado = "";

        for (int i = 0; i < cpf.length() - 2; i++) {
            digitoCpf = Integer.parseInt(cpf.substring(i, i + 1));
            somaD1 += (digitoCpf * (peso - 2));
            somaD2 += (digitoCpf * (peso - 1));
            peso--;
        }

        resto = somaD1 % 11;

        if (resto < 2) {
            digito1 = 0;
        } else {
            digito1 = 11 - resto;
        }

        somaD2 += (2 * digito1);

        resto = somaD2 % 11;

        if (resto < 2) {
            digito2 = 0;
        } else {
            digito2 = 11 - resto;
        }

        digResultado = String.valueOf(digito1);
        digResultado += String.valueOf(digito2);

        digVerificar = cpf.substring(cpf.length() - 2, cpf.length());

        return digResultado.equals(digVerificar);

    }
}