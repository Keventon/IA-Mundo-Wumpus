package pdwumpus;

import static pdwumpus.TestesAmbiente.verificar;

public final class TestesAG {
    private TestesAG() { }
    public static void testar() {
        java.util.Set<Long> sementes=new java.util.HashSet<>();
        java.util.Set<Integer> escolhas=new java.util.HashSet<>();
        for(int k=0;k<30;k++) {
            long s=Sementes.misturar(1920000L+k);sementes.add(s);
            escolhas.add(new java.util.Random(s).nextInt(4));
        }
        verificar(sementes.size()==30&&escolhas.size()>1,"Sementes ou primeiras escolhas não dispersas");
        Ambiente a=Gerador.gerar(4,1,1,1,2026);
        AlgoritmoGenetico.Aprendizagem ag=AlgoritmoGenetico.treinar(a,7,10,160,3);
        verificar(ag.evolucao.size()==11,"Faltou geração zero ou geração final");
        double anterior=-Double.MAX_VALUE;
        for(AlgoritmoGenetico.Estatistica e:ag.evolucao) {
            verificar(e.melhor>=anterior,"Elitismo perdeu o melhor fitness");
            verificar(e.pior<=e.media&&e.media<=e.melhor,"Estatística populacional inválida");anterior=e.melhor;
        }
        for(int g:ag.genes) verificar(g>=0&&g<=10,"Gene fora dos limites");
        AlgoritmoGenetico.Aprendizagem b=AlgoritmoGenetico.treinar(a,7,10,160,3);
        verificar(java.util.Arrays.equals(ag.genes,b.genes)&&ag.fitness==b.fitness,"Treinamento não reproduzível");
        System.out.println("OK: AG, limites dos genes, estatísticas, elitismo e reprodução por semente.");
    }
}
