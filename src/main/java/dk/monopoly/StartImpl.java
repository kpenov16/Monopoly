package dk.monopoly;

import dk.monopoly.ports.Field;

public class StartImpl extends Field {
    public final static String NAME = "Start";
    public final static int START_BONUS = 200;
    public StartImpl(){
        setIndex(currentFieldIndex++);
    }
}
