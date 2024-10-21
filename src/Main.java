import Util.RegexUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private final String globalInstructions;
    private List<String> mainInstructions;
    private final Map<String, String> attributes;
    private final List<Classe> classes;
    private final List<Objeto> objetos = new ArrayList<>();

    public Main(String globalInstructions, Map<String, String> attributes, List<Classe> classes) {
        this.globalInstructions = globalInstructions;
        this.attributes = attributes;
        this.classes = classes;
    }

    public Main(String globalInstructions, Map<String, String> attributes) {
        this(globalInstructions, attributes, new ArrayList<>());
    }

    public Main(String globalInstructions, List<Classe> classes) {
        this(globalInstructions, new HashMap<>(), classes);
    }

    public Main(String globalInstructions) {
        this(globalInstructions, new HashMap<>(), new ArrayList<>());
    }

    public void processBlockCode() {
        String mainBlock = RegexUtil.extractMain(globalInstructions).getFirst();
        this.mainInstructions = List.of(mainBlock.split("\\r?\\n"));
        List<String> vars = RegexUtil.extractVars(mainBlock);
        for (String var : vars) {
            this.attributes.put(var, "");
        }
        List<String> classStrings = RegexUtil.extractClasses(globalInstructions);
        for (String classStr : classStrings) {
            Classe c = new Classe(classStr);
            this.classes.add(c);
        }
    }

    public void interpret() {
        for (String instruction : mainInstructions) {
            instruction = instruction.trim();
            if (instruction.isEmpty()) {
                continue;
            }

            String[] parts = instruction.split("\\s+");
            String opcode = parts[0];

            try {
                switch (opcode) {
                    case "load":
                        handleLoad(parts);
                        break;
                    case "store":
                        handleStore(parts);
                        break;
                    case "const":
                        handleConst(parts);
                        break;
                    case "set":
                        if (instruction.contains("_prototype")) {
                            handlePrototypeSet(parts);
                        } else {
                            handleSet(parts);
                        }
                        break;
                    case "new":
                        handleNew(parts);
                        break;
                    default:
                        System.err.println("Unknown instruction: " + instruction);
                        break;
                }
            } catch (Exception e) {
                System.err.println("Error processing instruction: " + instruction);
                e.printStackTrace();
            }
        }
    }

    private void handleNew(String[] parts) {
        if (parts.length < 2) {
            throw new IllegalArgumentException("Missing operand in load instruction.");
        }
        String name = parts[1];
        Storage.addInstruction(String.join(" ", parts), name);
    }

    private void handleLoad(String[] parts) {
        if (parts.length < 2) {
            throw new IllegalArgumentException("Missing operand in load instruction.");
        }
        String name = parts[1];
        Storage.addInstruction(String.join(" ", parts), name);
    }

    private void handleStore(String[] parts) {
        if (parts.length < 2) {
            throw new IllegalArgumentException("Missing operand in store instruction.");
        }
        String variable = parts[1];
        String value = Storage.getInstruction();
        processAssignment(value, variable);
    }

    private void handleConst(String[] parts) {
        if (parts.length < 2) {
            throw new IllegalArgumentException("Missing operand in const instruction.");
        }
        String variable = parts[1];
        Storage.addInstruction(String.join(" ", parts), variable);
    }

    private void handleSet(String[] parts) {
        if (parts.length < 3) {
            throw new IllegalArgumentException("Incomplete set instruction.");
        }
        String variable = parts[1];
        String valueName = parts[2];
        processAssignment(valueName, variable);
    }

    private void handlePrototypeSet(String[] parts) {
        if (parts.length < 2) {
            throw new IllegalArgumentException("Missing operand in prototype set instruction.");
        }
        String name = parts[1];
        String value = Storage.getInstruction();
        attributes.put(name, value);
    }

    private void processAssignment(String value, String variable) {
        String[] valueIdent= value.split("\\s+");
        if (valueIdent[1].matches("\\d+")) {
            attributes.put(variable, value);
        } else if (value.contains("new")) {

            for (Classe c : classes) {
                if (value.contains(c.getNome())) {
                    objetos.add(new Objeto(c.getAtributos(),c));
                    break;
                }
            }

            attributes.put(variable, objetos.toString());

        } else {
            attributes.put(variable, attributes.get(value));
        }
    }
}
