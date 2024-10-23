package util;

import executadores.Frame;
import executadores.Interpreter;
import executadores.ObjectInstance;
import valores.ObjectValue;
import valores.Value;

import java.util.*;

public class GarbageCollector {
    private final Interpreter interpreter;

    public GarbageCollector(Interpreter interpreter) {
        this.interpreter = interpreter;
    }

    public void collect() {
        mark();
        sweep();
    }

    private void mark() {
        String currentColor = interpreter.isGcColorFlag() ? "red" : "black";

        for (Value value : interpreter.getOperandStack()) {
            markValue(value, currentColor);
        }

        for (Frame frame : interpreter.getCallStack()) {
            for (Value value : frame.getVariableElements()) {
                markValue(value, currentColor);
            }
        }
    }

    private void markValue(Value value, String color) {
        if (value instanceof ObjectValue) {
            int objectId = ((ObjectValue) value).getObjectId();
            ObjectInstance objInstance = interpreter.getElementHeap( objectId);
            if (objInstance != null && !objInstance.getColor().equals(color)) {
                objInstance.setColor(color);

                for (Value attrValue : objInstance.getAttributesList()) {
                    markValue(attrValue, color);
                }
                if (objInstance.getPrototypeId() != -1) {
                    markValue(new ObjectValue(objInstance.getPrototypeId()), color);
                }
            }
        }
    }

    private void sweep() {
        String currentColor = interpreter.isGcColorFlag() ? "red" : "black";
        Iterator<Map.Entry<Integer, ObjectInstance>> iterator = interpreter.getHeap().entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, ObjectInstance> entry = iterator.next();
            ObjectInstance objInstance = entry.getValue();
            if (!objInstance.getColor().equals(currentColor) && !objInstance.getColor().equals("gray")) {
                iterator.remove();
            }
        }
    }
}

