package dk.monopoly.usecases.rollingdice;

import dk.monopoly.common.RollingDiceResponseImpl;
import dk.monopoly.entities.*;
import dk.monopoly.entities.impls.*;
import dk.monopoly.gateways.FieldGateway;
import dk.monopoly.entities.RollingDiceResponse.PreviousFieldType;
import dk.monopoly.gateways.PlayerGateway;

public class RollingDiceImpl {

    private RollingDicePresenter presenter;
    private PlayerGateway playerGateway;
    private FieldGateway fieldGateway;
    public RollingDiceImpl(RollingDicePresenter presenter,
                           PlayerGateway playerGateway,
                           FieldGateway fieldGateway) {
        this.presenter = presenter;
        this.playerGateway = playerGateway;
        this.fieldGateway = fieldGateway;
    }
    private int nextFieldIndex;
    private boolean collectStartBonus = false;
    private Field field=null;
    private String msg="";
    private Player player;

    private int getNextFieldIndexByRoll(Player player){
        player.roll();
        int die = player.getDie(0);
        return player.getCurrentFieldIndex() + die;
    }
    private static int fiveFieldsAheadIndex=0;
    public void execute(String playerName, PreviousFieldType type) {
        player = playerGateway.getPlayerMyName(playerName);

        if(type == PreviousFieldType.NORMAL_ROLL){
            nextFieldIndex = getNextFieldIndexByRoll(player);
            fiveFieldsAheadIndex = 0;//reset
        }else if(type == PreviousFieldType.CHANCE)
            nextFieldIndex = fiveFieldsAheadIndex;

        msg = "";
        collectStartBonus = nextFieldIndex >= 24 ? true : false;
        if(collectStartBonus){
            nextFieldIndex = nextFieldIndex - 24;
            collectStartBonus(player);
            msg += "Start bonus " + StartImpl.START_BONUS + " ";
        }

        RollingDiceResponse response = new RollingDiceResponseImpl();
        response.callingField = PreviousFieldType.NORMAL_ROLL;
        field = fieldGateway.getFieldByIndex(nextFieldIndex);
        if(field instanceof StartImpl){
            player.setCurrentField(field);
        }else if(field instanceof Chance){
            msg += "Chance: ";
            ChanceCard chanceCard = ((ChanceImpl)field).getChanceCard();
            if(chanceCard instanceof YouGet100FromEachPlayer){
                YouGet100FromEachPlayer currentChance = (YouGet100FromEachPlayer) chanceCard;
                currentChance.act(player, playerGateway.getAllPlayersByNameExcept(player.getName()));
                msg += currentChance.getMessage();

                player.setCurrentField(field);
            }else if(chanceCard instanceof GetOutOfPrison){
                ChanceCard currentChance = chanceCard;
                player.setChanceCard(currentChance);
                msg += currentChance.getMessage();

                player.setCurrentField(field);
            }else if(chanceCard instanceof Pay200ToTheBank){
                Pay200ToTheBank currentChance = (Pay200ToTheBank) chanceCard;
                currentChance.act(player, "default");
                msg += currentChance.getMessage();

                player.setCurrentField(field);
            }else if(chanceCard instanceof Get200FromTheBank){
                Get200FromTheBank currentChance = (Get200FromTheBank) chanceCard;
                currentChance.act(player, "default");
                msg += currentChance.getMessage();

                player.setCurrentField(field);
            }else if(chanceCard instanceof MoveToStartCard){

                MoveToStartCard currentChance = (MoveToStartCard) chanceCard;
                currentChance.act(player);
                msg += currentChance.getMessage();
                msg += " Bonus on Start " + StartImpl.START_BONUS;
                nextFieldIndex = 0;
                player.setCurrentField(fieldGateway.getFieldByIndex(nextFieldIndex));
            }else if(chanceCard instanceof Move5FieldsAhead){
                msg += chanceCard.getMessage();
                player.setCurrentField(field);
                response.callingField = PreviousFieldType.CHANCE;
                fiveFieldsAheadIndex = nextFieldIndex + 5;
            }else{
                msg += chanceCard.getMessage();

                player.setCurrentField(field);
            }
        }else if (field instanceof PrisonGuestImpl){
            msg = "PrisonGuestImpl";
            player.setCurrentField(field);
        }else if (field instanceof ParkingImpl){
            msg = "ParkingImpl";
            player.setCurrentField(field);
        }else if (field instanceof PrisonImpl){
            msg = "PrisonImpl";
            player.setCurrentField(field);
        }else if(!field.hasOwner()){//buy the field
            player.buy(field);
            player.setCurrentField(field);
            msg += "field price: " + field.getPrice();
        }else if(!field.ownedBy(player)){//rent the field
            field.rent(player);
            player.setCurrentField(field);
            msg += "field rent: :" + field.getRent();

            response.playerPaysRent = true;
            response.landlord.name = field.getLandlordName();
            response.landlord.balance = field.getLandlordBalance();
        }else {//player owns field
            player.setCurrentField(field);
            msg += "player owns field";
        }
        response.playerName = player.getName();
        response.balance = player.getBalance();
        response.firstDie = player.getDie(0);
        response.currentFieldIndex = nextFieldIndex;
        response.isFinished = (response.balance <= 0)?true:false;
        response.msg = msg;
        presenter.present(response);
    }
    private void collectStartBonus(Player player){
        player.addToBalance(StartImpl.START_BONUS);
    }

}
