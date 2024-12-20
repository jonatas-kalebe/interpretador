package interpretador.instrucoes;

import interpretador.entidades.ClassDef;
import interpretador.entidades.ObjectInstance;
import interpretador.executadores.Interpreter;
import interpretador.valores.ObjectValue;

public class NewInstruction extends Instruction {
    String className;

    public NewInstruction(String className) {
        this.className = className;
    }

    @Override
    public void execute(Interpreter interpreter) {
        ClassDef classDef = interpreter.getProgram().getClassElement(className);

        int objectId = interpreter.getNextObjectId();
        ObjectInstance objInstance = new ObjectInstance(objectId, classDef);
        interpreter.putElementHeap(objectId, objInstance);
        interpreter.pushOperandStack(new ObjectValue(objectId));
    }
}
