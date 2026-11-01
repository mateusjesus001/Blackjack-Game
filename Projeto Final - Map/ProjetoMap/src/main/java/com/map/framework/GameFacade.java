package com.map.framework;

import com.map.framework.card.Baralho;
import com.map.framework.game.Partida;
import com.map.framework.game.PartidaConsoleListener;
import com.map.framework.game.PartidaListener;
import com.map.framework.game.PartidaSimples;
import com.map.framework.game.RegrasJogo;
import com.map.framework.player.Jogador;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Fachada (Facade) pública do mini-framework de jogos de cartas.
 * Oferece uma API simples e centralizada para criar e executar partidas,
 * encapsulando a complexidade das classes internas.</p>
 *
 * <p><strong>API Pública do Framework (requisito #1):</strong></p>
 * <ul>
 *   <li>{@link #criarPartida(List, RegrasJogo)} - cria uma partida com jogadores e regras</li>
 *   <li>{@link #executarPartida(Partida)} - executa a partida do início ao fim</li>
 *   <li>{@link #executarPartida(Partida, boolean)} - executa com ou sem listener de console</li>
 * </ul>
 *
 * <p><strong>Separação entre framework e aplicações cliente (requisito #3):</strong>
 * Pacote {@code com.map.framework} é o framework reutilizável, enquanto
 * {@code com.map.games.*} são as aplicações cliente (jogos concretos).</p>
 */
public final class GameFacade {

    private GameFacade() {
    }

    /**
     * Cria uma nova partida com os jogadores e regras fornecidos.
     *
     * @param jogadores lista de jogadores
     * @param regras    regras do jogo
     * @param <B>       tipo do Baralho
     * @param <R>       tipo das Regras
     * @return a Partida criada (ainda não iniciada)
     */
    public static <B extends Baralho, R extends RegrasJogo<B>> Partida<B, R> criarPartida(
            List<Jogador> jogadores, R regras) {
        return new PartidaSimples<>(new ArrayList<>(jogadores), regras);
    }

    /**
     * Executa uma partida completa do início ao fim, exibindo eventos no console.
     *
     * @param partida partida a ser executada
     * @param <B>     tipo do Baralho
     * @param <R>     tipo das Regras
     * @return o jogador vencedor (ou null)
     */
    public static <B extends Baralho, R extends RegrasJogo<B>> Jogador executarPartida(Partida<B, R> partida) {
        return executarPartida(partida, true);
    }

    /**
     * Executa uma partida completa, opcionalmente adicionando listener de console.
     *
     * @param partida          partida a ser executada
     * @param usarConsoleLog   se true, adiciona um listener que imprime eventos no console
     * @param <B> tipo do Baralho
     * @param <R> tipo das Regras
     * @return o vencedor
     */
    public static <B extends Baralho, R extends RegrasJogo<B>> Jogador executarPartida(
            Partida<B, R> partida, boolean usarConsoleLog) {
        if (usarConsoleLog) {
            PartidaListener console = new PartidaConsoleListener();
            partida.adicionarListener(console);
        }
        return partida.jogarPartida();
    }
}
