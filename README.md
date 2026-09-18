# Mundo de Wumpus - Projeto de Desenvolvimento em Inteligência Artificial

Projeto acadêmico desenvolvido para a disciplina de **Inteligência Artificial do mestrado**, organizado nas cinco etapas do Projeto de Desenvolvimento (PD) apresentado no material **AI 2026**, do professor **Otávio Noura Teixeira**.

O objetivo é implementar e comparar três versões de um agente no Mundo de Wumpus: um agente reativo sem memória, um agente com memória e planejamento e um agente de aprendizagem por algoritmo genético. A evolução é registrada em projetos independentes, permitindo acompanhar o código, as decisões e os resultados de cada etapa.

Este repositório corresponde ao PD de Inteligência Artificial, não às versões didáticas utilizadas na disciplina de Computação Gráfica.

## Tecnologias

- **Linguagem:** Java, compatível com JDK 8 ou superior.
- **IDE:** Apache NetBeans.
- **Tipo de aplicação:** console.
- **Construção:** Apache Ant.
- **Resultados:** tabelas CSV, relatórios Markdown e gráficos PNG gerados com Java2D.

Os projetos não utilizam bibliotecas externas e podem ser abertos e executados separadamente.

## O que é o Mundo de Wumpus?

O Mundo de Wumpus é um ambiente simplificado utilizado para estudar agentes, representação do conhecimento, tomada de decisão e aprendizagem. O mundo é formado por uma matriz de salas, na qual existem poços, ouro e um monstro chamado Wumpus.

O agente inicia na posição `(0,0)` e deve explorar o ambiente, coletar o ouro e retornar à origem sem morrer. Ele não conhece previamente a posição dos objetos: suas decisões são baseadas nas percepções disponíveis e, nas versões posteriores, na memória construída durante a exploração.

| Elemento ou percepção | Significado |
|---|---|
| Poço | Entrar nessa casa provoca a morte do agente. |
| Wumpus vivo | Entrar nessa casa provoca a morte do agente. |
| Ouro | Pode ser coletado pelo agente. |
| Brisa | Existe poço em alguma casa ortogonalmente adjacente. |
| Fedor | Existe Wumpus vivo em alguma casa ortogonalmente adjacente. |
| Brilho | Existe ouro na casa atual. |
| Impacto | Uma movimentação tentou ultrapassar o limite do mapa. |
| Grito | Um Wumpus foi atingido pela flecha. |

O agente pode movimentar-se para norte, sul, leste ou oeste, coletar ouro, disparar sua única flecha e sair pela origem. Movimentações e percepções não ocorrem na diagonal.

Embora o console possa exibir o mapa completo para acompanhamento, o agente **não consulta a localização dos objetos ocultos** para decidir suas ações.

## Objetivos do PD

Conforme o enunciado, a missão principal é sair da casa `(0,0)`, pegar o ouro e voltar à casa `(0,0)`. Além de concluir essa missão, busca-se obter a maior pontuação possível.

O projeto também deve permitir:

- Gerar ambientes aleatórios com tamanhos e quantidades de objetos configuráveis.
- Especificar as regras de um agente reativo e realizar escolhas aleatórias sem memória.
- Acrescentar memória e utilizar o conhecimento adquirido para orientar decisões.
- Projetar a aprendizagem por algoritmo genético, justificando seus componentes e o fitness.
- Comparar as três versões por meio de execuções repetidas, tabelas e gráficos.

## Organização do repositório

As pastas abaixo devem ficar na raiz do repositório, junto deste `README.md`:

```text
.
├── README.md
├── VALIDACAO.md
├── docs/
├── PD_Etapa1_Gerador/
├── PD_Etapa2_ReativoV1/
├── PD_Etapa3_MemoriaV2/
├── PD_Etapa4_GeneticoV3/
└── PD_Etapa5_Validacao/
```

Cada etapa contém seu próprio código, configuração do NetBeans, README e documentação. Nenhuma depende da abertura ou compilação das outras etapas.

