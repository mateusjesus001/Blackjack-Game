package com.map.games.blackjack;

import com.map.framework.GameFacade;
import com.map.framework.game.Partida;
import com.map.framework.player.EstrategiaHumanaConsole;
import com.map.framework.player.Jogador;

import java.util.List;
import java.util.Scanner;

/**
 * Aplicação cliente (jogo concreto) que executa partidas de Blackjack
 * utilizando o mini-framework MAP.
 * 
 * <p>Demonstra a API pública do framework e a separação entre framework e
 * aplicação cliente (requisitos #1 e #3).</p>
 * 
 * <p>Permite ao usuário jogar quantas partidas quiser, com a opção
 * "jogar novamente" ou "sair" após cada partida.</p>
 */
public class MainBlackjack {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=====================================");
        System.out.println("   BLACKJACK - Projeto MAP 2026.1");
        System.out.println("=====================================\n");

        boolean jogarNovamente;
        do {
            jogarUmaPartida(scanner);

            System.out.println("\n=====================================");
            System.out.println("   Deseja jogar novamente?");
            System.out.println("   1) Jogar novamente");
            System.out.println("   2) Sair");
            System.out.print("Digite sua escolha (1-2): ");
            int opcao = lerInteiro(scanner, 1, 2);
            jogarNovamente = (opcao == 1);
        } while (jogarNovamente);

        System.out.println("\nObrigado por jogar Blackjack! Até a próxima! 👋");
        scanner.close();
    }

    private static void jogarUmaPartida(Scanner scanner) {
        System.out.println("-------------------------------------");
        System.out.println("   Nova partida!");
        System.out.println("-------------------------------------\n");

        Jogador humano = new Jogador("Você", new EstrategiaHumanaConsole(scanner));
        Jogador dealer = new Jogador("Dealer (IA)", new EstrategiaDealerBlackjack());

        BlackjackRegras regras = new BlackjackRegras(2);

        Partida<com.map.framework.card.BaralhoTradicional, BlackjackRegras> partida =
                GameFacade.criarPartida(List.of(humano, dealer), regras);

        Jogador vencedor = GameFacade.executarPartida(partida);

        System.out.println("\n=====================================");
        System.out.println("Pontuação final:");
        System.out.println("  " + humano.getNome() + ": " +
                BlackjackUtils.calcularValorMao(humano.getMao()) + " pts " +
                humano.getMao().exibir());
        System.out.println("  " + dealer.getNome() + ": " +
                BlackjackUtils.calcularValorMao(dealer.getMao()) + " pts " +
                dealer.getMao().exibir());
        System.out.println("=====================================");
        if (vencedor != null) {
            System.out.println("🏆 Vencedor: " + vencedor.getNome());
        } else {
            System.out.println("🤝 Empate!");
        }
    }

    private static int lerInteiro(Scanner scanner, int min, int max) {
        while (true) {
            try {
                String linha = scanner.nextLine().trim();
                if (linha.isBlank()) continue;
                int val = Integer.parseInt(linha);
                if (val >= min && val <= max) {
                    return val;
                }
                System.out.println("Valor fora do intervalo. Tente novamente.");
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite um número.");
            }
            System.out.print("Digite sua escolha (" + min + "-" + max + "): ");
        }
    }
}