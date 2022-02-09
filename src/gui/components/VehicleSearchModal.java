package gui.components;

import gui.GUIManager;

import database.controllers.VehicleController;

import models.Vehicle;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import java.util.ArrayList;

public class VehicleSearchModal extends GUIManager implements ActionListener {

    private EventHandler customEvent;

    private ListAction listAction;

    private ArrayList<Vehicle> vehiclesData;

    private GridLayout gridLayout;

    private ArrayList<JTextField> formInputs = new ArrayList<JTextField>();

    private JFrame _frame;
    private JPanel _panel;
    private JScrollPane _scrollPane;

    private DefaultListModel _listModel;
    private JList _list;
    private JLabel _label;
    private JComboBox _comboBoxFilter;
    private JComboBox _comboBoxSort;
    private JTextField _textField;
    private JButton _button;
    private JLabel _error;

    /**
     * Create component for vehicle search modal
     *
     */
    public VehicleSearchModal(EventHandler customEvent, String modalTitle) {
        this.customEvent = customEvent;
        renderFrame(modalTitle);
        renderContent();
    }


    /**
     * Render and mount new frame to application
     *
     * @param title name of frame
     */
    public void renderFrame(String title) {
        gridLayout = new GridLayout();
        _frame = new JFrame();
        _frame.setTitle(title);
        _frame.setSize(500, 450);
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
     * Render and mount content to _frame
     *
     */
    public void renderContent() {
        _panel = new JPanel(gridLayout);

        /* Label */
        _label = new JLabel("Filtrowanie po: ");
        gridLayout.setConstraints(0, 0);
        _panel.add(_label, gridLayout.getConstraints());


        /* _comboBoxFilter */
        _comboBoxFilter = new JComboBox();
        _comboBoxFilter.addItem(new CustomItem("id", "identyfikatorze (domyślne)", "producer || ' ' || model || ' - ' || number_plate"));
        _comboBoxFilter.addItem(new CustomItem("producer", "producencie", "producer || ' ' || model"));
        _comboBoxFilter.addItem(new CustomItem("model", "modelu", "model || ' - ' || producer"));
        _comboBoxFilter.addItem(new CustomItem("horsepower", "mocy silnika", "horsepower || ' KM - ' || producer || ' ' || model"));
        _comboBoxFilter.addItem(new CustomItem("production_year", "roku produkcji", "production_year || ' r. - ' || producer || ' ' || model"));
        _comboBoxFilter.addItem(new CustomItem("number_plate", "tablicach", "number_plate || ' - ' || producer || ' ' || model"));
        gridLayout.setConstraints(1, 0, new Insets(0, 5, 0,0));
        _panel.add(_comboBoxFilter, gridLayout.getConstraints());

        /* Label */
        _label = new JLabel("Sortowanie: ");
        gridLayout.setConstraints(2, 0, new Insets(0, 15, 0, 0));
        _panel.add(_label, gridLayout.getConstraints());


        /* _comboBoxSort */
        _comboBoxSort = new JComboBox();
        _comboBoxSort.addItem(new CustomItem("asc", "rosnąco"));
        _comboBoxSort.addItem(new CustomItem("desc", "malejąco"));
        gridLayout.setConstraints(3, 0, new Insets(0, 5, 0,0));
        _panel.add(_comboBoxSort, gridLayout.getConstraints());

        /* Label */
        _label = new JLabel("Szukana fraza: ");
        gridLayout.setConstraints(0, 1, new Insets(20, 0, 0, 0));
        _panel.add(_label, gridLayout.getConstraints());

        /* TextField */
        _textField = new JTextField();
        _textField.setPreferredSize(new Dimension(150, 25));
        gridLayout.setConstraints(1, 1, new Insets(20, 10, 0, 0));
        _panel.add(_textField, gridLayout.getConstraints());

        /* Button */
        _button = new JButton("Wyszukaj");
        _button.addActionListener(this);
        _button.setActionCommand("searchVehicles");
        _button.setPreferredSize(new Dimension(150, 25));
        gridLayout.setConstraints(2, 1, 2, new Insets(20, 10, 0, 0));
        _panel.add(_button, gridLayout.getConstraints());

        /* Label */
        _label = new JLabel("Powyższa fraza wyszukiwana jest na podstawie wybranego filtru. ");
        _label.setFont(new Font("Arial", Font.PLAIN, 10));
        gridLayout.setConstraints(0, 2, 4, new Insets(5, 0, 0, 0));
        _panel.add(_label, gridLayout.getConstraints());

        _frame.add(_panel);
    }

    /**
     * Render and mount filtered list
     *
     */
    public void renderList() {
        /* ListModel and List */
        _listModel = new DefaultListModel();
        _list = new JList(_listModel);

        listAction = new ListAction(_list, new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                JList list = (JList)e.getSource();
                new VehicleDetails(customEvent, "Szczegóły pojazdu", vehiclesData.get(list.getSelectedIndex()));
            }
        });

        for(Vehicle vehicle : vehiclesData) {
            _listModel.addElement(vehicle.getCustomField());
        }

        /* ScrollPane */
        _scrollPane = new JScrollPane(_list);
        _scrollPane.setPreferredSize(new Dimension(350, 200));
        gridLayout.setConstraints(0, 3, 4, new Insets(10, 0, 0, 0));
        _panel.add(_scrollPane, gridLayout.getConstraints());
    }

    /**
     * Handle the requested action
     *
     * @param e ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();

        if (action == "searchVehicles") {
            CustomItem filterItem = (CustomItem) _comboBoxFilter.getSelectedItem();
            CustomItem sortItem = (CustomItem) _comboBoxSort.getSelectedItem();
            String WHERE_STATEMENT = "1 = 1";
            if (!_textField.getText().isEmpty()) WHERE_STATEMENT = filterItem.getKey() + " LIKE '%" + _textField.getText().replace("'","") + "%' ";

            vehiclesData = VehicleController.getInstance().getCustom("SELECT *, " + filterItem.getPattern() + " AS custom_field FROM vehicle WHERE " + WHERE_STATEMENT + " ORDER BY " + filterItem.getKey() + " " + sortItem.getKey());

            if (_panel.isAncestorOf(_scrollPane)) _panel.remove(_scrollPane);
            renderList();
            _panel.updateUI();
        }
    }

    /**
     * Custom inner class for defining items in fitler and search modal
     *
     */
    class CustomItem {
        private String key;
        private String name;
        private String pattern;

        public String getKey() { return key; }
        public String getName() { return name; }
        public String getPattern() { return pattern; }

        public CustomItem(String key, String name) {
            this.key = key;
            this.name = name;
        }

        public CustomItem(String key, String name, String pattern) {
            this.key = key;
            this.name = name;
            this.pattern = pattern;
        }

        public String toString() {
            return name;
        }
    }
}
