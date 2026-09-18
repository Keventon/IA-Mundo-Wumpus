package pdwumpus;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
public final class Main {
    public static void main(String[] args) throws Exception {
        Parametros p=new Parametros(args);
        if(p.tem("ajustes")) {AjustesFitness.executar(Paths.get(p.texto("saida","resultados_ajustes")));return;}
        Ambiente a=p.ambiente();
        System.out.println("PD ETAPA 4 - AGENTE v3 APRENDIZAGEM POR AG");a.mostrar();
        int limite=p.inteiro("limite",10*a.n*a.n),geracoes=p.inteiro("geracoes",1000);
        AlgoritmoGenetico.Aprendizagem ag=AlgoritmoGenetico.treinar(a,p.longo("agente",42),geracoes,limite,p.inteiro("fitness",3));
        Path pasta=Paths.get(p.texto("saida","resultados"));Files.createDirectories(pasta);
        a.salvar(pasta.resolve("ambiente.csv"));
        try(PrintWriter out=new PrintWriter(Files.newBufferedWriter(pasta.resolve("evolucao.csv"),StandardCharsets.UTF_8))) {
            out.println("geracao,melhor,pior,media");
            for(AlgoritmoGenetico.Estatistica e:ag.evolucao)
                out.printf(Locale.US,"%d,%.4f,%.4f,%.4f%n",e.geracao,e.melhor,e.pior,e.media);
        }
        double[] xs=new double[ag.evolucao.size()];double[][] ys=new double[3][xs.length];
        for(int i=0;i<xs.length;i++) {AlgoritmoGenetico.Estatistica e=ag.evolucao.get(i);
            xs[i]=e.geracao;ys[0][i]=e.melhor;ys[1][i]=e.pior;ys[2][i]=e.media;}
        Graficos.linhas(pasta.resolve("fitness.png"),"Evolução do algoritmo genético","Geração","Fitness",xs,ys,
                new String[]{"Melhor","Pior","Média"});
        System.out.println("Genes aprendidos: "+Arrays.toString(ag.genes)+" | fitness="+ag.fitness);
        Resultado r=Simulador.executar(a,new AgenteV3(a.n,p.longo("teste",1042),ag.genes),limite,System.out::println);
        System.out.println("Teste com semente distinta: "+r);
        try(PrintWriter out=new PrintWriter(Files.newBufferedWriter(pasta.resolve("aprendizagem.txt"),StandardCharsets.UTF_8))) {
            out.println("População=50; gerações="+geracoes+"; cruzamento=85%; mutação por gene=5%; fitness="+p.inteiro("fitness",3));
            out.println("Semente treino="+p.longo("agente",42)+"; teste="+p.longo("teste",1042));
            out.println("Genes="+Arrays.toString(ag.genes)+"; fitness treino="+ag.fitness+"; avaliações="+ag.avaliacoes);
            out.println(r);
        }
        System.out.println("Resultados em "+pasta.toAbsolutePath());
    }
}
