package com.company;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class InitializeDatabase {
    static Scanner stdin = new Scanner(System.in);
    static String dburl = "jdbc:sqlite:itemlist.db";
    static Connection conn;

    public static void main(String[] args) {
        connect();
        try {
            var items = getItems();
            for (Item i : items) {
                System.out.println(i.name);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static ArrayList<Item> getItems() throws SQLException {
        System.out.println("Step 1...");
        var items = new ArrayList<Item>();
        System.out.println("Step 2...");
        var statement = conn.createStatement();
        System.out.println("Step 3...");
        var results = statement.executeQuery("SELECT * FROM items");
        System.out.println("Step 4...");
        while (results.next()) {
            items.add(new Item(
                    results.getString("name"),
                    results.getString("flavor_text"),
                    results.getInt("effect"),
                    results.getInt("effect_rating"),
                    results.getInt("strength_req"),
                    results.getFloat("strength_scale"),
                    results.getInt("dexterity_req"),
                    results.getFloat("dexterity_scale"),
                    results.getInt("power_req"),
                    results.getFloat("power_scale"),
                    results.getInt("will_req"),
                    results.getFloat("will_scale"),
                    results.getFloat("weight"),
                    results.getInt("value")
            ));
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