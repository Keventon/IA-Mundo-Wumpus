package pdwumpus;

/** Interface sensorial. Não contém objetos de outras casas nem referência ao Ambiente. */
public final class Observacao {
    public final int n,linha,coluna;
    public final boolean brisa,fedor,brilho,impacto,grito,flecha,ouro;
    public Observacao(int n,int l,int c,boolean b,boolean f,boolean brilho,
            boolean impacto,boolean grito,boolean flecha,boolean ouro) {
        this.n=n;linha=l;coluna=c;brisa=b;fedor=f;this.brilho=brilho;
        this.impacto=impacto;this.grito=grito;this.flecha=flecha;this.ouro=ouro;
    }
    public int id() {return linha*n+coluna;}
    public String toString() {
        return "["+linha+"]["+coluna+"] brisa="+brisa+" fedor="+fedor+
                " brilho="+brilho+" impacto="+impacto+" grito="+grito;
    }
}
