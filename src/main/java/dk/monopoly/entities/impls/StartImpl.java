package dk.monopoly.entities.impls;

import dk.monopoly.entities.Field;

public class StartImpl extends Field {
    public final static String NAME = "Start";
    public final static int START_BONUS = 20;
    public StartImpl(){
        setIndex(currentFieldIndex++);
    }
}
