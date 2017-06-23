package com.nutricampus.app.entities;

/**
 * Created by Mateus on 14/06/2017.
 * For project NutriCampus.
 * Contact: <paulomatew@gmail.com>
 */

public class Animal {
    public int id = 0;
    public String nome = null;
    public String raca = null;
    public String pesoVivo = null;
    public String pesoMat = null;
    public String dieta = null;

    public Animal(int id, String nome, String raca, String pesoVivo, String pesoMat, String dieta) {
        this.id = id;
        this.nome = nome;
        this.raca = raca;
        this.pesoVivo = pesoVivo;
        this.pesoMat = pesoMat;
        this.dieta = dieta;
    }

    public Animal(String nome, String raca, String pesoVivo, String pesoMat, String dieta) {
        this.nome = nome;
        this.raca = raca;
        this.pesoVivo = pesoVivo;
        this.pesoMat = pesoMat;
        this.dieta = dieta;
    }
}
