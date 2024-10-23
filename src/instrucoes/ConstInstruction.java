package instrucoes;

import executadores.Interpreter;
import valores.IntValue;

public class ConstInstruction extends Instruction {
    int value;

    public ConstInstruction(int value) {
        this.value = value;
    }

    @Override
    public void execute(Interpreter interpreter) {
        interpreter.pushOperandStack(new IntValue(value));
    }
}
