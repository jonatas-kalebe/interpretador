package interpretador.instrucoes;

import interpretador.entidades.MethodDef;
import interpretador.entidades.ObjectInstance;
import interpretador.executadores.Frame;
import interpretador.executadores.Interpreter;
import interpretador.valores.IntValue;
import interpretador.valores.ObjectValue;
import interpretador.valores.Value;

public class CallInstruction extends Instruction {
    String methodName;

    public CallInstruction(String methodName) {
        this.methodName = methodName;
    }

    @Override
    public void execute(Interpreter interpreter) {
        Value objRef = interpreter.popOperandStack();

        int objectId = ((ObjectValue) objRef).getObjectId();

        if (objectId == 0 && methodName.equals("print")) {
            Value arg = interpreter.popOperandStack();
            interpreter.getIoObject().print(arg);
            interpreter.pushOperandStack(new IntValue(0));
            return;
        }

        ObjectInstance objInstance = interpreter.getElementHeap(objectId);
        MethodDef method = interpreter.findMethod(objInstance, methodName);

        Frame frame = new Frame(method);
        frame.putVariableElement("self", objRef);

        for (int i = method.getParameters().size() - 1; i >= 0; i--) {
            Value arg = interpreter.popOperandStack();
            frame.putVariableElement(method.getParameters().get(i), arg);
        }

        interpreter.pushCallStack(frame);
    }
}
