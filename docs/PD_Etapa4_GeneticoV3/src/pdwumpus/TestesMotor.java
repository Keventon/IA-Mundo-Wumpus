package pdwumpus;

import static pdwumpus.TestesAmbiente.verificar;

public final class TestesMotor {
    private TestesMotor() { }
    private static Agente roteiro(final Acao... a) {
        return new Agente() {int k;public Acao decidir(Observacao o) {return a[k++];}};
    }
    public static void testar() {
        boolean[] p=new boolean[9],w=new boolean[9],o=new boolean[9];o[1]=true;
        Ambiente a=new Ambiente(3,p,w,o);
        Resultado r=Simulador.executar(a,roteiro(Acao.LESTE,Acao.PEGAR,Acao.OESTE,Acao.SAIR),10,null);
        verificar(r.venceu()&&r.pontuacao==996&&r.acoes==4,"Vitória ou pontuação incorreta");
        verificar(a.ouro(1),"Simulador alterou o ambiente original");
        r=Simulador.executar(a,roteiro(Acao.NORTE,Acao.LESTE,Acao.PEGAR,Acao.OESTE,Acao.SAIR),10,null);
        verificar(r.pontuacao==995&&r.venceu(),"Impacto não custou uma ação");
        p[1]=true;o[1]=false;
        r=Simulador.executar(new Ambiente(3,p,w,o),roteiro(Acao.LESTE),10,null);
        verificar(!r.vivo&&r.pontuacao==-1001&&r.fim==Resultado.Fim.POCO,"Morte em poço incorreta");
        p[1]=false;w[1]=true;
        r=Simulador.executar(new Ambiente(3,p,w,o),roteiro(Acao.LESTE),10,null);
        verificar(r.fim==Resultado.Fim.WUMPUS&&r.pontuacao==-1001,"Wumpus deve matar sem combate aleatório");
        o[2]=true;
        r=Simulador.executar(new Ambiente(3,p,w,o),roteiro(Acao.ATIRAR_LESTE,Acao.LESTE,Acao.LESTE,
                Acao.PEGAR,Acao.OESTE,Acao.OESTE,Acao.SAIR),10,null);
        verificar(r.venceu()&&r.wumpusMortos==1&&r.pontuacao==1993,"Flecha, ouro ou recompensa incorreta");
        w[1]=false;w[2]=true;o[2]=false;
        r=Simulador.executar(new Ambiente(3,p,w,o),roteiro(Acao.ATIRAR_LESTE),1,null);
        verificar(r.wumpusMortos==0,"Flecha adjacente atingiu casa distante");
        w[1]=true;
        r=Simulador.executar(new Ambiente(3,p,w,o),roteiro(Acao.ATIRAR_LESTE,Acao.LESTE,
                Acao.ATIRAR_LESTE,Acao.LESTE),4,null);
        verificar(r.wumpusMortos==1&&r.fim==Resultado.Fim.WUMPUS,"Agente usou mais de uma flecha");
        System.out.println("OK: motor, vitória, pontuação, impacto, mortes, flecha única e mapa imutável.");
    }
}
