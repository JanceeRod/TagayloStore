package adminPackage;

import G_Package.colorPallete;
import G_Package.fontGallery;
import mainPackage.Operations;

import javax.swing.*;
import java.awt.*;

public class adminInterface extends adminDefinitions {
    colorPallete color = new colorPallete();
    fontGallery font = new fontGallery();

    boolean T = true, F = false;

    public adminInterface() {
        instantiate();

        JFrame frame = new JFrame();
        frame.setTitle("Java Jive Cafe - Admin Access");
        frame.setSize(1100, 765);
        frame.setLocationRelativeTo(null);
        frame.setResizable(T);

        JPanel TopRibbonPanel = createCustomPanel(1080, 60, color.getHeader(), null); TopRibbonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        frame.getContentPane().setLayout(new BorderLayout());
        frame.add(TopRibbonPanel, BorderLayout.NORTH);
        frame.setResizable(F);
        frame.setVisible(T);
    }

    public void instantiate() {
        //reserved
    }

    public JPanel createCustomPanel (int width, int height, Object backgroundSource, LayoutManager layout) {
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
}
