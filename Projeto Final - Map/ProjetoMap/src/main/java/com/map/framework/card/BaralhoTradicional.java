package com.map.framework.card;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementação concreta de {@link Baralho} que representa o baralho
 * tradicional francês com 52 cartas (13 valores x 4 naipes).
 * 
 * <p><strong>Padrão GoF: Factory Method (ConcreteCreator)</strong></p>
 * <p>Implementa o Factory Method {@link #criarCartas()} para produzir
 * as 52 cartas tradicionais.</p>
 */
public class BaralhoTradicional extends Baralho {

    private final int quantidadeDeBaralhos;

    /**
     * Cria um baralho tradicional com 1 baralho (52 cartas).
     */
    public BaralhoTradicional() {
        this(1);
    }

    /**
     * Cria um baralho tradicional com múltiplos baralhos.
     * Ex: Blackjack costuma usar 6 baralhos (312 cartas).
     *
     * @param quantidadeDeBaralhos número de baralhos de 52 cartas a combinar
     */
    public BaralhoTradicional(int quantidadeDeBaralhos) {
        super();
        if (quantidadeDeBaralhos < 1) {
            throw new IllegalArgumentException("Quantidade de baralhos deve ser >= 1.");
        }
        this.quantidadeDeBaralhos = quantidadeDeBaralhos;
        inicializar();
    }

    @Override
    protected List<Carta> criarCartas() {
        List<Carta> lista = new ArrayList<>();
        for (int b = 0; b < quantidadeDeBaralhos; b++) {
            for (Naipe naipe : Naipe.values()) {
                for (int valor = 1; valor <= 13; valor++) {
                    lista.add(new CartaTradicional(valor, naipe));
                }
            }
        }
        return lista;
    }

    public int getQuantidadeDeBaralhos() {
        return quantidadeDeBaralhos;
    }
}
