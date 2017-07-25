package com.nutricampus.app.entities;

import java.util.Calendar;

/** Created by Diego Bezerra on 15/06/17.
 * For project NutriCampus.
 * Contact: <diego.defb@gmail.com>
 */

public class Prole {

    private int id;
    private int id_matriz;
    private Calendar dataDeNascimento;
    private float pesoDeNascimento;
    private boolean isNatimorto;

    public Prole() {
    }

    public Prole(int id, int id_matriz, Calendar dataDeNascimento,
                 float pesoDeNascimento, boolean isNatimorto) {
        this.id = id;
        this.id_matriz = id_matriz;
        this.dataDeNascimento = dataDeNascimento;
        this.pesoDeNascimento = pesoDeNascimento;
        this.isNatimorto = isNatimorto;
    }

    public Prole(int id_matriz, Calendar dataDeNascimento,
                 float pesoDeNascimento, boolean isNatimorto) {
        this.id_matriz = id_matriz;
        this.dataDeNascimento = dataDeNascimento;
        this.pesoDeNascimento = pesoDeNascimento;
        this.isNatimorto = isNatimorto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMatriz() {
        return id_matriz;
    }

    public void setMatriz(int id_matriz) {
        this.id_matriz = id_matriz;
    }

    public Calendar getDataDeNascimento() {
        return dataDeNascimento;
    }

    public void setDataDeNascimento(Calendar dataDeNascimento) {
        this.dataDeNascimento = dataDeNascimento;
    }

    public float getPesoDeNascimento() {
        return pesoDeNascimento;
    }

    public void setPesoDeNascimento(float pesoDeNascimento) {
        this.pesoDeNascimento = pesoDeNascimento;
    }

    public boolean isNatimorto() {
        return isNatimorto;
    }

    public void setNatimorto(boolean natimorto) {
        isNatimorto = natimorto;
    }

    @Override
    public String toString() {
        return "Descendente{" +
                "id=" + id +
                ", matriz=" + id_matriz +
                ", dataDeNascimento=" + dataDeNascimento.toString() +
                ", pesoDeNascimento=" + pesoDeNascimento +
                ", isNatimorto=" + isNatimorto +
                '}';
    }
}