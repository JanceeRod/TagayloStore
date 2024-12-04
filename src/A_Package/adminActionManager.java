package A_Package;

import G_Package.customRoundedPanel;
import G_Package.customScrollBarUI;
import B_Package.userOperations;
import T_Package.TransactionManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static A_Package.adminOperations.panelFinisher;
import static T_Package.TransactionManager.getPurchases;

public class adminActionManager extends adminDefinitions {

    public static class viewTransaction implements ActionListener {
        private String transactionID;

        public viewTransaction(String transactionID) {
            this.setTransactionID(transactionID);
        }

        private void setTransactionID(String transactionID) {
            this.transactionID = transactionID;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("This is purchase no: " + transactionID);

            orderPaneCen.removeAll();
            orderPaneCen.setBorder(new EmptyBorder(20,0, 20,0));

            SpringLayout layout = new SpringLayout();
            orderPaneCen.setLayout(layout);

            int count = manager.getTransactionCount();

            if (count != 0) {
                for (int i = 0; i < count; i++) {
                    if (manager.getTransactionIDByIndex(i).equals(transactionID)) {
                        perOrder[i].setBackground(color.getRightSide());
                    } else {
                        perOrder[i].setBackground(Color.WHITE);
                    }
                }
            } else {
                System.out.println("Transaction ID not found!");
                JLabel errorLabel = new JLabel("Transaction not found.");
                orderPaneCen.add(errorLabel);
                orderPaneCen.revalidate();
                orderPaneCen.repaint();
                return;
            }

            JLabel transactionIDLabel = new JLabel("Transaction ID:");
            transactionIDLabel.setFont(font.getProductNameREGULAR());

            JLabel transactionIDValue = new JLabel(transactionID);
            transactionIDValue.setFont(font.getProductNameBOLD());

            JLabel customerLabel = new JLabel("Customer:");
            customerLabel.setFont(font.getProductNameREGULAR());

            JLabel customerValue = new JLabel(manager.getCustomerName(transactionID));
            customerValue.setFont(font.getProductNameBOLD());

            JLabel dateTimeLabel = new JLabel("Date & Time:");
            dateTimeLabel.setFont(font.getProductNameREGULAR());

            JLabel dateTimeLabelValue = new JLabel(manager.getPurchaseDate(transactionID));
            dateTimeLabelValue.setFont(font.getProductNameBOLD());

            JLabel purchasesLabel = new JLabel("Purchases:");
            purchasesLabel.setFont(font.getProductNameREGULAR());

            orderPaneCen.add(transactionIDLabel);
            orderPaneCen.add(transactionIDValue);
            orderPaneCen.add(customerLabel);
            orderPaneCen.add(customerValue);
            orderPaneCen.add(dateTimeLabel);
            orderPaneCen.add(dateTimeLabelValue);
            orderPaneCen.add(purchasesLabel);


            // Transaction ID
            layout.putConstraint(SpringLayout.WEST, transactionIDLabel, 20, SpringLayout.WEST, orderPaneCen);
            layout.putConstraint(SpringLayout.NORTH, transactionIDLabel, 10, SpringLayout.NORTH, orderPaneCen);
            layout.putConstraint(SpringLayout.EAST, transactionIDValue, -25, SpringLayout.EAST, orderPaneCen); // Right-aligned
            layout.putConstraint(SpringLayout.NORTH, transactionIDValue, 10, SpringLayout.NORTH, orderPaneCen);

            // Customer
            layout.putConstraint(SpringLayout.WEST, customerLabel, 20, SpringLayout.WEST, orderPaneCen);
            layout.putConstraint(SpringLayout.NORTH, customerLabel, 10, SpringLayout.SOUTH, transactionIDLabel);
            layout.putConstraint(SpringLayout.EAST, customerValue, -25, SpringLayout.EAST, orderPaneCen); // Right-aligned
            layout.putConstraint(SpringLayout.NORTH, customerValue, 10, SpringLayout.SOUTH, transactionIDValue);

            // Date & Time
            layout.putConstraint(SpringLayout.WEST, dateTimeLabel, 20, SpringLayout.WEST, orderPaneCen);
            layout.putConstraint(SpringLayout.NORTH, dateTimeLabel, 10, SpringLayout.SOUTH, customerLabel);
            layout.putConstraint(SpringLayout.EAST, dateTimeLabelValue, -25, SpringLayout.EAST, orderPaneCen); // Right-aligned
            layout.putConstraint(SpringLayout.NORTH, dateTimeLabelValue, 10, SpringLayout.SOUTH, customerValue);

            // Purchases
            layout.putConstraint(SpringLayout.WEST, purchasesLabel, 20, SpringLayout.WEST, orderPaneCen);
            layout.putConstraint(SpringLayout.NORTH, purchasesLabel, 20, SpringLayout.SOUTH, dateTimeLabel);


            Map<String, Integer> purchasesForTransactionPreview = getPurchases(manager.getPurchaseID(transactionID));

            int yOffset = 120;  // Adjust if necessary

            for (Map.Entry<String, Integer> entry : purchasesForTransactionPreview.entrySet()) {
                String productKey = entry.getKey();
                Integer quantity = entry.getValue();

                double price = productPrices.getOrDefault(productKey, 0.0);
                String productName = productNames.getOrDefault(productKey, "Unknown Product");

                double totalPrice = price * quantity;

                JLabel quantityLabel = new JLabel(quantity + "x");
                quantityLabel.setFont(font.getProductNameBOLD());

                JLabel productNameLabel = new JLabel(productName);
                productNameLabel.setFont(font.getProductNameBOLD());
                productNameLabel.setForeground(color.getHeader());
                productNameLabel.setPreferredSize(new Dimension(180, productNameLabel.getPreferredSize().height));
                productNameLabel.setHorizontalAlignment(SwingConstants.LEFT);

                JLabel priceLabel = new JLabel("PHP " + String.format("%,.2f", totalPrice));
                priceLabel.setFont(font.getProductNameBOLD());

                orderPaneCen.add(quantityLabel);
                orderPaneCen.add(productNameLabel);
                orderPaneCen.add(priceLabel);

                layout.putConstraint(SpringLayout.WEST, quantityLabel, 20, SpringLayout.WEST, orderPaneCen);
                layout.putConstraint(SpringLayout.NORTH, quantityLabel, yOffset, SpringLayout.NORTH, orderPaneCen);

                layout.putConstraint(SpringLayout.WEST, productNameLabel, 30, SpringLayout.EAST, quantityLabel);
                layout.putConstraint(SpringLayout.NORTH, productNameLabel, yOffset, SpringLayout.NORTH, orderPaneCen);

                layout.putConstraint(SpringLayout.EAST, priceLabel, -25, SpringLayout.EAST, orderPaneCen);
                layout.putConstraint(SpringLayout.NORTH, priceLabel, yOffset, SpringLayout.NORTH, orderPaneCen);

                yOffset += 30; // Adjust spacing between rows as needed
            }

            JLabel grandtotalLabel = new JLabel();
            grandtotalLabel.setText("Grandtotal:");
            grandtotalLabel.setFont(font.getProductNameREGULAR());

            JLabel grandtotalValue = new JLabel();
            grandtotalValue.setText("PHP " + String.format("%,.2f", manager.calculateGrandTotal(transactionID, productPrices))); // Replace `grandTotal` with your actual value
            grandtotalValue.setFont(font.getProductNameBOLD());

            JLabel amountInCashLabel = new JLabel();
            amountInCashLabel.setText("Amount in Cash:");
            amountInCashLabel.setFont(font.getProductNameREGULAR());

            JLabel amountInCashValue = new JLabel();
            amountInCashValue.setText("PHP " + String.format("%,.2f", manager.getPaidAmount(transactionID))); // Replace `amountInCash` with your actual value
            amountInCashValue.setFont(font.getProductNameBOLD());

            JLabel changeLabel = new JLabel();
            changeLabel.setText("Change:");
            changeLabel.setFont(font.getProductNameREGULAR());

            JLabel changeValue = new JLabel();
            changeValue.setText("PHP " + String.format("%,.2f", (manager.getPaidAmount(transactionID) - manager.calculateGrandTotal(transactionID, productPrices)))); // Replace `change` with your actual value
            changeValue.setFont(font.getProductNameBOLD());

            JLabel endOfPurchasesLabel = new JLabel("-- End of Purchase --");
            endOfPurchasesLabel.setFont(font.getProductNameBOLD());
            endOfPurchasesLabel.setHorizontalAlignment(SwingConstants.CENTER);

            orderPaneCen.add(grandtotalLabel);
            orderPaneCen.add(grandtotalValue);
            orderPaneCen.add(amountInCashLabel);
            orderPaneCen.add(amountInCashValue);
            orderPaneCen.add(changeLabel);
            orderPaneCen.add(changeValue);
            orderPaneCen.add(endOfPurchasesLabel);

            // Layout constraints for summary section

            int verticalSpacing = 20; // Spacing between summary rows
            int rightMargin = 25;     // Right-side margin for alignment

            // Grandtotal alignment
            layout.putConstraint(SpringLayout.EAST, grandtotalValue, -rightMargin, SpringLayout.EAST, orderPaneCen);
            layout.putConstraint(SpringLayout.NORTH, grandtotalValue, yOffset + verticalSpacing, SpringLayout.NORTH, orderPaneCen);

            layout.putConstraint(SpringLayout.EAST, grandtotalLabel, -10, SpringLayout.WEST, grandtotalValue);
            layout.putConstraint(SpringLayout.NORTH, grandtotalLabel, yOffset + verticalSpacing, SpringLayout.NORTH, orderPaneCen);

            // Amount in Cash alignment
            layout.putConstraint(SpringLayout.EAST, amountInCashValue, -rightMargin, SpringLayout.EAST, orderPaneCen);
            layout.putConstraint(SpringLayout.NORTH, amountInCashValue, yOffset + verticalSpacing * 2, SpringLayout.NORTH, orderPaneCen);

            layout.putConstraint(SpringLayout.EAST, amountInCashLabel, -10, SpringLayout.WEST, amountInCashValue);
            layout.putConstraint(SpringLayout.NORTH, amountInCashLabel, yOffset + verticalSpacing * 2, SpringLayout.NORTH, orderPaneCen);

            // Change alignment
            layout.putConstraint(SpringLayout.EAST, changeValue, -rightMargin, SpringLayout.EAST, orderPaneCen);
            layout.putConstraint(SpringLayout.NORTH, changeValue, yOffset + verticalSpacing * 3, SpringLayout.NORTH, orderPaneCen);

            layout.putConstraint(SpringLayout.EAST, changeLabel, -10, SpringLayout.WEST, changeValue);
            layout.putConstraint(SpringLayout.NORTH, changeLabel, yOffset + verticalSpacing * 3, SpringLayout.NORTH, orderPaneCen);

            layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, endOfPurchasesLabel, 0, SpringLayout.HORIZONTAL_CENTER, orderPaneCen);
            layout.putConstraint(SpringLayout.NORTH, endOfPurchasesLabel, yOffset + verticalSpacing * 5, SpringLayout.NORTH, orderPaneCen);

