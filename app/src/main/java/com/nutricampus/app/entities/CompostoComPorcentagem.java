package com.nutricampus.app.entities;

/**
 * Created by Paulo Mateus on 20/08/17.
 * For project NutriCampus.
 * Contact: <paulomatew@gmail.com>
 */
public class CompostoComPorcentagem {
    public CompostosAlimentares composto = null;
    public double porcentagem = -1;

    public CompostoComPorcentagem(CompostosAlimentares composto, double porcentagem) {
        this.composto = composto;
        this.porcentagem = porcentagem;
    }

    public void print() {
        System.out.println(composto.getIdentificador()
                + " || " + porcentagem + " (" + (porcentagem * 100) + ")");
    }
}

