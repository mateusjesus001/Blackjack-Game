package com.map.games.supertrunfo;

import com.map.framework.card.Carta;

import java.util.EnumMap;
import java.util.Map;

/**
 * Carta específica para o jogo Super Trunfo com tema de carros.
 * 
 * <p>Extende a classe abstrata {@link Carta} do framework e adiciona
 * múltiplos atributos numéricos para comparação.
 * 
 * <p>Demonstra a capacidade do framework de trabalhar com tipos de carta
 * completamente diferentes do baralho tradicional (extensibilidade).</p>
 */
public class CartaSuperTrunfo extends Carta {

    private final String modelo;
    private final String marca;
    private final Map<AtributoSuperTrunfo, Double> atributos;
    private final boolean ehSuperTrunfo;

    public CartaSuperTrunfo(String modelo, String marca,
                            double potencia, double velocidadeMaxima,
                            double aceleracao, double preco,
                            double cilindradas, boolean ehSuperTrunfo) {
        super(marca + " " + modelo);
        this.modelo = modelo;
        this.marca = marca;
        this.ehSuperTrunfo = ehSuperTrunfo;
        this.atributos = new EnumMap<>(AtributoSuperTrunfo.class);
        atributos.put(AtributoSuperTrunfo.POTENCIA, potencia);
        atributos.put(AtributoSuperTrunfo.VELOCIDADE_MAXIMA, velocidadeMaxima);
        atributos.put(AtributoSuperTrunfo.ACELERACAO, aceleracao);
        atributos.put(AtributoSuperTrunfo.PRECO, preco);
        atributos.put(AtributoSuperTrunfo.CILINDRADAS, cilindradas);
    }

    public double getAtributo(AtributoSuperTrunfo a) {
        return atributos.getOrDefault(a, 0.0);
    }

    public String getModelo() {
        return modelo;
    }

    public String getMarca() {
        return marca;
    }

    public boolean isEhSuperTrunfo() {
        return ehSuperTrunfo;
    }

    @Override
    public String exibir() {
        StringBuilder sb = new StringBuilder();
        sb.append("🏎️ ").append(marca).append(" ").append(modelo);
        if (ehSuperTrunfo) sb.append(" ⭐SUPER TRUNFO⭐");
        sb.append("\n");
        for (AtributoSuperTrunfo atr : AtributoSuperTrunfo.values()) {
            sb.append("   ").append(atr.getNome()).append(": ").append(getAtributo(atr)).append("\n");
        }
        return sb.toString();
    }
}
