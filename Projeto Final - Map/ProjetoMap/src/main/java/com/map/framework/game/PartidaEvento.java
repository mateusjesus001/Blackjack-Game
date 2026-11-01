package com.map.framework.game;

import com.map.framework.card.Carta;
import com.map.framework.player.Jogador;

/**
 * Representa um evento ocorrido durante uma partida de cartas.
 * 
 * <p>Utilizado pelo padrão Observer para notificar os listeners sobre
 * acontecimentos importantes (ex: carta jogada, turno iniciado, partida finalizada).</p>
 */
public record PartidaEvento(
        TipoEvento tipo,
        Jogador jogador,
        Carta carta,
        String mensagem,
        Object dados
) {

    public enum TipoEvento {
        PARTIDA_INICIADA,
        CARTA_DISTRIBUIDA,
        TURNO_INICIADO,
        TURNO_FINALIZADO,
        CARTA_JOGADA,
        PONTUACAO_ALTERADA,
        JOGADOR_ELIMINADO,
        RODADA_FINALIZADA,
        PARTIDA_FINALIZADA,
        MENSAGEM
    }

    public PartidaEvento(TipoEvento tipo, String mensagem) {
        this(tipo, null, null, mensagem, null);
    }

    public PartidaEvento(TipoEvento tipo, Jogador jogador, String mensagem) {
        this(tipo, jogador, null, mensagem, null);
    }

    public PartidaEvento(TipoEvento tipo, Jogador jogador, Carta carta, String mensagem) {
        this(tipo, jogador, carta, mensagem, null);
    }
}
