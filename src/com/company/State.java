package com.company;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
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
        String ent = "";
        int i;
        Room cur = allRooms.get(roomID);
        for (i = 0; i < cur.roomCommands.length; i++) {
            paths = paths + "A path leads '" + cur.roomCommands[i] + "'\n";
        }
        if (cur.items != null) {
            for (i = 0; i < cur.items.size(); i++) {
                items = items + "A '" + cur.items.get(i).getName() + "' is in the room\n";
            }
        }
        if (cur.entities != null) {
            for (i = 0; i < cur.entities.size(); i++) {
                ent = ent + cur.entities.get(i).getName() + ": " + cur.entities.get(i).GetDefaultLine() + "\n";
            }
        }

        return "|" + cur.title + "|\n" + cur.baseDescription + "\n" + paths + items + ent;
    }
    public String GetInventory() {
        if (player.getInventory().size() == 0) {
            return "Your inventory is empty";
        }
        String items = "";
        int i;
        for (i = 0; i < player.inventory.size(); i++) {
            items = items + (i+1) + ": " + player.inventory.get(i).getName() + "\n";
        }
        return items;
    }
    public String PickUpItem(String input) {
        Room cur = allRooms.get(roomID);
        int i;
        for (i = 0; i < cur.items.size(); i++) {
            if (cur.items.get(i).getName().equals(input)) {
                String n = cur.items.get(i).getName();
                player.inventory.add(cur.items.get(i));
                cur.items.remove(i);
                return n;
            }
        }
        return "INVALID";
    }
    public String DropItem(String input) {
        int i;
        for (i = 0; i < player.inventory.size(); i++) {
            if (player.inventory.get(i).getName().equals(input)) {
                Room cur = allRooms.get(roomID);
                cur.items.add(player.inventory.get(i));
                String temp = player.inventory.get(i).getName();
                player.inventory.remove(i);
                return temp;
            }
        }
        return "INVALID";
    }
    public int GetStatBonus(int str, int sReq, float sSc, int dex, int dReq, float dSc, int pow, int pReq, float pSc, int wil, int wReq, float wSc) {
        int strBonus = 0;
        int dexBonus = 0;
        int powBonus = 0;
        int wilBonus = 0;
        strBonus = (int) ( (str - sReq) * sSc);
        dexBonus = (int) ( (str - dReq ) * dSc);
        powBonus = (int) ( (str - pReq) * pSc);
        wilBonus = (int) ( (str - wReq) * wSc);
        return strBonus + dexBonus + powBonus + wilBonus;
    }
    public String ApplyEffect(String argOne, String argTwo) {
        int itemEffect = 0;
        int itemValue = 0;
        int sReq;
        float sSc;
        int dReq;
        float dSc;
        int pReq;
        float pSc;
        int wReq;
        float wSc;
        int i;
        String result;
        for (i = 0; i < player.inventory.size(); i++) {
            if (player.inventory.get(i).getName().equals(argOne)) {
                itemEffect = player.inventory.get(i).effect;
                itemValue = player.inventory.get(i).effectRating;
                sReq = player.inventory.get(i).strengthRequirement;
                sSc = player.inventory.get(i).strengthScaling;
                dReq = player.inventory.get(i).dexterityRequirement;
                dSc = player.inventory.get(i).dexterityScaling;
                pReq = player.inventory.get(i).powerRequirement;
                pSc = player.inventory.get(i).powerScaling;
                wReq = player.inventory.get(i).willRequirement;
                wSc = player.inventory.get(i).willScaling;
                itemValue += GetStatBonus(player.strength, sReq, sSc, player.dexterity, dReq, dSc, player.power, pReq, pSc, player.will, wReq, wSc);
            }
        }
        if (itemEffect != 0) {
            Room cur = allRooms.get(roomID);
            for (i = 0; i < cur.entities.size(); i++) {
                if (cur.entities.get(i).getName().equals(argTwo)) {
                    result = cur.entities.get(i).applyEffect(itemEffect, itemValue, "You", cur.entities.get(i).getName());
                    if (result.equals("DEFEAT")) {
                        cur.entities.remove(i);
                        return "You killed the target!";
                    }
                    return result;
                }
            }
        }
        return "That was not a valid item and/or entity!";
    }
    String enemyCheck() {
        String toSend = "";
        Room cur = allRooms.get(roomID);
        for (Entity ent : cur.entities) {
            if (ent.checkAggro()) {
                toSend += player.applyEffect(ent.GetPrimaryAttack(), ent.GetPrimaryValue(), ent.getName(), "you") + "\n";
            }
        }
        return toSend;
    }

    public String ReceiveInput(String input) {
        String[] args = input.split(" ", 0);
        String toReturn = "That was not a valid command!";
        if ((args[0].equals("go") || args[0].equals("enter")) && args.length > 1) {
            int temp = allRooms.get(roomID).CheckDirection(args[1]);
            if (temp != -1) {
                roomID = temp;
                toReturn = "You go into " + allRooms.get(roomID).title + "\n";
            }
        }
        if ((args[0].equals("pickup") || args[0].equals("grab")) && args.length > 1) {
            String temp = PickUpItem(args[1]);
            if (!temp.equals("INVALID")) {
                toReturn = "You picked the '" + temp + "' up" + "\n";
            }
            else {
                toReturn = "There is no item of that name in this room" + "\n";
            }
        }
        if ((args[0].equals("drop") && args.length > 1)) {
            String temp = DropItem(args[1]);
            if (!temp.equals("INVALID")) {
                toReturn = "You dropped the '" + temp + "' up" + "\n";
            }
            else {
                toReturn = "There is no item of that name in your inventory" + "\n";
            }
        }
        if ((args[0].equals("show") && args[1].equals("inventory")) || args[0].equals("inventory")) {
            toReturn = GetInventory();
        }
        if ((args[0].equals("use") || args[0].equals("activate")) && args[2].equals("on") && args.length == 4) {
            toReturn = ApplyEffect(args[1],args[3]);
        }
        if (args[0].equals("exit")) {
            toReturn = "Are you sure? (Type 'exit' again)";
        }
        String temp = enemyCheck();
        if (temp.contains("DEFEAT")) {
            return "You died...";
        }
        toReturn += temp;

        return toReturn;
    }
    //--------------+
    //DATABASE STUFF|
    //--------------+
    private static Connection connect() {
        String dburl = "jdbc:sqlite:itemlist.db";
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
        Connection conn = connect();
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
}
