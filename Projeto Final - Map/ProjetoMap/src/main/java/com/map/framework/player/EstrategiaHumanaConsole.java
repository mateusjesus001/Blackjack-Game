package com.map.framework.player;

import com.map.framework.card.Carta;
import com.map.framework.game.Partida;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 * Estratégia de jogador que lê as entradas do usuário via console (Scanner).
 * Usada para jogadores humanos em jogos de terminal.
 * 
 * <p><strong>Padrão GoF: Strategy (ConcreteStrategy)</strong></p>
 */
public class EstrategiaHumanaConsole implements EstrategiaJogador {

    private final Scanner scanner;

    public EstrategiaHumanaConsole() {
        this(new Scanner(System.in));
    }

    /**
     * Construtor que permite injetar um {@link Scanner} compartilhado.
     * Útil para aplicações que precisam ler do console também fora do jogo
     * (ex.: menu "jogar novamente / sair") sem conflito de buffers.
     *
     * @param scanner Scanner compartilhado (não pode ser nulo)
     */
    public EstrategiaHumanaConsole(Scanner scanner) {
        if (scanner == null) {
            throw new IllegalArgumentException("Scanner não pode ser nulo.");
        }
        this.scanner = scanner;
    }

    @Override
    public AcaoJogador decidirAcao(Jogador jogador, Partida<?, ?> partida, List<AcaoJogador> acoesPossiveis) {
        System.out.println("\n===== Turno de " + jogador.getNome() + " =====");
        System.out.println("Pontuação: " + jogador.getPontuacao());
        System.out.println("Mão:\n" + jogador.getMao().exibir());
        System.out.println("\nEscolha uma ação:");
        for (int i = 0; i < acoesPossiveis.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + acoesPossiveis.get(i));
        }
        int escolha = lerInteiro(1, acoesPossiveis.size());
        return acoesPossiveis.get(escolha - 1);
    }

    @Override
    public Optional<Carta> escolherCarta(Jogador jogador, List<Carta> opcoes, Partida<?, ?> partida) {
        if (opcoes == null || opcoes.isEmpty()) {
            return Optional.empty();
        }
        System.out.println("\n" + jogador.getNome() + ", escolha uma carta:");
        for (int i = 0; i < opcoes.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + opcoes.get(i));
        }
        System.out.println("  0. Cancelar / Pular");
        int escolha = lerInteiro(0, opcoes.size());
        if (escolha == 0) {
            return Optional.empty();
        }
        return Optional.of(opcoes.get(escolha - 1));
    }

    private int lerInteiro(int min, int max) {
        while (true) {
            System.out.print("Digite sua escolha (" + min + "-" + max + "): ");
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
        }
    }
}
