# PD_Etapa4_GeneticoV3

Agente de aprendizagem v3 por algoritmo genético. Projeto Java de console do PD do mestrado em Inteligência Artificial.

## O que esta etapa faz

A versão mantém memória e regras de v2 e aprende seis pesos de decisão. F6 executa população 50 por 1000 gerações, salva a evolução, desenha fitness.png e testa a política com outra semente.

## Abrir e executar no NetBeans

1. Extraia o ZIP e use **Arquivo > Abrir Projeto**.
2. Selecione a pasta PD_Etapa4_GeneticoV3.
3. Use JDK 8 ou superior. Nenhuma biblioteca adicional é necessária.
4. Pressione **F6**. A saída aparece no console do NetBeans.
5. Para alterar parâmetros, use **Propriedades do Projeto > Executar > Argumentos**.

Exemplo de argumentos:

```text
--n=5 --pocos=2 --wumpus=1 --ouros=1 --semente=2026 --agente=42 --teste=1042 --geracoes=1000 --fitness=3 --saida=resultados_novos
```

Os argumentos --n, --pocos, --wumpus, --ouros e --semente controlam o ambiente. Os valores padrão são n=5, poços=floor(n*n/10), Wumpus=1, ouro=1 e semente=2026. --mapa=caminho/ambiente.csv carrega um CSV exportado pelo gerador e tem precedência sobre a geração.

A semente do agente é definida por --agente (padrão 42), independentemente da semente do mapa. O orçamento padrão é 10*n*n ações; pode ser alterado por --limite.
Use --ajustes para repetir o piloto das três propostas de fitness, com cinco repetições pareadas. Consulte resultados_ajustes/comparacao.csv e docs/FITNESS.md. --geracoes é configurável para desenvolvimento; os resultados oficiais da Etapa 5 usam 1000.

## Código e documentação

AgenteV3 executa os genes aprendidos; AlgoritmoGenetico implementa população, torneio, cruzamento, mutação, elitismo e fitness; Graficos produz PNG; AjustesFitness compara as fórmulas F1, F2 e F3.

Leia docs/CONVENCOES.md para conhecer a pontuação específica do PD e as convenções de flecha, coordenadas, sobreposição e missão. As escolhas livres estão justificadas nos documentos; não são uma garantia de sucesso ou ótimo global.

## Testes automáticos

No NetBeans, clique com o botão direito em build.xml e execute o alvo **test**. Alternativamente, com Apache Ant instalado:

```text
ant test
```

Também é possível executar a classe pdwumpus.Testes como arquivo Java. Os testes usam apenas Java, sem JUnit. A compilação manual foi verificada com javac em compatibilidade Java 8.

## Publicação no GitHub

Versione src/, nbproject/ (exceto private/), build.xml, README.md, docs/ e os resultados que desejar preservar. O .gitignore exclui build/, dist/ e configurações pessoais. Não existe dependência dos outros quatro projetos.
