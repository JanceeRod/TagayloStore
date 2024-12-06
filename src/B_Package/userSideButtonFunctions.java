package B_Package;

import G_Package.customColorPallete;
import G_Package.customRoundedPanel;
import G_Package.customScrollBarUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static B_Package.userOperations.panelFinisher;
import static B_Package.userOperations.updateIndicator;

public class userSideButtonFunctions extends userDefinitions {

    public static void homeButtonToggle() {
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
            button2_[j].addActionListener(new menuTable(j, arrayLengths[j]));

            pillShape[j] = new customRoundedPanel(25);
            pillShape[j].setLayout(new GridLayout(1,1));
            button2_[j].add(pillShape[j]);

            pSLabel[j] = new JLabel();
            Set<String> arrayNames = categoryDataMap.keySet();
            if (j < arrayNames.size()) {
                String arrayName = (String) arrayNames.toArray()[j];
                pSLabel[j].setText(arrayName);
            }
            pSLabel[j].setHorizontalAlignment(SwingConstants.CENTER);
            pSLabel[j].setFont(font.getProductNameREGULAR());
            pSLabel[j].setForeground(Color.GRAY);

            pillShape[j].add(pSLabel[j]);

            centerPanelMainLayer.add(button2_[j]);
        }
        panelFinisher(centerContainerPanelUp);
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
//				String formattedCategory = Operations.toTitleCase(selectedCategory);
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
                        pSLabel[i].setForeground(Color.white);
                    } else {
                        pillShape[i].setBackground(centerPanelMainLayer.getBackground());
                        pSLabel[i].setText(userOperations.toTitleCase(categoryKeys.get(i)));
                        pSLabel[i].setFont(font.getProductNameREGULAR());
                        pSLabel[i].setForeground(customColorPallete.medyo_black);
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
            mainPanelOnCenter.removeAll();
            int length = menuArray.length;
            int lengthy = cafeInventory.size();
            int maxLength = 0;

            if (lengthy <= 20) {
                maxLength = lengthy;
            } else {
                maxLength = 50;
            }

            JPanel mainPanelOnCenters = new JPanel();
            mainPanelOnCenters.setBackground(mainPanelOnCenter.getBackground());
//            mainPanelOnCenters.setLayout(new GridLayout(0, 4, 0, 0));
            mainPanelOnCenters.setLayout(SpringForMenu);

            int leftMargin = 0; // Left margin for the first column
            int topMargin = 0;  // Top margin for the first row
            int horizontalSpacing = 0; // Space between columns
            int verticalSpacing = 0;   // Space between rows

//            int buttonWidth = 250; // Adjust as needed
//            int buttonHeight = 300; // Adjust as needed

            int columns = 4;

            panel5_ = new customRoundedPanel[maxLength];
            forPanel5_ = new JButton[maxLength];
            productName = new JLabel[maxLength];
            productPrice = new JLabel[maxLength];

            for (int i = 0; i < length; i++) {
                forPanel5_[i] = new JButton();
                forPanel5_[i].setBackground(mainPanelOnCenters.getBackground());
                forPanel5_[i].setLayout(new BorderLayout(1,1));
                forPanel5_[i].setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                forPanel5_[i].addActionListener(new userActionManager.menuButtons(i, menuArray, null));
                forPanel5_[i].setFocusPainted(F);
                forPanel5_[i].setEnabled(T);

                panel5_[i] = new customRoundedPanel(30);
                panel5_[i].setBackground(Color.WHITE);
                panel5_[i].setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
                panel5_[i].setLayout(new BorderLayout(1,1));

                String imagePath = "images/products/" + menuArray[i][3];
                File imageFile = new File(imagePath);

                if (!imageFile.exists() || imageFile == null) {
                    System.out.println("Image file not found: " + imagePath + ". Using default image.");
                    imagePath = "images/products/default.png";
                }

                JPanel panelForImage = new JPanel();
                panelForImage.setBackground(Color.WHITE);
                panelForImage.setBorder(new EmptyBorder(10, 10, 10, 10));
                panelForImage.setLayout(new BorderLayout());

                ImageIcon imageIcon = new ImageIcon(imagePath);
                Image image = imageIcon.getImage();

                JLabel imageLabel = new JLabel();
                panelForImage.add(imageLabel, BorderLayout.CENTER);

                // Add a ComponentListener to handle resizing
                panelForImage.addComponentListener(new ComponentAdapter() {
                    @Override
                    public void componentResized(ComponentEvent e) {
                        int panelWidth = panelForImage.getWidth();
                        int panelHeight = panelForImage.getHeight();

                        if (panelWidth > 0 && panelHeight > 0) {
                            // Calculate the new dimensions while maintaining the aspect ratio
                            double imageWidth = image.getWidth(null);
                            double imageHeight = image.getHeight(null);

                            double panelAspectRatio = (double) panelWidth / panelHeight;
                            double imageAspectRatio = imageWidth / imageHeight;

                            int newWidth, newHeight;

                            if (panelAspectRatio > imageAspectRatio) {
                                // Panel is wider than the image, fit by height
                                newHeight = panelHeight;
                                newWidth = (int) (imageAspectRatio * newHeight);
                            } else {
                                // Panel is taller than the image, fit by width
                                newWidth = panelWidth;
                                newHeight = (int) (newWidth / imageAspectRatio);
                            }

                            // Scale the image dynamically
                            Image resizedImage = image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
                            ImageIcon resizedIcon = new ImageIcon(resizedImage);
                            imageLabel.setIcon(resizedIcon);

                            // Center the image in the panel
                            imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
                            imageLabel.setVerticalAlignment(SwingConstants.CENTER);
                        }
                    }
                });


                panel5_[i].add(panelForImage, BorderLayout.CENTER);


                productName[i] = new JLabel();
                productName[i].setText(menuArray[i][1]);
                productName[i].setFont(font.getProductNameREGULAR());
                productName[i].setForeground(Color.BLACK);
//				productName[i].setBounds(30, 30, 300, 100);

                productPrice[i] = new JLabel();
                productPrice[i].setText("PHP " + menuArray[i][2] + ".00");
                productPrice[i].setFont(font.getProductPriceBOLD());
                productPrice[i].setForeground(color.getHeader());
//				productPrice[i].setBounds(30, 50, 300, 100);

                forPanel5_[i].addMouseListener(new userMouseListen(panel5_[i], forPanel5_[i], Color.WHITE, Color.WHITE));

                mainPanelOnCenters.add(forPanel5_[i]);

                int row = i / columns;
                int col = i % columns;

                // Calculate width and height of each button
                int buttonWidth = (mainPanelOnCenters.getWidth() - leftMargin * 2 - (columns - 1) * horizontalSpacing) / columns;
                int buttonHeight = (buttonWidth > 0) ? buttonWidth : 100; // Use width or default height if not initialized

                // Set constraints dynamically
                SpringLayout.Constraints constraints = SpringForMenu.getConstraints(forPanel5_[i]);

                // Set X position (column-based)
                constraints.setX(Spring.constant(leftMargin + col * (buttonWidth + horizontalSpacing)));

                // Set Y position (row-based)
                constraints.setY(Spring.constant(topMargin + row * (buttonHeight + verticalSpacing)));

                // Set preferred size of the button
                forPanel5_[i].setPreferredSize(new Dimension(buttonWidth, buttonHeight));


                forPanel5_[i].add(panel5_[i]);

                panel5_[i].add(productName[i], BorderLayout.NORTH);
                panel5_[i].add(productPrice[i], BorderLayout.SOUTH);
            }

            mainPanelOnCenters.addComponentListener(new ComponentAdapter() {
                public void componentResized(ComponentEvent e) {
                    for (int i = 0; i < length; i++) {
                        int panelWidth = mainPanelOnCenters.getWidth();
                        int buttonWidth = (panelWidth - leftMargin * 2 - (columns - 1) * horizontalSpacing) / columns;
                        int buttonHeight = (buttonWidth > 0) ? buttonWidth : 100; // Square buttons

                        SpringLayout.Constraints constraints = SpringForMenu.getConstraints(forPanel5_[i]);
                        int row = i / columns;
                        int col = i % columns;

                        constraints.setX(Spring.constant(leftMargin + col * (buttonWidth + horizontalSpacing)));
                        constraints.setY(Spring.constant(topMargin + row * (buttonHeight + verticalSpacing)));

                        forPanel5_[i].setPreferredSize(new Dimension(buttonWidth, buttonHeight));
                    }
                    mainPanelOnCenters.revalidate();
                    mainPanelOnCenters.repaint();
                }
            });

            customScrollBarUI scrollBarUI2 = new customScrollBarUI();
            JScrollPane scrollPane = new JScrollPane(mainPanelOnCenters);

            scrollBarUI2.setCustomUI(centerContainerPanelUp.getBackground(), color.getInactiveButton(), centerContainerPanelUp.getBackground());

            scrollPane.setBorder(BorderFactory.createEmptyBorder());
            scrollPane.getVerticalScrollBar().setUI(scrollBarUI2);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

            int buttonWidth = forPanel5_[0].getPreferredSize().width; // Assuming uniform button sizes
            int buttonHeight = forPanel5_[0].getPreferredSize().height;
            int rows = (int) Math.ceil((double) menuArray.length / 4); // Assuming 4 columns
            int totalHeight = rows * buttonHeight + (rows - 1) * 10 + 20; // Adjust for spacing and margins

            mainPanelOnCenters.setPreferredSize(new Dimension(scrollPane.getWidth(), totalHeight));

            mainPanelOnCenter.add(scrollPane, BorderLayout.CENTER);

            panelFinisher(mainPanelOnCenters);
            panelFinisher(mainPanelOnCenter);
        }

        public void panelFinisher(JPanel panel) {
            panel.revalidate();
            panel.repaint();
        }
    }
}