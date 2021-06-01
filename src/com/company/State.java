package com.company;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class State {
    Entity player;
    ArrayList<Room> allRooms;
    ArrayList<Item> _items;
    int roomID;
    String database;
    public State(String db) {
        database = db;
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
            items = items + (i+1) + ": " + player.inventory.get(i).getName() +
                    "\n==>Effect Rating: " + player.inventory.get(i).getEffectRating() +
                    "\n==>Strength Scaling/Requirement" +
                    "" +
                    "" +
                    "";
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
        if (itemEffect > 0) {
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
    String checkForEmptyRoom() throws SQLException {
        Room cur = allRooms.get(roomID);
        if (cur.getEntitiesRef().length() > 0 || cur.getItemsRef().length() > 0) {
            return "You must be in an empty room to save!";
        }
        savePlayer();
        return "Saving progress...";
    }
    public String ReceiveInput(String input) throws SQLException {
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
        if (args[0].equals("save")) {
            toReturn = checkForEmptyRoom();
        }
        if (args[0].equals("exit")) {
            toReturn = "Are you sure? You will lose unsaved progress! (Type 'exit' again)";
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
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
        return conn;
    }
    public ArrayList<Item> getItems() throws SQLException {
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
        conn.close();
        this._items = items;
        return items;
    }
    public ArrayList<Room> getRooms(ArrayList<Item> itemList, ArrayList<Entity> entityList) throws SQLException {
        var roomList = new ArrayList();
        Connection conn = connect(database);
        var statement = conn.createStatement();
        var results = statement.executeQuery("SELECT * FROM rooms");
        while (results.next()) {
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
                var zF = new ArrayList<Entity>();
                for (String regex : entitySplit) {
                    if (!regex.equals("")) {
                        zF.add(entityList.get(Integer.parseInt(regex)));
                    }

                }
                temp.SetEntities(zF);
            }

            if (itemRef != null) {
                String[] itemSplit = itemRef.split("\\.", 0);
                var zF = new ArrayList<Item>();
                for (String regex : itemSplit) {
                    if (!regex.equals("")) {
                        zF.add(itemList.get(Integer.parseInt(regex)));
                    }

                }
                temp.SetItems(zF);
            }

            if (roomRef != null) {
                temp.SetRooms(Arrays.stream(roomRef.split("\\.")).mapToInt(Integer::parseInt).toArray());
                temp.SetRoomCommands(comRef.split("\\.", 0));
            }


            roomList.add(temp);
        }
        conn.close();
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
            temp.setVitality(results.getInt("vitality"));
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
            temp.setBecomeHostile(results.getInt("hostile"));
            ents.add(temp);
        }
        conn.close();
        return ents;
    }
    public void setPlayer(String isLoading) throws SQLException {
        Connection conn = connect(database);
        player = new Entity();
        var statement = conn.createStatement();
        if (isLoading.equals("new")) {
            statement.execute("CREATE TABLE IF NOT EXISTS player (" +
                    "name TEXT DEFAULT \"player\"," +
                    "health INTEGER DEFAULT 50," +
                    "vitality INTEGER DEFAULT 5," +
                    "strength INTEGER DEFAULT 5," +
                    "dexterity INTEGER DEFAULT 5," +
                    "power INTEGER DEFAULT 5," +
                    "will INTEGER DEFAULT 5," +
                    "agility INTEGER DEFAULT 5," +
                    "location INTEGER," +
                    "inventory TEXT" +
                    ");");
        }
        else {
            var regQuery = conn.createStatement();
            var results = regQuery.executeQuery("SELECT * FROM player;");

            boolean zeta = false;
            while (results.next()) {
                player.setName(results.getString("name"));

                player.setVitality(results.getInt("vitality"));
                player.SetHP(results.getInt("health"));
                player.setStrength(results.getInt("strength"));
                player.setDexterity(results.getInt("dexterity"));
                player.setPower(results.getInt("power"));
                player.setWill(results.getInt("will"));
                player.setAgility(results.getInt("agility"));
                roomID = results.getInt("location");
                String inventoryRef = results.getString("inventory");
                ArrayList<Item> tempInv = new ArrayList<>();
                ArrayList<Integer> zetaInv = new ArrayList<>();
                String[] betaInv = inventoryRef.split("\\.");
                for (String b : betaInv) {
                    if (!b.equals("")) {
                        zetaInv.add(Integer.parseInt(b));
                    }
                }
                for (int z : zetaInv) {
                    tempInv.add(this._items.get(z));
                }
                player.setInventory(tempInv);
            }
        }

        if (player.getHp() == 0) {
            statement.execute("DROP TABLE player;");
            statement.execute("CREATE TABLE player (" +
                    "name TEXT DEFAULT \"player\"," +
                    "health INTEGER DEFAULT 50," +
                    "vitality INTEGER DEFAULT 5," +
                    "strength INTEGER DEFAULT 5," +
                    "dexterity INTEGER DEFAULT 5," +
                    "power INTEGER DEFAULT 5," +
                    "will INTEGER DEFAULT 5," +
                    "agility INTEGER DEFAULT 5," +
                    "location INTEGER," +
                    "inventory TEXT" +
                    ");");
            statement.execute("INSERT INTO player VALUES (" +
                    "\"player\"," +
                    "50," +
                    "5," +
                    "5," +
                    "5," +
                    "5," +
                    "5," +
                    "5," +
                    "0," +
                    "\"\"" +
                    ");"
            );
            player.setName("player");

            player.setVitality(5);
            player.SetHP(50);
            player.setStrength(5);
            player.setDexterity(5);
            player.setPower(5);
            player.setWill(5);
            player.setAgility(5);
            roomID = 0;
        }
        conn.close();
    }
    public void savePlayer() throws SQLException {
        Connection conn = connect(database);
        var statement = conn.createStatement();
        statement.execute("DROP TABLE player;");
        statement.execute("CREATE TABLE player (" +
                "name TEXT DEFAULT \"player\"," +
                "health INTEGER," +
                "vitality INTEGER," +
                "strength INTEGER," +
                "dexterity INTEGER," +
                "power INTEGER," +
                "will INTEGER," +
                "agility INTEGER," +
                "location INTEGER," +
                "inventory TEXT" +
                ");");
        String inventoryText = "";
        for (Item item : player.getInventory()) {
            for (int i = 0; i < this._items.size(); i++) {
                if (this._items.get(i).getName().equals(item.getName())) {
                    inventoryText += "." + i;
                    break;
                }
            }
        }
        statement.execute("INSERT INTO player VALUES (" +
                "\"" + player.getName() + "\"," +
                player.getHp() + "," +
                player.getVitality() + "," +
                player.getStrength() + "," +
                player.getDexterity() + "," +
                player.getPower() + "," +
                player.getWill() + "," +
                player.getAgility() + "," +
                roomID + "," +
                "\"" + inventoryText + "\"" +
                ");"
        );
        conn.close();
    }
}
