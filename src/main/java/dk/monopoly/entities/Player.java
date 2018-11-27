package dk.monopoly.entities;

public abstract class Player extends Owner{
    protected Hand hand;
    protected InfoService infoService;
    protected ChanceCard chanceCard;
    protected Bank bank;

    public Bank getBank(){
        return bank;
    }

    public abstract int getBalance();

    public abstract Account getAccount();

    protected abstract void setAccount( Account account);

    public abstract InfoService getInfoService();

    protected abstract void setInfoService( InfoService infoService);

    public abstract boolean isWinner();

    public abstract void play();

    public abstract void roll();

    protected abstract void setHand(Hand hand);

    public abstract String getLocation();

    public abstract String getMessage();

    public abstract int getDie(int index);

    public abstract void buy(Field field);

    public abstract boolean ownsTwoFields(Field.FieldColor fieldColor);

    public abstract int getCurrentFieldIndex();
    public abstract void setCurrentField(Field field);

    public abstract void addToBalance(int addend);

    public abstract void pay(Player player, int sum);
    public abstract void pay(String bankName, int sum);

    public abstract void setChanceCard(ChanceCard chanceCard);

    public abstract void setBank(Bank bank);
}