## Etapa 1 - Gerador aleatório de ambientes

**Projeto:** [PD_Etapa1_Gerador](PD_Etapa1_Gerador/README.md)

Esta etapa implementa o ambiente utilizado pelas versões posteriores dos agentes.

Requisitos do enunciado:

- Matriz quadrada de ordem `n`, com `n >= 3` e índices de `0` a `n-1`.
- Quantidades de poços, Wumpus e ouro maiores ou iguais a zero, definidas pelo usuário ou por uma regra automática.
- Distribuição aleatória dos objetos e definição das percepções correspondentes.
- Casa `(0,0)` sem objetos, pois é a posição inicial do agente.
- Casas com poços sem ouro ou Wumpus.

Na implementação, o ambiente pode ser reproduzido por uma semente e exportado em CSV. Ouro e Wumpus podem compartilhar uma casa fora da origem, mas nenhum deles pode compartilhar uma casa com poço.

## Etapa 2 - Agente reativo, versão 1

**Projeto:** [PD_Etapa2_ReativoV1](PD_Etapa2_ReativoV1/README.md)

O agente utiliza regras no formato `Se percepções, então ação`, integradas ao gerador da Etapa 1. Quando existem alternativas aplicáveis, a seleção é aleatória, conforme a tabela de regras documentada.

Esta versão não mantém memória das casas visitadas ou dos perigos percebidos e possui apenas uma flecha. Por isso, pode repetir caminhos, entrar em uma casa perigosa ou atingir o limite de ações sem concluir a missão.

A tabela implementada e suas prioridades estão em [REGRAS_V1.md](docs/REGRAS_V1.md). Ela é uma proposta deste projeto; não deve ser confundida com uma reprodução da tabela externa mencionada no enunciado como exemplo da Aula 04.

## Etapa 3 - Agente reativo, versão 2

**Projeto:** [PD_Etapa3_MemoriaV2](PD_Etapa3_MemoriaV2/README.md)

Esta etapa amplia a versão 1 com memória e um mecanismo de decisão orientado pelo conhecimento adquirido. O agente registra casas visitadas, percepções e possibilidades de perigo em matrizes próprias.

A ausência de brisa ou fedor permite descartar determinados perigos nas casas vizinhas. As suspeitas restantes orientam a escolha de fronteiras de exploração. O planejamento utiliza busca em largura (BFS) por casas já visitadas, incluindo o retorno à origem após a coleta do ouro.

A classificação proposta é **agente baseado em modelo e orientado a objetivos**, pois mantém uma representação interna do ambiente e planeja ações para explorar, coletar e retornar. A memória, a estratégia e a justificativa dessa classificação estão em [AGENTE_V2.md](docs/AGENTE_V2.md).

## Etapa 4 - Agente de aprendizagem, versão 3

**Projeto:** [PD_Etapa4_GeneticoV3](PD_Etapa4_GeneticoV3/README.md)

O enunciado exige aprendizagem por **algoritmos genéticos**, considerando o caminhamento, os perigos, a coleta do ouro e o retorno à origem em ambientes com `n > 3`.

Nesta implementação, o algoritmo genético aprende seis parâmetros da política com memória: custo de distância, aversão a poços, aversão a Wumpus, recompensa por informação, preferência direcional e limiar de uso da flecha. Cada indivíduo é um vetor de seis inteiros entre `0` e `10`.

O AG evolui uma **política de decisão**, não uma rota construída com conhecimento prévio dos objetos ocultos. Os componentes adotados são:

- Seleção por torneio de três candidatos.
- Cruzamento de um ponto.
- Mutação por substituição do valor de um gene.
- Elitismo de um indivíduo.
- Avaliação pelo resultado da partida e pelo cumprimento da missão.

A documentação registra três propostas de fitness: pontuação oficial (F1), pontuação com bônus de vitória (F2) e inclusão de sinais intermediários de progresso (F3). **F3 é a versão final utilizada na Etapa 5.** Seus bônus são exclusivos da avaliação genética e não alteram a pontuação oficial do jogo.

