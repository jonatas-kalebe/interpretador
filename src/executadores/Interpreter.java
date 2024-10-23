package executadores;

import instrucoes.Instruction;
import parser.ClassDef;
import parser.MethodDef;
import parser.Parser;
import parser.Program;
import util.GarbageCollector;
import util.IOObject;
import valores.Value;

import java.io.IOException;
import java.util.*;

public class Interpreter {
    private Program program;
    private Stack<Value> operandStack = new Stack<>();
    private Stack<Frame> callStack = new Stack<>();
    private Map<Integer, ObjectInstance> heap = new HashMap<>();
    private int nextObjectId = 1;
    private int instructionCount = 0;
    private boolean gcColorFlag = true;
    private IOObject ioObject;

    public Interpreter() {
        ioObject = new IOObject(0);
        heap.put(Integer.valueOf(0), ioObject);
    }

    public void loadProgram(String filename) throws IOException {
        Parser parser = new Parser();
        program = parser.parse(filename);
    }

    public void execute() {
        Frame mainFrame = new Frame(program.getMainFunction());
        callStack.push(mainFrame);

        while (!callStack.isEmpty()) {
            Frame currentFrame = callStack.peek();
            if (currentFrame.hasNextInstruction()) {
                Instruction instr = currentFrame.nextInstruction();
                instr.execute(this);
                instructionCount++;
                if (instructionCount % 5 == 0) {
                    runGarbageCollector();
                }
            } else {
                callStack.pop();
            }
        }
    }

    public void runGarbageCollector() {
        GarbageCollector gc = new GarbageCollector(this);
        gc.collect();
        gcColorFlag = !gcColorFlag;
    }

    public MethodDef findMethod(ObjectInstance objInstance, String methodName) {
        ClassDef classDef = objInstance.getClassDef();
        if (classDef != null) {
            MethodDef method = classDef.getMethodElement(methodName);
            if (method != null) {
                return method;
            }
        }
        if (objInstance.getPrototypeId() != -1) {
            ObjectInstance prototype =this.getElementHeap(objInstance.getPrototypeId());
            return findMethod(prototype, methodName);
        }
        return null;
    }

    public Frame currentFrame() {
        return callStack.peek();
    }

    public Program getProgram() {
        return program;
    }

    public Stack<Value> getOperandStack() {
        return operandStack;
    }

    public Value popOperandStack() {
        return operandStack.pop();
    }

    public void pushOperandStack(Value value) {
        this.operandStack.push(value);
    }

    public Frame popCallStack() {
        return callStack.pop();
    }

    public void pushCallStack(Frame frame) {
        this.callStack.push(frame);
    }

    public Stack<Frame> getCallStack() {
        return callStack;
    }

    public ObjectInstance getElementHeap(int id) {
        return heap.get(Integer.valueOf(id));
    }

    public Map<Integer, ObjectInstance> getHeap() {
        return heap;
    }

    public void putElementHeap(int objectId, ObjectInstance objInstance) {
        this.heap.put(Integer.valueOf(objectId), objInstance);
    }

    public int getNextObjectId() {
        return nextObjectId++;
    }

    public boolean isGcColorFlag() {
        return gcColorFlag;
    }

    public IOObject getIoObject() {
        return ioObject;
    }

}
