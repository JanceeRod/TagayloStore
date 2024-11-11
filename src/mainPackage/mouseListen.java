package mainPackage;

import G_Package.RoundedPanel;
import G_Package.colorPallete;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class mouseListen extends MouseAdapter {
    private RoundedPanel panel;
    private JButton button;
    colorPallete color = new colorPallete();

    public mouseListen(RoundedPanel panel, JButton button) {
        this.setPanel(panel);
        this.setButton(button);
    }
    private void setButton(JButton button) {
        this.button = button;
    }
    @Override
    public void mouseEntered(MouseEvent e) {
        getPanel().setBackground(color.getInactiveButton());
        button.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    }

    @Override
    public void mouseExited(MouseEvent e) {
        getPanel().setBackground(color.getLeftSide());
        button.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }
    public JPanel getPanel() {
        return panel;
    }
    public void setPanel(RoundedPanel panel) {
        this.panel = panel;
    }
}
