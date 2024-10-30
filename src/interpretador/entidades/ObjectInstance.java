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
    private final int id;
    private final ClassDef classDef;
    private final Map<String, Value> attributes = new HashMap<>();
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
            return new ObjectValue(prototypeId);
        } else if (attributes.containsKey(name)) {
            return attributes.get(name);
        } else if (prototypeId != -1) {
            ObjectInstance prototype = interpreter.getElementHeap(prototypeId);
            return prototype.getAttribute(name, interpreter);
        }
        return null;
    }

    public void setAttribute(String name, Value value, Interpreter interpreter) {
        if (name.equals("_prototype")) {
            this.prototypeId = ((ObjectValue) value).getObjectId();

        } else if (attributes.containsKey(name)) {
            attributes.put(name, value);
        } else if (prototypeId != -1) {
            ObjectInstance prototype = interpreter.getElementHeap(prototypeId);
            prototype.setAttribute(name, value, interpreter);
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
