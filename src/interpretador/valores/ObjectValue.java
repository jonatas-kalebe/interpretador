package interpretador.valores;

public class ObjectValue extends Value {
    private int objectId;

    public ObjectValue(int objectId) {
        this.objectId = objectId;
    }

    public int getObjectId() {
        return objectId;
    }

}

