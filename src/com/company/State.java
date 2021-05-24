package com.company;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class State {
    Entity player;
    ArrayList<Room> allRooms;
    int roomID;
    public State(Entity p, int r) {
        player = p;
        roomID = r;
    }
    public void SetRooms(ArrayList<Room> ar) {
        allRooms = ar;
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
        for (i = 0; i < player.getInventory().size(); i++) {
            if (player.getInventory().get(i).getName().equals(input)) {
                Room cur = allRooms.get(roomID);
                cur.items.add(player.getInventory().get(i));
                String temp = player.getInventory().get(i).getName();
                player.getInventory().remove(i);
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
        for (i = 0; i < player.getInventory().size(); i++) {
            if (player.getInventory().get(i).getName().equals(argOne)) {
                itemEffect = player.getInventory().get(i).effect;
                itemValue = player.getInventory().get(i).effectRating;
                sReq = player.getInventory().get(i).strengthRequirement;
                sSc = player.getInventory().get(i).strengthScaling;
                dReq = player.getInventory().get(i).dexterityRequirement;
                dSc = player.getInventory().get(i).dexterityScaling;
                pReq = player.getInventory().get(i).powerRequirement;
                pSc = player.getInventory().get(i).powerScaling;
                wReq = player.getInventory().get(i).willRequirement;
                wSc = player.getInventory().get(i).willScaling;
                itemValue += GetStatBonus(player.strength, sReq, sSc, player.dexterity, dReq, dSc, player.power, pReq, pSc, player.will, wReq, wSc);
            }
        }
        if (itemEffect != 0) {
            Room cur = allRooms.get(roomID);
            for (i = 0; i < cur.GetEntities().size(); i++) {
                if (cur.GetEntities().get(i).getName().equals(argTwo)) {
                    result = cur.GetEntities().get(i).applyEffect(itemEffect, itemValue, "You", cur.GetEntities().get(i).getName());
                    if (result.equals("DEFEAT")) {
                        cur.GetEntities().remove(i);
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
        for (Entity ent : cur.GetEntities()) {
            if (ent.checkAggro()) {
                toSend += player.applyEffect(ent.GetPrimaryAttack(), ent.GetPrimaryValue(), ent.getName(), "you") + "\n";
            }
            else if (ent.getBecomeHostile() == 1 && !ent.checkAggro()) {
                ent.applyEffect(-1, 0, "", "");
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
                toReturn = "You go into " + allRooms.get(roomID).GetTitle() + "\n";
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
    public static ArrayList<Room> getRooms(ArrayList<Item> itemList, ArrayList<Entity> entityList) throws SQLException {
        var roomList = new ArrayList();
        Connection conn = connect("avelaror");
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
            //.split(" ", 0);
            if (entRef != null) {
                String[] entitySplit = entRef.split("\\.", 0);
                for (String regex : entitySplit) {
                    try {
                        ArrayList<Entity> zF = temp.GetEntities();
                        zF.add(entityList.get(Integer.parseInt(regex)));
                        temp.SetEntities(zF);
                    } catch (NumberFormatException nfe){

                    }
                }
            }

            if (itemRef != null) {
                String[] itemSplit = itemRef.split("\\.", 0);
                var zF = temp.GetItems();
                for (String regex : itemSplit) {

                    try {
                        zF.add(itemList.get(Integer.parseInt(regex)));

                    } catch (NumberFormatException nfe){

                    }

                }
                temp.SetItems(zF);
            }

            if (roomRef != null) {
                temp.SetRooms(Arrays.stream(roomRef.split("\\.")).mapToInt(Integer::parseInt).toArray());
                //System.out.println(Arrays.toString(temp.GetRooms()));
                temp.SetRoomCommands(comRef.split("\\.", 0));
            }


            roomList.add(temp);
        }
        return roomList;
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
            temp.setBecomeHostile(results.getInt("hostile"));
            ents.add(temp);
        }
        return ents;
    }
}
