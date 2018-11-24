package dk.monopoly;

import dk.monopoly.ports.Field;

public class PrisonGuestImpl extends Field {
    public final static String NAME = "Prison Guest";

    public PrisonGuestImpl(){
        setIndex(currentFieldIndex++);
    }
}
