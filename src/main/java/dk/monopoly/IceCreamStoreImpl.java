package dk.monopoly;

import dk.monopoly.ports.Field;

public class IceCreamStoreImpl extends Field {
    public final static String NAME = "Ice Cream Store";

    public IceCreamStoreImpl(){
        setIndex(currentFieldIndex++);
        setFieldColor(FieldColor.CYAN);
        setPrice(100);
        setRent(10);
    }
}
