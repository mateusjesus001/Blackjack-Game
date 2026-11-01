package com.map.framework.game;

/**
 * Interface que define um observador de eventos da partida.
 * 
 * <p><strong>Padrão GoF Aplicado: Observer</strong></p>
 * <p>Esta é a interface <em>Observer</em>. Classes interessadas em reagir a
 * eventos ocorridos durante a partida (ex: interface com usuário, logger,
 * estatísticas) implementam esta interface e se registram em uma {@link Partida}.</p>
 *
 * <p><strong>Pontos de Extensão:</strong> Novos listeners podem ser adicionados
 * a qualquer momento sem alterar a lógica da Partida. Exemplos: GUI web,
 * persistência em banco, inteligência artificial para análise, etc.</p>
 */
public interface PartidaListener {

    /**
     * Invocado quando um evento ocorre na partida.
     *
     * @param evento o evento ocorrido
     */
    void onEvento(PartidaEvento evento);
}
