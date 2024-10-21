package entidades;

import java.util.*;

public class Classe {
    private final String nome;
    private final List<String> atributos;
    private final Map<String, Method> metodos;

    public Classe(String nome) {
        this.nome = nome;
        this.atributos = new ArrayList<>();
        this.metodos = new HashMap<>();
    }

    public void addAttributes(List<String> attrs) {
        atributos.addAll(attrs);
    }

    public void addMethod(Method method) {
        metodos.put(method.getName(), method);
    }

    public String getNome() {
        return nome;
    }

    public List<String> getAtributos() {
        return atributos;
    }

    public Method getMethod(String methodName) {
        return metodos.get(methodName);
    }

    public Collection<Method> getMethods() {
        return metodos.values();
    }
}
