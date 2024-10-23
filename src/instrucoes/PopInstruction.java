package instrucoes;

import executadores.Interpreter;

public class PopInstruction extends Instruction {
    @Override
    public void execute(Interpreter interpreter) {
        interpreter.popOperandStack();
    }
}
