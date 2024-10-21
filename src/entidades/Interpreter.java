package entidades;

import java.util.*;

public class Interpreter {
    private final Program program;
    private final Deque<Integer> stack;
    private final Map<String, Object> globalVars;
    private final Deque<Frame> callStack;
    private int instructionCounter;
    private boolean gcColor; // true for black, false for red

    // Built-in 'io' object
    private final Objeto ioObject;
    private final ObjetoManager objetoManager;

    public Interpreter(Program program) {
        this.program = program;
        this.stack = new ArrayDeque<>();
        this.globalVars = new HashMap<>();
        this.callStack = new ArrayDeque<>();
        this.instructionCounter = 0;
        this.gcColor = true; // Start with black

        this.objetoManager = ObjetoManager.getInstance();

        // Initialize built-in 'io' object
        Classe ioClasse = new Classe("io");
        this.ioObject = new Objeto(ioClasse);
        objetoManager.addObjeto(ioObject);
    }

    public void execute() {
        // Initialize global variables
        for (String var : program.getMainVars()) {
            globalVars.put(var, 0);
        }

        // Add 'io' object to global scope
        globalVars.put("io", ioObject.getId());

        // Execute main instructions
        executeInstructions(program.getMainInstructions(), globalVars, null);

        // Program finished
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
                    handleCall(parts, vars, self);
                    break;
                case "ret":
                    // O valor de retorno já está na pilha
                    return;
                case "pop":
                    handlePop();
                    break;
                case "if":
                    i = handleIf(parts, instructions, i);
                    continue;
                case "else":
                    i = handleElse(parts, instructions, i);
                    continue;
                case "eq":
                case "ne":
                case "gt":
                case "ge":
                case "lt":
                case "le":
                    handleComparison(opcode);
                    break;
                default:
                    System.err.println("Instrução desconhecida: " + opcode);
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
        if (name.contains(".")) {
            // Acesso a atributo de um objeto
            String[] objAttr = name.split("\\.");
            String objName = objAttr[0];
            String attrName = objAttr[1];

            int objId;
            if (objName.equals("self")) {
                if (self == null) {
                    System.err.println("Variável 'self' não definida.");
                    System.exit(1);
                    return;
                }
                objId = self.getId();
            } else if (vars.containsKey(objName)) {
                objId = (Integer) vars.get(objName);
            } else {
                System.err.println("Objeto não definido: " + objName);
                System.exit(1);
                return;
            }

            Objeto obj = objetoManager.getObjeto(objId);
            if (obj == null) {
                System.err.println("Objeto não encontrado: " + objId);
                System.exit(1);
                return;
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
        } else if (name.equals("self")) {
            if (self == null) {
                System.err.println("Variável 'self' não definida.");
                System.exit(1);
                return;
            }
            stack.push(self.getId());
        } else if (vars.containsKey(name)) {
            Object value = vars.get(name);
            if (value instanceof Integer) {
                stack.push((Integer) value);
            } else {
                stack.push((Integer) value);
            }
        } else {
            System.err.println("Variável não definida: " + name);
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
        objetoManager.addObjeto(obj);
        stack.push(obj.getId());
    }

    private void handleGet(String[] parts) {
        String attrName = parts[1];
        int objId = stack.pop();
        Objeto obj = objetoManager.getObjeto(objId);
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
        Objeto obj = objetoManager.getObjeto(objId);
        if (obj == null) {
            System.err.println("Objeto não encontrado: " + objId);
            System.exit(1);
            return;
        }
        obj.setAttribute(attrName, value);
    }

    private void handleCall(String[] parts, Map<String, Object> vars, Objeto self) {
        String methodName = parts[1];
        int objId = stack.pop();
        Objeto obj = objetoManager.getObjeto(objId);

        // Tratamento especial para o objeto 'io' e método 'print'
        if (obj == ioObject && methodName.equals("print")) {
            int value = stack.pop(); // Obter o valor a ser impresso
            System.out.println(value);
            // O método 'print' retorna 0
            stack.push(0);
            return;
        }

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
        // Inicializar variáveis locais do método
        for (String var : method.getVars()) {
            methodVars.put(var, 0);
        }

        // Obter parâmetros
        List<String> params = method.getParams();
        Map<String, Object> paramsMap = new HashMap<>();
        for (int j = params.size() - 1; j >= 0; j--) {
            int value = stack.pop();
            paramsMap.put(params.get(j), value);
        }

        // Adicionar parâmetros às variáveis do método
        methodVars.putAll(paramsMap);

        // Definir 'self' para o objeto
        Objeto originalSelf = obj;
        callStack.push(new Frame(vars, self));

        // Executar instruções do método
        executeInstructions(method.getInstructions(), methodVars, originalSelf);

        // Remover frame
        callStack.pop();

        // Após o 'ret', o valor de retorno deve estar no topo da pilha
    }

    private Method findMethod(Objeto obj, String methodName) {
        Objeto currentObj = obj;
        while (currentObj != null) {
            Classe classe = currentObj.getClasse();
            Method method = classe.getMethod(methodName);
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

    private int handleElse(String[] parts, List<String> instructions, int currentIndex) {
        int skipCount = Integer.parseInt(parts[1]);
        // Pular instruções se a condição original foi verdadeira
        return currentIndex + skipCount;
    }

    private void handleComparison(String opcode) {
        int b = stack.pop();
        int a = stack.pop();
        boolean result;
        switch (opcode) {
            case "eq":
                result = (a == b);
                break;
            case "ne":
                result = (a != b);
                break;
            case "gt":
                result = (a > b);
                break;
            case "ge":
                result = (a >= b);
                break;
            case "lt":
                result = (a < b);
                break;
            case "le":
                result = (a <= b);
                break;
            default:
                result = false;
                break;
        }
        stack.push(result ? 1 : 0);
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
            if (objetoManager.getObjeto(value) != null) {
                markObject(objetoManager.getObjeto(value), reachable);
            }
        }

        // Coletar objetos não alcançáveis
        objetoManager.removeUnreachableObjects(reachable);
    }

    private void markFromVariables(Map<String, Object> vars, Set<Integer> reachable) {
        for (Object value : vars.values()) {
            if (value instanceof Integer && objetoManager.getObjeto((Integer) value) != null) {
                markObject(objetoManager.getObjeto((Integer) value), reachable);
            }
        }
    }

    private void markObject(Objeto obj, Set<Integer> reachable) {
        if (reachable.contains(obj.getId())) {
            return;
        }
        reachable.add(obj.getId());
        for (Object value : obj.getAttributes().values()) {
            if (value instanceof Integer && objetoManager.getObjeto((Integer) value) != null) {
                markObject(objetoManager.getObjeto((Integer) value), reachable);
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
