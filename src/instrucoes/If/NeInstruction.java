package instrucoes.If;

import executadores.Interpreter;
import instrucoes.Instruction;
import valores.BooleanValue;
import valores.IntValue;
import valores.Value;

public class NeInstruction extends Instruction {
    @Override
    public void execute(Interpreter interpreter) {
        Value value2 = interpreter.popOperandStack();
        Value value1 = interpreter.popOperandStack();

        if (value1 instanceof IntValue && value2 instanceof IntValue) {
            boolean result = ((IntValue) value1).getValue() != ((IntValue) value2).getValue();
            interpreter.pushOperandStack(new BooleanValue(result));
        } else {
            throw new RuntimeException("NeInstruction: Both operands must be integers.");
        }
    }
}
