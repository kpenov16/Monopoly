package dk.monopoly.entities;

import dk.monopoly.entities.impls.MoveToStartCard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Chance extends Field {
    protected static List<ChanceCard> chanceCards = new ArrayList<>(
            Arrays.asList(  new BuyAFieldAndGetOneMoreChanceCard(),
                            new MoveToStartCard(),//done
                            new Move5FieldsAhead(),//done
                            new MoveToOrangeAndGetForFreeIfForSell(),
                            new MoveOneAheadOrTakeAChanceCardMore(),
                            new NextTurnBuyAFieldForSellOrFromAnotherPlayer(),
                            new Pay200ToTheBank(),//done
                            new GetOrangeOrGreenFieldForFreeIfForSellOtherwizePayRent(),
                            new GetCyanFieldForFreeIfForSellOtherwizePayRent(),
                            new GetOutOfPrison(),//working
                            new MoveToTheSeafrontField(),
                            new NextTurnBuyAFieldForSellOrFromAnotherPlayer(),
                            new NextTurnBuyAFieldForSellOrFromAnotherPlayer(),
                            new YouGet100FromEachPlayer(),//done
                            new GetPinkOrBlueFieldForFreeIfForSellOtherwizePayRent(),
                            new Get200FromTheBank(),//done
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
