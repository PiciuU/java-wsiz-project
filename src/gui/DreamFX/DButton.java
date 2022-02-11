package gui.DreamFX;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;

public class DButton extends JButton {

    public DButton(String text) {
        this.setText(text);
        this.redesign("primary");
    }

    public DButton(String type, String text) {
        this.setText(text);
        this.redesign(type);
    }

    private void redesign(String type) {
        this.setForeground(Color.white);
        if (type == "primary") {
            this.setFont(new Font("Arial", Font.BOLD, 14));
            this.setBackground(new Color(0x0078d7));
            this.setUI(new CustomButton());
        } else if (type == "secondary") {
            this.setFont(new Font("Arial", Font.PLAIN, 12));
            this.setBackground(new Color(0x333333));
            this.setBorderPainted(false);
            this.setFocusPainted(false);
        }
    }
}

class CustomButton extends BasicButtonUI {

    @Override
    public void installUI (JComponent c) {
        super.installUI(c);
        AbstractButton button = (AbstractButton) c;
        button.setOpaque(false);
        button.setBorder(new EmptyBorder(8, 10, 8, 10));
    }

    @Override
    public void paint (Graphics g, JComponent c) {
        AbstractButton b = (AbstractButton) c;
        paintBackground(g, b, b.getModel().isPressed() ? 2 : 0);
        super.paint(g, c);
    }

    private void paintBackground (Graphics g, JComponent c, int yOffset) {
        Dimension size = c.getSize();
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(c.getBackground().darker());
        g.fillRoundRect(0, yOffset, size.width, size.height - yOffset, 10, 10);
        g.setColor(c.getBackground());
        g.fillRoundRect(0, yOffset, size.width, size.height + yOffset - 5, 10, 10);
    }
}
