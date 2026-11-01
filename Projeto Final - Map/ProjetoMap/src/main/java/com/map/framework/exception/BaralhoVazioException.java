package com.map.framework.exception;

/**
 * Exceção lançada quando tenta-se comprar uma carta de um baralho vazio.
 */
public class BaralhoVazioException extends RuntimeException {

    public BaralhoVazioException() {
        super("O baralho está vazio. Não é possível comprar mais cartas.");
    }

    public BaralhoVazioException(String mensagem) {
        super(mensagem);
    }
}
