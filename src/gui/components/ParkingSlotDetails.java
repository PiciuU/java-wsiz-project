package gui.components;

import gui.DreamFX.*;
import gui.GUIManager;

import database.controllers.ParkingSlotReservationController;
import database.controllers.ParkingSlotController;
import database.controllers.VehicleController;

import models.ParkingSlot;
import models.ParkingSlotReservation;
import models.Vehicle;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class ParkingSlotDetails extends GUIManager implements ActionListener {

    private EventHandler customEvent;

    protected ParkingSlotReservation parkingSlotReservation = new ParkingSlotReservation();

    protected ParkingSlot slot;
    protected Boolean isOccupied;
    protected Vehicle chosenVehicle;

    private GridLayout gridLayout;

    private DFrame _frame;
    private DLabel _label;
    private DTextField _textField;
    private DButton _button;
    private DLabel _error;

    /**
     * Create component for undefined parking slot
     *
     */
    public ParkingSlotDetails(EventHandler customEvent, String modalTitle) {
        this.customEvent = customEvent;
        renderFrame(modalTitle);
        renderCreateModal();
    }

    /**
     * Create component for defined parking slot details
     *
     */
    public ParkingSlotDetails(EventHandler customEvent, String modalTitle, ParkingSlot slot) {
        this.customEvent = customEvent;
        this.slot = slot;
        this.isOccupied = (Integer.parseInt(slot.getCustomField()) != 0);
        this.chosenVehicle = new Vehicle();
        renderFrame(modalTitle);

        if(this.isOccupied) renderDeleteModal();
        else renderEditModal();
    }

    /**
     * Render and mount new frame to application
     *
     * @param title name of frame
     */
    public void renderFrame(String title) {
        gridLayout = new GridLayout();
        _frame = new DFrame();
        _frame.setTitle(title);
        _frame.setSize(450, 250);
        _frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                customEvent.disposeFrame(_frame);
            }
        });
        _frame.setLayout(gridLayout);
        _frame.setResizable(false);
        _frame.setVisible(true);
    }

    /**
     * Render and mount to frame content for creating new parking slot
     *
     */
    public void renderCreateModal() {
        /* Label */
        _label = new DLabel("Utwórz miejsce parkingowe: ", SwingConstants.CENTER);
        gridLayout.setConstraints(GridBagConstraints.HORIZONTAL, 0, 0, 2);
        _frame.add(_label, gridLayout.getConstraints());

        /* Label */
        _label = new DLabel("Numer miejsca parkingowego: ", SwingConstants.LEFT);
        gridLayout.setConstraints(0, 1, 1, new Insets(20,0,0,0));
        _frame.add(_label, gridLayout.getConstraints());

        /* TextField */
        _textField = new DTextField();
        _textField.setPreferredSize(new Dimension(100, 25));
        gridLayout.setConstraints(1, 1, new Insets(20, 10, 0, 0));
        _frame.add(_textField, gridLayout.getConstraints());

        /* Button */
        _button = new DButton("Dodaj");
        _button.addActionListener(this);
        _button.setActionCommand("addSlot");
        gridLayout.setConstraints(0, 2, 2, new Insets(20,0,0,0));
        _frame.add(_button, gridLayout.getConstraints());
    }

    /**
     * Render and mount to frame content for creating new parking slot reservation
     *
     */
    public void renderEditModal() {
        /* Label */
        _label = new DLabel("Miejsce parkingowe nr " + slot.getSlotNumber() + ": ", SwingConstants.CENTER);
        gridLayout.setConstraints(GridBagConstraints.HORIZONTAL, 0, 0, 3);
        _frame.add(_label, gridLayout.getConstraints());

        /* Label */
        _label = new DLabel("Pojazd do zaparkowania: ", SwingConstants.LEFT);
        gridLayout.setConstraints(0, 1, 1, new Insets(20,0,0,0));
        _frame.add(_label, gridLayout.getConstraints());

        /* TextField */
        _textField = new DTextField();
        _textField.setPreferredSize(new Dimension(150, 25));
        _textField.setEditable(false);
        gridLayout.setConstraints(1, 1, new Insets(20, 10, 0, 0));
        _frame.add(_textField, gridLayout.getConstraints());

        /* Button */
        _button = new DButton("secondary", "wybierz...");
        _button.addActionListener(this);
        _button.setActionCommand("chooseVehicle");
        gridLayout.setConstraints(2, 1, new Insets(20, 10, 0, 0));
        _frame.add(_button, gridLayout.getConstraints());

        /* Button */
        _button = new DButton("Dodaj");
        _button.addActionListener(this);
        _button.setActionCommand("addSlotReservation");
        gridLayout.setConstraints(0, 2, 3, new Insets(20,0,0,0));
        _frame.add(_button, gridLayout.getConstraints());
    }

    /**
     * Render and mount to frame content for deleting an existing parking slot reservation
     *
     */
    public void renderDeleteModal() {
        parkingSlotReservation = ParkingSlotReservationController.getInstance().getOne(slot.getId());
        Vehicle vehicle = VehicleController.getInstance().getOne(parkingSlotReservation.getVehicleId());

        /* Label */
        _label = new DLabel("Miejsce parkingowe nr " + slot.getSlotNumber() + ": ", SwingConstants.CENTER);
        gridLayout.setConstraints(GridBagConstraints.HORIZONTAL, 0, 0, 3);
        _frame.add(_label, gridLayout.getConstraints());

        /* Label */
        _label = new DLabel("Zaparkowany pojazd: ", SwingConstants.LEFT);
        gridLayout.setConstraints(0, 1, 1, new Insets(20,0,0,0));
        _frame.add(_label, gridLayout.getConstraints());

        /* TextField */
        _textField = new DTextField();
        _textField.setPreferredSize(new Dimension(150, 25));
        _textField.setEditable(false);
        _textField.setText(vehicle.getProducer() + " " + vehicle.getModel() + " - " + vehicle.getNumberPlate());
        gridLayout.setConstraints(1, 1, new Insets(20, 10, 0, 0));
        _frame.add(_textField, gridLayout.getConstraints());

        /* Button */
        _button = new DButton("Wyparkuj");
        _button.addActionListener(this);
        _button.setActionCommand("deleteSlotReservation");
        gridLayout.setConstraints(0, 2, 3, new Insets(20,0,0,0));
        _frame.add(_button, gridLayout.getConstraints());
    }

    /**
     * Handle the requested action
     *
     * @param e ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();

        if (action.equals("addSlot")) {
            if (_textField.getText().isEmpty()) {
                throwVisibleError("Numer miejsca parkingowego nie może być pusty!");
                return;
            }
            ParkingSlotController.getInstance().insertRecord(getEnv().getParkingId(), _textField.getText());
        }
        else if (action.equals("chooseVehicle")) {
            new VehicleListModal(new CustomEvent(), true);
            return;
        }
        else if (action.equals("addSlotReservation")) {
            if (chosenVehicle.isEmpty() || _textField.getText().isEmpty() ) {
                throwVisibleError("Aby przejść dalej należy wybrać pojazd!");
                return;
            }
            ParkingSlotReservationController.getInstance().insertRecord(chosenVehicle.getId(), slot.getId(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneOffset.UTC).format(Instant.now()));
        }
        else if (action.equals("deleteSlotReservation")) {
            if(parkingSlotReservation.isEmpty()) {
                throwVisibleError("Podane miejsce parkingowe nie jest zajęte!");
                return;
            }
            ParkingSlotReservationController.getInstance().deleteRecord(parkingSlotReservation.getId());
        }

        customEvent.disposeFrame(_frame);
        customEvent.reloadContent();
    }

    /**
     * Throw visible validation error for GUI
     *
     * @param text text of validation error
     */
    public void throwVisibleError(String text) {
        if (_frame.isAncestorOf(_error)) _frame.remove(_error);
        _error = new DLabel();
        _error.setText(Prettier.renderText(text, "text-align:center;"));
        _error.setForeground(Color.red);
        _error.setHorizontalAlignment(SwingConstants.CENTER);
        gridLayout.setConstraints(0, 3, new Insets(5, 0, 0, 0));
        _frame.add(_error, gridLayout.getConstraints());
        _frame.validate();
    }

    /**
     * Custom events handled by GUIManager EventHandler
     *
     */
    class CustomEvent extends EventHandler {
        @Override
        public void handleSelect(Object object) {
            if (getEnv().getEnvType() == "local") System.out.println("[ParkingSlotDetails]CustomEvent::handleSelect");
            chosenVehicle = (Vehicle) object;
            _textField.setText(chosenVehicle.getProducer() + " " + chosenVehicle.getModel() + " - " + chosenVehicle.getNumberPlate());
        }
    }
}
