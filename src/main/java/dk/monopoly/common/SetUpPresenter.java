package dk.monopoly.common;

import java.util.List;

public class SetUpPresenter {
    private MonopolyGui monopolyGui;
    private SetUpViewModel vm;

    public SetUpPresenter(MonopolyGui monopolyGui, SetUpViewModel vm) {
        this.monopolyGui = monopolyGui;
        this.vm = vm;
    }

    public void sendSuccessMsg() {
        String msg = "Successfully created players: ";
        for(String name : vm.playersNames)
            msg += name + ", ";
        vm.msg = msg.trim().substring(0,msg.length()-1);
        monopolyGui.sendSuccessMsg();
    }

    public void execute(List<Integer> balances) {
        vm.balancesPlayers = balances;
    }
}
