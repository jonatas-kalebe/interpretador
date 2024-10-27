package interpretador.entidades;

import interpretador.instrucoes.Instruction;

import java.util.ArrayList;
import java.util.List;

public class MainFunction {
    private List<String> localVariables = new ArrayList<>();
    private List<Instruction> instructions = new ArrayList<>();


    public void addLocalVariables(String localVariable) {
        this.localVariables.add(localVariable);
    }

    public List<Instruction> getInstructions() {
        return instructions;
    }

    public void addInstruction(Instruction instruction) {
        this.instructions.add(instruction);
    }
}
