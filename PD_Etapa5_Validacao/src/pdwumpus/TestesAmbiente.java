package pdwumpus;

/** Testes sem JUnit: executáveis no terminal e no NetBeans, sem dependências. */
public final class TestesAmbiente {
    private TestesAmbiente() { }
    public static void verificar(boolean condicao,String mensagem) {
        if(!condicao) throw new AssertionError(mensagem);
    }
    public static void testar() {
        for(int n:new int[]{3,4,5,10,15,20}) for(int seed=0;seed<50;seed++) {
            int p=n*n/10;Ambiente a=Gerador.gerar(n,p,2,2,seed),b=Gerador.gerar(n,p,2,2,seed);
            int cp=0,cw=0,co=0;
            verificar(!a.poco(0)&&!a.wumpus(0)&&!a.ouro(0),"Origem ocupada");
            for(int i=0;i<n*n;i++) {
                cp+=a.poco(i)?1:0;cw+=a.wumpus(i)?1:0;co+=a.ouro(i)?1:0;
                verificar(!(a.poco(i)&&(a.wumpus(i)||a.ouro(i))),"Sobreposição inválida");
                verificar(a.poco(i)==b.poco(i)&&a.wumpus(i)==b.wumpus(i)&&a.ouro(i)==b.ouro(i),"Semente não reproduziu mapa");
            }
            verificar(cp==p&&cw==2&&co==2,"Contagem de objetos incorreta");
        }
        boolean[] p=new boolean[9],w=new boolean[9],o=new boolean[9];p[4]=true;w[8]=true;o[1]=true;
        Ambiente a=new Ambiente(3,p,w,o);
        verificar(a.vizinho(1,true)&&!a.vizinho(0,true),"Brisa ou diagonal incorreta");
        verificar(a.vizinho(5,false)&&!a.vizinho(4,false),"Fedor ou diagonal incorreta");
        try {Gerador.gerar(2,0,0,0,0);throw new AssertionError("Aceitou n < 3");} catch(IllegalArgumentException ok) { }
        try {Gerador.gerar(3,8,1,0,0);throw new AssertionError("Aceitou objetos demais");} catch(IllegalArgumentException ok) { }
        System.out.println("OK: gerador, contagens, restrições, sementes, limites e percepções.");
    }
}
