package main;

import util.Parser;
import entidades.Program;
import entidades.Interpreter;

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

        String code;
        try {
            code = Files.readString(Path.of(inputFileName));
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo de entrada: " + e.getMessage());
            System.exit(1);
            return;
        }

        Parser parser = new Parser();
        Program program = parser.parse(code);

        Interpreter interpreter = new Interpreter(program);
        interpreter.execute();
    }
}
