package dk.monopoly.entities.impls;

import dk.monopoly.entities.Field;

public class PrisonImpl extends Field {
    public static final String NAME = "Prison";

    public PrisonImpl(){
        setIndex(currentFieldIndex++);
        setFieldColor(FieldColor.WHITE);
    }
}
