package GUI.layouts;

import javax.swing.*;

import GUI.views.*;

import java.awt.*;

public class Authorized {

    public static int parkingId;

    private JPanel layout;
    private JTabbedPane tabs;

    private Parking parkingLotTab;
    private Vehicles vehiclesTab;

    public Authorized(int id) {
        this.parkingId = id;
        init();
    }

    public JPanel getLayout() {
        return layout;
    }

    public void init() {
        layout = new JPanel(new BorderLayout());
        initTabs();
        layout.add(tabs);
    }

    public void initTabs() {
        tabs = new JTabbedPane();

        parkingLotTab = new Parking();
        vehiclesTab = new Vehicles();

        tabs.addTab("Parking", parkingLotTab.getView());
        tabs.addTab("Pojazdy", vehiclesTab.getView());
    }

}
