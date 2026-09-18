# Convenções do ambiente

## Regras específicas do PD

O motor aplica a pontuação do slide 26 do PD, e não a pontuação da apresentação geral do Wumpus (slide 89). Toda ação custa -1, inclusive coleta, saída, impacto e disparo. Ouro coletado concede +1000 e cada Wumpus morto concede +1000. Uma morte desconta -1000, além do custo da ação. Não há custo adicional de -10 pela flecha, bônus de retorno nem combate aleatório com o Wumpus.

A flecha atinge **apenas a casa ortogonal adjacente**, conforme a observação do slide 91 de que as ações impactam uma única casa. Esta é uma convenção explícita do projeto; a equipe deve confirmar com o docente se ele deseja a variante clássica de flecha em linha reta.

O mundo é estático: poços e Wumpus não se movem. Fedor depende dos Wumpus vivos. O grito e o impacto são apresentados na observação seguinte e depois expiram. Brisa e fedor não indicam a direção exata do perigo.

## Coordenadas e sobreposição

Usamos índices de 0 a n-1 e [0][0] no canto superior esquerdo da matriz impressa. Norte reduz a linha, sul aumenta a linha, leste aumenta a coluna e oeste reduz a coluna. Não existem movimentos nem percepções diagonais.

A origem não tem objetos, mas pode ter brisa ou fedor de casas vizinhas. Poços não coexistem com ouro ou Wumpus. Ouro e Wumpus são camadas independentes e podem coexistir; nesse caso o agente deve eliminar o Wumpus antes de entrar. Cada camada não tem objetos duplicados na mesma casa.

## Missão e orçamento

O gerador permite zero ou vários objetos. A missão termina ao sair de [0][0] com **ao menos uma unidade de ouro**. A política coletora não tenta buscar todos os ouros: essa interpretação da missão deve ser revisada se o docente exigir coleta total.

Cada partida tem orçamento de 10*n*n ações para impedir loops infinitos e tornar o custo máximo comparável. Atingir o orçamento é fracasso, não morte. A saída na origem sem ouro também é fracasso. Mapas aleatórios comuns não têm garantia de solução; o gerador não modifica mapas para beneficiar um agente.

Para os experimentos finais escolhemos um mapa factível por tamanho, verificando apenas a existência de uma rota sem perigo até algum ouro. Essa seleção pertence ao protocolo experimental, não é acesso privilegiado do agente.
