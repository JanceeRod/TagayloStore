package B_Package;

import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import G_Package.customPopupMenu;
import G_Package.customRoundedPanel;
import G_Package.customSwingCreate;
import G_Package.customScrollBarUI;

import L_Package.logMain;
import P_Package.paymentWindow;

import static B_Package.userOperations.*;

public class userInterface extends userDefinitions {

	public static void main(String[] args) {
		new userInterface();
	}

	public userInterface() {
		mainFrame = new JFrame();
		mainFrame.setTitle("Tagaylo Store - Point-of-Sales System | CSCC 14.1 Final Project");
		mainFrame.setSize(1330, 795);
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
		mainFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.out.println("Program is closed. Do cleanup or save data if needed.");
				userOperations.clearCSVFile(masterfile);
			}
		});

//		gd.setFullScreenWindow(mainFrame);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		systemTimeAndDate();
		mainFrame.setVisible(T);
	}



	public void instantiate() {
		clearCSVFile(inventory);

		categoryDataMap = userOperations.convertCategoriesToArrays(categories);

		userOperations.writeAllArraysToMasterFile(categoryDataMap, masterfile);
		globalInventory = saveToDataArray(masterfile);

//		Operations.menuPrint(globalInventory);

		processArrayToHashMap(globalInventory, cafeInventory);
		extractProductPrices(globalInventory, productPrices);
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
		searchBox.addKeyListener(new userActionManager.keyListen());
		searchBox.addFocusListener(new userActionManager.focusListen());
		searchBox.getDocument().addDocumentListener(new userActionManager.documentListen());
		tfPanel.add(searchBox);

		searchPanel = customSwingCreate.createCustomPanel(300, 50, topRibbonPanel, new BorderLayout());
		searchPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

		timePanel = customSwingCreate.createCustomPanel(500, 50, topRibbonPanel, new BorderLayout());
		timePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 25));

		profileButtonPanel = customSwingCreate.createCustomPanel(50, 50, Color.RED, new BorderLayout());
		profileButtonPanel.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));

//		JLabel imageLabel = new JLabel(new ImageIcon("images/ui/profile.png"));
		profileButton = new JButton();
		profileButton.setBackground(color.getHeader());
		profileButton.setBorder(BorderFactory.createEmptyBorder());
		profileButton.setLayout(new GridLayout(1,1));
		profileButton.setFocusPainted(F);

		profileButton.setEnabled(T);

		ImageIcon originalIcon = new ImageIcon("images/ui/profile.png");
		JLabel imageLabel = new JLabel(originalIcon);
		customRoundedPanel profileButtonRoundedPanel = customSwingCreate.createCustomRoundedPanel(15, 0, 0, 0, 0, color.getChoice(), new BorderLayout());
		profileButtonRoundedPanel.add(imageLabel, BorderLayout.CENTER);

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
					userOperations.clearCSVFile(masterfile);
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
		timePanel.add(TimeLabel, BorderLayout.EAST);
