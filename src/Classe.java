import Util.RegexUtil;

import java.util.List;

public class Classe {
    List<String> atributos;
    List<Method> metodos;
    String nome;

    public Classe(String classe) {
        this.atributos = RegexUtil.extractVars(classe);
        List<String> methods = RegexUtil.extractMethods(classe);
        for (String method : methods) {
            this.metodos.add(new Method(method));
        }
    }

    public String getNome() {
        return nome;
    }
    public List<String> getAtributos() {
        return  atributos;
    }
}
