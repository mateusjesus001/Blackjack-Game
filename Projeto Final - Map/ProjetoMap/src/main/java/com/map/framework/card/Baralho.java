package com.map.framework.card;

import com.map.framework.exception.BaralhoVazioException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Classe abstrata que representa um baralho genérico de cartas.
 * 
 * <p><strong>Padrão GoF Aplicado: Factory Method</strong></p>
 * <p>O método abstrato {@link #criarCartas()} é o <em>Factory Method</em> que
 * delega às subclasses a criação das cartas específicas de cada jogo.
 * Cada jogo concreto (Blackjack, Super Trunfo, etc.) implementa este método
 * para produzir seu conjunto próprio de cartas.</p>
 *
 * <p><strong>Princípios GRASP Aplicados:</strong></p>
 * <ul>
 *   <li><em>Creator</em>: o Baralho é o criador natural das suas cartas.</li>
 *   <li><em>Information Expert</em>: o Baralho conhece e gerencia suas cartas.</li>
 *   <li><em>Low Coupling / High Cohesion</em>: o Baralho só cuida de assuntos
 *       relacionados às cartas que contém (comprar, embaralhar, etc.).</li>
 * </ul>
 */
public abstract class Baralho {

    protected final List<Carta> cartas;
    private EmbaralhadorStrategy embaralhador;

    /**
     * Construtor do baralho. Cria as cartas invocando o Factory Method
     * {@link #criarCartas()} e define a estratégia de embaralhamento padrão
     * como {@link EmbaralhadorAleatorio}.
     */
    protected Baralho() {
        this.cartas = new ArrayList<>();
        this.embaralhador = new EmbaralhadorAleatorio();
    }

    /**
     * Inicializa o baralho criando as cartas via Factory Method.
     */
    protected final void inicializar() {
        cartas.clear();
        List<Carta> criadas = criarCartas();
        if (criadas != null) {
            cartas.addAll(criadas);
        }
    }

    /**
     * <strong>Factory Method</strong> - Método abstrato responsável por criar
     * as cartas do baralho. Cada subclasse implementa este método de acordo
     * com as especificações do seu jogo.
     *
     * @return lista com todas as cartas do baralho
     */
    protected abstract List<Carta> criarCartas();

    /**
     * Embaralha as cartas utilizando a estratégia definida.
     * 
     * <p><strong>Padrão Strategy:</strong> o algoritmo de embaralhamento é
     * delegado à interface {@link EmbaralhadorStrategy}.</p>
     */
    public void embaralhar() {
        embaralhador.embaralhar(cartas);
    }

    /**
     * Altera a estratégia de embaralhamento em tempo de execução.
     *
     * @param embaralhador nova estratégia de embaralhamento
     */
    public void setEmbaralhador(EmbaralhadorStrategy embaralhador) {
        if (embaralhador == null) {
            throw new IllegalArgumentException("Embaralhador não pode ser nulo.");
        }
        this.embaralhador = embaralhador;
    }

    /**
     * Compra (retira) a carta do topo do baralho.
     *
     * @return carta do topo
     * @throws BaralhoVazioException se o baralho estiver vazio
     */
    public Carta comprarCarta() {
        if (cartas.isEmpty()) {
            throw new BaralhoVazioException();
        }
        try {
            return cartas.remove(0);
        } catch (NoSuchElementException e) {
            throw new BaralhoVazioException();
        }
    }

    /**
     * Verifica se o baralho está vazio.
     *
     * @return true se vazio, false caso contrário
     */
    public boolean isVazio() {
        return cartas.isEmpty();
    }

    /**
     * Retorna a quantidade de cartas restantes no baralho.
     *
     * @return quantidade de cartas
     */
    public int tamanho() {
        return cartas.size();
    }

    /**
     * Retorna uma visão não modificável das cartas do baralho.
     * Respeita o encapsulamento das coleções internas (requisito #7).
     *
     * @return lista não modificável de cartas
     */
    public List<Carta> getCartasNaoModificaveis() {
        return Collections.unmodifiableList(cartas);
    }

    /**
     * Recria o baralho do zero (reinicia as cartas).
     */
    public void resetar() {
        cartas.clear();
        inicializar();
    }
}
