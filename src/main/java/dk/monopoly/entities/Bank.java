package dk.monopoly.entities;

import java.util.ArrayList;
import java.util.List;

public abstract class Bank {
    List<Field> fields = new ArrayList<>();
    private Account account;
    private String name = "default";

    public void addProperty(Field field){
        fields.add(field);
        field.setBank(this);
    }

    public int getBalance() {
       return account.getBalance();
    }

    private Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void sellProperty(Field field, Player owner) {
        account.add(field.getPrice());
        field.setOwner(owner);
    }
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }

    public void pay(int sum) {
        account.add(sum);
    }

    public void pay(Player player, int sum){
        account.subtract(sum);
        player.addToBalance(sum);
    };
}
