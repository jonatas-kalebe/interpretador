package interpretador.instrucoes;

import interpretador.executadores.Interpreter;
import interpretador.valores.IntValue;
import interpretador.valores.Value;

public class DivInstruction extends Instruction {
    @Override
    public void execute(Interpreter interpreter) {
        Value v2 = interpreter.popOperandStack();
        Value v1 = interpreter.popOperandStack();

        int result = ((IntValue) v1).getValue() / ((IntValue) v2).getValue();
        interpreter.pushOperandStack(new IntValue(result));

    }
}

