package gui.DreamFX;

import javax.swing.*;
import java.awt.*;

public class DToolBar extends JToolBar {
    public DToolBar() {
        this.setMargin(new Insets(0,-1,-2,0));
        this.setBorderPainted(false);
        this.setBackground(new Color(0x252525));
    }
}
