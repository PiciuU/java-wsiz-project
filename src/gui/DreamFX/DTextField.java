package gui.DreamFX;

import javax.swing.*;
import java.awt.*;

public class DTextField extends JTextField {
    public DTextField() {
        this.setBackground(new Color(0x252525));
        this.setForeground(new Color(0xe7e7e7));
        this.setCaretColor(new Color(0xe7e7e7));

        this.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(0xe7e7e7)));
    }

}
