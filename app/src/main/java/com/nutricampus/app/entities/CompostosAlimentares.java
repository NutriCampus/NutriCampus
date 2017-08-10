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
    private double ms;
    private double fdn;
    private double ee;
    private double mm;
    private double cnf;
    private double pb;
    private double ndt;
    private double fda;
    private String descricao;

    public CompostosAlimentares() {
    }

    public CompostosAlimentares(String tipo, String identificador, double ms, double fdn,
                                double ee, double mm, double cnf, double pb, double ndt, double fda, String descricao) {
        this.tipo = tipo;
        this.identificador = identificador;
        this.ms = ms;
        this.fdn = fdn;
        this.ee = ee;
        this.mm = mm;
        this.cnf = cnf;
        this.pb = pb;
        this.ndt = ndt;
        this.fda = fda;
        this.descricao = descricao;
    }

    public CompostosAlimentares(int id, String tipo, String identificador, double ms, double fdn,
                                double ee, double mm, double cnf, double pb, double ndt, double fda, String descricao) {

        this(tipo, identificador, ms, fdn, ee, mm, cnf, pb, ndt, fda, descricao);
        this.id = id;
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

    public double getMs() {
        return ms;
    }

    public void setMs(double ms) {
        this.ms = ms;
    }

    public double getFdn() {
        return fdn;
    }

    public void setFdn(double fdn) {
        this.fdn = fdn;
    }

    public double getEe() {
        return ee;
    }

    public void setEe(double ee) {
        this.ee = ee;
    }

    public double getMm() {
        return mm;
    }

    public void setMm(double mm) {
        this.mm = mm;
    }

    public double getCnf() {
        return cnf;
    }

    public void setCnf(double cnf) {
        this.cnf = cnf;
    }

    public double getPb() {
        return pb;
    }

    public void setPb(double pb) {
        this.pb = pb;
    }

    public double getNdt() {
        return ndt;
    }

    public void setNdt(double ndt) {
        this.ndt = ndt;
    }

    public double getFda() {
        return fda;
    }

    public void setFda(double fda) {
        this.fda = fda;
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
                ", ms=" + ms +
                ", fdn=" + fdn +
                ", ee=" + ee +
                ", mm=" + mm +
                ", cnf=" + cnf +
                ", pb=" + pb +
                ", ndt=" + ndt +
                ", fda=" + fda +
                ", descricao='" + descricao + '\'' +
                '}';
    }

}