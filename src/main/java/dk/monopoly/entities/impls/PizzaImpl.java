package dk.monopoly.entities.impls;

import dk.monopoly.entities.Field;

public class PizzaImpl extends Field {
    public final static String NAME = "Pizza";

    public PizzaImpl(){
        setIndex(currentFieldIndex++);
        setFieldColor(FieldColor.ORANGE);
        setPrice(100);
        setRent(10);
    }
}
