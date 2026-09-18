# Especificação do agente de aprendizagem v3

## Codificação

Cada indivíduo é um vetor de seis inteiros entre 0 e 10: custo de distância, aversão a poços, aversão a Wumpus, recompensa por informação, preferência direcional e limiar de uso da flecha. O fenótipo é o comportamento da política com memória parametrizada por esses pesos. A preferência direcional é neutra no valor 5 e permite adaptar desempates de fronteiras simétricas sem consultar objetos ocultos.

Essa codificação evolui uma **política de caminhamento**, não uma rota omnisciente. Cada decisão considera apenas a observação atual e a memória. Os objetos ocultos entram na avaliação pelo motor, através das consequências das ações e do resultado da missão, nunca por consulta direta da política ao mapa.

A população inicial contém os pesos de referência de v2 e outros 49 indivíduos aleatórios. Essa inclusão é explícita para conservar uma política válida de comparação; nenhuma rota ótima é semeada.

## Operadores fixos

- População de 50 indivíduos.
- Seleção por torneio de três candidatos, com reposição.
- Cruzamento de um ponto, probabilidade 85% por par.
- Mutação por substituição: probabilidade 5% **por gene**, para um inteiro diferente entre 0 e 10.
- Elitismo de um indivíduo; 49 descendentes completam a população.
- 1000 gerações, além da população inicial registrada como geração zero.
- Fitness F3 em todos os resultados finais.

Para o último par, o segundo filho não é inserido quando o tamanho 50 já foi atingido. Os operadores não mudam por ambiente ou execução.

A taxa de mutação do PDF não define a unidade. Aqui ela é por gene; a probabilidade de ao menos uma mutação em seis genes é 1-0.95^6, aproximadamente 26,5%. A equipe deve informar essa convenção na defesa.

## Avaliação e aprendizagem

Cada candidato executa uma partida no mapa de treinamento. Todos os candidatos de um ensaio usam a mesma semente de desempate, tornando o fitness determinístico e comparável dentro desse ensaio. Genes repetidos podem reutilizar a avaliação via cache. O cache é novo em cada ensaio; não reduz a população nem o número de gerações.

Após treinar, AgenteV3 usa os melhores pesos em uma partida com outra semente. A adaptação ocorre **entre indivíduos e gerações**, antes da partida final; não é Q-learning nem uma rede neural treinada a cada movimento.

Correspondência com arquitetura de aprendizagem: política com memória = elemento de desempenho; motor e fitness = crítica; AG = elemento de aprendizagem; variação genética = exploração de políticas alternativas. A classificação proposta é agente de aprendizagem cuja política de execução permanece baseada em modelo e objetivos.

## Restrições

O motor, o orçamento, as percepções e as regras de coleta e retorno são comuns a v2 e v3. O AG aprende os seis pesos, mas não descobre novas regras ou novas estruturas de memória. Ele pode melhorar, empatar ou piorar no teste e não garante encontrar o ótimo global.
