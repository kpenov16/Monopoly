package dk.monopoly.common;

import gui_fields.GUI_Field;
import gui_fields.GUI_Player;
import gui_fields.GUI_Street;
import gui_main.GUI;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class MonopolyGui {
    private int playerTurnIndex = 0;
    private SetUpViewModel setUpViewModel;
    private RollingDiceViewModel rollingDiceViewModel;
    private GUI_Player player1;
    private GUI_Player player2;
    private List<GUI_Player> playersGui = new ArrayList<>();
    private GUI gui;
    private PlayerGateway playerGateway = new PlayerGateway();
    List<String> playersNames = new ArrayList<>();
    private int numberOfPlayers = 0;

    class GetPalyerNameCallable implements Callable<String> {
        String msg = "";
        public  GetPalyerNameCallable(String msg){
            this.msg = msg;
        }
        @Override
        public String call() throws Exception {
            return JOptionPane.showInputDialog(msg);
        }
    }

    public void start() {
        setupPlayers();
        executeSetUpUseCase();
        setupGUI();
        rollingDiceViewModel = new RollingDiceViewModel();
        while (!isWinner()) {
            evaluatePlayerTurn();
            askPlayerToRoll();
            executeRollingDiceUseCase();
        }
        notifyTheWinner(rollingDiceViewModel.playerName + ". You won the game!");
    }

    private boolean isWinner() {
        return rollingDiceViewModel.isWinner;
    }

    private void notifyTheWinner(String s) {
        gui.showMessage(s);
    }

    private void evaluatePlayerTurn() {
        rollingDiceViewModel.playerName = setUpViewModel.playersNames.get(playerTurnIndex++);
    }

    private int generateNextPlayerIndex() {
        if(playerTurnIndex == numberOfPlayers)
            playerTurnIndex = 0;
        return playerTurnIndex;
    }

    private void executeRollingDiceUseCase() {
        RollingDicePresenter rollingDicePresenter = new RollingDicePresenter(this, rollingDiceViewModel);
        RollingDiceImpl rollingDiceImpl = new RollingDiceImpl(rollingDicePresenter, playerGateway);
        RollingDiceController rollingDiceController = new RollingDiceController(rollingDiceViewModel, rollingDiceImpl);
        rollingDiceController.execute();
    }

    private void executeSetUpUseCase() {
        setUpViewModel = new SetUpViewModel();
        setUpViewModel.playersNames = playersNames;
        SetUpPresenter setUpPresenter = new SetUpPresenter(this, setUpViewModel);
        SetUpImpl setUpImpl = new SetUpImpl(setUpPresenter, playerGateway);
        SetUpController setUpController = new SetUpController(setUpViewModel, setUpImpl);
        setUpController.execute();
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
            Response response = new Response(values);
            try {
                SwingUtilities.invokeAndWait(response);
                numberOfPlayers = response.getResponse();
            } catch (InterruptedException | InvocationTargetException ex) {
                ex.printStackTrace();
            }
        }
        return numberOfPlayers;
    }
    private class Response implements Runnable{
        private String[] values;
        private String response;
        public Response(String... values) {this.values = values;}
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
        //List<String> names = askForPlayersNames(playerIndex);
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
        /*
        ExecutorService pool = Executors.newFixedThreadPool(playerIndex);
        List<Future<String>> futureNames = new ArrayList<>();
        for (int i=0;i<playerIndex;i++){
            Future<String> player = pool.submit(new GetPalyerNameCallable("Type the name of player " + (i+1) + ": "));
            futureNames.add(player);
        }

        List<String> names = new ArrayList<>();
        try {
            for (int i=0;i<playerIndex;i++)
                names.add( futureNames.get(0).get() );
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return names;
        */
    }


    private List<String> askForPlayersNames(int numberOfPlayers) {
        return null;
        /*
        if (EventQueue.isDispatchThread()) {
            ExecutorService pool = Executors.newFixedThreadPool(numberOfPlayers);
            List<Future<String>> futureNames = new ArrayList<>();
            for (int i=0;i<numberOfPlayers;i++){
                Future<String> player = pool.submit(new GetPalyerNameCallable("Type the name of player " + (i+1) + ": "));
                futureNames.add(player);
            }

            List<String> names = new ArrayList<>();
            try {
                for (int i=0;i<numberOfPlayers;i++)
                    names.add( futureNames.get(0).get() );
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            return names;
        } else {
            Response response = new Response(numberOfPlayers);
            try {
                SwingUtilities.invokeAndWait(response);
                numberOfPlayers = response.getResponse();
            } catch (InterruptedException | InvocationTargetException ex) {
                ex.printStackTrace();
            }
        }
        return numberOfPlayers;
        */
        /*
        ExecutorService pool = Executors.newFixedThreadPool(playerIndex);
        List<Future<String>> futureNames = new ArrayList<>();
        for (int i=0;i<playerIndex;i++){
            Future<String> player = pool.submit(new GetPalyerNameCallable("Type the name of player " + (i+1) + ": "));
            futureNames.add(player);
        }

        List<String> names = new ArrayList<>();
        try {
            for (int i=0;i<playerIndex;i++)
                names.add( futureNames.get(0).get() );
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return names;
        */
    }

    private void setupGUI() {
        GUI_Field[] fields = new GUI_Field[40];
        GUI.setNull_fields_allowed(true);
        Field tower = new Field();
        tower.setTitle("Tower");
        tower.setRent("250,+");
        tower.setDescription("Tower, you win " + 250 + "kr.!");
        fields[0] = tower.getField();

        Field crater = new Field();
        crater.setTitle("Crater");
        crater.setRent("100,-");
        crater.setDescription("Crater, you lose " + 100 + "kr.!");
        fields[1] = crater.getField();

        gui = new GUI(fields);
        for(int i=0;i<numberOfPlayers;i++)
            gui.addPlayer( new GUI_Player(setUpViewModel.playersNames.get(i), setUpViewModel.balancesPlayers.get(i)) );
    }

    public void sendSuccessMsg() {
        JOptionPane.showMessageDialog(null, setUpViewModel.msg, "Success!", JOptionPane.INFORMATION_MESSAGE);
    }

    private void askPlayerToRoll(){
        String buttonName = gui.getUserButtonPressed("It's your turn " + rollingDiceViewModel.playerName, "Roll");
    }

    public void update() {
        gui.setDice(rollingDiceViewModel.firstDie, rollingDiceViewModel.secondDie);

        playerTurnIndex = generateNextPlayerIndex();
        playersGui.get(playerTurnIndex).setBalance(rollingDiceViewModel.balance);

        /*if(!isFirstPlyerTurn){
            player1.setBalance(rollingDiceViewModel.balance);
        }else{
            player2.setBalance(rollingDiceViewModel.balance);
        }*/

        gui.showMessage(rollingDiceViewModel.msg);
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
}
