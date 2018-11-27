package dk.monopoly.entities.impls;

import dk.monopoly.entities.Field;

public class BurgerImpl extends Field {
    public final static String NAME = "Burger";
    public BurgerImpl(){
        setIndex(currentFieldIndex++);
        setFieldColor(FieldColor.ORANGE);
        setPrice(100);
        setRent(10);
    }
}
