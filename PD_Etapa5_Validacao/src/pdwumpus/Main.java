package pdwumpus;
import java.nio.file.*;
public final class Main {
    public static void main(String[] args) throws Exception {
        Parametros p=new Parametros(args);boolean rapido=p.tem("rapido");
        System.out.println(rapido?"TESTE RÁPIDO - NÃO É O RESULTADO FINAL":"PD ETAPA 5 - 450 PARTIDAS E 150 TREINAMENTOS DO AG");
        Path pasta=Paths.get(p.texto("saida",rapido?"resultados_teste":"resultados"));
        Experimentos.executar(pasta,rapido);
        System.out.println("Resultados e gráficos em: "+pasta.toAbsolutePath());
    }
}
