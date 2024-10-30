package interpretador.instrucoes;

import interpretador.entidades.ObjectInstance;
import interpretador.executadores.Interpreter;
import interpretador.valores.ObjectValue;
import interpretador.valores.Value;

public class SetInstruction extends Instruction {
    private final String attributeName;

    public SetInstruction(String attributeName) {
        this.attributeName = attributeName;
    }

    @Override
    public void execute(Interpreter interpreter) {
        Value objectValue = interpreter.popOperandStack();

        Value valueToAssign = interpreter.popOperandStack();

        int objectId = ((ObjectValue) objectValue).getObjectId();
        ObjectInstance objectInstance = interpreter.getElementHeap(objectId);

        objectInstance.setAttribute(attributeName, valueToAssign, interpreter);
    }
}
