package dk.monopoly;

import dk.monopoly.ports.Die;
import dk.monopoly.ports.Hand;

public class HandImpl extends Hand {

    @Override
    public int roll() {
        hand = 0;
        int currentDie = super.dice[0].roll();//1
        int previousDie = -1;
        hand += currentDie;
        for(int i = 1; i< super.dice.length; i++){
            previousDie = currentDie;
            currentDie = super.dice[i].roll();//3
            if(currentDie != previousDie)
                diceAreSame = false;
            hand += currentDie;
        }
        return hand;
    }
    @Override
    public void setDice(Die[] dice){
        super.dice = dice;
    }

    @Override
    public int getDie(int index) {
        return super.dice[index].getValue();
    }
}
