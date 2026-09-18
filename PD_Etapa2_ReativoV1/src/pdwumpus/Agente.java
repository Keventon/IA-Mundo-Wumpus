package pdwumpus;

/** Agentes recebem só a observação atual; a memória é responsabilidade de cada versão. */
public interface Agente {
    Acao decidir(Observacao observacao);
}
