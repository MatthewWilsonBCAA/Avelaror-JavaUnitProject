package com.company;
import java.util.Scanner;
import java.util.ArrayList;
public class Main {
    public static Scanner input = new Scanner(System.in);
    static Item[] itemList = {
            new Item("Short_Sword", "A standard self-defense weapon", 1, 10, 5, 1, 10, 2, 0, 0, 0, 0, 2, 30),
            new Item("Gold_Sack", "A sack of 20 gold pieces", 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 20),
            new Item("Large_Gold_Sack", "A sack of 50 gold pieces", 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 50),
    };
    static Entity[] entityList = {
            new Entity("John_Doe", 5, 5,5 ,5 ,5 ,5),
    };
    public static void main(String[] args) {
        entityList[0].SetDefaultLine("Hey there!");
        entityList[0].SetHP(30);
        //a series of parallel arrays
        String[] _names = {"Dark Cave Room", "Lit Cave Room", "Exit Perimeter"};
        String[] _descriptions = {"You are in a dark room that lacks any notable features.", "You are in a lit room with a bloodied floor", "You stand at the fringe of the cave, overviewing a large valley"};
        int[] r = {1};
        String[] rc = {"north"};
        int[] x = {0, 2};
        String[] xc = {"south", "east"};
        String[] zc = {"west"};
        int[][] _paths = {r,x,r};
        String[][] _coms = {rc, xc, zc};
        ArrayList<Item> baseLoot = new ArrayList<Item>();
        baseLoot.add(itemList[0]);
        baseLoot.add(itemList[1]);
        ArrayList<Item> vLoot = new ArrayList<Item>();
        vLoot.add(itemList[2]);
        vLoot.add(itemList[2]);
        ArrayList<ArrayList<Item>> lootTables = new ArrayList<ArrayList<Item>>();
        ArrayList<ArrayList<Entity>> entityTables = new ArrayList<ArrayList<Entity>>();
        lootTables.add(baseLoot);
        lootTables.add(vLoot);
        lootTables.add(new ArrayList<Item>());
        entityTables.add(new ArrayList<Entity>());
        ArrayList<Entity> johnOnly = new ArrayList<Entity>();
        johnOnly.add(entityList[0]);
        entityTables.add(johnOnly);
        entityTables.add(new ArrayList<Entity>());
	    Entity player = new Entity("player", 10, 10, 10, 10, 10, 10);
        ArrayList<Room> rooms = new ArrayList<Room>();
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
	    State mainState = new State(player, rooms, 0);
        String j = "bean";
        while (!j.equals("exit")) {
            System.out.println(mainState.GetRoomDescription());
            j = input.nextLine();
            System.out.println(mainState.ReceiveInput(j));
            System.out.println("(press enter to continue)");
            j = input.nextLine();
        }
    }
}
