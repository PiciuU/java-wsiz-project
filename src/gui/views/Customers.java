package gui.views;

import gui.DreamFX.*;
import gui.GUIManager;

import gui.components.CustomerDetails;
import gui.components.CustomerList;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Customers extends GUIManager implements ActionListener {

    private CustomerList customerListComponent;

    private DPanel _panel;
    private DToolBar _toolBar;
    private DButton _button;

    /**
     * Create view for customers
     *
     */
    public Customers() {
        customerListComponent = new CustomerList(new CustomEvent());
        renderPanel();
    }

    /**
     * Get view of customers
     *
     * @return DPanel
     */
    public DPanel getView() {
        return _panel;
    }


    /**
     * Render and mount panel for customers view
     *
     */
    private void renderPanel() {
        _panel = new DPanel();
        _panel.setLayout(new BoxLayout(_panel, BoxLayout.PAGE_AXIS));

        /* Toolbar */
        _toolBar = new DToolBar();
        _toolBar.setAlignmentX(Component.LEFT_ALIGNMENT);
        _toolBar.setFloatable(false);
        _panel.add(_toolBar);

        /* Button */
        _button = new DButton("secondary", "Dodaj klienta");
        _button.setFocusable(false);
        _button.addActionListener(this);
        _button.setActionCommand("addVehicle");
        _toolBar.add(_button);

        /* Mount CustomerList Component */
        _panel.add(customerListComponent.getComponent());
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
            new CustomerDetails(new CustomEvent(), "Dodaj nowego klienta");
        }
    }

    /**
     * Custom events handled by GUIManager EventHandler
     *
     */
    class CustomEvent extends EventHandler {
        @Override
        public void disposeFrame(JFrame frame) {
            if (getEnv().getEnvType() == "local") System.out.println("[Customers]CustomEvent::disposeFrame");
            frame.dispose();
        }

        @Override
        public void reloadContent() {
            if (getEnv().getEnvType() == "local") System.out.println("[Customers]CustomEvent::reloadContent");
            _panel.remove(customerListComponent.getComponent());
            customerListComponent = new CustomerList(new Customers.CustomEvent());
            _panel.add(customerListComponent.getComponent());
        }
    }
}
