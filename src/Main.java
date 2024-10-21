import Util.RegexUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private final String globalInstructions;
    List<String> mainInstructions;
    private final Map<String,String> atributos;
    private final List<Classe> classes;

    public Main(String globalInstructions, Map<String,String> atributos, List<Classe> classes) {
        this.globalInstructions = globalInstructions;
        this.atributos = atributos;
        this.classes = classes;
    }

    public Main(String globalInstructions, Map<String,String> atributos) {
        this(globalInstructions, atributos, new ArrayList<>());
    }

    public Main(String globalInstructions, List<Classe> classes) {
        this(globalInstructions, new HashMap<>(), classes);
    }

    public Main(String globalInstructions) {
        this(globalInstructions, new HashMap<>(), new ArrayList<>());
    }

    public void process(){
        String mainBlock= RegexUtil.extractMain(globalInstructions).getFirst();
        this.mainInstructions=List.of(mainBlock.split("\n"));
        List<String> vars=RegexUtil.extractVars(mainBlock);
        for (String var : vars) {
           this.atributos.put(var,"");
        }
        List<String> classes=RegexUtil.extractClasses(globalInstructions);
        for (String classe : classes) {
            Classe c=new Classe(classe);
            this.classes.add(c);
        }
    }
}
