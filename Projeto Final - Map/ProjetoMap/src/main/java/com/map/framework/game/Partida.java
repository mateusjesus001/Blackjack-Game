package com.map.framework.game;

import com.map.framework.card.Baralho;
import com.map.framework.card.Carta;
import com.map.framework.exception.JogoInvalidoException;
import com.map.framework.player.AcaoJogador;
import com.map.framework.player.Jogador;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Classe abstrata que orquestra uma partida de cartas.
 * 
 * <p><strong>Padrão GoF Aplicado: Template Method</strong></p>
 * <p>O método {@link #jogarPartida()} define o esqueleto fixo do fluxo de uma
 * partida (inicialização → distribuição → turnos → finalização), enquanto
 * delega os detalhes variáveis para subclasses ou para a estratégia
 * {@link RegrasJogo}. Os métodos protegidos são os <em>primitive operations</em>
 * e hooks do Template Method.</p>
 *
 * <p><strong>Padrão GoF Aplicado: Observer</strong></p>
 * <p>Partida atua como <em>Subject</em>, gerenciando uma lista de
 * {@link PartidaListener} e notificando-os sobre eventos importantes.</p>
 *
 * <p><strong>Padrão GoF Aplicado: Strategy</strong></p>
 * <p>Delega a lógica específica do jogo para a interface {@link RegrasJogo},
 * permitindo que diferentes regras sejam plugáveis.</p>
 *
 * <h2>Pontos de Extensão do Framework:</h2>
 * <ol>
 *   <li>Novos tipos de {@link Baralho} via Factory Method</li>
 *   <li>Novas {@link RegrasJogo} para jogos diferentes (Strategy)</li>
 *   <li>Novas {@link com.map.framework.player.EstrategiaJogador} (humanos, IAs)</li>
 *   <li>Novos {@link PartidaListener} para notificações (Observer)</li>
 *   <li>Novas {@link com.map.framework.card.EmbaralhadorStrategy}</li>
 * </ol>
 *
 * @param <B> tipo do Baralho usado nesta partida
 * @param <R> tipo das Regras usadas (especializadas em B)
 */
public abstract class Partida<B extends Baralho, R extends RegrasJogo<B>> {

    protected final List<Jogador> jogadores;
    protected final B baralho;
    protected final R regras;
    protected final List<PartidaListener> listeners;

    protected int rodadaAtual;
    protected int jogadorDaVezIndex;
    protected boolean partidaIniciada;
    protected boolean partidaFinalizada;

    /**
     * Cria uma nova partida com jogadores e regras.
     *
     * @param jogadores lista de jogadores (não pode ser nula ou vazia)
     * @param regras    regras específicas do jogo
     */
    protected Partida(List<Jogador> jogadores, R regras) {
        if (jogadores == null || jogadores.isEmpty()) {
            throw new JogoInvalidoException("Uma partida precisa de pelo menos um jogador.");
        }
        if (regras == null) {
            throw new JogoInvalidoException("Regras do jogo não podem ser nulas.");
        }
        if (jogadores.size() < regras.minimoJogadores()) {
            throw new JogoInvalidoException(
                    "Quantidade insuficiente de jogadores. Mínimo: " + regras.minimoJogadores());
        }
        if (jogadores.size() > regras.maximoJogadores()) {
            throw new JogoInvalidoException(
                    "Quantidade excessiva de jogadores. Máximo: " + regras.maximoJogadores());
        }

        this.jogadores = new ArrayList<>(jogadores);
        this.regras = regras;
        this.baralho = regras.criarBaralho();
        this.listeners = new ArrayList<>();
        this.rodadaAtual = 0;
        this.jogadorDaVezIndex = 0;
        this.partidaIniciada = false;
        this.partidaFinalizada = false;
    }

    // ==================== Observer (Subject) ====================

    public void adicionarListener(PartidaListener listener) {
        if (listener != null) {
            listeners.add(listener);
        }
    }

    public void removerListener(PartidaListener listener) {
        listeners.remove(listener);
    }

    public void notificar(PartidaEvento evento) {
        listeners.forEach(l -> l.onEvento(evento));
    }

    // ==================== Template Method - Fluxo da Partida ====================

    /**
     * <strong>Template Method</strong>: Orquestra todo o ciclo de vida da partida.
     * Define os passos fixos do algoritmo e delega partes variáveis para hooks.
     */
    public final Jogador jogarPartida() {
        if (partidaIniciada) {
            throw new JogoInvalidoException("A partida já foi iniciada.");
        }
        partidaIniciada = true;

        inicializarPartida();            // Passo 1
        distribuirCartasIniciais();      // Passo 2
        hookAposDistribuicao();          // Hook

        notificar(new PartidaEvento(PartidaEvento.TipoEvento.PARTIDA_INICIADA, "Partida iniciada!"));

        while (!regras.partidaFinalizada(this)) {
            rodadaAtual++;
            notificar(new PartidaEvento(PartidaEvento.TipoEvento.MENSAGEM, "--- Rodada " + rodadaAtual + " ---"));
            hookAntesDaRodada();         // Hook

            rodarTurnos();               // Passo 3 - loop de turnos

            hookAposRodada();            // Hook
            notificar(new PartidaEvento(PartidaEvento.TipoEvento.RODADA_FINALIZADA,
                    "Rodada " + rodadaAtual + " finalizada."));
        }

        return finalizarPartida();       // Passo 4
    }

    /** Inicializa a partida (embaralha, reseta jogadores). */
    protected void inicializarPartida() {
        baralho.resetar();
        baralho.embaralhar();
        jogadores.forEach(j -> {
            j.getMao().limpar();
            j.setPontuacao(0);
        });
    }

    /** Distribui as cartas iniciais para cada jogador. */
    protected void distribuirCartasIniciais() {
        int qtd = regras.cartasIniciaisPorJogador();
        for (int i = 0; i < qtd; i++) {
            for (Jogador j : jogadores) {
                if (!baralho.isVazio()) {
                    Carta carta = baralho.comprarCarta();
                    j.getMao().adicionarCarta(carta);
                    notificar(new PartidaEvento(PartidaEvento.TipoEvento.CARTA_DISTRIBUIDA, j, carta,
                            "Recebeu carta"));
                }
            }
        }
    }

    /** Executa um turno de cada jogador (ordem de turno). */
    protected void rodarTurnos() {
        for (int i = 0; i < jogadores.size() && !regras.partidaFinalizada(this); i++) {
            jogadorDaVezIndex = i % jogadores.size();
            Jogador jogador = jogadores.get(jogadorDaVezIndex);
            executarTurno(jogador);
        }
    }

    /**
     * Executa o turno de um jogador.
     * Pede a lista de ações possíveis às regras, solicita a decisão à estratégia
     * do jogador e delega a execução para as regras.
     */
    protected void executarTurno(Jogador jogador) {
        notificar(new PartidaEvento(PartidaEvento.TipoEvento.TURNO_INICIADO, jogador, "Iniciou turno"));

        List<AcaoJogador> acoes = regras.acoesPossiveis(jogador, this);
        if (!acoes.isEmpty()) {
            AcaoJogador escolhida = jogador.decidirAcao(this, acoes);
            regras.executarAcao(jogador, escolhida, this);
        }

        notificar(new PartidaEvento(PartidaEvento.TipoEvento.TURNO_FINALIZADO, jogador, "Finalizou turno"));
    }

    /** Finaliza a partida e retorna o vencedor. */
    protected Jogador finalizarPartida() {
        partidaFinalizada = true;
        Jogador vencedor = regras.getVencedor(this);
        String msg = vencedor != null ? "Vencedor: " + vencedor.getNome() : "Empate ou sem vencedor.";
        notificar(new PartidaEvento(PartidaEvento.TipoEvento.PARTIDA_FINALIZADA, vencedor, msg));
        return vencedor;
    }

    // ==================== Hooks (subclasses podem sobrescrever) ====================

    protected void hookAposDistribuicao() {
        regras.aposDistribuicaoInicial(this);
    }

    protected void hookAntesDaRodada() {
        regras.antesDaRodada(this);
    }

    protected void hookAposRodada() {
        regras.aposRodada(this);
    }

    // ==================== Getters e Acessores (API Pública) ====================

    public List<Jogador> getJogadores() {
        return Collections.unmodifiableList(jogadores);
    }

    public Jogador getJogadorDaVez() {
        if (jogadores.isEmpty()) return null;
        return jogadores.get(jogadorDaVezIndex % jogadores.size());
    }

    public B getBaralho() {
        return baralho;
    }

    public R getRegras() {
        return regras;
    }

    public int getRodadaAtual() {
        return rodadaAtual;
    }

    public boolean isPartidaIniciada() {
        return partidaIniciada;
    }

    public boolean isPartidaFinalizada() {
        return partidaFinalizada;
    }
}
