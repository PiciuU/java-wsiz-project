package gui.components;

import gui.GUIManager;

import database.controllers.ParkingSlotController;

import models.ParkingSlot;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import java.util.ArrayList;

public class ParkingSlots extends GUIManager implements ActionListener {

    private EventHandler customEvent;

    protected ArrayList<ParkingSlot> parkingSlotsData;

    private JPanel _panel;
    private JScrollPane _scrollPane;
    private JButton _button;
    private ArrayList<JButton> _buttonArray = new ArrayList<JButton>();

    /**
     * Create component for parking slots
     *
     */
    public ParkingSlots(EventHandler customEvent) {
        this.customEvent = customEvent;
        parkingSlotsData = ParkingSlotController.getInstance().getCustom("SELECT parking_slot.*, IIF(parking_slot_reservation.id NOT NULL, 1, 0) AS custom_field FROM parking_slot LEFT JOIN parking_slot_reservation ON parking_slot.id = parking_slot_reservation.parking_slot_id WHERE parking_slot.parking_id = " + getEnv().getParkingId());
        renderPanel();
    }

    /**
     * Get component of parking slots
     *
     * @return JPanel
     */
    public JPanel getComponent() {
        return _panel;
    }

    /**
     * Get scrollable component of parking slots
     *
     * @return JScrollPane
     */
    public JScrollPane getComponentScrollable() {
        return _scrollPane;
    }

    /**
     * Render and mount panel for available parking slots
     *
     */
    public void renderPanel() {
        _panel = new JPanel(new WrapLayout(FlowLayout.CENTER, 10, 10));
        _scrollPane = new JScrollPane(_panel);
        _scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);

        for (ParkingSlot slot : parkingSlotsData) {
            renderButton(slot,"Miejsce parkingowe nr " + slot.getSlotNumber() + "<br><br> " + (Integer.parseInt(slot.getCustomField()) == 1 ? "Zajęte" : "Wolne"), "slot_" + slot.getSlotNumber());
        }

        renderButton(null,"Utwórz nowe miejsce parkingowe", "slot_create");
    }

    /**
     * Render and mount button to _panel
     *
     */
    public void renderButton(ParkingSlot slot, String text, String name) {
        _button = new JButton();
        _button.setText(Prettier.renderText(text, "text-align:center"));
        _button.setName(name);
        _button.addActionListener(this);
        _button.setPreferredSize(new Dimension(150, 100));
        _button.putClientProperty("slot", slot);
        _buttonArray.add(_button);

        _panel.add(_button);
    }

    /**
     * Handle the requested action
     *
     * @param e ActionEvent
     */
    public void actionPerformed(ActionEvent e) {
        ParkingSlot slot = (ParkingSlot) _buttonArray.get(_buttonArray.indexOf(e.getSource())).getClientProperty("slot");

        if (slot == null || slot.isEmpty()) {
            new ParkingSlotDetails(customEvent, "Kreator miejsca parkingowego");
        }
        else {
            new ParkingSlotDetails(customEvent, "Miejsce parkingowe nr " + slot.getSlotNumber(), slot);
        }
    }
}