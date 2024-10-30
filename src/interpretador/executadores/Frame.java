package interpretador.executadores;

import interpretador.entidades.MainFunction;
import interpretador.entidades.MethodDef;
import interpretador.instrucoes.Instruction;
import interpretador.valores.Value;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Frame {
    private final List<Instruction> instructions;
    private final Map<String, Value> variables = new HashMap<>();
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
        return null;
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
