package com.company;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class WorldCreator {
    static Scanner in = new Scanner(System.in);
    public static void main (String args[]) throws SQLException {
        var itemList = getItems();
        var entityList = getEntities();
        var roomList = new ArrayList<Room>();
        System.out.println("Welcome to the World Editor, where you can create or edit world");
        System.out.println("spaces using the packaged Items and NPCs!");
        String databaseChoice = "itemlist";
        while (databaseChoice.equals("itemlist")) {
            System.out.println("Please enter a file name for your world, except 'itemlist'.");
            databaseChoice = in.nextLine();
        }
        Connection conn = connect(databaseChoice);
        var rooms = new ArrayList<Room>();
        var defineTable = conn.createStatement();
        defineTable.execute("CREATE TABLE IF NOT EXISTS rooms (" +
                "title TEXT," +
                "description TEXT," +
                "entities TEXT," + //"ARRAY"
                "items TEXT," + //"ARRAY"
                "rooms TEXT," + //"ARRAY"
                "commands TEXT" + //"ARRAY"
                ");");
        var statement = conn.createStatement();
        var results = statement.executeQuery("SELECT * FROM rooms");
        while (results.next()) {
            int i;
            Room temp = new Room();
            temp.SetTitle(results.getString("title"));
            temp.SetBaseDescription(results.getString("description"));

            String entRef = results.getString("entities");
            String itemRef = results.getString("items");

            String roomRef = results.getString("rooms");
            //int[] roomIntRef = Arrays.stream(itemRef.split(".")).mapToInt(Integer::parseInt).toArray();

            String comRef = results.getString("commands");
            //String[] commandsRef = comRef.split(".", 0);

            temp.setEntitiesRef(entRef);
            temp.setItemsRef(itemRef);
            temp.setRoomsRef(roomRef);
            temp.setRoomCommandsRef(comRef);
            roomList.add(temp);
        }

        String choice = "";
        while (!choice.equals("save") && !choice.equals("quit")) {
            System.out.println("/--------------------------------/");
            if (roomList.size() == 0) {
                System.out.println("This world contains no rooms.");
            }
            else {
                int j = 0;
                System.out.println("Current rooms:");
                for (Room i : roomList) {
                    System.out.println(j + ": " + i.GetTitle());
                    System.out.println(i.GetBaseDescription());
                    System.out.println("NPC ID list: " + i.getEntitiesRef());
                    System.out.println("Item ID list: " + i.getItemsRef());
                    System.out.println("Linked Rooms list: " + i.getRoomsRef());
                    System.out.println("Commands: " + i.getRoomCommandsRef());
                    System.out.println("---------------------------------");
                    j += 1;
                }
            }

            System.out.println("What would you like to do?");
            System.out.println("[replace] - redefine an existing room.");
            System.out.println("[create] - create a new room.");
            System.out.println("[save] - exit and save your changes.");
            System.out.println("[link] - link two rooms together.");
            System.out.println("[quit] - exit WITHOUT saving your changes.");
            choice = in.nextLine();
            if (choice.equals("create") || choice.equals("replace")) {
                int replaceID = -1;
                if (choice.equals("replace")) {
                    String i;
                    while (true) {
                        System.out.println("Please enter the ID of the room you want to replace.");
                        i = in.nextLine();
                        try {
                            replaceID = Integer.parseInt(i);
                            break;
                        } catch (NumberFormatException nfe) {
                            continue;
                        }
                    }


                }
                Room temp = new Room();
                String tz;
                System.out.println("Enter the name of the room.");
                tz = in.nextLine();
                temp.SetTitle(tz);
                System.out.println("Provide a description for this room.");
                tz = in.nextLine();
                temp.SetBaseDescription(tz);
                tz = "-";
                int d;
                String entsRef = "";
                while (!tz.equals("done")) {
                    System.out.println("Enter the ID's of the NPCs for this room");
                    System.out.println("or type [list] for a list of all NPCs.");
                    System.out.println("Type [current] for a read out of what you have so far.");
                    System.out.println("Type [done] to move on.");
                    tz = in.nextLine();
                    if (tz.equals("list")) {
                        int j = 0;
                        for (Entity ez : entityList) {
                            System.out.println(j + ": " + ez.getName());
                            j += 1;
                        }
                    }
                    else if (tz.equals("current")) {
                        System.out.println("Current: " + entsRef);
                    }
                    else {
                        try {
                            d = Integer.parseInt(tz);
                            entsRef += d + ".";
                        } catch (NumberFormatException nfe) {
                            continue;
                        }
                    }
                }
                temp.setEntitiesRef(entsRef);
                tz = "-";
                String itemsRef = "";
                while (!tz.equals("done")) {
                    System.out.println("Enter the ID's of the items for this room");
                    System.out.println("or type [list] for a list of all Items.");
                    System.out.println("Type [done] to move on.");
                    tz = in.nextLine();
                    if (tz.equals("list")) {
                        int j = 0;
                        for (Item ez : itemList) {
                            System.out.println(j + ": " + ez.getName());
                            j += 1;
                        }
                    }
                    else {
                        try {
                            d = Integer.parseInt(tz);
                            itemsRef += d + ".";
                        } catch (NumberFormatException nfe) {
                            continue;
                        }
                    }
                }
                temp.setItemsRef(itemsRef);
                System.out.println("Room Created");
                if (choice.equals("replace")) {
                    roomList.set(replaceID, temp);
                }
                else {
                    roomList.add(temp);
                }

            }
//            if (choice.equals("edit")) {
//                int d;
//                while (true) {
//                    System.out.println("Enter the numeric ID of the room you want to edit.");
//                    choice = in.nextLine();
//                    try {
//                        d = Integer.parseInt(choice);
//                    } catch (NumberFormatException nfe) {
//                        continue;
//                    }
//                    break;
//                }
//                if (d > -1 && d < roomList.size()) {
//
//                }
//                else {
//
//                }
//
//            }
            if (choice.equals("link")) {
                int d = -2;
                int z = -2;
                String chaza;
                while (d < -1 || d >= roomList.size()) {
                    System.out.println("Enter the ID for a room to link *from*, or -1 to cancel.");
                    chaza = in.nextLine();
                    try {
                        d = Integer.parseInt(chaza);
                    } catch (NumberFormatException nfe) {
                        continue;
                    }
                }
                if (d == -1) {
                    continue;
                }
                while (z < -1 || z >= roomList.size()) {
                    System.out.println("Enter the ID for a room to link *to*, or -1 to cancel.");
                    chaza = in.nextLine();
                    try {
                        z = Integer.parseInt(chaza);
                    } catch (NumberFormatException nfe) {
                        continue;
                    }
                }
                if (z == -1) {
                    continue;
                }
                String com;
                while (true) {
                    System.out.println("Enter a one-word command for the transition from " + roomList.get(d).GetTitle() +
                            " to " + roomList.get(z).GetTitle());
                    com = in.nextLine();
                    for (char reg : com.toCharArray()) {
                        if (Character.isWhitespace(reg)) {
                            continue;
                        }
                    }
                    break;
                }
                String oldRoomRef = roomList.get(d).getRoomsRef();
                String oldRoomComRef = roomList.get(d).getRoomCommandsRef();
                if (oldRoomRef == null) {
                    oldRoomRef = "";
                }
                if (oldRoomComRef == null) {
                    oldRoomComRef = "";
                }
                roomList.get(d).setRoomsRef(oldRoomRef + z + ".");
                roomList.get(d).setRoomCommandsRef(oldRoomComRef + com + ".");




            }
            if (choice.equals("save")) {
                var dropAndSave = conn.createStatement();
                dropAndSave.execute("DROP TABLE IF EXISTS rooms;");
                dropAndSave.execute("CREATE TABLE IF NOT EXISTS rooms (" +
                        "title TEXT," +
                        "description TEXT," +
                        "entities TEXT," + //"ARRAY"
                        "items TEXT," + //"ARRAY"
                        "rooms TEXT," + //"ARRAY"
                        "commands TEXT" + //"ARRAY"
                        ");");
                for (Room i : roomList) {
                    String query = " INSERT INTO rooms"
                            + " VALUES (?, ?, ?, ?, ?, ?)";
                    PreparedStatement preparedStmt = conn.prepareStatement(query);
                    preparedStmt.setString(1, i.GetTitle());
                    preparedStmt.setString(2, i.GetBaseDescription());
                    preparedStmt.setString(3, i.getEntitiesRef());
                    preparedStmt.setString(4, i.getItemsRef());
                    preparedStmt.setString(5, i.getRoomsRef());
                    preparedStmt.setString(6, i.getRoomCommandsRef());
                    preparedStmt.execute();
                }
            }
        }
    }
    //--------------+
    //DATABASE STUFF|
    //--------------+
    private static Connection connect(String database) {
        String dburl = "jdbc:sqlite:"+database+".db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(dburl);
            System.out.println("Connection to SQLite has been established.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
        return conn;
    }
    public static ArrayList<Item> getItems() throws SQLException {
        Connection conn = connect("itemlist");
        var items = new ArrayList<Item>();
        var statement = conn.createStatement();
        var results = statement.executeQuery("SELECT * FROM items;");
        while (results.next()) {
            Item temp = new Item();
            temp.setName(results.getString("name"));
            temp.setFlavorText(results.getString("flavor_text"));
            temp.setEffect(results.getInt("effect"));
            temp.setEffectRating(results.getInt("effect_rating"));
            temp.setStrengthRequirement(results.getInt("strength_req"));
            temp.setStrengthScaling(results.getFloat("strength_scale"));
            temp.setDexterityRequirement(results.getInt("dexterity_req"));
            temp.setStrengthScaling(results.getFloat("dexterity_scale"));
            temp.setPowerRequirement(results.getInt("power_req"));
            temp.setPowerScaling(results.getFloat("power_scale"));
            temp.setWillRequirement(results.getInt("will_req"));
            temp.setWillScaling(results.getFloat("will_scale"));
            temp.setWeight(results.getFloat("weight"));
            temp.setValue(results.getInt("value"));
            items.add(temp);
        }
        return items;
    }
    public static ArrayList<Entity> getEntities() throws SQLException {
        Connection conn = connect("itemlist");
        var ents = new ArrayList<Entity>();
        var statement = conn.createStatement();
        var results = statement.executeQuery("SELECT * FROM entities");
        while (results.next()) {
            Entity temp = new Entity();
            temp.setName(results.getString("name"));
            temp.SetHP(results.getInt("hp"));
            temp.setStrength(results.getInt("strength"));
            temp.setDexterity(results.getInt("dexterity"));
            temp.setPower(results.getInt("power"));
            temp.setWill(results.getInt("will"));
            temp.setAgility(results.getInt("agility"));
            temp.SetDefaultLine(results.getString("default_line"));
            temp.SetAggroLine(results.getString("agro_line"));
            temp.setPrimaryAttack(results.getInt("primary_attack"));
            temp.setPrimaryValue(results.getInt("primary_value"));
            ents.add(temp);
        }
        return ents;
    }
}
