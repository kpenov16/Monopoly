package dk.monopoly.entities;

import dk.monopoly.common.Context;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FieldTest {
    private Field field;
    private int rent;
    private Player owner;
    private Bank bank;

    @BeforeEach
    void setUp() {
        field = Context.createField("hotel");
        bank = Context.createBank();
        field.setBank(bank);
        owner = Context.createPlayer();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void givenGreeFieldCreated_returnStringGreen(){
        field.setFieldColor(Field.FieldColor.GREEN);
        assertEquals("green", field.getFieldColor().toString());
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

        field.setBuyer(player);
        assertEquals(true, field.ownedBy(player));
        assertEquals(player, field.getOwner());
        assertEquals(false, field.ownedBy(player2));
    }

    @Test
    public void OtherPlayerCantBuyOwnedField(){
        Player player = Context.createPlayer();
        Player player2 = Context.createPlayer();

        field.setBuyer(player);
        field.setBuyer(player2);
        assertEquals(true, field.ownedBy(player));
        assertEquals(false, field.ownedBy(player2));
    }

    @Test
    public void givenFieldWithoutOwner_returnHasNegativeRent(){
        assertTrue( field.getRent() < 0);
    }

    @Test
    public void givenFieldOwnedByAPlayer_returnRent(){
        rent = 5;
        field.setRent(rent);

        field.setBuyer(owner);
        assertEquals(rent, field.getRent());
    }

    @Test
    public void givenOwnerTryToRentOwnedField_returnOwnerBalanceUnchanged(){
        //arrange
        field.setBuyer(owner);
        rent = 5;
        field.setRent(rent);
        int ownerBalanceBefore = owner.getBalance();

        //act
        field.rent(owner);

        //assert
        assertEquals(ownerBalanceBefore, owner.getBalance());
    }

    @Test
    public void playerRentsOtherPlayersOneOfColorField(){
        //arrange
        field.setBuyer(owner);
        int ownerBalanceBefore = owner.getBalance();
        rent = 5;
        field.setRent(rent);
        Player player = Context.createPlayer();
        int playerBalanceBefore = player.getBalance();

        //act
        field.rent(player);

        //assert
        assertEquals(playerBalanceBefore - rent, player.getBalance());
        assertEquals(ownerBalanceBefore + rent, owner.getBalance());
    }

    @Test
    public void playerRentsOtherPlayersTwoOfColorField(){
        //arrange
        field.setFieldColor(Field.FieldColor.GREEN);
        owner.buy(field);
        rent = 5;
        field.setRent(rent);

        Field field2 = Context.createField("hotel");
        field2.setFieldColor(Field.FieldColor.GREEN);
        field2.setBank(bank);
        field2.setPrice(70);
        int rent2 = 7;
        field2.setRent(rent2);
        owner.buy(field2);
        int ownerBalanceBefore = owner.getBalance();

        Player player = Context.createPlayer();
        int playerBalanceBefore = player.getBalance();

        //act
        field2.rent(player);

        //assert
        assertEquals(playerBalanceBefore - (2*rent2), player.getBalance());
        assertEquals(ownerBalanceBefore + (2*rent2), owner.getBalance());
    }


    class FakeOwner extends Player{
        @Override
        public String getName() {
            return null;
        }
        @Override
        public void setName(String name) { }
        @Override
        public int getBalance() {
            return 0;
        }
        @Override
        public Account getAccount() {
            return null;
        }
        @Override
        protected void setAccount(Account account) { }
        @Override
        public InfoService getInfoService() {
            return null;
        }
        @Override
        protected void setInfoService(InfoService infoService) { }
        @Override
        public boolean isWinner() {
            return false;
        }
        @Override
        public void play() { }
        @Override
        public void roll() { }
        @Override
        protected void setHand(Hand hand) { }
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
        @Override
        public void buy(Field field) { }
        @Override
        public boolean ownsTwoFields(Field.FieldColor fieldColor) {
            return false;
        }
        @Override
        public int getCurrentFieldIndex() {
            return 0;
        }
        @Override
        public void setCurrentField(Field field) { }
        @Override
        public void addToBalance(int addend) { }
        @Override
        public void pay(Player player, int i) { }

        @Override
        public void pay(String bankName, int sum) {

        }

        @Override
        public void setChanceCard(ChanceCard chanceCard) {

        }

        @Override
        public void setBank(Bank bank) {

        }
    }
}