package dk.monopoly;

import dk.monopoly.common.PlayerGatewayImpl;
import dk.monopoly.controllers.EndGameControllerImpl;
import dk.monopoly.gui.MonopolyGui;
import dk.monopoly.gui.presenters.EndGamePresenterImpl;
import dk.monopoly.gui.viewmodels.EndGameViewModel;
import dk.monopoly.usecases.endgame.EndGameController;
import dk.monopoly.usecases.endgame.EndGameImpl;
import dk.monopoly.usecases.endgame.EndGamePresenter;

public class Application {

    public static void main(String[] args){
        PlayerGatewayImpl playerGateway = new PlayerGatewayImpl();
        EndGameViewModel viewModel =  new EndGameViewModel();

        MonopolyGui monopolyGui = new MonopolyGui(playerGateway,viewModel);

        EndGamePresenter endGamePresenterImpl = new EndGamePresenterImpl(monopolyGui, viewModel);
        EndGameImpl endGameImpl = new EndGameImpl(endGamePresenterImpl, playerGateway);
        EndGameController endGameControllerImpl = new EndGameControllerImpl(viewModel, endGameImpl);

        monopolyGui.setController(endGameControllerImpl);
        monopolyGui.start();
    }
}
