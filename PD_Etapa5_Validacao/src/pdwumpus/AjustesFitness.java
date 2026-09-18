package pdwumpus;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

/** Piloto reproduzível das três propostas de fitness, não histórico inventado da equipe. */
public final class AjustesFitness {
    public static void main(String[] args) throws Exception {
        Path pasta=Paths.get(args.length==0?"resultados_ajustes":args[0]);
        executar(pasta);
    }
    public static void executar(Path pasta) throws IOException {
        Files.createDirectories(pasta);long seed=2026005L;
        Ambiente a=Gerador.gerar(5,2,1,1,seed);
        while(!Gerador.temOuroAlcancavelSemPerigo(a)) a=Gerador.gerar(5,2,1,1,++seed);
        a.salvar(pasta.resolve("ambiente.csv"));
        try(PrintWriter out=new PrintWriter(Files.newBufferedWriter(pasta.resolve("comparacao.csv"),StandardCharsets.UTF_8))) {
            out.println("fitness,execucao,semente_mapa,semente_treino,semente_teste,fitness_treino,pontos_teste,vitoria_teste,genes");
            for(int v=1;v<=3;v++) for(int k=0;k<5;k++) {
                long treino=Sementes.misturar(925000L+k),teste=Sementes.misturar(1925000L+k);
                AlgoritmoGenetico.Aprendizagem ag=AlgoritmoGenetico.treinar(a,treino,1000,250,v);
                Resultado r=Simulador.executar(a,new AgenteV3(5,teste,ag.genes),250,null);
                out.printf(Locale.US,"F%d,%d,%d,%d,%d,%.4f,%d,%b,%s%n",v,k+1,seed,treino,teste,
                        ag.fitness,r.pontuacao,r.venceu(),Arrays.toString(ag.genes).replace(", "," "));
                System.out.println("Piloto F"+v+", execução "+(k+1)+": "+r);
            }
        }
    }
}
