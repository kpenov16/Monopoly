package dk.monopoly.common;

import dk.monopoly.ports.Context;
import dk.monopoly.ports.Player;

public class SetUpImpl {
    private SetUpPresenter setUpPresenter;
    private Player player1;
    private Player player2;
    private PlayerGateway playerGateway;

    public SetUpImpl(SetUpPresenter setUpPresenter, PlayerGateway playerGateway) {
        this.setUpPresenter = setUpPresenter;
        this.playerGateway = playerGateway;
    }

    public void execute(String nameFirstPlayer, String nameSecondPlayer) {
        player1 = Context.createPlayer();
        player1.setName(nameFirstPlayer);
        playerGateway.addPlayer(player1);

        player2 = Context.createPlayer();
        player2.setName(nameSecondPlayer);
        playerGateway.addPlayer(player2);

        setUpPresenter.execute(player1.getBalance(),player2.getBalance());
        setUpPresenter.sendSuccessMsg();
    }
}
