package com.company;
import java.util.ArrayList;
public class Room {
    public String title;
    public String baseDescription;
    public ArrayList<Entity> entities = new ArrayList<Entity>();
    public ArrayList<Item> items = new ArrayList<Item>();
    //these are going to be parallel lists
    int[] rooms;
    String[] roomCommands;
    public Room (String t, String b, ArrayList<Entity> e, ArrayList<Item> i, int[] r, String[] rc) {
        title = t;
        baseDescription = b;
        entities = e;
        items = i;
        rooms = r;
        roomCommands = rc;
    }
    public int CheckDirection(String in) {
        int i;
        for (i = 0; i < rooms.length; i++) {
            if (in.equals(roomCommands[i])) {
                return rooms[i];
            }
        }
        return -1;
    }
}
