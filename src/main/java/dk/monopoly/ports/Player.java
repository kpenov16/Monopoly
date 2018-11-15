package dk.monopoly.ports;

public abstract class Player extends Owner{
    protected Hand hand;
    protected InfoService infoService;

    public abstract int getBalance();

    public abstract Account getAccount();

    protected abstract void setAccount( Account account);

    public abstract InfoService getInfoService();

    protected abstract void setInfoService( InfoService infoService);

    public abstract boolean isWinner();

    public abstract void play();

    protected abstract void setHand(Hand hand);

    public abstract String getLocation();

    public abstract String getMessage();

    public abstract int getDie(int index);

    public abstract void buy(Field field);

    public abstract boolean ownsTwoFields(Field.FieldColor fieldColor);
}
