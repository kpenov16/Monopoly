package dk.monopoly;

import dk.monopoly.ports.Field;

public class ParkingImpl extends Field {
    public static final String NAME = "Parking";

    public ParkingImpl(){
        setIndex(currentFieldIndex++);
        setFieldColor(FieldColor.WHITE);
    }
}
