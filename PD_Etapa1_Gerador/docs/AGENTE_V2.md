# Agente v2 com memória e objetivos

## Estruturas de memória

As matrizes são armazenadas como vetores de n*n posições, com índice linha*n+coluna. O agente registra casas visitadas, possibilidade de poço, possibilidade de Wumpus, suspeitas acumuladas e percepções já registradas. Não recebe uma cópia dos objetos reais.

Ao sobreviver à entrada, sabe que a casa atual é segura. Sem brisa, descarta poços nos vizinhos; sem fedor, descarta Wumpus nos vizinhos. Uma percepção positiva aumenta suspeitas, mas não confirma que todos os vizinhos contêm perigo.

## Escolha de regras

1. Com ouro na origem, sai.
2. Com brilho e sem ouro, coleta.
3. Com ouro fora da origem, planeja o retorno por casas visitadas.
4. Com fedor e flecha, avalia os possíveis vizinhos. Um candidato único permite disparo por inferência; nos demais casos usa um limiar de evidência.
5. Sem ouro, planeja a ida até uma fronteira desconhecida, priorizando fronteiras cuja segurança foi inferida.
6. Sem fronteiras seguras, assume o menor risco segundo a avaliação documentada.
7. Sem qualquer fronteira alcançável, volta à origem e pode sair sem ouro.

A busca em largura (BFS) encontra trajetos intermediários usando exclusivamente casas visitadas. Fronteiras não visitadas podem ser destino, mas nunca atalhos de passagem desconhecida. Com ouro, BFS busca a origem nesse grafo conhecido; a rota é de menor número de passos entre casas registradas, não necessariamente a menor rota do mapa real.

## Nota das fronteiras

nota = -(g0+1)*distância - 10*g1*riscoPoço - 10*g2*riscoWumpus + g3*informação + (g4-5)*(linha-coluna)

Risco de um perigo possível vale 1 mais as suspeitas acumuladas. Se o perigo foi descartado, vale zero. Informação conta vizinhos ainda não visitados. Havendo ao menos uma fronteira segura, fronteiras com perigo possível não concorrem.

Pesos de referência: [3, 10, 10, 2, 5, 5]. O quinto peso é um viés direcional: 5 é neutro, valores maiores favorecem a componente vertical e menores favorecem a horizontal. Ele permite ao cromossomo expressar preferências até em fronteiras geometricamente simétricas. O sexto peso é o limiar da flecha; permite disparo quando suspeita*2 >= g5, além da regra de candidato único. Empates finais ainda são sorteados; a escolha principal deixou de ser aleatória.

## Classificação e justificativa

É um agente **baseado em modelo e orientado por objetivos**. É baseado em modelo porque mantém estado interno estimado do ambiente e o atualiza pelas percepções. É orientado por objetivos porque planeja exploração e retorno com a finalidade de coletar e sair.

As regras locais reativas continuam presentes; memória e planejamento foram adicionados sobre essa base. A nota heurística não o transforma automaticamente em um agente de utilidade ótima: ela não modela todas as consequências futuras nem prova ótimo global. Sem informação segura suficiente, o agente ainda pode escolher uma casa perigosa.
