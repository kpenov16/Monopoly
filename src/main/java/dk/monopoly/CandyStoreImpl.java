package dk.monopoly;

import dk.monopoly.ports.Field;

public class CandyStoreImpl extends Field {
    public final static String NAME = "Candy Store";

    public CandyStoreImpl(){
        setIndex(currentFieldIndex++);
        setFieldColor(FieldColor.CYAN);
        setPrice(100);
        setRent(10);
    }
}
