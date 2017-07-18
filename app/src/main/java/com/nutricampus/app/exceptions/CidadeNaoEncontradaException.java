package com.nutricampus.app.exceptions;


public class CidadeNaoEncontradaException extends Exception {
    public CidadeNaoEncontradaException() {
        super("Cidade não encontrada para o termo de busca informado");
    }
}
