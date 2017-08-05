package com.nutricampus.app.entities;


import java.util.Calendar;

/**
 * Created by Diego Bezerra on 15/06/17.
 * For project NutriCampus.
 * Contact: <diego.defb@gmail.com>
 */

public class ProducaoDeLeite {

    private int id;

    // A produção possui uma depedência a um determinado animal.
    private int idAnimal;

    private Calendar data;

    // A medida utilizada para produção de leite é o kg.
    private float qntProduzida;

    // pct: abreviatura para percentual ou porcentagem.
    private float pctLactose;
    private float gordura;
    private float pctProteinaVerdadeira;
    private float pctProteinaBruta;

    public ProducaoDeLeite() {

    }

    public ProducaoDeLeite(Calendar data, int idAnimal, float qntProduzida, float pctLactose,
                           float pctProteinaVerdadeira, float pctProteinaBruta, float gordura) {
        this.data = data;
        this.idAnimal = idAnimal;
        this.qntProduzida = qntProduzida;
        this.pctLactose = pctLactose;
        this.pctProteinaVerdadeira = pctProteinaVerdadeira;
        this.pctProteinaBruta = pctProteinaBruta;
        this.gordura = gordura;
    }

    public ProducaoDeLeite(int id, Calendar data, int idAnimal, float qntProduzida, float pctLactose,
                           float pctProteinaVerdadeira, float pctProteinaBruta, float gordura) {
        this(data, idAnimal, qntProduzida, pctLactose, pctProteinaVerdadeira, pctProteinaBruta, gordura);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAnimal() {
        return idAnimal;
    }

    public void setAnimal(int id_animal) {
        this.idAnimal = id_animal;
    }

    public Calendar getData() {
        return data;
    }

    public void setData(Calendar data) {
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

    public int getIdAnimal() {
        return idAnimal;
    }

    public void setIdAnimal(int idAnimal) {
        this.idAnimal = idAnimal;
    }

    public float getGordura() {
        return gordura;
    }

    public void setGordura(float gordura) {
        this.gordura = gordura;
    }

    @Override
    public String toString() {
        return "ProducaoDeLeite{" +
                "id=" + id +
                "Animal=" + idAnimal +
                ", data=" + data.toString() +
                ", qntProduzida=" + qntProduzida +
                ", pctLactose=" + pctLactose +
                ", pctProteinaVerdadeira=" + pctProteinaVerdadeira +
                ", pctProteinaBruta=" + pctProteinaBruta +
                ", gordura=" + gordura +
                '}';
    }
}
