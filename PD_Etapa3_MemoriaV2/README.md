# PD_Etapa3_MemoriaV2

Agente v2 com memória e planejamento. Projeto Java de console do PD do mestrado em Inteligência Artificial.

## O que esta etapa faz

A versão inclui o motor e o agente v1, acrescentando descarte de perigos por ausência de sinais, notas para fronteiras e busca em largura sobre casas visitadas. Depois de coletar, planeja a volta à origem.

## Abrir e executar no NetBeans

1. Extraia o ZIP e use **Arquivo > Abrir Projeto**.
2. Selecione a pasta PD_Etapa3_MemoriaV2.
3. Use JDK 8 ou superior. Nenhuma biblioteca adicional é necessária.
4. Pressione **F6**. A saída aparece no console do NetBeans.
5. Para alterar parâmetros, use **Propriedades do Projeto > Executar > Argumentos**.

Exemplo de argumentos:

```text
--n=10 --pocos=10 --wumpus=1 --ouros=1 --semente=2026 --agente=42 --limite=1000
```

Os argumentos --n, --pocos, --wumpus, --ouros e --semente controlam o ambiente. Os valores padrão são n=5, poços=floor(n*n/10), Wumpus=1, ouro=1 e semente=2026. --mapa=caminho/ambiente.csv carrega um CSV exportado pelo gerador e tem precedência sobre a geração.

A semente do agente é definida por --agente (padrão 42), independentemente da semente do mapa. O orçamento padrão é 10*n*n ações; pode ser alterado por --limite.


## Código e documentação

AgenteV2 mantém a memória, avalia regras e planeja. A classificação proposta e sua justificativa estão em docs/AGENTE_V2.md.

Leia docs/CONVENCOES.md para conhecer a pontuação específica do PD e as convenções de flecha, coordenadas, sobreposição e missão. As escolhas livres estão justificadas nos documentos; não são uma garantia de sucesso ou ótimo global.

## Testes automáticos

No NetBeans, clique com o botão direito em build.xml e execute o alvo **test**. Alternativamente, com Apache Ant instalado:

```text
ant test
```

Também é possível executar a classe pdwumpus.Testes como arquivo Java. Os testes usam apenas Java, sem JUnit. A compilação manual foi verificada com javac em compatibilidade Java 8.

## Publicação no GitHub

Versione src/, nbproject/ (exceto private/), build.xml, README.md, docs/ e os resultados que desejar preservar. O .gitignore exclui build/, dist/ e configurações pessoais. Não existe dependência dos outros quatro projetos.
