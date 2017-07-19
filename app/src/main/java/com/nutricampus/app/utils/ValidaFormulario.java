package com.nutricampus.app.utils;

import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public final class ValidaFormulario {

    private ValidaFormulario() {
        throw new IllegalStateException("Classe de utilidades");
    }

    public static List<TextView> camposTextosVazios(List<TextView> campos) {
        List<TextView> camposVazios = new ArrayList<>();

        for (TextView view : campos) {
            if (view.getText().toString().isEmpty())
                camposVazios.add(view);
        }

        return camposVazios;
    }


    /*
     * Implementar teste para ver se é inteiro
     */

    public static boolean isCEPValido(String cep) {
        String cepSemMascara = Mascara.unmask(cep);
        return (cepSemMascara.length() == 8);
    }

    public static boolean isTelefoneValido(String telefone) {
        String telefoneSemMascara = Mascara.unmask(telefone);
        return (telefoneSemMascara.length() == 11) || (telefoneSemMascara.length() == 10);
    }


    /**
     * Verifica se um seleção é válida ou não, de acordo com o item selecionado e o valor considerado
     * inválido.
     * <p>
     * Comum para o uso em spinners para opções do tipo "rótulo" (Ex.: "Selecione um item aqui:")
     *
     * @param selecionado Item a ser comparado
     * @param invalido    Valor para comparação, aquele considerado inválido
     * @return Válido ou não
     */
    public static boolean isSelecaoValida(int selecionado, int invalido) {
        return (selecionado != invalido);
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