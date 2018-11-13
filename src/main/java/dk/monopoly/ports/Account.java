package dk.monopoly.ports;

import java.io.Serializable;

public abstract class Account implements Serializable {
    //En spiller starter med 1000 kr i sin pengebeholdning.
    protected int balance = 1000;
    protected Owner owner;

    public int getBalance() {
        return balance;
    }
    public abstract void add(int addend);
    public abstract void subtract(int subtrahend);

    protected abstract void setBalance(int newBalance);

    public abstract void setOwner(Owner owner);
    public abstract Owner getOwner();

    public abstract String getOwnerName();
}
