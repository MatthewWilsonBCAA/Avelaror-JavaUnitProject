package com.company;

import java.sql.Connection;
import java.sql.DriverManager;
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
            1, 0, 0
    };
    static int[] dexterityReqs = {
            10, 0, 0
    };
    static float[] dexterityScales = {
            2, 0, 0
    };
    static int[] powerReqs = {
            0, 0, 0
    };
    static float[] powerScales = {
            0, 0, 0
    };
    static int[] willReqs = {
            0, 0, 0
    };
    static float[] willScales = {
            0, 0, 0
    };
    static float[] weights = {
            2, 0, 0
    };
    static int[] value = {
            30, 20, 50
    };
    public static void setItems() throws SQLException {
        var statement = conn.createStatement();
        statement.execute("DROP items");
        int i;
        for (i = 0; i < names.length; i++) {
            statement.execute("" + "");
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
            else {
                var items = getItems();
                for (Item i : items) {
                    System.out.println(i.name);
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static ArrayList<Item> getItems() throws SQLException {
        var items = new ArrayList<Item>();
        var statement = conn.createStatement();
        var results = statement.executeQuery("SELECT * FROM items");
        while (results.next()) {
            results.getString("name");
            results.getString("flavor_text");
            results.getInt("effect");
            results.getInt("effect_rating");
            results.getInt("strength_req");
            results.getFloat("strength_scale");
            results.getInt("dexterity_req");
            results.getFloat("dexterity_scale");
            results.getInt("power_req");
            results.getFloat("power_scale");
            results.getInt("will_req");
            results.getFloat("will_scale");
            results.getFloat("weight");
            results.getInt("value");
            items.add(new Item());
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