package dk.monopoly;

import dk.monopoly.ports.*;

import java.util.ArrayList;
import java.util.List;

public class MonopolyPlayer extends Player {
    protected Account account;
    private List<Field> ownedFields = new ArrayList<>();
    private Field currentField = null;
    public MonopolyPlayer(Account account){
        setAccount(account);
    }

    @Override
    public String getName() {
        return super.name;
    }

    @Override
    public void setName(String name) {
        super.name = name;
    }

    @Override
    public int getBalance() {
        return account.getBalance();
    }

    @Override
    public Account getAccount() {
        return account;
    }

    protected void setAccount(Account account){
        account.setOwner(this);
        this.account = account;
    }

    @Override
    public InfoService getInfoService() {
        return infoService;
    }

    @Override
    public void setInfoService(InfoService infoService) {
        super.infoService = infoService;
    }

    @Override
    public boolean isWinner() {
        return getBalance() >= 3000;
    }

    @Override
    public void play() {
        int point = hand.roll();
        infoService.setPoints(point);
        int score = infoService.getScore();
        if(score > 0)
            account.add(score);
        else
            account.subtract(Math.abs(score));
    }

    @Override
    public void roll(){
        hand.roll();
    }

    @Override
    public void setHand(Hand hand) {
        super.hand = hand;
    }

    @Override
    public String getLocation() {
        return super.infoService.getLocation();
    }

    @Override
    public String getMessage() {
        return super.infoService.getMessage();
    }

    @Override
    public int getDie(int index) {
        return hand.getDie(index);
    }

    @Override
    public void buy(Field field) {
        account.subtract(field.getPrice());
        this.ownedFields.add(field);
        field.setBuyer(this);
    }

    @Override
    public boolean ownsTwoFields(Field.FieldColor fieldColor) {
        int counts = 0;
        for(Field f : ownedFields){
            if(f.getFieldColor().toString().equals(fieldColor.toString())) {
                if(++counts == 2)
                    return true;
            }
        }
        return false;
    }

    @Override
    public int getCurrentFieldIndex() {
        return currentField.getIndex();
    }

    @Override
    public void setCurrentField(Field field) {
        this.currentField = field;
    }

    @Override
    public void addToBalance(int addend) {
        account.add(addend);
    }

    @Override
    public void pay(Player payee, int amount) {
        account.subtract(amount);
        payee.addToBalance(amount);
    }


}
