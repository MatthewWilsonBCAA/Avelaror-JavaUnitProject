package com.company;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
public class Main {
    public static Scanner input = new Scanner(System.in);
    public static ArrayList<Room> rooms = new ArrayList<Room>();
    static Entity[] entityList = {
            new Entity("John_Doe", 5, 5,5 ,5 ,5 ,5, 1, 5),
    };
    static void EntityInitialization() {
        entityList[0].SetDefaultLine("Hey there!");
        entityList[0].SetAggroLine("I'll kill you!");
        entityList[0].SetHP(30);
    }
    static void RoomInitialization() {
        String[] _names = {"Dark Cave Room", "Lit Cave Room", "Exit Perimeter"};
        String[] _descriptions = {"You are in a dark room that lacks any notable features.", "You are in a lit room with a bloodied floor", "You stand at the fringe of the cave, overviewing a large valley"};
        int[][] _paths = {new int[]{1}, new int[]{0,2},new int[]{1}};
        String[][] _coms = {new String[]{"north"}, new String[]{"south", "east"}, new String[]{"west"}};

        // item table
        ArrayList<Item> baseLoot = new ArrayList<Item>(Arrays.asList(itemList[0], itemList[1]));
        ArrayList<Item> vLoot = new ArrayList<Item>(Arrays.asList(itemList[2], itemList[2]));
        ArrayList<ArrayList<Item>> lootTables = new ArrayList<ArrayList<Item>>(Arrays.asList(baseLoot, vLoot, new ArrayList<Item>()));

        // entity table
        ArrayList<Entity> johnOnly = new ArrayList<Entity>();
        johnOnly.add(entityList[0]);
        ArrayList<ArrayList<Entity>> entityTables = new ArrayList<ArrayList<Entity>>(Arrays.asList(new ArrayList<Entity>(), johnOnly, new ArrayList<Entity>()));

        int i;
        for (i = 0; i < _names.length; i++) {
            rooms.add(new Room(
                    _names[i],
                    _descriptions[i],
                    entityTables.get(i),
                    lootTables.get(i),
                    _paths[i],
                    _coms[i]
            ));
        }
    }
    public static void main(String[] args) {
        EntityInitialization();
        RoomInitialization();
        Entity player = new Entity("player", 10, 10, 10, 10, 10, 10, 0, 0);
        player.SetHP(player.maxHp);
	    State mainState = new State(player, rooms, 0);
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
