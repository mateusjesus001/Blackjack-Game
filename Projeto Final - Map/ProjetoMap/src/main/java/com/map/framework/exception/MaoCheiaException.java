package com.map.framework.exception;

/**
 * Exceção lançada quando tenta-se adicionar uma carta a uma mão que já atingiu o limite máximo.
 */
public class MaoCheiaException extends RuntimeException {

    public MaoCheiaException(int limite) {
        super("A mão já atingiu o limite máximo de " + limite + " cartas.");
    }

    public MaoCheiaException(String mensagem) {
        super(mensagem);
    }
}
