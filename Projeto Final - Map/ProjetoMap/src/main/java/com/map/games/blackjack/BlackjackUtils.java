package com.map.games.blackjack;

import com.map.framework.card.Carta;
import com.map.framework.card.CartaTradicional;
import com.map.framework.player.Mao;

/**
 * Classe utilitária com a lógica de pontuação do Blackjack.
 * 
 * <p>Regras básicas:
 * <ul>
 *   <li>Cartas 2-10: valor nominal</li>
 *   <li>Valete (J), Dama (Q), Rei (K): 10 pontos</li>
 *   <li>Ás (A): 1 ou 11 pontos, o que for mais vantajoso sem estourar 21</li>
 * </ul>
 */
public final class BlackjackUtils {

    public static final int ALVO = 21;
    public static final int VALOR_FIGURA = 10;

    private BlackjackUtils() {
    }

    /**
     * Calcula o valor total da mão de um jogador no Blackjack.
     *
     * @param mao mão do jogador
     * @return valor total (melhor combinação considerando Ás flexível)
     */
    public static int calcularValorMao(Mao mao) {
        int total = 0;
        int ases = 0;

        for (Carta c : mao.getCartas()) {
            if (!(c instanceof CartaTradicional ct)) {
                continue;
            }
            int valor = ct.getValor();
            if (valor == 1) {
                ases++;
                total += 11;
            } else if (valor >= 10) {
                total += VALOR_FIGURA;
            } else {
                total += valor;
            }
        }

        while (total > ALVO && ases > 0) {
            total -= 10;
            ases--;
        }

        return total;
    }

    /**
     * Verifica se a mão "estourou" (passou de 21).
     */
    public static boolean estourou(Mao mao) {
        return calcularValorMao(mao) > ALVO;
    }

    /**
     * Verifica se a mão é Blackjack natural (21 com exatamente 2 cartas).
     */
    public static boolean isBlackjack(Mao mao) {
        return mao.tamanho() == 2 && calcularValorMao(mao) == ALVO;
    }
}
