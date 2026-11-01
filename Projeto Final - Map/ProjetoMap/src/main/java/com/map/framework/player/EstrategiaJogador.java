package com.map.framework.player;

import com.map.framework.game.Partida;
import com.map.framework.card.Carta;

import java.util.List;
import java.util.Optional;

/**
 * Interface que define a estratégia de tomada de decisão de um jogador.
 * 
 * <p><strong>Padrão GoF Aplicado: Strategy</strong></p>
 * <p>Representa o papel <em>Strategy</em> para as decisões do jogador.
 * Permite intercambiar diferentes estratégias: jogadores humanos (leem entrada),
 * IAs aleatórias, IAs avançadas, etc. Tudo sem modificar a classe {@link Jogador}.</p>
 *
 * <p><strong>Princípio SOLID: Dependency Inversion</strong> - Jogador depende
 * de uma abstração (EstrategiaJogador), não de implementações concretas.</p>
 */
public interface EstrategiaJogador {

    /**
     * Solicita ao jogador uma ação a ser tomada no turno.
     *
     * @param jogador   jogador que deve tomar a decisão
     * @param partida   contexto da partida em andamento (estado do jogo)
     * @param acoesPossiveis lista de ações válidas que o jogador pode realizar
     * @return a ação escolhida
     */
    AcaoJogador decidirAcao(Jogador jogador, Partida<?, ?> partida, List<AcaoJogador> acoesPossiveis);

    /**
     * Solicita ao jogador que escolha uma carta entre as opções.
     *
     * @param jogador jogador que deve escolher
     * @param opcoes  cartas disponíveis para escolha
     * @param partida contexto da partida
     * @return a carta escolhida (Optional.empty() se pular)
     */
    Optional<Carta> escolherCarta(Jogador jogador, List<Carta> opcoes, Partida<?, ?> partida);
}
