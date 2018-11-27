package dk.monopoly.entities.impls;

import dk.monopoly.entities.Field;

public class PrisonGuestImpl extends Field {
    public final static String NAME = "Prison Guest";

    public PrisonGuestImpl(){
        setIndex(currentFieldIndex++);
    }
}
