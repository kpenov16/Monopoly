package dk.monopoly.entities.impls;

import dk.monopoly.entities.Account;
import dk.monopoly.entities.Owner;

public class MonopolyAccount extends Account {

    @Override
    public void add(int addend) {
       balance += addend;
    }
    @Override
    public void subtract(int subtrahend){
        int tempBalance = balance - subtrahend;
        balance = (tempBalance < 0) ? 0 : tempBalance;
    }
    @Override
    public void setBalance(int newBalance) {
        balance = newBalance;
    }
    @Override
    public void setOwner(Owner newOwner) {
        owner = newOwner;
    }
    @Override
    public Owner getOwner() {
        return owner;
    }

    @Override
    public String getOwnerName() {
        return owner.getName();
    }

}
