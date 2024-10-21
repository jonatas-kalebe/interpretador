package entidades;

import java.util.*;

public class Interpreter {
    private final Program program;
    private final Deque<Integer> stack;
    private final Map<String, Object> globalVars;
    private final Map<Integer, Objeto> heap;
    private final Deque<Frame> callStack;
    private int instructionCounter;
    private boolean gcColor; // true for black, false for red

    public Interpreter(Program program) {
        this.program = program;
        this.stack = new ArrayDeque<>();
        this.globalVars = new HashMap<>();
        this.heap = new HashMap<>();
        this.callStack = new ArrayDeque<>();
        this.instructionCounter = 0;
        this.gcColor = true; // Start with black
    }

    public void execute() {
        // Inicializar variáveis globais
        for (String var : program.getMainVars()) {
            globalVars.put(var, 0);
        }

        // Executar instruções do main
        executeInstructions(program.getMainInstructions(), globalVars, null);

        // Programa finalizado
    }

    private void executeInstructions(List<String> instructions, Map<String, Object> vars, Objeto self) {
        int i = 0;
        while (i < instructions.size()) {
            String line = instructions.get(i).trim();
            if (line.isEmpty()) {
                i++;
                continue;
            }

            instructionCounter++;
            if (instructionCounter % 5 == 0) {
                garbageCollect();
            }

            String[] parts = line.split("\\s+");
            String opcode = parts[0];

            switch (opcode) {
                case "const":
                    handleConst(parts);
                    break;
                case "load":
                    handleLoad(parts, vars, self);
                    break;
                case "store":
                    handleStore(parts, vars);
                    break;
                case "add":
                    handleAdd();
                    break;
                case "sub":
                    handleSub();
                    break;
                case "mul":
                    handleMul();
                    break;
                case "div":
                    handleDiv();
                    break;
                case "new":
                    handleNew(parts);
                    break;
                case "get":
                    handleGet(parts);
                    break;
                case "set":
                    handleSet(parts);
                    break;
                case "call":
                    handleCall(parts, vars);
                    break;
                case "ret":
                    return;
                case "pop":
                    handlePop();
                    break;
                case "if":
                    i = handleIf(parts, instructions, i);
                    continue;
                default:
                    System.err.println("Instrução desconhecida: " + opcode);
                    System.exit(1);
            }
            i++;
        }
    }

    // Implementação dos métodos de tratamento das instruções
    private void handleConst(String[] parts) {
        int value = Integer.parseInt(parts[1]);
        stack.push(value);
    }

    private void handleLoad(String[] parts, Map<String, Object> vars, Objeto self) {
        String name = parts[1];
        if (name.equals("self")) {
            stack.push(self.getId());
        } else if (vars.containsKey(name)) {
            Object value = vars.get(name);
            if (value instanceof Integer) {
                stack.push((Integer) value);
            } else {
                stack.push(((Objeto) value).getId());
            }
        } else {
            System.err.println("Variável não definida: " + name);
            System.exit(1);
        }
    }

    private void handleStore(String[] parts, Map<String, Object> vars) {
        String name = parts[1];
        int value = stack.pop();
        vars.put(name, value);
    }

    private void handleAdd() {
        int b = stack.pop();
        int a = stack.pop();
        stack.push(a + b);
    }

    private void handleSub() {
        int b = stack.pop();
        int a = stack.pop();
        stack.push(a - b);
    }

    private void handleMul() {
        int b = stack.pop();
        int a = stack.pop();
        stack.push(a * b);
    }

    private void handleDiv() {
        int b = stack.pop();
        int a = stack.pop();
        stack.push(a / b);
    }

    private void handleNew(String[] parts) {
        String className = parts[1];
        Classe classe = program.getClasses().get(className);
        if (classe == null) {
            System.err.println("Classe não definida: " + className);
            System.exit(1);
        }
        Objeto obj = new Objeto(classe);
        heap.put(obj.getId(), obj);
        stack.push(obj.getId());
    }

