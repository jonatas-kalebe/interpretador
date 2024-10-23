package parser;

import java.util.*;

public class ClassDef {
    private String className;
    private List<String> attributes = new ArrayList<>();
    private Map<String, MethodDef> methods = new HashMap<>();

    public ClassDef(String className) {
        this.className = className;
    }

    public List<String> getAttributes() {
        return attributes;
    }

    public void addAttribute(String attribute) {
        this.attributes.add(attribute);
    }

    public  MethodDef getMethodElement(String methodName) {
        return methods.get(methodName);
    }

    public void putMethodElement(String methodName, MethodDef method) {
        this.methods.put(methodName, method);
    }
}
