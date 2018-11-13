package dk.monopoly.ports;

public abstract class Field {
    private Player owner;
    private int price;
    public boolean hasOwner(){
        return owner != null;
    }
    protected void setOwner(Player owner){
        this.owner = owner;
    }
    protected Owner getOwner(){return owner;};
    public abstract void buy(Player player);

    public abstract boolean ownedBy(Player player);

    public int getPrice(){
        return price;
    }

    protected void setPrice(int price){
        this.price = price;
    }
}
