package dk.monopoly.gui;

import dk.monopoly.common.FieldGatewayImpl;
import dk.monopoly.common.PlayerGatewayImpl;
import dk.monopoly.controllers.EndGameControllerImpl;
import dk.monopoly.gui.presenters.EndGamePresenterImpl;
import dk.monopoly.gui.viewmodels.EndGameViewModel;
import dk.monopoly.gui.viewmodels.RollingDiceViewModel;
import dk.monopoly.gui.viewmodels.SetUpViewModel;
import dk.monopoly.controllers.RollingDiceControllerImpl;
import dk.monopoly.controllers.SetUpControllerImpl;
import dk.monopoly.entities.RollingDiceResponse;
import dk.monopoly.gui.presenters.RollingDicePresenterImpl;
import dk.monopoly.gui.presenters.SetUpPresenterImpl;
import dk.monopoly.usecases.endgame.*;
import dk.monopoly.usecases.rollingdice.RollDiceView;
import dk.monopoly.usecases.rollingdice.RollingDiceImpl;
import dk.monopoly.usecases.setup.SetUpImpl;
import gui_fields.*;
import gui_main.GUI;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MonopolyGui implements RollDiceView, EndGameView {
    private int playerTurnIndex = 0;
    private SetUpViewModel setUpViewModel;
    private EndGameViewModel endGameViewModel;
    private RollingDiceViewModel rollingDiceViewModel;
    private GUI_Player player1;
    private GUI_Player player2;
    private List<GUI_Player> playersGui = new ArrayList<>();
    private Map<String, GUI_Player> playersByName = new HashMap<>();
    private GUI gui;
    private PlayerGatewayImpl playerGatewayImpl = new PlayerGatewayImpl();
    private FieldGatewayImpl fieldGatewayImpl = new FieldGatewayImpl();
    List<String> playersNames = new ArrayList<>();
    private int numberOfPlayers = 0;
    private Map<String,BoardField> fieldsMap = new HashMap<>();
    private GUI_Field[] fields = new GUI_Field[24];
    private List<Integer> playersBoardIndexes = new ArrayList<>();
    private boolean thereIsAWinner = false;
    public void start() {
        setupPlayers();
        executeSetUpUseCase();
        setupGUI();
        rollingDiceViewModel = new RollingDiceViewModel();
        while (!thereIsAWinner) {
            evaluatePlayerTurn();
            askPlayerToRoll();
            executeRollingDiceUseCase();
            thereIsAWinner = rollingDiceViewModel.isWinner;
            resetViewModel();
        }
        endGameViewModel = new EndGameViewModel();
        executeEndGameUseCase();
    }
    private void executeEndGameUseCase() {
        EndGamePresenter endGamePresenterImpl = new EndGamePresenterImpl(this, endGameViewModel);
        EndGameImpl endGameImpl = new EndGameImpl(endGamePresenterImpl, playerGatewayImpl);
        EndGameController endGameControllerImpl = new EndGameControllerImpl(endGameViewModel, endGameImpl);
        endGameControllerImpl.execute();
    }
    private void resetViewModel() {
        if(rollingDiceViewModel.callingFieldType == RollingDiceResponse.PreviousFieldType.NORMAL_ROLL)
            rollingDiceViewModel = new RollingDiceViewModel();
        else{
            rollingDiceViewModel.msg="";
            rollingDiceViewModel.balance = 0;
            rollingDiceViewModel.playerPaysRent = false;
            rollingDiceViewModel.landlordName = "";
            rollingDiceViewModel.playerPaysRent = false;
        }
    }
    private void showMessage(String msg) {
        gui.showMessage(msg);
    }
    @Override
    public void endGame() {
        showMessage(endGameViewModel.msg);
    }
    private void evaluatePlayerTurn() {
        rollingDiceViewModel.playerName = setUpViewModel.playersNames.get(playerTurnIndex);
    }
    private int generateNextPlayerIndex() {
        if(playerTurnIndex == numberOfPlayers)
            playerTurnIndex = 0;
        return playerTurnIndex;
    }
    private void executeRollingDiceUseCase() {
        RollingDicePresenterImpl rollingDicePresenterImpl = new RollingDicePresenterImpl(this, rollingDiceViewModel);
        RollingDiceImpl rollingDiceImpl = new RollingDiceImpl(rollingDicePresenterImpl, playerGatewayImpl, fieldGatewayImpl);
        RollingDiceControllerImpl rollingDiceControllerImpl = new RollingDiceControllerImpl(rollingDiceViewModel, rollingDiceImpl);
        rollingDiceControllerImpl.execute();
    }
    public void sendSuccessMsg() {
        JOptionPane.showMessageDialog(null, setUpViewModel.msg, "Success!", JOptionPane.INFORMATION_MESSAGE);
    }
    private void askPlayerToRoll(){
        String buttonName = gui.getUserButtonPressed("It's your turn " + rollingDiceViewModel.playerName, "Roll");
    }
    @Override
    public void update() {
        if(rollingDiceViewModel.callingFieldType == RollingDiceResponse.PreviousFieldType.NORMAL_ROLL){
            gui.setDice(rollingDiceViewModel.firstDie, rollingDiceViewModel.secondDie);

            GUI_Player player = playersGui.get(playerTurnIndex);
            fields[playersBoardIndexes.get(playerTurnIndex)].setCar(player,false);
            playersBoardIndexes.set(playerTurnIndex, rollingDiceViewModel.currentFieldIndex);
            fields[playersBoardIndexes.get(playerTurnIndex)].setCar(player,true);
            //fields[0].removeAllCars();

            playersGui.get(playerTurnIndex++).setBalance(rollingDiceViewModel.balance);
            playerTurnIndex = generateNextPlayerIndex();

            gui.showMessage(rollingDiceViewModel.msg);

            if(hasPlayerPaidRent())
                playersByName.get(rollingDiceViewModel.landlordName).setBalance(rollingDiceViewModel.landlordBalance);
        }else if(rollingDiceViewModel.callingFieldType == RollingDiceResponse.PreviousFieldType.CHANCE){
            //5 fields ahead chance
            gui.setDice(rollingDiceViewModel.firstDie, rollingDiceViewModel.secondDie);
            GUI_Player player = playersGui.get(playerTurnIndex);
            fields[playersBoardIndexes.get(playerTurnIndex)].setCar(player,false);
            playersBoardIndexes.set(playerTurnIndex, rollingDiceViewModel.currentFieldIndex);
            fields[playersBoardIndexes.get(playerTurnIndex)].setCar(player,true);
            //fields[0].removeAllCars();

            playersGui.get(playerTurnIndex).setBalance(rollingDiceViewModel.balance);
            //playerTurnIndex = generateNextPlayerIndex();

            gui.showMessage(rollingDiceViewModel.msg);

            //if(hasPlayerPaidRent())
            //  playersByName.get(rollingDiceViewModel.landlordName).setBalance(rollingDiceViewModel.landlordBalance);
        }
    }
    private boolean hasPlayerPaidRent() {
        return rollingDiceViewModel.playerPaysRent != null && rollingDiceViewModel.playerPaysRent==true;
    }
    private void executeSetUpUseCase() {
        setUpViewModel = new SetUpViewModel();
        setUpViewModel.playersNames = playersNames;
        SetUpPresenterImpl setUpPresenterImpl = new SetUpPresenterImpl(this, setUpViewModel);
        SetUpImpl setUpImpl = new SetUpImpl(setUpPresenterImpl, playerGatewayImpl, fieldGatewayImpl);
        SetUpControllerImpl setUpControllerImpl = new SetUpControllerImpl(setUpViewModel, setUpImpl);
        setUpControllerImpl.execute();
    }
    // from here https://stackoverflow.com/questions/13408238/simple-java-gui-as-a-popup-window-and-drop-down-menu
    // by MadProgrammer
    private String askForNumberOfPlayers(final String... values) {
        String numberOfPlayers = null;
        if (EventQueue.isDispatchThread()) {
            JPanel panel = new JPanel();
            panel.add(new JLabel("Select number players:"));
            DefaultComboBoxModel model = new DefaultComboBoxModel();
            for (String value : values) {
                model.addElement(value);
            }
            JComboBox comboBox = new JComboBox(model);
            panel.add(comboBox);

            int iResult = JOptionPane.showConfirmDialog(null, panel, "Players playerTurnIndex", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
            switch (iResult) {
                case JOptionPane.OK_OPTION:
                    numberOfPlayers = (String) comboBox.getSelectedItem();
                    break;
            }
        } else {
            NumberPlayersResponse numberPlayersResponse = new NumberPlayersResponse(values);
            try {
                SwingUtilities.invokeAndWait(numberPlayersResponse);
                numberOfPlayers = numberPlayersResponse.getResponse();
            } catch (InterruptedException | InvocationTargetException ex) {
                ex.printStackTrace();
            }
        }
        return numberOfPlayers;
    }
    private class NumberPlayersResponse implements Runnable{
        private String[] values;
        private String response;
        public NumberPlayersResponse(String... values) {this.values = values;}
        @Override
        public void run() {response = askForNumberOfPlayers(values);}
        public String getResponse() {return response;}
    }
    private class NameResponse implements Runnable{
        private int playerIndex;
        private String response;
        public NameResponse(int playerIndex) {
            this.playerIndex = playerIndex;
        }
        @Override
        public void run() {
            response = askForPlayerName(playerIndex);
        }
        public String getResponse() {return response;}
    }
    private void setupPlayers() {
        numberOfPlayers = Integer.parseInt( askForNumberOfPlayers("2","3","4") );
        playersNames = askForNames(numberOfPlayers);
    }
    private List<String> askForNames(int numberOfPlayers) {
        List<String> names = new ArrayList<>();
        for (int i=0;i<numberOfPlayers;i++)
            names.add(askForPlayerName(i));
        return names;
    }
    private String askForPlayerName(int playerIndex) {
        String name = "none " + (playerIndex+1);
        if (EventQueue.isDispatchThread()) {
            name = JOptionPane.showInputDialog("Type the name of player " + (playerIndex+1) + ": ");
            return name;
        } else {
            NameResponse response = new NameResponse(playerIndex);
            try {
                SwingUtilities.invokeAndWait(response);
                name = response.getResponse();
            } catch (InterruptedException | InvocationTargetException ex) {
                ex.printStackTrace();
            }
        }
        return name;
    }
    private void setupGUI(){
        GUI.setNull_fields_allowed(true);

        StartField start = new StartField();
        fields[0] = start.getField();
        fieldsMap.put(start.getTitle(),start);

        BurgerField burger = new BurgerField();
        fields[1] = burger.getField();
        fieldsMap.put(burger.getTitle(),burger);

        PizzaField pizza = new PizzaField();
        fields[2] = pizza.getField();
        fieldsMap.put(pizza.getTitle(),pizza);

        ChanceField chance1 = new ChanceField();
        chance1.setTitle("Chance 1");
        fields[3] = chance1.getField();
        fieldsMap.put(chance1.getTitle(),chance1);

        IceCreamStoreField iceCreamStore = new IceCreamStoreField();
        fields[4] = iceCreamStore.getField();
        fieldsMap.put(iceCreamStore.getTitle(),iceCreamStore);

        CandyStoreField candyStore = new CandyStoreField();
        fields[5] = candyStore.getField();
        fieldsMap.put(candyStore.getTitle(),candyStore);

        PrisonGuestField prisonGuest = new PrisonGuestField();
        fields[6] = prisonGuest.getField();
        fieldsMap.put(prisonGuest.getTitle(),prisonGuest);

        MuseumField museum = new MuseumField();
        fields[7] = museum.getField();
        fieldsMap.put(museum.getTitle(),museum);

        LibraryField library = new LibraryField();
        fields[8] = library.getField();
        fieldsMap.put(library.getTitle(),library);


        ChanceField chance2 = new ChanceField();
        chance2.setTitle("Chance 2");
        fields[9] = chance2.getField();
        fieldsMap.put(chance2.getTitle(),chance2);


        SkateParkField skatePark = new SkateParkField();
        fields[10] = skatePark.getField();
        fieldsMap.put(skatePark.getTitle(),skatePark);

        SwimmingPoolField swimmingPool = new SwimmingPoolField();
        fields[11] = swimmingPool.getField();
        fieldsMap.put(swimmingPool.getTitle(),swimmingPool);

        ParkingField parking = new ParkingField();
        fields[12] = parking.getField();
        fieldsMap.put(parking.getTitle(),parking);

        TheaterField theater = new TheaterField();
        fields[13] = theater.getField();
        fieldsMap.put(theater.getTitle(),theater);

        CinemaField cinema = new CinemaField();
        fields[14] = cinema.getField();
        fieldsMap.put(cinema.getTitle(),cinema);

        ChanceField chance3 = new ChanceField();
        chance3.setTitle("Chance 3");
        fields[15] = chance3.getField();
        fieldsMap.put(chance3.getTitle(),chance3);

        ToyStoreField toyStore = new ToyStoreField();
        fields[16] = toyStore.getField();
        fieldsMap.put(toyStore.getTitle(),toyStore);

        PetShopField petShop = new PetShopField();
        fields[17] = petShop.getField();
        fieldsMap.put(petShop.getTitle(),petShop);

        PrisonField prison = new PrisonField();
        fields[18] = prison.getField();
        fieldsMap.put(prison.getTitle(),prison);

        BowlingField bowling = new BowlingField();
        fields[19] = bowling.getField();
        fieldsMap.put(bowling.getTitle(),bowling);

        ZooField zoo = new ZooField();
        fields[20] = zoo.getField();
        fieldsMap.put(zoo.getTitle(),zoo);

        ChanceField chance4 = new ChanceField();
        chance4.setTitle("Chance 4");
        fields[21] = chance4.getField();
        fieldsMap.put(chance4.getTitle(),chance4);

        WaterParkField waterPark = new WaterParkField();
        fields[22] = waterPark.getField();
        fieldsMap.put(waterPark.getTitle(),waterPark);

        SeafrontField seafront = new SeafrontField();
        fields[23] = seafront.getField();
        fieldsMap.put(seafront.getTitle(),seafront);

        gui = new GUI(fields);
        for(int i=0;i<numberOfPlayers;i++) {
            GUI_Player player = new GUI_Player(setUpViewModel.playersNames.get(i), setUpViewModel.balancesPlayers.get(i));
            gui.addPlayer(player);
            fields[0].setCar(player,true);

            playersBoardIndexes.add(0);

            playersGui.add(player);
            playersByName.put(player.getName(),player);
        }
        /*
        System.out.println( gui.getUserString("type something") );
        boolean selection = gui.getUserLeftButtonPressed("vælg Ja eller nej","Ja","Nej");
        System.out.println( selection );
        int tal = gui.getUserInteger("Vælg et tal");
        System.out.println( tal );*/
    }
    public class Field {
        GUI_Street field;

        public Field() {
            field = new GUI_Street();
        }

        void setTitle(String title) {
            field.setTitle(title);
        }

        String getTitle() {
            return field.getTitle();
        }

        GUI_Street getField() {
            return field;
        }

        void setRent(String rentMagic) {
            field.setRent(rentMagic);
            field.setRentLabel(rentMagic);
            field.ownableLable = rentMagic;
            field.rentLable = rentMagic;
            field.setOwnableLabel(rentMagic);
        }
        void setDescription(String description) {
            field.setDescription(description);
        }
    }
    private abstract class BoardField {
    }
    private class ParkingField extends BoardField{
        private GUI_Brewery field;
        public ParkingField(){
            field = new GUI_Brewery();
            setTitle("Parking");
            setBackGroundColor(Color.WHITE);
            setDescription("Parking description here");
            setSubText("subtext here");
        }

        private void setSubText(String subtext) {
            field.setSubText(subtext);
        }
        private void setBackGroundColor(Color color) {
            field.setBackGroundColor(color);
        }

        void setTitle(String title) {
            field.setTitle(title);
        }

        String getTitle() {
            return field.getTitle();
        }

        GUI_Brewery getField() {
            return field;
        }

        void setDescription(String description) {
            field.setDescription(description);
        }
    }
    private class SkateParkField extends BoardField{
        private GUI_Street field;
        public SkateParkField(){
            field = new GUI_Street();
            setTitle("Skate Park");
            setBackGroundColor(Color.PINK);
            setDescription("Skate Park description here");
            setSubText("subtext here");
        }

        private void setSubText(String subtext) {
            field.setSubText(subtext);
        }
        private void setBackGroundColor(Color color) {
            field.setBackGroundColor(color);
        }

        void setTitle(String title) {
            field.setTitle(title);
        }

        String getTitle() {
            return field.getTitle();
        }

        GUI_Street getField() {
            return field;
        }

        void setDescription(String description) {
            field.setDescription(description);
        }
    }
    private class SwimmingPoolField extends BoardField{
        private GUI_Street field;
        public SwimmingPoolField(){
            field = new GUI_Street();
            setTitle("Swimming Pool");
            setBackGroundColor(Color.PINK);
            setDescription("Swimming Pool description here");
            setSubText("subtext here");
        }

        private void setSubText(String subtext) {
            field.setSubText(subtext);
        }
        private void setBackGroundColor(Color color) {
            field.setBackGroundColor(color);
        }

        void setTitle(String title) {
            field.setTitle(title);
        }

        String getTitle() {
            return field.getTitle();
        }

        GUI_Street getField() {
            return field;
        }

        void setDescription(String description) {
            field.setDescription(description);
        }
    }
    private class LibraryField extends BoardField{
        private GUI_Street field;
        public LibraryField(){
            field = new GUI_Street();
            setTitle("Library");
            setBackGroundColor(Color.MAGENTA);
            setDescription("Library description here");
            setSubText("subtext here");
        }

        private void setSubText(String subtext) {
            field.setSubText(subtext);
        }
        private void setBackGroundColor(Color color) {
            field.setBackGroundColor(color);
        }

        void setTitle(String title) {
            field.setTitle(title);
        }

        String getTitle() {
            return field.getTitle();
        }

        GUI_Street getField() {
            return field;
        }

        void setDescription(String description) {
            field.setDescription(description);
        }
    }
    private class MuseumField extends BoardField{
        private GUI_Street field;
        public MuseumField(){
            field = new GUI_Street();
            setTitle("Museum");
            setBackGroundColor(Color.MAGENTA);
            setDescription("Museum description here");
            setSubText("subtext here");
        }

        private void setSubText(String subtext) {
            field.setSubText(subtext);
        }
        private void setBackGroundColor(Color color) {
            field.setBackGroundColor(color);
        }

        void setTitle(String title) {
            field.setTitle(title);
        }

        String getTitle() {
            return field.getTitle();
        }

        GUI_Street getField() {
            return field;
        }

        void setDescription(String description) {
            field.setDescription(description);
        }
    }
    private class PrisonGuestField extends BoardField{
        private GUI_Refuge field;
        public PrisonGuestField(){
            field = new GUI_Refuge();
            setTitle("Prison Guest");
            setBackGroundColor(Color.WHITE);
            setDescription("Prison Guest description here");
            setSubText("subtext here");
        }

        private void setSubText(String subtext) {
            field.setSubText(subtext);
        }

        private void setBackGroundColor(Color color) {
            field.setBackGroundColor(color);
        }

        void setTitle(String title) {
            field.setTitle(title);
        }

        String getTitle() {
            return field.getTitle();
        }

        GUI_Refuge getField() {
            return field;
        }

        void setDescription(String description) {
            field.setDescription(description);
        }
    }
    private class IceCreamStoreField extends BoardField{
        private GUI_Street field;
        public IceCreamStoreField(){
            field = new GUI_Street();
            setTitle("Ice Cream Store");
            setBackGroundColor(Color.CYAN);
            setDescription("Ice Cream Store description here");
            setSubText("subtext here");
        }

        private void setSubText(String subtext) {
            field.setSubText(subtext);
        }

        private void setBackGroundColor(Color color) {
            field.setBackGroundColor(color);
        }

        void setTitle(String title) {
            field.setTitle(title);
        }

        String getTitle() {
            return field.getTitle();
        }

        GUI_Street getField() {
            return field;
        }

        void setDescription(String description) {
            field.setDescription(description);
        }
    }
    private class CandyStoreField extends BoardField{
        private GUI_Street field;
        public CandyStoreField(){
            field = new GUI_Street();
            setTitle("Candy Store");
            setBackGroundColor(Color.CYAN);
            setDescription("Candy Store description here");
            setSubText("subtext here");
        }

        private void setSubText(String subtext) {
            field.setSubText(subtext);
        }

        private void setBackGroundColor(Color color) {
            field.setBackGroundColor(color);
        }

        void setTitle(String title) {
            field.setTitle(title);
        }

        String getTitle() {
            return field.getTitle();
        }

        GUI_Street getField() {
            return field;
        }

        void setDescription(String description) {
            field.setDescription(description);
        }
    }
    private class PizzaField extends BoardField{
        private GUI_Street field;
        public PizzaField(){
            field = new GUI_Street();
            setTitle("Pizza");
            setBackGroundColor(Color.orange);
            setDescription("Pizza description here");
            setSubText("subtext here");
        }

        private void setSubText(String subtext) {
            field.setSubText(subtext);
        }

        private void setBackGroundColor(Color color) {
            field.setBackGroundColor(color);
        }

        void setTitle(String title) {
            field.setTitle(title);
        }

        String getTitle() {
            return field.getTitle();
        }

        GUI_Street getField() {
            return field;
        }

        void setDescription(String description) {
            field.setDescription(description);
        }
    }
    private class BurgerField extends BoardField{
        private GUI_Street field;
        public BurgerField(){
            field = new GUI_Street();
            setTitle("Burger");
            setBackGroundColor(Color.orange);
            setDescription("Burger description here");
            setSubText("subtext here");
        }

        private void setSubText(String subtext) {
            field.setSubText(subtext);
        }

        private void setBackGroundColor(Color color) {
            field.setBackGroundColor(color);
        }

        void setTitle(String title) {
            field.setTitle(title);
        }

        String getTitle() {
            return field.getTitle();
        }

        GUI_Street getField() {
            return field;
        }

        void setDescription(String description) {
            field.setDescription(description);
        }
    }
    private class SeafrontField extends BoardField{
        private GUI_Street field;
        public SeafrontField(){
            field = new GUI_Street();
            setTitle("Seafront");
            setBackGroundColor(Color.BLUE);
            setDescription("Seafront description here");
            setSubText("subtext here");
        }

        private void setSubText(String subtext) {
            field.setSubText(subtext);
        }

        private void setBackGroundColor(Color color) {
            field.setBackGroundColor(color);
        }

        void setTitle(String title) {
            field.setTitle(title);
        }

        String getTitle() {
            return field.getTitle();
        }

        GUI_Street getField() {
            return field;
        }

        void setDescription(String description) {
            field.setDescription(description);
        }
    }
    private class WaterParkField extends BoardField{
        private GUI_Street field;
        public WaterParkField(){
            field = new GUI_Street();
            setTitle("Water Park");
            setBackGroundColor(Color.BLUE);
            setDescription("Water Park description here");
            setSubText("subtext here");
        }

        private void setSubText(String subtext) {
            field.setSubText(subtext);
        }

        private void setBackGroundColor(Color color) {
            field.setBackGroundColor(color);
        }

        void setTitle(String title) {
            field.setTitle(title);
        }

        String getTitle() {
            return field.getTitle();
        }

        GUI_Street getField() {
            return field;
        }

        void setDescription(String description) {
            field.setDescription(description);
        }
    }
    private class ZooField extends BoardField{
        private GUI_Street field;
        public ZooField(){
            field = new GUI_Street();
            setTitle("Zoo");
            setBackGroundColor(Color.GREEN);
            setDescription("Zoo description here");
            setSubText("subtext here");
        }

        private void setSubText(String subtext) {
            field.setSubText(subtext);
        }

        private void setBackGroundColor(Color color) {
            field.setBackGroundColor(color);
        }

        void setTitle(String title) {
            field.setTitle(title);
        }

        String getTitle() {
            return field.getTitle();
        }

        GUI_Street getField() {
            return field;
        }

        void setDescription(String description) {
            field.setDescription(description);
        }
    }
    private class BowlingField extends BoardField{
        private GUI_Street field;
        public BowlingField(){
            field = new GUI_Street();
            setTitle("Bowling");
            setBackGroundColor(Color.GREEN);
            setDescription("Bowling description here");
            setSubText("subtext here");
        }

        private void setSubText(String subtext) {
            field.setSubText(subtext);
        }

        private void setBackGroundColor(Color color) {
            field.setBackGroundColor(color);
        }

        void setTitle(String title) {
            field.setTitle(title);
        }

        String getTitle() {
            return field.getTitle();
        }

        GUI_Street getField() {
            return field;
        }

        void setDescription(String description) {
            field.setDescription(description);
        }
    }
    private class PrisonField extends BoardField{
        private GUI_Jail field;
        public PrisonField(){
            field = new GUI_Jail();
            setTitle("Prison");
            setBackGroundColor(Color.WHITE);
            setDescription("Prison description here");
            setSubText("subtext here");
        }

        private void setSubText(String subtext) {
            field.setSubText(subtext);
        }

        private void setBackGroundColor(Color color) {
            field.setBackGroundColor(color);
        }

        void setTitle(String title) {
            field.setTitle(title);
        }

        String getTitle() {
            return field.getTitle();
        }

        GUI_Jail getField() {
            return field;
        }

        void setDescription(String description) {
            field.setDescription(description);
        }
    }
    private class ChanceField extends BoardField{
        private GUI_Chance field;
        public ChanceField(){
            field = new GUI_Chance();
            setTitle("Chance");
            setBackGroundColor(Color.white);
            setDescription("Chance description here");
            setSubText("Subtext here");
        }

        private void setSubText(String subtext) {
            field.setSubText(subtext);
        }

        private void setBackGroundColor(Color color) {
            field.setBackGroundColor(color);
        }

        void setTitle(String title) {
            field.setTitle(title);
        }

        String getTitle() {
            return field.getTitle();
        }

        GUI_Chance getField() {
            return field;
        }

        void setDescription(String description) {
            field.setDescription(description);
        }
    }
    private class PetShopField extends BoardField{
        private GUI_Street field;
        public PetShopField(){
            field = new GUI_Street();
            setTitle("Pet Shop");
            setBackGroundColor(Color.YELLOW);
            setDescription("Pet Shop description here");
            setSubText("subtext here");
        }

        private void setSubText(String subtext) {
            field.setSubText(subtext);
        }

        private void setBackGroundColor(Color color) {
            field.setBackGroundColor(color);
        }

        void setTitle(String title) {
            field.setTitle(title);
        }

        String getTitle() {
            return field.getTitle();
        }

        GUI_Street getField() {
            return field;
        }

        void setDescription(String description) {
            field.setDescription(description);
        }
    }
    private class ToyStoreField extends BoardField{
        private GUI_Street field;
        public ToyStoreField(){
            field = new GUI_Street();
            setTitle("Toy Store");
            setBackGroundColor(Color.YELLOW);
            setDescription("Toy Store description here");
            setSubText("subtext here");
        }

        private void setSubText(String subtext) {
            field.setSubText(subtext);
        }

        private void setBackGroundColor(Color color) {
            field.setBackGroundColor(color);
        }

        void setTitle(String title) {
            field.setTitle(title);
        }

        String getTitle() {
            return field.getTitle();
        }

        GUI_Street getField() {
            return field;
        }

        void setDescription(String description) {
            field.setDescription(description);
        }
    }
    private class CinemaField extends BoardField{
        private GUI_Street field;
        public CinemaField(){
            field = new GUI_Street();
            setTitle("Cinema");
            setBackGroundColor(Color.RED);
            setDescription("Cinema description here");
            setSubText("subtext here");
        }

        private void setSubText(String subtext) {
            field.setSubText(subtext);
        }

        private void setBackGroundColor(Color color) {
            field.setBackGroundColor(color);
        }

        void setTitle(String title) {
            field.setTitle(title);
        }

        String getTitle() {
            return field.getTitle();
        }

        GUI_Street getField() {
            return field;
        }

        void setDescription(String description) {
            field.setDescription(description);
        }
    }
    private class TheaterField extends BoardField{
        private GUI_Street field;
        public TheaterField(){
            field = new GUI_Street();
            setTitle("Theater");
            setBackGroundColor(Color.RED);
            setDescription("Theater description here");
            setSubText("subtext here");
        }

        private void setSubText(String subtext) {
            field.setSubText(subtext);
        }
        private void setBackGroundColor(Color color) {
            field.setBackGroundColor(color);
        }

        void setTitle(String title) {
            field.setTitle(title);
        }

        String getTitle() {
            return field.getTitle();
        }

        GUI_Street getField() {
            return field;
        }

        void setDescription(String description) {
            field.setDescription(description);
        }
    }
    private class StartField extends BoardField{
        private GUI_Start field;

        public StartField() {
            field = new GUI_Start();
            setTitle("Start");
            setBackGroundColor(Color.WHITE);
            setDescription("Starting point");
            setSubText("subtext here");
        }

        private void setSubText(String subtext) {
            field.setSubText(subtext);
        }
        private void setBackGroundColor(Color color) {
            field.setBackGroundColor(color);
        }

        void setTitle(String title) {
            field.setTitle(title);
        }

        String getTitle() {
            return field.getTitle();
        }

        GUI_Start getField() {
            return field;
        }

        void setDescription(String description) {
            field.setDescription(description);
        }

    }
}
