package com.nutricampus.app.exceptions;

public class EstadoNaoEncontradoException extends Exception {
    public EstadoNaoEncontradoException() {
        super("Estado não encontrado para o termo de busca informado");
    }
}
