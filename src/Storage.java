import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Storage {
    private static Stack<String> pilha= new Stack<>();
    private static List<Objeto> objetos=new ArrayList<>();
    private static int contadorInstrucoes=0;

    public static void addInstruction(String s){
        if (contadorInstrucoes==5){
            garbageCollector();
            contadorInstrucoes=0;
        }
        pilha.push(s);
        contadorInstrucoes++;
    }

    public static String getInstruction(){
        return pilha.pop();
    }

    public static void addObjeto(Objeto o){
        objetos.add(o);
    }

    public static void garbageCollector(){
        objetos.clear();
    }


}
