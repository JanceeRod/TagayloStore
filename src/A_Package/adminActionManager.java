package A_Package;

import G_Package.customColorPallete;
import G_Package.customRoundedPanel;
import G_Package.customScrollBarUI;
import B_Package.userOperations;
import G_Package.customSwingCreate;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static A_Package.adminOperations.getJTextField;
import static A_Package.adminOperations.panelFinisher;
import static C_Package.manageCategories.addProductToCategory;
import static C_Package.manageCategories.removeProductFromCategory;
import static T_Package.TransactionManager.getPurchases;
import static javax.swing.SwingConstants.CENTER;

public class adminActionManager extends adminDefinitions {

    public static class forRemoveProductButton implements ActionListener {
        private String category;

        public forRemoveProductButton(String category) {
            this.setCategory(category);
        }

        private void setCategory(String category) {
            this.category = category;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Remove Product button clicked.");

            // Retrieve the product code from the text field
            String productCode = forProductCodeTextBox.getText().trim();

            // Check if the product code is empty
            if (productCode.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Product code cannot be empty!", "Input Error", JOptionPane.ERROR_MESSAGE);
                return; // Exit if no product code is entered
            }

            // Remove the product from the category using the removeProductFromCategory method
            boolean isRemoved = removeProductFromCategory(inventoryCategoryDataMap, category, productCode);

            // Check if the product was successfully removed and show a corresponding message
            if (isRemoved) {
                JOptionPane.showMessageDialog(null, "Product has been removed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Product with the specified code does not exist!", "Removal Error", JOptionPane.ERROR_MESSAGE);
            }

            // Refresh the inventory table or data display
            String[][] categoryData = inventoryCategoryDataMap.get(category);
            if (categoryData != null) {
                inventoryTable.tableModifier(category, categoryData);
                System.out.println("Table updated for category: " + category);
            } else {
                System.out.println("No data found for category: " + category);
            }

            // Optionally, clear the text field after removal
            forProductCodeTextBox.setText("");
        }

        public static boolean removeProductFromCategory(Map<String, String[][]> inventoryCategoryDataMap, String category, String productCode) {
            if (!inventoryCategoryDataMap.containsKey(category)) {
                System.out.println("Category " + category + " does not exist.");
                return false; // Category doesn't exist
            }

            String[][] currentData = inventoryCategoryDataMap.get(category);
            for (int i = 0; i < currentData.length; i++) {
                if (currentData[i][0].equals(productCode)) { // Assuming product code is in the first index
                    // Create a new array excluding the product to be removed
                    String[][] updatedData = new String[currentData.length - 1][];
                    for (int j = 0, k = 0; j < currentData.length; j++) {
                        if (j != i) {
                            updatedData[k++] = currentData[j];
                        }
                    }
                    inventoryCategoryDataMap.put(category, updatedData); // Update the map
                    System.out.println("Product removed from category \"" + category + "\": Code: " + productCode);
                    return true; // Product was successfully removed
                }
            }

            System.out.println("Product with code " + productCode + " does not exist in category " + category);
            return false; // Product was not found
        }

    }



    public static class forAddProductButton implements ActionListener {
        private String category;

        public forAddProductButton(String category) {
            this.setCategory(category);
        }

        private void setCategory(String category) {
            this.category = category;
        }

        String[] newProduct;

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Add Product button clicked.");

            String productCode = forProductCodeTextBox.getText().trim();
            String productName = forProductNameTextBox.getText().trim();
            String productPrice = forProductPriceTextBox.getText().trim();
            String productImage = "default.png";

