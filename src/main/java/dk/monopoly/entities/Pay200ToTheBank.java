package dk.monopoly.entities;

public class Pay200ToTheBank extends ChanceCard {
    @Override
    public String getMessage() {
        return "Pay200ToTheBank";
    }

    public void act(Player player, String bankName) {
        player.pay(bankName,200);
    }
}
