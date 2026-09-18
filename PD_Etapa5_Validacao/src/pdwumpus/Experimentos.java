package pdwumpus;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.*;

/** Produz resultados reais, sementes, tabelas largas e curvas das 30 aprendizagens por mapa. */
public final class Experimentos {
    private Experimentos() { }
    private static PrintWriter escritor(Path p) throws IOException {
        Files.createDirectories(p.toAbsolutePath().getParent());
        return new PrintWriter(Files.newBufferedWriter(p,StandardCharsets.UTF_8));
    }
    public static void executar(Path pasta,boolean rapido) throws IOException {
        int[] tamanhos=rapido?new int[]{4,5}:new int[]{4,5,10,15,20};
        int repeticoes=rapido?2:30,geracoes=rapido?20:AlgoritmoGenetico.GERACOES;
        double[][] medias=new double[3][tamanhos.length],taxas=new double[3][tamanhos.length];
        double[][] desvios=new double[3][tamanhos.length];
        int[][] pontos=new int[repeticoes][tamanhos.length*3],vitorias=new int[repeticoes][tamanhos.length*3];
        try(PrintWriter exec=escritor(pasta.resolve("execucoes.csv"));
                PrintWriter config=escritor(pasta.resolve("configuracoes.csv"))) {
            exec.println("n,versao,execucao,semente_agente,semente_teste,acoes,pontuacao,vitoria,fim,ouros,wumpus_mortos,casas_visitadas,fitness_treino,genes,avaliacoes_distintas");
            config.println("n,pocos,wumpus,ouros,semente_mapa,limite_acoes,repeticoes,populacao,geracoes,cruzamento,mutacao,fitness");
            for(int ti=0;ti<tamanhos.length;ti++) {
                int n=tamanhos[ti],p=Math.max(1,n*n/10),limite=10*n*n;
                long seed=2026000L+n;Ambiente a=Gerador.gerar(n,p,1,1,seed);
                // Apenas para selecionar mapas factíveis; nenhum agente recebe o caminho dessa checagem.
                while(!Gerador.temOuroAlcancavelSemPerigo(a)) a=Gerador.gerar(n,p,1,1,++seed);
                a.salvar(pasta.resolve("ambientes/mapa_"+n+".csv"));
                config.printf(Locale.US,"%d,%d,1,1,%d,%d,%d,50,%d,0.85,0.05,3%n",n,p,seed,limite,repeticoes,geracoes);
                double[][] curvas=new double[3][geracoes+1];
                final Ambiente mapaFixo=a;
                ExecutorService trabalhadores=Executors.newFixedThreadPool(Math.min(4,Runtime.getRuntime().availableProcessors()));
                List<Future<AlgoritmoGenetico.Aprendizagem>> treinos=new ArrayList<>();
                for(int k=0;k<repeticoes;k++) {
                    final long sementeTreino=Sementes.misturar(900000L+n*1000L+k);
                    treinos.add(trabalhadores.submit(()->AlgoritmoGenetico.treinar(mapaFixo,sementeTreino,geracoes,limite,3)));
                }
                try(PrintWriter evol=escritor(pasta.resolve("evolucao/evolucao_n"+n+".csv"))) {
                    evol.println("execucao,geracao,melhor,pior,media");
                    for(int k=0;k<repeticoes;k++) {
                        long treino=Sementes.misturar(900000L+n*1000L+k),test=Sementes.misturar(1900000L+n*1000L+k);
                        AlgoritmoGenetico.Aprendizagem ag=aguardar(treinos.get(k));
                        for(AlgoritmoGenetico.Estatistica e:ag.evolucao) {
                            evol.printf(Locale.US,"%d,%d,%.4f,%.4f,%.4f%n",k+1,e.geracao,e.melhor,e.pior,e.media);
                            curvas[0][e.geracao]+=e.melhor/repeticoes;
                            curvas[1][e.geracao]+=e.pior/repeticoes;
                            curvas[2][e.geracao]+=e.media/repeticoes;
                        }
                        for(int v=0;v<3;v++) {
                            Agente agente=v==0?new AgenteV1(test):v==1?new AgenteV2(n,test):new AgenteV3(n,test,ag.genes);
                            Resultado r=Simulador.executar(a,agente,limite,null);
                            pontos[k][ti*3+v]=r.pontuacao;vitorias[k][ti*3+v]=r.venceu()?1:0;
                            medias[v][ti]+=r.pontuacao/(double)repeticoes;
                            taxas[v][ti]+=r.venceu()?100.0/repeticoes:0;
                            String genes=v==2?Arrays.toString(ag.genes).replace(", "," "):"";
                            exec.printf(Locale.US,"%d,v%d,%d,%d,%d,%d,%d,%b,%s,%d,%d,%d,%s,%s,%s%n",
                                    n,v+1,k+1,v==2?treino:test,test,r.acoes,r.pontuacao,r.venceu(),r.fim,
                                    r.ouros,r.wumpusMortos,r.casasVisitadas,v==2?""+ag.fitness:"",genes,v==2?""+ag.avaliacoes:"");
                        }
                        exec.flush();evol.flush();
                        System.out.printf("n=%d execução %d/%d concluída (AG: %d avaliações distintas)%n",n,k+1,repeticoes,ag.avaliacoes);
                    }
                } finally {trabalhadores.shutdownNow();}
                double[] xs=new double[geracoes+1];for(int k=0;k<xs.length;k++) xs[k]=k;
                Graficos.linhas(pasta.resolve("graficos/fitness_n"+n+".png"),"Evolução média do AG em "+n+" x "+n,
                        "Geração","Fitness",xs,curvas,new String[]{"Melhor (média dos ensaios)","Pior (média dos ensaios)","Média populacional"});
                try(PrintWriter media=escritor(pasta.resolve("evolucao/media_n"+n+".csv"))) {
                    media.println("geracao,melhor_medio,pior_medio,media_populacional");
                    for(int k=0;k<xs.length;k++) media.printf(Locale.US,"%d,%.4f,%.4f,%.4f%n",k,curvas[0][k],curvas[1][k],curvas[2][k]);
                }
                for(int v=0;v<3;v++) {
                    double soma=0;
                    for(int k=0;k<repeticoes;k++) soma+=Math.pow(pontos[k][ti*3+v]-medias[v][ti],2);
                    desvios[v][ti]=Math.sqrt(soma/(repeticoes-1));
                }
            }
        }
        tabelaLarga(pasta.resolve("tabela_pontuacoes.csv"),tamanhos,pontos);
        tabelaLarga(pasta.resolve("tabela_vitorias.csv"),tamanhos,vitorias);
        double[] xs=Arrays.stream(tamanhos).asDoubleStream().toArray();
        Graficos.linhas(pasta.resolve("graficos/pontuacao_media.png"),"Pontuação média no teste por tamanho do mapa",
                "Tamanho n","Pontos",xs,medias,new String[]{"v1 sem memória","v2 com memória","v3 pesos aprendidos"});
        Graficos.linhas(pasta.resolve("graficos/taxa_vitoria.png"),"Taxa de vitória no teste por tamanho do mapa",
                "Tamanho n","Vitórias (%)",xs,taxas,new String[]{"v1 sem memória","v2 com memória","v3 pesos aprendidos"});
        try(PrintWriter sum=escritor(pasta.resolve("resumo.csv"));PrintWriter md=escritor(pasta.resolve("RESULTADOS.md"))) {
            sum.println("n,versao,execucoes,pontuacao_media,desvio_padrao_amostral,vitorias_percentual");
            md.println("# Resultados medidos\n\n"+(rapido?"TESTE RÁPIDO, NÃO É O PROTOCOLO FINAL.":"Protocolo completo: 450 partidas de teste, 150 aprendizagens independentes do AG.")+"\n");
            md.println("| n | Agente | Execuções | Média dos pontos | Desvio amostral | Vitórias |\n|---|---|---|---|---|---|");
            for(int ti=0;ti<tamanhos.length;ti++) for(int v=0;v<3;v++) {
                sum.printf(Locale.US,"%d,v%d,%d,%.4f,%.4f,%.4f%n",tamanhos[ti],v+1,repeticoes,medias[v][ti],desvios[v][ti],taxas[v][ti]);
                md.printf(Locale.US,"| %d | v%d | %d | %.2f | %.2f | %.1f%% |%n",tamanhos[ti],v+1,repeticoes,medias[v][ti],desvios[v][ti],taxas[v][ti]);
            }
            md.println("\n## Interpretação e limites\n\nVitória exige coleta e saída na origem; pontuação e vitória são medidas distintas. "
                    +"Um agente pode matar um Wumpus e ainda perder a missão. O fitness não é a pontuação oficial. "
                    +"Os arquivos preservam também as mortes, os limites e as saídas sem ouro, sem excluir fracassos.\n\n"
                    +"Há somente um mapa fixo por tamanho, compartilhado pelas três versões. "
                    +"O AG é treinado nesse mesmo mapa, mas o teste usa outra semente de desempate. "
                    +"Portanto, o teste verifica estabilidade no ambiente treinado, não generalização para mapas inéditos. "
                    +"Trinta execuções não equivalem a trinta ambientes. Tempos de treinamento não são custo de ações da partida.\n\n"
                    +"configuracoes.csv registra o protocolo. execucoes.csv contém cada partida; "
                    +"tabela_pontuacoes.csv reproduz o formato execução x tamanho x versão. "
                    +"evolucao/ registra 0 até a última geração. graficos/ contém PNGs gerados a partir desses números. "
                    +"Consulte docs/METODOLOGIA.md antes de interpretar comparações como evidência de superioridade geral.");
        }
    }
    private static void tabelaLarga(Path p,int[] ns,int[][] valores) throws IOException {
        try(PrintWriter out=escritor(p)) {
            out.print("execucao");for(int n:ns) for(int v=1;v<=3;v++) out.print(",n"+n+"_v"+v);out.println();
            for(int k=0;k<valores.length;k++) {out.print(k+1);for(int x:valores[k]) out.print(","+x);out.println();}
        }
    }
    private static AlgoritmoGenetico.Aprendizagem aguardar(Future<AlgoritmoGenetico.Aprendizagem> futuro) throws IOException {
        try {return futuro.get();}
        catch(InterruptedException e) {Thread.currentThread().interrupt();throw new IOException("Experimento interrompido.",e);}
        catch(ExecutionException e) {throw new IOException("Treinamento falhou.",e.getCause());}
    }
}
