# Comentários sobre os resultados medidos

Os valores abaixo são da bateria final incluída na Etapa 5. Consulte execucoes.csv para cada partida e resumo.csv para as estatísticas completas. Não são estimativas nem exemplos fictícios.

## Cumprimento da missão

| Ambiente | Vitória v1 | Vitória v2 | Vitória v3 |
| -------- | ---------- | ---------- | ---------- |
| 4x4      | 26,7%      | 100,0%     | 100,0%     |
| 5x5      | 36,7%      | 100,0%     | 100,0%     |
| 10x10    | 0,0%       | 33,3%      | 90,0%      |
| 15x15    | 0,0%       | 40,0%      | 70,0%      |
| 20x20    | 0,0%       | 33,3%      | 76,7%      |

Nesses mapas e nessas sementes, a memória e o planejamento favoreceram o cumprimento da missão em relação a v1. Os pesos aprendidos elevaram a taxa de vitória em relação a v2 nos mapas 10x10, 15x15 e 20x20. Em 4x4 e 5x5, a taxa já era 100% na referência.

Isso não demonstra superioridade universal do AG: há um único mapa por tamanho, e os pesos são treinados nesse mesmo mapa. A política aprendida ainda fracassou em partidas de teste.

## Pontuação e fitness

Em 4x4, as médias foram 1554,23 para v2 e 1886,97 para v3, embora ambas tenham vencido todas as partidas. A diferença pode envolver o uso da flecha e a quantidade de ações; a vitória por si só não descreve todo o resultado.

Em 20x20, as médias foram -200,63 para v2 e 621,13 para v3, com desvios amostrais de 1194,69 e 1011,62. A dispersão é elevada; não se deve interpretar apenas a média como um resultado estável em todas as execuções.

As curvas do AG são de fitness de treinamento. Elas podem aumentar sem que a política vença todos os testes, porque o teste usa outra semente de desempate. O elitismo conserva o melhor fitness de treino, mas não garante melhoria da pontuação em toda partida de teste.

## Piloto das propostas de fitness

F1, F2 e F3 venceram as cinco partidas de teste do piloto 5x5 e produziram a mesma média de pontuação, 994,8. Portanto, esse piloto não distinguiu empiricamente a qualidade das propostas. A adoção de F3 foi justificada pela estrutura da avaliação, não por uma alegação de ganho estatístico nesse piloto.

## Pontos para a defesa

Explique por que foram escolhidos os pesos e os operadores, a diferença entre ambiente fixo e execução repetida, o custo de aprendizagem que não entra nas ações da partida, as situações de morte e os limites de generalização. Um próximo experimento útil seria treinar e testar em conjuntos separados de mapas, com mais de um mapa por tamanho.
