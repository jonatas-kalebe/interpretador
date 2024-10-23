package parser;

import instrucoes.Instruction;

import java.util.*;

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
