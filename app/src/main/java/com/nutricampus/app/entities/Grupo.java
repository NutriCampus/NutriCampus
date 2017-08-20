package com.nutricampus.app.entities;

import java.io.Serializable;

/**
 * Created by felipe on 19/08/17.
 */

public class Grupo implements Serializable {

    private int id;
    private String identificador;
    private String observacao;
    private int idUsuario;

    public Grupo () {

    }

    public Grupo(String identificador, String observacao, int idUsuario) {
        this.identificador = identificador;
        this.observacao = observacao;
        this.idUsuario = idUsuario;
    }

    public Grupo(int id, String identificador, String observacao, int idUsuario) {
        this(identificador, observacao, idUsuario);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
}
