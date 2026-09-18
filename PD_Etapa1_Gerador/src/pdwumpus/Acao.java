package pdwumpus;

/** Todas as ações custam um ponto, inclusive impacto, coleta e saída. */
public enum Acao {
    NORTE(-1, 0), SUL(1, 0), LESTE(0, 1), OESTE(0, -1),
    ATIRAR_NORTE(-1, 0), ATIRAR_SUL(1, 0),
    ATIRAR_LESTE(0, 1), ATIRAR_OESTE(0, -1), PEGAR(0, 0), SAIR(0, 0);

    public final int dl, dc;
    Acao(int dl, int dc) { this.dl = dl; this.dc = dc; }
    public boolean movimento() { return ordinal() < 4; }
    public boolean disparo() { return ordinal() >= 4 && ordinal() < 8; }
    public static Acao mover(int direcao) { return values()[direcao]; }
    public static Acao atirar(int direcao) { return values()[direcao + 4]; }
}
