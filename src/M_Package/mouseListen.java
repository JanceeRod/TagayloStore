package M_Package;

import G_Package.customRoundedPanel;
import G_Package.customColorPallete;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class mouseListen extends MouseAdapter {
    private customRoundedPanel panel;
    private JButton button;
    customColorPallete color = new customColorPallete();

    public mouseListen(customRoundedPanel panel, JButton button) {
        this.setPanel(panel);
        this.setButton(button);
    }
    private void setButton(JButton button) {
        this.button = button;
    }
    @Override
    public void mouseEntered(MouseEvent e) {
        getPanel().setBackground(Color.white);
        button.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    }

    @Override
    public void mouseExited(MouseEvent e) {
        getPanel().setBackground(Color.white);
        button.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }
    public JPanel getPanel() {
        return panel;
    }
    public void setPanel(customRoundedPanel panel) {
        this.panel = panel;
    }
}
