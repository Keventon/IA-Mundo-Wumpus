package pdwumpus;

import java.util.*;

/** Amostragem sem reposição em cada camada, usando uma semente reproduzível. */
public final class Gerador {
    private Gerador() { }
    public static Ambiente gerar(int n,int p,int w,int o,long semente) {
        if(n<3||n>100) throw new IllegalArgumentException("Use 3 <= n <= 100.");
        int capacidade=n*n-1;
        if(p<0||w<0||o<0||p>capacidade||w>capacidade-p||o>capacidade-p)
            throw new IllegalArgumentException("Quantidades incompatíveis com a capacidade do mapa.");
        boolean[] pocos=new boolean[n*n],wumpus=new boolean[n*n],ouro=new boolean[n*n];
        Random r=new Random(semente);
        List<Integer> casas=new ArrayList<>();
        for(int i=1;i<n*n;i++) casas.add(i);
        Collections.shuffle(casas,r);
        for(int k=0;k<p;k++) pocos[casas.get(k)]=true;
        List<Integer> livres=new ArrayList<>(casas.subList(p,casas.size()));
        Collections.shuffle(livres,r);
        for(int k=0;k<w;k++) wumpus[livres.get(k)]=true;
        Collections.shuffle(livres,r);
        for(int k=0;k<o;k++) ouro[livres.get(k)]=true;
        return new Ambiente(n,pocos,wumpus,ouro);
    }

    /** Só o desenho experimental consulta o mapa real para assegurar uma missão factível. */
    public static boolean temOuroAlcancavelSemPerigo(Ambiente a) {
        boolean[] vistos=new boolean[a.n*a.n];int[] fila=new int[vistos.length];
        int inicio=0,fim=0;fila[fim++]=0;vistos[0]=true;
        while(inicio<fim) {
            int i=fila[inicio++];
            if(a.ouro(i)) return true;
            for(int d=0;d<4;d++) {
                Acao ac=Acao.mover(d);int l=i/a.n+ac.dl,c=i%a.n+ac.dc;
                if(a.dentro(l,c)) {
                    int v=a.id(l,c);
                    if(!vistos[v]&&!a.poco(v)&&!a.wumpus(v)) {vistos[v]=true;fila[fim++]=v;}
                }
            }
        }
        return false;
    }
}
