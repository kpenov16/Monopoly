package dk.monopoly.usecases.endgame;

import dk.monopoly.common.EndGameResponseImpl;
import dk.monopoly.common.PlayerGatewayImpl;
import dk.monopoly.entities.EndGameResponse;
import dk.monopoly.entities.Player;
import dk.monopoly.entities.impls.MonopolyPlayer;

import java.util.List;

public class EndGameImpl {
    private EndGamePresenter presenter;
    private PlayerGatewayImpl playerGateway;
    public EndGameImpl(EndGamePresenter presenter, PlayerGatewayImpl playerGateway) {
        this.presenter = presenter;
        this.playerGateway = playerGateway;
    }
    public void execute(){
        //do work here
        List<Player> players = playerGateway.getAllPlayers();
        Player winner = null;
        int maxBalance = 0;
        for(Player p : players){
            if(p.getBalance() > maxBalance) {
                maxBalance = p.getBalance();
                winner = p;
            }
        }
        EndGameResponse response = new EndGameResponseImpl();
        if(winner != null){
            response.playerName = winner.getName();
            response.balance = winner.getBalance();
            response.msg  = "";
            presenter.present(response);
        }
    }
}
