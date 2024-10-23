package instrucoes;

import executadores.Interpreter;
import valores.IntValue;
import valores.Value;

public class DivInstruction extends Instruction {
    @Override
    public void execute(Interpreter interpreter) {
        Value v2 = interpreter.popOperandStack();
        Value v1 = interpreter.popOperandStack();
        if (v1 instanceof IntValue && v2 instanceof IntValue) {
            if (((IntValue) v2).getValue() == 0) {
                throw new RuntimeException("Division by zero");
            }
            int result = ((IntValue) v1).getValue() / ((IntValue) v2).getValue();
            interpreter.pushOperandStack(new IntValue(result));
        } else {
            throw new RuntimeException("Div operation requires two integers");
        }
    }
}

