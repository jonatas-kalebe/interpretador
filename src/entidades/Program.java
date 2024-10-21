package entidades;

import java.util.List;
import java.util.Map;

public class Program {
    private final Map<String, Classe> classes;
    private final List<String> mainVars;
    private final List<String> mainInstructions;

    public Program(Map<String, Classe> classes, List<String> mainVars, List<String> mainInstructions) {
        this.classes = classes;
        this.mainVars = mainVars;
        this.mainInstructions = mainInstructions;
    }

    public Map<String, Classe> getClasses() {
        return classes;
    }

    public List<String> getMainVars() {
        return mainVars;
    }

    public List<String> getMainInstructions() {
        return mainInstructions;
    }
}
