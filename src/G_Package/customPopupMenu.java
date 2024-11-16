package G_Package;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class customPopupMenu extends JPopupMenu {
    private JScrollPane scrollPane;
    private customRoundedPanel itemPanel;
    private customFontGallery font = new customFontGallery();

    public customPopupMenu() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        itemPanel = new customRoundedPanel(25);
        itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.Y_AXIS));
        itemPanel.setBackground(Color.WHITE);

        scrollPane = new JScrollPane(itemPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        customScrollBarUI customScrollBar = new customScrollBarUI();
        scrollPane.getVerticalScrollBar().setUI(customScrollBar);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        add(scrollPane, BorderLayout.CENTER);
    }

    public void addMenuItem(String text, ActionListener actionListener) {
        JButton menuItem = new JButton(text);
        menuItem.setAlignmentX(Component.LEFT_ALIGNMENT);
        menuItem.setFocusable(false);
        menuItem.setBackground(Color.WHITE);
        menuItem.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        menuItem.setHorizontalAlignment(SwingConstants.LEFT);
        menuItem.setFont(font.getFG4());

        int buttonWidth = 180;
        int buttonHeight = 30;

        menuItem.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        menuItem.setMaximumSize(new Dimension(buttonWidth, buttonHeight));
        menuItem.setMinimumSize(new Dimension(buttonWidth, buttonHeight));

        menuItem.addActionListener(actionListener);

        // Hover effect
        menuItem.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                menuItem.setBackground(new Color(230, 230, 230));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                menuItem.setBackground(Color.WHITE);
            }
        });

        itemPanel.add(menuItem);
        itemPanel.revalidate();
        itemPanel.repaint();
    }
}

