package com.nutricampus.app.entities;

import com.nutricampus.app.utils.Conversor;

import java.io.Serializable;
import java.sql.Date;
import java.util.Calendar;

/**
 * Created by Diego Bezerra on 14/06/17.
 * For project NutriCampus.
 * Contact: <diego.defb@gmail.com>
 */

public class Animal implements Serializable {

    private int id;
    private String indentificador;
    private int id_usuario;
    private int id_propriedade;
    private Calendar dataDeNascimento;
    private boolean isAtivo;

    public Animal() {

    }

    public Animal(String indentificador, int id_propriedade,
                  Calendar dataDeNascimento, boolean isAtivo, int id_usuario) {

        this.indentificador = indentificador;
        this.id_propriedade = id_propriedade;
        this.dataDeNascimento = dataDeNascimento;
        this.isAtivo = isAtivo;
        this.id_usuario = id_usuario;

    }

    public Animal(int id, String identificador, int id_propriedade,
                  Calendar dataDeNascimento, boolean isAtivo, int id_usuario) {
        this(identificador, id_propriedade, dataDeNascimento, isAtivo, id_usuario);
        this.id = id;
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

    public Calendar getDataDeNascimento() {
        return dataDeNascimento;
    }

    public void setDataDeNascimento(Calendar dataDeNascimento) {
        this.dataDeNascimento = dataDeNascimento;
    }

    public int getId_propriedade() {
        return id_propriedade;
    }

    public void setId_propriedade(int id_propriedade) {
        this.id_propriedade = id_propriedade;
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
                ", idUsuario=" + id_usuario +
                "}";
    }

}

