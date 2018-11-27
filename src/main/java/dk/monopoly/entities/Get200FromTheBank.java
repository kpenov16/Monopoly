package dk.monopoly.entities;

public class Get200FromTheBank extends ChanceCard{
    @Override
    public String getMessage() {
        return "Get200FromTheBank";
    }

    public void act(Player player, String bankName) {
        if(player.getBank().getName().equals(bankName))
            player.getBank().pay(player,200);
    }
}
