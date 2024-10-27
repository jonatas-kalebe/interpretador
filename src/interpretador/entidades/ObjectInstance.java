package interpretador.entidades;

import interpretador.executadores.Interpreter;
import interpretador.valores.IntValue;
import interpretador.valores.ObjectValue;
import interpretador.valores.Value;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ObjectInstance {
    private int id;
    private ClassDef classDef;
    private Map<String, Value> attributes = new HashMap<>();
    private int prototypeId = -1;
    private String color;

    public ObjectInstance(int id, ClassDef classDef) {
        this.id = id;
        this.classDef = classDef;
        this.color = "gray";
        if (classDef != null) {
            for (String attr : classDef.getAttributes()) {
                attributes.put(attr, new IntValue(0));
            }
        }
    }

    public Value getAttribute(String name, Interpreter interpreter) {
        if (name.equals("_prototype")) {
            if (prototypeId != -1) {
                return new ObjectValue(prototypeId);
            } else {
                throw new RuntimeException("Prototype not set for this object.");
            }
        } else if (attributes.containsKey(name)) {
            return attributes.get(name);
        } else if (prototypeId != -1) {
            ObjectInstance prototype = interpreter.getElementHeap(prototypeId);
            return prototype.getAttribute(name, interpreter);
        } else {
            throw new RuntimeException("Attribute not found: " + name);
        }
    }

    public void setAttribute(String name, Value value, Interpreter interpreter) {
        if (name.equals("_prototype")) {
            if (value instanceof ObjectValue) {
                int prototypeObjectId = ((ObjectValue) value).getObjectId();
                if (prototypeObjectId == this.id) {
                    throw new RuntimeException("An object cannot be its own prototype.");
                }
                this.prototypeId = prototypeObjectId;
            } else {
                throw new RuntimeException("_prototype must be assigned an object reference.");
            }
        } else if (attributes.containsKey(name)) {
            attributes.put(name, value);
        } else if (prototypeId != -1) {
            ObjectInstance prototype = interpreter.getElementHeap(prototypeId);
            prototype.setAttribute(name, value, interpreter);
        } else {
            throw new RuntimeException("Attribute not found: " + name);
        }
    }

    public int getId() {
        return id;
    }

    public ClassDef getClassDef() {
        return classDef;
    }

    public int getPrototypeId() {
        return prototypeId;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public List<Value> getAttributesList() {
        return new ArrayList<>(attributes.values());
    }
}
