package com.nutricampus.app.entities;

/**
 * Created by Mateus on 14/06/2017.
 * For project NutriCampus.
 * Contact: <paulomatew@gmail.com>
 */

public class Propriedade {

    private int id;
    private String nome;
    private String telefone;
    private String logradouro;
    private String bairro;
    private String cep;
    private String cidade;
    private String estado;
    private String numero;
    private int idProprietario;
    private int idUsuario;

    // A definir permanencia
    private Proprietario proprietario;

    public Propriedade() {}

    public Propriedade(String nome, String telefone, String logradouro, String bairro, String cep,
                       String cidade, String estado, String numero, int idProprietario, int idUsuario) {
        this.nome = nome;
        this.telefone = telefone;
        this.logradouro = logradouro;
        this.bairro = bairro;
        this.cep = cep;
        this.cidade = cidade;
        this.estado = estado;
        this.numero = numero;
        this.idProprietario = idProprietario;
        this.idUsuario = idUsuario;
    }

    public Propriedade(int id, String nome, String telefone, String logradouro, String bairro, String cep, String cidade, String estado,
                       String numero, int idProprietario, int idUsuario) {
        this(nome, telefone, logradouro, bairro, cep, cidade, estado, numero, idProprietario, idUsuario);
        this.id = id;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Proprietario getProprietario() { return proprietario; }

    public void setProprietario(Proprietario proprietario) {
        this.proprietario = proprietario;
    }

    public int getIdProprietario() {
        return idProprietario;
    }

    public void setIdProprietario(int idProprietario) {
        this.idProprietario = idProprietario;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
    @Override
    public boolean equals(Object obj) {

        if( (obj == null) || !(obj instanceof Propriedade))
            return false;

        Propriedade objeto = (Propriedade) obj;

        if( (objeto.getId() == (this.getId())) &&
                (objeto.getNome().equals(this.getNome()))  &&
                (objeto.getTelefone().equals(this.getTelefone())) &&
                (objeto.getLogradouro().equals(this.getLogradouro())) &&
                (objeto.getBairro().equals(this.getBairro()))  &&
                (objeto.getCep().equals(this.getCep()))  &&
                (objeto.getCidade().equals(this.getCidade()))  &&
                (objeto.getEstado().equals(this.getEstado()))  &&
                (objeto.getNumero().equals(this.getNumero()))  &&
                (objeto.getIdProprietario() == (this.getIdProprietario())) &&
                (objeto.getIdUsuario() == (this.getIdUsuario())))
            return true;

        return false;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + String.valueOf(this.getId()).hashCode();
        result = 31 * result + this.getNome().hashCode();
        result = 31 * result + this.getTelefone().hashCode();
        result = 31 * result + this.getLogradouro().hashCode();
        result = 31 * result + this.getBairro().hashCode();
        result = 31 * result + this.getCep().hashCode();
        result = 31 * result + this.getCidade().hashCode();
        result = 31 * result + this.getEstado().hashCode();
        result = 31 * result + this.getNumero().hashCode();
        result = 31 * result + String.valueOf(this.getIdProprietario()).hashCode();
        result = 31 * result + String.valueOf(this.getIdUsuario()).hashCode();

        return result;
    }
}
