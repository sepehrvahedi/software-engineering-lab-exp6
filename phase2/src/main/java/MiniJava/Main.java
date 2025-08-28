package MiniJava;

import MiniJava.facade.CompilerFacade;
import java.util.Scanner;

/**
 * Main class for MiniJava Compiler
 */
public class Main {
    public static void main(String[] args) {
        CompilerFacade compiler = new CompilerFacade();
        Scanner scanner = new Scanner(System.in);

        compiler.compileAndPrint(scanner);

        scanner.close();
    }
}
