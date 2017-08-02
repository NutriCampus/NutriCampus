package com.nutricampus.app.entities;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by Diego Bezerra on 15/06/17.
 * For project NutriCampus.
 * Contact: <diego.defb@gmail.com>
 */

public class DadosComplAnimal implements Serializable {

    private int id;
    private Calendar data;
    private int idAnimal;
    private float pesoVivo;

    /** O eec (Escore de Condição Corporal) mede, em uma escala de 1 a 5, a quantidade de
     gordura presente no corpo do animal.*/
    private int eec;

    private float caminadaHorizontal;
    private float caminhadaVertical;
    private int semanaLactacao;
    private boolean isPastando;
    private boolean isLactacao;
    private boolean isGestante;
    private boolean isCio;

    public DadosComplAnimal() {

    }


    public DadosComplAnimal(Calendar data, int idAnimal, float pesoVivo, int eec, float caminadaHorizontal,
                            float caminhadaVertical, int semanaLactacao, boolean isPastando,
                            boolean isLactacao, boolean isGestante, boolean isCio) {
        this(data, pesoVivo, eec, caminadaHorizontal, caminhadaVertical, semanaLactacao, isPastando,
                isLactacao, isGestante, isCio);
        this.idAnimal = idAnimal;
    }

    public DadosComplAnimal(int id, Calendar data, int idAnimal, float pesoVivo, int eec, float caminadaHorizontal,
                            float caminhadaVertical, int semanaLactacao, boolean isPastando,
                            boolean isLactacao, boolean isGestante, boolean isCio) {

        this(data, idAnimal, pesoVivo, eec, caminadaHorizontal, caminhadaVertical, semanaLactacao, isPastando,
                isLactacao, isGestante, isCio);
        this.id = id;
    }

    public DadosComplAnimal(Calendar data, float pesoVivo, int eec, float caminadaHorizontal,
                            float caminhadaVertical, int semanaLactacao, boolean isPastando,
                            boolean isLactacao, boolean isGestante, boolean isCio) {
        this.data = data;
        this.pesoVivo = pesoVivo;
        this.eec = eec;
        this.caminadaHorizontal = caminadaHorizontal;
        this.caminhadaVertical = caminhadaVertical;
        this.semanaLactacao = semanaLactacao;
        this.isPastando = isPastando;
        this.isLactacao = isLactacao;
        this.isGestante = isGestante;
        this.isCio = isCio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Calendar getData() {
        return data;
    }

    public void setData(Calendar data) {
        this.data = data;
    }

    public int getAnimal() {
        return idAnimal;
    }

    public void setAnimal(int id_animal) {
        this.idAnimal = id_animal;
    }

    public float getPesoVivo() {
        return pesoVivo;
    }

    public void setPesoVivo(float pesoVivo) {
        this.pesoVivo = pesoVivo;
    }

    public int getEec() {
        return eec;
    }

    public void setEec(int eec) {
        this.eec = eec;
    }

    public float getCaminadaHorizontal() {
        return caminadaHorizontal;
    }

    public void setCaminadaHorizontal(float caminadaHorizontal) {
        this.caminadaHorizontal = caminadaHorizontal;
    }

    public float getCaminhadaVertical() {
        return caminhadaVertical;
    }

    public void setCaminhadaVertical(float caminhadaVertical) {
        this.caminhadaVertical = caminhadaVertical;
    }

    public int getSemanaLactacao() {
        return semanaLactacao;
    }

    public void setSemanaLactacao(int semanaLactacao) {
        this.semanaLactacao = semanaLactacao;
    }

    public boolean isPastando() {
        return isPastando;
    }

    public void setPastando(boolean pastando) {
        isPastando = pastando;
    }

    public boolean isLactacao() {
        return isLactacao;
    }

    public void setLactacao(boolean lactacao) {
        isLactacao = lactacao;
    }

    public boolean isGestante() {
        return isGestante;
    }

    public void setGestante(boolean gestante) {
        isGestante = gestante;
    }

    public boolean isCio() {
        return isCio;
    }

    public void setCio(boolean cio) { isCio = cio; }

    @Override
    public String toString() {
        return "DadosComplAnimal{" +
                "id=" + id +
                ", animal=" + idAnimal +
                ", data=" + data.toString() +
                ", pesoVivo=" + pesoVivo +
                ", eec=" + eec +
                ", caminadaHorizontal=" + caminadaHorizontal +
                ", caminhadaVertical=" + caminhadaVertical +
                ", semanaLactacao=" + semanaLactacao +
                ", Pastando?=" + isPastando +
                ", Lactacao?=" + isLactacao +
                ", Gestante?=" + isGestante +
                ", Cio?=" + isCio +
                '}';
    }
}
