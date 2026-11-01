package com.map.framework.card;

/**
 * Enumeração que representa os quatro naipes tradicionais de baralho.
 * Utilizado por jogos que empregam o baralho francês.
 */
public enum Naipe {

    COPAS("Copas", "♥"),
    ESPADAS("Espadas", "♠"),
    OUROS("Ouros", "♦"),
    PAUS("Paus", "♣");

    private final String nome;
    private final String simbolo;

    Naipe(String nome, String simbolo) {
        this.nome = nome;
        this.simbolo = simbolo;
    }

    public String getNome() {
        return nome;
    }

    public String getSimbolo() {
        return simbolo;
    }

    @Override
    public String toString() {
        return simbolo;
    }
}