Consulte [ALGORITMO_GENETICO.md](docs/ALGORITMO_GENETICO.md) e [FITNESS.md](docs/FITNESS.md) para conhecer a codificação, os operadores, as fórmulas, os ajustes e o piloto reproduzível. O piloto não demonstra superioridade estatística de F3.

## Etapa 5 - Validação e resultados

**Projeto:** [PD_Etapa5_Validacao](PD_Etapa5_Validacao/README.md)

Esta etapa compara as versões `v1`, `v2` e `v3` seguindo o protocolo solicitado no PDF:

| Parâmetro | Configuração |
|---|---|
| Tamanhos dos ambientes | `4×4`, `5×5`, `10×10`, `15×15` e `20×20` |
| Distribuição dos objetos | Fixa por tamanho e compartilhada pelas três versões |
| Execuções | 30 por versão e por tamanho |
| Total de partidas finais | 450 |
| População do AG | 50 indivíduos |
| Gerações do AG | 1.000, além do registro da população inicial |
| Taxa de cruzamento | 85% por par |
| Taxa de mutação | 5% por gene |
| Operadores e fitness | Fixos em todos os experimentos finais |

São realizados **150 treinamentos independentes** do AG: 30 para cada um dos cinco tamanhos. Para cada tamanho, foi escolhido um mapa com uma rota sem perigo até o ouro, contendo `floor(n²/10)` poços, um Wumpus e um ouro. Essas quantidades e o critério de factibilidade são escolhas da implementação, não valores impostos pelo PDF.

Os resultados incluem a pontuação de cada execução, média, desvio padrão amostral, taxa de vitória e gráficos comparativos. Para o AG, também são registrados o melhor, o pior e o fitness médio de cada geração. Os gráficos agregados apresentam a média dessas estatísticas nos 30 treinamentos de cada tamanho.

O texto do enunciado exige **30 execuções**; esse requisito foi seguido mesmo que a ilustração da tabela do slide 28 termine na linha 20.

### Pontuação específica do PD

| Evento | Pontos |
|---|---:|
| Cada ação | -1 |
| Morte por poço ou Wumpus | -1.000 |
| Cada unidade de ouro coletada | +1.000 |
| Cada Wumpus morto | +1.000 |

O custo da ação é aplicado também quando ela provoca morte, coleta ou disparo. Foi adotada a pontuação do **slide 26 do PD**, sem o desconto adicional de -10 pela flecha presente na explicação geral do Wumpus em outra parte do material.

### Onde encontrar os resultados

Os arquivos estão em [PD_Etapa5_Validacao/resultados](PD_Etapa5_Validacao/resultados/):

- [RESULTADOS.md](PD_Etapa5_Validacao/resultados/RESULTADOS.md): resumo das medidas e interpretação.
- [execucoes.csv](PD_Etapa5_Validacao/resultados/execucoes.csv): registro individual das 450 partidas, com sementes e genes.
- [tabela_pontuacoes.csv](PD_Etapa5_Validacao/resultados/tabela_pontuacoes.csv): pontuações organizadas por execução, tamanho e versão.
- [tabela_vitorias.csv](PD_Etapa5_Validacao/resultados/tabela_vitorias.csv): registro das vitórias.
- [configuracoes.csv](PD_Etapa5_Validacao/resultados/configuracoes.csv): parâmetros dos experimentos.
- [ambientes/](PD_Etapa5_Validacao/resultados/ambientes/): mapas fixos utilizados.
- [evolucao/](PD_Etapa5_Validacao/resultados/evolucao/): estatísticas de fitness por geração e ensaio.
- [graficos/](PD_Etapa5_Validacao/resultados/graficos/): comparação dos agentes e curvas do AG.
- [CONFERENCIA.txt](PD_Etapa5_Validacao/resultados/CONFERENCIA.txt): conferência reproduzível dos dados exportados.

