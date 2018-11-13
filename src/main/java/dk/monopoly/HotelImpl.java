package dk.monopoly;

import dk.monopoly.ports.Field;
import dk.monopoly.ports.Owner;
import dk.monopoly.ports.Player;

public class HotelImpl extends Field {

    @Override
    public void buy(Player player) {
        if(getOwner()==null){
            player.getAccount().subtract(getPrice());
            setOwner(player);
        }

    }

    @Override
    public boolean ownedBy(Player player) {
        return getOwner().equals(player);
    }
}
