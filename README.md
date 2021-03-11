# PDDL-plan-validator
Java PDDL parser/validator [[PT-BR]](https://github.com/smart-pucrs/PDDL-plan-validator/wiki/Home-pt-br)

## Compiling  

- ## Using Gradle 

  The program already comes with the Java files required to compile the program and doesn't require any additional software. 
  * `gradle build` - Compiles the program and runs the tests. 
  * `gradle run`   - Runs the demo function from the Parser.java file. 
  * `gradle test`  - Runs the tests without recompiling the program. 


- ## Using Javac

  The files in the /src/Parser folders can be directly compiled with the "Javac" command. There are no extra dependencies required to compile and run the program without the tests. 
 
  To run the demo function the program requires certain files with a hardcoded path. The easiest way to solve this issue is to move the file "template" and the folder "test" to the folder with the compiled .class files. 
 
## Usage

 The program comes with a demo function that can be executed by running the Parser file.

 The function parses the pddl files located within the "/test/" folder, prints the internal data structures and the execution of the plan to the terminal, and outputs a LaTeX report to the "out" file. The pddl files can be modified. 

For other use cases it's necessary to use the internal functions to parse and validade pddl files. 

### [Quick Start](https://github.com/smart-pucrs/PDDL-plan-validator/wiki/Quick-Start)
