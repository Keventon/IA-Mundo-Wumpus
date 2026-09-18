package pdwumpus;

import static pdwumpus.TestesAmbiente.verificar;

public final class TestesMemoria {
    private TestesMemoria() { }
    public static void testar() {
        for(int seed=0;seed<30;seed++) {
            boolean[] p=new boolean[25],w=new boolean[25],o=new boolean[25];o[24]=true;
            Resultado r=Simulador.executar(new Ambiente(5,p,w,o),new AgenteV2(5,seed),250,null);
            verificar(r.venceu()&&r.linha==0&&r.coluna==0,"Agente não coletou e retornou em ambiente vazio");
        }
        System.out.println("OK: memória, busca em largura, coleta e retorno em 30 sementes.");
    }
}
