package entidades;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ObjetoManager {
    private static ObjetoManager instance;
    private final Map<Integer, Objeto> heap;

    private ObjetoManager() {
        heap = new HashMap<>();
    }

    public static ObjetoManager getInstance() {
        if (instance == null) {
            instance = new ObjetoManager();
        }
        return instance;
    }

    public void addObjeto(Objeto obj) {
        heap.put(obj.getId(), obj);
    }

    public Objeto getObjeto(int id) {
        return heap.get(id);
    }

    public void removeObjeto(int id) {
        heap.remove(id);
    }

    public void removeUnreachableObjects(Set<Integer> reachable) {
        heap.entrySet().removeIf(entry -> !reachable.contains(entry.getKey()));
    }
}
