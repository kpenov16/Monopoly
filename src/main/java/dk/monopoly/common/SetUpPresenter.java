package dk.monopoly.common;

public class SetUpPresenter {
    private MonopolyGui monopolyGui;
    private SetUpViewModel vm;

    public SetUpPresenter(MonopolyGui monopolyGui, SetUpViewModel vm) {
        this.monopolyGui = monopolyGui;
        this.vm = vm;
    }


    public void sendSuccessMsg() {
        vm.msg = "Successfully created players: " + vm.nameFirstPlayer + ", " + vm.nameSecondPlayer;
        monopolyGui.sendSuccessMsg();
    }

    public void execute(int balanceFirstPlayer, int balanceSecondPlayer) {
        vm.balanceFirstPlayer = balanceFirstPlayer;
        vm.balanceSecondPlayer = balanceSecondPlayer;

    }
}
