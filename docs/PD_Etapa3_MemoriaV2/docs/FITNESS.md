# Especificação e ajustes da função de avaliação

Este registro descreve as propostas implementadas e o piloto incluído. Não representa um histórico anterior de experimentos da equipe.

## F1 Pontuação oficial

F1 = pontuação final da partida.

Vantagem: corresponde diretamente à recompensa do PD. Limite: matar um Wumpus e morrer sem ouro pode produzir pontuação superior a uma coleta sem retorno. Não separa cumprimento da missão de ganhos isolados.

## F2 Prioridade para a missão

F2 = pontuação + 5000 se houve vitória.

O bônus é somente de avaliação genética, não é pontuação oficial. Seu propósito é priorizar a missão concluída; entre vencedores, a ordenação segue a pontuação. A política coleta no máximo uma unidade de ouro e tem uma única flecha, limitando sua recompensa oficial positiva a 2000.

Limite: até a primeira vitória, candidatos podem receber pouco sinal sobre avanço seguro.

## F3 Sinais intermediários com ordem preservada entre vencedores

F3 = F2 + 100 se o agente permaneceu vivo + 2*casasVisitadas se não venceu - 10*distânciaManhattanDaOrigem se tem ouro mas ainda não venceu.

O termo de exploração fornece informação gradual para soluções ainda não vencedoras. A distância ao início indica progresso de retorno; não revela a localização do ouro. O termo por casas visitadas é desligado na vitória para não recompensar percursos desnecessários entre soluções bem-sucedidas.

**Versão final selecionada: F3.** A escolha é metodológica: missão em primeiro lugar, pontuação entre vencedores e sinais para os fracassos. O piloto não deve ser usado para afirmar superioridade estatística de F3.

## Piloto reproduzível

A classe AjustesFitness executa F1, F2 e F3 com cinco sementes pareadas, em um mapa 5x5 fixo e factível. Em cada configuração: população 50, 1000 gerações e operadores iguais. Cada teste usa uma semente diferente da semente de treino.

O arquivo resultados_ajustes/comparacao.csv contém valores efetivamente medidos, inclusive genes e sementes. Esse piloto serve para conferir as implementações das fórmulas. Um único mapa fácil não basta para validar os termos ou comparar generalização.

Etapa 4: executar com argumento --ajustes. As fórmulas estão em AlgoritmoGenetico.fitness. Alterações futuras devem criar outro registro e não misturar resultados de versões distintas do fitness.
