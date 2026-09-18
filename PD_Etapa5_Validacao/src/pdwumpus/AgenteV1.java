package pdwumpus;

import java.util.*;

/** Reativo simples: não armazena casas, percepções passadas nem caminho. */
public final class AgenteV1 implements Agente {
    private final Random aleatorio;
    public AgenteV1(long semente) {aleatorio=new Random(semente);}
    public Acao decidir(Observacao p) {
        // Prioridades de execução da tabela de regras, não memória espacial.
        if(p.ouro&&p.id()==0) return Acao.SAIR;
        if(p.brilho&&!p.ouro) return Acao.PEGAR;
        List<Acao> aplicaveis=new ArrayList<>();
        for(int d=0;d<4;d++) aplicaveis.add(Acao.mover(d));
        if(p.fedor&&p.flecha)
            for(int d=0;d<4;d++) aplicaveis.add(Acao.atirar(d));
        // Brisa não informa a direção do poço: a versão 1 assume o risco.
        return aplicaveis.get(aleatorio.nextInt(aplicaveis.size()));
    }
}
