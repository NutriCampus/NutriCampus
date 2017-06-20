package com.nutricampus.app.entities;

/**
 * Created by Mateus on 14/06/2017.
 * For project NutriCampus.
 * Contact: <paulomatew@gmail.com>
 */

public class CompostoAlimentar {
    public int id = 0;
    public String nome = null;
    public String descricao = null;

    public CompostoAlimentar(int id, String nome, String descricao) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
    }

    public CompostoAlimentar(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }
}
