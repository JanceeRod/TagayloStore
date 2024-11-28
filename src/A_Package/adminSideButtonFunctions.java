package A_Package;

import G_Package.customRoundedPanel;
import G_Package.customScrollBarUI;
import B_Package.userOperations;

import javax.swing.*;
import java.awt.*;

import static javax.swing.SwingConstants.CENTER;

public class adminSideButtonFunctions extends adminDefinitions {

    public static void homeButtonToggle() {
        centerPanelMainLayer.removeAll();
        mainPanelOnCenter.removeAll();
        orderPaneCen.removeAll();

        adminOperations.updateIndicator("Oten");

        centerPanelMainLayer.setBackground(Color.BLUE);
        adminOperations.panelFinisher(centerContainerPanelUp);
    }

    public static void transactionHistoryButtonToggle() {
        centerPanelMainLayer.removeAll();
        mainPanelOnCenter.removeAll();
        orderPaneCen.removeAll();

        orderPaneLabel.setText("VIEW TRANSACTION");

        centerPanelMainLayer.setBackground(color.getCenterPiece());
        mainPanelOnCenter.setBackground(color.getCenterPane());

        JLabel title = userOperations.createCustomLabel("TRANSACTION HISTORY", Color.white, font.getProductPriceBOLD(), 0, 0, 0, 0, 0, 0, 0, 0, CENTER);
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
            purchaseGrandTotal[i].setForeground(color.getHeader());

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
        orderPaneCen.removeAll();

        orderPaneLabel.setText("QUICK ACTIONS");

        JLabel title = userOperations.createCustomLabel("SALES", Color.white, font.getProductPriceBOLD(), 0, 0, 0, 0, 0, 0, 0, 0, CENTER);
        centerPanelMainLayer.add(title);

        centerPanelMainLayer.setBackground(color.getCenterPiece());
        mainPanelOnCenter.setBackground(Color.RED);

        int size = transactionHistory2D.length;
        int orderWidth = centerPanelMainLayer.getWidth();
        int orderHeight = 58;

        SpringLayout SL = new SpringLayout();
        mainPanelOnCenters = new JPanel();
        mainPanelOnCenters.setBackground(mainPanelOnCenter.getBackground());
        mainPanelOnCenters.setLayout(SL);
        mainPanelOnCenters.setPreferredSize(new Dimension(orderWidth, size * (orderHeight + 10)));

        System.out.println("This is sales?????");

        customRoundedPanel[] salesChartPanels = new customRoundedPanel[4];

        for (int i = 0; i < 4; i++) {
            salesChartPanels[i] = new customRoundedPanel(25);
            salesChartPanels[i].setBackground(Color.BLUE);
            salesChartPanels[i].setBorder(BorderFactory.createEmptyBorder());

            salesChartPanels[i].setPreferredSize(new Dimension(mainPanelOnCenter.getWidth() - 50, 150));

            mainPanelOnCenters.add(salesChartPanels[i]);

            if (i == 0) {
                // Attach the first panel to the top of the container
                SL.putConstraint(SpringLayout.NORTH, salesChartPanels[i], 10, SpringLayout.NORTH, mainPanelOnCenter);
            } else {
                // Attach subsequent panels below the previous one
                SL.putConstraint(SpringLayout.NORTH, salesChartPanels[i], 10, SpringLayout.SOUTH, salesChartPanels[i - 1]);
            }

            // Center the panels horizontally
            SL.putConstraint(SpringLayout.HORIZONTAL_CENTER, salesChartPanels[i], 0, SpringLayout.HORIZONTAL_CENTER, mainPanelOnCenter);
        }

        customScrollBarUI scrollBarUI2 = new customScrollBarUI();
        scrollBarUI2.setCustomUI(color.getHeader(), Color.GREEN, color.getCenterPane());

        JScrollPane scrollPane = new JScrollPane(mainPanelOnCenters);

        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUI(scrollBarUI2);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        mainPanelOnCenter.add(scrollPane);

//        new salesBarChart(mainPanelOnCenter);
//        salesBarChart.displaySalesChart();

        adminOperations.panelFinisher(orderPaneCen);
        adminOperations.panelFinisher(centerContainerPanelUp);
    }

    public static void inventoryButtonToggle() {
        centerPanelMainLayer.removeAll();
        mainPanelOnCenter.removeAll();
        orderPaneCen.removeAll();

        orderPaneLabel.setText("QUICK ACTIONS");

        centerPanelMainLayer.setBackground(color.getCenterPiece());

        JLabel title = userOperations.createCustomLabel("INVENTORY MANAGER", Color.white, font.getProductPriceBOLD(), 0, 0, 0, 0, 0, 0, 0, 0, CENTER);
        centerPanelMainLayer.add(title);

        System.out.println("This is inventory?????");

        adminOperations.panelFinisher(orderPaneCen);
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
