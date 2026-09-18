# Resultados medidos

Protocolo completo: 450 partidas de teste, 150 aprendizagens independentes do AG.

| n | Agente | Execuções | Média dos pontos | Desvio amostral | Vitórias |
|---|---|---|---|---|---|
| 4 | v1 | 30 | 52.17 | 872.95 | 26.7% |
| 4 | v2 | 30 | 1554.23 | 496.51 | 100.0% |
| 4 | v3 | 30 | 1886.97 | 302.53 | 100.0% |
| 5 | v1 | 30 | 160.33 | 790.82 | 36.7% |
| 5 | v2 | 30 | 995.00 | 1.02 | 100.0% |
| 5 | v3 | 30 | 995.60 | 0.81 | 100.0% |
| 10 | v1 | 30 | -1008.43 | 11.46 | 0.0% |
| 10 | v2 | 30 | -262.23 | 1100.76 | 33.3% |
| 10 | v3 | 30 | 759.27 | 598.36 | 90.0% |
| 15 | v1 | 30 | -997.83 | 179.79 | 0.0% |
| 15 | v2 | 30 | 527.67 | 973.67 | 40.0% |
| 15 | v3 | 30 | 1169.07 | 919.54 | 70.0% |
| 20 | v1 | 30 | -1011.60 | 20.26 | 0.0% |
| 20 | v2 | 30 | -200.63 | 1194.69 | 33.3% |
| 20 | v3 | 30 | 621.13 | 1011.62 | 76.7% |

## Interpretação e limites

Vitória exige coleta e saída na origem; pontuação e vitória são medidas distintas. Um agente pode matar um Wumpus e ainda perder a missão. O fitness não é a pontuação oficial. Os arquivos preservam também as mortes, os limites e as saídas sem ouro, sem excluir fracassos.

Há somente um mapa fixo por tamanho, compartilhado pelas três versões. O AG é treinado nesse mesmo mapa, mas o teste usa outra semente de desempate. Portanto, o teste verifica estabilidade no ambiente treinado, não generalização para mapas inéditos. Trinta execuções não equivalem a trinta ambientes. Tempos de treinamento não são custo de ações da partida.

configuracoes.csv registra o protocolo. execucoes.csv contém cada partida; tabela_pontuacoes.csv reproduz o formato execução x tamanho x versão. evolucao/ registra 0 até a última geração. graficos/ contém PNGs gerados a partir desses números. Consulte docs/METODOLOGIA.md antes de interpretar comparações como evidência de superioridade geral.
