package dk.monopoly.gui.viewmodels;

import dk.monopoly.entities.RollingDiceResponse;

public class RollingDiceViewModel {
    public Integer currentFieldIndex = null;
    public String landlordName = null;
    public Integer landlordBalance = null;
    public Boolean playerPaysRent = null;
    public RollingDiceResponse.PreviousFieldType callingFieldType = RollingDiceResponse.PreviousFieldType.NORMAL_ROLL;
    public Integer firstDie = null;
    public Integer secondDie = null;
    public Integer balance = null;
    public String playerName = null;
    public String msg = null;
    public Boolean isWinner = false;
}
