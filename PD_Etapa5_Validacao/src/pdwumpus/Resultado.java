package pdwumpus;

public final class Resultado {
    public enum Fim {VITORIA, POCO, WUMPUS, LIMITE, SAIDA_SEM_OURO}
    public Fim fim;
    public int acoes,pontuacao,ouros,wumpusMortos,casasVisitadas,linha,coluna;
    public boolean vivo=true;
    public boolean venceu() {return fim==Fim.VITORIA;}
    public String toString() {
        return "fim="+fim+" ações="+acoes+" pontos="+pontuacao+" ouro="+ouros+
                " Wumpus mortos="+wumpusMortos+" casas="+casasVisitadas;
    }
}
