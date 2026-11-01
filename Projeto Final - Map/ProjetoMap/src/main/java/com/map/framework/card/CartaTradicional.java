package com.map.framework.card;

/**
 * Representa uma carta tradicional de baralho francês, com valor e naipe.
 * Utilizada como base para jogos como Blackjack, Truco, Poker, etc.
 * 
 * <p>Esta é uma extensão concreta de {@link Carta} que adiciona os
 * atributos valor e naipe típicos de baralhos tradicionais.</p>
 */
public class CartaTradicional extends Carta {

    private final int valor;
    private final Naipe naipe;

    /**
     * Constrói uma carta tradicional.
     *
     * @param valor valor numérico da carta (ex: 1 para Ás, 13 para Rei)
     * @param naipe naipe da carta
     */
    public CartaTradicional(int valor, Naipe naipe) {
        super(valor + " de " + naipe.getNome());
        this.valor = valor;
        this.naipe = naipe;
    }

    public int getValor() {
        return valor;
    }

    public Naipe getNaipe() {
        return naipe;
    }

    /**
     * Retorna o nome do valor da carta (ex: "Ás", "Valete", "Dama", "Rei").
     *
     * @return nome textual do valor
     */
    public String getNomeValor() {
        return switch (valor) {
            case 1 -> "Ás";
            case 11 -> "Valete";
            case 12 -> "Dama";
            case 13 -> "Rei";
            default -> String.valueOf(valor);
        };
    }

    @Override
    public String exibir() {
        return "[" + getNomeValor() + " " + naipe.getSimbolo() + "]";
    }
}
