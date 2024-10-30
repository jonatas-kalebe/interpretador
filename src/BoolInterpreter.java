import interpretador.executadores.Interpreter;

import java.io.IOException;

public class BoolInterpreter {
    public static void main(String[] args) throws IOException {
        String filename = args[0];

        Interpreter interpreter = new Interpreter();
        interpreter.loadProgram(filename);
        interpreter.execute();

    }
}

