# PDDL-plan-validator
Java PDDL parser/validator

## Compilação 

- ## Com Gradle 

  O programa já vem com todos os arquivos java necessários para a compilação sem a necessidade de programas adicionais. 
  * 'gradle build' - Compila os arquivos java e roda os testes. 
  * 'gradle run'   - Executa a função de demonstração localizada no arquivo Parser.
  * 'gradle test'  - Roda os testes sem recompilar o programa. 


- ## Sem Gradle 

  Os arquivos na pasta /src/Parser podem ser compilados diretamente com o comando 'Javac'. Não é necessario nenhuma dependência além do próprio Java 8 para compilar o programa sem os testes. 
 
  Para o uso da função de demonstração é necessário a presença de certos arquivos dentro do diretório de execução, o que pode ser resolvido simplesmente movendo o arquivo "template" e a pasta "test" para a mesma pasta dos arquivos .class. 
 
## Uso 

 O programa vem com uma função de demonstração que pode ser acessada executando o arquivo Parser. 
 
 A função processa os arquivos pddl na pasta "/test/", que podem ser modificados, imprime as estruturas internas para o terminal, e gera um relatório LaTeX da aplicação do plano para o arquivo "out".

Para os demais usos é necessário a implementação das funções internas em um novo programa. 

### [Guia rápido](https://github.com/smart-pucrs/PDDL-plan-validator/wiki/Guia-R%C3%A1pido)
