package com.map.framework.player;

import com.map.framework.card.Carta;
import com.map.framework.exception.CartaNaoEncontradaException;
import com.map.framework.exception.MaoCheiaException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Representa a mão de cartas de um jogador.
 * 
 * <p><strong>Princípios GRASP Aplicados:</strong></p>
 * <ul>
 *   <li><em>Information Expert / High Cohesion</em>: a Mao é especialista em
 *       gerenciar as cartas que o jogador possui em mãos.</li>
 *   <li><em>Encapsulamento</em>: a coleção interna é protegida e só pode ser
 *       acessada através de métodos controlados (requisito #7).</li>
 * </ul>
 */
public class Mao {

    private final List<Carta> cartas;
    private final int limiteMaximo;

    /**
     * Cria uma mão sem limite de cartas.
     */
    public Mao() {
        this(Integer.MAX_VALUE);
    }

    /**
     * Cria uma mão com um limite máximo de cartas.
     *
     * @param limiteMaximo quantidade máxima de cartas permitida
     */
    public Mao(int limiteMaximo) {
        if (limiteMaximo < 1) {
            throw new IllegalArgumentException("Limite máximo deve ser >= 1.");
        }
        this.cartas = new ArrayList<>();
        this.limiteMaximo = limiteMaximo;
    }

    /**
     * Adiciona uma carta à mão.
     *
     * @param carta carta a ser adicionada
     * @throws MaoCheiaException se a mão já estiver cheia
     */
    public void adicionarCarta(Carta carta) {
        if (carta == null) {
            throw new IllegalArgumentException("Carta não pode ser nula.");
        }
        if (cartas.size() >= limiteMaximo) {
            throw new MaoCheiaException(limiteMaximo);
        }
        cartas.add(carta);
    }

    /**
     * Remove uma carta específica da mão.
     *
     * @param carta carta a ser removida
     * @return true se a carta foi removida, false caso contrário
     * @throws CartaNaoEncontradaException se a carta não estiver na mão
     */
    public boolean removerCarta(Carta carta) {
        if (carta == null) {
            throw new IllegalArgumentException("Carta não pode ser nula.");
        }
        boolean removida = cartas.remove(carta);
        if (!removida) {
            throw new CartaNaoEncontradaException("A carta não está na mão: " + carta);
        }
        return true;
    }

    /**
     * Remove a carta de um índice específico.
     *
     * @param indice índice da carta (0-based)
     * @return a carta removida
     */
    public Carta removerCarta(int indice) {
        if (indice < 0 || indice >= cartas.size()) {
            throw new IndexOutOfBoundsException("Índice inválido: " + indice);
        }
        return cartas.remove(indice);
    }

    /**
     * Retorna a quantidade de cartas na mão.
     */
    public int tamanho() {
        return cartas.size();
    }

    /**
     * Verifica se a mão está vazia.
     */
    public boolean isVazia() {
        return cartas.isEmpty();
    }

    /**
     * Verifica se a mão está cheia.
     */
    public boolean isCheia() {
        return cartas.size() >= limiteMaximo;
    }

    public int getLimiteMaximo() {
        return limiteMaximo;
    }

    /**
     * Retorna uma visão não modificável das cartas da mão.
     * Encapsulamento da coleção interna.
     *
     * @return lista imutável de cartas
     */
    public List<Carta> getCartas() {
        return Collections.unmodifiableList(cartas);
    }

    /**
     * Vira todas as cartas da mão para cima.
     */
    public void virarTodasParaCima() {
        cartas.forEach(Carta::virarParaCima);
    }

    /**
     * Vira todas as cartas da mão para baixo.
     */
    public void virarTodasParaBaixo() {
        cartas.forEach(Carta::virarParaBaixo);
    }

    /**
     * Remove todas as cartas da mão.
     */
    public void limpar() {
        cartas.clear();
    }

    /**
     * Exibe todas as cartas da mão (viradas para cima) em formato texto.
     */
    public String exibir() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < cartas.size(); i++) {
            sb.append(i + 1).append(". ").append(cartas.get(i)).append("\n");
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return "Mao [" + cartas.size() + " carta(s), limite=" +
                (limiteMaximo == Integer.MAX_VALUE ? "sem limite" : limiteMaximo) + "]";
    }
}
