package dk.monopoly.common;

import dk.monopoly.entities.*;
import dk.monopoly.entities.impls.*;

public class Context {
    private static BankImpl defaultBank = new BankImpl();
    private static Account defaultBankAccount = new MonopolyAccount();

    public static Bank getBank(String bankName){
        if(bankName.equals("default")){
            defaultBank.setAccount(defaultBankAccount);
            return defaultBank;
        }
        return null;
    }

    public static Bank createBank(){
        BankImpl bank = new BankImpl();
        bank.setAccount(Context.createAccount());
        return bank;
    }

    public static Field createField(String fieldType){
        Field field = null;
        if(fieldType.equals(StartImpl.NAME))
            field = new StartImpl();
        else if(fieldType.equals(BurgerImpl.NAME))
            field = new BurgerImpl();
        else if(fieldType.equals(PizzaImpl.NAME))
            field = new PizzaImpl();
        else if(fieldType.equals(ChanceImpl.NAME)) //chance
            field = new ChanceImpl();
        else if(fieldType.equals(IceCreamStoreImpl.NAME))
            field = new IceCreamStoreImpl();
        else if(fieldType.equals(CandyStoreImpl.NAME))
            field = new CandyStoreImpl();
        else if(fieldType.equals(PrisonGuestImpl.NAME)) //
            field = new PrisonGuestImpl();
        else if(fieldType.equals(MuseumImpl.NAME))
            field = new MuseumImpl();
        else if(fieldType.equals(LibraryImpl.NAME))
            field = new LibraryImpl();
        else if(fieldType.equals(SkateParkImpl.NAME))
            field = new SkateParkImpl();
        else if(fieldType.equals(SwimmingPoolImpl.NAME))
            field = new SwimmingPoolImpl();
        else if(fieldType.equals(ParkingImpl.NAME))
            field = new ParkingImpl();
        else if(fieldType.equals(TheaterImpl.NAME))
            field = new TheaterImpl();
        else if(fieldType.equals(CinemaImpl.NAME))
            field = new CinemaImpl();
        else if(fieldType.equals(ToyStoreImpl.NAME))
            field = new ToyStoreImpl();
        else if(fieldType.equals(PetShopImpl.NAME))
            field = new PetShopImpl();
        else if(fieldType.equals(PrisonImpl.NAME))
            field = new PrisonImpl();
        else if(fieldType.equals(BowlingImpl.NAME))
            field = new BowlingImpl();
        else if(fieldType.equals(ZooImpl.NAME))
            field = new ZooImpl();
        else if(fieldType.equals(WaterParkImpl.NAME))
            field = new WaterParkImpl();
        else if(fieldType.equals(SeafrontImpl.NAME))
            field = new SeafrontImpl();
        //this is part of a test and needs to be removed when the test is fixed
        else if(fieldType.equals("hotel"))
            field = new HotelImpl();

        if(field!=null)
            field.setBank(Context.getBank("default"));
        return field;

    }

    public static Player createPlayer(){
        MonopolyPlayer p = new MonopolyPlayer( Context.createAccount() );
        p.setHand( Context.createHand(2) );
        p.setInfoService(Context.createInfoService("DK"));
        return p;
    }

    public static Player createPlayer(String name){
        MonopolyPlayer p = new MonopolyPlayer( Context.createAccount() );
        p.setName(name);
        p.setHand( Context.createHand(2) );
        p.setInfoService(Context.createInfoService("DK"));
        return p;
    }

    public static Player createPlayer(String name, int balance, Bank bank){
        MonopolyPlayer p = new MonopolyPlayer( Context.createAccount(balance) );
        p.setName(name);
        p.setHand( Context.createHand(2) );
        p.setInfoService(Context.createInfoService("DK"));
        p.setBank(bank);
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

    private static Account createAccount(int balance) {
        Account account = new MonopolyAccount();
        account.setBalance(balance);
        return account;
    }

}
