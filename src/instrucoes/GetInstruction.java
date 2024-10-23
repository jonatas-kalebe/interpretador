package instrucoes;

import executadores.Interpreter;
import executadores.ObjectInstance;
import valores.ObjectValue;
import valores.Value;

public class GetInstruction extends Instruction {
    String attributeName;

    public GetInstruction(String attributeName) {
        this.attributeName = attributeName;
    }

    @Override
    public void execute(Interpreter interpreter) {
        Value objRef = interpreter.popOperandStack();
        if (objRef instanceof ObjectValue) {
            int objectId = ((ObjectValue) objRef).getObjectId();
            ObjectInstance objInstance = interpreter.getElementHeap(objectId);
            Value value = objInstance.getAttribute(attributeName, interpreter);
            interpreter.pushOperandStack(value);
        } else {
            throw new RuntimeException("Get instruction requires object reference");
        }
    }
}
