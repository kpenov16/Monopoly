package dk.monopoly.gateways;

import dk.monopoly.entities.Player;
import dk.monopoly.entities.impls.MonopolyPlayer;

import java.util.List;

public interface PlayerGateway {
    Player getPlayerMyName(String playerName);
    void addPlayer(Player player);
    List<Player> getAllPlayersByNameExcept(String playerName);
    List<Player> getAllPlayers();
}
