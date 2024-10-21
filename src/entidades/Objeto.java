package entidades;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Objeto {
    private static final AtomicInteger ID_GENERATOR = new AtomicInteger(1);

    private final int id;
    private final Classe classe;
    private final Map<String, Object> atributos;
    private Objeto prototype;
    private boolean marked;

    public Objeto(Classe classe) {
        this.id = ID_GENERATOR.getAndIncrement();
        this.classe = classe;
        this.atributos = new HashMap<>();
        this.prototype = null;
        this.marked = false;

        // Inicializar atributos
        for (String attr : classe.getAtributos()) {
            atributos.put(attr, 0);
        }

        // Atributo especial _prototype
        atributos.put("_prototype", null);
    }

    public int getId() {
        return id;
    }

    public Classe getClasse() {
        return classe;
    }

    public Map<String, Object> getAttributes() {
        return atributos;
    }

    public Object getAttribute(String name) {
        if (atributos.containsKey(name)) {
            return atributos.get(name);
        } else if (prototype != null) {
            return prototype.getAttribute(name);
        } else {
            return null;
        }
    }

    public void setAttribute(String name, Object value) {
        if (atributos.containsKey(name)) {
            atributos.put(name, value);
        } else if (prototype != null) {
            prototype.setAttribute(name, value);
        } else {
            atributos.put(name, value);
        }

        if (name.equals("_prototype") && value instanceof Integer) {
            this.prototype = (Objeto) value;
        }
    }

    public Objeto getPrototype() {
        return prototype;
    }

    public boolean isMarked() {
        return marked;
    }

    public void setMarked(boolean marked) {
        this.marked = marked;
    }
}
