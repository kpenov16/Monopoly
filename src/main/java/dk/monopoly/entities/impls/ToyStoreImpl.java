package dk.monopoly.entities.impls;

import dk.monopoly.entities.Field;

public class ToyStoreImpl extends Field {
    public static final String NAME = "Toy Store";

    public ToyStoreImpl(){
        setIndex(currentFieldIndex++);
        setFieldColor(FieldColor.YELLOW);
        setPrice(300);
        setRent(30);
    }
}
