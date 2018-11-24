package dk.monopoly.ports;

import dk.monopoly.MoveToStartCard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Chance extends Field {
    protected static List<ChanceCard> chanceCards = new ArrayList<>(
            Arrays.asList(  new BuyAFieldAndGetOneMoreChanceCard(),
                            new MoveToStartCard(),
                            new Move5FieldsAhead(),
                            new MoveToOrangeAndGetForFreeIfForSell(),
                            new MoveOneAheadOrTakeAChanceCardMore(),
                            new NextTurnBuyAFieldForSellOrFromAnotherPlayer(),
                            new Pay200ToTheBank(),
                            new GetOrangeOrGreenFieldForFreeIfForSellOtherwizePayRent(),
                            new GetCyanFieldForFreeIfForSellOtherwizePayRent(),
                            new GetOutOfPrison(),
                            new MoveToTheSeafrontField(),
                            new NextTurnBuyAFieldForSellOrFromAnotherPlayer(),
                            new NextTurnBuyAFieldForSellOrFromAnotherPlayer(),
                            new YouGet100FromEachPlayer(),
                            new GetPinkOrBlueFieldForFreeIfForSellOtherwizePayRent(),
                            new Get200FromTheBank(),
                            new GetRedFieldForFreeIfForSellOtherwizePayRent(),
                            new GetSkateParkFieldForFreeIfForSellOtherwizePayRent()
                            ) );
    protected static int nextChanceCardIndex = 0;
    protected int getNextChanceCardIndex() {
        if( nextChanceCardIndex > (chanceCards.size()-1) )
            return nextChanceCardIndex = 0;
        return nextChanceCardIndex++;
    }

}
