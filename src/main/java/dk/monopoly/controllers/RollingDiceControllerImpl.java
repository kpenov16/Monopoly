package dk.monopoly.controllers;

import dk.monopoly.gui.viewmodels.RollingDiceViewModel;
import dk.monopoly.usecases.rollingdice.RollingDiceController;
import dk.monopoly.usecases.rollingdice.RollingDiceImpl;

public class RollingDiceControllerImpl implements RollingDiceController {
    private RollingDiceViewModel rollingDiceViewModel;
    private RollingDiceImpl rollingDiceImpl;
    public RollingDiceControllerImpl(RollingDiceViewModel rollingDiceViewModel, RollingDiceImpl rollingDiceImpl) {
        this.rollingDiceViewModel = rollingDiceViewModel;
        this.rollingDiceImpl = rollingDiceImpl;
    }
    @Override
    public void execute() {
        rollingDiceImpl.execute(rollingDiceViewModel.playerName, rollingDiceViewModel.callingFieldType);
    }
}
