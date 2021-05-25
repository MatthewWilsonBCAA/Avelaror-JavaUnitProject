package com.company;
import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
public class Main {
    public static Scanner input = new Scanner(System.in);
    public static ArrayList<Room> rooms = new ArrayList<Room>();
    static ArrayList<Entity> entityList = new ArrayList<>();
    static ArrayList<Item> itemList;


    public static void main(String[] args) throws SQLException {
        System.out.println("Please enter the .db file you want to load (the game will crash if you mistype)");
        String zeta = input.nextLine();
        State mainState = new State(zeta);
        mainState.setPlayer();
        itemList = mainState.getItems();
        entityList = mainState.getEntities();
        mainState.SetRooms(mainState.getRooms(itemList, entityList));

        String j = "bean";
        String toPrint = "";
        while (!j.equals("exit")) {
            System.out.println(mainState.GetRoomDescription());
            j = input.nextLine();

            toPrint = mainState.ReceiveInput(j);

            System.out.println(toPrint);
            System.out.println("(press enter to continue)");
            j = input.nextLine();
            if (toPrint.equals("You died...")) {
                break;
            }
        }
    }
}
