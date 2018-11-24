package dk.monopoly;

import dk.monopoly.ports.Field;

public class SwimmingPoolImpl extends Field {
    public static final String NAME = "Swimming Pool";

    public SwimmingPoolImpl(){
        setIndex(currentFieldIndex++);
        setFieldColor(FieldColor.PINK);
        setPrice(200);
        setRent(20);
    }
}
