package com.map.framework.exception;

/**
 * Exceção lançada quando uma carta não é encontrada em uma coleção.
 */
public class CartaNaoEncontradaException extends RuntimeException {

    public CartaNaoEncontradaException() {
        super("Carta não encontrada na coleção.");
    }

    public CartaNaoEncontradaException(String mensagem) {
        super(mensagem);
    }
}
