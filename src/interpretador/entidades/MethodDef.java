package interpretador.entidades;

import interpretador.instrucoes.Instruction;

import java.util.ArrayList;
import java.util.List;

public class MethodDef {
    private String methodName;
    private List<String> parameters = new ArrayList<>();
    private List<String> localVariables = new ArrayList<>();
    private List<Instruction> instructions = new ArrayList<>();

    public MethodDef(String methodName) {
        this.methodName = methodName;
    }

    public String getMethodName() {
        return methodName;
    }

    public List<String> getParameters() {
        return parameters;
    }

    public void setParameters(List<String> parameters) {
        this.parameters = parameters;
    }

    public void addLocalVariables(String localVariable) {
        this.localVariables.add(localVariable);
    }

    public List<Instruction> getInstructions() {
        return instructions;
    }

    public void addInstructions(Instruction instruction) {
        this.instructions.add(instruction);
    }
}
