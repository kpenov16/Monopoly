package dk.monopoly;

import dk.monopoly.ports.Field;

public class CinemaImpl extends Field {
    public static final String NAME = "Cinema";

    public CinemaImpl(){
        setIndex(currentFieldIndex++);
        setFieldColor(FieldColor.RED);
        setPrice(300);
        setRent(30);
    }
}
