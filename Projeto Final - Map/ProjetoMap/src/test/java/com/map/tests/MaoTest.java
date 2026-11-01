package com.map.tests;

import com.map.framework.card.CartaTradicional;
import com.map.framework.card.Naipe;
import com.map.framework.exception.MaoCheiaException;
import com.map.framework.player.Mao;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Testes unitários para a classe Mao (mão de cartas).
 * 
 * <p>Cobre: adicionar cartas, limite máximo, remoção, encapsulamento.</p>
 */
public class MaoTest {

    @Test
    public void maoVazia_quandoCriada() {
        Mao mao = new Mao();
        assertTrue(mao.isVazia());
        assertEquals(0, mao.tamanho());
    }

    @Test
    public void adicionarCarta_aumentaTamanho() {
        Mao mao = new Mao();
        mao.adicionarCarta(new CartaTradicional(10, Naipe.COPAS));
        assertEquals(1, mao.tamanho());
        assertFalse(mao.isVazia());
    }

    @Test(expected = MaoCheiaException.class)
    public void adicionarAlemDoLimite_lancaExcecao() {
        Mao mao = new Mao(2);
        mao.adicionarCarta(new CartaTradicional(1, Naipe.ESPADAS));
        mao.adicionarCarta(new CartaTradicional(2, Naipe.OUROS));
        // Terceira carta deve lançar exceção
        mao.adicionarCarta(new CartaTradicional(3, Naipe.PAUS));
    }

    @Test
    public void removerCarta_diminuiTamanho() {
        Mao mao = new Mao();
        CartaTradicional c = new CartaTradicional(5, Naipe.COPAS);
        mao.adicionarCarta(c);
        assertTrue(mao.removerCarta(c));
        assertTrue(mao.isVazia());
    }

    @Test
    public void getCartas_naoPermiteModificacaoDireta() {
        Mao mao = new Mao();
        mao.adicionarCarta(new CartaTradicional(1, Naipe.COPAS));
        try {
            mao.getCartas().clear();
            fail("Deveria ter lançado UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            // Esperado - garante encapsulamento da coleção (requisito #7)
        }
        assertEquals(1, mao.tamanho());
    }
}