    private void handleGet(String[] parts) {
        String attrName = parts[1];
        int objId = stack.pop();
        Objeto obj = heap.get(objId);
        if (obj == null) {
            System.err.println("Objeto não encontrado: " + objId);
            System.exit(1);
        }
        Object value = obj.getAttribute(attrName);
        if (value instanceof Integer) {
            stack.push((Integer) value);
        } else if (value instanceof Objeto) {
            stack.push(((Objeto) value).getId());
        } else {
            System.err.println("Atributo não encontrado: " + attrName);
            System.exit(1);
        }
    }

    private void handleSet(String[] parts) {
        String attrName = parts[1];
        int objId = stack.pop();
        int value = stack.pop();
        Objeto obj = heap.get(objId);
        if (obj == null) {
            System.err.println("Objeto não encontrado: " + objId);
            System.exit(1);
        }
        obj.setAttribute(attrName, value);
    }

    private void handleCall(String[] parts, Map<String, Object> vars) {
        String methodName = parts[1];
        int objId = stack.pop();
        Objeto obj = heap.get(objId);
        if (obj == null) {
            System.err.println("Objeto não encontrado: " + objId);
            System.exit(1);
        }

        Method method = findMethod(obj, methodName);
        if (method == null) {
            System.err.println("Método não encontrado: " + methodName);
            System.exit(1);
        }

        Map<String, Object> methodVars = new HashMap<>();
        methodVars.putAll(vars); // Permite acesso às variáveis do escopo atual

        // Inicializar variáveis locais do método
        for (String var : method.getVars()) {
            methodVars.put(var, 0);
        }

        // Criar novo frame
        callStack.push(new Frame(vars, obj));

        // Executar instruções do método
        executeInstructions(method.getInstructions(), methodVars, obj);

        // Remover frame
        callStack.pop();
    }

    private Method findMethod(Objeto obj, String methodName) {
        Objeto currentObj = obj;
        while (currentObj != null) {
            Method method = currentObj.getClasse().getMethod(methodName);
            if (method != null) {
                return method;
            }
            currentObj = currentObj.getPrototype();
        }
        return null;
    }

    private void handlePop() {
        stack.pop();
    }

    private int handleIf(String[] parts, List<String> instructions, int currentIndex) {
        int skipCount = Integer.parseInt(parts[1]);
        int condition = stack.pop();
        if (condition == 0) {
            // Condição falsa, pular instruções
            return currentIndex + skipCount;
        }
        return currentIndex;
    }

    private void garbageCollect() {
        // Implementação simplificada do coletor de lixo mark-and-sweep
        // Alterna a cor a cada execução
        gcColor = !gcColor;
        Set<Integer> reachable = new HashSet<>();

        // Marcar objetos acessíveis a partir das variáveis globais
        markFromVariables(globalVars, reachable);

        // Marcar objetos acessíveis a partir da pilha de chamadas
        for (Frame frame : callStack) {
            markFromVariables(frame.getVars(), reachable);
            if (frame.getSelf() != null) {
                markObject(frame.getSelf(), reachable);
            }
        }

        // Marcar objetos acessíveis a partir da pilha de operandos
        for (Integer value : stack) {
            if (heap.containsKey(value)) {
                markObject(heap.get(value), reachable);
            }
        }

        // Coletar objetos não alcançáveis
        heap.entrySet().removeIf(entry -> !reachable.contains(entry.getKey()));
    }

    private void markFromVariables(Map<String, Object> vars, Set<Integer> reachable) {
        for (Object value : vars.values()) {
            if (value instanceof Integer && heap.containsKey((Integer) value)) {
                markObject(heap.get((Integer) value), reachable);
            }
        }
    }

    private void markObject(Objeto obj, Set<Integer> reachable) {
        if (reachable.contains(obj.getId())) {
            return;
        }
        reachable.add(obj.getId());
        for (Object value : obj.getAttributes().values()) {
            if (value instanceof Integer && heap.containsKey((Integer) value)) {
                markObject(heap.get((Integer) value), reachable);
            }
        }
        if (obj.getPrototype() != null) {
            markObject(obj.getPrototype(), reachable);
        }
    }

    // Classe interna para representar um frame na pilha de chamadas
    private static class Frame {
        private final Map<String, Object> vars;
        private final Objeto self;

        public Frame(Map<String, Object> vars, Objeto self) {
            this.vars = vars;
            this.self = self;
        }

        public Map<String, Object> getVars() {
            return vars;
        }

        public Objeto getSelf() {
            return self;
        }
    }
}
