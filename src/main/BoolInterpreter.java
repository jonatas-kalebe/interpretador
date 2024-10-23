package main;

import executadores.Interpreter;

public class BoolInterpreter {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: java BoolInterpreter <file.boolc>");
            System.exit(1);
        }

        String filename = args[0];

        try {
            Interpreter interpreter = new Interpreter();
            interpreter.loadProgram(filename);
            interpreter.execute();
        } catch (Exception e) {
            System.err.println("An error occurred during execution:");
            e.printStackTrace();
        }
    }
}

