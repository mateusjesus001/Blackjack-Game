package com.map.tests;

import com.map.framework.card.CartaTradicional;
import com.map.framework.card.Naipe;
import com.map.framework.player.Mao;
import com.map.games.blackjack.BlackjackUtils;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Testes unitários para a lógica de pontuação do Blackjack.
 */
public class BlackjackUtilsTest {

    @Test
    public void asEDez_blackjackNatural() {
        Mao mao = new Mao();
        mao.adicionarCarta(new CartaTradicional(1, Naipe.COPAS));   // Ás
        mao.adicionarCarta(new CartaTradicional(10, Naipe.ESPADAS)); // 10
        assertTrue(BlackjackUtils.isBlackjack(mao));
        assertEquals(21, BlackjackUtils.calcularValorMao(mao));
    }

    @Test
    public void asEValete_blackjackNatural() {
        Mao mao = new Mao();
        mao.adicionarCarta(new CartaTradicional(1, Naipe.COPAS));  // Ás = 11
        mao.adicionarCarta(new CartaTradicional(11, Naipe.OUROS)); // J = 10
        assertEquals(21, BlackjackUtils.calcularValorMao(mao));
    }

    @Test
    public void tresCartas_21NaoEBlackjack() {
        Mao mao = new Mao();
        mao.adicionarCarta(new CartaTradicional(5, Naipe.COPAS));
        mao.adicionarCarta(new CartaTradicional(7, Naipe.ESPADAS));
        mao.adicionarCarta(new CartaTradicional(9, Naipe.OUROS)); // 5+7+9=21
        assertEquals(21, BlackjackUtils.calcularValorMao(mao));
        assertFalse(BlackjackUtils.isBlackjack(mao)); // precisa ser 2 cartas
    }

    @Test
    public void multiplosAses_convertePara1SeEstourar() {
        Mao mao = new Mao();
        mao.adicionarCarta(new CartaTradicional(1, Naipe.COPAS));   // Ás (11)
        mao.adicionarCarta(new CartaTradicional(1, Naipe.ESPADAS)); // Ás (11)
        mao.adicionarCarta(new CartaTradicional(9, Naipe.OUROS));   // 9
        // 11 + 11 + 9 = 31 → estoura, então um Ás vira 1 → 21 total
        assertEquals(21, BlackjackUtils.calcularValorMao(mao));
    }

    @Test
    public void cartasAltas_estoura() {
        Mao mao = new Mao();
        mao.adicionarCarta(new CartaTradicional(12, Naipe.COPAS));  // Dama = 10
        mao.adicionarCarta(new CartaTradicional(13, Naipe.ESPADAS));// Rei = 10
        mao.adicionarCarta(new CartaTradicional(5, Naipe.OUROS));   // 5
        assertEquals(25, BlackjackUtils.calcularValorMao(mao));
        assertTrue(BlackjackUtils.estourou(mao));
    }
}
