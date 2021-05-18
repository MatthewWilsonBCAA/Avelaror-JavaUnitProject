package com.company;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class InitializeDatabase {
    public static ArrayList<Item> itemList = new ArrayList<Item>();
    static Scanner stdin = new Scanner(System.in);
    static String dburl = "jdbc:sqlite:itemlist.db";
    static Connection conn;
    static String[] names = {
            "Short_Sword",
            "Gold_Sack",
            "Large_Gold_Sack"
    };
    static String[] flavorTexts = {
            "A standard self-defense weapon",
            "A sack of 20 gold pieces",
            "A sack of 50 gold pieces"
    };
    static int[] effects = {
            1, 0, 0
    };
    static int[] effectRatings = {
            10, 0, 0
    };
    static int[] strengthReqs = {
            5, 0, 0
    };
    static float[] strengthScales = {
            1.0f, 0.0f, 0.0f
    };
    static int[] dexterityReqs = {
            10, 0, 0
    };
    static float[] dexterityScales = {
            2.0f, 0.0f, 0.0f
    };
    static int[] powerReqs = {
            0, 0, 0
    };
    static float[] powerScales = {
            0.0f, 0.0f, 0.0f
    };
    static int[] willReqs = {
            0, 0, 0
    };
    static float[] willScales = {
            0.0f, 0.0f, 0.0f
    };
    static float[] weights = {
            2.0f, 0.0f, 0.0f
    };
    static int[] values = {
            30, 20, 50
    };
    public static void setItems() throws SQLException {
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
    public static void main(String[] args) {
        connect();
        try {
            System.out.println("Initialize?");
            String in = stdin.nextLine();
            if (in.equals("yes")) {
                setItems();
            }
                itemList = getItems();
                for (Item i : itemList) {
                    System.out.println(i.name);
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