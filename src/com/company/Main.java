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
        Entity player = new Entity();
        player.setName("player");
        player.setVitality(10);
        player.setStrength(10);
        player.setDexterity(10);
        player.setPower(10);
        player.setWill(10);
        player.setAgility(10);
        player.SetHP(player.maxHp);
        State mainState = new State(player, 0);

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
