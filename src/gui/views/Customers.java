package gui.views;

import gui.GUIManager;

import gui.components.CustomerDetails;
import gui.components.CustomerList;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Customers extends GUIManager implements ActionListener {

    private CustomerList customerListComponent;

    private JPanel _panel;
    private JToolBar _toolBar;
    private JButton _button;

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
     * @return JPanel
     */
    public JPanel getView() {
        return _panel;
    }


    /**
     * Render and mount panel for customers view
     *
     */
    private void renderPanel() {
        _panel = new JPanel();
        _panel.setLayout(new BoxLayout(_panel, BoxLayout.PAGE_AXIS));

        /* Toolbar */
        _toolBar = new JToolBar();
        _toolBar.setAlignmentX(Component.LEFT_ALIGNMENT);
        _toolBar.setFloatable(false);
        _panel.add(_toolBar);

        /* Button */
        _button = new JButton("Dodaj klienta");
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
