package dk.monopoly.ports;

import dk.monopoly.ports.Player;

public interface PlayerGateway {
    public Player getPlayerMyName(String playerName);
    public void addPlayer(Player player);
}
