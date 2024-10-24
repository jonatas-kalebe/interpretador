package instrucoes;

import executadores.Interpreter;
import executadores.ObjectInstance;
import parser.ClassDef;
import valores.ObjectValue;

public class NewInstruction extends Instruction {
    String className;

    public NewInstruction(String className) {
        this.className = className;
    }

    @Override
    public void execute(Interpreter interpreter) {
        ClassDef classDef = interpreter.getProgram().getClassElement(className);
        if (classDef == null) {
            throw new RuntimeException("Class not found: " + className);
        }
        int objectId = interpreter.getNextObjectId();
        ObjectInstance objInstance = new ObjectInstance(objectId, classDef);
        interpreter.putElementHeap(objectId, objInstance);
        interpreter.pushOperandStack(new ObjectValue(objectId));
    }
}
