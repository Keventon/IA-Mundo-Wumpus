# Base de regras do agente reativo v1

As regras terminais e de coleta têm prioridade por especificarem o objetivo imediato. Nas demais situações, todas as alternativas aplicáveis têm igual probabilidade. Norte, sul, leste e oeste são representados por N, S, L e O.

| Condição atual | Ações elegíveis | Seleção |
|---|---|---|
| Possui ouro e está na origem | SAIR | Única ação terminal |
| Há brilho e ainda não possui ouro | PEGAR | Única ação de coleta |
| Fedor, sem brilho e com flecha | N, S, L, O, atirar N, atirar S, atirar L, atirar O | Sorteio uniforme entre oito ações |
| Brisa e fedor, sem brilho e com flecha | Mesmas oito ações | Sorteio uniforme |
| Brisa, sem fedor | N, S, L, O | Sorteio uniforme |
| Fedor e sem flecha | N, S, L, O | Sorteio uniforme |
| Nenhum sinal | N, S, L, O | Sorteio uniforme |
| Impacto ou grito, sem condição prioritária | Conjunto de movimento ou disparo compatível com o fedor e a flecha atuais | Sorteio uniforme |

Fedor com brilho segue a regra prioritária de coleta. Depois da coleta, possui ouro e está fora da origem: as regras locais continuam, até encontrar a origem, morrer ou atingir o orçamento.

A brisa informa perigo, mas não a direção. Esta versão não estima segurança, não registra visitas e não usa um caminho de retorno. O estado de recursos (ouro e flecha) e a posição atual são fornecidos pelo motor; isso não representa memória de percepções passadas. O Random guarda apenas o estado do sorteador.

Classificação: agente reativo simples. Limites: pode voltar a casas, impactar paredes, atirar na direção errada, morrer ou coletar sem conseguir retornar.
