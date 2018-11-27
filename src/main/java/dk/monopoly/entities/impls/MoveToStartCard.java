package dk.monopoly.entities.impls;

import dk.monopoly.entities.ChanceCard;
import dk.monopoly.entities.Player;

public class MoveToStartCard extends ChanceCard {
    @Override
    public String getMessage() {
        return "MoveToStartCard: ";
    }

    public void act(Player player) {
        player.addToBalance(StartImpl.START_BONUS);
    }
}
