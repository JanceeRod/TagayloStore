package mainPackage;

import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.Map.Entry;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import G_Package.customRoundedPanel;
import G_Package.customPopupMenu;
import G_Package.customScrollBarUI;

import P_Package.paymentWindow;

public class userInterface extends Definitions {

	userInterface() {

		mainFrame = new JFrame();
		mainFrame.setTitle("Tagaylo Store - Point-of-Sales System | CSCC 14.1 Final Project");
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
				Operations.clearCSVFile(masterfile);
			}
		});

//		gd.setFullScreenWindow(mainFrame);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		systemTimeAndDate();

	}

	public void topRibbon() {

		topRibbonPanel = Operations.createCustomPanel(1080, 60, color.getHeader(), null);
		topRibbonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 10));

		javaJivePanel = Operations.createCustomPanel(650, 50, topRibbonPanel, null);
		javaJivePanel.setBorder(BorderFactory.createEmptyBorder(0, 25, 0, 0));

		titlePOS = Operations.createCustomLabel("Tagaylo Store POS", color.getTitleColor(), font.getTitleFont(), 0, 0, 0, 0, 0, 0, 0, 20, SwingConstants.LEFT);

		tfPanel = Operations.createCustomRoundedPanel(20, 0, 15, 1, 15, color.getSearch(), new BorderLayout());

		searchBox = new JTextField("Search products...");
		searchBox.setPreferredSize(new Dimension(425, 29));
		searchBox.setCaretPosition(0);
		searchBox.setFont(font.getFG4());
		searchBox.setBackground(tfPanel.getBackground());
		searchBox.setForeground(Color.GRAY);
		searchBox.setHorizontalAlignment(SwingConstants.LEFT);
		searchBox.setBorder(BorderFactory.createEmptyBorder());
		searchBox.addKeyListener(new keyListen());
		searchBox.addFocusListener(new focusListen());
		searchBox.getDocument().addDocumentListener(new documentListen());
		tfPanel.add(searchBox);

		searchPanel = Operations.createCustomPanel(300, 50, topRibbonPanel, new BorderLayout());
		searchPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

		timePanel = Operations.createCustomPanel(500, 50, topRibbonPanel, new BorderLayout());
		timePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 25));

		profileButtonPanel = Operations.createCustomPanel(50, 50, Color.RED, new BorderLayout());
		profileButtonPanel.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));

		profileButton = new JButton();
		profileButton.setBackground(Color.BLUE);
		profileButton.setBorder(BorderFactory.createEmptyBorder());
		profileButton.setLayout(new GridLayout(1,1));
		profileButton.setEnabled(T);
//		profileButton.addActionListener(new menuButtons(-1, null, null));

		profileButtonPop = new customPopupMenu();
		profileButtonPop.addMenuItem("Settings", e -> JOptionPane.showMessageDialog(mainFrame, "This is Settings"));
		profileButtonPop.addMenuItem("About Us?", e -> JOptionPane.showMessageDialog(mainFrame, "This is about us!"));
		profileButtonPop.addMenuItem("Log Out", e -> JOptionPane.showMessageDialog(mainFrame, "Logged out"));
		profileButtonPop.addMenuItem("Help", e -> JOptionPane.showMessageDialog(mainFrame, "Help"));

		profileButton.addActionListener(e -> {
			int x = profileButton.getWidth() - profileButtonPop.getPreferredSize().width;
			int y = profileButton.getHeight();
			profileButtonPop.show(profileButton, x, y);
		});

		profileButtonPanel.add(profileButton, BorderLayout.CENTER);

		TimeLabel = Operations.createCustomLabel(null, Color.GRAY, font.gettCF(), 0, 0, 0, 0, 0, 0, 0, 0, SwingConstants.LEFT);
		DateLabel = Operations.createCustomLabel(null, Color.GRAY, font.getdCF(), 0, 0, 0, 0, 0, 0, 0, 0, SwingConstants.LEFT);

		searchPanel.add(tfPanel);
		timePanel.add(TimeLabel, BorderLayout.EAST); //timePanel.add(DateLabel, BorderLayout.CENTER);

		javaJivePanel.add(titlePOS, BorderLayout.WEST);
		javaJivePanel.add(searchPanel, BorderLayout.CENTER);

		topRibbonPanel.add(javaJivePanel, BorderLayout.WEST);
