package com.map.games.supertrunfo;

import com.map.framework.GameFacade;
import com.map.framework.game.Partida;
import com.map.framework.player.EstrategiaHumanaConsole;
import com.map.framework.player.EstrategiaIAAleatoria;
import com.map.framework.player.Jogador;

import java.util.List;

/**
 * Aplicação cliente para jogar Super Trunfo via console.
 * 
 * <p>Demonstra a <strong>extensibilidade</strong> do framework: um jogo completamente
 * diferente do Blackjack usa exatamente as mesmas classes-base do pacote
 * {@code com.map.framework}, apenas estendendo Carta, Baralho e RegrasJogo.</p>
 */
public class MainSuperTrunfo {

    public static void main(String[] args) {
        System.out.println("=====================================");
        System.out.println("   SUPER TRUNFO - Projeto MAP 2026.1");
        System.out.println("   🏎️ Tema: Carros Esportivos 🏎️");
        System.out.println("=====================================\n");

        Jogador humano = new Jogador("Você", new EstrategiaHumanaConsole());
        Jogador cpu = new Jogador("Computador", new EstrategiaIAAleatoria(7L));

        SuperTrunfoRegras regras = new SuperTrunfoRegras();

        Partida<BaralhoSuperTrunfo, SuperTrunfoRegras> partida =
                GameFacade.criarPartida(List.of(humano, cpu), regras);

        humano.getMao().virarTodasParaCima();

        Jogador vencedor = GameFacade.executarPartida(partida);

        System.out.println("\n=====================================");
        System.out.println("Pontuação final:");
        System.out.println("  " + humano.getNome() + ": " + humano.getPontuacao() + " pontos");
        System.out.println("  " + cpu.getNome() + ": " + cpu.getPontuacao() + " pontos");
        System.out.println("=====================================");
        if (vencedor != null) {
            System.out.println("🏆 Vencedor: " + vencedor.getNome());
        } else {
            System.out.println("🤝 Empate!");
        }
    }
}
