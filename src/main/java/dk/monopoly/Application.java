package dk.monopoly;

import dk.monopoly.common.MonopolyGui;
import gui_fields.GUI_Field;
import gui_fields.GUI_Player;
import gui_fields.GUI_Start;
import gui_fields.GUI_Street;
import gui_main.GUI;

import java.awt.*;

public class Application {

    public static void main(String[] args){

        MonopolyGui monopolyGui = new MonopolyGui();
        monopolyGui.start();
        /*
        GUI_Field[] fields = new GUI_Field[20];

        GUI_Player ico = new GUI_Player("Ico");
        GUI_Player kaloyan = new GUI_Player("Kaloyan");

        GUI_Start start = new GUI_Start();
        start.setTitle("Start");
        start.setBackGroundColor(Color.BLUE);
        start.setDescription("Starting point");

        GUI_Street street = new GUI_Street();
        street.setTitle("Main street");
        street.setRentLabel("Buy it now!");
        street.setRent("600,-");
        street.setBorder(Color.BLUE, Color.CYAN);

        fields[0] = start;
        fields[1] = street;
        //fields[2] = ;
        //fields[3] = street;

        GUI.setNull_fields_allowed(true);
        GUI gui = new GUI(fields);
        gui.setDice(1,4);
        gui.addPlayer(ico);
        gui.addPlayer(kaloyan);

        String buttonName = gui.getUserButtonPressed("It's your turn " , "Roll");

        fields[0].setCar(ico,true);
        fields[0].setCar(kaloyan,true);

        */

    }
}
