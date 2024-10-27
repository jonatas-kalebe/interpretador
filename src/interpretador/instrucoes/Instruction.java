package interpretador.instrucoes;

import interpretador.executadores.Interpreter;

public abstract class Instruction {
    public abstract void execute(Interpreter interpreter);
}
