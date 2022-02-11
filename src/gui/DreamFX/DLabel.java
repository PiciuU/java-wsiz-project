package gui.DreamFX;

import javax.swing.*;
import java.awt.*;

public class DLabel extends JLabel {
    public DLabel() {
        this.redesign();
    }

    public DLabel(String text) {
        this.redesign();
        this.setText(text);
    }

    public DLabel(String text, int constant) {
        this.redesign();
        this.setText(text);
        this.setHorizontalAlignment(constant);
    }

    public void redesign() {
        this.setForeground(new Color(0xe7e7e7));
    }
}
