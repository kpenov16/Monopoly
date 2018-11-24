package dk.monopoly;

import dk.monopoly.ports.Field;

public class PrisonImpl extends Field {
    public static final String NAME = "Prison";

    public PrisonImpl(){
        setIndex(currentFieldIndex++);
        setFieldColor(FieldColor.WHITE);
    }
}
