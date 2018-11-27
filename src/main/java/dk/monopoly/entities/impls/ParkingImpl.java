package dk.monopoly.entities.impls;

import dk.monopoly.entities.Field;

public class ParkingImpl extends Field {
    public static final String NAME = "Parking";

    public ParkingImpl(){
        setIndex(currentFieldIndex++);
        setFieldColor(FieldColor.WHITE);
    }
}
