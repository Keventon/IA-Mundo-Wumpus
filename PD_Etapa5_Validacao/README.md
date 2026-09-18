# PD_Etapa5_Validacao

Validação e resultados. Projeto Java de console do PD do mestrado em Inteligência Artificial.

## O que esta etapa faz

F6 executa o protocolo completo: mapas 4,5,10,15,20; 30 partidas por versão e mapa; 150 treinamentos independentes do AG. As 450 partidas finais geram tabelas, resumos e gráficos. Resultados reais da bateria acompanham este projeto.

## Abrir e executar no NetBeans

1. Extraia o ZIP e use **Arquivo > Abrir Projeto**.
2. Selecione a pasta PD_Etapa5_Validacao.
3. Use JDK 8 ou superior. Nenhuma biblioteca adicional é necessária.
4. Pressione **F6**. A saída aparece no console do NetBeans.
5. Para alterar parâmetros, use **Propriedades do Projeto > Executar > Argumentos**.

Exemplo de argumentos:

```text
--saida=resultados_novos
```

Para um teste breve, use --rapido. Esse modo tem somente 12 partidas e 20 gerações; NÃO é o resultado final do PD. Para conferir os dados incluídos, abra resultados/RESULTADOS.md, resultados/execucoes.csv e resultados/graficos/.




## Código e documentação

Experimentos organiza mapas fixos, sementes, treinamentos e testes; Graficos desenha curvas e comparações. O motor e todos os agentes estão incluídos, sem dependência de outro projeto.

Leia docs/CONVENCOES.md para conhecer a pontuação específica do PD e as convenções de flecha, coordenadas, sobreposição e missão. As escolhas livres estão justificadas nos documentos; não são uma garantia de sucesso ou ótimo global.

## Testes automáticos

No NetBeans, clique com o botão direito em build.xml e execute o alvo **test**. Alternativamente, com Apache Ant instalado:

```text
ant test
```

Também é possível executar a classe pdwumpus.Testes como arquivo Java. Os testes usam apenas Java, sem JUnit. A compilação manual foi verificada com javac em compatibilidade Java 8.

## Publicação no GitHub

Para reexecutar a conferência de todas as partidas incluídas, use o alvo **conferir** do build.xml ou execute a classe pdwumpus.ConferirResultados. Ela lê mapas, sementes e genes do CSV e compara o resultado de cada replay. Consulte resultados/CONFERENCIA.txt e docs/RESULTADOS_COMENTADOS.md.

Versione src/, nbproject/ (exceto private/), build.xml, README.md, docs/ e os resultados que desejar preservar. O .gitignore exclui build/, dist/ e configurações pessoais. Não existe dependência dos outros quatro projetos.
