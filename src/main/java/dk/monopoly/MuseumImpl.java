package dk.monopoly;

import dk.monopoly.ports.Field;

public class MuseumImpl extends Field {
    public final static String NAME = "Museum";

    public MuseumImpl(){
        setIndex(currentFieldIndex++);
        setFieldColor(FieldColor.MAGENTA);
        setPrice(200);
        setRent(20);
    }
}
