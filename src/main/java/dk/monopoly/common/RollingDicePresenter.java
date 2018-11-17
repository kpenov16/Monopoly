package dk.monopoly.common;

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

}
