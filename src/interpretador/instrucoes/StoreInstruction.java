package interpretador.instrucoes;

import interpretador.executadores.Interpreter;
import interpretador.valores.Value;

public class StoreInstruction extends Instruction {
    String variableName;

    public StoreInstruction(String variableName) {
        this.variableName = variableName;
    }

    @Override
    public void execute(Interpreter interpreter) {
        Value value = interpreter.popOperandStack();
        interpreter.currentFrame().putVariableElement(variableName, value);
    }
}
