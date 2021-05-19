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
    static void RoomInitialization() {
        String[] _names = {"Dark Cave Room", "Lit Cave Room", "Exit Perimeter"};
        String[] _descriptions = {"You are in a dark room that lacks any notable features.", "You are in a lit room with a bloodied floor", "You stand at the fringe of the cave, overviewing a large valley"};
        int[][] _paths = {new int[]{1}, new int[]{0,2},new int[]{1}};
        String[][] _coms = {new String[]{"north"}, new String[]{"south", "east"}, new String[]{"west"}};

        // item table
        ArrayList<Item> baseLoot = new ArrayList<Item>(Arrays.asList(itemList.get(0), itemList.get(1)));
        ArrayList<Item> vLoot = new ArrayList<Item>(Arrays.asList(itemList.get(2), itemList.get(2)));
        ArrayList<ArrayList<Item>> lootTables = new ArrayList<ArrayList<Item>>(Arrays.asList(baseLoot, vLoot, new ArrayList<Item>()));

        // entity table
        ArrayList<Entity> johnOnly = new ArrayList<Entity>();
        johnOnly.add(entityList.get(0));
        ArrayList<ArrayList<Entity>> entityTables = new ArrayList<ArrayList<Entity>>(Arrays.asList(new ArrayList<Entity>(), johnOnly, new ArrayList<Entity>()));

        int i;
        for (i = 0; i < _names.length; i++) {
            Room temp = new Room();
            temp.SetTitle(_names[i]);
            temp.SetBaseDescription(_descriptions[i]);
            temp.SetItems(lootTables.get(i));
            temp.SetEntities(entityTables.get(i));
            temp.SetRooms(_paths[i]);
            temp.SetRoomCommands(_coms[i]);
            rooms.add(temp);
        }
    }
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
        State mainState = new State(player, rooms, 0);

        itemList = mainState.getItems();
        entityList = mainState.getEntities();
        RoomInitialization();




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