//		topRibbonPanel.add(timePanel, BorderLayout.EAST);
		topRibbonPanel.add(profileButtonPanel, BorderLayout.EAST);

	}

	public void leftPanel() {

		leftRibbonPanel = Operations.createCustomPanel(85, 670, color.getLeftSide(), null);
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

			label2_[i] = new JLabel();
			label2_[i].setText(sideRibbonLabels[i]);
			label2_[i].setBorder(new EmptyBorder(0, 0, 20, 0));
//			label2_[i].setForeground(color.getSideTitle());
			label2_[i].setForeground(Color.GREEN);
			label2_[i].setFont(font.getFG4());

			leftRibbonPanel.add(sideRibbonButtons[i]);
			sideRibbonButtons[i].add(sideRibbonRoundedPanels[i]);
			leftRibbonPanel.add(label2_[i]);

			sideRibbonButtons[i].setVisible(T);
			label2_[i].setVisible(T);

			sideRibbonButtons[i].addActionListener(new sideButtons(i, sideRibbonLabels[i]));
		}

	}

	public void rightPanel() {

		rightRibbonPanel = Operations.createCustomPanel(360, 370, color.getRightSide(), new BorderLayout());
		rightRibbonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

		orderPaneTop = Operations.createCustomPanel(340, 35, rightRibbonPanel, new BorderLayout());
		orderPaneTop.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 15));

		orderPaneTitleTab = new customRoundedPanel(20);
		orderPaneTitleTab.setBackground(color.getCenterPiece());
		orderPaneTitleTab.setLayout(new BorderLayout());

		orderPaneLabel = Operations.createCustomLabel("CUSTOMER CART", Color.BLACK, font.getProductPriceBOLD(), 0, 0, 0, 0, 0, 0, 0, 0, SwingConstants.CENTER);

		orderPaneCen = Operations.createCustomPanel(8, 0, rightRibbonPanel.getBackground(), new BorderLayout());

		orderPaneBot = Operations.createCustomPanel(340, 150, color.getOrderPane(), null);
		orderPaneBot.setLayout(null);

		cartLabels = new String[]{"Subtotal", "Tax", "Payable Amount"};
		labelText1_ = new int[]{18, 41, 69};
		cartLabelsText = new JLabel[cartLabels.length];
		cartLabelsNumbers = new JLabel[cartLabels.length];

		for (int i = 0; i < 3; i++) {
			cartLabelsText[i] = new JLabel();
			cartLabelsText[i].setText(cartLabels[i]);
			cartLabelsText[i].setBorder(new EmptyBorder(0, 0, 15, 0));
			cartLabelsText[i].setForeground(Color.GRAY);
			cartLabelsText[i].setFont(font.getProductNameREGULAR());
			cartLabelsText[i].setBounds(15, labelText1_[i], 120, 30);

			cartLabelsNumbers[i] = new JLabel();
			cartLabelsNumbers[i].setText(formattedDefaultNo);
			cartLabelsNumbers[i].setBorder(new EmptyBorder(0, 0, 15, 0));
			cartLabelsNumbers[i].setForeground(Color.GRAY);
			cartLabelsNumbers[i].setFont(font.getProductNameREGULAR());
			cartLabelsNumbers[i].setHorizontalAlignment(SwingConstants.RIGHT);
			cartLabelsNumbers[i].setBounds(200, labelText1_[i], 120, 30);

			orderPaneBot.add(cartLabelsText[i]);
			orderPaneBot.add(cartLabelsNumbers[i]);
		}

		cartLabelsText[2].setFont(font.getProductPriceBOLD());
		cartLabelsText[2].setForeground(Color.DARK_GRAY);

		cartLabelsNumbers[2].setFont(font.getProductPriceBOLD());
		cartLabelsNumbers[2].setForeground(Color.DARK_GRAY);

		centerPaneOnRightPanel = new JPanel();
		centerPaneOnRightPanel.setBorder(new EmptyBorder(0, 10, 0, 0));
		centerPaneOnRightPanel.setBackground(rightRibbonPanel.getBackground());
		centerPaneOnRightPanel.setLayout(new GridLayout(0, 1));

		scrollBarForCart = new customScrollBarUI();
		scrollBarForCart.setCustomUI(color.getLeftSide(), Color.LIGHT_GRAY, centerPaneOnRightPanel.getBackground());

		scrollPane = new JScrollPane(centerPaneOnRightPanel);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		scrollPane.getVerticalScrollBar().setUI(scrollBarForCart);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		cancelButton = new JButton();
		cancelButton.setBounds(10, 98, 155, 40);
		cancelButton.setBackground(orderPaneBot.getBackground());
		cancelButton.setBorder(BorderFactory.createEmptyBorder());
		cancelButton.setLayout(new GridLayout(1,1));
		cancelButton.setEnabled(F);
		cancelButton.addActionListener(new menuButtons(-1, null, null));

		cancelLabel = new JLabel();
		cancelLabel.setText("Cancel Order");
		cancelLabel.setHorizontalAlignment(SwingConstants.CENTER);
		cancelLabel.setFont(font.getFG5());

		proceedButton = new JButton();
		proceedButton.setBounds(175, 98, 155, 40);
		proceedButton.setBackground(orderPaneBot.getBackground());
		proceedButton.setBorder(BorderFactory.createEmptyBorder());
		proceedButton.setLayout(new GridLayout(1,1));
		proceedButton.setEnabled(F);
		proceedButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (orderRecord != null) {
					new paymentWindow(orderRecord, calculations);
				}
			}
		});

		proceedLabel = new JLabel();
		proceedLabel.setText("Proceed to Payment");
		proceedLabel.setHorizontalAlignment(SwingConstants.CENTER);
		proceedLabel.setFont(font.getFG5());

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

		orderPaneBot.add(cancelButton);
		orderPaneBot.add(proceedButton);

		rightRibbonPanel.add(orderPaneTop, BorderLayout.NORTH);
		rightRibbonPanel.add(orderPaneBot, BorderLayout.SOUTH);
		rightRibbonPanel.add(orderPaneCen, BorderLayout.CENTER);

	}

	public void centerPanel() {

		centerContainerPanelUp = Operations.createCustomPanel(0, 0, color.getCenterPane(), new BorderLayout());
		centerContainerPanelUp.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 0));

		centerContainerPanelDown = Operations.createCustomPanel(600, 35, centerContainerPanelUp, new BorderLayout());
		centerContainerPanelDown.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));

		mainPanelOnCenter = Operations.createCustomPanel(0, 0, centerContainerPanelUp, new BorderLayout());
		mainPanelOnCenter.setBorder(BorderFactory.createEmptyBorder(15, 5, 0, 0));

		centerPanelMainLayer = new customRoundedPanel(15);
		centerPanelMainLayer.setBackground(color.getCenterPiece());

		centerContainerPanelDown.add(centerPanelMainLayer);

		centerContainerPanelUp.add(mainPanelOnCenter, BorderLayout.CENTER);
		centerContainerPanelUp.add(centerContainerPanelDown, BorderLayout.NORTH);

	}

	public void instantiate() {
		Operations.clearCSVFile(inventory);

		categoryDataMap = Operations.convertCategoriesToArrays(categories);

		Operations.writeAllArraysToMasterFile(categoryDataMap, masterfile);
		globalInventory = Operations.saveToDataArray(masterfile);

//		Operations.menuPrint(globalInventory);
		Operations.processArrayToHashMap(globalInventory, cafeInventory);
	}

	private class sideButtons implements ActionListener { //for SideBar buttons
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
				case 1 -> {
					inventoryButtonToggle();
					buttonColorReset(sideRibbonRoundedPanels, buttonIndex, color.getInactiveButton(), color.getChoice());
				}
				case 2 -> {
					customersButtonToggle();
					buttonColorReset(sideRibbonRoundedPanels, buttonIndex, color.getInactiveButton(), color.getChoice());
				}
				case 3 -> {
					cashierButtonToggle();
					buttonColorReset(sideRibbonRoundedPanels, buttonIndex, color.getInactiveButton(), color.getChoice());
				}
				case 4 -> {
					salesButtonToggle();
					buttonColorReset(sideRibbonRoundedPanels, buttonIndex, color.getInactiveButton(), color.getChoice());
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

	public void homeButtonToggle() {
		centerPanelMainLayer.removeAll();
		mainPanelOnCenter.removeAll();

		centerPanelMainLayer.setBackground(color.getLeftSide());
		centerPanelMainLayer.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		centerPanelMainLayer.setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 0));

		int[] arrayLengths = new int[categoryDataMap.size()];
		int i = 0;
		for (Entry<String, String[][]> entry : categoryDataMap.entrySet()) {
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
			button2_[j].addActionListener(new userInterface.menuTable(j, arrayLengths[j]));

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
			pSLabel[j].setFont(font.getFG4());
			pSLabel[j].setForeground(Color.GRAY);

			pillShape[j].add(pSLabel[j]);

			centerPanelMainLayer.add(button2_[j]);
		}
		panelFinisher(centerContainerPanelUp);
	}

	public class menuTable implements ActionListener {
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
						pillShape[i].setBackground(color.getRightSide());
						pSLabel[i].setText(Operations.toTitleCase(categoryKeys.get(i)));
						pSLabel[i].setFont(font.getProductPriceBOLD());
						pSLabel[i].setForeground(Color.DARK_GRAY);
					} else {
						pillShape[i].setBackground(centerPanelMainLayer.getBackground());
						pSLabel[i].setText(Operations.toTitleCase(categoryKeys.get(i)));
						pSLabel[i].setFont(font.getFG4());
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
			mainPanelOnCenter.removeAll();
			int lengthy = cafeInventory.size();
			int maxLength = 0;

			if (lengthy <= 20) {
				maxLength = lengthy;
			} else {
				maxLength = 40;
			}

			JPanel mainPanelOnCenters = new JPanel();
			mainPanelOnCenters.setBackground(mainPanelOnCenter.getBackground());
			mainPanelOnCenters.setLayout(new GridLayout(0, 4, 0, 0));

			panel5_ = new customRoundedPanel[maxLength];
			forPanel5_ = new JButton[maxLength];
			productName = new JLabel[maxLength];
			productPrice = new JLabel[maxLength];

			for (int i = 0; i < maxLength; i++) {
				forPanel5_[i] = new JButton();
				forPanel5_[i].setBackground(mainPanelOnCenters.getBackground());
				forPanel5_[i].setLayout(new BorderLayout(1,1));
				forPanel5_[i].setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
				forPanel5_[i].setPreferredSize(new Dimension(175, 175));
				forPanel5_[i].addActionListener(new menuButtons(i, menuArray, null));
				forPanel5_[i].setEnabled(F);

				panel5_[i] = new customRoundedPanel(30);
				panel5_[i].setBackground(color.getCenterPane());
				panel5_[i].setBorder(BorderFactory.createEmptyBorder(111, 12, 13, 12));
				panel5_[i].setLayout(new BorderLayout());

				productName[i] = new JLabel();
				productName[i].setFont(font.getProductNameREGULAR());
				productName[i].setForeground(Color.BLACK);

				productPrice[i] = new JLabel();
				productPrice[i].setFont(font.getProductPriceBOLD());
				productPrice[i].setForeground(color.getHeader());

				mainPanelOnCenters.add(forPanel5_[i]);

				forPanel5_[i].add(panel5_[i]);

				panel5_[i].add(productName[i], BorderLayout.NORTH);
				panel5_[i].add(productPrice[i], BorderLayout.SOUTH);
			}

			int length = menuArray.length;
			for (int i = 0; i < length; i++) {
				forPanel5_[i].setEnabled(T);
				forPanel5_[i].setFocusPainted(F);
				forPanel5_[i].addMouseListener(new mouseListen(panel5_[i], forPanel5_[i]));
				panel5_[i].setBackground(Color.WHITE);

				productName[i].setText(menuArray[i][1]);
				productPrice[i].setText("PHP " + menuArray[i][2] + ".00");
			}

			customScrollBarUI scrollBarUI2 = new customScrollBarUI();
			JScrollPane scrollPane = new JScrollPane(mainPanelOnCenters);

			scrollPane.setBorder(BorderFactory.createEmptyBorder());
			scrollPane.getVerticalScrollBar().setUI(scrollBarUI2);
			scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

			if (menuArray.length <= 12) {
				scrollBarUI2.setCustomUI(centerContainerPanelUp.getBackground(), color.getInactiveButton(), centerContainerPanelUp.getBackground());
				scrollPane.setWheelScrollingEnabled(F);
				scrollPane.getVerticalScrollBar().setEnabled(F);
			} else {
				scrollBarUI2.setCustomUI(color.getInactiveButton(), centerContainerPanelUp.getBackground(), centerContainerPanelUp.getBackground());
				scrollPane.setWheelScrollingEnabled(T);
				scrollPane.getVerticalScrollBar().setEnabled(T);
			}

			mainPanelOnCenter.add(scrollPane);
			panelFinisher(mainPanelOnCenter);
		}

		public void panelFinisher(JPanel panel) {
			panel.revalidate();
			panel.repaint();
		}
	}

	public class menuButtons implements ActionListener {
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




	public void systemTimeAndDate() {
		while (T) {
			CT = LocalTime.now();
			CTF = DateTimeFormatter.ofPattern("hh:mm a");
			CTF2 = DateTimeFormatter.ofPattern("hh:mm:ss a");

			CD = LocalDate.now();
			CDF = DateTimeFormatter.ofPattern("MM-dd-yyyy");

			TM = CT.format(CTF);
			TM2 = CT.format(CTF2);
			DM = CD.format(CDF);

			DateLabel.setText(DM);

			try {
				System.out.println("TimeLabel: " + TM2 + "\nDate: " + DM + "\n");
				TimeLabel.setText(TM);
				Thread.sleep(60000);
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void whenSearched() {
		centerPanelMainLayer.removeAll();
		mainPanelOnCenter.removeAll();

		centerPanelMainLayer.setLayout(new BorderLayout());
		centerPanelMainLayer.setBorder(BorderFactory.createEmptyBorder(0, 15, 1, 10));

		JLabel searchResult = new JLabel();
		searchResult.setText("Search Results: ");
		searchResult.setHorizontalAlignment(SwingConstants.LEFT);
		searchResult.setFont(font.getProductNameREGULAR());
		searchResult.setForeground(Color.GRAY);

		JButton closeSearch = new JButton();
		closeSearch.setBackground(centerPanelMainLayer.getBackground());
		closeSearch.setForeground(Color.GRAY);
		closeSearch.setBorder(BorderFactory.createEmptyBorder());
		closeSearch.setBorder(new EmptyBorder(5, 0, 5, 0));
		closeSearch.setEnabled(T);
		closeSearch.setPreferredSize(new Dimension(65, 25));
		closeSearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				searchBox.setText("Search products...");
				searchBox.addKeyListener(new keyListen());
				sideRibbonButtons[0].doClick();
			}
		});

		customRoundedPanel pillShape = new customRoundedPanel(25);
		pillShape.setBackground(color.getInactiveButton());
		pillShape.setBorder(new EmptyBorder(0, 0, 0, 0));
		pillShape.setLayout(new GridLayout(1,1));
		closeSearch.add(pillShape);

		JLabel pSLabel = new JLabel();
		pSLabel.setText("close");
		pSLabel.setHorizontalAlignment(SwingConstants.CENTER);
		pSLabel.setFont(font.getProductNameREGULAR());
		pSLabel.setForeground(Color.GRAY);
		pillShape.add(pSLabel);
		closeSearch.add(pillShape);

		centerPanelMainLayer.add(searchResult, BorderLayout.WEST);
		centerPanelMainLayer.add(closeSearch, BorderLayout.EAST);

		JPanel resultPanel = new JPanel();
		resultPanel.setBackground(mainPanelOnCenter.getBackground());
		resultPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		resultPanel.setLayout(new GridLayout(0, 1, 0, 0));

		int size = searchResults.size();

		JButton[] results = new JButton[size];
		customRoundedPanel[] resultsPane = new customRoundedPanel[size];
		JLabel[] productCode4s = new JLabel[size];
		JLabel[] productName4s = new JLabel[size];
		JLabel[] productPrice4s = new JLabel[size];
		for (int i = 0; i < size; i++) {
			String[] result = searchResults.get(i);

			Color panelColor = (i % 2 == 0) ? resultPanel.getBackground() : color.getOrderPane();

			results[i] = new JButton();
			results[i].setBackground(resultPanel.getBackground());
			results[i].setBorder(new EmptyBorder(0, 10, 0, 10));
			results[i].setPreferredSize(new Dimension(605, 45));
			results[i].setLayout(new GridLayout(1, 1));
			results[i].addActionListener(new menuButtons(i, null, result[0]));

			resultsPane[i] = new customRoundedPanel(15);
			resultsPane[i].setBackground(panelColor);
			resultsPane[i].setBorder(new EmptyBorder(0, 40, 0, 40));
			resultsPane[i].setLayout(new BorderLayout());

			results[i].add(resultsPane[i]);
			resultPanel.add(results[i]);

			productCode4s[i] = new JLabel();
			productCode4s[i].setText(result[0]);
			productCode4s[i].setForeground(Color.GRAY);
			productCode4s[i].setFont(font.getProductNameREGULAR());
			productCode4s[i].setPreferredSize(new Dimension(55, 15));

			productName4s[i] = new JLabel();
			productName4s[i].setText(result[1]);
			productName4s[i].setForeground(Color.DARK_GRAY);
			productName4s[i].setFont(font.getProductNameREGULAR());
			productName4s[i].setPreferredSize(new Dimension(350, 15));

			productPrice4s[i] = new JLabel();
			productPrice4s[i].setText("₱" + result[2] + ".00");
			productPrice4s[i].setForeground(Color.GRAY);
			productPrice4s[i].setFont(font.getProductNameREGULAR());
			productPrice4s[i].setHorizontalAlignment(SwingConstants.RIGHT);
			productPrice4s[i].setPreferredSize(new Dimension(60, 15));

			resultsPane[i].add(productCode4s[i], BorderLayout.WEST);
			resultsPane[i].add(productPrice4s[i], BorderLayout.EAST);
			resultsPane[i].add(productName4s[i], BorderLayout.CENTER);
		}
		searchResult.setText("Search Results:   " + size);

		JScrollPane scrollPane = new JScrollPane(resultPanel);
		customScrollBarUI scrollBarUI3 = new customScrollBarUI();

		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		scrollPane.getVerticalScrollBar().setUI(scrollBarUI3);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		scrollBarUI3.setCustomUI(Color.GRAY, Color.DARK_GRAY, Color.GREEN);

		mainPanelOnCenter.add(scrollPane);
		panelFinisher(centerContainerPanelUp);
	}

	private class documentListen implements DocumentListener {
		@Override
		public void insertUpdate(DocumentEvent e) {
			whenSearched();
			Operations.searchProducts(searchBox.getText());
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			whenSearched();
			Operations.searchProducts(searchBox.getText());
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			whenSearched();
			Operations.searchProducts(searchBox.getText());
		}
	}

	private class focusListen implements FocusListener {
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

	public class keyListen implements KeyListener {
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

	public void panelFinisher(JPanel panel) {
		panel.revalidate();
		panel.repaint();
	}

	public void buttonColorReset(JPanel[] panel, int buttonIndex, Color a, Color b) {
        for (JPanel jPanel : panel) {
            jPanel.setBackground(a);
        }
		panel[buttonIndex].setBackground(b);
	}

	public void updateIndicator() {
		mainPanelOnCenter.setLayout(new BorderLayout());

		JLabel notice = new JLabel();
		notice.setText("We're still working on this page :(");
		notice.setFont(font.getProductPriceBOLD());
		notice.setForeground(Color.LIGHT_GRAY);
		notice.setHorizontalAlignment(SwingConstants.CENTER);

		mainPanelOnCenter.add(notice, BorderLayout.CENTER);
	}

	public void inventoryButtonToggle() {
		centerPanelMainLayer.removeAll();
		mainPanelOnCenter.removeAll();

		centerPanelMainLayer.setBackground(color.getCenterPiece());
		centerPanelMainLayer.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		centerPanelMainLayer.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

		int[] arrayLengths = new int[arrayOf2DArrays.size()];
		int i = 0;
		for (Entry<String, String[][]> entry : arrayOf2DArrays.entrySet()) {
			String[][] array2D = entry.getValue();

			int length = array2D.length;
			arrayLengths[i] = length;
			i++;
		}

		int size = arrayOf2DArrays.size();
		button2_ = new JButton[size];
		pillShape = new customRoundedPanel[size];
		pSLabel = new JLabel[size];
		for (int j = 0; j < button2_.length; j++) {
			button2_[j] = new JButton();
			button2_[j].setBackground(centerPanelMainLayer.getBackground());
			button2_[j].setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			button2_[j].setEnabled(T);
			button2_[j].setPreferredSize(new Dimension(75, 34));
			button2_[j].addActionListener(new userInterface.menuTable(j, arrayLengths[j]));

			pillShape[j] = new customRoundedPanel(25);
			pillShape[j].setBackground(centerPanelMainLayer.getBackground());
			pillShape[j].setBorder(new EmptyBorder(0, 0, 0, 0));
			pillShape[j].setLayout(new GridLayout(1,1));
			button2_[j].add(pillShape[j]);

			pSLabel[j] = new JLabel();
			Set<String> arrayNames = arrayOf2DArrays.keySet();
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

		updateIndicator();
		panelFinisher(centerContainerPanelUp);
	}

	public void customersButtonToggle() {
		centerPanelMainLayer.removeAll();
		mainPanelOnCenter.removeAll();

		updateIndicator();

		centerPanelMainLayer.setBackground(Color.MAGENTA);
		panelFinisher(centerContainerPanelUp);
	}

	public void cashierButtonToggle() {
		centerPanelMainLayer.removeAll();
		mainPanelOnCenter.removeAll();

		updateIndicator();

		centerPanelMainLayer.setBackground(Color.BLUE);
		panelFinisher(centerContainerPanelUp);
	}

	public void salesButtonToggle() {
		centerPanelMainLayer.removeAll();
		mainPanelOnCenter.removeAll();

		updateIndicator();

		centerPanelMainLayer.setBackground(Color.ORANGE);
		panelFinisher(centerContainerPanelUp);
	}
}