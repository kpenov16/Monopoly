package dk.monopoly.entities.impls;

import dk.monopoly.entities.Field;

public class LibraryImpl extends Field {
    public final static String NAME = "Library";

    public LibraryImpl(){
        setIndex(currentFieldIndex++);
        setFieldColor(FieldColor.MAGENTA);
        setPrice(200);
        setRent(20);
    }
}
