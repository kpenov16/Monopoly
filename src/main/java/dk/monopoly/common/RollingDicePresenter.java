package dk.monopoly.common;

import dk.monopoly.ports.RollingDiceResponse;

public class RollingDicePresenter {
    private MonopolyGui monopolyGui;
    private RollingDiceViewModel vm;
    public RollingDicePresenter(MonopolyGui monopolyGui, RollingDiceViewModel vm) {
        this.monopolyGui = monopolyGui;
        this.vm = vm;
    }

    public void present(int firstDie, int secondDie, int balance, boolean isWinner, String msg) {
        vm.firstDie = firstDie;
        vm.secondDie = secondDie;
        vm.msg = vm.playerName + ": "+ msg;
        vm.balance = balance;
        vm.isWinner = isWinner;
        monopolyGui.update();
    }

    public void present(RollingDiceResponse response) {
        if(response.playerPaysRent!=null && response.playerPaysRent) {
            vm.msg = response.playerName + ": " + response.msg + ", has landlord: " + response.landlord.name;
            vm.landlordName = response.landlord.name;
            vm.landlordBalance = response.landlord.balance;
            vm.playerPaysRent = response.playerPaysRent;
        }else {
            vm.msg = response.playerName + ": " + response.msg;
        }
        vm.firstDie = response.firstDie;
        vm.secondDie = 1;
        vm.balance = response.balance;
        vm.isWinner = response.isFinished;
        vm.currentFieldIndex = response.currentFieldIndex;
        monopolyGui.update();
    }
}
