package com.map.games.supertrunfo;

import com.map.framework.card.Carta;
import com.map.framework.game.Partida;
import com.map.framework.game.PartidaEvento;
import com.map.framework.game.RegrasJogo;
import com.map.framework.player.AcaoJogador;
import com.map.framework.player.Jogador;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementação de {@link RegrasJogo} para o jogo Super Trunfo (versão simplificada).
 * 
 * <p><strong>Padrão GoF: Strategy (ConcreteStrategy)</strong></p>
 *
 * <p>Regras simplificadas:</p>
 * <ul>
 *   <li>2 jogadores, 5 cartas iniciais cada</li>
 *   <li>Em cada turno, o jogador da vez escolhe um atributo</li>
 *   <li>Ambos jogam a primeira carta da mão</li>
 *   <li>Vence a comparação quem tiver o melhor valor (depende do atributo)</li>
 *   <li>Super Trunfo vence qualquer carta exceto se a outra for do menor atributo</li>
 *   <li>Vencedor ganha 1 ponto por rodada e as cartas da rodada</li>
 *   <li>Jogo acaba quando alguém ficar sem cartas ou completar 10 rodadas</li>
 * </ul>
 */
public class SuperTrunfoRegras implements RegrasJogo<BaralhoSuperTrunfo> {

    public static final AcaoJogador ACAO_ESCOLHER_POTENCIA = new AcaoJogador("POT", "Potência (HP)");
    public static final AcaoJogador ACAO_ESCOLHER_VELOCIDADE = new AcaoJogador("VEL", "Velocidade Máxima");
    public static final AcaoJogador ACAO_ESCOLHER_ACELERACAO = new AcaoJogador("ACE", "Aceleração 0-100");
    public static final AcaoJogador ACAO_ESCOLHER_PRECO = new AcaoJogador("PRE", "Preço");
    public static final AcaoJogador ACAO_ESCOLHER_CILINDRADAS = new AcaoJogador("CIL", "Cilindradas");

    private static final int RODADAS_MAX = 10;

    private AtributoSuperTrunfo atributoEscolhido;

    @Override
    public BaralhoSuperTrunfo criarBaralho() {
        return new BaralhoSuperTrunfo();
    }

    @Override
    public int cartasIniciaisPorJogador() {
        return 5;
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
    public List<AcaoJogador> acoesPossiveis(Jogador jogador, Partida<BaralhoSuperTrunfo, ? extends RegrasJogo<BaralhoSuperTrunfo>> partida) {
        List<AcaoJogador> acoes = new ArrayList<>();
        if (!jogador.getMao().isVazia()) {
            acoes.add(ACAO_ESCOLHER_POTENCIA);
            acoes.add(ACAO_ESCOLHER_VELOCIDADE);
            acoes.add(ACAO_ESCOLHER_ACELERACAO);
            acoes.add(ACAO_ESCOLHER_PRECO);
            acoes.add(ACAO_ESCOLHER_CILINDRADAS);
        }
        return acoes;
    }

    @Override
    public void executarAcao(Jogador jogador, AcaoJogador acao, Partida<BaralhoSuperTrunfo, ? extends RegrasJogo<BaralhoSuperTrunfo>> partida) {
        atributoEscolhido = switch (acao.codigo()) {
            case "POT" -> AtributoSuperTrunfo.POTENCIA;
            case "VEL" -> AtributoSuperTrunfo.VELOCIDADE_MAXIMA;
            case "ACE" -> AtributoSuperTrunfo.ACELERACAO;
            case "PRE" -> AtributoSuperTrunfo.PRECO;
            case "CIL" -> AtributoSuperTrunfo.CILINDRADAS;
            default -> throw new IllegalArgumentException("Atributo inválido: " + acao);
        };

        partida.notificar(new PartidaEvento(PartidaEvento.TipoEvento.MENSAGEM,
                jogador.getNome() + " escolheu: " + atributoEscolhido.getNome() + " (" + atributoEscolhido.getDescricao() + ")"));

        realizarRodada(partida);
    }

    private void realizarRodada(Partida<BaralhoSuperTrunfo, ? extends RegrasJogo<BaralhoSuperTrunfo>> partida) {
        List<Jogador> jogadores = partida.getJogadores();
        Jogador j1 = jogadores.get(0);
        Jogador j2 = jogadores.get(1);

        if (j1.getMao().isVazia() || j2.getMao().isVazia()) return;

        CartaSuperTrunfo c1 = (CartaSuperTrunfo) j1.getMao().removerCarta(0);
        CartaSuperTrunfo c2 = (CartaSuperTrunfo) j2.getMao().removerCarta(0);
        c1.virarParaCima();
        c2.virarParaCima();

        partida.notificar(new PartidaEvento(PartidaEvento.TipoEvento.CARTA_JOGADA, j1, c1, "Jogou"));
        partida.notificar(new PartidaEvento(PartidaEvento.TipoEvento.CARTA_JOGADA, j2, c2, "Jogou"));

        Jogador vencedorRodada = comparar(c1, c2, atributoEscolhido) >= 0 ? j1 : j2;
        vencedorRodada.adicionarPontos(1);

        partida.notificar(new PartidaEvento(PartidaEvento.TipoEvento.MENSAGEM,
                "Resultado: " + c1.getMarca() + " " + c1.getModelo() + " (" +
                        String.format("%.1f", c1.getAtributo(atributoEscolhido)) + ") vs " +
                        c2.getMarca() + " " + c2.getModelo() + " (" +
                        String.format("%.1f", c2.getAtributo(atributoEscolhido)) + ")" +
                        " → " + vencedorRodada.getNome() + " venceu a rodada!"));

        atributoEscolhido = null;
    }

    private int comparar(CartaSuperTrunfo a, CartaSuperTrunfo b, AtributoSuperTrunfo atr) {
        if (a.isEhSuperTrunfo() && !b.isEhSuperTrunfo()) return 1;
        if (b.isEhSuperTrunfo() && !a.isEhSuperTrunfo()) return -1;
        return atr.comparar(a.getAtributo(atr), b.getAtributo(atr));
    }

    @Override
    public boolean partidaFinalizada(Partida<BaralhoSuperTrunfo, ? extends RegrasJogo<BaralhoSuperTrunfo>> partida) {
        List<Jogador> js = partida.getJogadores();
        boolean alguemSemCartas = js.stream().anyMatch(j -> j.getMao().isVazia());
        boolean rodadasExcedidas = partida.getRodadaAtual() >= RODADAS_MAX;
        return alguemSemCartas || rodadasExcedidas;
    }

    @Override
    public Jogador getVencedor(Partida<BaralhoSuperTrunfo, ? extends RegrasJogo<BaralhoSuperTrunfo>> partida) {
        List<Jogador> js = partida.getJogadores();
        Jogador j1 = js.get(0);
        Jogador j2 = js.get(1);
        if (j1.getPontuacao() > j2.getPontuacao()) return j1;
        if (j2.getPontuacao() > j1.getPontuacao()) return j2;
        return null; // empate
    }
}
