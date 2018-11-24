package dk.monopoly;

import dk.monopoly.ports.Chance;
import dk.monopoly.ports.ChanceCard;
import dk.monopoly.ports.Field;

import java.util.ArrayList;
import java.util.List;

public class ChanceImpl extends Chance {
    public final static String NAME = "Chance";
    ChanceCard chanceCard = null;
    public ChanceImpl(){
        setIndex(currentFieldIndex++);
        setChanceCard(chanceCards.get(getNextChanceCardIndex()));
    }
    private void setChanceCard(ChanceCard chanceCard) {
        this.chanceCard = chanceCard;
    }
    public ChanceCard getChanceCard(){
        return this.chanceCard;
    }
}
