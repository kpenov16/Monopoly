package dk.monopoly;

import dk.monopoly.ports.Field;

public class LibraryImpl extends Field {
    public final static String NAME = "Library";

    public LibraryImpl(){
        setIndex(currentFieldIndex++);
        setFieldColor(FieldColor.MAGENTA);
        setPrice(200);
        setRent(20);
    }
}
