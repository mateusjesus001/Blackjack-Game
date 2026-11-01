package com.map.framework.player;

import com.map.framework.card.Carta;
import com.map.framework.game.Partida;

import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * Estratégia de jogador autônomo (IA) que escolhe ações e cartas aleatoriamente.
 * Útil para testes, jogos single-player contra o computador, ou simuladores.
 * 
 * <p><strong>Padrão GoF: Strategy (ConcreteStrategy)</strong></p>
 */
public class EstrategiaIAAleatoria implements EstrategiaJogador {

    private final Random random;

    public EstrategiaIAAleatoria() {
        this.random = new Random();
    }

    public EstrategiaIAAleatoria(long seed) {
        this.random = new Random(seed);
    }

    @Override
    public AcaoJogador decidirAcao(Jogador jogador, Partida<?, ?> partida, List<AcaoJogador> acoesPossiveis) {
        if (acoesPossiveis == null || acoesPossiveis.isEmpty()) {
            throw new IllegalStateException("Nenhuma ação possível para a IA.");
        }
        pausaCurta();
        int idx = random.nextInt(acoesPossiveis.size());
        AcaoJogador escolhida = acoesPossiveis.get(idx);
        System.out.println(jogador.getNome() + " escolheu: " + escolhida);
        return escolhida;
    }

    @Override
    public Optional<Carta> escolherCarta(Jogador jogador, List<Carta> opcoes, Partida<?, ?> partida) {
        if (opcoes == null || opcoes.isEmpty()) {
            return Optional.empty();
        }
        pausaCurta();
        int idx = random.nextInt(opcoes.size() + 1); // +1 para chance de pular
        if (idx >= opcoes.size()) {
            System.out.println(jogador.getNome() + " pulou a escolha.");
            return Optional.empty();
        }
        Carta escolhida = opcoes.get(idx);
        System.out.println(jogador.getNome() + " escolheu: " + escolhida);
        return Optional.of(escolhida);
    }

    private void pausaCurta() {
        try {
            Thread.sleep(400);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
