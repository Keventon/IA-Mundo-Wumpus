package pdwumpus;

import java.util.*;

/** Aprende seis pesos da política com memória, sem entregar o mapa real ao agente. */
public final class AlgoritmoGenetico {
    public static final int POPULACAO=50,GERACOES=1000;
    public static final double CRUZAMENTO=0.85,MUTACAO=0.05;
    public static final class Estatistica {
        public final int geracao;
        public final double melhor,pior,media;
        Estatistica(int g,double b,double w,double m) {geracao=g;melhor=b;pior=w;media=m;}
    }
    public static final class Aprendizagem {
        public int[] genes;
        public double fitness;
        public int avaliacoes;
        public final List<Estatistica> evolucao=new ArrayList<>();
    }
    private static final class Individuo {
        final int[] genes;final double fitness;
        Individuo(int[] g,double f) {genes=g;fitness=f;}
    }

    /** Fitness auxilia a aprendizagem; não altera a pontuação oficial da partida. */
    public static double fitness(Resultado r,int versao) {
        if(versao==1) return r.pontuacao;
        double f=r.pontuacao+(r.venceu()?5000:0);
        if(versao==2) return f;
        if(versao!=3) throw new IllegalArgumentException("Fitness deve ser 1, 2 ou 3.");
        // Sinais intermediários graduais para reduzir a escassez de recompensa.
        return f+(r.vivo?100:0)+(r.venceu()?0:2*r.casasVisitadas)
                -(r.ouros>0&&!r.venceu()?10*(r.linha+r.coluna):0);
    }

    public static Aprendizagem treinar(Ambiente mapa,long semente,int geracoes,int limite,int versao) {
        if(mapa.n<=3) throw new IllegalArgumentException("Na etapa de aprendizagem, use n > 3.");
        if(geracoes<1) throw new IllegalArgumentException("Ao menos uma geração exigida.");
        Random random=new Random(semente);Map<String,Double> cache=new HashMap<>();
        Aprendizagem saida=new Aprendizagem();
        Individuo[] pop=new Individuo[POPULACAO];
        // Uma referência explícita e 49 candidatos aleatórios; nenhuma rota pronta.
        pop[0]=avaliar(AgenteV2.PESOS_REFERENCIA.clone(),mapa,semente,limite,versao,cache);
        for(int i=1;i<pop.length;i++) {
            int[] g=new int[6];for(int k=0;k<g.length;k++) g[k]=random.nextInt(11);
            pop[i]=avaliar(g,mapa,semente,limite,versao,cache);
        }
        registrar(saida,pop,0);
        for(int geracao=1;geracao<=geracoes;geracao++) {
            Individuo[] nova=new Individuo[POPULACAO];
            nova[0]=melhor(pop); // elitismo de um indivíduo
            for(int i=1;i<nova.length;i+=2) {
                int[] a=torneio(pop,random).genes.clone(),b=torneio(pop,random).genes.clone();
                if(random.nextDouble()<CRUZAMENTO) {
                    int corte=1+random.nextInt(a.length-1);
                    for(int k=corte;k<a.length;k++) {int t=a[k];a[k]=b[k];b[k]=t;}
                }
                mutar(a,random);mutar(b,random);
                nova[i]=avaliar(a,mapa,semente,limite,versao,cache);
                if(i+1<nova.length) nova[i+1]=avaliar(b,mapa,semente,limite,versao,cache);
            }
            pop=nova;registrar(saida,pop,geracao);
        }
        Individuo vencedor=melhor(pop);saida.genes=vencedor.genes.clone();
        saida.fitness=vencedor.fitness;saida.avaliacoes=cache.size();return saida;
    }

    private static Individuo avaliar(int[] g,Ambiente a,long seed,int limite,int v,Map<String,Double> cache) {
        String chave=Arrays.toString(g);Double f=cache.get(chave);
        if(f==null) {
            Resultado r=Simulador.executar(a,new AgenteV3(a.n,seed,g),limite,null);
            f=fitness(r,v);cache.put(chave,f);
        }
        // Mesma semente sensorial em todos os candidatos: avaliação determinística,
        // cache legítimo. Ele não reaproveita avaliações entre execuções distintas.
        return new Individuo(g,f);
    }
    private static Individuo melhor(Individuo[] p) {
        Individuo b=p[0];for(Individuo x:p) if(x.fitness>b.fitness) b=x;return b;
    }
    private static Individuo torneio(Individuo[] p,Random r) {
        Individuo b=p[r.nextInt(p.length)];
        for(int k=1;k<3;k++) {Individuo x=p[r.nextInt(p.length)];if(x.fitness>b.fitness) b=x;}
        return b;
    }
    private static void mutar(int[] g,Random r) {
        for(int k=0;k<g.length;k++) if(r.nextDouble()<MUTACAO) {
            int novo;do {novo=r.nextInt(11);} while(novo==g[k]);g[k]=novo;
        }
    }
    private static void registrar(Aprendizagem a,Individuo[] pop,int geracao) {
        double b=-Double.MAX_VALUE,w=Double.MAX_VALUE,soma=0;
        for(Individuo x:pop) {b=Math.max(b,x.fitness);w=Math.min(w,x.fitness);soma+=x.fitness;}
        a.evolucao.add(new Estatistica(geracao,b,w,soma/pop.length));
    }
}
