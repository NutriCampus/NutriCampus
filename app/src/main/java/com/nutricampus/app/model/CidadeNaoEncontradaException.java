package com.nutricampus.app.model;


public class CidadeNaoEncontradaException extends Exception {
    public CidadeNaoEncontradaException() {
        super("Cidade não encontrada para o termo de busca informado");
    }
}
