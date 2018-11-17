package dk.monopoly.common;

import gui_fields.GUI_Field;
import gui_fields.GUI_Player;
import gui_fields.GUI_Street;
import gui_main.GUI;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class MonopolyGui {
    private boolean isFirstPlyerTurn = true;
    private SetUpViewModel setUpViewModel;
    private RollingDiceViewModel rollingDiceViewModel;
    private GUI_Player player1;
    private GUI_Player player2;
    private GUI gui;
    private PlayerGateway playerGateway = new PlayerGateway();
    private String nameFirstPlayer="";
    private String nameSecondPlayer="";

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
        if (isFirstPlyerTurn) {
            rollingDiceViewModel.playerName = setUpViewModel.nameFirstPlayer;
            isFirstPlyerTurn = false;
        } else {
            rollingDiceViewModel.playerName = setUpViewModel.nameSecondPlayer;
            isFirstPlyerTurn = true;
        }
    }

    private void executeRollingDiceUseCase() {
        RollingDicePresenter rollingDicePresenter = new RollingDicePresenter(this, rollingDiceViewModel);
        RollingDiceImpl rollingDiceImpl = new RollingDiceImpl(rollingDicePresenter, playerGateway);
        RollingDiceController rollingDiceController = new RollingDiceController(rollingDiceViewModel, rollingDiceImpl);
        rollingDiceController.execute();
    }

    private void executeSetUpUseCase() {
        setUpViewModel = new SetUpViewModel();
        setUpViewModel.nameFirstPlayer = nameFirstPlayer;
        setUpViewModel.nameSecondPlayer = nameSecondPlayer;
        SetUpPresenter setUpPresenter = new SetUpPresenter(this, setUpViewModel);
        SetUpImpl setUpImpl = new SetUpImpl(setUpPresenter, playerGateway);
        SetUpController setUpController = new SetUpController(setUpViewModel, setUpImpl);
        setUpController.execute();
    }

    private void setupPlayers() {
        ExecutorService pool = Executors.newFixedThreadPool(2);
        List<Future<String>> names = new ArrayList<>();
        Future<String> futureSecondPlayer = pool.submit(new GetPalyerNameCallable("Type the name of the second player."));
        Future<String> futureFirstPlayer = pool.submit(new GetPalyerNameCallable("Type the name of the first player."));
        names.add(futureFirstPlayer);
        names.add(futureSecondPlayer);

        try {
            nameFirstPlayer = names.get(0).get();
            nameSecondPlayer = names.get(1).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
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
        player1 = new GUI_Player(setUpViewModel.nameFirstPlayer, setUpViewModel.balanceFirstPlayer);
        player2 = new GUI_Player(setUpViewModel.nameSecondPlayer, setUpViewModel.balanceSecondPlayer);
        gui.addPlayer(player1);
        gui.addPlayer(player2);
    }

    public void sendSuccessMsg() {
        JOptionPane.showMessageDialog(null, setUpViewModel.msg, "Success!", JOptionPane.INFORMATION_MESSAGE);
    }

    private void askPlayerToRoll(){
        String buttonName = gui.getUserButtonPressed("It's your turn " + rollingDiceViewModel.playerName, "Roll");
    }

    public void update() {
        gui.setDice(rollingDiceViewModel.firstDie, rollingDiceViewModel.secondDie);
        if(!isFirstPlyerTurn){
            player1.setBalance(rollingDiceViewModel.balance);
        }else{
            player2.setBalance(rollingDiceViewModel.balance);
        }
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
