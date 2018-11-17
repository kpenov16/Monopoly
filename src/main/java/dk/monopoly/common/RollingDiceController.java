package dk.monopoly.common;

public class RollingDiceController {
    private RollingDiceViewModel rollingDiceViewModel;
    private RollingDiceImpl rollingDiceImpl;
    public RollingDiceController(RollingDiceViewModel rollingDiceViewModel, RollingDiceImpl rollingDiceImpl) {
        this.rollingDiceViewModel = rollingDiceViewModel;
        this.rollingDiceImpl = rollingDiceImpl;
    }

    public void execute() {
        rollingDiceImpl.execute(rollingDiceViewModel.playerName);
    }
}
