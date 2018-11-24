package dk.monopoly.common;

import dk.monopoly.ports.Player;
import dk.monopoly.ports.PlayerGateway;

import java.util.ArrayList;
import java.util.List;

public class PlayerGatewayImpl implements PlayerGateway {
    private List<Player> players = new ArrayList<>();

    public Player getPlayerMyName(String playerName){
        Player player = null;
        for (Player p : players){
            if(p.getName().equals(playerName)){
                player = p;
                break;
            }
        }
        return player;
    }

    public void addPlayer(Player p) {
        players.add(p);
    }

    @Override
    public List<Player> getAllPlayersByNameExcept(String playerName) {
        List<Player> playersResponse = new ArrayList<>();
        for (Player p : players)
            if(!playerName.equals(p.getName()))
                playersResponse.add(p);
        return playersResponse;
    }

}
