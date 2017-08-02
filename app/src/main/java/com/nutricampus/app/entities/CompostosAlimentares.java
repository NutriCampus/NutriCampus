package com.nutricampus.app.entities;

/**
 * Created by Diego Bezerra on 02/08/17.
 * For project NutriCampus.
 * Contact: <diego.defb@gmail.com>
 */

public class CompostosAlimentares {

    private int id;
    private String tipo;
    private String identificador;
    private float MS;
    private float FDN;
    private float EE;
    private float MM;
    private float CNF;
    private float PB;
    private float NDT;
    private float FDA;
    private String descricao;

    public CompostosAlimentares() {
    }

    public CompostosAlimentares(String tipo, String identificador, float MS, float FDN,
                                float EE, float MM, float CNF, float PB, float NDT, float FDA, String descricao) {
        this.tipo = tipo;
        this.identificador = identificador;
        this.MS = MS;
        this.FDN = FDN;
        this.EE = EE;
        this.MM = MM;
        this.CNF = CNF;
        this.PB = PB;
        this.NDT = NDT;
        this.FDA = FDA;
        this.descricao = descricao;
    }

    public CompostosAlimentares(int id, String tipo, String identificador, float MS, float FDN,
                                float EE, float MM, float CNF, float PB, float NDT, float FDA, String descricao) {
        this.id = id;
        this.tipo = tipo;
        this.identificador = identificador;
        this.MS = MS;
        this.FDN = FDN;
        this.EE = EE;
        this.MM = MM;
        this.CNF = CNF;
        this.PB = PB;
        this.NDT = NDT;
        this.FDA = FDA;
        this.descricao = descricao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public float getMS() {
        return MS;
    }

    public void setMS(float MS) {
        this.MS = MS;
    }

    public float getFDN() {
        return FDN;
    }

    public void setFDN(float FDN) {
        this.FDN = FDN;
    }

    public float getEE() {
        return EE;
    }

    public void setEE(float EE) {
        this.EE = EE;
    }

    public float getMM() {
        return MM;
    }

    public void setMM(float MM) {
        this.MM = MM;
    }

    public float getCNF() {
        return CNF;
    }

    public void setCNF(float CNF) {
        this.CNF = CNF;
    }

    public float getPB() {
        return PB;
    }

    public void setPB(float PB) {
        this.PB = PB;
    }

    public float getNDT() {
        return NDT;
    }

    public void setNDT(float NDT) {
        this.NDT = NDT;
    }

    public float getFDA() {
        return FDA;
    }

    public void setFDA(float FDA) {
        this.FDA = FDA;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return "CompostosAlimentares{" +
                "id=" + id +
                ", tipo='" + tipo + '\'' +
                ", identificador='" + identificador + '\'' +
                ", MS=" + MS +
                ", FDN=" + FDN +
                ", EE=" + EE +
                ", MM=" + MM +
                ", CNF=" + CNF +
                ", PB=" + PB +
                ", NDT=" + NDT +
                ", FDA=" + FDA +
                ", descricao='" + descricao + '\'' +
                '}';
    }
}
