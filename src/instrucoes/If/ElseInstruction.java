package instrucoes.If;

import executadores.Interpreter;
import instrucoes.Instruction;

public class ElseInstruction extends Instruction {
    int skipCount;

    public ElseInstruction(int skipCount) {
        this.skipCount = skipCount;
    }

    @Override
    public void execute(Interpreter interpreter) {
        interpreter.currentFrame().incrementInstructionPointer(skipCount);
    }
}
