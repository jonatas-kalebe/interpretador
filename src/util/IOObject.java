package util;

import executadores.ObjectInstance;
import valores.IntValue;
import valores.ObjectValue;
import valores.Value;

public class IOObject extends ObjectInstance {
    public IOObject(int id) {
        super(id, null);
    }

    public void print(Value value) {
        if (value instanceof IntValue) {
            System.out.println(((IntValue) value).getValue());
        } else if (value instanceof ObjectValue) {
            System.out.println("Object@" + ((ObjectValue) value).getObjectId());
        } else {
            System.out.println("Unknown value");
        }
    }
}

