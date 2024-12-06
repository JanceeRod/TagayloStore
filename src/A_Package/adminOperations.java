package A_Package;

import G_Package.customRoundedPanel;

import javax.swing.*;
import java.awt.*;

import static javax.swing.SwingConstants.CENTER;

public class adminOperations extends adminDefinitions {

    public static JTextField getJTextField(String text, int textBoxWidth, customRoundedPanel colorReference) {
        JTextField textBox = new JTextField(text);
        textBox.setPreferredSize(new Dimension(textBoxWidth, 29));
        textBox.setCaretPosition(0);
        textBox.setFont(font.getProductNameBOLD());
        textBox.setBackground(colorReference.getBackground());
        textBox.setForeground(Color.GRAY);
        textBox.setHorizontalAlignment(SwingConstants.LEFT);
        textBox.setBorder(BorderFactory.createEmptyBorder());
        return textBox;
    }

    public static void panelFinisher(JPanel panel) {
        panel.revalidate();
        panel.repaint();
    }

    public static void updateIndicator(String text) {
        mainPanelOnCenter.setLayout(new BorderLayout());

        JLabel notice = new JLabel();
        notice.setText(text);
        notice.setFont(font.getProductPriceBOLD());
        notice.setForeground(Color.LIGHT_GRAY);
        notice.setHorizontalAlignment(CENTER);

        mainPanelOnCenter.add(notice, BorderLayout.CENTER);
    }

    public static void buttonColorReset(JPanel[] panel, int buttonIndex, Color a, Color b) {
        for (JPanel jPanel : panel) {
            jPanel.setBackground(a);
        }
        panel[buttonIndex].setBackground(b);
    }
}
