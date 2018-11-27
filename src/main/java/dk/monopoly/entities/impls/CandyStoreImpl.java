package dk.monopoly.entities.impls;

import dk.monopoly.entities.Field;

public class CandyStoreImpl extends Field {
    public final static String NAME = "Candy Store";

    public CandyStoreImpl(){
        setIndex(currentFieldIndex++);
        setFieldColor(FieldColor.CYAN);
        setPrice(100);
        setRent(10);
    }
}
