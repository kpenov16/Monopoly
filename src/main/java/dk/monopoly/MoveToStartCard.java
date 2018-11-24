package dk.monopoly;

import dk.monopoly.ports.ChanceCard;
import dk.monopoly.ports.Player;

public class MoveToStartCard extends ChanceCard {
    @Override
    public String getMessage() {
        return "MoveToStartCard: ";
    }

    public void act(Player player) {
        player.addToBalance(StartImpl.START_BONUS);
    }
}
