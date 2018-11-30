package dk.monopoly.usecases.rollingdice;

import dk.monopoly.common.RollingDiceResponseImpl;
import dk.monopoly.entities.*;
import dk.monopoly.gateways.FieldGateway;
import dk.monopoly.gateways.PlayerGateway;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RollingDiceImplTest {
    private FakeRollingDicePresenter presenter;
    private FakePlayerGateway playerGateway;
    private FakeFieldGateway fieldGateway;
    @BeforeEach
    void setUp() {
        presenter = new FakeRollingDicePresenter();
        playerGateway = new FakePlayerGateway();
        fieldGateway =  new FakeFieldGateway();
    }

    @AfterEach
    void tearDown() {
    }


    @Test
    public void playerAtStartRollsToFieldForRent(){
        FakePlayer tentant = new FakePlayer();
        FakeAccount account = new FakeAccount(1990);

        String playerName = "PlayerName";
        tentant.setAccount(account);
        tentant.setCurrentFieldIndex(0);//tentant at start
        tentant.setDie(1);//rolls to field for sell
        tentant.setName(playerName);
        tentant.setBalance(1990);
        playerGateway.setPlayer(tentant);

        FakeField burger = new FakeField();
        burger.setIndex(1);
        burger.setHasOwner(true);
        fieldGateway.addField(burger);

        RollingDiceImpl rollingDice = new RollingDiceImpl(presenter, playerGateway, fieldGateway);
        rollingDice.execute(playerName, RollingDiceResponse.PreviousFieldType.NORMAL_ROLL);

        RollingDiceResponseImpl response = (RollingDiceResponseImpl) presenter.getRollingDiceResponse();

        assertEquals(true, response.playerPaysRent);
        assertEquals(2010, (int)response.landlord.balance);
        assertEquals("LandlordName", response.landlord.name);

        assertEquals(1990, (int)response.balance);
        assertEquals(playerName, response.playerName);
        assertEquals(false, response.isFinished);
        assertEquals(1, (int)response.currentFieldIndex);
        assertEquals(1, (int)response.firstDie);
    }

    @Test
    public void playerAtStartRollsToFieldForSell(){
        //arrange
        FakePlayer buyer = new FakePlayer();
        FakeAccount account = new FakeAccount(1900);
        String playerName = "PlayerName";
        buyer.setAccount(account);
        buyer.setCurrentFieldIndex(0);//player at start
        buyer.setDie(1);//rolls to field for sell
        buyer.setName(playerName);
        buyer.setBalance(1900);
        playerGateway.setPlayer(buyer);

        FakeFieldGateway fieldGateway = new FakeFieldGateway();
        FakeField burger = new FakeField();
        burger.setIndex(1);
        burger.setHasOwner(false);
        fieldGateway.addField(burger);

        RollingDiceImpl rollingDice = new RollingDiceImpl(presenter, playerGateway, fieldGateway);

        //act
        rollingDice.execute(playerName, RollingDiceResponse.PreviousFieldType.NORMAL_ROLL);

        //logisk assert her
        RollingDiceResponseImpl response = (RollingDiceResponseImpl) presenter.getRollingDiceResponse();
        assertEquals(playerName, response.playerName);
        assertEquals(1900, (int)response.balance);
        assertEquals(false, response.isFinished);
        assertEquals(1, (int)response.currentFieldIndex);
        assertEquals(1, (int)response.firstDie);
        assertEquals("field price: " + burger.getPrice(), response.msg);
        assertEquals(1, buyer.getFieldIndexBuyWasCalledWith());
        assertEquals(1, buyer.getCountCallsToBuy());
    }

    public class FakeField extends Field {
        boolean hasOwner =false;

        public void setHasOwner(boolean hasOwner){
            this.hasOwner = hasOwner;
        }

        @Override
        public boolean ownedBy(Player player) {
            return false;
        }

        @Override
        public boolean hasOwner(){
            return hasOwner;
        }

        @Override
        public void rent(Player player){
            //;
        }

        @Override
        public String getLandlordName(){
            return "LandlordName";
        }

        @Override
        public Integer getLandlordBalance(){
            return 2010;
        }

        @Override
        public int getRent(){
            return 10;
        }

        public void setIndex(int i){
            super.setIndex(i);
        }
        /*
        public void setPrice(int price){
            super.setPrice(price);

        }*/

        @Override
        public int getPrice(){
            return 100;
        }
    }

    public class FakeAccount extends Account {
        private int balance;

        public FakeAccount(int balance) {
            this.balance = balance;
        }

        @Override
        public void add(int addend) {

        }

        @Override
        public void subtract(int subtrahend) {
            balance -= subtrahend;
        }

        public int getBalance(){
            return balance;
        }

        @Override
        public void setBalance(int newBalance) {
            this.balance = newBalance;
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
            return null;
        }
    }

    public class FakePlayer extends Player {
        private  int die;
        @Override
        public int getDie(int index) {
            return die;
        }
        public void setDie(int n) {
            die = n;
        }

        private int currentFieldIndex;
        @Override
        public int getCurrentFieldIndex() {
            return currentFieldIndex;
        }
        public void setCurrentFieldIndex(int i) {
            currentFieldIndex = i;
        }

        int fieldIndex=-1;
        int countCallsToBuy=0;
        @Override
        public void buy(Field field) {
            fieldIndex = field.getIndex();
            countCallsToBuy++;
        }
        public int getFieldIndexBuyWasCalledWith(){
            return fieldIndex;
        }
        public int getCountCallsToBuy(){
            return countCallsToBuy;
        }

        int balance = 0;
        @Override
        public int getBalance() {
            return balance;
        }
        public void setBalance(int balance) {
            this.balance = balance;
        }

        @Override
        public Account getAccount() {
            return null;
        }

        Account account;
        @Override
        protected void setAccount(Account account) {
            this.account = account;
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
        public void roll() {

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
        public boolean ownsTwoFields(Field.FieldColor fieldColor) {
            return false;
        }


        @Override
        public void setCurrentField(Field field) {

        }

        @Override
        public void addToBalance(int addend) {

        }

        @Override
        public void pay(Player player, int sum) {

        }

        @Override
        public void pay(String bankName, int sum) {

        }

        @Override
        public void setChanceCard(ChanceCard chanceCard) {

        }

        @Override
        public void setBank(Bank bank) {

        }

        @Override
        public String getName() {
            return super.name;
        }

        @Override
        public void setName(String name) {
            super.name = name;
        }


    }

    public class FakeRollingDicePresenter implements RollingDicePresenter {
        private RollingDiceResponse response;
        public RollingDiceResponse getRollingDiceResponse() {
            return response;
        }
        @Override
        public void present(RollingDiceResponse response) {
            this.response = response;
        }
    }

    public class FakePlayerGateway implements PlayerGateway {
        private Player player;
        @Override
        public Player getPlayerMyName(String playerName) {
            return player;
        }

        @Override
        public void addPlayer(Player player) {

        }

        @Override
        public List<Player> getAllPlayersByNameExcept(String playerName) {
            return null;
        }

        @Override
        public List<Player> getAllPlayers() {
            return null;
        }

        protected void setPlayer(Player player){
            this.player = player;
        }
    }

    public class FakeFieldGateway implements FieldGateway {
        Field field ;
        @Override
        public Field getFieldByIndex(int fieldIndex) {
            return field;
        }

        @Override
        public void addField(Field field) {
            this.field = field;
        }
    }
}