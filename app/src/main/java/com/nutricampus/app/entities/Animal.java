package com.nutricampus.app.entities;

import com.nutricampus.app.utils.Conversor;

import java.sql.Date;

/**
 * Created by Diego Bezerra on 14/06/17.
 * For project NutriCampus.
 * Contact: <diego.defb@gmail.com>
 */

public class Animal {

    private int id;
    private String indentificador;
    private int id_usuario;
    private int id_propriedade;
    private Date dataDeNascimento;
    private boolean isAtivo;

    public Animal() {

    }

    public Animal(String indentificador, int id_propriedade,
                  Date dataDeNascimento, boolean isAtivo) {

        this.indentificador = indentificador;
        this.id_propriedade = id_propriedade;
        this.dataDeNascimento = dataDeNascimento;
        this.isAtivo = isAtivo;

    }

    public Animal(int id, String indentificador, int id_propriedade,
                  Date dataDeNascimento, boolean isAtivo) {

        this.id = id;
        this.indentificador = indentificador;
        this.id_propriedade = id_propriedade;
        this.dataDeNascimento = dataDeNascimento;
        this.isAtivo = isAtivo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getIndentificador() {
        return indentificador;
    }

    public int getPropriedade() {
        return id_propriedade;
    }

    public void setPropriedade(int propriedade) {
        this.id_propriedade = propriedade;
    }

    public void setIndentificador(String indentificador) {
        this.indentificador = indentificador;
    }

    public Date getDataDeNascimento() {
        return dataDeNascimento;
    }

    public void setDataDeNascimento(Date dataDeNascimento) {
        this.dataDeNascimento = dataDeNascimento;
    }

    public boolean isAtivo() {
        return isAtivo;
    }

    public void setAtivo(boolean ativo) {
        isAtivo = ativo;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "id=" + id +
                ", indentificador='" + indentificador + '\'' +
                ", dataDeNascimento=" + dataDeNascimento +
                ", isAtivo=" + isAtivo +
                '}';
    }

}