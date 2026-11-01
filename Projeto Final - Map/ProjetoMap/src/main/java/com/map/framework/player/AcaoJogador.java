package com.map.framework.player;

/**
 * Representa uma ação que um jogador pode realizar durante seu turno.
 * 
 * <p>Exemplos de ações: "comprar carta", "descartar carta", "passar turno",
 * "pedir truco", etc. Cada jogo concreto define suas ações específicas.</p>
 *
 * @param codigo    código único da ação (ex: "COMPRAR", "PASSAR")
 * @param descricao descrição amigável para exibição
 */
public record AcaoJogador(String codigo, String descricao) {

    public AcaoJogador {
        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException("Código da ação não pode ser vazio.");
        }
        if (descricao == null || descricao.isBlank()) {
            throw new IllegalArgumentException("Descrição da ação não pode ser vazia.");
        }
    }

    @Override
    public String toString() {
        return codigo + " - " + descricao;
    }
}
