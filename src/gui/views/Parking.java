package gui.views;

import gui.DreamFX.*;
import gui.GUIManager;

import gui.components.ParkingSlotDetails;
import gui.components.ParkingSlots;

import database.controllers.ParkingSlotController;

import models.ParkingSlot;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Parking extends GUIManager implements ActionListener {

    private ParkingSlots parkingSlotsComponent;

    private DPanel _panel;
    private DToolBar _toolBar;
    private DButton _button;

    /**
     * Create view for parking
     *
     */
    public Parking() {
        parkingSlotsComponent = new ParkingSlots(new CustomEvent());
        renderPanel();
    }

    /**
     * Get view of parking
     *
     * @return DPanel
     */
    public DPanel getView() { return _panel; }

    /**
     * Render and mount panel for parking view
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
        _button = new DButton("secondary", "Zaparkuj pojazd");
        _button.setFocusable(false);
        _button.addActionListener(this);
        _button.setActionCommand("parkVehicle");
        _toolBar.add(_button);
        _toolBar.addSeparator(new Dimension(5, 0));

        /* Button */
        _button = new DButton("secondary", "Utwórz nowe miejsce parkingowe");
        _button.setFocusable(false);
        _button.addActionListener(this);
        _button.setActionCommand("createSlot");
        _toolBar.add(_button);

        /* Mount Slot Component */
        _panel.add(parkingSlotsComponent.getComponentScrollable());
    }

    /**
     * Handle the requested action
     *
     * @param e ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();

        if (action.equals("parkVehicle")) {
            ParkingSlot slot = ParkingSlotController.getInstance().getCustomOne("SELECT parking_slot.*, 0 AS custom_field FROM parking_slot LEFT JOIN parking_slot_reservation ON parking_slot.id = parking_slot_reservation.parking_slot_id WHERE parking_slot.parking_id = " + getEnv().getParkingId() + " AND parking_slot_reservation.id IS NULL LIMIT 1");

            new ParkingSlotDetails(new CustomEvent(), "Miejsce parkingowe nr " + slot.getSlotNumber(), slot);
        }
        else if (action.equals("createSlot")) {
            new ParkingSlotDetails(new CustomEvent(), "Kreator miejsca parkingowego");
        }
    }

    /**
     * Custom events handled by GUIManager EventHandler
     *
     */
    class CustomEvent extends EventHandler {
        @Override
        public void disposeFrame(JFrame frame) {
            if (getEnv().getEnvType() == "local") System.out.println("[Parking]CustomEvent::disposeFrame");
            frame.dispose();
        }

        @Override
        public void reloadContent() {
            if (getEnv().getEnvType() == "local") System.out.println("[Parking]CustomEvent::reloadComponent");
            _panel.remove(parkingSlotsComponent.getComponentScrollable());
            parkingSlotsComponent = new ParkingSlots(new CustomEvent());
            _panel.add(parkingSlotsComponent.getComponentScrollable());
        }
    }

}
