import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Objeto {
    private final Map<String, String> atributos;
    private final Classe classeOrigem;

    public Objeto(List<String> atributos, Classe classeOrigem) {
        this.atributos = new HashMap<>();
        for (String atributo : atributos) {
            this.atributos.put(atributo, "");
        }
        this.classeOrigem = classeOrigem;
    }

    public void addAtributo(String nome, String valor) {
        atributos.put(nome, valor);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Objeto da Classe: ").append(classeOrigem.getNome()).append("\n");
        sb.append("Atributos:");

        for (Map.Entry<String, String> entry : atributos.entrySet()) {
            sb.append(" - ").append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }

        return sb.toString();
    }
}
