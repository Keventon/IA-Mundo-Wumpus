package pdwumpus;

import java.util.function.Consumer;

/** Único motor para todas as versões: mesma dinâmica, pontuação e orçamento. */
public final class Simulador {
    private Simulador() { }
    public static Resultado executar(Ambiente original,Agente agente,int limite,Consumer<String> log) {
        if(limite<=0) throw new IllegalArgumentException("Limite positivo exigido.");
        Ambiente a=original.copia();Resultado r=new Resultado();
        boolean flecha=true,impacto=false,grito=false;
        boolean[] visitados=new boolean[a.n*a.n];visitados[0]=true;r.casasVisitadas=1;
        while(r.acoes<limite&&r.fim==null) {
            int i=a.id(r.linha,r.coluna);
            Observacao p=new Observacao(a.n,r.linha,r.coluna,a.vizinho(i,true),
                    a.vizinho(i,false),a.ouro(i),impacto,grito,flecha,r.ouros>0);
            Acao ac=agente.decidir(p);
            if(ac==null) throw new IllegalStateException("Agente retornou uma ação nula.");
            impacto=false;grito=false;r.acoes++;r.pontuacao--;
            if(ac.movimento()) {
                int l=r.linha+ac.dl,c=r.coluna+ac.dc;
                if(!a.dentro(l,c)) impacto=true;
                else {
                    r.linha=l;r.coluna=c;i=a.id(l,c);
                    if(!visitados[i]) {visitados[i]=true;r.casasVisitadas++;}
                    if(a.poco(i)) {r.fim=Resultado.Fim.POCO;r.vivo=false;r.pontuacao-=1000;}
                    else if(a.wumpus(i)) {r.fim=Resultado.Fim.WUMPUS;r.vivo=false;r.pontuacao-=1000;}
                }
            } else if(ac.disparo()) {
                // Convenção do slide 91: ação atinge só uma casa, sem diagonais.
                if(flecha) {
                    flecha=false;int l=r.linha+ac.dl,c=r.coluna+ac.dc;
                    if(a.dentro(l,c)&&a.wumpus(a.id(l,c))) {
                        a.matar(a.id(l,c));r.wumpusMortos++;r.pontuacao+=1000;grito=true;
                    }
                }
            } else if(ac==Acao.PEGAR) {
                if(a.ouro(i)) {a.coletar(i);r.ouros++;r.pontuacao+=1000;}
            } else if(ac==Acao.SAIR&&i==0) {
                r.fim=r.ouros>0?Resultado.Fim.VITORIA:Resultado.Fim.SAIDA_SEM_OURO;
            }
            if(log!=null) log.accept(r.acoes+" | "+p+" -> "+ac+" | pontos="+r.pontuacao);
        }
        if(r.fim==null) r.fim=Resultado.Fim.LIMITE;
        return r;
    }
}
