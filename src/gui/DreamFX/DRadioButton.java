package gui.DreamFX;

import javax.swing.*;
import java.awt.*;

public class DRadioButton extends JRadioButton {
    public DRadioButton(String text, boolean isSelected) {
        this.setText(text);
        this.setSelected(isSelected);
        this.setBackground(new Color(0x252525));
        this.setForeground(new Color(0xe7e7e7));
        this.setFocusPainted(false);
    }
}
