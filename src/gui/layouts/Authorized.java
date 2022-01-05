package gui.layouts;

import gui.GUIManager;
import gui.views.*;

import java.awt.*;
import javax.swing.*;

public class Authorized extends GUIManager {

    private JPanel _panel;
    private JTabbedPane _tabbedPane;

    private Parking _parkingTab;
    private Vehicles _vehiclesTab;
    private Customers _customersTab;

    public JPanel getLayout() { return _panel; }

    public Authorized(int id) {
        getEnv().setParkingId(id);
        renderPanel();
    }

    /**
     * Render and mount panel for parking layout
     *
     */
    public void renderPanel() {
        _panel = new JPanel(new BorderLayout());
        _parkingTab = new Parking();
        _vehiclesTab = new Vehicles();
        _customersTab = new Customers();

        /* Tabbed Pane */
        _tabbedPane = new JTabbedPane();
        _tabbedPane.addTab("Parking", _parkingTab.getView());
        _tabbedPane.addTab("Pojazdy", _vehiclesTab.getView());
        _tabbedPane.addTab("Klienci", _customersTab.getView());
        _panel.add(_tabbedPane);
    }

}
