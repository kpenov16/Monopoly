package dk.monopoly.common;

import dk.monopoly.*;
import dk.monopoly.ports.*;

import java.util.ArrayList;
import java.util.List;

public class SetUpImpl {
    private SetUpPresenter setUpPresenter;
    private PlayerGateway playerGateway;
    private FieldGateway fieldGateway;

    public SetUpImpl(SetUpPresenter setUpPresenter,
                     PlayerGateway playerGateway,
                     FieldGateway fieldGateway) {
        this.setUpPresenter = setUpPresenter;
        this.playerGateway = playerGateway;
        this.fieldGateway = fieldGateway;
    }

    public void execute(List<String> playersNames) {
        generateFields();

        List<Integer> balances = new ArrayList<>();
        int startBalance = getStartBalance(playersNames.size());
        for (String name : playersNames){
            Player p = Context.createPlayer(name, startBalance, Context.getBank("default"));
            p.setCurrentField( fieldGateway.getFieldByIndex(0) );
            playerGateway.addPlayer(p);

            balances.add(p.getBalance());
        }


        setUpPresenter.execute(balances);
        setUpPresenter.sendSuccessMsg();
    }

    private void generateFields() {

        fieldGateway.addField(Context.createField(StartImpl.NAME));
        fieldGateway.addField(Context.createField(BurgerImpl.NAME));
        fieldGateway.addField(Context.createField(PizzaImpl.NAME));
        fieldGateway.addField(Context.createField(ChanceImpl.NAME));
        fieldGateway.addField(Context.createField(IceCreamStoreImpl.NAME));
        fieldGateway.addField(Context.createField(CandyStoreImpl.NAME));
        fieldGateway.addField(Context.createField(PrisonGuestImpl.NAME));
        fieldGateway.addField(Context.createField(MuseumImpl.NAME));
        fieldGateway.addField(Context.createField(LibraryImpl.NAME));
        fieldGateway.addField(Context.createField(ChanceImpl.NAME));
        fieldGateway.addField(Context.createField(SkateParkImpl.NAME));
        fieldGateway.addField(Context.createField(SwimmingPoolImpl.NAME));
        fieldGateway.addField(Context.createField(ParkingImpl.NAME));
        fieldGateway.addField(Context.createField(TheaterImpl.NAME));
        fieldGateway.addField(Context.createField(CinemaImpl.NAME));
        fieldGateway.addField(Context.createField(ChanceImpl.NAME));
        fieldGateway.addField(Context.createField(ToyStoreImpl.NAME));
        fieldGateway.addField(Context.createField(PetShopImpl.NAME));
        fieldGateway.addField(Context.createField(PrisonImpl.NAME));
        fieldGateway.addField(Context.createField(BowlingImpl.NAME));
        fieldGateway.addField(Context.createField(ZooImpl.NAME));
        fieldGateway.addField(Context.createField(ChanceImpl.NAME));
        fieldGateway.addField(Context.createField(WaterParkImpl.NAME));
        fieldGateway.addField(Context.createField(SeafrontImpl.NAME));

    }

    protected int getStartBalance(int size) {
        int startBalance;
        switch (size){
            case 2:{
                startBalance = 2000;
                break;
            }
            case 3:{
                startBalance = 1800;
                break;
            }
            case 4:{
                startBalance = 1600;
                break;
            }
            default:
                startBalance = 0;
                break;
        }
        return startBalance;
    }
}
