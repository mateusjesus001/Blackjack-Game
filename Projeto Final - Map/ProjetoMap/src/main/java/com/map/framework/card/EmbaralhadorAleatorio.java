package com.map.framework.card;

import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Implementação concreta de {@link EmbaralhadorStrategy} que utiliza
 * o algoritmo de embaralhamento aleatório padrão (Fisher-Yates) via
 * {@link Collections#shuffle}.
 * 
 * <p><strong>Padrão GoF: Strategy (ConcreteStrategy)</strong></p>
 */
public class EmbaralhadorAleatorio implements EmbaralhadorStrategy {

    private final Random random;

    public EmbaralhadorAleatorio() {
        this.random = new Random();
    }

    /**
     * Construtor que permite injetar uma semente (seed) para testes reprodutíveis.
     *
     * @param seed semente para o gerador de números aleatórios
     */
    public EmbaralhadorAleatorio(long seed) {
        this.random = new Random(seed);
    }

    @Override
    public void embaralhar(List<Carta> cartas) {
        Collections.shuffle(cartas, random);
    }
}
