package main;

import GUI.App;

import java.util.ArrayList;

import database.*;
import modules.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("Init app");

        ArrayList<ParkingLot> parkingLotList = ParkingLotDB.getInstance().getAll();
        for(ParkingLot obj : parkingLotList) {
            System.out.println(obj);
        }

        ParkingLot parkingLot = ParkingLotDB.getInstance().getOne(2);
        System.out.println(parkingLot.isEmpty());
        System.out.println(parkingLot);

    }
}
