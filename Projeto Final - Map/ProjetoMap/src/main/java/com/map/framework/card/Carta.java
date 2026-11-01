package com.map.framework.card;

/**
 * Classe abstrata que representa uma carta genérica em um jogo de cartas.
 * Cada jogo concreto deve estender esta classe para adicionar atributos e
 * comportamentos específicos (ex: valor, naipe, atributos de Super Trunfo).
 * 
 * <p>Esta classe segue o princípio Open/Closed (SOLID), sendo aberta para
 * extensão (novos tipos de carta) e fechada para modificação.</p>
 */
public abstract class Carta {

    private String identificador;
    private boolean viradaParaCima;

    /**
     * Construtor padrão de uma carta.
     *
     * @param identificador identificador único da carta (ex: "As de Copas")
     */
    public Carta(String identificador) {
        this.identificador = identificador;
        this.viradaParaCima = false;
    }

    /**
     * Retorna o identificador da carta.
     *
     * @return identificador da carta
     */
    public String getIdentificador() {
        return identificador;
    }

    /**
     * Verifica se a carta está virada para cima (visível).
     *
     * @return true se a carta estiver virada para cima, false caso contrário
     */
    public boolean isViradaParaCima() {
        return viradaParaCima;
    }

    /**
     * Vira a carta para cima.
     */
    public void virarParaCima() {
        this.viradaParaCima = true;
    }

    /**
     * Vira a carta para baixo (esconde).
     */
    public void virarParaBaixo() {
        this.viradaParaCima = false;
    }

    /**
     * Método abstrato que retorna uma representação textual da carta.
     * Cada subclasse deve implementar sua própria forma de exibição.
     *
     * @return representação textual da carta
     */
    public abstract String exibir();

    @Override
    public String toString() {
        if (viradaParaCima) {
            return exibir();
        }
        return "[Carta Oculta]";
    }
}
