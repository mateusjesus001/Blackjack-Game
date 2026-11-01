package com.map.framework.exception;

/**
 * Exceção lançada quando ocorre uma operação inválida no contexto de um jogo.
 */
public class JogoInvalidoException extends RuntimeException {

    public JogoInvalidoException(String mensagem) {
        super(mensagem);
    }

    public JogoInvalidoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
