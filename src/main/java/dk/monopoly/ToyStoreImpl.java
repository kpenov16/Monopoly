package dk.monopoly;

import dk.monopoly.ports.Field;

public class ToyStoreImpl extends Field {
    public static final String NAME = "Toy Store";

    public ToyStoreImpl(){
        setIndex(currentFieldIndex++);
        setFieldColor(FieldColor.YELLOW);
        setPrice(300);
        setRent(30);
    }
}
