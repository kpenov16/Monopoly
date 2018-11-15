package dk.monopoly.ports;

public abstract class Field {
    public enum FieldColor{
        RED{
            @Override
            public String toString() {
                return "red";
            }
        },
        GREEN{
            @Override
            public String toString() {
                return "green";
            }
        },
        NONE
    }
    private FieldColor fieldColor;
    private Player owner;
    private int price;
    private int rent = -1;
    private Bank bank;

    public FieldColor getFieldColor() {
        return fieldColor;
    }
    protected void setFieldColor(FieldColor fieldColor) {
        this.fieldColor = fieldColor;
    }
    public boolean hasOwner(){
        return owner != null;
    }
    protected void setOwner(Player owner){
        this.owner = owner;
    }
    protected Player getOwner(){return owner;};
    public void setBuyer(Player player) {
        if(getOwner()==null){
            //player.getAccount().subtract(getPrice());
            //setOwner(player);
            bank.sellProperty(this, player);
        }
    }
    public boolean ownedBy(Player player) {
        return getOwner().equals(player);
    }
    public int getPrice(){
        return price;
    }

    protected void setPrice(int price){
        this.price = price;
    }

    public int getRent(){
        if(owner!=null && owner.ownsTwoFields(fieldColor))
            return 2*rent;
        return rent;
    }
    protected void setRent(int rent){
        this.rent = rent;
    }

    public void rent(Player player){
        if( getOwner().equals(player) ){
            //do nothing
        }else {
            player.getAccount().subtract(getRent());
            owner.getAccount().add(getRent());

        }
    }

    protected void setBank(Bank bank) {
        this.bank = bank;
    }
}

