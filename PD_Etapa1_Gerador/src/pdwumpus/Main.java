package pdwumpus;
import java.nio.file.*;
public final class Main {
    public static void main(String[] args) throws Exception {
        Parametros p=new Parametros(args);Ambiente a=p.ambiente();
        System.out.println("PD ETAPA 1 - GERADOR ALEATÓRIO");a.mostrar();
        Path destino=Paths.get(p.texto("saida","resultados/ambiente.csv"));
        a.salvar(destino);System.out.println("Mapa e percepções salvos em: "+destino.toAbsolutePath());
    }
}
