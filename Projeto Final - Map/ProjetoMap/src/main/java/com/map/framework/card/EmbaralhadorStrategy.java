package com.map.framework.card;

import java.util.List;

/**
 * Interface que define a estratégia de embaralhamento de um baralho.
 * 
 * <p><strong>Padrão GoF Aplicado: Strategy</strong></p>
 * <p>Este é o papel <em>Strategy</em> no padrão. Permite que diferentes
 * algoritmos de embaralhamento sejam intercambiáveis em tempo de execução.
 * Cada jogo pode escolher a estratégia mais adequada (aleatório, perfeito,
 * sem embaralhar, etc.) sem alterar a classe {@link Baralho}.</p>
 *
 * <p><strong>Princípio SOLID Aplicado:</strong> Open/Closed - novas estratégias
 * de embaralhamento podem ser adicionadas sem modificar as classes existentes.</p>
 */
public interface EmbaralhadorStrategy {

    /**
     * Embaralha a lista de cartas fornecida.
     *
     * @param cartas lista de cartas a ser embaralhada
     */
    void embaralhar(List<Carta> cartas);
}
