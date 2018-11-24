package dk.monopoly;

import dk.monopoly.ports.Field;

public class SkateParkImpl extends Field {
    public static final String NAME = "SkatePark";

    public SkateParkImpl(){
        setIndex(currentFieldIndex++);
        setFieldColor(FieldColor.PINK);
        setPrice(200);
        setRent(20);
    }
}
