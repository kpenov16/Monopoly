package dk.monopoly;

import dk.monopoly.ports.Field;

public class BowlingImpl extends Field {
    public static final String NAME = "Bowling";

    public BowlingImpl(){
        setIndex(currentFieldIndex++);
        setFieldColor(FieldColor.GREEN);
        setPrice(400);
        setRent(40);
    }
}