            if (productCode.isEmpty() || productName.isEmpty() || productPrice.isEmpty()) {
                JOptionPane.showMessageDialog(
                        null,
                        "All fields must be filled out before adding the product!",
                        "Input Error",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            if (isProductExist(productName, productCode)) {
                JOptionPane.showMessageDialog(
                        null,
                        "A product with this code or name already exists!",
                        "Duplicate Product Error",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            newProduct = new String[]{productCode, productName, productPrice, productImage};

            addProductToCategory(inventoryCategoryDataMap, category, newProduct);
            System.out.println("Product added successfully!");

            JOptionPane.showMessageDialog(
                    null,
                    "Product has been added successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            System.out.println("Updated inventory:");
            inventoryTable.printInventoryCategoryDataMap(inventoryCategoryDataMap);

            forProductCodeTextBox.setText("");
            forProductNameTextBox.setText("");
            forProductPriceTextBox.setText("");

            productCode = "";
            productName = "";
            productPrice = "";

            System.out.println("Text fields cleared and ready for next input.");

            String[][] categoryData = inventoryCategoryDataMap.get(category);
            if (categoryData != null) {
                inventoryTable.tableModifier(category, categoryData);
                System.out.println("Table updated for category: " + category);
            } else {
                System.out.println("No data found for category: " + category);
            }

            panelFinisher(centerContainerPanelUp);
            panelFinisher(orderPaneCen);
        }

        private boolean isProductExist(String name, String code) {
            for (String[] product : inventoryCategoryDataMap.get(category)) {
                if (product[0].equals(code) || product[1].equals(name)) {
                    return true;
                }
            }
            return false;
        }
    }


    public static class inventoryTable implements ActionListener {

        private int buttonIndex;

        public inventoryTable (int buttonIndex, int length) {
            this.setButtonIndex(buttonIndex);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            List<String> categoryKeys = new ArrayList<>(inventoryCategoryDataMap.keySet());

            if (buttonIndex >= 0 && buttonIndex < categoryKeys.size()) {
                String selectedCategory = categoryKeys.get(buttonIndex);
//				String formattedCategory = Operations.toTitleCase(selectedCategory);
                String[][] categoryData = inventoryCategoryDataMap.get(selectedCategory);

                if (categoryData != null) {
                    tableModifier(selectedCategory, categoryData);
                } else {
                    System.out.println("No data found for category: " + selectedCategory);
                }

                for (int i = 0; i < categoryKeys.size(); i++) {
                    if (i == buttonIndex) {
                        pillShapeButtonTabs[i].setBackground(color.getChoice());
                        pillShapeButtonTabsLabel[i].setText(userOperations.toTitleCase(categoryKeys.get(i)));
                        pillShapeButtonTabsLabel[i].setFont(font.getProductNameBOLD());
                        pillShapeButtonTabsLabel[i].setForeground(Color.white);
                    } else {
                        pillShapeButtonTabs[i].setBackground(centerPanelMainLayer.getBackground());
                        pillShapeButtonTabsLabel[i].setText(userOperations.toTitleCase(categoryKeys.get(i)));
                        pillShapeButtonTabsLabel[i].setFont(font.getProductNameREGULAR());
                        pillShapeButtonTabsLabel[i].setForeground(customColorPallete.medyo_black);
                    }
                }
            } else {
                System.out.println("Invalid button index: " + buttonIndex);
            }
        }

        private void setButtonIndex(int buttonIndex) {
            this.buttonIndex = buttonIndex;
        }


        static SpringLayout forMainPanelOnCenters = new SpringLayout();
        static SpringLayout forPerProductPanel = new SpringLayout();

        public static void tableModifier(String category, String[][] menuArray) {
            mainPanelOnCenter.removeAll();

            mainPanelOnCenter.setBackground(color.getCenterPane());
            mainPanelOnCenter.setLayout(new BorderLayout());

            int orderWidth = centerPanelMainLayer.getWidth();
            int orderHeight = 58;

            int size = menuArray.length;

            JPanel mainPanelOnCenters = new JPanel();
            mainPanelOnCenters.setBackground(mainPanelOnCenter.getBackground());
            mainPanelOnCenters.setLayout(forMainPanelOnCenters);
            mainPanelOnCenters.setPreferredSize(new Dimension(orderWidth, size * (orderHeight + 10)));

            mainPanelOnCenter.add(mainPanelOnCenters, BorderLayout.CENTER);

            customRoundedPanel[] perProductPanel = new customRoundedPanel[size];
            JLabel[] perProduct_productCode = new JLabel[size];
            JLabel[] perProduct_productName = new JLabel[size];
            JLabel[] perProduct_productPrice = new JLabel[size];

            for (int i = 0; i < size; i++) {
                perProductPanel[i] = new customRoundedPanel(25);
                perProductPanel[i].setBackground(Color.WHITE);
                perProductPanel[i].setBorder(new EmptyBorder(1, 0, 1, 10));
                perProductPanel[i].setLayout(forPerProductPanel);
                perProductPanel[i].setPreferredSize(new Dimension(555, orderHeight));

                ImageIcon imageIcon = new ImageIcon("images/products/" + menuArray[i][3]);

                Image image = imageIcon.getImage();
                Image resizedImage = image.getScaledInstance(48, 48, Image.SCALE_SMOOTH);
                ImageIcon resizedIcon = new ImageIcon(resizedImage);

                JLabel imageLabel = new JLabel(resizedIcon);
                imageLabel.setPreferredSize(new Dimension(89, 89));

                perProduct_productCode[i] = new JLabel();
                perProduct_productCode[i].setText(menuArray[i][0]);
                perProduct_productCode[i].setFont(font.getProductNameREGULAR());
                perProduct_productCode[i].setForeground(color.getHeader());

                perProduct_productName[i] = new JLabel();
                perProduct_productName[i].setText(menuArray[i][1]);
                perProduct_productName[i].setFont(font.getProductNameBOLD());
                perProduct_productName[i].setForeground(color.getHeader());

                perProduct_productPrice[i] = new JLabel();
                perProduct_productPrice[i].setText("PHP " + menuArray[i][2] + ".00");
                perProduct_productPrice[i].setFont(font.getProductPriceBOLD());
                perProduct_productPrice[i].setForeground(color.getHeader());

                perProductPanel[i].add(imageLabel);
                perProductPanel[i].add(perProduct_productCode[i]);
                perProductPanel[i].add(perProduct_productName[i]);
                perProductPanel[i].add(perProduct_productPrice[i]);

                forPerProductPanel.putConstraint(SpringLayout.WEST, imageLabel, 5, SpringLayout.WEST, perProductPanel[i]);
                forPerProductPanel.putConstraint(SpringLayout.VERTICAL_CENTER, imageLabel, 0, SpringLayout.VERTICAL_CENTER, perProductPanel[i]);

                forPerProductPanel.putConstraint(SpringLayout.WEST, perProduct_productCode[i], 90, SpringLayout.WEST, perProductPanel[i]);
                forPerProductPanel.putConstraint(SpringLayout.NORTH, perProduct_productCode[i], 10, SpringLayout.NORTH, perProductPanel[i]);

                forPerProductPanel.putConstraint(SpringLayout.WEST, perProduct_productName[i], 90, SpringLayout.WEST, perProductPanel[i]);
                forPerProductPanel.putConstraint(SpringLayout.NORTH, perProduct_productName[i], 30, SpringLayout.NORTH, perProductPanel[i]);

                forPerProductPanel.putConstraint(SpringLayout.EAST, perProduct_productPrice[i], -20, SpringLayout.EAST, perProductPanel[i]);
                forPerProductPanel.putConstraint(SpringLayout.NORTH, perProduct_productPrice[i], 15, SpringLayout.NORTH, perProductPanel[i]);

                mainPanelOnCenters.add(perProductPanel[i]);
            }

            for (int i = 0; i < size; i++) {
                if (i == 0) {
                    forMainPanelOnCenters.putConstraint(SpringLayout.NORTH, perProductPanel[i], 10, SpringLayout.NORTH, mainPanelOnCenters);
                } else {
                    forMainPanelOnCenters.putConstraint(SpringLayout.NORTH, perProductPanel[i], 10, SpringLayout.SOUTH, perProductPanel[i - 1]);
                }
                forMainPanelOnCenters.putConstraint(SpringLayout.WEST, perProductPanel[i], 10, SpringLayout.WEST, mainPanelOnCenters);
                forMainPanelOnCenters.putConstraint(SpringLayout.EAST, perProductPanel[i], -10, SpringLayout.EAST, mainPanelOnCenters);
            }

            customScrollBarUI scrollBarUI2 = new customScrollBarUI();
            scrollBarUI2.setCustomUI(color.getHeader(), Color.GREEN, color.getCenterPane());

            JScrollPane scrollPane = new JScrollPane(mainPanelOnCenters);

            scrollPane.setBorder(BorderFactory.createEmptyBorder());
            scrollPane.getVerticalScrollBar().setUI(scrollBarUI2);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

            mainPanelOnCenter.add(scrollPane, BorderLayout.CENTER);

            panelFinisher(mainPanelOnCenter);

            rightPanel(category, menuArray);
        }

        public static void printInventoryCategoryDataMap(Map<String, String[][]> inventoryCategoryDataMap) {
            if (inventoryCategoryDataMap == null || inventoryCategoryDataMap.isEmpty()) {
                System.out.println("The inventoryCategoryDataMap is empty or null.");
                return;
            }

            for (Map.Entry<String, String[][]> entry : inventoryCategoryDataMap.entrySet()) {
                String categoryName = entry.getKey();
                String[][] data = entry.getValue();

                System.out.println("Category: " + categoryName);

                if (data == null || data.length == 0) {
                    System.out.println("  No data available for this category.");
                    continue;
                }

                for (int i = 0; i < data.length; i++) {
                    System.out.print("  Row " + i + ": ");
                    if (data[i] == null) {
                        System.out.println("null");
                        continue;
                    }
                    for (int j = 0; j < data[i].length; j++) {
                        System.out.print(data[i][j] + (j < data[i].length - 1 ? ", " : ""));
                    }
                    System.out.println();
                }
            }
        }


        public static void rightPanel(String category, String[][] menuArray) {
            orderPaneCen.removeAll();
            orderPaneCen.setLayout(new BorderLayout());

            orderPaneLabel.setText(category);

            SpringLayout SL = new SpringLayout();

            JPanel forAddRemoveFeature = new JPanel();
            forAddRemoveFeature.setBackground(orderPaneCen.getBackground());
            forAddRemoveFeature.setBorder(new EmptyBorder(20,12,0,12));
            forAddRemoveFeature.setLayout(SL);

            //----

            JLabel forProductNameLabel = new JLabel("Product Name:");
            forProductNameLabel.setFont(font.getProductNameREGULAR());

            customRoundedPanel productNameTextFieldPanel = customSwingCreate.createCustomRoundedPanel(20, 0, 15, 1, 15, color.getSearch(), new BorderLayout());

            forProductNameTextBox = getJTextField("", 250, productNameTextFieldPanel);
            productNameTextFieldPanel.add(forProductNameTextBox);

            //----

            JLabel forProductCodeLabel = new JLabel("Code:");
            forProductCodeLabel.setFont(font.getProductNameREGULAR());

            customRoundedPanel productCodeTextFieldPanel = customSwingCreate.createCustomRoundedPanel(20, 0, 15, 1, 15, color.getSearch(), new BorderLayout());

            forProductCodeTextBox = getJTextField("", 50, productCodeTextFieldPanel);
            productCodeTextFieldPanel.add(forProductCodeTextBox);

            //----

            JLabel forProductPriceLabel = new JLabel("Price:");
            forProductPriceLabel.setFont(font.getProductNameREGULAR());

            customRoundedPanel productPriceTextFieldPanel = customSwingCreate.createCustomRoundedPanel(20, 0, 15, 1, 15, color.getSearch(), new BorderLayout());

            forProductPriceTextBox = getJTextField("", 100, productPriceTextFieldPanel);
            productPriceTextFieldPanel.add(forProductPriceTextBox);

            //----

            JButton forImageFetchButton = new JButton("Select an Image File");
            forImageFetchButton.setFont(font.getProductNameBOLD());
            forImageFetchButton.setBorder(new EmptyBorder(0,0,0,0));
            forImageFetchButton.setBackground(color.getCenterPiece());
            forImageFetchButton.setFocusPainted(F);

            customRoundedPanel imageFetchButtonPanel = customSwingCreate.createCustomRoundedPanel(20, 0, 15, 1, 15, forImageFetchButton.getBackground(), new BorderLayout());
            imageFetchButtonPanel.setPreferredSize(productCodeTextFieldPanel.getPreferredSize());
            imageFetchButtonPanel.add(forImageFetchButton);

            forAddRemoveFeature.add(imageFetchButtonPanel);

            SL.putConstraint(SpringLayout.WEST, imageFetchButtonPanel, 10, SpringLayout.WEST, forAddRemoveFeature);
            SL.putConstraint(SpringLayout.EAST, imageFetchButtonPanel, -10, SpringLayout.EAST, forAddRemoveFeature);
            SL.putConstraint(SpringLayout.NORTH, imageFetchButtonPanel, 20, SpringLayout.SOUTH, productCodeTextFieldPanel);


            // Constraints for Product Name label and text field
            SL.putConstraint(SpringLayout.WEST, forProductNameLabel, 10, SpringLayout.WEST, forAddRemoveFeature);
            SL.putConstraint(SpringLayout.NORTH, forProductNameLabel, 10, SpringLayout.NORTH, forAddRemoveFeature);

            SL.putConstraint(SpringLayout.WEST, productNameTextFieldPanel, 10, SpringLayout.WEST, forAddRemoveFeature);
            SL.putConstraint(SpringLayout.NORTH, productNameTextFieldPanel, 5, SpringLayout.SOUTH, forProductNameLabel);
            SL.putConstraint(SpringLayout.EAST, productNameTextFieldPanel, -10, SpringLayout.EAST, forAddRemoveFeature);

            // Constraints for Product Code label and text field
            SL.putConstraint(SpringLayout.WEST, forProductCodeLabel, 10, SpringLayout.WEST, forAddRemoveFeature);
            SL.putConstraint(SpringLayout.NORTH, forProductCodeLabel, 20, SpringLayout.SOUTH, productNameTextFieldPanel);

            SL.putConstraint(SpringLayout.WEST, productCodeTextFieldPanel, 10, SpringLayout.WEST, forAddRemoveFeature);
            SL.putConstraint(SpringLayout.NORTH, productCodeTextFieldPanel, 5, SpringLayout.SOUTH, forProductCodeLabel);
            SL.putConstraint(SpringLayout.EAST, productCodeTextFieldPanel, -250, SpringLayout.EAST, forAddRemoveFeature);

            // Constraints for Product Price label and text field
            SL.putConstraint(SpringLayout.WEST, forProductPriceLabel, 30, SpringLayout.EAST, productCodeTextFieldPanel);
            SL.putConstraint(SpringLayout.NORTH, forProductPriceLabel, 0, SpringLayout.NORTH, forProductCodeLabel);

            SL.putConstraint(SpringLayout.WEST, productPriceTextFieldPanel, 30, SpringLayout.EAST, productCodeTextFieldPanel);
            SL.putConstraint(SpringLayout.NORTH, productPriceTextFieldPanel, 5, SpringLayout.SOUTH, forProductPriceLabel);
            SL.putConstraint(SpringLayout.EAST, productPriceTextFieldPanel, -10, SpringLayout.EAST, forAddRemoveFeature);

            forAddRemoveFeature.add(forProductNameLabel);
            forAddRemoveFeature.add(productNameTextFieldPanel);
            forAddRemoveFeature.add(forProductCodeLabel);
            forAddRemoveFeature.add(productCodeTextFieldPanel);
            forAddRemoveFeature.add(forProductPriceLabel);
            forAddRemoveFeature.add(productPriceTextFieldPanel);

            JLabel removeProductLabel = new JLabel("Remove Product");
            removeProductLabel.setFont(font.getProductNameBOLD());
            removeProductLabel.setHorizontalAlignment(CENTER);
            removeProductLabel.setForeground(Color.WHITE);

            customRoundedPanel removeProductPanel = customSwingCreate.createCustomRoundedPanel(25, 0,0,0,0, color.getCenterPiece(), new BorderLayout());
            removeProductPanel.add(removeProductLabel, BorderLayout.CENTER);

            removeProductFeature = new JButton();
            removeProductFeature.setPreferredSize(new Dimension(155, 40));
            removeProductFeature.setBackground(forAddRemoveFeature.getBackground());
            removeProductFeature.setBorder(BorderFactory.createEmptyBorder());
            removeProductFeature.setLayout(new GridLayout(1,1));
            removeProductFeature.setFocusPainted(F);
            removeProductFeature.setEnabled(T);
            removeProductFeature.add(removeProductPanel);
            removeProductFeature.addActionListener(new adminActionManager.forRemoveProductButton(category));

            JLabel addProductLabel = new JLabel("Add Product");
            addProductLabel.setFont(font.getProductNameBOLD());
            addProductLabel.setHorizontalAlignment(CENTER);
            addProductLabel.setForeground(Color.WHITE);

            customRoundedPanel addProductPanel = customSwingCreate.createCustomRoundedPanel(25, 0,0,0,0, color.getHeader(), new BorderLayout());
            addProductPanel.add(addProductLabel, BorderLayout.CENTER);

            addProductFeature = new JButton();
            addProductFeature.setPreferredSize(new Dimension(155, 40));
            addProductFeature.setBackground(forAddRemoveFeature.getBackground());
            addProductFeature.setBorder(BorderFactory.createEmptyBorder());
            addProductFeature.setLayout(new GridLayout(1,1));
            addProductFeature.setFocusPainted(F);
            addProductFeature.setEnabled(T);
            addProductFeature.add(addProductPanel);
            addProductFeature.addActionListener(new adminActionManager.forAddProductButton(category));

            JPanel forAddRemoveButtons = new JPanel();
            forAddRemoveButtons.setLayout(new BorderLayout());
            forAddRemoveButtons.setBackground(forAddRemoveFeature.getBackground());
            forAddRemoveButtons.add(removeProductFeature, BorderLayout.WEST);
            forAddRemoveButtons.add(addProductFeature, BorderLayout.EAST);

            forAddRemoveFeature.add(forAddRemoveButtons);

            SL.putConstraint(SpringLayout.WEST, forAddRemoveButtons, 10, SpringLayout.WEST, forAddRemoveFeature);
            SL.putConstraint(SpringLayout.EAST, forAddRemoveButtons, -10, SpringLayout.EAST, forAddRemoveFeature);
            SL.putConstraint(SpringLayout.SOUTH, forAddRemoveButtons, -10, SpringLayout.SOUTH, forAddRemoveFeature);

            orderPaneCen.add(forAddRemoveFeature, BorderLayout.CENTER);

            panelFinisher(orderPaneCen);
        }
    }

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

            layout.putConstraint(SpringLayout.WEST, transactionIDLabel, 20, SpringLayout.WEST, orderPaneCen);
            layout.putConstraint(SpringLayout.NORTH, transactionIDLabel, 10, SpringLayout.NORTH, orderPaneCen);
            layout.putConstraint(SpringLayout.EAST, transactionIDValue, -25, SpringLayout.EAST, orderPaneCen); // Right-aligned
            layout.putConstraint(SpringLayout.NORTH, transactionIDValue, 10, SpringLayout.NORTH, orderPaneCen);

            layout.putConstraint(SpringLayout.WEST, customerLabel, 20, SpringLayout.WEST, orderPaneCen);
            layout.putConstraint(SpringLayout.NORTH, customerLabel, 10, SpringLayout.SOUTH, transactionIDLabel);
            layout.putConstraint(SpringLayout.EAST, customerValue, -25, SpringLayout.EAST, orderPaneCen); // Right-aligned
            layout.putConstraint(SpringLayout.NORTH, customerValue, 10, SpringLayout.SOUTH, transactionIDValue);

            layout.putConstraint(SpringLayout.WEST, dateTimeLabel, 20, SpringLayout.WEST, orderPaneCen);
            layout.putConstraint(SpringLayout.NORTH, dateTimeLabel, 10, SpringLayout.SOUTH, customerLabel);
            layout.putConstraint(SpringLayout.EAST, dateTimeLabelValue, -25, SpringLayout.EAST, orderPaneCen); // Right-aligned
            layout.putConstraint(SpringLayout.NORTH, dateTimeLabelValue, 10, SpringLayout.SOUTH, customerValue);

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
                quantityLabel.setPreferredSize(new Dimension(20, quantityLabel.getPreferredSize().height));
                quantityLabel.setHorizontalAlignment(SwingConstants.LEFT);

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
            endOfPurchasesLabel.setForeground(Color.GRAY);
            endOfPurchasesLabel.setFont(font.getProductNameREGULAR());
            endOfPurchasesLabel.setHorizontalAlignment(SwingConstants.CENTER);

            orderPaneCen.add(grandtotalLabel);
            orderPaneCen.add(grandtotalValue);
            orderPaneCen.add(amountInCashLabel);
            orderPaneCen.add(amountInCashValue);
            orderPaneCen.add(changeLabel);
            orderPaneCen.add(changeValue);
            orderPaneCen.add(endOfPurchasesLabel);


            int verticalSpacing = 20; // Spacing between summary rows
            int rightMargin = 25;     // Right-side margin for alignment

            layout.putConstraint(SpringLayout.EAST, grandtotalValue, -rightMargin, SpringLayout.EAST, orderPaneCen);
            layout.putConstraint(SpringLayout.NORTH, grandtotalValue, yOffset + verticalSpacing, SpringLayout.NORTH, orderPaneCen);

            layout.putConstraint(SpringLayout.EAST, grandtotalLabel, -10, SpringLayout.WEST, grandtotalValue);
            layout.putConstraint(SpringLayout.NORTH, grandtotalLabel, yOffset + verticalSpacing, SpringLayout.NORTH, orderPaneCen);

            layout.putConstraint(SpringLayout.EAST, amountInCashValue, -rightMargin, SpringLayout.EAST, orderPaneCen);
            layout.putConstraint(SpringLayout.NORTH, amountInCashValue, yOffset + verticalSpacing * 2, SpringLayout.NORTH, orderPaneCen);

            layout.putConstraint(SpringLayout.EAST, amountInCashLabel, -10, SpringLayout.WEST, amountInCashValue);
            layout.putConstraint(SpringLayout.NORTH, amountInCashLabel, yOffset + verticalSpacing * 2, SpringLayout.NORTH, orderPaneCen);

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
            List<String> categoryKeys = new ArrayList<>(inventoryCategoryDataMap.keySet());

            if (buttonIndex >= 0 && buttonIndex < categoryKeys.size()) {
                String selectedCategory = categoryKeys.get(buttonIndex);
                String[][] categoryData = inventoryCategoryDataMap.get(selectedCategory);

                if (categoryData != null) {
                    tableModifier(categoryData);
                } else {
                    System.out.println("No data found for category: " + selectedCategory);
                }

                for (int i = 0; i < categoryKeys.size(); i++) {
                    if (i == buttonIndex) {
                        pillShapeButtonTabs[i].setBackground(color.getChoice());
                        pillShapeButtonTabsLabel[i].setText(userOperations.toTitleCase(categoryKeys.get(i)));
                        pillShapeButtonTabsLabel[i].setFont(font.getProductNameBOLD());
                        pillShapeButtonTabsLabel[i].setForeground(Color.DARK_GRAY);
                    } else {
                        pillShapeButtonTabs[i].setBackground(centerPanelMainLayer.getBackground());
                        pillShapeButtonTabsLabel[i].setText(userOperations.toTitleCase(categoryKeys.get(i)));
                        pillShapeButtonTabsLabel[i].setFont(font.getProductNameREGULAR());
                        pillShapeButtonTabsLabel[i].setForeground(Color.GRAY);
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
                addProductFeature.setEnabled(F);
                removeProductFeature.setEnabled(F);
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

                addProductFeature.setEnabled(T);
                removeProductFeature.setEnabled(T);

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
                    adminSideButtonFunctions.inventoryButtonToggle();
                    inventoryTabsButtons[0].doClick();
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
