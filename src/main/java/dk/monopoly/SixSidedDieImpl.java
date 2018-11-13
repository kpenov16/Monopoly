package dk.monopoly;

import dk.monopoly.ports.Die;

public class SixSidedDieImpl extends Die {
    public int roll() {
        value = (int)(Math.random()*6) + 1;
        return value;
    }

}
