package dk.monopoly;

import dk.monopoly.ports.Field;

public class SeafrontImpl extends Field {
    public static final String NAME = "Seafront";

    public SeafrontImpl(){
        setIndex(currentFieldIndex++);
        setFieldColor(FieldColor.BLUE);
        setPrice(500);
        setRent(50);
    }
}
