package dk.monopoly.ports;

import dk.monopoly.HotelImpl;
import dk.monopoly.common.Context;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FieldTest {
    private Field field;
    @BeforeEach
    void setUp() {
        field = new HotelImpl();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void givenFieldHasNoOwner_returnNoOwner(){
        assertEquals(false, field.hasOwner());
    }

    @Test
    public void givenFieldHasOwner_returnThereIsOwner(){
        field.setOwner(new FakeOwner());
        assertEquals(true, field.hasOwner());
    }

    @Test
    public void givenBuyingField_returnPlayerOwnsField(){
        Player player = Context.createPlayer();
        Player player2 = Context.createPlayer();

        field.buy(player);
        assertEquals(true, field.ownedBy(player));
        assertEquals(false, field.ownedBy(player2));
    }

    @Test
    public void givenBuyingField_returnPlayersBalanceIsLessFieldPrice(){
        //hotel price
        field.setPrice(50);
        Player player = Context.createPlayer();
        int newBalance = player.getBalance() - field.getPrice();
        //act
        field.buy(player);
        //assert
        assertEquals(newBalance, player.getBalance());
    }

    @Test
    public void OtherPlayerCantBuyOwnedField(){
        Player player = Context.createPlayer();
        Player player2 = Context.createPlayer();

        field.buy(player);
        field.buy(player2);
        assertEquals(true, field.ownedBy(player));
        assertEquals(false, field.ownedBy(player2));
    }

    class FakeOwner extends Player{
        @Override
        public String getName() {
            return null;
        }

        @Override
        public void setName(String name) {

        }

        @Override
        public int getBalance() {
            return 0;
        }

        @Override
        public Account getAccount() {
            return null;
        }

        @Override
        protected void setAccount(Account account) {

        }

        @Override
        public InfoService getInfoService() {
            return null;
        }

        @Override
        protected void setInfoService(InfoService infoService) {

        }

        @Override
        public boolean isWinner() {
            return false;
        }

        @Override
        public void play() {

        }

        @Override
        protected void setHand(Hand hand) {

        }

        @Override
        public String getLocation() {
            return null;
        }

        @Override
        public String getMessage() {
            return null;
        }

        @Override
        public int getDie(int index) {
            return 0;
        }
    }
}