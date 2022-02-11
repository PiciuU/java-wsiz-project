package main;

import gui.App;

// import database.DBMigration;

public class Main {

    public static void main(String[] args) {
        /** Uncomment the line below if you would like to delete current database */
        // DBMigration.init("rollback");
        /** Uncomment the line below if you would like to create new database */
        // DBMigration.init("migrate");

        App.setInstance("Car Parking System", 700, 450);

        /** Uncomment the line below if you want to set application environment to local (Debug logs) */
        App.getInstance().getEnv().setEnvType("local");

        /** Uncomment the line below if you want to set application environment to production (No debug logs) */
        // App.getInstance().getEnv().setEnvType("production");
    }
}
