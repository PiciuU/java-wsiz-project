package gui.DreamFX;

import javax.swing.*;
import java.awt.*;

public class DList extends JList {
    public DList(DefaultListModel listModel) {
        this.setModel(listModel);
        this.setBackground(new Color(0x343332));
        this.setForeground(new Color(0xe7e7e7));

        this.setSelectionBackground(new Color(0x0078d7));
        this.setSelectionForeground(new Color(0xe7e7e7));
    }
}
