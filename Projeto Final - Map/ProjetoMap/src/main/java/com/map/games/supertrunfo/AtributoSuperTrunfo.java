package com.map.games.supertrunfo;

/**
 * Atributos que podem ser comparados em uma partida de Super Trunfo.
 * Cada atributo define se o "melhor" valor é o maior ou o menor.
 */
public enum AtributoSuperTrunfo {

    POTENCIA("Potência (HP)", true, "Maior potência vence"),
    VELOCIDADE_MAXIMA("Velocidade Máxima (km/h)", true, "Maior velocidade vence"),
    ACELERACAO("Aceleração 0-100 (s)", false, "Menor tempo de aceleração vence"),
    PRECO("Preço (R$ mil)", true, "Maior preço vence"),
    CILINDRADAS("Cilindradas (cc)", true, "Maior cilindrada vence");

    private final String nome;
    private final boolean maiorEhMelhor;
    private final String descricao;

    AtributoSuperTrunfo(String nome, boolean maiorEhMelhor, String descricao) {
        this.nome = nome;
        this.maiorEhMelhor = maiorEhMelhor;
        this.descricao = descricao;
    }

    public String getNome() {
        return nome;
    }

    public boolean isMaiorEhMelhor() {
        return maiorEhMelhor;
    }

    public String getDescricao() {
        return descricao;
    }

    /**
     * Compara dois valores para este atributo.
     *
     * @return negativo se a < b, positivo se a > b, 0 se iguais.
     *         Sempre retorna positivo se 'a' é o vencedor.
     */
    public int comparar(double a, double b) {
        int cmp = Double.compare(a, b);
        return maiorEhMelhor ? cmp : -cmp;
    }
}
