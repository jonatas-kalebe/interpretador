package main;

import entidades.Program;
import entidades.Interpreter;
import util.Parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        // Verificar se o nome do arquivo foi passado como argumento
        if (args.length != 1) {
            System.err.println("Uso: java BoolInterpreter <arquivo>.boolc");
            System.exit(1);
        }

        String inputFileName = args[0];

        // Ler o arquivo de entrada
        String code;
        try {
            code = Files.readString(Path.of(inputFileName));
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo de entrada: " + e.getMessage());
            System.exit(1);
            return;
        }

        // Analisar o código e construir o programa
        Parser parser = new Parser();
        Program program = parser.parse(code);

        // Executar o programa
        Interpreter interpreter = new Interpreter(program);
        interpreter.execute();
    }
}
