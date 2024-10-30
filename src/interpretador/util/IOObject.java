package interpretador.util;

import interpretador.entidades.ObjectInstance;
import interpretador.valores.IntValue;
import interpretador.valores.ObjectValue;
import interpretador.valores.Value;

public class IOObject extends ObjectInstance {
    public IOObject(int id) {
        super(id, null);
    }

    public void print(Value value) {
        if (value instanceof IntValue) {
            System.out.println(((IntValue) value).getValue());
        }
    }
}

