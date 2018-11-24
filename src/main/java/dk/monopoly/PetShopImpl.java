package dk.monopoly;

import dk.monopoly.ports.Field;

public class PetShopImpl extends Field {
    public static final String NAME = "Pet Shop";

    public PetShopImpl(){
        setIndex(currentFieldIndex++);
        setFieldColor(FieldColor.YELLOW);
        setPrice(300);
        setRent(30);
    }
}
