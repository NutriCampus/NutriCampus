package com.nutricampus.app.entities;


public class Cidade extends Estado {

    private int idEstado;

    public Cidade(int id, String sigla, String nome, int idEstado) {
        super(id, sigla, nome);
        this.idEstado = idEstado;
    }

    public int getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }
}
