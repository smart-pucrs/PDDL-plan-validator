# PDDL-plan-validator

## Compilação 

O programa já vem com todos os arquivos java necessários para a compilação sem a necessidade de programas adicionais. 
- Modificações ao arquivo Parser.java devem ser feitas no arquivo domain.y, gerando um arquivo .java novo com o uso do programa Yacc com o parametro "-J". 
- Modificações ao arquivo Yylex.java devem ser feitas no arquivo domain.flex, gerando um arquivo .java novo com o uso do programa JFlex.



## Arquivos

### Parser.java
Parser de PDDL. 
#### void main(String args[])
  Função de teste. 
  Processa o problema e plano localizados na pasta /test, imprime o dominio para o console, e gera um arquivo latex "out" com o output. 
  O output pode ser gerado para o console trocando a linha "pddl.valOut("out");" por "pddl.planTest();" e recompilando o arquivo.
#### PDDL parseDomain(String filename)
  Processa o arquivo de domínio pddl "filename" e retorna um objecto PDDL.
#### void parseProblem(PDDL domain, String filename)
  Processa o arquivo de problema pddl "filename" e configura o problema no pddl "domain". 
  Requer um pddl com domínio já configurado. Problemas inválidos ou de domínios diferentes podem gerar erros. 
#### void parsePlan(PDDL domain, String filename)
  Processa o arquivo de plano pddl "filename" e configura o plano no pddl "domain".

### PDDL.java
Objetos de pddl. 
#### boolean goalAchieved()
  Verifica se o goal foi completo.
#### void fixPlanCase()
  Arruma erros de letras maiusculas/minusculas no plano.
#### Object[] tryPlan()
  Tenta aplicar o plano, retorna um objeto com o resultado.
  * Null -------------------> O plano não teve nenhum erro.
  * [Ação | Null] ----------> Erro nos parâmetros da ação. 
  * [Ação | [List | List]] -> Ação não aplicável, retorna a lista de predicados positivos não presentes e uma lista de predicados negativos presentes.
#### void planTest()
  Testa o plano e imprime o resultado para o console. 
#### void valOut(String outN)
  Gera um arquivo latex "outN" a partir do do arquivo template "String template" (por padrão é um arquivo sem extensão template). Por padrão esse arquivo contém o nome do problema e domínio, os arquivos, e o resultado do plano.
#### void printTest()
  Imprime o domínio para o console.
#### PrintTest2()
  Imprime o problema e plano para o console.
#### test()
  Imprime o estado atual.
  
### domain.flex
Arquivo lexer/scanner Jflex.

### domain.y
Arquivo parser, especifica a gramática. 

### out
Exemplo de resultado latex. 

### Template
Arquivo de template, usado para o output latex. 
Lista de palavras que são substituidas
  * %!name!% ---------> Nome do programa
	* %!domain!% -------> Nome do domínio
	* %!problem!% ------> Nome do problema
	* %!domainFile!% ---> Arquivo de domínio
	* %!problemFile!% --> Arquivo de problema
	* %!planFile!% -----> Arquivo de plano
  
	* %!plan!% ---------> Repete a linha e substitui por cada ação do plano
	* %!planNum!% ------> Numero da ação atual, deve estar na mesma linha
  
	* %!val!% ----------> Repete até o endval para cada ação aplicada, substitui o val por cada ação
	* %!valNum!% -------> Número da ação, deve estar na mesma linha
	* %!valP!% ---------> Resultado (erros, predicados aplicados), repete para cada erro/predicado 
	* %!endVal!% -------> Demarca o fim, a linha é ignorada 
  
	* %!goalAchieved!% -> String se o objetivo foi atingido
  

