package dk.monopoly.ports;

import dk.monopoly.MonopolyAccount;
import dk.monopoly.MonopolyInfoService;
import dk.monopoly.MonopolyPlayer;
import dk.monopoly.common.Context;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PlayerTest {
    private Player player;
    FakeHand hand;
    MonopolyInfoService MonopolyInfoService;

    @BeforeEach
    void setUp() {
        player = new MonopolyPlayer(new MonopolyAccount());
        player.setName("Jan");
        hand = new FakeHand();
        MonopolyInfoService = (MonopolyInfoService) Context.createInfoService("DK");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void givenNewPlayer_returnTimeBetweenRollsLessThan333Millisec(){
        long start = 0;
        long stop = 0;

        //act
        start = System.currentTimeMillis();

        Player player1 = Context.createPlayer();
        player1.setName("NAME");
        player1.play();
        int balance = player1.getBalance();
        int firstDie = player1.getDie(0);
        int secondDie = player1.getDie(1);
        boolean isWinner = player1.isWinner();
        String msg = player1.getMessage();

        stop = System.currentTimeMillis();
        long diffTime = stop - start;

        //assert
        assertTrue(diffTime  < 333);
    }

    @Test
    public void givenNewPlayer_returnEmpltyPalyerName(){
        //arrange
        //act
        player = new MonopolyPlayer(new MonopolyAccount());
        //assert
        assertEquals("",player.getName());
    }

    @Test
    public void givenNewPlayerWithName_returnPalyerName(){
        //arrange
        //act

        //assert
        assertEquals("Jan",player.getName());
    }

    @Test
    public void givenNewPlayer_returnAccountBalanceIsPositive1000(){
        //arrange
        //act

        //assert
        assertEquals(+1000,player.getBalance());
    }

    @Test
    public void givenNewPlayer_returnPlayerOwnsTheAccount(){
        //arrange
        //act

        //assert
        assertEquals("Jan", player.getAccount().getOwnerName());
    }

    @Test
    public void givenPlayerReachesScoreOf3000_returnPlayerWinsTheGame(){
        //arrange
        //act
        FakeAccount account = new FakeAccount();
        account.setBalance(3000);
        player.setAccount(account);

        //assert
        assertEquals(true, player.isWinner());
    }


    @Test
    public void givenPlayerScoreIsLessThan3000_returnPlayerNotWinner(){
        //arrange
        //act
        FakeAccount account = new FakeAccount();
        account.setBalance(2000);
        player.setAccount(account);

        //assert
        assertEquals(false, player.isWinner());
    }

    private class FakeAccount extends Account{
        @Override
        public void add(int addend) {

        }
        @Override
        public void subtract(int subtrahend) {

        }
        @Override
        protected void setBalance(int newBalance) {
            balance = newBalance;
        }
        @Override
        public void setOwner(Owner owner) {

        }
        @Override
        public Owner getOwner() {
            return null;
        }
        @Override
        public String getOwnerName() {
            return "";
        }
    }

    @Test
    public void givenNewPlayerScores2_returnBalanceIs3250(){
        //arrange
        hand.setRoll(2);
        player.setHand(hand);
        MonopolyInfoService.setPoints(2);
        player.setInfoService(MonopolyInfoService);

        int balance = player.getBalance() + 250;

        //act
        player.play();

        //assert
        assertEquals(balance, player.getBalance());
        assertEquals(InfoService.TOWER, player.getLocation());
        assertEquals("Du har fundet Tower og får 250 kr, du er rig!", player.getMessage());
    }

    @Test
    public void givenNewPlayerScores3_returnBalanceIs2900(){
        //arrange
        hand.setRoll(3);
        player.setHand(hand);
        MonopolyInfoService.setPoints(3);
        player.setInfoService(MonopolyInfoService);

        int balance = player.getBalance() - 100;

        //act
        player.play();

        //assert
        assertEquals(balance, player.getBalance());
        assertEquals(InfoService.CRATER, player.getLocation());
        assertEquals("Du har fundet Crater og får -100 kr, du er ikke rig!", player.getMessage());
    }

    private class FakeHand extends Hand{

        @Override
        public int roll() {
            return hand;
        }

        protected void setRoll(int roll){
            super.hand = roll;
        }

        @Override
        public void setDice(Die[] dice) {

        }

        @Override
        public int getDie(int index) {
            return 0;
        }
    }

}