package interpretador.instrucoes;

import interpretador.entidades.ObjectInstance;
import interpretador.executadores.Interpreter;
import interpretador.valores.ObjectValue;
import interpretador.valores.Value;

public class GetInstruction extends Instruction {
    String attributeName;

    public GetInstruction(String attributeName) {
        this.attributeName = attributeName;
    }

    @Override
    public void execute(Interpreter interpreter) {
        Value objRef = interpreter.popOperandStack();

        int objectId = ((ObjectValue) objRef).getObjectId();
        ObjectInstance objInstance = interpreter.getElementHeap(objectId);
        Value value = objInstance.getAttribute(attributeName, interpreter);
        interpreter.pushOperandStack(value);

    }
}
