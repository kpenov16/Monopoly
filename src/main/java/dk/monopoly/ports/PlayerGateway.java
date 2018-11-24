package dk.monopoly.ports;

import dk.monopoly.ports.Player;

import java.util.List;

public interface PlayerGateway {
    public Player getPlayerMyName(String playerName);
    public void addPlayer(Player player);
    List<Player> getAllPlayersByNameExcept(String playerName);
}