            orderPaneCen.revalidate();
            orderPaneCen.repaint();
        }

    }

    public static class viewSales implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    public static class menuTable implements ActionListener {
        private int buttonIndex;
        public menuTable(int buttonIndex, int length) {
            this.setButtonIndex(buttonIndex);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            List<String> categoryKeys = new ArrayList<>(categoryDataMap.keySet());

            if (buttonIndex >= 0 && buttonIndex < categoryKeys.size()) {
                String selectedCategory = categoryKeys.get(buttonIndex);
                String[][] categoryData = categoryDataMap.get(selectedCategory);

                if (categoryData != null) {
                    tableModifier(categoryData);
                } else {
                    System.out.println("No data found for category: " + selectedCategory);
                }

                for (int i = 0; i < categoryKeys.size(); i++) {
                    if (i == buttonIndex) {
                        pillShape[i].setBackground(color.getChoice());
                        pSLabel[i].setText(userOperations.toTitleCase(categoryKeys.get(i)));
                        pSLabel[i].setFont(font.getProductNameBOLD());
                        pSLabel[i].setForeground(Color.DARK_GRAY);
                    } else {
                        pillShape[i].setBackground(centerPanelMainLayer.getBackground());
                        pSLabel[i].setText(userOperations.toTitleCase(categoryKeys.get(i)));
                        pSLabel[i].setFont(font.getProductNameREGULAR());
                        pSLabel[i].setForeground(Color.GRAY);
                    }
                }
            } else {
                System.out.println("Invalid button index: " + buttonIndex);
            }
        }

        @SuppressWarnings("unused")
        public int getButtonIndex() {
            return buttonIndex;
        }

        private void setButtonIndex(int buttonIndex) {
            this.buttonIndex = buttonIndex;
        }

        public void tableModifier(String[][] menuArray) {

            JPanel mainPanelOnCenters = new JPanel();
//            mainPanelOnCenters.setBackground(mainPanelOnCenter.getBackground());
            mainPanelOnCenters.setBackground(Color.BLUE);
//            mainPanelOnCenters.setLayout(new GridLayout(0, 4, 0, 0));

            customScrollBarUI scrollBarUI2 = new customScrollBarUI();
            JScrollPane scrollPane = new JScrollPane(mainPanelOnCenters);

            scrollPane.setBorder(BorderFactory.createEmptyBorder());
            scrollPane.getVerticalScrollBar().setUI(scrollBarUI2);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

            mainPanelOnCenter.add(scrollPane);
            panelFinisher(mainPanelOnCenter);

        }

        public void panelFinisher(JPanel panel) {
            panel.revalidate();
            panel.repaint();
        }
    }

    public static class menuButtons implements ActionListener {
        private int buttonIndex;
        private String altProductCode;
        private String[][] menuArray;

        public menuButtons(int buttonIndex, String[][] menuArray, String altProductCode) {
            this.setButtonIndex(buttonIndex);
            if (menuArray == null && altProductCode == null) {
                this.setMenuArray(null);
                this.setProductCode(null);
            } else if (menuArray == null && altProductCode != null) {
                this.setMenuArray(null);
                this.setProductCode(altProductCode);
            } else {
                this.setMenuArray(menuArray);
            }
        }

        public void actionPerformed(ActionEvent e) {
            if (menuArray == null && altProductCode != null) {
                String productCode = altProductCode;
                if (orderRecord.containsKey(productCode)) {
                    int currentQuantity = orderRecord.get(productCode);
                    orderRecord.put(productCode, currentQuantity + 1);
                } else {
                    orderRecord.put(productCode, 1);
                }
                functions();
            }

            if (menuArray != null && altProductCode == null) {
                if (buttonIndex >= 0 && buttonIndex < menuArray.length) {
                    String productCode = menuArray[buttonIndex][0];
                    if (orderRecord.containsKey(productCode)) {
                        int currentQuantity = orderRecord.get(productCode);
                        orderRecord.put(productCode, currentQuantity + 1);
                    } else {
                        orderRecord.put(productCode, 1);
                    }
                    functions();
                } else {
                    System.err.println("Invalid button index for array access.");
                }
            } else if (buttonIndex == -1){
                for (int i = 0; i < cartLabelsNumbers.length; i++) {
                    cartLabelsNumbers[i].setText(formattedDefaultNo);
                }
                centerPaneOnRightPanel.removeAll();
                centerPaneOnRightPanel.repaint();
                centerPaneOnRightPanel.revalidate();

                roundedPanelForCancelButton.setBackground(Color.GRAY);
                roundedPanelForProceedButton.setBackground(Color.GRAY);
                cancelButton.setEnabled(F);
                proceedButton.setEnabled(F);
                orderRecord.clear();
                System.out.println("Order is CANCELLED");
            }
        }

        public void functions() {
            Integer[] valuesArray = orderRecord.values().toArray(new Integer[0]);
            String[] keysArray = orderRecord.keySet().toArray(new String[0]);
            int[] intArray = new int[valuesArray.length];

            int number = orderRecord.size();
            int maxCount = 9; //modify here
            if (number != 0) {
                centerPaneOnRightPanel.removeAll();
                if (number > maxCount) {
                    number = orderRecord.size();
                } else {
                    number = maxCount;
                }

                mainPanelOnCenter_ = new customRoundedPanel[number];
                quantityLabel6s = new JLabel[mainPanelOnCenter_.length];
                productName6s = new JLabel[mainPanelOnCenter_.length];
                productPrice6s = new JLabel[mainPanelOnCenter_.length];

                for (int i = 0; i < mainPanelOnCenter_.length; i++) {
                    mainPanelOnCenter_[i] = new customRoundedPanel(12);
                    mainPanelOnCenter_[i].setBorder(new EmptyBorder(8, 10, 0, 10));
                    mainPanelOnCenter_[i].setBackground(centerPaneOnRightPanel.getBackground());
                    mainPanelOnCenter_[i].setLayout(new FlowLayout(FlowLayout.LEFT, 12, 11));
                    mainPanelOnCenter_[i].setPreferredSize(new Dimension(310, 50));

                    centerPaneOnRightPanel.add(mainPanelOnCenter_[i]);

                    quantityLabel6s[i] = new JLabel();
                    quantityLabel6s[i].setText(null);
                    quantityLabel6s[i].setForeground(Color.DARK_GRAY);
                    quantityLabel6s[i].setFont(font.getProductNameREGULAR());
                    quantityLabel6s[i].setPreferredSize(new Dimension(25, 15));

                    mainPanelOnCenter_[i].add(quantityLabel6s[i]);

                    productName6s[i] = new JLabel();
                    productName6s[i].setText(null);
                    productName6s[i].setForeground(Color.DARK_GRAY);
                    productName6s[i].setFont(font.getProductNameREGULAR());
                    productName6s[i].setPreferredSize(new Dimension(170, 15));

                    mainPanelOnCenter_[i].add(productName6s[i]);

                    productPrice6s[i] = new JLabel();
                    productPrice6s[i].setText(null);
                    productPrice6s[i].setForeground(Color.DARK_GRAY);
                    productPrice6s[i].setFont(font.getProductNameREGULAR());
                    productPrice6s[i].setHorizontalAlignment(SwingConstants.RIGHT);
                    productPrice6s[i].setPreferredSize(new Dimension(60, 15));
                }

                String[] productPrice = userOperations.getPricesArray(keysArray);
                for (int i = 0; i < orderRecord.size(); i++) {

                    intArray[i] = valuesArray[i];

                    Color panelColor = (i % 2 == 0) ? Color.WHITE : color.getRightSide();
                    mainPanelOnCenter_[i].setBackground(panelColor);

                    mainPanelOnCenter_[i].add(productPrice6s[i]);
                    quantityLabel6s[i].setText(String.valueOf(intArray[i]));
                    productName6s[i].setText(userOperations.getProductName(keysArray[i]));
                    productPrice6s[i].setText("₱" + productPrice[i] + ".00");
                }

                cancelButton.setEnabled(T);
                proceedButton.setEnabled(T);

                roundedPanelForCancelButton.setBackground(color.getLeftSide());
                roundedPanelForProceedButton.setBackground(color.getLeftSide());

                centerPaneOnRightPanel.repaint();
                centerPaneOnRightPanel.revalidate();

                sidePanelPaymentHandling(cartLabelsNumbers);
            }
        }

        public void sidePanelPaymentHandling(JLabel[] labelArray) {
            double finalPrice = 0.0;
            double taxRate = 0.0;
            double subtotal = 0.0;
            double grandTotal = 0.0;

            for (String productCode : orderRecord.keySet()) {
                int quantity = orderRecord.get(productCode);
                try {
                    double price = Double.parseDouble(userOperations.getProductPrice(productCode));

                    double productTax = (salesTax / 100) * price;
                    subtotal = price * quantity;
                    finalPrice += subtotal;
                    taxRate += productTax;
                } catch (NumberFormatException | NullPointerException e) {
                    System.err.println("Error processing product: " + productCode);
                    e.printStackTrace();
                }
            }

            grandTotal = finalPrice + taxRate;

            String subTotal = String.format("%.2f", finalPrice);
            String totalTaxAmount = String.format("%.2f", taxRate);
            String payableAmount = String.format("%.2f", grandTotal);

            calculations = new String[3];
            saveStringToArray(calculations, subTotal);
            saveStringToArray(calculations, totalTaxAmount);
            saveStringToArray(calculations, payableAmount);

            labelArray[0].setText("₱" + subTotal);
            labelArray[1].setText("₱" + totalTaxAmount);
            labelArray[2].setText("₱" + payableAmount);
        }

        public void saveStringToArray(String[] array, String value) {
            for (int i = 0; i < array.length; i++) {
                if (array[i] == null) {
                    array[i] = value;
                    return;
                }
            }
            System.out.println("Array is full. Cannot save: " + value);
        }

        @SuppressWarnings("unused")
        public int getButtonIndex() {
            return buttonIndex;
        }
        public void setButtonIndex(int buttonIndex) {
            this.buttonIndex = buttonIndex;
        }

        @SuppressWarnings("unused")
        public String[][] getMenuArray() {
            return menuArray;
        }
        public void setMenuArray(String[][] menuArray) {
            this.menuArray = menuArray;
        }

        private void setProductCode(String productCode) {
            this.altProductCode = productCode;
        }
    }

    static class sideButtons implements ActionListener { //for SideBar buttons
        private int buttonIndex;
        private String buttonName;

        public sideButtons(int buttonIndex, String buttonName) {
            this.setButtonIndex(buttonIndex);
            this.setButtonName(buttonName);
        }

        public void actionPerformed(ActionEvent e) {
            switch (buttonIndex) {
                case 0 -> {
                    adminSideButtonFunctions.homeButtonToggle();
                    adminOperations.buttonColorReset(sideRibbonRoundedPanels, buttonIndex, color.getInactiveButton(), color.getChoice());
                }
                case 1 -> {
                    adminSideButtonFunctions.transactionHistoryButtonToggle();
                    adminOperations.buttonColorReset(sideRibbonRoundedPanels, buttonIndex, color.getInactiveButton(), color.getChoice());
                }
                case 2 -> {
                    adminSideButtonFunctions.salesButtonToggle();
                    adminOperations.buttonColorReset(sideRibbonRoundedPanels, buttonIndex, color.getInactiveButton(), color.getChoice());
                }
                case 3 -> {
                    adminSideButtonFunctions.inventoryButtonToggle();
                    adminOperations.buttonColorReset(sideRibbonRoundedPanels, buttonIndex, color.getInactiveButton(), color.getChoice());
                }
                case 4 -> {
                    adminSideButtonFunctions.categoriesButtonToggle();
                    adminOperations.buttonColorReset(sideRibbonRoundedPanels, buttonIndex, color.getInactiveButton(), color.getChoice());
                }
            }
        }

        @SuppressWarnings("unused")
        public int getButtonIndex() {
            return buttonIndex;
        }
        public void setButtonIndex(int buttonIndex) {
            this.buttonIndex = buttonIndex;
        }

        @SuppressWarnings("unused")
        public String getButtonName() {
            return buttonName;
        }

        public void setButtonName(String buttonName) {
            this.buttonName = buttonName;
        }
    }

    public static class keyListen implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {
            searchBox.setText("");
            searchBox.removeKeyListener(this);
            searchBox.setForeground(Color.DARK_GRAY);
            //addition: Recent Searches and Recommendations
        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_A) {
                searchBox.setText("");
                searchBox.setCaretPosition(0);
            }
            if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) {
                e.consume();
                searchBox.setCaretPosition(0);
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            searchBox.setText("Search products...");
            searchBox.setCaretPosition(0);
            searchBox.addKeyListener(this);
        }
    }

    static class documentListen implements DocumentListener {
        @Override
        public void insertUpdate(DocumentEvent e) {
            adminSearchAlgo.whenSearched();
            userOperations.searchProducts(searchBox.getText());
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            adminSearchAlgo.whenSearched();
            userOperations.searchProducts(searchBox.getText());
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            adminSearchAlgo.whenSearched();
            userOperations.searchProducts(searchBox.getText());
        }
    }

    static class focusListen implements FocusListener {
        @Override
        public void focusGained(FocusEvent e) {
            searchBox.setCaretPosition(0);
        }

        @Override
        public void focusLost(FocusEvent e) {
            searchBox.addKeyListener(new adminActionManager.keyListen());
            searchBox.setForeground(Color.GRAY);
        }
    }
}
