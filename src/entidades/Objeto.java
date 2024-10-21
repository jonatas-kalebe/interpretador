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

    public Objeto(Classe classe) {
        this.id = ID_GENERATOR.getAndIncrement();
        this.classe = classe;
        this.atributos = new HashMap<>();

        // Inicializar atributos
        for (String attr : classe.getAtributos()) {
            atributos.put(attr, 0);
        }

        // Atributo especial _prototype
        atributos.put("_prototype", null);
        this.prototype = null;
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
            if (name.equals("_prototype")) {
                if (this.prototype != null) {
                    return this.prototype.getId();
                } else {
                    return null;
                }
            }
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
            if (name.equals("_prototype")) {
                if (value instanceof Integer) {
                    this.prototype = ObjetoManager.getInstance().getObjeto((Integer) value);
                } else {
                    this.prototype = null;
                }
            }
        } else {
            atributos.put(name, value);
        }
    }

    public Objeto getPrototype() {
        return prototype;
    }
}
