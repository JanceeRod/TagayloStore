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

        orderPaneLabel.setText("VIEW TRANSACTION");

        centerPanelMainLayer.setBackground(color.getCenterPiece());
//        centerPanelMainLayer.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 5));

        JLabel title = Operations.createCustomLabel("TRANSACTION HISTORY", Color.white, font.getProductPriceBOLD(), 0, 0, 0, 0, 0, 0, 0, 0, CENTER);
        centerPanelMainLayer.add(title);

        int size = transactionHistory2D.length;
        int orderWidth = centerPanelMainLayer.getWidth();
        int orderHeight = 58;
        perOrderButtons = new JButton[size];
        perOrder = new customRoundedPanel[size];

        JLabel[] orderID = new JLabel[size];
        JLabel[] dateAndTime = new JLabel[size];
        JLabel[] purchaseGrandTotal = new JLabel[size];
        JLabel[] customerType = new JLabel[size];

        SpringLayout SL = new SpringLayout();
        mainPanelOnCenters = new JPanel();
        mainPanelOnCenters.setBackground(mainPanelOnCenter.getBackground());
        mainPanelOnCenters.setLayout(SL);
        mainPanelOnCenters.setPreferredSize(new Dimension(orderWidth, size * (orderHeight + 10)));

        for (int i = 0; i < size; i++) {

            perOrderButtons[i] = new JButton();
            perOrderButtons[i].setForeground(Color.RED);
            perOrderButtons[i].setBackground(mainPanelOnCenters.getBackground());
            perOrderButtons[i].setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
            perOrderButtons[i].setEnabled(T);
            perOrderButtons[i].setFocusPainted(F);
            perOrderButtons[i].setLayout(new BorderLayout(1,1));
            perOrderButtons[i].setPreferredSize(new Dimension(555, orderHeight));
            perOrderButtons[i].addActionListener(new adminActionManager.viewTransaction(transactionHistory2D[i][0]));

            perOrder[i] = new customRoundedPanel(25);
            perOrder[i].setBackground(Color.WHITE);
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

            for (int j = 0; j < 9; j++) {
//                System.out.println(transactionHistory2D[i][j]);
            }
        }

        for (int i = 0; i < size; i++) {
            customerType[i] = new JLabel(transactionHistory2D[i][8]);
            customerType[i].setFont(font.getProductNameBOLD());
            customerType[i].setForeground(color.getHeader());

            dateAndTime[i] = new JLabel(transactionHistory2D[i][1]);
            dateAndTime[i].setFont(font.getProductNameREGULAR());

            orderID[i] = new JLabel("Transaction ID: " + transactionHistory2D[i][0]);
            orderID[i].setFont(font.getProductNameREGULAR());

            purchaseGrandTotal[i] = new JLabel("PHP " + transactionHistory2D[i][5]);
            purchaseGrandTotal[i].setFont(font.getProductPriceBOLD());

            SpringLayout layout = new SpringLayout();
            perOrder[i].setLayout(layout);

            perOrder[i].add(orderID[i]);
            perOrder[i].add(dateAndTime[i]);
            perOrder[i].add(purchaseGrandTotal[i]);
            perOrder[i].add(customerType[i]);

            layout.putConstraint(SpringLayout.WEST, customerType[i], 10, SpringLayout.WEST, perOrder[i]);
            layout.putConstraint(SpringLayout.NORTH, customerType[i], 10, SpringLayout.NORTH, perOrder[i]);

            layout.putConstraint(SpringLayout.WEST, dateAndTime[i], 10, SpringLayout.EAST, customerType[i]);
            layout.putConstraint(SpringLayout.NORTH, dateAndTime[i], 0, SpringLayout.NORTH, customerType[i]);

            layout.putConstraint(SpringLayout.WEST, orderID[i], 10, SpringLayout.WEST, perOrder[i]);
            layout.putConstraint(SpringLayout.NORTH, orderID[i], 5, SpringLayout.SOUTH, customerType[i]);

            layout.putConstraint(SpringLayout.EAST, purchaseGrandTotal[i], -10, SpringLayout.EAST, perOrder[i]);
            layout.putConstraint(SpringLayout.VERTICAL_CENTER, purchaseGrandTotal[i], 0, SpringLayout.VERTICAL_CENTER, perOrder[i]);
        }

        customScrollBarUI scrollBarUI2 = new customScrollBarUI();
        scrollBarUI2.setCustomUI(color.getHeader(), Color.GREEN, color.getCenterPane());

        JScrollPane scrollPane = new JScrollPane(mainPanelOnCenters);

        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUI(scrollBarUI2);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        mainPanelOnCenter.add(scrollPane);

        perOrderButtons[0].doClick();

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
