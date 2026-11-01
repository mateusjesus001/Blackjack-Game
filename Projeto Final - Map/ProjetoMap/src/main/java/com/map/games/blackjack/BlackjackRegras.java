package com.map.games.blackjack;

import com.map.framework.card.BaralhoTradicional;
import com.map.framework.card.Carta;
import com.map.framework.game.Partida;
import com.map.framework.game.PartidaEvento;
import com.map.framework.game.RegrasJogo;
import com.map.framework.player.AcaoJogador;
import com.map.framework.player.Jogador;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementação concreta de {@link RegrasJogo} para o jogo Blackjack (21).
 * 
 * <p><strong>Padrão GoF: Strategy (ConcreteStrategy)</strong></p>
 * 
 * <p>Regras simplificadas usadas nesta implementação:</p>
 * <ul>
 *   <li>Dois jogadores: Jogador vs Dealer (ambos usando estratégias do framework)</li>
 *   <li>1 a 6 baralhos</li>
 *   <li>Ações: HIT (comprar carta) e STAND (ficar)</li>
 *   <li>Quem chegar mais próximo de 21 sem estourar vence. Blackjack natural vale mais.</li>
 * </ul>
 */
public class BlackjackRegras implements RegrasJogo<BaralhoTradicional> {

    public static final AcaoJogador ACAO_HIT = new AcaoJogador("HIT", "Comprar mais uma carta");
    public static final AcaoJogador ACAO_STAND = new AcaoJogador("STAND", "Parar com as cartas atuais");

    private final int qtdBaralhos;
    private final Jogador[] jogadorQueParou;
    private int vencedorId = -1;

    public BlackjackRegras() {
        this(1);
    }

    public BlackjackRegras(int qtdBaralhos) {
        this.qtdBaralhos = qtdBaralhos;
        this.jogadorQueParou = new Jogador[2];
    }

    @Override
    public BaralhoTradicional criarBaralho() {
        return new BaralhoTradicional(qtdBaralhos);
    }

    @Override
    public int cartasIniciaisPorJogador() {
        return 2;
    }

    @Override
    public int maximoJogadores() {
        return 2;
    }

    @Override
    public int minimoJogadores() {
        return 2;
    }

    @Override
    public void aposDistribuicaoInicial(Partida<BaralhoTradicional, ? extends RegrasJogo<BaralhoTradicional>> partida) {
        for (Jogador j : partida.getJogadores()) {
            j.getMao().virarTodasParaCima();
            if (BlackjackUtils.isBlackjack(j.getMao())) {
                j.adicionarPontos(999);
            }
            partida.notificar(new PartidaEvento(PartidaEvento.TipoEvento.MENSAGEM, j,
                    "Mão vale " + BlackjackUtils.calcularValorMao(j.getMao()) + " pontos."));
        }
    }

    @Override
    public List<AcaoJogador> acoesPossiveis(Jogador jogador, Partida<BaralhoTradicional, ? extends RegrasJogo<BaralhoTradicional>> partida) {
        List<AcaoJogador> acoes = new ArrayList<>();
        if (jogadorQueParou[partida.getJogadores().indexOf(jogador)] != null) {
            return acoes; // já parou
        }
        int valor = BlackjackUtils.calcularValorMao(jogador.getMao());
        if (valor < 21 && !jogador.getMao().isCheia()) {
            acoes.add(ACAO_HIT);
        }
        acoes.add(ACAO_STAND);
        return acoes;
    }

    @Override
    public void executarAcao(Jogador jogador, AcaoJogador acao, Partida<BaralhoTradicional, ? extends RegrasJogo<BaralhoTradicional>> partida) {
        if (acao.equals(ACAO_HIT)) {
            if (!partida.getBaralho().isVazio()) {
                Carta carta = partida.getBaralho().comprarCarta();
                carta.virarParaCima();
                jogador.getMao().adicionarCarta(carta);
                partida.notificar(new PartidaEvento(PartidaEvento.TipoEvento.CARTA_JOGADA, jogador, carta, "Comprou carta"));
                jogador.adicionarPontos(0);
                if (BlackjackUtils.estourou(jogador.getMao())) {
                    partida.notificar(new PartidaEvento(PartidaEvento.TipoEvento.JOGADOR_ELIMINADO, jogador, "Estourou!"));
                    jogadorQueParou[partida.getJogadores().indexOf(jogador)] = jogador;
                }
            }
        } else if (acao.equals(ACAO_STAND)) {
            jogadorQueParou[partida.getJogadores().indexOf(jogador)] = jogador;
        }
        partida.notificar(new PartidaEvento(PartidaEvento.TipoEvento.MENSAGEM, jogador,
                "Mão agora vale " + BlackjackUtils.calcularValorMao(jogador.getMao()) + " pontos."));
    }

    @Override
    public boolean partidaFinalizada(Partida<BaralhoTradicional, ? extends RegrasJogo<BaralhoTradicional>> partida) {
        if (partida.getRodadaAtual() > 50) return true;
        List<Jogador> js = partida.getJogadores();
        // Se alguém tem Blackjack natural ou ambos pararam
        boolean temBJ = js.stream().anyMatch(j -> BlackjackUtils.isBlackjack(j.getMao()));
        boolean todosPararam = jogadorQueParou[0] != null && jogadorQueParou[1] != null;
        boolean todosEstouraram = js.stream().allMatch(j -> BlackjackUtils.estourou(j.getMao()));
        return temBJ || todosPararam || todosEstouraram;
    }

    @Override
    public Jogador getVencedor(Partida<BaralhoTradicional, ? extends RegrasJogo<BaralhoTradicional>> partida) {
        List<Jogador> js = partida.getJogadores();
        Jogador j1 = js.get(0);
        Jogador j2 = js.get(1);

        int v1 = BlackjackUtils.calcularValorMao(j1.getMao());
        int v2 = BlackjackUtils.calcularValorMao(j2.getMao());
        boolean e1 = BlackjackUtils.estourou(j1.getMao());
        boolean e2 = BlackjackUtils.estourou(j2.getMao());
        boolean bj1 = BlackjackUtils.isBlackjack(j1.getMao());
        boolean bj2 = BlackjackUtils.isBlackjack(j2.getMao());

        if (bj1 && !bj2) return j1;
        if (bj2 && !bj1) return j2;
        if (e1 && e2) return null;
        if (e1) return j2;
        if (e2) return j1;
        if (v1 > v2) return j1;
        if (v2 > v1) return j2;
        return null; // empate
    }
}
