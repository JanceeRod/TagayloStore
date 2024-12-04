package B_Package;

import G_Package.customRoundedPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;

import static B_Package.userOperations.*;
import static B_Package.userSideButtonFunctions.homeButtonToggle;

public class userActionManager extends userDefinitions {

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
                    homeButtonToggle();
                    button2_[0].doClick();
                    buttonColorReset(sideRibbonRoundedPanels, buttonIndex, color.getInactiveButton(), color.getChoice());
                }
            }
        }

        public void setButtonIndex(int buttonIndex) {
            this.buttonIndex = buttonIndex;
        }

        public void setButtonName(String buttonName) {
            this.buttonName = buttonName;
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
                    orderRecord.compute(productCode, (k, currentQuantity) -> currentQuantity + 1);
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
            } else if (buttonIndex == -1) {
                functions();

                centerPaneOnRightPanel.removeAll();

                numbersReset();

                panelFinisher(centerPaneOnRightPanel);
                centerPaneOnRightPanel.setPreferredSize(new Dimension(0, 0));

                scrollPane.revalidate();
                scrollPane.repaint();

                roundedPanelForCancelButton.setBackground(color.getRightSide());
                roundedPanelForProceedButton.setBackground(color.getRightSide());

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

            if (number != 0) {

                centerPaneOnRightPanel.removeAll();

                productsOnCartPanel = new customRoundedPanel[number];
                quantityLabel6s = new JLabel[number];
                productName6s = new JLabel[number];
                productPrice6s = new JLabel[number];

                cancelProductPurchase = new JButton[number];

                int topMargin = 10;
                int verticalSpacing = 0;

                String[] productPrice = userOperations.getPricesArray(keysArray);

                for (int i = 0; i < orderRecord.size(); i++) {

                    Color panelColor = (i % 2 == 0) ? Color.WHITE : color.getRightSide();
                    intArray[i] = valuesArray[i];

                    productsOnCartPanel[i] = new customRoundedPanel(25);
                    productsOnCartPanel[i].setBorder(new EmptyBorder(8, 10, 0, 10));
                    productsOnCartPanel[i].setBackground(panelColor);
                    productsOnCartPanel[i].setLayout(new FlowLayout(FlowLayout.LEFT, 12, 11));
                    productsOnCartPanel[i].setPreferredSize(new Dimension(320, 55));

                    quantityLabel6s[i] = new JLabel();
                    quantityLabel6s[i].setText(String.valueOf(intArray[i]));
                    quantityLabel6s[i].setForeground(color.getHeader());
                    quantityLabel6s[i].setFont(font.getProductNameREGULAR());
                    quantityLabel6s[i].setPreferredSize(new Dimension(25, 15));

                    productName6s[i] = new JLabel();
                    productName6s[i].setText(userOperations.getProductName(keysArray[i]));
                    productName6s[i].setForeground(Color.DARK_GRAY);
                    productName6s[i].setFont(font.getProductNameREGULAR());
                    productName6s[i].setPreferredSize(new Dimension(150, 15));

                    productPrice6s[i] = new JLabel();
                    productPrice6s[i].setText("₱" + productPrice[i] + ".00");
                    productPrice6s[i].setForeground(color.getHeader());
                    productPrice6s[i].setFont(font.getProductNameBOLD());
                    productPrice6s[i].setHorizontalAlignment(SwingConstants.RIGHT);
                    productPrice6s[i].setPreferredSize(new Dimension(50, 15));

                    int currentIndex = i;

                    cancelProductPurchase[i] = new JButton();
                    cancelProductPurchase[i].setPreferredSize(new Dimension(20,20));
                    cancelProductPurchase[i].setBorder(BorderFactory.createEmptyBorder());
                    cancelProductPurchase[i].setLayout(new BorderLayout());
                    cancelProductPurchase[i].setBackground(Color.GREEN);
                    cancelProductPurchase[i].setFocusPainted(F);
                    cancelProductPurchase[i].addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String productCode = keysArray[currentIndex];
                            orderRecord.remove(productCode);
                            centerPaneOnRightPanel.removeAll();

                            panelFinisher(centerPaneOnRightPanel);
                            scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMinimum());

                            numbersReset();
                            functions();
                        }
                    });

                    centerPaneOnRightPanel.add(productsOnCartPanel[i]);

                    productsOnCartPanel[i].add(quantityLabel6s[i]);
                    productsOnCartPanel[i].add(productName6s[i]);
                    productsOnCartPanel[i].add(productPrice6s[i]);
                    productsOnCartPanel[i].add(cancelProductPurchase[i]);

                    SpringLayout.Constraints constraints = SpringForCart.getConstraints(productsOnCartPanel[i]);

                    constraints.setX(Spring.constant(10));

                    if (i == 0) {
                        constraints.setY(Spring.constant(topMargin));
                    } else {
                        SpringLayout.Constraints prevConstraints = SpringForCart.getConstraints(productsOnCartPanel[i - 1]);
                        constraints.setY(Spring.sum(prevConstraints.getConstraint(SpringLayout.SOUTH), Spring.constant(verticalSpacing)));
                    }
                }


                cancelButton.setEnabled(T);
                proceedButton.setEnabled(T);

                roundedPanelForCancelButton.setBackground(color.getLeftSide());
                roundedPanelForProceedButton.setBackground(color.getLeftSide());

                adjustPreferredSize();
                panelFinisher(centerPaneOnRightPanel);

                sidePanelPaymentHandling(cartLabelsNumbers);
            }
        }

        private void adjustPreferredSize() {
            int totalHeight = 0;
            int verticalSpacing = 10;
            int topMargin = 10;

            for (Component comp : centerPaneOnRightPanel.getComponents()) {
                totalHeight += comp.getPreferredSize().height + verticalSpacing;
            }
            totalHeight += topMargin;

            centerPaneOnRightPanel.setPreferredSize(new Dimension(
                    centerPaneOnRightPanel.getWidth(), // Use current width
                    totalHeight
            ));
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

    public static class documentListen implements DocumentListener {
        @Override
        public void insertUpdate(DocumentEvent e) {
            userSearchAlgo.whenSearched();
            userOperations.searchProducts(searchBox.getText());
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            userSearchAlgo.whenSearched();
            userOperations.searchProducts(searchBox.getText());
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            userSearchAlgo.whenSearched();
            userOperations.searchProducts(searchBox.getText());
        }
    }

    public static class focusListen implements FocusListener {
        @Override
        public void focusGained(FocusEvent e) {
            searchBox.setCaretPosition(0);
        }

        @Override
        public void focusLost(FocusEvent e) {
            searchBox.addKeyListener(new keyListen());
            searchBox.setForeground(Color.GRAY);
        }
    }



}
