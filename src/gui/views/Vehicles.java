package gui.views;

import gui.DreamFX.*;
import gui.GUIManager;

import gui.components.VehicleList;
import gui.components.VehicleDetails;
import gui.components.VehicleSearchModal;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Vehicles extends GUIManager implements ActionListener {

    private VehicleList vehicleListComponent;

    private DPanel _panel;
    private DToolBar _toolBar;
    private DButton _button;

    /**
     * Create view for vehicles
     *
     */
    public Vehicles() {
        vehicleListComponent = new VehicleList(new CustomEvent());
        renderPanel();
    }

    /**
     * Get view of vehicles
     *
     * @return DPanel
     */
    public DPanel getView() {
        return _panel;
    }

    /**
     * Render and mount panel for vehicles view
     *
     */
    public void renderPanel() {
        _panel = new DPanel();
        _panel.setLayout(new BoxLayout(_panel, BoxLayout.PAGE_AXIS));

        /* Toolbar */
        _toolBar = new DToolBar();
        _toolBar.setAlignmentX(Component.LEFT_ALIGNMENT);
        _toolBar.setFloatable(false);
        _panel.add(_toolBar);

        /* Button */
        _button = new DButton("secondary", "Dodaj pojazd");
        _button.setFocusable(false);
        _button.addActionListener(this);
        _button.setActionCommand("addVehicle");
        _toolBar.add(_button);
        _toolBar.addSeparator(new Dimension(5, 0));

        /* Button */
        _button = new DButton("secondary", "Wyszukiwanie pojazdów");
        _button.setFocusable(false);
        _button.addActionListener(this);
        _button.setActionCommand("findVehicle");
        _toolBar.add(_button);

        /* Mount VehicleList Component */
        _panel.add(vehicleListComponent.getComponent());
    }

    /**
     * Handle the requested action
     *
     * @param e ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        if (action == "addVehicle") {
            new VehicleDetails(new CustomEvent(), "Dodaj nowy pojazd");
        }
        else if (action == "findVehicle") {
            new VehicleSearchModal(new CustomEvent(),"Wyszukiwarka pojazdów");
        }
    }

    /**
     * Custom events handled by GUIManager EventHandler
     *
     */
    class CustomEvent extends EventHandler {
        @Override
        public void disposeFrame(JFrame frame) {
            if (getEnv().getEnvType() == "local") System.out.println("[Vehicles]CustomEvent::disposeFrame");
            frame.dispose();
        }

        @Override
        public void reloadContent() {
            if (getEnv().getEnvType() == "local") System.out.println("[Vehicles]CustomEvent::reloadComponent");
            _panel.remove(vehicleListComponent.getComponent());
            vehicleListComponent = new VehicleList(new Vehicles.CustomEvent(), vehicleListComponent.getCurrentFilterValue());
            _panel.add(vehicleListComponent.getComponent());
        }
    }
}
