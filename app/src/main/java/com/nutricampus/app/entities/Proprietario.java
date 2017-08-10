package com.nutricampus.app.entities;

import java.io.Serializable;

/**
 * Created by Felipe on 05/07/2017.
 * For project NutriCampus.
 * Contact: <felipeguimaraes540@gmail.com>
 */

public class Proprietario implements Serializable {

    private int id;
    private String nome;
    private String cpf;
    private String telefone;
    private String email;

    public Proprietario() {
    }

    public Proprietario(String cpf, String nome, String email, String telefone) {
        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
    }

    public Proprietario(int id, String cpf, String nome, String email, String telefone) {
        this(cpf, nome, email, telefone);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return this.getNome();
    }

    @Override
    public boolean equals(Object obj) {

        if ((obj == null) || !(obj instanceof Proprietario))
            return false;

        Proprietario objeto = (Proprietario) obj;

        return ((objeto.getId() == (this.getId())) &&
                (objeto.getCpf().equals(this.getCpf())) &&
                (objeto.getNome().equals(this.getNome())) &&
                (objeto.getEmail().equals(this.getEmail())) &&
                (objeto.getTelefone().equals(this.getTelefone())));
    }


    @Override
    public int hashCode() {
        int result = this.getId();
        result = 31 * result + this.getCpf().hashCode();
        result = 31 * result + this.getNome().hashCode();
        result = 31 * result + this.getEmail().hashCode();
        result = 31 * result + this.getTelefone().hashCode();

        return result;

    }
}