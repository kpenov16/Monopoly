package dk.monopoly.entities.impls;

import dk.monopoly.entities.Die;

public class SixSidedDieImpl extends Die {
    public int roll() {
        value = (int)(Math.random()*6) + 1;
        return value;
    }

}
