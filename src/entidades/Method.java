package entidades;

import java.util.ArrayList;
import java.util.List;

public class Method {
    private final String name;
    private final List<String> vars;
    private final List<String> instructions;

    public Method(String name) {
        this.name = name;
        this.vars = new ArrayList<>();
        this.instructions = new ArrayList<>();
    }

    public void addVars(List<String> vars) {
        this.vars.addAll(vars);
    }

    public void addInstruction(String instruction) {
        instructions.add(instruction);
    }

    public String getName() {
        return name;
    }

    public List<String> getVars() {
        return vars;
    }

    public List<String> getInstructions() {
        return instructions;
    }
}
