package A_Package;

import G_Package.customRoundedPanel;
import G_Package.customScrollBarUI;
import G_Package.customSwingCreate;
import S_Package.salesBarChart;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.Set;

import static A_Package.adminOperations.getJTextField;
import static A_Package.adminOperations.panelFinisher;
import static javax.swing.SwingConstants.CENTER;

public class adminSideButtonFunctions extends adminDefinitions {

    public static void transactionHistoryButtonToggle() {
        centerPanelMainLayer.removeAll();
        mainPanelOnCenter.removeAll();
        orderPaneCen.removeAll();

        orderPaneLabel.setText("TRANSACTION DETAILS");

        centerPanelMainLayer.setBackground(color.getCenterPiece());
        centerPanelMainLayer.setLayout(new BorderLayout());

        mainPanelOnCenter.setBackground(color.getCenterPane());

        JLabel title = customSwingCreate.createCustomLabel("TRANSACTION HISTORY", Color.white, font.getProductPriceBOLD(), 0, 0, 0, 0, 0, 0, 0, 0, CENTER);
        centerPanelMainLayer.add(title, BorderLayout.CENTER);

        int size = manager.getTransactionCount();
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
            perOrderButtons[i].addActionListener(new adminActionManager.viewTransaction(manager.getTransactionIDByIndex(i)));

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

        DecimalFormat formatter = new DecimalFormat("#,###.00");

        for (int i = 0; i < size; i++) {

            customerType[i] = new JLabel(manager.getCustomerName(manager.getTransactionIDByIndex(i)));
            customerType[i].setFont(font.getProductNameBOLD());
            customerType[i].setForeground(color.getHeader());

            dateAndTime[i] = new JLabel(manager.getPurchaseDate(manager.getTransactionIDByIndex(i)));
            dateAndTime[i].setFont(font.getProductNameREGULAR());

            orderID[i] = new JLabel("Transaction ID: " + manager.getTransactionIDByIndex(i));
            orderID[i].setFont(font.getProductNameREGULAR());

            purchaseGrandTotal[i] = new JLabel("PHP " + formatter.format(manager.calculateGrandTotal(manager.getTransactionIDByIndex(i), productPrices)));
            purchaseGrandTotal[i].setFont(font.getProductPriceBOLD());
            purchaseGrandTotal[i].setForeground(color.getHeader());

            SpringLayout layout = new SpringLayout();
            perOrder[i].setLayout(layout);

            perOrder[i].add(orderID[i]);
            perOrder[i].add(dateAndTime[i]);
            perOrder[i].add(purchaseGrandTotal[i]);
            perOrder[i].add(customerType[i]);

            layout.putConstraint(SpringLayout.WEST, customerType[i], 15, SpringLayout.WEST, perOrder[i]);
            layout.putConstraint(SpringLayout.NORTH, customerType[i], 10, SpringLayout.NORTH, perOrder[i]);

            layout.putConstraint(SpringLayout.WEST, dateAndTime[i], 15, SpringLayout.EAST, customerType[i]);
            layout.putConstraint(SpringLayout.NORTH, dateAndTime[i], 0, SpringLayout.NORTH, customerType[i]);

            layout.putConstraint(SpringLayout.WEST, orderID[i], 15, SpringLayout.WEST, perOrder[i]);
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

        panelFinisher(centerContainerPanelUp);
    }

    public static void salesButtonToggle() {
        centerPanelMainLayer.removeAll();
        mainPanelOnCenter.removeAll();
        orderPaneCen.removeAll();

        orderPaneLabel.setText("SALES DETAILS");

        centerPanelMainLayer.setBackground(color.getCenterPiece());
        centerPanelMainLayer.setLayout(new BorderLayout());

        mainPanelOnCenter.setBackground(color.getCenterPane());

        JLabel title = customSwingCreate.createCustomLabel(
                "SALES CHARTS", Color.WHITE, font.getProductPriceBOLD(),
                0, 0, 0, 0, 0, 0, 0, 0, CENTER
        );
        centerPanelMainLayer.add(title, BorderLayout.CENTER);

        int size = 5; // Number of panels
        int panelHeight = 350; // Height of each sales chart panel
        int panelGap = 10; // Gap between panels
        int orderWidth = centerPanelMainLayer.getWidth(); // Get the width of the main container

        SpringLayout layout = new SpringLayout();
        mainPanelOnCenters = new JPanel();
        mainPanelOnCenters.setBackground(mainPanelOnCenter.getBackground());
        mainPanelOnCenters.setLayout(layout);

        // Calculate preferred size based on the number of panels
        mainPanelOnCenters.setPreferredSize(new Dimension(orderWidth, size * (panelHeight + panelGap) + panelGap));

        customRoundedPanel[] salesChartPanels = new customRoundedPanel[size];

        JPanel[] afterSalesChartPanels = new JPanel[size];

        JPanel[] chart = new JPanel[size];
        String[] filter = {"last5Days", "2024", "2023", "2022", "yearly"};

        // Create and add panels to mainPanelOnCenters
        for (int i = 0; i < size; i++) {
            salesChartPanels[i] = new customRoundedPanel(25);
            salesChartPanels[i].setBackground(Color.WHITE);
            salesChartPanels[i].setBorder(new EmptyBorder(0, 15, 0, 15));
            salesChartPanels[i].setPreferredSize(new Dimension(0, panelHeight)); // Width dynamically adjusted by layout
            salesChartPanels[i].setLayout(new BorderLayout());

            afterSalesChartPanels[i] = new JPanel();
            afterSalesChartPanels[i].setBackground(Color.GREEN);

            salesChartPanels[i].add(afterSalesChartPanels[i]);

            mainPanelOnCenters.add(salesChartPanels[i]);

            if (i == 0) {
                layout.putConstraint(SpringLayout.NORTH, salesChartPanels[i], panelGap, SpringLayout.NORTH, mainPanelOnCenters);
            } else {
                layout.putConstraint(SpringLayout.NORTH, salesChartPanels[i], panelGap, SpringLayout.SOUTH, salesChartPanels[i - 1]);
            }

            layout.putConstraint(SpringLayout.WEST, salesChartPanels[i], panelGap, SpringLayout.WEST, mainPanelOnCenters);
            layout.putConstraint(SpringLayout.EAST, salesChartPanels[i], -panelGap, SpringLayout.EAST, mainPanelOnCenters);

            chart[i] = afterSalesChartPanels[i];
            salesBarChart barChart = new salesBarChart(chart[i], filter[i]);
            salesBarChart.displaySalesChart();
        }

        // Create a scrollable pane
        customScrollBarUI scrollBarUI = new customScrollBarUI();
        scrollBarUI.setCustomUI(color.getHeader(), Color.GREEN, color.getCenterPane());

        JScrollPane scrollPane = new JScrollPane(mainPanelOnCenters);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUI(scrollBarUI);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Add the scrollPane to mainPanelOnCenter
        mainPanelOnCenter.setLayout(new BorderLayout());
        mainPanelOnCenter.add(scrollPane, BorderLayout.CENTER);

        // Finalize panels
        panelFinisher(orderPaneCen);
        panelFinisher(centerContainerPanelUp);
    }



    public static void inventoryButtonToggle()  {
        centerPanelMainLayer.removeAll();
        mainPanelOnCenter.removeAll();
        orderPaneCen.removeAll();

        mainPanelOnCenter.setBackground(color.getCenterPane());

        centerPanelMainLayer.setBackground(color.getLeftSide());
        centerPanelMainLayer.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        centerPanelMainLayer.setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 0));

