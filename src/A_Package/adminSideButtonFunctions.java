package A_Package;

import G_Package.customRoundedPanel;
import G_Package.customScrollBarUI;
import G_Package.customSwingCreate;
import M_Package.Operations;
import M_Package.mouseListen;

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.Set;

import static javax.swing.SwingConstants.CENTER;
import static jdk.internal.org.jline.utils.InfoCmp.Capability.buttons;

public class adminSideButtonFunctions extends adminDefinitions {

    public static void homeButtonToggle() {
        centerPanelMainLayer.removeAll();
        mainPanelOnCenter.removeAll();

        adminOperations.updateIndicator("Oten");

        centerPanelMainLayer.setBackground(Color.BLUE);
        adminOperations.panelFinisher(centerContainerPanelUp);
    }

    public static void transactionHistoryButtonToggle() {
        centerPanelMainLayer.removeAll();
        mainPanelOnCenter.removeAll();

        centerPanelMainLayer.setBackground(color.getCenterPiece());
//        centerPanelMainLayer.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 5));

        JLabel title = Operations.createCustomLabel("TRANSACTION HISTORY", Color.white, font.getProductPriceBOLD(), 0, 0, 0, 0, 0, 0, 0, 0, CENTER);

        centerPanelMainLayer.add(title);

        SpringLayout SL = new SpringLayout();
        JPanel mainPanelOnCenters = new JPanel();
//            mainPanelOnCenters.setBackground(mainPanelOnCenter.getBackground());
        mainPanelOnCenters.setBackground(Color.BLUE);
        mainPanelOnCenters.setLayout(SL);


        int size = transactionHistory2D.length;
        JButton[] perOrderButtons = new JButton[size];
        customRoundedPanel[] perOrder = new customRoundedPanel[size];

        System.out.println(size);

        int orderWidth = centerPanelMainLayer.getWidth();

        mainPanelOnCenters.setPreferredSize(new Dimension(orderWidth, size * 75));

        for (int i = 0; i < size; i++) {
            perOrderButtons[i] = new JButton("Button " + i);
            perOrderButtons[i].setBackground(Color.GREEN);
            perOrderButtons[i].setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            perOrderButtons[i].setEnabled(T);
            perOrderButtons[i].setFocusPainted(F);
            perOrderButtons[i].setLayout(new BorderLayout(1,1));
            perOrderButtons[i].setPreferredSize(new Dimension(555, 65));

            perOrder[i] = new customRoundedPanel(25);
            perOrder[i].setBackground(Color.BLUE);
            perOrder[i].setLayout(null);

            perOrderButtons[i].add(perOrder[i]);
            mainPanelOnCenters.add(perOrderButtons[i]);

        }

        for (int i = 0; i < size; i++) {
            if (i == 0) {
                SL.putConstraint(SpringLayout.NORTH, perOrderButtons[i], 10, SpringLayout.NORTH, mainPanelOnCenters);
                SL.putConstraint(SpringLayout.WEST, perOrderButtons[i], 10, SpringLayout.WEST, mainPanelOnCenters);
                SL.putConstraint(SpringLayout.EAST, perOrderButtons[i], -10, SpringLayout.EAST, mainPanelOnCenters);
            } else {
                SL.putConstraint(SpringLayout.NORTH, perOrderButtons[i], 10, SpringLayout.SOUTH, perOrderButtons[i - 1]);
                SL.putConstraint(SpringLayout.WEST, perOrderButtons[i], 10, SpringLayout.WEST, mainPanelOnCenters);
                SL.putConstraint(SpringLayout.EAST, perOrderButtons[i], -10, SpringLayout.EAST, mainPanelOnCenters);
            }
        }

        customScrollBarUI scrollBarUI2 = new customScrollBarUI();
        JScrollPane scrollPane = new JScrollPane(mainPanelOnCenters);

        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUI(scrollBarUI2);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        mainPanelOnCenter.add(scrollPane);

        adminOperations.panelFinisher(centerContainerPanelUp);
    }

    public static void salesButtonToggle() {
        centerPanelMainLayer.removeAll();
        mainPanelOnCenter.removeAll();

        adminOperations.updateIndicator("miss ko na sya");

        centerPanelMainLayer.setBackground(Color.MAGENTA);
        adminOperations.panelFinisher(centerContainerPanelUp);
    }

    public static void inventoryButtonToggle() {
        centerPanelMainLayer.removeAll();
        mainPanelOnCenter.removeAll();

        centerPanelMainLayer.setBackground(color.getLeftSide());
        centerPanelMainLayer.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        centerPanelMainLayer.setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 0));

        int[] arrayLengths = new int[categoryDataMap.size()];
        int i = 0;
        for (Map.Entry<String, String[][]> entry : categoryDataMap.entrySet()) {
            String[][] array2D = entry.getValue();

            int length = array2D.length;
            arrayLengths[i] = length;
            i++;
        }

        int size = categoryDataMap.size();
        button2_ = new JButton[size];
        pillShape = new customRoundedPanel[size];
        pSLabel = new JLabel[size];
        for (int j = 0; j < button2_.length; j++) {
            button2_[j] = new JButton();
            button2_[j].setBackground(centerPanelMainLayer.getBackground());
            button2_[j].setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            button2_[j].setEnabled(T);
            button2_[j].setFocusPainted(F);
            button2_[j].setPreferredSize(new Dimension(105, 34));
            button2_[j].addActionListener(new adminActionManager.menuTable(j, arrayLengths[j]));

            pillShape[j] = new customRoundedPanel(25);
            pillShape[j].setLayout(new GridLayout(1,1));
            button2_[j].add(pillShape[j]);

            button2_[j].addMouseListener(new mouseListen(pillShape[j], button2_[j], color.getChoice(), Color.RED));

            pSLabel[j] = new JLabel();
            Set<String> arrayNames = categoryDataMap.keySet();
            if (j < arrayNames.size()) {
                String arrayName = (String) arrayNames.toArray()[j];
                pSLabel[j].setText(arrayName);
            }
            pSLabel[j].setHorizontalAlignment(CENTER);
            pSLabel[j].setFont(font.getProductNameREGULAR());
            pSLabel[j].setForeground(Color.GRAY);

            pillShape[j].add(pSLabel[j]);

            centerPanelMainLayer.add(button2_[j]);
        }
        adminOperations.panelFinisher(centerContainerPanelUp);
    }

    public static void categoriesButtonToggle() {
        centerPanelMainLayer.removeAll();
        mainPanelOnCenter.removeAll();

        adminOperations.updateIndicator("hahaha");

        centerPanelMainLayer.setBackground(Color.ORANGE);
        adminOperations.panelFinisher(centerContainerPanelUp);
    }
}
