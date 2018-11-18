package dk.monopoly.common;

import dk.monopoly.ports.Context;
import dk.monopoly.ports.Player;

import java.util.ArrayList;
import java.util.List;

public class SetUpImpl {
    private SetUpPresenter setUpPresenter;
    private PlayerGateway playerGateway;

    public SetUpImpl(SetUpPresenter setUpPresenter, PlayerGateway playerGateway) {
        this.setUpPresenter = setUpPresenter;
        this.playerGateway = playerGateway;
    }

    public void execute(List<String> playersNames) {
        List<Integer> balances = new ArrayList<>();
        for (String name : playersNames){
            Player p = Context.createPlayer(name);
            playerGateway.addPlayer(p);

            balances.add(p.getBalance());
        }

        setUpPresenter.execute(balances);
        setUpPresenter.sendSuccessMsg();
    }
}
