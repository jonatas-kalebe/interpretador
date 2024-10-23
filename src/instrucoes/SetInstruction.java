package instrucoes;

import executadores.Interpreter;
import executadores.ObjectInstance;
import valores.ObjectValue;
import valores.Value;

public class SetInstruction extends Instruction {
    private String attributeName;

    public SetInstruction(String attributeName) {
        this.attributeName = attributeName;
    }

    @Override
    public void execute(Interpreter interpreter) {
        Value objectValue = interpreter.popOperandStack();

        Value valueToAssign = interpreter.popOperandStack();

        if (!(objectValue instanceof ObjectValue)) {
            throw new RuntimeException("SetInstruction: Expected an object reference on the stack.");
        }

        int objectId = ((ObjectValue) objectValue).getObjectId();
        ObjectInstance objectInstance = interpreter.getElementHeap(objectId);

        if (objectInstance == null) {
            throw new RuntimeException("SetInstruction: Object with ID " + objectId + " not found.");
        }

        objectInstance.setAttribute(attributeName, valueToAssign, interpreter);
    }
}
