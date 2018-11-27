package dk.monopoly.entities.impls;

import dk.monopoly.entities.Field;

public class ZooImpl extends Field {
    public static final String NAME = "Zoo";

    public ZooImpl(){
        setIndex(currentFieldIndex++);
        setFieldColor(FieldColor.GREEN);
        setPrice(400);
        setRent(40);
    }
}
