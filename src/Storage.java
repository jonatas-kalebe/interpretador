import java.util.*;

public class Storage {
    private static Stack<String> pilha= new Stack<>();
    private static Map<String,String> valoresDaPilha=new HashMap<>();
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
        try {
            String instrucao= pilha.pop();
            return instrucao;
        } catch (Exception e) {
            return "";
        }


    }

    public static void addObjeto(Objeto o){
        objetos.add(o);
    }

    public static void garbageCollector(){
        //objetos.remove(new Objeto(null,null));
    }


}