//		timePanel.add(DateLabel, BorderLayout.CENTER);
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
		leftRibbonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 8));

		sideRibbonLabels = new String[]{"Home"};
		sideRibbonRoundedPanels = new customRoundedPanel[sideRibbonLabels.length];
		sideRibbonButtons = new JButton[sideRibbonLabels.length];
		label2_ = new JLabel[sideRibbonLabels.length];

		for (int i = 0; i < sideRibbonButtons.length; i++) {
			sideRibbonButtons[i] = new JButton();
			sideRibbonButtons[i].setBackground(leftRibbonPanel.getBackground());
			sideRibbonButtons[i].setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
			sideRibbonButtons[i].setEnabled(T);
			sideRibbonButtons[i].setFocusPainted(F);
			sideRibbonButtons[i].setLayout(new BorderLayout(1,1));
			sideRibbonButtons[i].setPreferredSize(new Dimension(85, 65));

			sideRibbonRoundedPanels[i] = new customRoundedPanel(20);
			sideRibbonRoundedPanels[i].setBackground(color.getRightSide());
			sideRibbonRoundedPanels[i].setBorder(BorderFactory.createEmptyBorder());

			JLabel imageLabel = new JLabel(new ImageIcon("images/ui/home.png"));
			sideRibbonRoundedPanels[i].add(imageLabel, BorderLayout.CENTER);

			label2_[i] = new JLabel();
			label2_[i].setText(sideRibbonLabels[i]);
			label2_[i].setBorder(new EmptyBorder(0, 0, 20, 0));
//			label2_[i].setForeground(color.getSideTitle());
			label2_[i].setForeground(Color.black);
			label2_[i].setFont(font.getProductNameREGULAR());

			leftRibbonPanel.add(sideRibbonButtons[i]);
			sideRibbonButtons[i].add(sideRibbonRoundedPanels[i]);
			leftRibbonPanel.add(label2_[i]);

			sideRibbonButtons[i].setVisible(T);
			label2_[i].setVisible(T);

			sideRibbonButtons[i].addActionListener(new userActionManager.sideButtons(i, sideRibbonLabels[i]));
		}
		leftRibbonPanel.setVisible(T);
	}

	public void rightPanel() {

		rightRibbonPanel = customSwingCreate.createCustomPanel(360, 370, color.getRightSide(), new BorderLayout());
		rightRibbonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

		orderPaneTop = customSwingCreate.createCustomPanel(340, 35, rightRibbonPanel, new BorderLayout());
		orderPaneTop.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 15));

		orderPaneTitleTab = new customRoundedPanel(20);
		orderPaneTitleTab.setBackground(color.getOrderPane());
		orderPaneTitleTab.setLayout(new BorderLayout());

		orderPaneLabel = customSwingCreate.createCustomLabel("CUSTOMER CART", Color.white, font.getProductPriceBOLD(), 0, 0, 0, 0, 0, 0, 0, 0, SwingConstants.CENTER);

		orderPaneCen = customSwingCreate.createCustomPanel(8, 0, rightRibbonPanel.getBackground(), new BorderLayout());

		orderPaneBot = customSwingCreate.createCustomPanel(340, 150, color.getOrderPane(), new BorderLayout());
		orderPaneBot.setLayout(null);

		cartLabels = new String[]{"Subtotal", "Tax", "Total:"};
		cartLabel_Ycoord = new int[]{18, 41, 64};
		cartLabelsText = new JLabel[cartLabels.length];
		cartLabelsNumbers = new JLabel[cartLabels.length];

		for (int i = 0; i < 3; i++) {
			cartLabelsText[i] = new JLabel();
			cartLabelsText[i].setText(cartLabels[i]);
			cartLabelsText[i].setBorder(new EmptyBorder(0, 0, 15, 0));
			cartLabelsText[i].setForeground(Color.white);
			cartLabelsText[i].setFont(font.getProductNameREGULAR());
			cartLabelsText[i].setBounds(20, cartLabel_Ycoord[i], 120, 30);

			cartLabelsNumbers[i] = new JLabel();
			cartLabelsNumbers[i].setText(formattedDefaultNo);
			cartLabelsNumbers[i].setBorder(new EmptyBorder(0, 0, 15, 0));
			cartLabelsNumbers[i].setForeground(Color.white);
			cartLabelsNumbers[i].setFont(font.getProductNameREGULAR());
			cartLabelsNumbers[i].setHorizontalAlignment(SwingConstants.RIGHT);
			cartLabelsNumbers[i].setBounds(215, cartLabel_Ycoord[i], 120, 30);

			orderPaneBot.add(cartLabelsText[i]);
			orderPaneBot.add(cartLabelsNumbers[i]);
		}

		cartLabelsText[2].setFont(font.getProductPriceBOLD());
		cartLabelsText[2].setForeground(Color.white);

		cartLabelsNumbers[2].setFont(font.getProductPriceBOLD());
		cartLabelsNumbers[2].setForeground(Color.white);

		centerPaneOnRightPanel = new JPanel();
		centerPaneOnRightPanel.setBorder(new EmptyBorder(0, 10, 10, 0));
		centerPaneOnRightPanel.setBackground(rightRibbonPanel.getBackground());
		centerPaneOnRightPanel.setLayout(SpringForCart);

		scrollBarForCart = new customScrollBarUI();
		scrollBarForCart.setCustomUI(color.getHeader(), Color.GREEN, centerPaneOnRightPanel.getBackground());

		scrollPane = new JScrollPane(centerPaneOnRightPanel);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		scrollPane.getVerticalScrollBar().setUI(scrollBarForCart);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		cancelButton = new JButton();
		cancelButton.setBounds(20, 98, 155, 40);
		cancelButton.setBackground(orderPaneBot.getBackground());
		cancelButton.setBorder(BorderFactory.createEmptyBorder());
		cancelButton.setLayout(new GridLayout(1,1));
		cancelButton.setEnabled(F);
		cancelButton.addActionListener(new userActionManager.menuButtons(-1, null, null));

		cancelLabel = new JLabel();
		cancelLabel.setText("Cancel Order");
		cancelLabel.setHorizontalAlignment(SwingConstants.CENTER);
		cancelLabel.setFont(font.getProductNameBOLD());

		proceedButton = new JButton();
		proceedButton.setBounds(185, 98, 155, 40);
		proceedButton.setBackground(orderPaneBot.getBackground());
		proceedButton.setBorder(BorderFactory.createEmptyBorder());
		proceedButton.setLayout(new GridLayout(1,1));
		proceedButton.setEnabled(F);
		proceedButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (orderRecord != null) {
					paymentWindow payment = new paymentWindow(orderRecord, calculations);
				}
				proceedButton.setEnabled(F);
				cancelButton.setEnabled(F);
			}
		});

		proceedLabel = new JLabel();
		proceedLabel.setText("Proceed to Payment");
		proceedLabel.setHorizontalAlignment(SwingConstants.CENTER);
		proceedLabel.setFont(font.getProductNameBOLD());

		roundedPanelForCancelButton = new customRoundedPanel(20);
		roundedPanelForCancelButton.setBackground(color.getInactiveButton());
		roundedPanelForCancelButton.setLayout(new GridLayout(1,1));
		roundedPanelForCancelButton.add(cancelLabel);

		roundedPanelForProceedButton = new customRoundedPanel(20);
		roundedPanelForProceedButton.setBackground(color.getInactiveButton());
		roundedPanelForProceedButton.setLayout(new GridLayout(1,1));
		roundedPanelForProceedButton.add(proceedLabel);

		orderPaneTitleTab.add(orderPaneLabel);
		orderPaneTop.add(orderPaneTitleTab);

		cancelButton.add(roundedPanelForCancelButton);
		proceedButton.add(roundedPanelForProceedButton);

		orderPaneCen.add(scrollPane);

		orderPaneBot.add(cancelButton, BorderLayout.EAST);
		orderPaneBot.add(proceedButton, BorderLayout.WEST);

		rightRibbonPanel.add(orderPaneTop, BorderLayout.NORTH);
		rightRibbonPanel.add(orderPaneBot, BorderLayout.SOUTH);
		rightRibbonPanel.add(orderPaneCen, BorderLayout.CENTER);
		rightRibbonPanel.setVisible(T);
	}

	public void centerPanel() {

		centerContainerPanelUp = customSwingCreate.createCustomPanel(0, 0, color.getCenterPane(), new BorderLayout());
		centerContainerPanelUp.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 0));

		centerContainerPanelDown = customSwingCreate.createCustomPanel(600, 35, centerContainerPanelUp, new BorderLayout());
		centerContainerPanelDown.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));

		mainPanelOnCenter = customSwingCreate.createCustomPanel(0, 0, color.getCenterPane(), new BorderLayout());
		mainPanelOnCenter.setBorder(BorderFactory.createEmptyBorder(15, 5, 0, 0));

		centerPanelMainLayer = new customRoundedPanel(15);
		centerPanelMainLayer.setBackground(color.getCenterPiece());

		centerContainerPanelDown.add(centerPanelMainLayer);

		centerContainerPanelUp.add(mainPanelOnCenter, BorderLayout.CENTER);
		centerContainerPanelUp.add(centerContainerPanelDown, BorderLayout.NORTH);
		centerContainerPanelUp.setVisible(T);
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
					System.out.println("TimeLabel: " + currentTime.format(consoleTimeFormatter));
					Thread.sleep(60000); // Sleep for 1 minute
				} catch (InterruptedException ex) {
					ex.printStackTrace();
					break; // Exit the loop if the thread is interrupted
				}
			}
		}).start();
	}
}