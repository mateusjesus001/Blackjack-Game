package com.map.tests;

import com.map.games.supertrunfo.AtributoSuperTrunfo;
import com.map.games.supertrunfo.CartaSuperTrunfo;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Testes para cartas e atributos do Super Trunfo.
 */
public class SuperTrunfoCartaTest {

    @Test
    public void atributoAceleracao_menorVence() {
        // Aceleração: menor tempo = melhor
        AtributoSuperTrunfo atr = AtributoSuperTrunfo.ACELERACAO;
        assertFalse(atr.isMaiorEhMelhor());
        // Compara 2.1s vs 3.0s → 2.1 deve vencer (retorno positivo)
        assertTrue(atr.comparar(2.1, 3.0) > 0);
        assertTrue(atr.comparar(5.0, 2.0) < 0);
    }

    @Test
    public void atributoPotencia_maiorVence() {
        AtributoSuperTrunfo atr = AtributoSuperTrunfo.POTENCIA;
        assertTrue(atr.isMaiorEhMelhor());
        assertTrue(atr.comparar(800, 500) > 0);
        assertTrue(atr.comparar(100, 300) < 0);
    }

    @Test
    public void cartaSuperTrunfo_marcaDefinida() {
        CartaSuperTrunfo c = new CartaSuperTrunfo(
                "Chiron", "Bugatti",
                1600, 490, 2.4, 20000, 8000, true);
        assertTrue(c.isEhSuperTrunfo());
        assertEquals(1600, c.getAtributo(AtributoSuperTrunfo.POTENCIA), 0.001);
        assertEquals(490, c.getAtributo(AtributoSuperTrunfo.VELOCIDADE_MAXIMA), 0.001);
        assertEquals(2.4, c.getAtributo(AtributoSuperTrunfo.ACELERACAO), 0.001);
        assertTrue(c.exibir().contains("SUPER TRUNFO"));
    }
}
