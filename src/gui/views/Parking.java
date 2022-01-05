package gui.views;

import gui.GUIManager;
import gui.components.*;
import database.controllers.ParkingSlotController;
import models.ParkingSlot;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Parking extends GUIManager implements ActionListener {

    private Slots slotsComponent;

    private JPanel _panel;
    private JToolBar _toolBar;
    private JButton _button;

    public JPanel getView() { return _panel; }

    public Parking() {
        slotsComponent = new Slots(new CustomEvent());
        renderPanel();
    }

    public void renderPanel() {
        _panel = new JPanel();
        _panel.setLayout(new BoxLayout(_panel, BoxLayout.PAGE_AXIS));

        /* Toolbar */
        _toolBar = new JToolBar();
        _toolBar.setAlignmentX(Component.LEFT_ALIGNMENT);
        _toolBar.setFloatable(false);
        _panel.add(_toolBar);

        /* Button */
        _button = new JButton("Zaparkuj pojazd");
        _button.setFocusable(false);
        _button.addActionListener(this);
        _button.setActionCommand("parkVehicle");
        _toolBar.add(_button);

        /* Button */
        _button = new JButton("Utwórz nowe miejsce parkingowe");
        _button.setFocusable(false);
        _button.addActionListener(this);
        _button.setActionCommand("createSlot");
        _toolBar.add(_button);

        /* Mount Slot Component */
        _panel.add(slotsComponent.getComponentScrollable());
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

            new SlotDetails(new CustomEvent(), "Miejsce parkingowe nr " + slot.getSlotNumber(), slot);
        }
        else if (action.equals("createSlot")) {
            new SlotDetails(new CustomEvent(), "Kreator miejsca parkingowego");
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
            _panel.remove(slotsComponent.getComponentScrollable());
            System.out.println("CustomEvent::reloadComponent");
            slotsComponent = new Slots(new CustomEvent());
            _panel.add(slotsComponent.getComponentScrollable());
        }
    }

}
