package com.company;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class InitializeDatabase {
    public static ArrayList<Item> itemList = new ArrayList<Item>();
    static Scanner stdin = new Scanner(System.in);
    static String dburl = "jdbc:sqlite:itemlist.db";
    static Connection conn;
    public static void setEntities() throws SQLException {
        String[] names = {
            "Rat", "Wolf", "Draugr"
        };
        int[] hps = {
                25, 50, 115
        };
        int[] vits = {
                2, 3, 5
        };
        int[] strs = {
                1, 1, 3
        };
        int[] dexs = {
                1, 1, 3
        };
        int[] pows = {
                1, 1, 3
        };
        int[] wils = {
                1, 1, 3
        };
        int[] agls = {
                1, 1, 2
        };
        String[] defaults = {
                "*chattering*", "*growling*", "*groans*"
        };
        String[] agros = {
                "*squealing*", "*barks and growls*", "*inhuman growls*"
        };
        int[] pas = {
                1, 1, 2
        };
        int[] pvs = {
                5, 10, 12
        };
        //0, no. 1, yes.
        int[] aggros = {
                1, 1, 1
        };
        var statement = conn.createStatement();
        statement.execute("DROP TABLE IF EXISTS entities;");
        System.out.println("STEP 2");
        var st = conn.createStatement();
        st.execute("CREATE TABLE entities (" +
                "name TEXT," +
                "hp INTEGER," +
                "vitality INTEGER," +
                "strength INTEGER," +
                "dexterity INTEGER," +
                "power INTEGER," +
                "will INTEGER," +
                "agility INTEGER," +
                "default_line TEXT," +
                "agro_line TEXT," +
                "primary_attack INTEGER," +
                "primary_value INTEGER," +
                "hostile INTEGER" + //0, no. 1, yes.
                ");");
        int i;

        System.out.println("STEP 3");
        for (i = 0; i < names.length; i++) {
            String query = " INSERT INTO entities"
                    + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, names[i]);
            preparedStmt.setInt(2, hps[i]);
            preparedStmt.setInt(3, vits[i]);
            preparedStmt.setInt(4, strs[i]);
            preparedStmt.setInt(5, dexs[i]);
            preparedStmt.setInt(6, pows[i]);
            preparedStmt.setInt(7, wils[i]);
            preparedStmt.setInt(8, agls[i]);
            preparedStmt.setString(9, defaults[i]);
            preparedStmt.setString(10, agros[i]);
            preparedStmt.setInt(11, pas[i]);
            preparedStmt.setInt(12, pvs[i]);
            preparedStmt.setInt(13, aggros[i]);
            preparedStmt.execute();
        }
    }
    public static void setItems() throws SQLException {
        String[] names = {
                "Short_Sword",
                "Gold_Sack",
                "Large_Gold_Sack",
                "Arming_Sword",
                "Long_Sword",
                "War_Axe",
                "Mace",
                "Pike",
                "Fireball"
        };
        String[] flavorTexts = {
                "A standard self-defense weapon",
                "A sack of 20 gold pieces",
                "A sack of 50 gold pieces",
                "Standard weapon for soldiers",
                "A greatsword that can be used with either one or both hands",
                "A small axe used in combat",
                "A basic bludgeoning weapon",
                "A very long spear with used commonly in battle",
                "A basic spell that hurls a ball of Fire at a target"
        };
        int[] effects = {
                1, 0, 0, 1, 1, 2, 3, 4, 5
        };
        int[] effectRatings = {
                20, 0, 0, 25, 30, 25, 25, 35, 30
        };
        int[] strengthReqs = {
                5, 0, 0, 10, 15, 10, 10, 10, 0
        };
        float[] strengthScales = {
                1.0f, 0.0f, 0.0f, 2.0f, 2.0f, 2.0f, 3.0f, 4.0f, 0.0f
        };
        int[] dexterityReqs = {
                10, 0, 0, 5, 7, 5, 0, 5, 0
        };
        float[] dexterityScales = {
                2.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 2.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f
        };
        int[] powerReqs = {
                0, 0, 0, 0, 0, 0, 0, 0, 5
        };
        float[] powerScales = {
                0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 3.0f
        };
        int[] willReqs = {
                0, 0, 0, 0, 0, 0, 0, 0, 5
        };
        float[] willScales = {
                0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f
        };
        float[] weights = {
                2.0f, 0.0f, 0.0f, 3.0f, 4.0f, 3.0f, 4.0f, 6.0f, 0.0f
        };
        int[] values = {
                30, 20, 50, 45, 55, 45, 45, 60, 80
        };
        System.out.println("STEP 1");
        var statement = conn.createStatement();
        statement.execute("DROP TABLE IF EXISTS items;");
        System.out.println("STEP 2");
        var st = conn.createStatement();
        st.execute("CREATE TABLE items (" +
                "name TEXT," +
                "flavor_text TEXT," +
                "effect INTEGER," +
                "effect_rating INTEGER," +
                "strength_req INTEGER," +
                "strength_scale REAL," +
                "dexterity_req INTEGER," +
                "dexterity_scale REAL," +
                "power_req INTEGER," +
                "power_scale REAL," +
                "will_req INTEGER," +
                "will_scale REAL," +
                "weight REAL," +
                "value INTEGER" +
                ");");
        int i;

        System.out.println("STEP 3");
        for (i = 0; i < names.length; i++) {
            String query = " INSERT INTO items"
                    + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, names[i]);
            preparedStmt.setString(2, flavorTexts[i]);
            preparedStmt.setInt(3, (int) effects[i]);
            preparedStmt.setInt(4, (int) effectRatings[i]);
            preparedStmt.setInt(5, (int) strengthReqs[i]);
            preparedStmt.setDouble(6, (double) strengthScales[i]);
            preparedStmt.setInt(7, (int) dexterityReqs[i]);
            preparedStmt.setDouble(8, (double)dexterityScales[i]);
            preparedStmt.setInt(9, (int) powerReqs[i]);
            preparedStmt.setDouble(10, (double)powerScales[i]);
            preparedStmt.setInt(11, (int) willReqs[i]);
            preparedStmt.setDouble(12, (double)willScales[i]);
            preparedStmt.setDouble(13, (double)weights[i]);
            preparedStmt.setInt(14, (int) values[i]);
            preparedStmt.execute();
        }
    }
    static ArrayList<Entity> getEntities() throws SQLException {
        ArrayList<Entity> ents = new ArrayList<Entity>();
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
    public static void main(String[] args) {
        connect();
        try {
            System.out.println("Initialize?");
            String in = stdin.nextLine();
            if (in.equals("yes")) {
                setItems();
                setEntities();
            }
            itemList = getItems();
            var entitylist = getEntities();
            for (Item i : itemList) {
                System.out.println(i.getName());
            }
            for (Entity i: entitylist) {
                System.out.println(i.getName());
            }

            conn.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static ArrayList<Item> getItems() throws SQLException {
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

    private static void connect() {
        try {
            conn = DriverManager.getConnection(dburl);
            System.out.println("Connection to SQLite has been established.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }
}