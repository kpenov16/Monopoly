package dk.monopoly.entities.impls;

import dk.monopoly.entities.Chance;
import dk.monopoly.entities.ChanceCard;

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
