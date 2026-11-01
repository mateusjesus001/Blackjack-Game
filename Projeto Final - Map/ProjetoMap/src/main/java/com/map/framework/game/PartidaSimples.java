package com.map.framework.game;

import com.map.framework.card.Baralho;
import com.map.framework.player.Jogador;

import java.util.List;

/**
 * Implementação padrão de {@link Partida} que não adiciona comportamentos
 * extras. Serve como a implementação concreta usada pela maioria dos jogos.
 * 
 * <p>Jogos com fluxos muito específicos podem estender {@link Partida}
 * diretamente e sobrescrever hooks ou passos do Template Method.</p>
 *
 * @param <B> tipo do Baralho
 * @param <R> tipo das Regras
 */
public class PartidaSimples<B extends Baralho, R extends RegrasJogo<B>> extends Partida<B, R> {

    public PartidaSimples(List<Jogador> jogadores, R regras) {
        super(jogadores, regras);
    }
}
