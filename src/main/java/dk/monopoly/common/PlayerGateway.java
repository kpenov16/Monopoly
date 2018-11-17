package dk.monopoly.common;

import dk.monopoly.ports.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerGateway {
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
}
