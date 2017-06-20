package com.nutricampus.app.entities;

/**
 * Created by Mateus on 14/06/2017.
 * For project NutriCampus.
 * Contact: <paulomatew@gmail.com>
 */

public class Usuario {
    public int id = 0;
    public String cpf = null;
    public String email = null;
    public String senha = null;
    public String nome = null;
    public String registro = null;

    public Usuario(String cpf, String email, String senha, String nome, String registro) {
        this.cpf = cpf;
        this.email = email;
        this.senha = senha;
        this.nome = nome;
        this.registro = registro;
    }

    public Usuario(int id, String cpf, String email, String senha, String nome, String registro) {
        this.id = id;
        this.cpf = cpf;
        this.email = email;
        this.senha = senha;
        this.nome = nome;
        this.registro = registro;
    }
}
