package entidades;

import java.util.ArrayList;
import java.util.List;

public class Method {
    private final String name;
    private final List<String> vars;
    private final List<String> instructions;
    private final List<String> params;

    public Method(String declaration) {
        String[] parts = declaration.split("\\(");
        this.name = parts[0].trim();

        if (parts.length > 1) {
            String paramsPart = parts[1].replace(")", "").trim();
            if (!paramsPart.isEmpty()) {
                String[] paramsArray = paramsPart.split(",");
                this.params = new ArrayList<>();
                for (String param : paramsArray) {
                    this.params.add(param.trim());
                }
            } else {
                this.params = new ArrayList<>();
            }
        } else {
            this.params = new ArrayList<>();
        }

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

    public List<String> getParams() {
        return params;
    }
}
