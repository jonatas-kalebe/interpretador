package interpretador.entidades;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassDef {
    private final String className;
    private final List<String> attributes = new ArrayList<>();
    private final Map<String, MethodDef> methods = new HashMap<>();

    public ClassDef(String className) {
        this.className = className;
    }

    public List<String> getAttributes() {
        return attributes;
    }

    public void addAttribute(String attribute) {
        this.attributes.add(attribute);
    }

    public MethodDef getMethodElement(String methodName) {
        return methods.get(methodName);
    }

    public void putMethodElement(String methodName, MethodDef method) {
        this.methods.put(methodName, method);
    }
}
