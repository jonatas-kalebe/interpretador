package util;

import entidades.*;

import java.util.*;

public class Parser {
    public Program parse(String code) {
        List<String> lines = Arrays.asList(code.split("\\r?\\n"));
        Iterator<String> iterator = lines.iterator();

        Map<String, Classe> classes = new HashMap<>();
        List<String> mainInstructions = new ArrayList<>();
        List<String> mainVars = new ArrayList<>();

        Classe currentClass = null;
        Method currentMethod = null;
        boolean inMain = false;

        while (iterator.hasNext()) {
            String line = iterator.next().trim();
            if (line.isEmpty()) continue;

            if (line.startsWith("class")) {
                String className = line.substring(5).trim();
                currentClass = new Classe(className);
                classes.put(className, currentClass);
            } else if (line.equals("end-class")) {
                currentClass = null;
            } else if (line.startsWith("vars")) {
                List<String> vars = parseVars(line);
                if (currentMethod != null) {
                    currentMethod.addVars(vars);
                } else if (currentClass != null) {
                    currentClass.addAttributes(vars);
                } else if (inMain) {
                    mainVars.addAll(vars);
                }
            } else if (line.startsWith("method")) {
                String methodDeclaration = line.substring(6).trim();
                currentMethod = new Method(methodDeclaration);
                currentClass.addMethod(currentMethod);
            } else if (line.equals("end-method")) {
                currentMethod = null;
            } else if (line.equals("main()")) {
                inMain = true;
            } else if (line.equals("end")) {
                inMain = false;
            } else if (line.equals("begin") || line.equals("end-if") || line.equals("else") || line.equals("then")) {
                // Ignorar palavras-chave estruturais
            } else {
                if (currentMethod != null) {
                    currentMethod.addInstruction(line);
                } else if (inMain) {
                    mainInstructions.add(line);
                }
            }
        }

        return new Program(classes, mainVars, mainInstructions);
    }

    private List<String> parseVars(String line) {
        String varsPart = line.substring(4).trim();
        String[] varsArray = varsPart.split(",");
        List<String> vars = new ArrayList<>();
        for (String var : varsArray) {
            vars.add(var.trim());
        }
        return vars;
    }
}
