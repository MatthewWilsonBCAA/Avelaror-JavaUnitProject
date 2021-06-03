package com.company;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
public class Main {
    public static Scanner input = new Scanner(System.in);
    static ArrayList<Entity> entityList = new ArrayList<>();
    static ArrayList<Item> itemList;


    public static void main(String[] args) throws SQLException {
        System.out.println("Welcome to Stardust Kingdoms, The Text Adventure!");
        System.out.println("-------------------------------------------------");
        System.out.println("Please enter the world (.db) file you want to load (the game will crash if you mistype)");
        String zeta = input.nextLine();
        State mainState = new State(zeta);
        String isLoading = "";
        while (!isLoading.equals("continue") && !isLoading.equals("new")) {
            System.out.println("Would you like to [continue] your current save or start a [new] one.");
            System.out.println("Notes:");
            System.out.println("If you enter continue without a save for this world, you will default");
            System.out.println("to a new game. Also, entering new will erase all of your old progress.");
            isLoading = input.nextLine();
        }



        itemList = mainState.getItems();
        entityList = mainState.getEntities();
        mainState.setPlayer(isLoading);
        mainState.setRooms(mainState.getRooms(itemList, entityList));

        String j = "bean";
        String toPrint = "";
        while (!j.equals("exit")) {
            System.out.println(mainState.GetRoomDescription());
            System.out.println("At any time you can type [help] for a command reference.");
            j = input.nextLine();
            if (j.equals("help")) {
                System.out.println("Command reference:");
                System.out.println("grab/pickup <item_name>: use this to add items to your inventory");
                System.out.println("drop <item_name>: use this to drop items");
                System.out.println("go/enter <path>: use this to take a path");
                System.out.println("show inventory: show is optional, but shows items you have on you");
                System.out.println("use/activate <inventory_item> on <target>: apply the effect of an item onto an entity");
                System.out.println("save: record your current save");
                System.out.println("exit: close the game out");
                continue;
            }
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
