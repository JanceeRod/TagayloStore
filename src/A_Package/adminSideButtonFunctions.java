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

        centerPanelMainLayer.setBackground(color.getCenterPiece());
//        centerPanelMainLayer.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 5));

        JLabel title = Operations.createCustomLabel("TRANSACTION HISTORY", Color.white, font.getProductPriceBOLD(), 0, 0, 0, 0, 0, 0, 0, 0, CENTER);
        centerPanelMainLayer.add(title);

        int size = transactionHistory2D.length;
        int orderWidth = centerPanelMainLayer.getWidth();
        int orderHeight = 160;
        JButton[] perOrderButtons = new JButton[size];
        customRoundedPanel[] perOrder = new customRoundedPanel[size];

        JLabel[] orderID = new JLabel[size];
        JLabel[] dateAndTime = new JLabel[size];
        JLabel[] productName = new JLabel[size];
        JLabel[] productPrice = new JLabel[size];
        JLabel[] productQuantity = new JLabel[size];
        JLabel[] purchaseSubtotal = new JLabel[size];
        JLabel[] purchaseGrandTotal = new JLabel[size];
        JLabel[] purchaseChange = new JLabel[size];
        JLabel[] customerType = new JLabel[size];

        SpringLayout SL = new SpringLayout();
        mainPanelOnCenters = new JPanel();
        mainPanelOnCenters.setBackground(Color.BLUE);
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
            perOrderButtons[i].setPreferredSize(new Dimension(555, 150));

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
            orderID[i] = new JLabel("Transaction ID: " + transactionHistory2D[i][0]);
            orderID[i].setFont(font.getProductNameREGULAR());

            dateAndTime[i] = new JLabel(transactionHistory2D[i][1]);
            dateAndTime[i].setFont(font.getProductNameBOLD());

            productName[i] = new JLabel(transactionHistory2D[i][2]);
            productName[i].setFont(font.getTitleFont());

            productPrice[i] = new JLabel("PHP " + transactionHistory2D[i][3]);
            productPrice[i].setFont(font.getProductPriceBOLD());

            productQuantity[i] = new JLabel(transactionHistory2D[i][4] + "x");
            productQuantity[i].setFont(font.getProductPriceBOLD());

            purchaseSubtotal[i] = new JLabel("PHP " + transactionHistory2D[i][5]);
            purchaseSubtotal[i].setFont(font.getProductPriceBOLD());

            purchaseGrandTotal[i] = new JLabel("PHP " + transactionHistory2D[i][6]);
            purchaseGrandTotal[i].setFont(font.getProductPriceBOLD());

            purchaseChange[i] = new JLabel("Change: PHP " + transactionHistory2D[i][7]);
            purchaseChange[i].setFont(font.getProductNameREGULAR());

            customerType[i] = new JLabel(transactionHistory2D[i][8]);
            customerType[i].setFont(font.getProductNameREGULAR());

            SpringLayout layout = new SpringLayout();
            perOrder[i].setLayout(layout);

            perOrder[i].add(orderID[i]);
            perOrder[i].add(dateAndTime[i]);
            perOrder[i].add(productName[i]);
            perOrder[i].add(productPrice[i]);
            perOrder[i].add(productQuantity[i]);
            perOrder[i].add(purchaseSubtotal[i]);
            perOrder[i].add(purchaseGrandTotal[i]);
            perOrder[i].add(purchaseChange[i]);
            perOrder[i].add(customerType[i]);

            // Constraints
            // 1. Top Row
            layout.putConstraint(SpringLayout.WEST, productName[i], 10, SpringLayout.WEST, perOrder[i]);
            layout.putConstraint(SpringLayout.NORTH, productName[i], 10, SpringLayout.NORTH, perOrder[i]);

            layout.putConstraint(SpringLayout.EAST, dateAndTime[i], -10, SpringLayout.EAST, perOrder[i]);
            layout.putConstraint(SpringLayout.NORTH, dateAndTime[i], 10, SpringLayout.NORTH, perOrder[i]);

            // 2. Second Row
            layout.putConstraint(SpringLayout.WEST, orderID[i], 10, SpringLayout.WEST, perOrder[i]);
            layout.putConstraint(SpringLayout.NORTH, orderID[i], 2, SpringLayout.SOUTH, productName[i]);

            layout.putConstraint(SpringLayout.EAST, customerType[i], -10, SpringLayout.EAST, perOrder[i]);
            layout.putConstraint(SpringLayout.NORTH, customerType[i], 10, SpringLayout.SOUTH, dateAndTime[i]);

            // 3. Third Row (Details)
            layout.putConstraint(SpringLayout.WEST, productQuantity[i], 10, SpringLayout.WEST, perOrder[i]);
            layout.putConstraint(SpringLayout.NORTH, productQuantity[i], 10, SpringLayout.SOUTH, orderID[i]);

            layout.putConstraint(SpringLayout.WEST, productPrice[i], 50, SpringLayout.EAST, productQuantity[i]);
            layout.putConstraint(SpringLayout.NORTH, productPrice[i], 0, SpringLayout.NORTH, productQuantity[i]);

            layout.putConstraint(SpringLayout.WEST, purchaseSubtotal[i], 50, SpringLayout.EAST, productPrice[i]);
            layout.putConstraint(SpringLayout.NORTH, purchaseSubtotal[i], 0, SpringLayout.NORTH, productPrice[i]);

            layout.putConstraint(SpringLayout.WEST, purchaseGrandTotal[i], 50, SpringLayout.EAST, purchaseSubtotal[i]);
            layout.putConstraint(SpringLayout.NORTH, purchaseGrandTotal[i], 0, SpringLayout.NORTH, purchaseSubtotal[i]);

            // 4. Bottom Row
            layout.putConstraint(SpringLayout.WEST, purchaseChange[i], 10, SpringLayout.WEST, perOrder[i]);
            layout.putConstraint(SpringLayout.NORTH, purchaseChange[i], 10, SpringLayout.SOUTH, productQuantity[i]);

            perOrder[i].setPreferredSize(new Dimension(555, orderHeight));
        }

        //---------------------------------------------------------

//        int rowSize = transactionHistory2D.length;

        //---------------------------------------------------------

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
