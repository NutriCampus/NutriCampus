package com.nutricampus.app.entities;

/**
 * Created by Diego Bezerra on 20/06/2017.
 * For project NutriCampus.
 * Contact: <diego.defb@gmail.com>
 */

public class Usuario {

    private int id;
    private String crmv;
    private String cpf;
    private String nome;
    private String email;
    private String senha;

    public Usuario() { }

    public Usuario(String crmv, String cpf, String nome, String email, String senha) {
        this.crmv = crmv;
        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public Usuario(int id, String crmv, String cpf, String nome, String email, String senha) {
        this.id = id;
        this.crmv = crmv;
        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCrmv() {
        return crmv;
    }

    public void setCrmv(String crmv) {
        this.crmv = crmv;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    @Override
    public String toString() {
        return "Usuario{" +
                "ID: " + id +
                ", crmv/CRZ: '" + crmv + '\'' +
                ", CPF: '" + cpf + '\'' +
                ", Nome: '" + nome + '\'' +
                ", Email: '" + email + '\'' +
                ", Senha: '" + senha + '\'' +
                '}';
    }
}
