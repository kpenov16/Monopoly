package dk.monopoly;

import dk.monopoly.ports.Field;

public class TheaterImpl extends Field {
    public static final String NAME = "Theater";

    public TheaterImpl(){
        setIndex(currentFieldIndex++);
        setFieldColor(FieldColor.RED);
        setPrice(300);
        setRent(30);
    }
}
