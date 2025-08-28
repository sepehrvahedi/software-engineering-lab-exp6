package MiniJava.facade;

import MiniJava.codeGenerator.CodeGenerator;
import MiniJava.parser.Parser;
import MiniJava.errorHandler.ErrorHandler;
import java.util.Scanner;

/**
 * Facade pattern implementation to simplify the compiler interface
 * This class provides a unified interface to the complex subsystems of lexical analysis,
 * parsing, semantic analysis, and code generation
 */
public class CompilerFacade {
    private Parser parser;
    private CodeGenerator codeGenerator;
    
    public CompilerFacade() {
        this.parser = new Parser();
    }
    
    /**
     * Compiles the input source code through all phases
     * @param input Scanner containing the source code
     * @return true if compilation successful, false otherwise
     */
    public boolean compile(Scanner input) {
        try {
            parser.startParse(input);
            return !ErrorHandler.hasError;
        } catch (Exception e) {
            ErrorHandler.printError("Compilation failed: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Compiles and prints the generated code if successful
     * @param input Scanner containing the source code
     */
    public void compileAndPrint(Scanner input) {
        if (compile(input)) {
            System.out.println("Compilation successful!");
        } else {
            System.out.println("Compilation failed with errors.");
        }
    }
}
