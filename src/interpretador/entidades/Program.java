package interpretador.entidades;

import java.util.HashMap;
import java.util.Map;

public class Program {
    private final Map<String, ClassDef> classes = new HashMap<>();
    private MainFunction mainFunction;

    public ClassDef getClassElement(String className) {
        return classes.get(className);
    }

    public void putClassElement(String className, ClassDef currentClass) {
        this.classes.put(className, currentClass);
    }

    public MainFunction getMainFunction() {
        return mainFunction;
    }

    public void setMainFunction(MainFunction mainFunction) {
        this.mainFunction = mainFunction;
    }
}
