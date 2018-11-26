package dk.monopoly.common;

import dk.monopoly.ports.RollingDiceResponse;

public class RollingDiceViewModel {
    public Integer currentFieldIndex = null;
    public String landlordName = null;
    public Integer landlordBalance = null;
    public Boolean playerPaysRent = null;
    public RollingDiceResponse.PreviousFieldType callingFieldType = RollingDiceResponse.PreviousFieldType.NORMAL_ROLL;
    Integer firstDie = null;
    Integer secondDie = null;
    Integer balance = null;
    String playerName = null;
    String msg = null;
    Boolean isWinner = false;
}
