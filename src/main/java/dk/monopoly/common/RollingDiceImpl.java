package dk.monopoly.common;

import dk.monopoly.ports.Player;

public class RollingDiceImpl {
    private RollingDicePresenter rollingDicePresenter;
    private PlayerGateway playerGateway;
    public RollingDiceImpl(RollingDicePresenter rollingDicePresenter, PlayerGateway playerGateway) {
        this.rollingDicePresenter = rollingDicePresenter;
        this.playerGateway = playerGateway;
    }

    public void execute(String playerName) {
        Player player = playerGateway.getPlayerMyName(playerName);
        player.play();
        int balance = player.getBalance();
        int firstDie = player.getDie(0);
        int secondDie = player.getDie(1);
        boolean isWinner = player.isWinner();
        String msg = player.getMessage();
        rollingDicePresenter.present(firstDie,secondDie,balance, isWinner, msg);
    }
}
