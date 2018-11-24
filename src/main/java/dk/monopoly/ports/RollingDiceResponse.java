package dk.monopoly.ports;

import dk.monopoly.common.RollingDiceViewModel;

public abstract class RollingDiceResponse {
    public String playerName = null;
    public Integer firstDie = null;
    public Integer balance = null;
    public Boolean isFinished = null;
    public String msg = null;
    public Integer currentFieldIndex = null;
    public Landlord landlord = new Landlord();
    public Boolean playerPaysRent = null;

    public class Landlord {
        public String name=null;
        public Integer balance=null;
    }
}
