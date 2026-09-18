package pdwumpus;

import java.util.*;

/** Baseada em modelo e objetivos: infere segurança e planeja sobre casas conhecidas. */
public class AgenteV2 implements Agente {
    public static final int[] PESOS_REFERENCIA={3,10,10,2,5,5};
    private final int n,total;
    private final int[] pesos,visitas,suspeitaP,suspeitaW,distancia,primeira,fila;
    private final boolean[] possivelP,possivelW,observada;
    private final Random aleatorio;
    private int ultimaCasa=-1,ultimoDisparo=-1;

    public AgenteV2(int n,long semente) {this(n,semente,PESOS_REFERENCIA);}
    public AgenteV2(int n,long semente,int[] pesos) {
        if(pesos.length!=6) throw new IllegalArgumentException("Seis pesos exigidos.");
        this.n=n;total=n*n;this.pesos=pesos.clone();aleatorio=new Random(semente);
        visitas=new int[total];suspeitaP=new int[total];suspeitaW=new int[total];
        distancia=new int[total];primeira=new int[total];fila=new int[total];
        possivelP=new boolean[total];possivelW=new boolean[total];observada=new boolean[total];
        Arrays.fill(possivelP,true);Arrays.fill(possivelW,true);
    }

    private int vizinho(int i,int d) {
        Acao a=Acao.mover(d);int l=i/n+a.dl,c=i%n+a.dc;
        return l<0||l>=n||c<0||c>=n?-1:l*n+c;
    }

    private void memorizar(Observacao p) {
        int i=p.id();
        if(ultimaCasa!=i) {visitas[i]++;ultimaCasa=i;}
        // Sobreviver à entrada confirma que a casa atual é segura.
        possivelP[i]=false;possivelW[i]=false;
        if(p.grito&&ultimoDisparo>=0) possivelW[ultimoDisparo]=false;
        for(int d=0;d<4;d++) {
            int v=vizinho(i,d);if(v<0) continue;
            // Ausência de um sinal permite descartar o perigo correspondente.
            if(!p.brisa) possivelP[v]=false;
            if(!p.fedor) possivelW[v]=false;
            if(!observada[i]) {
                if(p.brisa) suspeitaP[v]++;
                if(p.fedor) suspeitaW[v]++;
            }
        }
        observada[i]=true;
    }

    /** Busca em largura. Caminhos intermediários usam somente casas visitadas. */
    private void calcularCaminhos(int origem) {
        Arrays.fill(distancia,-1);Arrays.fill(primeira,-1);
        int inicio=0,fim=0;fila[fim++]=origem;distancia[origem]=0;
        while(inicio<fim) {
            int i=fila[inicio++];
            for(int d=0;d<4;d++) {
                int v=vizinho(i,d);if(v<0||distancia[v]>=0) continue;
                distancia[v]=distancia[i]+1;
                primeira[v]=i==origem?d:primeira[i];
                // Uma fronteira pode ser destino, mas não passagem desconhecida.
                if(visitas[v]>0) fila[fim++]=v;
            }
        }
    }

    public Acao decidir(Observacao p) {
        memorizar(p);int i=p.id();
        if(p.ouro&&i==0) return Acao.SAIR;
        if(p.brilho&&!p.ouro) return Acao.PEGAR;
        calcularCaminhos(i);
        if(p.ouro) {
            // O caminho até o início existe porque todas essas casas foram visitadas.
            if(primeira[0]<0) throw new IllegalStateException("Memória de retorno desconectada.");
            return Acao.mover(primeira[0]);
        }

        if(p.fedor&&p.flecha) {
            int quantidade=0,melhor=-1,maior=-1;
            for(int d=0;d<4;d++) {
                int v=vizinho(i,d);
                if(v>=0&&possivelW[v]) {
                    quantidade++;
                    if(suspeitaW[v]>maior) {maior=suspeitaW[v];melhor=d;}
                }
            }
            // Candidato único é inferência; os demais casos dependem do peso de cautela.
            if(melhor>=0&&(quantidade==1||maior*2>=pesos[5])) {
                ultimoDisparo=vizinho(i,melhor);return Acao.atirar(melhor);
            }
        }

        boolean existeSegura=false;
        for(int v=0;v<total;v++)
            if(visitas[v]==0&&distancia[v]>0&&!possivelP[v]&&!possivelW[v]) existeSegura=true;
        long maiorNota=Long.MIN_VALUE;int escolhido=-1,empates=0;
        for(int v=0;v<total;v++) {
            if(visitas[v]>0||distancia[v]<=0) continue;
            boolean segura=!possivelP[v]&&!possivelW[v];
            if(existeSegura&&!segura) continue;
            int informacao=0;
            for(int d=0;d<4;d++) {int x=vizinho(v,d);if(x>=0&&visitas[x]==0) informacao++;}
            long nota=-(long)(pesos[0]+1)*distancia[v]
                    -(long)pesos[1]*(possivelP[v]?1+suspeitaP[v]:0)*10
                    -(long)pesos[2]*(possivelW[v]?1+suspeitaW[v]:0)*10
                    +(long)pesos[3]*informacao
                    +(long)(pesos[4]-5)*(v/n-v%n);
            if(nota>maiorNota) {maiorNota=nota;escolhido=v;empates=1;}
            else if(nota==maiorNota&&aleatorio.nextInt(++empates)==0) escolhido=v;
        }
        if(escolhido>=0) return Acao.mover(primeira[escolhido]);
        // Sem fronteiras: retorna ao início. Sem ouro, saída é fracasso explícito.
        return i==0?Acao.SAIR:Acao.mover(primeira[0]);
    }
}