Os resultados incluídos foram obtidos pela execução do código. A conferência reexecutou as 450 partidas a partir dos mapas, sementes e genes e reproduziu os resultados registrados.

## Como abrir e executar no NetBeans

1. Baixe ou clone o repositório.
2. No NetBeans, selecione **Arquivo > Abrir Projeto**.
3. Abra a pasta de uma etapa, por exemplo, `PD_Etapa1_Gerador`. A raiz do repositório não é um projeto Java único.
4. Configure um JDK 8 ou superior, se necessário.
5. Execute com **F6** e acompanhe a saída no console.

Para alterar parâmetros, use **Propriedades do Projeto > Executar > Argumentos**. Os argumentos disponíveis e os exemplos estão no README de cada etapa.

Na Etapa 5, F6 inicia a bateria completa. Para preservar os resultados já incluídos, informe:

```text
--saida=resultados_novos
```

Para verificar rapidamente a execução, sem cumprir o protocolo final:

```text
--rapido --saida=resultados_rapidos
```

O modo rápido utiliza apenas dois tamanhos, duas repetições e 20 gerações, totalizando 12 partidas. **Seus dados não substituem os resultados exigidos pelo PD.**

## Testes e reprodução

Cada projeto possui testes automáticos para os componentes disponíveis naquela etapa. No NetBeans, execute o alvo `test` do arquivo `build.xml`. Com Apache Ant instalado, também é possível usar:

```bash
ant clean jar test
```

Na Etapa 5, o alvo abaixo confere os resultados incluídos por meio de reexecução das partidas e validação dos registros de treinamento:

```bash
ant conferir
```

Os cinco projetos passaram na compilação e nos testes com o Apache Ant da instalação do NetBeans. Consulte [VALIDACAO.md](VALIDACAO.md) para o escopo das verificações.

## Convenções e limites da solução

Alguns pontos precisam ser considerados na análise e na defesa do trabalho:

- As coordenadas seguem `mapa[linha][coluna]`, com `(0,0)` no canto superior esquerdo. Norte reduz a linha; sul aumenta a linha; leste aumenta a coluna; oeste reduz a coluna.
- A flecha atinge apenas a casa ortogonal adjacente. Essa interpretação está documentada e deve ser confirmada com o docente caso ele prefira a variante clássica de disparo em linha reta.
- A vitória exige coletar ao menos uma unidade de ouro e executar a saída na origem. A política não busca todos os ouros quando o gerador cria vários.
- Cada partida tem limite de `10 × n²` ações. Atingir o limite é fracasso, mas não morte.
- Um mapa fixo por tamanho permite a comparação pedida, mas não permite generalizar o desempenho para todos os ambientes possíveis.
- O AG treina no mesmo mapa utilizado no teste, com outra semente para a partida final. Isso avalia estabilidade no mapa treinado, não generalização para mapas inéditos.
- O fitness é diferente da pontuação oficial. O custo computacional do treinamento também não está incluído na contagem de ações da partida final.
- Nenhuma versão garante vitória em todos os mapas ou obtenção do ótimo global.

As regras, a memória, a estratégia, a codificação e o fitness são escolhas propostas nesta implementação, dentro da liberdade concedida pelo enunciado. Devem ser compreendidas, revisadas e justificadas na apresentação acadêmica.

## Documentação e referência

- [REQUISITOS.md](docs/REQUISITOS.md): correspondência entre exigências do PDF e implementação.
- [CONVENCOES.md](docs/CONVENCOES.md): regras e interpretações adotadas.
- [METODOLOGIA.md](docs/METODOLOGIA.md): protocolo, sementes, métricas e limitações.
- [RESULTADOS_COMENTADOS.md](docs/RESULTADOS_COMENTADOS.md): análise dos resultados medidos.

**Referência da atividade:** TEIXEIRA, Otávio Noura. *AI 2026*. Material da disciplina de Inteligência Artificial fornecido para o mestrado. Projeto de Desenvolvimento: slides numerados **22 a 28**, correspondentes às páginas **18 a 24** do PDF.
