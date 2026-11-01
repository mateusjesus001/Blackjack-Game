package com.map.framework.card;

import java.util.List;

/**
 * Implementação de {@link EmbaralhadorStrategy} que não embaralha as cartas.
 * Útil para testes, jogos que exigem ordem específica ou debug.
 * 
 * <p><strong>Padrão GoF: Strategy (ConcreteStrategy)</strong></p>
 */
public class SemEmbaralhamento implements EmbaralhadorStrategy {

    @Override
    public void embaralhar(List<Carta> cartas) {
        // Não faz nada - mantém a ordem original
    }
}
