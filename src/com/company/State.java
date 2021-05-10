package com.company;
import java.util.ArrayList;
public class State {
    Entity player;
    ArrayList<Room> allRooms;
    int roomID;
    public State(Entity p, ArrayList<Room> ar, int r) {
        player = p;
        allRooms = ar;
        roomID = r;
    }
    public String GetRoomDescription() {
        String paths = "";
        String items = "";
        int i;
        Room cur = allRooms.get(roomID);
        for (i = 0; i < cur.roomCommands.length; i++) {
            paths = paths + "A path leads '" + cur.roomCommands[i] + "'\n";
        }
        if (cur.items != null) {
            for (i = 0; i < cur.items.size(); i++) {
                items = items + "A '" + cur.items.get(i).name + "' is in the room\n";
            }
        }

        return "|" + cur.title + "|\n" + cur.baseDescription + "\n" + paths + "\n" + items;
    }
    public String GetInventory() {
        if (player.inventory.size() == 0) {
            return "Your inventory is empty";
        }
        String items = "";
        int i;
        for (i = 0; i < player.inventory.size(); i++) {
            items = items + (i+1) + ": " + player.inventory.get(i).name + "\n";
        }
        return items;
    }
    public String PickUpItem(String input) {
        Room cur = allRooms.get(roomID);
        int i;
        for (i = 0; i < cur.items.size(); i++) {
            if (cur.items.get(i).name.equals(input)) {
                String n = cur.items.get(i).name;
                player.inventory.add(cur.items.get(i));
                cur.items.remove(i);
                return n;
            }
        }
        return "INVALID";
    }
    public String ReceiveInput(String input) {
        String[] args = input.split(" ", 0);
        String toReturn = "That was not a valid command!";
        if ((args[0].equals("go") || args[0].equals("enter")) && args.length > 1) {
            int temp = allRooms.get(roomID).CheckDirection(args[1]);
            if (temp != -1) {
                roomID = temp;
                toReturn = "You go into " + allRooms.get(roomID).title;
            }
        }
        if ((args[0].equals("pickup") || args[0].equals("grab")) && args.length > 1) {
            String temp = PickUpItem(args[1]);
            if (!temp.equals("INVALID")) {
                toReturn = "You picked it up";
            }
            else {
                toReturn = "There is no item of that name in this room";
            }
        }
        if ((args[0].equals("show") && args[1].equals("inventory")) || args[0].equals("inventory")) {
            toReturn = GetInventory();
        }
        if (args[0].equals("exit")) {
            toReturn = "Goodbye!";
        }
        return toReturn;
    }
}
