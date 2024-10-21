import Util.RegexUtil;

import java.util.List;

public class Method {
    private  String name;
    private  String instruction;
    private  List<String> atributos;



    public Method(String methodo) {
        this.name = methodo;
        this.atributos = RegexUtil.extractVars(methodo);
        this.instruction = RegexUtil.extractMethodBody(methodo);

    }

    public void process(){
        Storage.addInstruction(instruction);
        this.atributos.add("processado");
    }

    public String getName() {
        return name;
    }
}
