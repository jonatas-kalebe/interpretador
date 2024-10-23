package instrucoes;

import executadores.Interpreter;

public abstract class Instruction {
    public abstract void execute(Interpreter interpreter);
}
