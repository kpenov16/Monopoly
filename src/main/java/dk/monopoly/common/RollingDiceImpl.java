package dk.monopoly.common;

import dk.monopoly.*;
import dk.monopoly.ports.*;

public class RollingDiceImpl {
    private RollingDicePresenter rollingDicePresenter;
    private PlayerGatewayImpl playerGatewayImpl;
    private FieldGateway fieldGateway;

    public RollingDiceImpl(RollingDicePresenter rollingDicePresenter,
                           PlayerGatewayImpl playerGatewayImpl,
                           FieldGateway fieldGateway) {
        this.rollingDicePresenter = rollingDicePresenter;
        this.playerGatewayImpl = playerGatewayImpl;
        this.fieldGateway = fieldGateway;
    }

    private void collectStartBonus(Player player){
        player.addToBalance(StartImpl.START_BONUS);
    }

    public void execute(String playerName) {
        RollingDiceResponse response = new RollingDiceResponseImpl();
        String msg = "";
        Player player = playerGatewayImpl.getPlayerMyName(playerName);
        player.roll();
        int die = player.getDie(0);
        int currentFieldIndex = player.getCurrentFieldIndex();
        int calculatedIndex = currentFieldIndex + die;
        Field field=null;
        if(calculatedIndex == 24) {//on the start
            collectStartBonus(player);
            calculatedIndex = 0;
            player.setCurrentField(fieldGateway.getFieldByIndex(calculatedIndex));
            msg = "Bonus on Start " + StartImpl.START_BONUS;
        }else if(calculatedIndex > 24) {//crossing the start
            collectStartBonus(player);
            calculatedIndex = calculatedIndex - 24;
            msg = "Bonus of " + StartImpl.START_BONUS + " for passing the Start\n";
            //player.setCurrentField(fieldGateway.getFieldByIndex(calculatedIndex));
            msg += executeBeforeStart(player, calculatedIndex, response);
        }else{ //before start
            msg = executeBeforeStart(player, calculatedIndex, response);
        }


        response.playerName = player.getName();
        response.balance = player.getBalance();
        response.firstDie = player.getDie(0);
        response.currentFieldIndex = calculatedIndex;
        response.isFinished = (response.balance <= 0)?true:false;
        response.msg = msg;
        //player.play();
        int balance = player.getBalance();
        int firstDie = player.getDie(0);
        int secondDie = player.getDie(1);
        boolean isWinner = player.isWinner();
        //String msg = player.getMessage();
        rollingDicePresenter.present(response);
    }

    private String executeBeforeStart(Player player, int calculatedIndex, RollingDiceResponse response) {
        Field field;
        String msg;
        field = fieldGateway.getFieldByIndex(calculatedIndex);
        if(field instanceof Chance){
            ChanceImpl chance = (ChanceImpl)field;
            ChanceCard chanceCard = chance.getChanceCard();
            msg = "Chance";
        }else if (field instanceof PrisonGuestImpl){
            msg = "PrisonGuestImpl";
            player.setCurrentField(field);
        }else if (field instanceof ParkingImpl){
            msg = "ParkingImpl";
            player.setCurrentField(field);
        }else if (field instanceof PrisonImpl){
            msg = "PrisonImpl";
        }else{
            if(!field.hasOwner()){//buy the field
                player.buy(field);
                player.setCurrentField(field);
                msg = "field price: " + field.getPrice();
            }else if(!field.ownedBy(player)){//rent the field
                field.rent(player);
                player.setCurrentField(field);
                msg = "field rent: :" + field.getRent();

                response.playerPaysRent = true;
                response.landlord.name = field.getLandlordName();
                response.landlord.balance = field.getLandlordBalance();
            }else {//player owns field
                player.setCurrentField(field);
                msg = "player owns field";
            }
        }
        return msg;
    }
}
