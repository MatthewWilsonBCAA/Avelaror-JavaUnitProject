package com.company;

import java.sql.Connection;
import java.sql.DriverManager;
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
            int[] entIntRef = Arrays.stream(entRef.split(".")).mapToInt(Integer::parseInt).toArray();
            var ents = new ArrayList<Entity>();
            for (i = 0; i < entIntRef.length; i++) {
                ents.add(entityList.get(entIntRef[i]));
            }
            String itemRef = results.getString("items");
            int[] itemIntRef = Arrays.stream(itemRef.split(".")).mapToInt(Integer::parseInt).toArray();
            var items = new ArrayList<Item>();
            for (i = 0; i < itemIntRef.length; i++) {
                items.add(itemList.get(itemIntRef[i]));
            }

            String roomRef = results.getString("rooms");
            int[] roomIntRef = Arrays.stream(itemRef.split(".")).mapToInt(Integer::parseInt).toArray();

            String comRef = results.getString("commands");
            String[] commandsRef = comRef.split(".", 0);

            temp.SetEntities(ents);
            temp.SetItems(items);
            temp.SetRooms(roomIntRef);
            temp.SetRoomCommands(commandsRef);
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
                    j += 1;
                }
            }

            System.out.println("What would you like to do?");
            System.out.println("[edit] - redefine an existing room.");
            System.out.println("[create] - create a new room.");
            System.out.println("[save] - exit and save your changes.");
            System.out.println("[quit] - exit WITHOUT saving your changes.");
            choice = in.nextLine();
            if (choice.equals("create")) {
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
                    System.out.println("Type [done] to move on.");
                    tz = in.nextLine();
                    if (tz.equals("list")) {
                        int j = 0;
                        for (Entity ez : entityList) {
                            System.out.println(j + ": " + ez.getName());
                            j += 1;
                        }
                    }
                    else {
                        try {
                            d = Integer.parseInt(choice);
                            entsRef += d + ".";
                        } catch (NumberFormatException nfe) {
                            continue;
                        }

                    }
                }
            }
            if (choice.equals("edit")) {
                int d;
                while (true) {
                    System.out.println("Enter the numeric ID of the room you want to edit.");
                    choice = in.nextLine();
                    try {
                        d = Integer.parseInt(choice);
                    } catch (NumberFormatException nfe) {
                        continue;
                    }
                    break;
                }
                if (d > -1 && d < roomList.size()) {

                }
                else {

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
        var results = statement.executeQuery("SELECT * FROM items");
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
            temp.setHp(results.getInt("hp"));
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
