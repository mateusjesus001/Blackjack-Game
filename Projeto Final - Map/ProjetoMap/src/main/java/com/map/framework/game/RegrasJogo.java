package com.map.framework.game;

import com.map.framework.card.Baralho;
import com.map.framework.card.Carta;
import com.map.framework.player.AcaoJogador;
import com.map.framework.player.Jogador;

import java.util.List;

/**
 * Interface que define as regras específicas de um jogo de cartas.
 * 
 * <p><strong>Padrão GoF Aplicado: Strategy</strong></p>
 * <p>Esta é a interface <em>Strategy</em> para as regras do jogo.
 * Cada jogo concreto (Blackjack, Super Trunfo, Uno, etc.) implementa esta
 * interface com a sua própria lógica. A {@link Partida} atua como Context,
 * delegando ao RegrasJogo todas as decisões que dependem do jogo específico.</p>
 *
 * <p><strong>Pontos de Extensão:</strong> Qualquer novo jogo é apenas uma nova
 * implementação desta interface, sem a necessidade de alterar o framework.</p>
 *
 * <p><strong>Princípio SOLID: Interface Segregation</strong> - a interface é
 * coesa e define apenas os métodos realmente necessários para um jogo.</p>
 *
 * @param <B> tipo do Baralho usado pelo jogo
 */
public interface RegrasJogo<B extends Baralho> {

    /**
     * Cria o baralho específico do jogo.
     *
     * @return nova instância do baralho
     */
    B criarBaralho();

    /**
     * Define quantas cartas cada jogador recebe no início da partida.
     */
    int cartasIniciaisPorJogador();

    /**
     * Define o número máximo de jogadores permitido.
     */
    int maximoJogadores();

    /**
     * Define o número mínimo de jogadores necessário.
     */
    int minimoJogadores();

    /**
     * Lista as ações possíveis para o jogador da vez.
     *
     * @param jogador jogador da vez
     * @param partida contexto atual da partida
     * @return lista de ações permitidas
     */
    List<AcaoJogador> acoesPossiveis(Jogador jogador, Partida<B, ? extends RegrasJogo<B>> partida);

    /**
     * Executa uma ação escolhida pelo jogador.
     *
     * @param jogador jogador que realiza a ação
     * @param acao    ação a ser realizada
     * @param partida contexto da partida
     */
    void executarAcao(Jogador jogador, AcaoJogador acao, Partida<B, ? extends RegrasJogo<B>> partida);

    /**
     * Valida se uma carta pode ser jogada (opcional - sobrescreva se necessário).
     *
     * @param carta   carta a ser validada
     * @param jogador jogador que quer jogar a carta
     * @param partida contexto
     * @return true se válida, false caso contrário
     */
    default boolean podeJogarCarta(Carta carta, Jogador jogador,
                                   Partida<B, ? extends RegrasJogo<B>> partida) {
        return true;
    }

    /**
     * Verifica se a partida foi finalizada (alcançou condição de término).
     *
     * @param partida contexto
     * @return true se a partida terminou
     */
    boolean partidaFinalizada(Partida<B, ? extends RegrasJogo<B>> partida);

    /**
     * Retorna o jogador vencedor (ou null em caso de empate ou partida em andamento).
     *
     * @param partida contexto
     * @return jogador vencedor
     */
    Jogador getVencedor(Partida<B, ? extends RegrasJogo<B>> partida);

    /**
     * Executa ações após a distribuição inicial de cartas (hook).
     */
    default void aposDistribuicaoInicial(Partida<B, ? extends RegrasJogo<B>> partida) {
        // Hook: subclasses podem sobrescrever
    }

    /**
     * Executa ações antes de cada rodada (hook).
     */
    default void antesDaRodada(Partida<B, ? extends RegrasJogo<B>> partida) {
        // Hook: subclasses podem sobrescrever
    }

    /**
     * Executa ações após cada rodada (hook).
     */
    default void aposRodada(Partida<B, ? extends RegrasJogo<B>> partida) {
        // Hook: subclasses podem sobrescrever
    }
}
