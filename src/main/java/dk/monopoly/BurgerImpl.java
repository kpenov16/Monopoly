package dk.monopoly;

import dk.monopoly.ports.Field;

public class BurgerImpl extends Field {
    public final static String NAME = "Burger";
    public BurgerImpl(){
        setIndex(currentFieldIndex++);
        setFieldColor(FieldColor.ORANGE);
        setPrice(100);
        setRent(10);
    }
}
