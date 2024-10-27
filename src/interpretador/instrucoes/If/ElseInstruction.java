package interpretador.instrucoes.If;

import interpretador.executadores.Interpreter;
import interpretador.instrucoes.Instruction;

public class ElseInstruction extends Instruction {
    private int skipCount;

    public ElseInstruction(int skipCount) {
        this.skipCount = skipCount;
    }

    @Override
    public void execute(Interpreter interpreter) {
        if (interpreter.isIfConditionTrue()) {
            interpreter.currentFrame().incrementInstructionPointer(skipCount);
        }
    }
}
