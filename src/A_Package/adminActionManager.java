package A_Package;

import G_Package.customRoundedPanel;
import G_Package.customScrollBarUI;
import M_Package.Operations;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class adminActionManager extends adminDefinitions {

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
                        pSLabel[i].setText(Operations.toTitleCase(categoryKeys.get(i)));
                        pSLabel[i].setFont(font.getProductNameBOLD());
                        pSLabel[i].setForeground(Color.DARK_GRAY);
                    } else {
                        pillShape[i].setBackground(centerPanelMainLayer.getBackground());
                        pSLabel[i].setText(Operations.toTitleCase(categoryKeys.get(i)));
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

                String[] productPrice = Operations.getPricesArray(keysArray);
                for (int i = 0; i < orderRecord.size(); i++) {

                    intArray[i] = valuesArray[i];

                    Color panelColor = (i % 2 == 0) ? Color.WHITE : color.getRightSide();
                    mainPanelOnCenter_[i].setBackground(panelColor);

                    mainPanelOnCenter_[i].add(productPrice6s[i]);
                    quantityLabel6s[i].setText(String.valueOf(intArray[i]));
                    productName6s[i].setText(Operations.getProductName(keysArray[i]));
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
                    double price = Double.parseDouble(Operations.getProductPrice(productCode));

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
            Operations.searchProducts(searchBox.getText());
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            adminSearchAlgo.whenSearched();
            Operations.searchProducts(searchBox.getText());
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            adminSearchAlgo.whenSearched();
            Operations.searchProducts(searchBox.getText());
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
