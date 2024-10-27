package interpretador.instrucoes.If;

import interpretador.executadores.Interpreter;
import interpretador.instrucoes.Instruction;
import interpretador.valores.BooleanValue;
import interpretador.valores.IntValue;
import interpretador.valores.Value;

public class GtInstruction extends Instruction {
    @Override
    public void execute(Interpreter interpreter) {
        Value value2 = interpreter.popOperandStack();
        Value value1 = interpreter.popOperandStack();

        if (value1 instanceof IntValue && value2 instanceof IntValue) {
            boolean result = ((IntValue) value1).getValue() > ((IntValue) value2).getValue();
            interpreter.pushOperandStack(new BooleanValue(result));
        } else {
            throw new RuntimeException("GtInstruction: Both operands must be integers.");
        }
    }
}
