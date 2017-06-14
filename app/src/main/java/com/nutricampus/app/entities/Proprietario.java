package com.nutricampus.app.entities;

import java.util.ArrayList;

/**
 * Created by Mateus on 14/06/2017.
 * For project NutriCampus.
 * Contact: <paulomatew@gmail.com>
 */

public class Proprietario {
    public int id = 0;
    public String nome = null;
    public String cpf = null;
    public ArrayList<String> telefones = new ArrayList<>();

    public Proprietario(String nome, String cpf) {
        this.nome = nome;
        this.cpf = cpf;
    }

    public Proprietario(int id, String nome, String cpf) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
    }
}
