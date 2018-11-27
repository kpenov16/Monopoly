package dk.monopoly.controllers;

import dk.monopoly.gui.viewmodels.EndGameViewModel;
import dk.monopoly.usecases.endgame.EndGameController;
import dk.monopoly.usecases.endgame.EndGameImpl;

public class EndGameControllerImpl implements EndGameController {
    private EndGameViewModel viewModel;
    private EndGameImpl endGameImpl;
    public EndGameControllerImpl(EndGameViewModel viewModel, EndGameImpl endGameImpl) {
        this.viewModel = viewModel;
        this.endGameImpl = endGameImpl;
    }

    @Override
    public void execute() {
        viewModel.msg = "";
        viewModel.winnerName = "";
        endGameImpl.execute();
    }
}
