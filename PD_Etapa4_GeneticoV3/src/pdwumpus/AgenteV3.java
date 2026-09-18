package pdwumpus;

/** Política baseada em modelo cujos pesos são aprendidos pelo algoritmo genético. */
public final class AgenteV3 extends AgenteV2 {
    public AgenteV3(int n,long semente,int[] cromossomo) {
        super(n,semente,validar(cromossomo));
    }
    private static int[] validar(int[] genes) {
        if(genes.length!=6) throw new IllegalArgumentException("Cromossomo deve ter seis genes.");
        for(int g:genes) if(g<0||g>10) throw new IllegalArgumentException("Genes entre 0 e 10 exigidos.");
        return genes;
    }
}
