package gui.DreamFX;

import javax.swing.*;
import java.awt.*;

public class DComboBox extends JComboBox {
    public DComboBox() {
        this.setBackground(new Color(0x252525));
        this.setForeground(new Color(0xe7e7e7));
        this.setFocusable(false);
        this.setRenderer(new CustomRenderer(this.getRenderer()));
    }
}

class CustomRenderer extends DefaultListCellRenderer {

    private ListCellRenderer defaultRenderer;

    public CustomRenderer(ListCellRenderer defaultRenderer) {
        this.defaultRenderer = defaultRenderer;
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        Component c = defaultRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        if (c instanceof JLabel) {
            if (isSelected) {
                c.setBackground(new Color(0x0078d7));
            }
        }
        return c;
    }
}
