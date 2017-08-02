package com.nutricampus.app.entities;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by Diego Bezerra on 14/06/17.
 * For project NutriCampus.
 * Contact: <diego.defb@gmail.com>
 */

public class Animal implements Serializable {

    private int id;
    private String indentificador;
    private int idUsuario;
    private int idPropriedade;
    private Calendar dataDeNascimento;
    private boolean isAtivo;

    public Animal() {

    }

    public Animal(String indentificador, int idPropriedade,
                  Calendar dataDeNascimento, boolean isAtivo, int idUsuario) {

        this.indentificador = indentificador;
        this.idPropriedade = idPropriedade;
        this.dataDeNascimento = dataDeNascimento;
        this.isAtivo = isAtivo;
        this.idUsuario = idUsuario;

    }

    public Animal(int id, String indentificador, int idPropriedade,
                  Calendar dataDeNascimento, boolean isAtivo) {

        this.id = id;
        this.indentificador = indentificador;
        this.idPropriedade = idPropriedade;
        this.dataDeNascimento = dataDeNascimento;
        this.isAtivo = isAtivo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getIndentificador() {
        return indentificador;
    }

    public int getPropriedade() {
        return idPropriedade;
    }

    public void setPropriedade(int propriedade) {
        this.idPropriedade = propriedade;
    }

    public void setIndentificador(String indentificador) {
        this.indentificador = indentificador;
    }

    public Calendar getDataDeNascimento() {
        return dataDeNascimento;
    }

    public void setDataDeNascimento(Calendar dataDeNascimento) {
        this.dataDeNascimento = dataDeNascimento;
    }

    public int getIdPropriedade() {
        return idPropriedade;
    }

    public void setIdPropriedade(int idPropriedade) {
        this.idPropriedade = idPropriedade;
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

