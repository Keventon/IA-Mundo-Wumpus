package pdwumpus;

import java.util.*;

/** Argumentos --chave=valor também podem ser configurados em Properties > Run no NetBeans. */
public final class Parametros {
    private final Map<String,String> dados=new HashMap<>();
    public Parametros(String[] args) {
        for(String s:args) {
            if(!s.startsWith("--")) throw new IllegalArgumentException("Use --chave=valor: "+s);
            String[] partes=s.substring(2).split("=",2);
            dados.put(partes[0],partes.length==2?partes[1]:"true");
        }
    }
    public int inteiro(String chave,int padrao) {return Integer.parseInt(dados.getOrDefault(chave,""+padrao));}
    public long longo(String chave,long padrao) {return Long.parseLong(dados.getOrDefault(chave,""+padrao));}
    public String texto(String chave,String padrao) {return dados.getOrDefault(chave,padrao);}
    public boolean tem(String chave) {return dados.containsKey(chave);}
    public Ambiente ambiente() throws java.io.IOException {
        if(tem("mapa")) return Ambiente.carregar(java.nio.file.Paths.get(texto("mapa","")));
        int n=inteiro("n",5);
        return Gerador.gerar(n,inteiro("pocos",Math.max(1,n*n/10)),inteiro("wumpus",1),
                inteiro("ouros",1),longo("semente",2026));
    }
}
