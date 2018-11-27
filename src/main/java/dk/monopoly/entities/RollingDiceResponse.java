package dk.monopoly.entities;

public abstract class RollingDiceResponse {
    public String playerName = null;
    public Integer firstDie = null;
    public Integer balance = null;
    public Boolean isFinished = null;
    public String msg = null;
    public Integer currentFieldIndex = null;
    public Landlord landlord = new Landlord();
    public Boolean playerPaysRent = null;
    public PreviousFieldType callingField = null;

    public class Landlord {
        public String name=null;
        public Integer balance=null;
    }
    public enum PreviousFieldType {
        CHANCE,
        NORMAL_ROLL,
        SPECIAL_ROLL
    }
}
