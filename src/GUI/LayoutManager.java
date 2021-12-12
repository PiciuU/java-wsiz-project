package GUI;

import java.awt.*;

public abstract class LayoutManager {
    private GridBagConstraints gbc = new GridBagConstraints();

    protected GridBagConstraints getConstraints() {
        return gbc;
    }

    protected void setConstraints(int fill, int gridx, int gridy, int gridwidth, Insets insets) {
        gbc.fill = fill;
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.gridwidth = gridwidth;
        gbc.insets = insets;
    }

    protected void setConstraints(int gridx, int gridy, int gridwidth, Insets insets) {
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.gridwidth = gridwidth;
        gbc.insets = insets;
    }

    protected void setConstraints(int gridx, int gridy, Insets insets) {
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.insets = insets;
    }

    protected void setConstraints(int gridx, int gridy, int gridwidth) {
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.gridwidth = gridwidth;
    }

    protected void setConstraints(int gridx, int gridy) {
        gbc.gridx = gridx;
        gbc.gridy = gridy;
    }
}
