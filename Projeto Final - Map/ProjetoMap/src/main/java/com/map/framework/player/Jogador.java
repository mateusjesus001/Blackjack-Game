package com.map.framework.player;

import com.map.framework.card.Carta;
import com.map.framework.game.Partida;

import java.util.List;
import java.util.Optional;

/**
 * Representa um jogador genérico em um jogo de cartas.
 * 
 * <p>Um Jogador possui: nome, pontuação, mão de cartas e uma estratégia de
 * tomada de decisão ({@link EstrategiaJogador}) que define como ele joga.</p>
 *
 * <p><strong>Padrão GoF: Strategy (Context)</strong></p>
 * <p>Jogador é o <em>Context</em> que utiliza a interface {@link EstrategiaJogador}
 * para delegar suas decisões. A estratégia pode ser trocada em runtime.</p>
 *
 * <p><strong>Princípio SOLID: Single Responsibility</strong> - Jogador concentra-se
 * apenas em representar o participante (estado), delegando a lógica de decisão
 * à estratégia e a lógica de jogo às regras.</p>
 */
public class Jogador {

    private final String nome;
    private final Mao mao;
    private int pontuacao;
    private EstrategiaJogador estrategia;

    /**
     * Cria um jogador com nome e estratégia definidos.
     *
     * @param nome      nome do jogador
     * @param estrategia estratégia de tomada de decisão
     */
    public Jogador(String nome, EstrategiaJogador estrategia) {
        this(nome, estrategia, Integer.MAX_VALUE);
    }

    /**
     * Cria um jogador com nome, estratégia e limite máximo de cartas na mão.
     *
     * @param nome         nome do jogador
     * @param estrategia    estratégia de tomada de decisão
     * @param limiteCartas limite máximo de cartas na mão
     */
    public Jogador(String nome, EstrategiaJogador estrategia, int limiteCartas) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome do jogador não pode ser vazio.");
        }
        if (estrategia == null) {
            throw new IllegalArgumentException("Estratégia não pode ser nula.");
        }
        this.nome = nome;
        this.estrategia = estrategia;
        this.mao = new Mao(limiteCartas);
        this.pontuacao = 0;
    }

    public String getNome() {
        return nome;
    }

    public Mao getMao() {
        return mao;
    }

    public int getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(int pontuacao) {
        this.pontuacao = pontuacao;
    }

    public void adicionarPontos(int pontos) {
        this.pontuacao += pontos;
    }

    public void subtrairPontos(int pontos) {
        this.pontuacao -= pontos;
    }

    public EstrategiaJogador getEstrategia() {
        return estrategia;
    }

    /**
     * Altera a estratégia do jogador em tempo de execução.
     *
     * @param estrategia nova estratégia
     */
    public void setEstrategia(EstrategiaJogador estrategia) {
        if (estrategia == null) {
            throw new IllegalArgumentException("Estratégia não pode ser nula.");
        }
        this.estrategia = estrategia;
    }

    /**
     * Delegar decisão de ação para a estratégia.
     */
    public AcaoJogador decidirAcao(Partida<?, ?> partida, List<AcaoJogador> acoesPossiveis) {
        return estrategia.decidirAcao(this, partida, acoesPossiveis);
    }

    /**
     * Delegar escolha de carta para a estratégia.
     */
    public Optional<Carta> escolherCarta(List<Carta> opcoes, Partida<?, ?> partida) {
        return estrategia.escolherCarta(this, opcoes, partida);
    }

    @Override
    public String toString() {
        return "Jogador[" + nome + " | Pontos: " + pontuacao + " | Cartas: " + mao.tamanho() + "]";
    }
}
