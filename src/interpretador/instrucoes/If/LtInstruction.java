package interpretador.instrucoes.If;

import interpretador.executadores.Interpreter;
import interpretador.instrucoes.Instruction;
import interpretador.valores.BooleanValue;
import interpretador.valores.IntValue;
import interpretador.valores.Value;

public class LtInstruction extends Instruction {
    @Override
    public void execute(Interpreter interpreter) {
        Value value2 = interpreter.popOperandStack();
        Value value1 = interpreter.popOperandStack();

        boolean result = ((IntValue) value1).getValue() < ((IntValue) value2).getValue();
        interpreter.pushOperandStack(new BooleanValue(result));

    }
}
