package instrucoes;

import executadores.Frame;
import executadores.Interpreter;
import executadores.ObjectInstance;
import parser.MethodDef;
import valores.IntValue;
import valores.ObjectValue;
import valores.Value;

public class CallInstruction extends Instruction {
    String methodName;

    public CallInstruction(String methodName) {
        this.methodName = methodName;
    }

    @Override
    public void execute(Interpreter interpreter) {
        Value objRef = interpreter.popOperandStack();
        if (!(objRef instanceof ObjectValue)) {
            throw new RuntimeException("Method call on non-object");
        }
        int objectId = ((ObjectValue) objRef).getObjectId();

        if (objectId == 0 && methodName.equals("print")) {
            Value arg = interpreter.popOperandStack();
            interpreter.getIoObject().print(arg);
            interpreter.pushOperandStack(new IntValue(0));
            return;
        }

        ObjectInstance objInstance = interpreter.getElementHeap(objectId);
        MethodDef method = interpreter.findMethod(objInstance, methodName);
        if (method == null) {
            throw new RuntimeException("Method not found: " + methodName);
        }

        Frame frame = new Frame(method);
        frame.putVariableElement("self", objRef);

        for (int i = method.getParameters().size() - 1; i >= 0; i--) {
            Value arg = interpreter.popOperandStack();
            frame.putVariableElement(method.getParameters().get(i), arg);
        }

        interpreter.pushCallStack(frame);
    }
}
