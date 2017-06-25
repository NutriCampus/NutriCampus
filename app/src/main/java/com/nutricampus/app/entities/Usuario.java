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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRegistro() {
        return registro;
    }

    public void setRegistro(String registro) {
        this.registro = registro;
    }
}
