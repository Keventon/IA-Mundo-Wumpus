# Protocolo de validação e resultados

## Desenho experimental

São cinco tamanhos: 4, 5, 10, 15 e 20. Cada mapa tem floor(n*n/10) poços, um Wumpus e um ouro. Essas quantidades são uma escolha do projeto porque o enunciado não as fixa.

O gerador começa com semente 2026000+n e incrementa se não existe rota sem perigo até o ouro. O primeiro mapa factível é salvo e reutilizado para **todos os agentes e todas as 30 execuções desse tamanho**. A seleção de factibilidade não exige que a estratégia sensorial encontre essa rota.

Não selecionamos mapas por pontuação ou taxa de sucesso dos agentes. O arquivo configuracoes.csv registra a semente realmente usada. Objetos e percepções estão em ambientes/mapa_n.csv.

## Execuções

30 repetições * 5 tamanhos * 3 versões = 450 partidas finais.

Para o AG, cada repetição tem um treinamento independente de 50 indivíduos e 1000 gerações. Existem 150 treinamentos. A semente de treino é misturar(900000+n*1000+k); a de teste é misturar(1900000+n*1000+k), com k entre 0 e 29. misturar é a transformação determinística SplitMix64 da classe Sementes, usada para dispersar os bits antes de alimentar o Random. Evitamos assim usar sementes consecutivas diretamente, pois elas podem repetir as primeiras escolhas. As sementes resultantes estão no CSV, não precisam ser reconstruídas manualmente.

As três versões compartilham a semente de teste em cada repetição para permitir pareamento nominal; o número de sorteios consumidos depende da política. As sementes distintas não são uma prova estatística de independência.

Há até quatro trabalhadores Java para os treinamentos independentes. Os resultados são coletados na ordem das sementes e não mudam pela ordem de execução. Cada treino tem cache próprio; as partidas clonam o mesmo ambiente-base.

## Métricas

- Vitória: coletou e executou SAIR na origem.
- Pontuação final, calculada exatamente pelo motor comum.
- Ações, mortes, saídas sem ouro, limites, ouros, Wumpus mortos e casas visitadas.
- Média de pontuação, desvio padrão amostral e taxa de vitória por combinação n x versão.
- Fitness populacional: melhor, pior e média em cada geração de cada treinamento.
- Curvas agregadas: média dessas três estatísticas nas 30 repetições de cada tamanho.

O melhor fitness agregado é a média dos melhores por ensaio, não o melhor global entre todos os ensaios. Geração zero tem a população inicial; o CSV final inclui 1001 gerações por execução.

## Reprodutibilidade

Código sem dependências externas e sementes explícitas. Para repetir os resultados, execute Etapa 5 sem argumentos. Para não sobrescrever os arquivos incluídos, use --saida=resultados_novos. Para uma verificação breve, use --rapido; esse modo gera 12 partidas (dois tamanhos, duas repetições, três agentes) e apenas 20 gerações. O teste rápido **não cumpre o protocolo final**.

Os resultados CSV mantêm todos os fracassos. Uma exceção de programa interrompe a bateria em vez de substituir a execução por uma linha artificial. Não são produzidos números simulados sem execução do motor.

## Limites da comparação

Um mapa por tamanho atende a configuração fixa pedida, mas impede atribuir efeitos apenas ao tamanho ou generalizar para todos os ambientes. O AG aprende no mesmo mapa usado no teste; trocar apenas a semente avalia estabilidade, não desempenho em mapas inéditos.

A comparação de ações finais não inclui o custo computacional do treinamento. O AG parte de uma política de memória, enquanto v1 não tem memória; ganhos não podem ser atribuídos exclusivamente ao operador genético. v2 é a referência relevante para isolar a adaptação dos pesos.

A taxa de vitória e a pontuação respondem a perguntas diferentes. Um agente pode matar um Wumpus e fracassar na coleta, ou voltar com ouro sem matar Wumpus. Não há prova de ótimo global nem teste de significância automaticamente embutido.

Antes da entrega acadêmica, a equipe deve discutir essas limitações, justificar as escolhas e confirmar com o docente as convenções de flecha, sobreposição e missão com múltiplos ouros.
