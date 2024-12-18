package A_Package;

import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import G_Package.customRoundedPanel;
import G_Package.customPopupMenu;
import G_Package.customScrollBarUI;
import G_Package.customSwingCreate;

import L_Package.logMain;
import T_Package.TransactionManager;

import static B_Package.userOperations.*;
import static javax.swing.SwingConstants.CENTER;

public class adminInterface extends adminDefinitions {

    public static void main(String[] args) {
        new adminInterface();
    }

    public adminInterface() {

        mainFrame = new JFrame();
        mainFrame.setTitle("Tagaylo Store - Admin View | CSCC 14.1 Final Project");
        mainFrame.setSize(1330, 765);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setResizable(T);

        instantiate();

        topRibbon();
        leftPanel();
        rightPanel();
        centerPanel();

        sideRibbonButtons[0].doClick();

        mainFrame.getContentPane().setLayout(new BorderLayout());
        mainFrame.add(topRibbonPanel, BorderLayout.NORTH);
        mainFrame.add(rightRibbonPanel, BorderLayout.EAST);
        mainFrame.add(leftRibbonPanel, BorderLayout.WEST);
        mainFrame.add(centerContainerPanelUp, BorderLayout.CENTER);
        mainFrame.setVisible(T);
        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.out.println("Program is closing. Do cleanup or save data if needed.");
                clearCSVFile(masterfile);
            }
        });

