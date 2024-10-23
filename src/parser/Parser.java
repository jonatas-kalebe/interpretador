package parser;
import instrucoes.*;
import instrucoes.If.*;

import java.io.*;
import java.util.*;

public class Parser {

    public Program parse(String filename) throws IOException {
        Program program = new Program();
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        ClassDef currentClass = null;
        MethodDef currentMethod = null;
        boolean inMain = false;
        boolean inMethod = false;

        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty()) continue;

            if (line.startsWith("class ")) {
                String className = line.substring(6).trim();
                currentClass = new ClassDef(className);
                program.putClassElement(className, currentClass);
                continue;
            }

            if (line.equals("end-class")) {
                currentClass = null;
                continue;
            }

            if (currentClass != null) {
                if (line.startsWith("vars ")) {
                    String varsLine = line.substring(5).trim();
                    String[] vars = varsLine.split(",");
                    for (String var : vars) {
                        currentClass.addAttribute(var.trim());
                    }
                    continue;
                }

                if (line.startsWith("method ")) {
                    String methodHeader = line.substring(7).trim();
                    currentMethod = parseMethodHeader(methodHeader);
                    currentClass.putMethodElement(currentMethod.getMethodName(), currentMethod);
                    inMethod = true;
                    continue;
                }

                if (line.equals("end-method")) {
                    currentMethod = null;
                    inMethod = false;
                    continue;
                }

                if (inMethod && currentMethod != null) {
                    if (line.startsWith("vars ")) {
                        String varsLine = line.substring(5).trim();
                        String[] vars = varsLine.split(",");
                        for (String var : vars) {
                            currentMethod.addLocalVariables(var.trim());
                        }
                        continue;
                    }

                    if (line.equals("begin")) {
                        continue;
                    }
                    if (line.contains("end")){
                        continue;
                    }

                    Instruction instr = parseInstruction(line);
                    currentMethod.addInstructions(instr);
                    continue;
                }
            }

            if (line.startsWith("main()")) {
                program.setMainFunction(new MainFunction());
                inMain = true;
                continue;
            }

            if (line.equals("end")) {
                inMain = false;
                continue;
            }

            if (inMain && program.getMainFunction() != null) {
                if (line.startsWith("vars ")) {
                    String varsLine = line.substring(5).trim();
                    String[] vars = varsLine.split(",");
                    for (String var : vars) {
                        program.getMainFunction().addLocalVariables(var.trim());
                    }
                    continue;
                }

                if (line.equals("begin")) {
                    continue;
                }
                if (line.contains("end")){
                    continue;
                }

                Instruction instr = parseInstruction(line);
                program.getMainFunction().addInstruction(instr);
            }
        }

        reader.close();
        return program;
    }

    private MethodDef parseMethodHeader(String header) {
        String methodName;
        List<String> parameters = new ArrayList<>();

        int paramStart = header.indexOf('(');
        int paramEnd = header.indexOf(')');
        methodName = header.substring(0, paramStart).trim();
        String params = header.substring(paramStart + 1, paramEnd).trim();
        if (!params.isEmpty()) {
            String[] paramList = params.split(",");
            for (String param : paramList) {
                parameters.add(param.trim());
            }
        }

        MethodDef method = new MethodDef(methodName);
        method.setParameters(parameters);
        return method;
    }

    private Instruction parseInstruction(String line) {
        String[] parts = line.split("\\s+");
        return switch (parts[0]) {
            case "const" -> new ConstInstruction(Integer.parseInt(parts[1]));
            case "load" -> new LoadInstruction(parts[1]);
            case "store" -> new StoreInstruction(parts[1]);
            case "add" -> new AddInstruction();
            case "sub" -> new SubInstruction();
            case "mul" -> new MulInstruction();
            case "div" -> new DivInstruction();
            case "new" -> new NewInstruction(parts[1]);
            case "get" -> new GetInstruction(parts[1]);
            case "set" -> new SetInstruction(parts[1]);
            case "call" -> new CallInstruction(parts[1]);
            case "eq" -> new EqInstruction();
            case "ne" -> new NeInstruction();
            case "gt" -> new GtInstruction();
            case "ge" -> new GeInstruction();
            case "lt" -> new LtInstruction();
            case "le" -> new LeInstruction();
            case "if" -> new IfInstruction(Integer.parseInt(parts[1]));
            case "else" -> new ElseInstruction(Integer.parseInt(parts[1]));
            case "ret" -> new ReturnInstruction();
            case "pop" -> new PopInstruction();
            default -> null;
        };
    }
}
