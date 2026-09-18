package pdwumpus;

import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import javax.imageio.ImageIO;

/** Reexecuta cada partida dos CSVs e verifica curvas, contagens e recompensas. */
public final class ConferirResultados {
    public static void main(String[] args) throws Exception {
        Path pasta=Paths.get(args.length==0?"resultados":args[0]);
        List<String> linhas=Files.readAllLines(pasta.resolve("execucoes.csv"),StandardCharsets.UTF_8);
        exigir(linhas.size()==451,"Esperadas 450 partidas finais");
        Set<String> chaves=new HashSet<>();Map<String,Integer> contagens=new HashMap<>();
        Map<Integer,Ambiente> mapas=new HashMap<>();
        for(int n:new int[]{4,5,10,15,20}) mapas.put(n,Ambiente.carregar(pasta.resolve("ambientes/mapa_"+n+".csv")));
        for(int k=1;k<linhas.size();k++) {
            String[] s=linhas.get(k).split(",",-1);exigir(s.length==15,"CSV inválido");
            int n=Integer.parseInt(s[0]),exec=Integer.parseInt(s[2]);String v=s[1];
            exigir(exec>=1&&exec<=30&&mapas.containsKey(n),"Configuração inválida");
            exigir(chaves.add(n+"_"+v+"_"+exec),"Execução duplicada");
            String grupo=n+"_"+v;contagens.put(grupo,contagens.getOrDefault(grupo,0)+1);
            long treino=Long.parseLong(s[3]),teste=Long.parseLong(s[4]);int[] genes=null;
            Agente agente;
            if(v.equals("v1")) agente=new AgenteV1(teste);
            else if(v.equals("v2")) agente=new AgenteV2(n,teste);
            else {
                exigir(v.equals("v3"),"Versão inválida");
                String[] tokens=s[13].substring(1,s[13].length()-1).split(" ");
                genes=new int[tokens.length];for(int i=0;i<genes.length;i++) genes[i]=Integer.parseInt(tokens[i]);
                agente=new AgenteV3(n,teste,genes);
                exigir(treino!=teste,"Teste reutilizou semente de treino");
            }
            Resultado r=Simulador.executar(mapas.get(n),agente,10*n*n,null);
            exigir(r.acoes==Integer.parseInt(s[5])&&r.pontuacao==Integer.parseInt(s[6])
                    &&r.venceu()==Boolean.parseBoolean(s[7])&&r.fim.toString().equals(s[8])
                    &&r.ouros==Integer.parseInt(s[9])&&r.wumpusMortos==Integer.parseInt(s[10])
                    &&r.casasVisitadas==Integer.parseInt(s[11]),"Replay não reproduziu a partida "+k);
            exigir(r.pontuacao==1000*r.ouros+1000*r.wumpusMortos-(r.vivo?0:1000)-r.acoes,"Pontuação fora do PD");
            exigir(!r.venceu()||(r.ouros>0&&r.linha==0&&r.coluna==0),"Vitória fora da origem");
            if(genes!=null) {
                Resultado rt=Simulador.executar(mapas.get(n),new AgenteV3(n,treino,genes),10*n*n,null);
                exigir(AlgoritmoGenetico.fitness(rt,3)==Double.parseDouble(s[12]),"Fitness final não reproduzível");
            }
        }
        exigir(contagens.size()==15,"Esperadas 15 combinações");
        for(int c:contagens.values()) exigir(c==30,"Esperadas 30 partidas por combinação");
        for(int n:new int[]{4,5,10,15,20}) {
            List<String> evol=Files.readAllLines(pasta.resolve("evolucao/evolucao_n"+n+".csv"),StandardCharsets.UTF_8);
            exigir(evol.size()==30031,"Esperados 30*1001 registros de geração");
            double[] anterior=new double[30];Arrays.fill(anterior,-Double.MAX_VALUE);
            int[] geracao=new int[30];Arrays.fill(geracao,-1);
            for(int k=1;k<evol.size();k++) {
                String[] s=evol.get(k).split(",");int e=Integer.parseInt(s[0])-1,g=Integer.parseInt(s[1]);
                double melhor=Double.parseDouble(s[2]),pior=Double.parseDouble(s[3]),media=Double.parseDouble(s[4]);
                exigir(g==geracao[e]+1&&melhor>=anterior[e],"Geração ausente ou elitismo incorreto");
                exigir(pior<=media+0.0001&&media<=melhor+0.0001,"Estatísticas inválidas");
                geracao[e]=g;anterior[e]=melhor;
            }
            for(int g:geracao) exigir(g==1000,"Treinamento incompleto");
            exigir(ImageIO.read(pasta.resolve("graficos/fitness_n"+n+".png").toFile())!=null,"PNG inválido");
        }
        exigir(Files.readAllLines(pasta.resolve("tabela_pontuacoes.csv")).size()==31,"Tabela larga deve ter 30 linhas");
        System.out.println("OK: 450 replays idênticos; 15 combinações x 30 partidas; pontuação e vitória corretas.");
        System.out.println("OK: fitness final reproduzido; 150 treinamentos com 1001 populações; elitismo e gráficos válidos.");
    }
    private static void exigir(boolean b,String mensagem) {if(!b) throw new AssertionError(mensagem);}
}
