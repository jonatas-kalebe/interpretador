import java.util.List;

public class Method {
    private final String name;
    private final String instruction;
    private final List<String> atributos;

    public Method(String name, String instruction, List<String> atributos) {
        this.name = name;
        this.instruction = instruction;
        this.atributos = atributos;
    }

    public void process(){
        Storage.addInstruction(instruction);
        this.atributos.add("processado");
    }

    public String getName() {
        return name;
    }
}
