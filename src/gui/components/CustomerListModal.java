package gui.components;

import gui.DreamFX.*;
import gui.GUIManager;

import database.controllers.CustomerController;

import models.Customer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.util.ArrayList;

public class CustomerListModal extends GUIManager implements ActionListener {

    private EventHandler customEvent;

    private ArrayList<Customer> customersData;

    private GridLayout gridLayout;

    private DFrame _frame;
    private DScrollPane _scrollPane;

    private DefaultListModel _listModel;
    private DList _list;
    private DButton _button;
    private DLabel _error;

    /**
     * Create component for customers list modal
     *
     */
    public CustomerListModal(EventHandler customEvent) {
        this.customEvent = customEvent;
        customersData = CustomerController.getInstance().getAll();
        renderFrame();
        renderContent();
    }

    /**
     * Render and mount frame for customers list modal
     *
     */
    public void renderFrame() {
        gridLayout = new GridLayout();
        _frame = new DFrame();
        _frame.setLayout(gridLayout);
        _frame.setTitle("Lista klientów");
        _frame.setSize(400, 400);
        _frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        _frame.setResizable(false);
        _frame.setVisible(true);
    }

    /**
     * Render and mount content for customers list modal
     *
     */
    public void renderContent() {
        /* ListModel and List */
        _listModel = new DefaultListModel();
        _list = new DList(_listModel);

        for(Customer customer : customersData) {
            _listModel.addElement(customer.getFirstname() + " " + customer.getSurname());
        }

        /* ScrollPane */
        _scrollPane = new DScrollPane(_list);
        _scrollPane.setPreferredSize(new Dimension(250, 200));
        gridLayout.setConstraints(GridBagConstraints.HORIZONTAL, 0, 0, 1);
        _frame.add(_scrollPane, gridLayout.getConstraints());

        /* Button */
        _button = new DButton("Wybierz");
        _button.addActionListener(this);
        _button.setActionCommand("chooseCustomer");
        _button.setPreferredSize(new Dimension(250, 50));
        gridLayout.setConstraints(0, 1, new Insets(20, 0, 0, 0));
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

        if (action.equals("chooseCustomer")) {
            if (_list.getSelectedIndex() == -1) {
                throwVisibleError("Aby przejść dalej klient musi być wybrany!");
                return;
            }
            customEvent.handleSelect(customersData.get(_list.getSelectedIndex()));
            _frame.dispose();
        }
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
        gridLayout.setConstraints(0, 2, new Insets(5, 0, 0, 0));
        _frame.add(_error, gridLayout.getConstraints());
        _frame.validate();
    }
}
