package instrucoes.If;

import executadores.Interpreter;
import instrucoes.Instruction;
import valores.BooleanValue;
import valores.Value;

public class IfInstruction extends Instruction {
    int skipCount;

    public IfInstruction(int skipCount) {
        this.skipCount = skipCount;
    }

    @Override
    public void execute(Interpreter interpreter) {
        Value condition = interpreter.popOperandStack();
        if (condition instanceof BooleanValue) {
            if (!((BooleanValue) condition).isValue()) {
                interpreter.currentFrame().incrementInstructionPointer(skipCount);
                interpreter.setIfConditionTrue(false);
            } else {
                interpreter.setIfConditionTrue(true);
            }
        }
    }
}
