package dk.monopoly.entities.impls;

import dk.monopoly.entities.Field;

public class WaterParkImpl extends Field {
    public static final String NAME = "Water Park";

    public WaterParkImpl(){
        setIndex(currentFieldIndex++);
        setFieldColor(FieldColor.BLUE);
        setPrice(500);
        setRent(50);
    }
}
