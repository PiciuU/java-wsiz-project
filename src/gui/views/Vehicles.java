package gui.views;

import gui.GUIManager;
import gui.components.CustomerListModal;
import gui.components.Slots;
import gui.components.VehicleDetails;
import gui.components.VehicleList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Vehicles extends GUIManager implements ActionListener {

    private VehicleList vehicleListComponent;

    private JPanel _panel;
    private JToolBar _toolBar;
    private JButton _button;

    public JPanel getView() {
        return _panel;
    }

    public Vehicles() {
        vehicleListComponent = new VehicleList(new CustomEvent());
        renderPanel();
    }

    private void renderPanel() {
        _panel = new JPanel();
        _panel.setLayout(new BoxLayout(_panel, BoxLayout.PAGE_AXIS));

        /* Toolbar */
        _toolBar = new JToolBar();
        _toolBar.setAlignmentX(Component.LEFT_ALIGNMENT);
        _toolBar.setFloatable(false);
        _panel.add(_toolBar);

        /* Button */
        _button = new JButton("Dodaj pojazd");
        _button.setFocusable(false);
        _button.addActionListener(this);
        _button.setActionCommand("addVehicle");
        _toolBar.add(_button);

        /* Button */
        _button = new JButton("Wyszukiwanie pojazdów");
        _button.setFocusable(false);
        _button.addActionListener(this);
        _button.setActionCommand("createSlot");
        _toolBar.add(_button);

        /* Mount VehicleList Component */
        _panel.add(vehicleListComponent.getComponent());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        if (action == "addVehicle") {
            new VehicleDetails(new CustomEvent(), "Dodaj nowy pojazd");
        }
    }

    /**
     * Custom events handled by GUIManager EventHandler
     *
     */
    class CustomEvent extends EventHandler {
        @Override
        public void disposeFrame(JFrame frame) {
            System.out.println("CustomEvent::disposeFrame");
            frame.dispose();
        }

        @Override
        public void reloadContent() {
            _panel.remove(vehicleListComponent.getComponent());
            System.out.println("CustomEvent::reloadComponent");
            vehicleListComponent = new VehicleList(new Vehicles.CustomEvent(), vehicleListComponent.getCurrentFilterValue());
            _panel.add(vehicleListComponent.getComponent());
        }
    }
}
