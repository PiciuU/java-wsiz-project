package gui.DreamFX;

import javax.swing.*;
import javax.swing.plaf.metal.MetalTabbedPaneUI;
import java.awt.*;

public class DTabbedPane extends JTabbedPane {
    public DTabbedPane() {
        this.setBackground(new Color(0x333333));
        this.setForeground(new Color(0xe7e7e7));
        this.setUI(new CustomTabbedPane());
    }
}

class CustomTabbedPane extends MetalTabbedPaneUI {
    @Override
    protected void installDefaults() {
        super.installDefaults();
        if (contentBorderInsets != null) {
            contentBorderInsets = new Insets(0, 0, 0, 0);
        }
    }
    @Override
    protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
        g.fillRoundRect(x, y, w, h, 0, 0);
    }


}
