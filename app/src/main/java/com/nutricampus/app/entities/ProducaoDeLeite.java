package com.nutricampus.app.entities;

import java.sql.Date;

/**
 * Created by Diego Bezerra on 15/06/17.
 * For project NutriCampus.
 * Contact: <diego.defb@gmail.com>
 */

public class ProducaoDeLeite {

    private int id;

    // A produção possui uma depedência a um determinado animal.
    private int id_animal;

    private Date data;

    // A medida utilizada para produção de leite é o kg.
    private float qntProduzida;

    // pct: abreviatura para percentual ou porcentagem.
    private float pctLactose;
    private float pctProteinaVerdadeira;
    private float pctProteinaBruta;

    public ProducaoDeLeite() {

    }

    public ProducaoDeLeite(Date data, int id_animal, float qntProduzida, float pctLactose,
                           float pctProteinaVerdadeira, float pctProteinaBruta) {
        this.data = data;
        this.id_animal = id_animal;
        this.qntProduzida = qntProduzida;
        this.pctLactose = pctLactose;
        this.pctProteinaVerdadeira = pctProteinaVerdadeira;
        this.pctProteinaBruta = pctProteinaBruta;
    }

    public ProducaoDeLeite(int id, Date data, int id_animal, float qntProduzida, float pctLactose,
                           float pctProteinaVerdadeira, float pctProteinaBruta) {
        this.id = id;
        this.data = data;
        this.id_animal = id_animal;
        this.qntProduzida = qntProduzida;
        this.pctLactose = pctLactose;
        this.pctProteinaVerdadeira = pctProteinaVerdadeira;
        this.pctProteinaBruta = pctProteinaBruta;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAnimal() { return id_animal; }

    public void setAnimal(int id_animal) { this.id_animal = id_animal; }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public float getQntProduzida() {
        return qntProduzida;
    }

    public void setQntProduzida(float qntProduzida) {
        this.qntProduzida = qntProduzida;
    }

    public float getPctLactose() {
        return pctLactose;
    }

    public void setPctLactose(float pctLactose) {
        this.pctLactose = pctLactose;
    }

    public float getPctProteinaVerdadeira() {
        return pctProteinaVerdadeira;
    }

    public void setPctProteinaVerdadeira(float pctProteinaVerdadeira) {
        this.pctProteinaVerdadeira = pctProteinaVerdadeira;
    }

    public float getPctProteinaBruta() {
        return pctProteinaBruta;
    }

    public void setPctProteinaBruta(float pctProteinaBruta) {
        this.pctProteinaBruta = pctProteinaBruta;
    }

    @Override
    public String toString() {
        return "ProducaoDeLeite{" +
                "id=" + id +
                "Animal=" + id_animal +
                ", data=" + data.toString() +
                ", qntProduzida=" + qntProduzida +
                ", pctLactose=" + pctLactose +
                ", pctProteinaVerdadeira=" + pctProteinaVerdadeira +
                ", pctProteinaBruta=" + pctProteinaBruta +
                '}';
    }
}
