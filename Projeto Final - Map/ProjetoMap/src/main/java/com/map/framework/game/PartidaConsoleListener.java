package com.map.framework.game;

/**
 * Implementação concreta de {@link PartidaListener} que imprime os eventos
 * no console (saída padrão). Ideal para jogos executados em terminal.
 * 
 * <p><strong>Padrão GoF: Observer (ConcreteObserver)</strong></p>
 */
public class PartidaConsoleListener implements PartidaListener {

    @Override
    public void onEvento(PartidaEvento evento) {
        StringBuilder sb = new StringBuilder();
        sb.append("[Evento] ").append(evento.tipo());

        if (evento.jogador() != null) {
            sb.append(" | Jogador: ").append(evento.jogador().getNome());
        }
        if (evento.carta() != null) {
            sb.append(" | Carta: ").append(evento.carta());
        }
        if (evento.mensagem() != null && !evento.mensagem().isBlank()) {
            sb.append(" | ").append(evento.mensagem());
        }

        System.out.println(sb);
    }
}
