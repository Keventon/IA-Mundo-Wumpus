package pdwumpus;
public final class Main {
    public static void main(String[] args) throws Exception {
        Parametros p=new Parametros(args);Ambiente a=p.ambiente();
        System.out.println("PD ETAPA 3 - AGENTE v2 COM MEMÓRIA E PLANEJAMENTO");a.mostrar();
        Resultado r=Simulador.executar(a,new AgenteV2(a.n,p.longo("agente",42)),
                p.inteiro("limite",10*a.n*a.n),System.out::println);
        System.out.println(r);
    }
}
