package gui.DreamFX;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;

public class DScrollPane extends JScrollPane {
    public DScrollPane(Component view) {
        this.setViewportView(view);
        this.setBorder(new EmptyBorder(0,0,0,0));
        this.getVerticalScrollBar().setUI(new CustomScrollBar());
        this.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));
        this.getVerticalScrollBar().setBackground(new Color(0x252525));
    }
}

class CustomScrollBar extends BasicScrollBarUI {
    private final Dimension d = new Dimension();

    public CustomScrollBar() {

    }

    @Override
    protected JButton createDecreaseButton(int orientation) {
        return new JButton() {
            @Override
            public Dimension getPreferredSize() {
                return d;
            }
        };
    }

    @Override
    protected JButton createIncreaseButton(int orientation) {
        return new JButton() {
            @Override
            public Dimension getPreferredSize() {
                return d;
            }
        };
    }

    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle r) {

    }

    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle r) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setPaint(Color.DARK_GRAY);
        g2.fillRoundRect(r.x, r.y, r.width, r.height, 0, 0);
        g2.dispose();
    }

    @Override
    protected void setThumbBounds(int x, int y, int width, int height) {
        super.setThumbBounds(x, y, width, height);
        scrollbar.repaint();
    }
}
