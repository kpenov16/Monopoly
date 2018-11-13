package dk.monopoly.common;

import dk.monopoly.*;
import dk.monopoly.ports.*;

public class Context {

    public static Player createPlayer(){
        MonopolyPlayer p = new MonopolyPlayer( Context.createAccount() );
        p.setHand( Context.createHand(2) );
        p.setInfoService(Context.createInfoService("DK"));
        return p;
    }

    public static Hand createHand(int diceNumber){
        Hand h = new HandImpl();
        Die[] dice = new Die[diceNumber];
        for (int i=0; i<dice.length; i++){
            dice[i] = Context.createDie(6);
        }
        h.setDice(dice);
        return h;
    }

    public static Die createDie(int numberOfSides){
        if(numberOfSides == 6){
            return new SixSidedDieImpl();
        }/*else if(numberOfSides == 7){
            return new SevenSidedDieImpl();
        }*/
        return null;
    }

    public static FieldsInitializer createFieldsInitializer(){
        return new FieldsInitializer();
    }

    public static InfoService createInfoService(String language) {
        MonopolyInfoService diceGame2InfoService = new MonopolyInfoService();
        MessageBag messageBag = Context.createMessageBag();
        diceGame2InfoService.setMessageBag(messageBag);
        if (language.equals("DK")) {
            FieldsInitializer fieldsInitializer = Context.createFieldsInitializer();
            fieldsInitializer.populateFields(messageBag, "DK.txt");
        }
        return diceGame2InfoService;
    }

    public static MessageBag createMessageBag() {
        return new MessageBag();
    }

    public static Account createAccount(){
        return new MonopolyAccount();
    }
}