//		gd.setFullScreenWindow(mainFrame);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        systemTimeAndDate();
    }

    public void instantiate() {
        clearCSVFile(inventory);

        inventoryCategoryDataMap = convertCategoriesToArrays(categories);

        writeAllArraysToMasterFile(inventoryCategoryDataMap, masterfile);
        globalInventory = saveToDataArray(masterfile);

        processArrayToHashMap(globalInventory, cafeInventory);

        extractProductPrices(globalInventory, productPrices);
        extractProductNames(globalInventory, productNames);

        manager = new TransactionManager("transactionHistory.csv");
    }

    public void topRibbon() {

        topRibbonPanel = customSwingCreate.createCustomPanel(1080, 60, color.getHeader(), null);
        topRibbonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 10));

        javaJivePanel = customSwingCreate.createCustomPanel(650, 50, topRibbonPanel, null);
        javaJivePanel.setBorder(BorderFactory.createEmptyBorder(0, 25, 0, 0));

        titlePOS = customSwingCreate.createCustomLabel("Tagaylo Store POS", color.getTitleColor(), font.getTitleFont(), 0, 0, 0, 0, 0, 0, 0, 20, SwingConstants.LEFT);

        tfPanel = customSwingCreate.createCustomRoundedPanel(20, 0, 15, 1, 15, color.getSearch(), new BorderLayout());

        searchBox = new JTextField("Search products...");
        searchBox.setPreferredSize(new Dimension(425, 29));
        searchBox.setCaretPosition(0);
        searchBox.setFont(font.getProductNameREGULAR());
        searchBox.setBackground(tfPanel.getBackground());
        searchBox.setForeground(Color.GRAY);
        searchBox.setHorizontalAlignment(SwingConstants.LEFT);
        searchBox.setBorder(BorderFactory.createEmptyBorder());
        searchBox.addKeyListener(new adminActionManager.keyListen());
        searchBox.addFocusListener(new adminActionManager.focusListen());
        searchBox.getDocument().addDocumentListener(new adminActionManager.documentListen());
        tfPanel.add(searchBox);

        searchPanel = customSwingCreate.createCustomPanel(300, 50, topRibbonPanel, new BorderLayout());
        searchPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

        timePanel = customSwingCreate.createCustomPanel(500, 50, topRibbonPanel, new BorderLayout());
        timePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 25));

        profileButtonPanel = customSwingCreate.createCustomPanel(50, 50, Color.RED, new BorderLayout());
        profileButtonPanel.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));

        ImageIcon originalIcon = new ImageIcon("images/ui/profile.png");
        JLabel imageLabel = new JLabel(originalIcon);
        customRoundedPanel profileButtonRoundedPanel = customSwingCreate.createCustomRoundedPanel(15, 0, 0, 0, 0, color.getChoice(), new BorderLayout());
        profileButtonRoundedPanel.add(imageLabel, BorderLayout.CENTER);

        profileButton = new JButton();
        profileButton.setBackground(color.getHeader());
        profileButton.setBorder(BorderFactory.createEmptyBorder());
        profileButton.setLayout(new GridLayout(1,1));
        profileButton.setFocusPainted(F);
        profileButton.setEnabled(T);
        profileButton.add(profileButtonRoundedPanel);

        profileButtonPop = new customPopupMenu();
        profileButtonPop.addMenuItem("Log Out",
                e -> {
                    System.out.println("Program is closing. Do cleanup or save data if needed.");
                    JOptionPane.showMessageDialog(mainFrame, "Logged out");
                    mainFrame.dispose();
                    new logMain();
                });
        profileButtonPop.addMenuItem("Exit",
                e -> {
                    System.out.println("Program is closing. Do cleanup or save data if needed.");
                    JOptionPane.showMessageDialog(mainFrame, "System Closing");
                    mainFrame.dispose();
                    System.exit(0);
                });

        profileButton.addActionListener(e -> {
            int x = profileButton.getWidth() - profileButtonPop.getPreferredSize().width;
            int y = profileButton.getHeight();
            profileButtonPop.show(profileButton, x, y);
        });

        profileButtonPanel.add(profileButton, BorderLayout.CENTER);

        TimeLabel = customSwingCreate.createCustomLabel(null, Color.WHITE, font.gettCF(), 0, 0, 0, 0, 0, 0, 0, 0, SwingConstants.LEFT);
        DateLabel = customSwingCreate.createCustomLabel(null, Color.WHITE, font.getdCF(), 0, 0, 0, 0, 0, 0, 0, 0, SwingConstants.LEFT);

        JPanel timeProfilePanel = new JPanel();
        timeProfilePanel.setBackground(color.getHeader());
        timeProfilePanel.setLayout(new BorderLayout());

        searchPanel.add(tfPanel);
        timePanel.add(TimeLabel, BorderLayout.EAST); //timePanel.add(DateLabel, BorderLayout.CENTER);
        timePanel.setVisible(T);

        javaJivePanel.add(titlePOS, BorderLayout.WEST);
        javaJivePanel.add(searchPanel, BorderLayout.CENTER);

        timeProfilePanel.add(timePanel, BorderLayout.WEST);
        timeProfilePanel.add(profileButtonPanel, BorderLayout.EAST);

        topRibbonPanel.add(javaJivePanel, BorderLayout.WEST);
        topRibbonPanel.add(timeProfilePanel, BorderLayout.EAST);
        topRibbonPanel.setVisible(T);
    }

    public void leftPanel() {

        leftRibbonPanel = customSwingCreate.createCustomPanel(85, 670, color.getLeftSide(), null);
        leftRibbonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 5));

        sideRibbonLabels = new String[]{"Inventory", "Orders", "Sales"};
        sideRibbonRoundedPanels = new customRoundedPanel[sideRibbonLabels.length];
        sideRibbonButtons = new JButton[sideRibbonLabels.length];
        label2_ = new JLabel[sideRibbonLabels.length];

        String[] imagesNames = {"inventory.png", "orders.png", "sales.png"};

        for (int i = 0; i < sideRibbonButtons.length; i++) {
            sideRibbonButtons[i] = new JButton();
            sideRibbonButtons[i].setBackground(leftRibbonPanel.getBackground());
            sideRibbonButtons[i].setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            sideRibbonButtons[i].setEnabled(T);
            sideRibbonButtons[i].setFocusPainted(F);
            sideRibbonButtons[i].setLayout(new BorderLayout(1,1));
            sideRibbonButtons[i].setPreferredSize(new Dimension(85, 65));

            sideRibbonRoundedPanels[i] = new customRoundedPanel(20);
            sideRibbonRoundedPanels[i].setBackground(Color.PINK);
            sideRibbonRoundedPanels[i].setBorder(BorderFactory.createEmptyBorder());
            sideRibbonRoundedPanels[i].setLayout(new BorderLayout());

            // Load and resize the image to fit the panel's dimensions
            ImageIcon originalIcon = new ImageIcon("images/ui/" + imagesNames[i]);
            Image originalImage = originalIcon.getImage();

            // Assuming the panel has fixed dimensions (e.g., width = 100, height = 100)
            int panelWidth = 55;  // Replace with the actual width of your panel
            int panelHeight = 45; // Replace with the actual height of your panel

            // Calculate scaled dimensions while maintaining aspect ratio
            double imageWidth = originalImage.getWidth(null);
            double imageHeight = originalImage.getHeight(null);

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

            // Scale the image
            Image resizedImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
            ImageIcon resizedIcon = new ImageIcon(resizedImage);

            JLabel imageLabel = new JLabel(resizedIcon);
            imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
            imageLabel.setVerticalAlignment(SwingConstants.CENTER);

            sideRibbonRoundedPanels[i].add(imageLabel, BorderLayout.CENTER);


            label2_[i] = new JLabel();
            label2_[i].setText(sideRibbonLabels[i]);
            label2_[i].setBorder(new EmptyBorder(0, 0, 14, 0));
//			label2_[i].setForeground(color.getSideTitle());
            label2_[i].setForeground(Color.DARK_GRAY);
            label2_[i].setFont(font.getProductNameREGULAR());

            leftRibbonPanel.add(sideRibbonButtons[i]);
            sideRibbonButtons[i].add(sideRibbonRoundedPanels[i]);
            leftRibbonPanel.add(label2_[i]);

            sideRibbonButtons[i].setVisible(T);
            label2_[i].setVisible(T);
            label2_[i].setVisible(T);

            sideRibbonButtons[i].addActionListener(new adminActionManager.sideButtons(i, sideRibbonLabels[i]));
        }

    }

    public void rightPanel() {

        rightRibbonPanel = customSwingCreate.createCustomPanel(360, 370, color.getRightSide(), new BorderLayout());
        rightRibbonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        orderPaneTop = customSwingCreate.createCustomPanel(340, 35, rightRibbonPanel, new BorderLayout());
        orderPaneTop.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 15));

        orderPaneTitleTab = new customRoundedPanel(20);
        orderPaneTitleTab.setBackground(color.getChoice());
        orderPaneTitleTab.setLayout(new BorderLayout());

        orderPaneLabel = customSwingCreate.createCustomLabel("QUICK ACTIONS", Color.BLACK, font.getProductPriceBOLD(), 0, 0, 0, 0, 0, 0, 0, 0, CENTER);

        orderPaneCen = customSwingCreate.createCustomPanel(8, 0, rightRibbonPanel.getBackground(), new BorderLayout());

        orderPaneBot = customSwingCreate.createCustomPanel(340, 150, color.getChoice(), null);
        orderPaneBot.setLayout(null);

        centerPaneOnRightPanel = new JPanel();
        centerPaneOnRightPanel.setBorder(new EmptyBorder(0, 10, 0, 0));
        centerPaneOnRightPanel.setBackground(Color.GREEN);
        centerPaneOnRightPanel.setLayout(new GridLayout(0, 1));

        scrollBarForCart = new customScrollBarUI();
        scrollBarForCart.setCustomUI(color.getLeftSide(), Color.LIGHT_GRAY, centerPaneOnRightPanel.getBackground());

        scrollPane = new JScrollPane(centerPaneOnRightPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUI(scrollBarForCart);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        orderPaneTitleTab.add(orderPaneLabel);
        orderPaneTop.add(orderPaneTitleTab);

        orderPaneCen.add(scrollPane);

        rightRibbonPanel.add(orderPaneTop, BorderLayout.NORTH);
        rightRibbonPanel.add(orderPaneCen, BorderLayout.CENTER);
        rightRibbonPanel.setVisible(T);
    }

    public void centerPanel() {

        centerContainerPanelUp = customSwingCreate.createCustomPanel(0, 0, color.getCenterPane(), new BorderLayout());
        centerContainerPanelUp.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 0));

        centerContainerPanelDown = customSwingCreate.createCustomPanel(600, 35, centerContainerPanelUp, new BorderLayout());
        centerContainerPanelDown.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));

        mainPanelOnCenter = customSwingCreate.createCustomPanel(0, 0, centerContainerPanelUp, new BorderLayout());
        mainPanelOnCenter.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));

        centerPanelMainLayer = new customRoundedPanel(15);
        centerPanelMainLayer.setBackground(color.getChoice());

        centerContainerPanelDown.add(centerPanelMainLayer);

        centerContainerPanelUp.add(mainPanelOnCenter, BorderLayout.CENTER);
        centerContainerPanelUp.add(centerContainerPanelDown, BorderLayout.NORTH);

    }

    public void systemTimeAndDate() {
        // Swing Timer for updating the UI every second
        Timer timer = new Timer(1000, e -> {
            LocalTime currentTime = LocalTime.now();
            LocalDate currentDate = LocalDate.now();

            // Format time and date
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");

            // Update labels in the UI
            TimeLabel.setText(currentTime.format(timeFormatter));
            DateLabel.setText(currentDate.format(dateFormatter));
        });
        timer.start(); // Start the timer for UI updates

        // Background Thread for printing time to the console every minute
        new Thread(() -> {
            while (true) {
                try {
                    LocalTime currentTime = LocalTime.now();
                    DateTimeFormatter consoleTimeFormatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
                    System.out.println("Time: " + currentTime.format(consoleTimeFormatter));
                    Thread.sleep(60000); // Sleep for 1 minute
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                    break; // Exit the loop if the thread is interrupted
                }
            }
        }).start();
    }
}