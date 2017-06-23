package com.nutricampus.app.entities;

/**
 * Created by Mateus on 14/06/2017.
 * For project NutriCampus.
 * Contact: <paulomatew@gmail.com>
 */

public class Propriedade {
    public int id = 0;
    public String nome = null;
    public String uf = null;
    public String cidade = null;
    public String bairro = null;
    public String logradouro = null;
    public String numero = null;
    public Proprietario proprietario = null;

    public Propriedade(String nome, String uf, String cidade, String bairro, String logradouro, String numero, Proprietario proprietario) {
        this.nome = nome;
        this.uf = uf;
        this.cidade = cidade;
        this.bairro = bairro;
        this.logradouro = logradouro;
        this.numero = numero;
        this.proprietario = proprietario;
    }

    public Propriedade(int id, String nome, String uf, String cidade, String bairro, String logradouro, String numero, Proprietario proprietario) {
        this.id = id;
        this.nome = nome;
        this.uf = uf;
        this.cidade = cidade;
        this.bairro = bairro;
        this.logradouro = logradouro;
        this.numero = numero;
        this.proprietario = proprietario;
    }
}
