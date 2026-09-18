# Validação dos projetos e dos resultados

Este documento registra as verificações realizadas no pacote das cinco etapas do Projeto de Desenvolvimento (PD) de Inteligência Artificial e explica como repeti-las.

As evidências descritas correspondem ao código e aos resultados fornecidos no pacote original. Alterações posteriores no código, nos parâmetros ou nos arquivos de resultados exigem uma nova validação.

## 1. Compilação dos projetos

Os cinco projetos foram compilados e empacotados com o Apache Ant da instalação do Apache NetBeans. Em cada projeto, foram executados os alvos `clean`, `jar` e `test`, com conclusão **BUILD SUCCESSFUL**.

| Projeto              | Compilação e empacotamento | Testes automáticos |
| -------------------- | -------------------------- | ------------------ |
| PD_Etapa1_Gerador    | Aprovados                  | Aprovados          |
| PD_Etapa2_ReativoV1  | Aprovados                  | Aprovados          |
| PD_Etapa3_MemoriaV2  | Aprovados                  | Aprovados          |
| PD_Etapa4_GeneticoV3 | Aprovados                  | Aprovados          |
| PD_Etapa5_Validacao  | Aprovados                  | Aprovados          |

O código também foi compilado em compatibilidade Java 8. Os projetos não dependem de bibliotecas externas nem de código localizado nas outras etapas.

Esta validação utilizou o sistema de construção da instalação do NetBeans; não representa um teste automatizado da interface gráfica da IDE.

## 2. Escopo dos testes automáticos

Cada etapa executa os testes dos componentes que já estão disponíveis nela.

### Gerador e percepções

- Geração em 300 combinações de tamanho e semente.
- Contagem dos objetos e respeito às regras de ocupação das casas.
- Origem sem objetos.
- Reprodução do ambiente pela mesma semente.
- Brisa e fedor em casas ortogonalmente adjacentes, sem diagonais.
- Rejeição de configurações inválidas.

### Motor das partidas

- Movimentação e impacto nos limites do mapa.
- Coleta de ouro, retorno e saída na origem.
- Mortes por poço e por Wumpus vivo.
- Pontuação das ações, mortes, coletas e eliminação de Wumpus.
- Disponibilidade de apenas uma flecha e alcance adjacente adotado pelo projeto.
- Preservação do mapa-base entre partidas.

### Memória e retorno

- Exploração e retorno com ouro em um mapa sem perigos, usando 30 sementes.
- Conclusão da missão com o agente de memória nesse cenário de teste.

Esse teste não prova que a versão com memória vence todos os mapas com perigos.

### Algoritmo genético

- Genes dentro do intervalo permitido, de 0 a 10.
- Registro da população inicial e das gerações seguintes.
- Ordenação consistente entre pior, média e melhor fitness.
- Conservação do melhor fitness pelo elitismo.
- Reprodução de um treinamento curto com a mesma configuração e semente.
- Dispersão das sementes e diversidade das primeiras escolhas aleatórias no teste correspondente.

Os testes cobrem invariantes e cenários específicos; não são uma prova de ausência de todos os erros possíveis.

## 3. Bateria final da Etapa 5

A bateria completa executou o protocolo abaixo:

| Item                              | Quantidade ou configuração            |
| --------------------------------- | ------------------------------------- |
| Tamanhos dos mapas                | 4, 5, 10, 15 e 20                     |
| Versões dos agentes               | v1, v2 e v3                           |
| Partidas por tamanho e versão     | 30                                    |
| Partidas finais                   | 450                                   |
| Treinamentos independentes do AG  | 150                                   |
| População por treinamento         | 50 indivíduos                         |
| Gerações por treinamento          | 1.000, além da geração inicial        |
| Registros de evolução por tamanho | 30.030, sem contar o cabeçalho do CSV |

Os resultados preservam tanto as vitórias quanto as mortes e os demais fracassos. Os testes rápidos foram executados em pasta separada e não foram misturados com a bateria final.

## 4. Conferência dos arquivos exportados

A classe `pdwumpus.ConferirResultados`, disponível na Etapa 5, reexecutou as 450 partidas usando os mapas, as sementes e os genes exportados. A conferência terminou com sucesso e verificou:

