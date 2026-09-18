# PD_Etapa1_Gerador

Gerador aleatório de ambientes. Projeto Java de console do PD do mestrado em Inteligência Artificial.

## O que esta etapa faz

O gerador cria matriz n>=3, posiciona as quantidades pedidas, preserva a origem e calcula brisa, fedor e brilho. F6 mostra o mapa e salva resultados/ambiente.csv.

## Abrir e executar no NetBeans

1. Extraia o ZIP e use **Arquivo > Abrir Projeto**.
2. Selecione a pasta PD_Etapa1_Gerador.
3. Use JDK 8 ou superior. Nenhuma biblioteca adicional é necessária.
4. Pressione **F6**. A saída aparece no console do NetBeans.
5. Para alterar parâmetros, use **Propriedades do Projeto > Executar > Argumentos**.

Exemplo de argumentos:

```text
--n=10 --pocos=10 --wumpus=2 --ouros=3 --semente=2026
```

Os argumentos --n, --pocos, --wumpus, --ouros e --semente controlam o ambiente. Os valores padrão são n=5, poços=floor(n*n/10), Wumpus=1, ouro=1 e semente=2026. --mapa=caminho/ambiente.csv carrega um CSV exportado pelo gerador e tem precedência sobre a geração.




## Código e documentação

Acao define direções; Ambiente mantém camadas e percepções; Gerador posiciona objetos; Parametros interpreta a configuração; Main exporta o ambiente.

Leia docs/CONVENCOES.md para conhecer a pontuação específica do PD e as convenções de flecha, coordenadas, sobreposição e missão. As escolhas livres estão justificadas nos documentos; não são uma garantia de sucesso ou ótimo global.

## Testes automáticos

No NetBeans, clique com o botão direito em build.xml e execute o alvo **test**. Alternativamente, com Apache Ant instalado:

```text
ant test
```

Também é possível executar a classe pdwumpus.Testes como arquivo Java. Os testes usam apenas Java, sem JUnit. A compilação manual foi verificada com javac em compatibilidade Java 8.

## Publicação no GitHub

Versione src/, nbproject/ (exceto private/), build.xml, README.md, docs/ e os resultados que desejar preservar. O .gitignore exclui build/, dist/ e configurações pessoais. Não existe dependência dos outros quatro projetos.