        int[] arrayLengths = new int[inventoryCategoryDataMap.size()];
        int i = 0;
        for (Map.Entry<String, String[][]> entry : inventoryCategoryDataMap.entrySet()) {
            String[][] array2D = entry.getValue();

            int length = array2D.length;
            arrayLengths[i] = length;
            i++;
        }

        int size = inventoryCategoryDataMap.size();
        inventoryTabsButtons = new JButton[size];
        pillShapeButtonTabs = new customRoundedPanel[size];
        pillShapeButtonTabsLabel = new JLabel[size];
        for (int j = 0; j < inventoryTabsButtons.length; j++) {
            inventoryTabsButtons[j] = new JButton();
            inventoryTabsButtons[j].setBackground(centerPanelMainLayer.getBackground());
            inventoryTabsButtons[j].setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            inventoryTabsButtons[j].setEnabled(T);
            inventoryTabsButtons[j].setFocusPainted(F);
            inventoryTabsButtons[j].setPreferredSize(new Dimension(105, 34));
            inventoryTabsButtons[j].addActionListener(new adminActionManager.inventoryTable(j, arrayLengths[j]));

            pillShapeButtonTabs[j] = new customRoundedPanel(25);
            pillShapeButtonTabs[j].setLayout(new GridLayout(1,1));
            inventoryTabsButtons[j].add(pillShapeButtonTabs[j]);

            pillShapeButtonTabsLabel[j] = new JLabel();
            Set<String> arrayNames = inventoryCategoryDataMap.keySet();
            if (j < arrayNames.size()) {
                String arrayName = (String) arrayNames.toArray()[j];
                pillShapeButtonTabsLabel[j].setText(arrayName);
            }
            pillShapeButtonTabsLabel[j].setHorizontalAlignment(CENTER);
            pillShapeButtonTabsLabel[j].setFont(font.getProductNameREGULAR());
            pillShapeButtonTabsLabel[j].setForeground(Color.GRAY);

            pillShapeButtonTabs[j].add(pillShapeButtonTabsLabel[j]);

            centerPanelMainLayer.add(inventoryTabsButtons[j]);
        }
        panelFinisher(orderPaneCen);
        panelFinisher(centerContainerPanelUp);
    }
}
