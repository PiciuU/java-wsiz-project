package GUI.views;

import GUI.GUIManager;
import GUI.components.*;
import modules.Vehicle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Parking extends GUIManager {

    private JPanel view;
    private JToolBar toolBar;

    private Slots parkingSlotComponent;

    public JPanel getView() {
        return view;
    }

    public Parking() {
        view = new JPanel();
        view.setLayout(new BoxLayout(view, BoxLayout.PAGE_AXIS));
        init();
    }

    public void init() {
        parkingSlotComponent = new Slots(new CustomEvent());

        toolBar = new JToolBar();
        toolBar.setAlignmentX(Component.LEFT_ALIGNMENT);
        toolBar.setFloatable(false);

        JButton btn = new JButton("Zaparkuj samochód");
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.setFocusable(false);
        btn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toolBar.add(btn);

        view.add(toolBar);
        view.add(parkingSlotComponent.getComponentScrollable());
    }

    class CustomEvent extends EventHandler {
        @Override
        public void disposeFrame(JFrame frame) {
            System.out.println("CustomEvent::disposeFrame");
            frame.dispose();
        }

        @Override
        public void reloadContent() {
            view.remove(parkingSlotComponent.getComponentScrollable());
            System.out.println("CustomEvent::reloadComponent");
            parkingSlotComponent = new Slots(new CustomEvent());
            view.add(parkingSlotComponent.getComponentScrollable());
        }
    }

}
