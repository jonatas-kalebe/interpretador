package interpretador.instrucoes;

import interpretador.executadores.Interpreter;

public class ReturnInstruction extends Instruction {
    @Override
    public void execute(Interpreter interpreter) {
        interpreter.popCallStack();
    }
}
