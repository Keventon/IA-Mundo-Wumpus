# Correspondência com o enunciado

| Exigência do PDF | Implementação ou evidência |
|---|---|
| Matriz quadrada n >= 3 e quantidades configuráveis | Gerador e argumentos --n, --pocos, --wumpus, --ouros |
| Objetos fora da origem e poço exclusivo | Validação de capacidade e camadas booleanas |
| Posicionar percepções | Brisa e fedor ortogonais, brilho local; impressão e CSV |
| v1 sem memória, regras e escolha aleatória | AgenteV1 e REGRAS_V1.md |
| Uma flecha | Simulador mantém o recurso e valida todos os disparos |
| v2 com memória e escolha mais inteligente | AgenteV2, descarte de perigos pela ausência de sinais e BFS |
| Classificar e justificar v2 | AGENTE_V2.md |
| v3 por algoritmo genético | AlgoritmoGenetico, AgenteV3 e ALGORITMO_GENETICO.md |
| Codificação, seleção, cruzamento, mutação, fitness | Seis genes inteiros; torneio; um ponto; substituição; F3 |
| Registrar especificação e ajustes do fitness | FITNESS.md e piloto reproduzível resultados_ajustes |
| Coletar e voltar à origem | PEGAR e SAIR; SAIR com ouro em [0][0] é a única vitória |
| Pontuação específica do PD | Ação -1; morte -1000; ouro e Wumpus morto +1000 |
| n = 4, 5, 10, 15, 20; configurações fixas | configuracoes.csv e ambientes/mapa_n.csv |
| 30 execuções de v1, v2 e v3 por tamanho | execucoes.csv com 450 linhas de dados |
| AG: população 50; 1000 gerações; cruzamento 85%; mutação 5% | Constantes e configuração exportada |
| Operadores e versão final de fitness fixos | Mesmos métodos e F3 em toda a Etapa 5 |
| Gráficos v1 e v2 | Pontuação média e taxa de vitória, também comparadas com v3 |
| v3: gerações x fitness melhor, pior e média | CSV por ensaio e curvas agregadas para cada tamanho |
| Tabela de cada execução | execucoes.csv e tabela_pontuacoes.csv em formato largo |

O desenho da tabela do slide 28 mostra uma linha final 20, mas o texto do slide 26 exige 30 execuções. A implementação segue o requisito explícito de 30.

A tabela de regras de uma aula externa não foi fornecida neste PDF como tabela completa. A tabela implementada é a proposta documentada neste pacote.
