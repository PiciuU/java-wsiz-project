package main;

import GUI.App;
import database.DBMigration;

public class Main {

    public static void main(String[] args) {
        // DBMigration.init("rollback");
        App.setInstance("Car Parking System", 700, 450);
    }
}
