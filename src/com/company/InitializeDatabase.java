package com.company;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class InitializeDatabase {
    static Scanner stdin = new Scanner(System.in);
    static String dburl = "jdbc:sqlite:database.db";
    static Connection conn;

    public static void main(String[] args) throws SQLException {
        connect();
        //            var people = findAllPeople();
//            for (Person person : people) {
//                System.out.println(person.name);
//            }
    }

//    private static ArrayList<Person> findAllPeople() throws SQLException {
//        var people = new ArrayList<Person>();
//        var statement = conn.createStatement();
//        var results = statement.executeQuery("SELECT name FROM people");
//        while (results.next()) {
//            people.add(new Person(results.getString("name")));
//        }
//        return people;
//    }

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