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
        if (cur.items.size() > 0) {
            for (i = 0; i < cur.items.size(); i++) {
                items = items + "A '" + cur.items.get(i).name + "' is in the room\n";
            }
        }

        return "|" + cur.title + "|\n" + cur.baseDescription + "\n" + paths + "\n" + items;
    }
    public boolean PickUpItem(String input) {
        Room cur = allRooms.get(roomID);
        int i;
        for (i = 0; i < cur.items.size(); i++) {
            if (cur.items.get(i).name.equals(input)) {
                return true;
            }
        }
        return false;
    }
    public String ReceiveInput(String input) {
        String[] args = input.split(" ", 0);
        String toReturn = "That was not a valid command!";
        if (args[0].equals("go") || args[0].equals("enter")) {
            int temp = allRooms.get(roomID).CheckDirection(args[1]);
            if (temp != -1) {
                roomID = temp;
                toReturn = "You go into " + allRooms.get(roomID).title;
            }
        }
        if (args[0].equals("exit")) {
            toReturn = "Goodbye!";
        }
        return toReturn;
    }
}
