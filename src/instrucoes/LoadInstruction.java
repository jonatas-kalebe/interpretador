package instrucoes;

import executadores.Interpreter;
import valores.ObjectValue;
import valores.Value;

public class LoadInstruction extends Instruction {
    String variableName;

    public LoadInstruction(String variableName) {
        this.variableName = variableName;
    }

    @Override
    public void execute(Interpreter interpreter) {
        Value value;
        if (variableName.equals("self")) {
            value = interpreter.currentFrame().getVariableElement("self");
        } else if (variableName.equals("io")) {
            value = new ObjectValue(interpreter.getIoObject().getId());
        } else {
            value = interpreter.currentFrame().getVariable(variableName);
        }
        interpreter.pushOperandStack(value);
    }
}
