package dk.monopoly.entities;

import java.util.List;

public class YouGet100FromEachPlayer extends ChanceCard {
    private int total = 0;

    public void act(Player player, List<Player> players){
        for(Player p : players){
            p.pay(player,100);
            total += 100;
        }
    }
    @Override
    public String getMessage() {
        return "You Get 100 From Each Player!" + " " + total + " in total.";
    }

}
