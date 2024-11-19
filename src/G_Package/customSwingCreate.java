package G_Package;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class customSwingCreate {
    public static JPanel createCustomPanel(int width, int height, Object backgroundSource, LayoutManager layout) {
        JPanel customPanel = new JPanel();
        customPanel.setPreferredSize(new Dimension(width, height));
        if (backgroundSource instanceof JPanel) {
            customPanel.setBackground(((JPanel) backgroundSource).getBackground());
        } else if (backgroundSource instanceof Color) {
            customPanel.setBackground((Color) backgroundSource);
        }
        customPanel.setLayout(new BorderLayout());
        return customPanel;
    }

    public static JLabel createCustomLabel(String text, Color foreground, Font font, int x, int y, int width, int height, int topBorder, int leftBorder, int bottomBorder, int rightBorder, int horizontalAlignment) {
        JLabel label = new JLabel();
        label.setText(text);
        label.setForeground(foreground);
        label.setFont(font);
        label.setBounds(x, y, width, height);
        label.setBorder(new EmptyBorder(topBorder, leftBorder, bottomBorder, rightBorder));
        label.setHorizontalAlignment(horizontalAlignment);
        return label;
    }

    public static customRoundedPanel createCustomRoundedPanel(int radius, int borderTop, int borderLeft, int borderBottom, int borderRight, Object backgroundSource, LayoutManager layout) {
        customRoundedPanel customPanel = new customRoundedPanel(radius);
        customPanel.setBorder(BorderFactory.createEmptyBorder(borderTop, borderLeft, borderBottom, borderRight));
        if (backgroundSource instanceof JPanel) {
            customPanel.setBackground(((JPanel) backgroundSource).getBackground());
        } else if (backgroundSource instanceof Color) {
            customPanel.setBackground((Color) backgroundSource);
        }
        customPanel.setLayout(layout);
        return customPanel;
    }
}
