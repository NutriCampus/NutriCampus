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
    private double MS;
    private double FDN;
    private double EE;
    private double MM;
    private double CNF;
    private double PB;
    private double NDT;
    private double FDA;
    private String descricao;

    public CompostosAlimentares() {
    }

    public CompostosAlimentares(String tipo, String identificador, double MS, double FDN,
                                double EE, double MM, double CNF, double PB, double NDT, double FDA, String descricao) {
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

    public CompostosAlimentares(int id, String tipo, String identificador, double MS, double FDN,
                                double EE, double MM, double CNF, double PB, double NDT, double FDA, String descricao) {
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

    public double getMS() {
        return MS;
    }

    public void setMS(double MS) {
        this.MS = MS;
    }

    public double getFDN() {
        return FDN;
    }

    public void setFDN(double FDN) {
        this.FDN = FDN;
    }

    public double getEE() {
        return EE;
    }

    public void setEE(double EE) {
        this.EE = EE;
    }

    public double getMM() {
        return MM;
    }

    public void setMM(double MM) {
        this.MM = MM;
    }

    public double getCNF() {
        return CNF;
    }

    public void setCNF(double CNF) {
        this.CNF = CNF;
    }

    public double getPB() {
        return PB;
    }

    public void setPB(double PB) {
        this.PB = PB;
    }

    public double getNDT() {
        return NDT;
    }

    public void setNDT(double NDT) {
        this.NDT = NDT;
    }

    public double getFDA() {
        return FDA;
    }

    public void setFDA(double FDA) {
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

    public void print() {
        System.out.println(this.toString());
    }
}
