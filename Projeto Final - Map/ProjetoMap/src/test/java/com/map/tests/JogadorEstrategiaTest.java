package com.map.tests;

import com.map.framework.player.AcaoJogador;
import com.map.framework.player.EstrategiaIAAleatoria;
import com.map.framework.player.Jogador;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Testes para estratégias de jogador.
 */
public class JogadorEstrategiaTest {

    @Test
    public void estrategiaIA_escolheAcaoValida() {
        Jogador ia = new Jogador("CPU", new EstrategiaIAAleatoria(123L));
        List<AcaoJogador> acoes = new ArrayList<>();
        acoes.add(new AcaoJogador("A", "Ação A"));
        acoes.add(new AcaoJogador("B", "Ação B"));
        acoes.add(new AcaoJogador("C", "Ação C"));

        for (int i = 0; i < 100; i++) {
            AcaoJogador escolhida = ia.decidirAcao(null, acoes);
            assertNotNull(escolhida);
            assertTrue(acoes.contains(escolhida));
        }
    }

    @Test
    public void jogador_pontuacaoAlterada() {
        Jogador j = new Jogador("Teste", new EstrategiaIAAleatoria());
        assertEquals(0, j.getPontuacao());
        j.adicionarPontos(5);
        assertEquals(5, j.getPontuacao());
        j.subtrairPontos(2);
        assertEquals(3, j.getPontuacao());
        j.setPontuacao(10);
        assertEquals(10, j.getPontuacao());
    }

    @Test(expected = IllegalArgumentException.class)
    public void jogador_nomeVazio_lancaExcecao() {
        new Jogador("", new EstrategiaIAAleatoria());
    }

    @Test(expected = IllegalArgumentException.class)
    public void jogador_estrategiaNula_lancaExcecao() {
        new Jogador("Teste", null);
    }
}
