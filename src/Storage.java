import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class Storage {
    private static Stack<String> pilha= new Stack<>();
    private static Map<String,String> valoresDaPilha;
    private static List<Objeto> objetos=new ArrayList<>();
    private static int contadorInstrucoes=0;

    public static void addInstruction(String instrucao){
        if (contadorInstrucoes==5){
            garbageCollector();
            contadorInstrucoes=0;
        }
        pilha.push(instrucao);
        contadorInstrucoes++;
    }

    public static void addInstruction(String instrucao,String valor){
        if (contadorInstrucoes==5){
            garbageCollector();
            contadorInstrucoes=0;
        }
        pilha.push(instrucao);
        valoresDaPilha.put(instrucao,valor);
        contadorInstrucoes++;
    }

    public static String getInstruction(){
        String instrucao= pilha.pop();
        String valor=valoresDaPilha.get(instrucao);
        return valor;
    }

    public static void addObjeto(Objeto o){
        objetos.add(o);
    }

    public static void garbageCollector(){
        objetos.add(new Objeto());
    }


}
