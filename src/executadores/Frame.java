package executadores;

import instrucoes.Instruction;
import parser.MainFunction;
import parser.MethodDef;
import valores.Value;

import java.util.*;

public class Frame {
    private List<Instruction> instructions;
    private Map<String, Value> variables = new HashMap<>();
    private int instructionPointer = 0;

    public Frame(MethodDef method) {
        this.instructions = method.getInstructions();
    }

    public Frame(MainFunction mainFunction) {
        this.instructions = mainFunction.getInstructions();
    }

    public boolean hasNextInstruction() {
        return instructionPointer < instructions.size();
    }

    public Instruction nextInstruction() {
        return instructions.get(instructionPointer++);
    }

    public Value getVariable(String name) {
        if (variables.containsKey(name)) {
            return variables.get(name);
        }
        throw new RuntimeException("Variable not found: " + name);
    }

    public void putVariableElement(String name, Value value) {
        variables.put(name, value);
    }

    public Value getVariableElement(String name) {
        return variables.get(name);
    }

    public List<Value> getVariableElements() {
        return new ArrayList<>(variables.values());
    }

    public void incrementInstructionPointer(int skipCount) {
        this.instructionPointer += skipCount;
    }
}
