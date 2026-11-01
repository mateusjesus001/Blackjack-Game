package com.map.tests;

import com.map.framework.card.BaralhoTradicional;
import com.map.framework.card.Carta;
import com.map.framework.card.EmbaralhadorStrategy;
import com.map.framework.card.SemEmbaralhamento;
import com.map.framework.exception.BaralhoVazioException;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Testes unitários para BaralhoTradicional.
 * 
 * <p>Cobre: Factory Method, estratégias de embaralhamento, compra de cartas.</p>
 */
public class BaralhoTradicionalTest {

    @Test
    public void baralhoUnico_tem52Cartas() {
        BaralhoTradicional baralho = new BaralhoTradicional(1);
        assertEquals(52, baralho.tamanho());
    }

    @Test
    public void baralhoSeisUnidades_tem312Cartas() {
        BaralhoTradicional baralho = new BaralhoTradicional(6);
        assertEquals(312, baralho.tamanho());
        assertEquals(6, baralho.getQuantidadeDeBaralhos());
    }

    @Test
    public void comprarCarta_diminuiTamanho() {
        BaralhoTradicional baralho = new BaralhoTradicional();
        Carta c = baralho.comprarCarta();
        assertNotNull(c);
        assertEquals(51, baralho.tamanho());
    }

    @Test(expected = BaralhoVazioException.class)
    public void comprarBaralhoVazio_lancaExcecao() {
        BaralhoTradicional baralho = new BaralhoTradicional();
        for (int i = 0; i < 52; i++) {
            baralho.comprarCarta();
        }
        assertTrue(baralho.isVazio());
        baralho.comprarCarta(); // deve lançar
    }

    @Test
    public void semEmbaralhamento_ordemPreservada() {
        EmbaralhadorStrategy sem = new SemEmbaralhamento();
        BaralhoTradicional b1 = new BaralhoTradicional();
        BaralhoTradicional b2 = new BaralhoTradicional();
        b1.setEmbaralhador(sem);
        b2.setEmbaralhador(sem);
        b1.embaralhar();
        b2.embaralhar();

        List<Carta> l1 = b1.getCartasNaoModificaveis();
        List<Carta> l2 = b2.getCartasNaoModificaveis();
        for (int i = 0; i < 52; i++) {
            assertEquals(l1.get(i).getIdentificador(), l2.get(i).getIdentificador());
        }
    }

    @Test
    public void resetar_restaura52Cartas() {
        BaralhoTradicional baralho = new BaralhoTradicional();
        for (int i = 0; i < 20; i++) baralho.comprarCarta();
        assertEquals(32, baralho.tamanho());
        baralho.resetar();
        assertEquals(52, baralho.tamanho());
    }
}
