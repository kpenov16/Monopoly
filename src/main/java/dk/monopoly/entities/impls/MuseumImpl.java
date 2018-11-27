package dk.monopoly.entities.impls;

import dk.monopoly.entities.Field;

public class MuseumImpl extends Field {
    public final static String NAME = "Museum";

    public MuseumImpl(){
        setIndex(currentFieldIndex++);
        setFieldColor(FieldColor.MAGENTA);
        setPrice(200);
        setRent(20);
    }
}
