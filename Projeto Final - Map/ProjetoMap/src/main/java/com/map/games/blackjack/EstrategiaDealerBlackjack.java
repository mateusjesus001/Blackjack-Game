package com.map.games.blackjack;

import com.map.framework.card.Carta;
import com.map.framework.game.Partida;
import com.map.framework.player.AcaoJogador;
import com.map.framework.player.EstrategiaJogador;
import com.map.framework.player.Jogador;

import java.util.List;
import java.util.Optional;

/**
 * Estratégia do Dealer no Blackjack, seguindo a regra clássica:
 * sempre comprar enquanto a mão for menor que 17 e parar em 17 ou mais.
 *
 * <p><strong>Padrão GoF: Strategy (ConcreteStrategy)</strong></p>
 *
 * <p>Esta classe é uma aplicação cliente (jogo concreto) que implementa a
 * interface {@link EstrategiaJogador} do framework, sem alterar a estrutura
 * do framework. Substitui a IA totalmente aleatória por uma estratégia mais
 * realista e tática.</p>
 *
 * <p>Regras do dealer nesta implementação:</p>
 * <ul>
 *   <li>Se a mão vale <strong>16 ou menos</strong> → HIT (comprar carta)</li>
 *   <li>Se a mão vale <strong>17 ou mais</strong> → STAND (parar)</li>
 * </ul>
 */
public class EstrategiaDealerBlackjack implements EstrategiaJogador {

    /** Limite clássico: dealer para a partir de 17. */
    public static final int LIMITE_DEALER = 17;

    @Override
    public AcaoJogador decidirAcao(Jogador jogador, Partida<?, ?> partida, List<AcaoJogador> acoesPossiveis) {
        if (acoesPossiveis == null || acoesPossiveis.isEmpty()) {
            throw new IllegalStateException("Nenhuma ação possível para o dealer.");
        }

        pausaCurta();

        int valor = BlackjackUtils.calcularValorMao(jogador.getMao());

        if (valor >= LIMITE_DEALER) {
            System.out.println(jogador.getNome() + " parou com " + valor + " pontos (dealer).");
            return encontrarAcao(acoesPossiveis, BlackjackRegras.ACAO_STAND);
        }

        System.out.println(jogador.getNome() + " comprou uma carta (mão: " + valor + " pontos, dealer).");
        return encontrarAcao(acoesPossiveis, BlackjackRegras.ACAO_HIT);
    }

    @Override
    public Optional<Carta> escolherCarta(Jogador jogador, List<Carta> opcoes, Partida<?, ?> partida) {
        if (opcoes == null || opcoes.isEmpty()) {
            return Optional.empty();
        }
        // Dealer não escolhe cartas em Blackjack (apenas compra do topo do baralho).
        return Optional.empty();
    }

    private AcaoJogador encontrarAcao(List<AcaoJogador> acoesPossiveis, AcaoJogador desejada) {
        return acoesPossiveis.stream()
                .filter(a -> a.codigo().equals(desejada.codigo()))
                .findFirst()
                .orElse(acoesPossiveis.get(0));
    }

    private void pausaCurta() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}