package dk.monopoly.gui.presenters;

import dk.monopoly.gui.MonopolyGui;
import dk.monopoly.gui.viewmodels.SetUpViewModel;
import dk.monopoly.usecases.setup.SetUpPresenter;

import java.util.List;

public class SetUpPresenterImpl implements SetUpPresenter {
    private MonopolyGui monopolyGui;
    private SetUpViewModel vm;

    public SetUpPresenterImpl(MonopolyGui monopolyGui, SetUpViewModel vm) {
        this.monopolyGui = monopolyGui;
        this.vm = vm;
    }

    public void sendSuccessMsg() {
        String msg = "Successfully created players: ";
        for(String name : vm.playersNames)
            msg += name + ", ";
        vm.msg = msg.trim().substring(0,msg.length()-2);
        monopolyGui.sendSuccessMsg();
    }

    public void execute(List<Integer> balances) {
        vm.balancesPlayers = balances;
    }
}
