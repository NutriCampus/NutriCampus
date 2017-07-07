package com.nutricampus.app.entities;

/**
 * Created by Mateus on 14/06/2017.
 * For project NutriCampus.
 * Contact: <paulomatew@gmail.com>
 */

public class Propriedade {

    private int id;
    private String nome;
    private String telefone;
    private String logradouro;
    private String bairro;
    private String cep;
    private String cidade;
    private String estado;
    private String numero;
    private Proprietario proprietario;

    public Propriedade() {}

    public Propriedade(String nome, String telefone, String logradouro, String bairro, String cep, String cidade, String estado, String numero) {
        this.nome = nome;
        this.telefone = telefone;
        this.logradouro = logradouro;
        this.bairro = bairro;
        this.cep = cep;
        this.cidade = cidade;
        this.estado = estado;
        this.numero = numero;
    }

    public Propriedade(int id, String nome, String telefone, String logradouro, String bairro, String cep, String cidade, String estado, String numero) {
        this(nome, telefone, logradouro, bairro, cep, cidade, estado,numero);
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

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Proprietario getProprietario() { return proprietario; }

    public void setProprietario(Proprietario proprietario) {
        this.proprietario = proprietario;
    }
}
