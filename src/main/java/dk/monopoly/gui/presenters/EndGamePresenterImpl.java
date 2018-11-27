package dk.monopoly.gui.presenters;

import dk.monopoly.entities.EndGameResponse;
import dk.monopoly.gui.MonopolyGui;
import dk.monopoly.gui.viewmodels.EndGameViewModel;
import dk.monopoly.usecases.endgame.EndGamePresenter;
import dk.monopoly.usecases.endgame.EndGameView;

public class EndGamePresenterImpl implements EndGamePresenter {
    private EndGameView view;
    private EndGameViewModel vm;
    public EndGamePresenterImpl(EndGameView view, EndGameViewModel vm) {
        this.view = view;
        this.vm = vm;
    }

    @Override
    public void present(EndGameResponse response) {
        vm.winnerName = response.playerName;
        vm.msg = vm.winnerName + " you won the game! Your score is " + response.balance;
        view.endGame();
    }
}