- 15 combinações de tamanho e versão, com 30 partidas em cada combinação.
- Ausência de execuções duplicadas.
- Reprodução das ações, da pontuação, do resultado da missão, dos ouros coletados, dos Wumpus mortos e das casas visitadas.
- Vitória somente com ouro e retorno à posição `(0,0)`.
- Pontuação conforme a fórmula implementada para o PD.
- Reprodução do fitness final dos melhores genes, por uma nova partida com a semente de treinamento.
- Sequência de gerações de 0 a 1.000 nos 150 registros de treinamento.
- Melhor fitness não decrescente, conforme o elitismo.
- Consistência entre pior, média e melhor fitness nos CSVs.
- Tabela de pontuações com 30 linhas de execuções.
- Leitura válida dos gráficos PNG de fitness por tamanho.

A pontuação foi conferida pela fórmula:

```text
pontuação = 1000 × ouros coletados
          + 1000 × Wumpus mortos
          - 1000, se houve morte
          - quantidade de ações
```

O recibo está em [PD_Etapa5_Validacao/resultados/CONFERENCIA.txt](PD_Etapa5_Validacao/resultados/CONFERENCIA.txt). Os gráficos finais também foram inspecionados visualmente.

A conferência não repete integralmente a evolução das 150 populações. Ela reexecuta as partidas finais, reproduz a avaliação dos melhores genes e verifica a consistência dos registros de evolução exportados.

## 5. Como repetir a validação

### No NetBeans

1. Abra a pasta do projeto desejado por **Arquivo > Abrir Projeto**.
2. Use um JDK 8 ou superior.
3. Localize `build.xml` e execute o alvo `test` para os testes automáticos.
4. Na Etapa 5, execute o alvo `conferir` para conferir os resultados incluídos.

Também é possível executar a classe `pdwumpus.Testes`. Na Etapa 5, a classe de conferência é `pdwumpus.ConferirResultados`; seu diretório de trabalho deve ser a pasta do projeto, onde está `resultados/`.

### Com Apache Ant

Abra o terminal na pasta de cada projeto e execute:

```bash
ant clean jar test
```

Na pasta `PD_Etapa5_Validacao`, execute:

```bash
ant conferir
```

O resultado esperado é **BUILD SUCCESSFUL**, sem falhas de verificação. O alvo `conferir` precisa dos CSVs e dos mapas originais em `resultados/`.

### Para gerar uma nova bateria completa

Na Etapa 5, configure em **Propriedades > Executar > Argumentos**:

```text
--saida=resultados_novos
```

Execute com F6. A execução completa pode levar alguns minutos, conforme o computador. O argumento acima preserva os resultados incluídos. Não execute duas baterias apontando para a mesma pasta de saída.

Para conferir essa nova pasta pelo terminal, a partir da Etapa 5, use:

```bash
ant compile
java -cp build/classes pdwumpus.ConferirResultados resultados_novos
```

O modo `--rapido` verifica apenas uma execução reduzida: 12 partidas e 20 gerações. **Ele não substitui a bateria final exigida pelo PD.**

## 6. Limites das evidências

Compilação, testes e reprodução mostram que os cenários verificados e os arquivos incluídos são consistentes com a implementação. Não demonstram vitória garantida, ótimo global ou superioridade universal de uma versão.

Foi utilizado um mapa fixo por tamanho. O AG foi treinado nesse mesmo mapa e testado com outra semente. Assim, os resultados não comprovam generalização para mapas inéditos nem constituem, por si só, um teste de significância estatística.

Antes da entrega acadêmica, devem ser revisadas as convenções de alcance da flecha, coleta de múltiplos ouros e mutação por gene. Essas decisões estão documentadas e não são substituídas pela aprovação dos testes.

## 7. Evidências relacionadas

- [README principal](README.md): apresentação das etapas e instruções gerais.
- [README da Etapa 5](PD_Etapa5_Validacao/README.md): execução e conferência dos experimentos.
- [Resultados medidos](PD_Etapa5_Validacao/resultados/RESULTADOS.md): estatísticas e interpretação.
- [Partidas exportadas](PD_Etapa5_Validacao/resultados/execucoes.csv): dados individuais, sementes e genes.
- [Configurações exportadas](PD_Etapa5_Validacao/resultados/configuracoes.csv): parâmetros da bateria final.
