package pdwumpus;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

/** Objetos em camadas: ouro e Wumpus podem ocupar a mesma casa, mas poço não. */
public final class Ambiente {
    public final int n;
    private final boolean[] pocos, wumpus, ouro;

    public Ambiente(int n, boolean[] p, boolean[] w, boolean[] o) {
        if (n < 3 || p.length != n*n || w.length != n*n || o.length != n*n)
            throw new IllegalArgumentException("Matriz quadrada com n >= 3 exigida.");
        this.n = n;
        pocos = p.clone(); wumpus = w.clone(); ouro = o.clone();
        if (p[0] || w[0] || o[0])
            throw new IllegalArgumentException("A casa [0][0] deve estar vazia.");
        for (int i = 0; i < n*n; i++)
            if (p[i] && (w[i] || o[i]))
                throw new IllegalArgumentException("Poço não pode coexistir com outros objetos.");
    }

    public boolean dentro(int l, int c) { return l >= 0 && l < n && c >= 0 && c < n; }
    public int id(int l, int c) { return l*n+c; }
    public boolean poco(int i) { return pocos[i]; }
    public boolean wumpus(int i) { return wumpus[i]; }
    public boolean ouro(int i) { return ouro[i]; }
    public void matar(int i) { wumpus[i] = false; }
    public void coletar(int i) { ouro[i] = false; }
    public Ambiente copia() { return new Ambiente(n, pocos, wumpus, ouro); }

    public boolean vizinho(int i, boolean procurarPoco) {
        int l = i/n, c = i%n;
        for (int d=0; d<4; d++) {
            Acao a = Acao.mover(d);
            int nl=l+a.dl, nc=c+a.dc;
            if (dentro(nl,nc) && (procurarPoco ? pocos[id(nl,nc)] : wumpus[id(nl,nc)]))
                return true;
        }
        return false;
    }

    public void mostrar() {
        System.out.println("Objetos: P=poço, W=Wumpus, O=ouro, .=vazio; início=[0][0]");
        System.out.print("     ");
        for (int c=0;c<n;c++) System.out.printf("%5d",c);
        System.out.println();
        for (int l=0;l<n;l++) {
            System.out.printf("%4d ",l);
            for (int c=0;c<n;c++) {
                int i=id(l,c);
                String s=pocos[i]?"P":(wumpus[i]?"W":"")+(ouro[i]?"O":"");
                System.out.printf("%5s",s.isEmpty()?".":s);
            }
            System.out.println();
        }
        System.out.println("Percepções: b=brisa, f=fedor, B=brilho (vizinhança sem diagonais)");
        for (int l=0;l<n;l++) {
            System.out.printf("%4d ",l);
            for (int c=0;c<n;c++) {
                int i=id(l,c);
                String s=(vizinho(i,true)?"b":"")+(vizinho(i,false)?"f":"")+(ouro[i]?"B":"");
                System.out.printf("%5s",s.isEmpty()?".":s);
            }
            System.out.println();
        }
    }

    public void salvar(Path arquivo) throws IOException {
        Files.createDirectories(arquivo.toAbsolutePath().getParent());
        try (PrintWriter out=new PrintWriter(Files.newBufferedWriter(arquivo,StandardCharsets.UTF_8))) {
            out.println("linha,coluna,poco,wumpus,ouro,brisa,fedor,brilho");
            for (int i=0;i<n*n;i++) out.printf(Locale.US,"%d,%d,%b,%b,%b,%b,%b,%b%n",
                    i/n,i%n,pocos[i],wumpus[i],ouro[i],vizinho(i,true),vizinho(i,false),ouro[i]);
        }
    }

    public static Ambiente carregar(Path arquivo) throws IOException {
        List<String> linhas=Files.readAllLines(arquivo,StandardCharsets.UTF_8);
        int total=linhas.size()-1, n=(int)Math.sqrt(total);
        if (n*n!=total) throw new IOException("CSV deve conter n*n casas.");
        boolean[] p=new boolean[total],w=new boolean[total],o=new boolean[total],vistos=new boolean[total];
        for(int k=1;k<linhas.size();k++) {
            String[] s=linhas.get(k).split(",");
            if(s.length<5) throw new IOException("Linha CSV inválida.");
            int l=Integer.parseInt(s[0]),c=Integer.parseInt(s[1]);
            if(l<0||l>=n||c<0||c>=n||vistos[l*n+c]) throw new IOException("Coordenada inválida ou repetida.");
            int i=l*n+c; vistos[i]=true;
            p[i]=Boolean.parseBoolean(s[2]);w[i]=Boolean.parseBoolean(s[3]);o[i]=Boolean.parseBoolean(s[4]);
        }
        return new Ambiente(n,p,w,o);
    }
}
