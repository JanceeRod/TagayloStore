package mainPackage;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.Map.Entry;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import G_Package.RoundedPanel;
import G_Package.customScrollBarUI;

import newWindow_Package.paymentWindow;

public class userInterface extends Definitions {

	Double defaultNo = 0.00;
	Double salesTax = 5.25;
	DecimalFormat decimalFormat = new DecimalFormat("0.00");
	String formattedDefaultNo  = String.format("%.2f", defaultNo);

	JPanel panel4;
	JPanel panel6;
	JPanel sidePanel;
	JButton cancelButton, proceedButton;
	JTextField textField1;
	RoundedPanel panel5s;
	RoundedPanel roundedButtons1, roundedButtons2;
	public static JLabel C2, C3;

	public JPanel createCustomPanel(int width, int height, Object backgroundSource, LayoutManager layout) {
		JPanel customPanel = new JPanel();

		customPanel.setPreferredSize(new Dimension(width, height));

		if (backgroundSource instanceof JPanel) {
			customPanel.setBackground(((JPanel) backgroundSource).getBackground());
		} else if (backgroundSource instanceof Color) {
			customPanel.setBackground((Color) backgroundSource);
		}

		customPanel.setLayout(new BorderLayout());

		return customPanel;
	}

	public JLabel createCustomLabel(String text, Color fg, Font f, int x, int y, int w, int h, int tBorder, int lBorder, int bBorder, int rBorder, int horAl) {
		JLabel label = new JLabel();

		label.setText(text);
		label.setForeground(fg);
		label.setFont(f);
		label.setBounds(x, y, w, h);
		label.setBorder(new EmptyBorder(tBorder, lBorder, bBorder, rBorder));
		label.setHorizontalAlignment(horAl);

		return label;
	}

	public RoundedPanel createCustomRoundedPanel(int r, int tBorder, int lBorder, int bBorder, int rBorder, Object bgSrc, LayoutManager layout) {
		RoundedPanel customPanel = new RoundedPanel(r);

		customPanel.setBorder(BorderFactory.createEmptyBorder(tBorder, lBorder, bBorder, rBorder));
		if (bgSrc instanceof JPanel) {
			customPanel.setBackground(((JPanel) bgSrc).getBackground());
		} else if (bgSrc instanceof Color) {
			customPanel.setBackground((Color) bgSrc);
		}

		customPanel.setLayout(layout);

		return customPanel;
	}

	userInterface() {
		instantiate();

		JFrame frame = new JFrame();
    	frame.setTitle("Tagaylo Store - Point-of-Sales System");
    	frame.setSize(1100, 765);
    	frame.setLocationRelativeTo(null);
    	frame.setResizable(T);

		JPanel topRibbonPanel;
		topRibbonPanel = createCustomPanel(1080, 60, color.getHeader(), null);
		topRibbonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

		JPanel javaJivePanel;
		javaJivePanel = createCustomPanel(650, 50, topRibbonPanel, null);
		javaJivePanel.setBorder(BorderFactory.createEmptyBorder(0, 25, 0, 0));

		JLabel storePOSTitle;
		storePOSTitle = createCustomLabel("Tagaylo Store POS", color.getTitleColor(), font.getFG1(), 0, 0, 0, 0, 0, 0, 0, 0, SwingConstants.RIGHT);

		RoundedPanel tfPanel;
		tfPanel = createCustomRoundedPanel(20, 0, 15, 1, 15, color.getSearch(), new BorderLayout());
    	
    	textField1 = new JTextField("Search products...");
    	textField1.setPreferredSize(new Dimension(425, 29));
		textField1.setCaretPosition(0);
    	textField1.setFont(font.getFG4());
    	textField1.setBackground(tfPanel.getBackground());
    	textField1.setForeground(Color.GRAY);
    	textField1.setHorizontalAlignment(SwingConstants.LEFT);
    	textField1.setBorder(BorderFactory.createEmptyBorder());
    	textField1.addKeyListener(new keyListen());
    	textField1.addFocusListener(new focusListen());
    	textField1.getDocument().addDocumentListener(new documentListen());
    	tfPanel.add(textField1);

		JPanel searchPanel;
		searchPanel = createCustomPanel(300, 50, topRibbonPanel, new BorderLayout());
		searchPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
    	searchPanel.add(tfPanel);

		JPanel timePanel;
		timePanel = createCustomPanel(500, 50, topRibbonPanel, new BorderLayout());
		timePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 25));
    	
    	C2 = new JLabel();
    	C2.setText(null);
		C2.setFont(font.gettCF());
		C2.setForeground(Color.GRAY);
		C2.setVisible(T);
		
		C3 = new JLabel();
		C3.setText(null);
		C3.setFont(font.getdCF());
		C3.setForeground(Color.GRAY);
		C3.setVisible(T);
		
		timePanel.add(C2, BorderLayout.EAST);
//		timePanel.add(C3, BorderLayout.CENTER);
    	
    	javaJivePanel.add(storePOSTitle, BorderLayout.WEST);
    	javaJivePanel.add(searchPanel, BorderLayout.CENTER);
    	
    	topRibbonPanel.add(javaJivePanel, BorderLayout.WEST);
		topRibbonPanel.add(timePanel, BorderLayout.EAST);
    	
    	JPanel panel2 = new JPanel(); //Panel on the left
    	panel2.setPreferredSize(new Dimension(85, 670));
    	panel2.setBackground(color.getLeftSide());
    	panel2.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 8));
    	
    	curved1 = new RoundedPanel[5];
    	button1_ = new JButton[5];
    	JLabel[] label2_ = new JLabel[5];
    	String[] labelText = {"Home", "Inventory", "Customers", "Cashier", "Sales"};
    	
    	JPanel panel3 = new JPanel(); //Panel on the right
    	panel3.setPreferredSize(new Dimension(360, 670));
    	panel3.setBorder(new EmptyBorder(10, 0, 0, 0));
    	panel3.setBackground(color.getRightSide());
    	panel3.setLayout(new BorderLayout());
    	
    	JPanel orderPaneTop = new JPanel();
    	orderPaneTop.setPreferredSize(new Dimension(340, 35));
    	orderPaneTop.setBorder(new EmptyBorder(0, 10, 0, 15));
    	orderPaneTop.setLayout(new BorderLayout());
    	orderPaneTop.setBackground(panel3.getBackground());
    	
    	RoundedPanel orderPane1s = new RoundedPanel(20);
    	orderPane1s.setBackground(color.getCenterPiece());
    	orderPane1s.setLayout(new BorderLayout());
    	
    	JLabel orderPaneLabel = new JLabel();
    	orderPaneLabel.setText("Order List");
    	orderPaneLabel.setHorizontalAlignment(SwingConstants.CENTER);
    	orderPaneLabel.setFont(font.getFG2());
    	
    	orderPane1s.add(orderPaneLabel);
    	orderPaneTop.add(orderPane1s);
    	
    	JPanel orderPaneCen = new JPanel();
    	orderPaneCen.setBorder(new EmptyBorder(8, 0, 10, 0));
    	orderPaneCen.setBackground(panel3.getBackground());
    	orderPaneCen.setLayout(new BorderLayout());
    	
    	JPanel orderPaneBot = new JPanel();
    	orderPaneBot.setPreferredSize(new Dimension(340, 150));
    	orderPaneBot.setBackground(color.getOrderPane());
    	orderPaneBot.setLayout(null);
    	
    	String[] labelText1 = {"Subtotal", "Tax", "Payable Amount"};
    	int[] labelText1_ = {18, 41, 69};
    	JLabel[] label3_ = new JLabel[labelText1.length];
    	label4_ = new JLabel[labelText1.length];
     	for (int i = 0; i < 3; i++) {
    		label3_[i] = new JLabel();
    		label3_[i].setText(labelText1[i]);
    		label3_[i].setBorder(new EmptyBorder(0, 0, 15, 0));
    		label3_[i].setForeground(Color.GRAY);
    		label3_[i].setFont(font.getFG3());
    		label3_[i].setBounds(15, labelText1_[i], 120, 30);
    		
    		label4_[i] = new JLabel();
    		label4_[i].setText(formattedDefaultNo);
    		label4_[i].setBorder(new EmptyBorder(0, 0, 15, 0));
    		label4_[i].setForeground(Color.GRAY);
    		label4_[i].setFont(font.getFG3());
    		label4_[i].setHorizontalAlignment(SwingConstants.RIGHT);
    		label4_[i].setBounds(200, labelText1_[i], 120, 30);
    		
    		orderPaneBot.add(label3_[i]);
    		orderPaneBot.add(label4_[i]);
    	}
     	
     	label3_[2].setFont(font.getFG2());
     	label4_[2].setFont(font.getFG2());
     	label3_[2].setForeground(Color.DARK_GRAY);
     	label4_[2].setForeground(Color.DARK_GRAY);
    	
     	panel3.add(orderPaneTop, BorderLayout.NORTH);
    	panel3.add(orderPaneBot, BorderLayout.SOUTH);
    	panel3.add(orderPaneCen, BorderLayout.CENTER);

    	sidePanel = new JPanel();
    	sidePanel.setBorder(new EmptyBorder(0, 10, 0, 0));
    	sidePanel.setBackground(panel3.getBackground());
    	sidePanel.setLayout(new GridLayout(0, 1));
    	
    	customScrollBarUI scrollBarUI1 = new customScrollBarUI();
    	scrollBarUI1.setCustomUI(color.getLeftSide(), Color.LIGHT_GRAY, sidePanel.getBackground());
    	
    	JScrollPane scrollPane = new JScrollPane(sidePanel);
    	scrollPane.setBorder(BorderFactory.createEmptyBorder());
    	scrollPane.getVerticalScrollBar().setUI(scrollBarUI1);
    	scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    	scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    	
    	orderPaneCen.add(scrollPane);
    	
     	cancelButton = new JButton();
    	cancelButton.setBounds(10, 98, 155, 40);
    	cancelButton.setBackground(orderPaneBot.getBackground());
    	cancelButton.setBorder(BorderFactory.createEmptyBorder());
    	cancelButton.setLayout(new GridLayout(1,1));
    	cancelButton.setEnabled(F);
    	cancelButton.addActionListener(new menuButtons(-1, null, null));
    	
    	JLabel cancelLabel = new JLabel();
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
    	
    	JLabel proceedLabel = new JLabel();
    	proceedLabel.setText("Proceed to Payment");
    	proceedLabel.setHorizontalAlignment(SwingConstants.CENTER);
    	proceedLabel.setFont(font.getFG5());
    	
    	roundedButtons1 = new RoundedPanel(20);
    	roundedButtons1.setBackground(color.getInactiveButton());
    	roundedButtons1.setLayout(new GridLayout(1,1));
    	roundedButtons1.add(cancelLabel);
    	
    	roundedButtons2 = new RoundedPanel(20);
    	roundedButtons2.setBackground(color.getInactiveButton());
    	roundedButtons2.setLayout(new GridLayout(1,1));
    	roundedButtons2.add(proceedLabel);
    	
    	cancelButton.add(roundedButtons1);
    	proceedButton.add(roundedButtons2);
    	
    	orderPaneBot.add(cancelButton);
    	orderPaneBot.add(proceedButton);
    	
    	panel4 = new JPanel(); //Panel on the center
    	panel4.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 0));
    	panel4.setBackground(color.getCenterPane());
    	panel4.setLayout(new BorderLayout());
    	
    	JPanel panel5 = new JPanel();
    	panel5.setPreferredSize(new Dimension(600, 35));
    	panel5.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
    	panel5.setBackground(panel4.getBackground());
    	panel5.setLayout(new BorderLayout());
    	
    	panel5s = new RoundedPanel(15);
    	panel5s.setBackground(color.getCenterPiece());
    	
    	panel5.add(panel5s);
    	
    	panel6 = new JPanel();
    	panel6.setBorder(BorderFactory.createEmptyBorder(15, 5, 0, 0));
    	panel6.setBackground(panel4.getBackground());
    	panel6.setLayout(new BorderLayout());
    	
    	panel4.add(panel6, BorderLayout.CENTER);
    	panel4.add(panel5, BorderLayout.NORTH);

		for (int i = 0; i < button1_.length; i++) {
			button1_[i] = new JButton();
			button1_[i].setBackground(panel2.getBackground());
			button1_[i].setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
			button1_[i].setEnabled(T);
			button1_[i].setFocusPainted(F);
			button1_[i].setLayout(new BorderLayout(1,1));
			button1_[i].setPreferredSize(new Dimension(85, 65));

			curved1[i] = new RoundedPanel(20);
			curved1[i].setBackground(color.getRightSide());
			curved1[i].setBorder(BorderFactory.createEmptyBorder());

			label2_[i] = new JLabel();
			label2_[i].setText(labelText[i]);
			label2_[i].setBorder(new EmptyBorder(0, 0, 20, 0));
			label2_[i].setForeground(color.getSideTitle());
			label2_[i].setFont(font.getFG4());

			panel2.add(button1_[i]);
			button1_[i].add(curved1[i]);
			panel2.add(label2_[i]);

			button1_[i].setVisible(T);
			label2_[i].setVisible(T);

			button1_[i].addActionListener(new sideButtons(i, labelText[i]));
		}
    	
    	frame.getContentPane().setLayout(new BorderLayout());
    	frame.add(topRibbonPanel, BorderLayout.NORTH);
    	frame.add(panel3, BorderLayout.EAST);
    	frame.add(panel2, BorderLayout.WEST);
    	frame.add(panel4, BorderLayout.CENTER);
    	frame.setVisible(T);
    	frame.addWindowListener(new WindowAdapter() {
    		public void windowClosing(WindowEvent e) {
    			System.out.println("Program is closing. Do cleanup or save data if needed.");
    			Operations.clearCSVFile(masterfile);
    		}
    	});
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	
    	button1_[0].doClick();
    	systemTimeAndDate();

	}



	public void instantiate() {
		Operations.clearCSVFile(inventory);
		beveragesMenu = saveToDataArray(beverages);
		foodMenu = saveToDataArray(food);

		arrayOf2DArrays.put("Food", foodMenu);
		arrayOf2DArrays.put("Drinks", beveragesMenu);

		writeArrayToCSV(foodMenu, masterfile);
		writeArrayToCSV(beveragesMenu, masterfile);

		inventoryMasterfile = saveToDataArray(masterfile);
		writeArrayToCSV(inventoryMasterfile, inventory);
		processArray(cafeInventory, inventoryMasterfile);

//    	menuPrint(inventoryMasterfile);
	}

	public static void processArray(LinkedHashMap<String, Integer> resultMap, String[][] array) {
		for (String[] row : array) {
			if (row.length > 0) {
				String key = row[0];
				int value = 20;
				resultMap.put(key, value);
			}
		}
	}

	public static void writeArrayToCSV(String[][] array, String filePath) {
		try (PrintWriter writer = new PrintWriter(new FileWriter(filePath, true))) {
			for (String[] row : array) {
				for (int i = 0; i < row.length - 1; i++) {
					writer.print(row[i] + ",");
				}
				writer.println(row[row.length - 1]);
			}

			System.out.println("CSV file updated successfully.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String[][] saveToDataArray(String a) {
		try (BufferedReader reader = new BufferedReader(new FileReader(a))) {
			long lineCount = reader.lines().count();
			BufferedReader reader1 = new BufferedReader(new FileReader(a));

			String[][] dataArray = new String[(int) lineCount][];
			reader.close();

			int index = 0;
			String line;
			while ((line = reader1.readLine()) != null) {
				dataArray[index++] = line.split(",");
			}

			return dataArray;
		} catch (IOException e) {
			e.printStackTrace();
			return new String[0][];
		}
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
					buttonColorReset(curved1, buttonIndex, color.getInactiveButton(), color.getChoice());
				}
				case 1 -> {
					inventoryButtonToggle();
					buttonColorReset(curved1, buttonIndex, color.getInactiveButton(), color.getChoice());
				}
				case 2 -> {
					customersButtonToggle();
					buttonColorReset(curved1, buttonIndex, color.getInactiveButton(), color.getChoice());
				}
				case 3 -> {
					cashierButtonToggle();
					buttonColorReset(curved1, buttonIndex, color.getInactiveButton(), color.getChoice());
				}
				case 4 -> {
					salesButtonToggle();
					buttonColorReset(curved1, buttonIndex, color.getInactiveButton(), color.getChoice());
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
		panel5s.removeAll();
		panel6.removeAll();

		panel5s.setBackground(color.getCenterPiece());
		panel5s.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		panel5s.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

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
		pillShape = new RoundedPanel[size];
		pSLabel = new JLabel[size];
		for (int j = 0; j < button2_.length; j++) {
			button2_[j] = new JButton();
			button2_[j].setBackground(panel5s.getBackground());
			button2_[j].setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			button2_[j].setEnabled(T);
			button2_[j].setFocusPainted(F);
			button2_[j].setPreferredSize(new Dimension(75, 34));
			button2_[j].addActionListener(new userInterface.menuTable(j, arrayLengths[j]));

			pillShape[j] = new RoundedPanel(25);
			pillShape[j].setLayout(new GridLayout(1,1));
			button2_[j].add(pillShape[j]);

			pSLabel[j] = new JLabel();
			Set<String> arrayNames = arrayOf2DArrays.keySet();
			if (j < arrayNames.size()) {
				String arrayName = (String) arrayNames.toArray()[j];
				pSLabel[j].setText(arrayName);
			}
			pSLabel[j].setHorizontalAlignment(SwingConstants.CENTER);
			pSLabel[j].setFont(font.getFG3());
			pSLabel[j].setForeground(Color.GRAY);

			pillShape[j].add(pSLabel[j]);

			panel5s.add(button2_[j]);
		}
		panelFinisher(panel4);
	}

	public class menuTable implements ActionListener {
		private int buttonIndex;
		public menuTable(int buttonIndex, int length) {
			this.setButtonIndex(buttonIndex);
		}

		@Override
		public void actionPerformed(ActionEvent e) { //subject for revision as this is not final
			switch (buttonIndex) {
				case 0:
					tableModifier(foodMenu);
					pillShape[1].setBackground(panel5s.getBackground());
					pillShape[0].setBackground(color.getRightSide());
					pSLabel[0].setForeground(Color.DARK_GRAY);
					pSLabel[1].setForeground(Color.GRAY);
					break;
				case 1:
					tableModifier(beveragesMenu);
					pillShape[0].setBackground(panel5s.getBackground());
					pillShape[1].setBackground(color.getRightSide());
					pSLabel[1].setForeground(Color.DARK_GRAY);
					pSLabel[0].setForeground(Color.GRAY);
					break;
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
			panel6.removeAll();
			int lengthy = cafeInventory.size();
			int maxLength = 0;

			if (lengthy <= 20) {
				maxLength = lengthy;
			} else {
				maxLength = 50;
			}

			JPanel panel6s = new JPanel();
			panel6s.setBackground(panel6.getBackground());
			panel6s.setLayout(new GridLayout(0, 4, 0, 0));

			panel5_ = new RoundedPanel[maxLength];
			forPanel5_ = new JButton[maxLength];
			productName = new JLabel[maxLength];
			productPrice = new JLabel[maxLength];

			for (int i = 0; i < maxLength; i++) {
				forPanel5_[i] = new JButton();
				forPanel5_[i].setBackground(panel6s.getBackground());
				forPanel5_[i].setLayout(new BorderLayout(1,1));
				forPanel5_[i].setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
				forPanel5_[i].setPreferredSize(new Dimension(140, 175));
				forPanel5_[i].addActionListener(new menuButtons(i, menuArray, null));
				forPanel5_[i].setEnabled(F);

				panel5_[i] = new RoundedPanel(30);
				panel5_[i].setBackground(panel6s.getBackground());
				panel5_[i].setBorder(BorderFactory.createEmptyBorder(120, 12, 13, 10));
				panel5_[i].setLayout(new BorderLayout());

				productName[i] = new JLabel();
				productName[i].setFont(font.getFG5());
				productName[i].setForeground(Color.RED);

				productPrice[i] = new JLabel();
				productPrice[i].setFont(font.getFG3());

				panel6s.add(forPanel5_[i]);

				forPanel5_[i].add(panel5_[i]);

				panel5_[i].add(productName[i], BorderLayout.NORTH);
				panel5_[i].add(productPrice[i], BorderLayout.SOUTH);
			}

			int length = menuArray.length;
			for (int i = 0; i < length; i++) {
				forPanel5_[i].setEnabled(T);
				forPanel5_[i].setFocusPainted(F);
				forPanel5_[i].addMouseListener(new mouseListen(panel5_[i], forPanel5_[i]));
				panel5_[i].setBackground(color.getLeftSide());

				productName[i].setText(menuArray[i][1]);
				productPrice[i].setText("₱" + menuArray[i][2] + ".00");
			}

			customScrollBarUI scrollBarUI2 = new customScrollBarUI();
			JScrollPane scrollPane = new JScrollPane(panel6s);

			scrollPane.setBorder(BorderFactory.createEmptyBorder());
			scrollPane.getVerticalScrollBar().setUI(scrollBarUI2);
			scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

			if (menuArray.length <= 12) {
				scrollBarUI2.setCustomUI(panel4.getBackground(), color.getInactiveButton(), panel4.getBackground());
				scrollPane.setWheelScrollingEnabled(F);
				scrollPane.getVerticalScrollBar().setEnabled(F);
			} else {
				scrollBarUI2.setCustomUI(color.getInactiveButton(), panel4.getBackground(), panel4.getBackground());
				scrollPane.setWheelScrollingEnabled(T);
				scrollPane.getVerticalScrollBar().setEnabled(T);
			}

			panel6.add(scrollPane);
			panelFinisher(panel6);
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
				for (int i = 0; i < label4_.length; i++) {
					label4_[i].setText(formattedDefaultNo);
				}
				sidePanel.removeAll();
				sidePanel.repaint();
				sidePanel.revalidate();

				roundedButtons1.setBackground(Color.GRAY);
				roundedButtons2.setBackground(Color.GRAY);
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
				sidePanel.removeAll();
				if (number > maxCount) {
					number = orderRecord.size();
				} else {
					number = maxCount;
				}

				panel6_ = new RoundedPanel[number];
				quantityLabel6s = new JLabel[panel6_.length];
				productName6s = new JLabel[panel6_.length];
				productPrice6s = new JLabel[panel6_.length];

				for (int i = 0; i < panel6_.length; i++) {
					panel6_[i] = new RoundedPanel(12);
					panel6_[i].setBorder(new EmptyBorder(8, 10, 0, 10));
					panel6_[i].setBackground(sidePanel.getBackground());
					panel6_[i].setLayout(new FlowLayout(FlowLayout.LEFT, 12, 11));
					panel6_[i].setPreferredSize(new Dimension(310, 50));

					sidePanel.add(panel6_[i]);

					quantityLabel6s[i] = new JLabel();
					quantityLabel6s[i].setText(null);
					quantityLabel6s[i].setForeground(Color.DARK_GRAY);
					quantityLabel6s[i].setFont(font.getFG3());
					quantityLabel6s[i].setPreferredSize(new Dimension(25, 15));

					panel6_[i].add(quantityLabel6s[i]);

					productName6s[i] = new JLabel();
					productName6s[i].setText(null);
					productName6s[i].setForeground(Color.DARK_GRAY);
					productName6s[i].setFont(font.getFG3());
					productName6s[i].setPreferredSize(new Dimension(170, 15));

					panel6_[i].add(productName6s[i]);

					productPrice6s[i] = new JLabel();
					productPrice6s[i].setText(null);
					productPrice6s[i].setForeground(Color.DARK_GRAY);
					productPrice6s[i].setFont(font.getFG3());
					productPrice6s[i].setHorizontalAlignment(SwingConstants.RIGHT);
					productPrice6s[i].setPreferredSize(new Dimension(60, 15));
				}

				String[] productPrice = Operations.getPricesArray(keysArray);
				for (int i = 0; i < orderRecord.size(); i++) {

					intArray[i] = valuesArray[i];

					Color panelColor = (i % 2 == 0) ? Color.WHITE : color.getRightSide();
					panel6_[i].setBackground(panelColor);

					panel6_[i].add(productPrice6s[i]);
					quantityLabel6s[i].setText(String.valueOf(intArray[i]));
					productName6s[i].setText(Operations.getProductName(keysArray[i]));
					productPrice6s[i].setText("₱" + productPrice[i] + ".00");
				}

				cancelButton.setEnabled(T);
				proceedButton.setEnabled(T);

				roundedButtons1.setBackground(color.getLeftSide());
				roundedButtons2.setBackground(color.getLeftSide());

				sidePanel.repaint();
				sidePanel.revalidate();

				sidePanelPaymentHandling(label4_);
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


		public int getButtonIndex() {
			return buttonIndex;
		}
		
		public void setButtonIndex(int buttonIndex) {
			this.buttonIndex = buttonIndex;
		}

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
			
			C3.setText(DM);
			
			try {
				System.out.println("Time: " + TM2 + "\nDate: " + DM + "\n");
				C2.setText(TM);
				Thread.sleep(60000);
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void whenSearched() {
		panel5s.removeAll();
		panel6.removeAll();
		
		panel5s.setLayout(new BorderLayout());
    	panel5s.setBorder(BorderFactory.createEmptyBorder(0, 15, 1, 10));
		
    	JLabel searchResult = new JLabel();
    	searchResult.setText("Search Results: ");
    	searchResult.setHorizontalAlignment(SwingConstants.LEFT);
    	searchResult.setFont(font.getFG3());
    	searchResult.setForeground(Color.GRAY);
    	
    	JButton closeSearch = new JButton();
    	closeSearch.setBackground(panel5s.getBackground());
    	closeSearch.setForeground(Color.GRAY);
    	closeSearch.setBorder(BorderFactory.createEmptyBorder());
    	closeSearch.setBorder(new EmptyBorder(5, 0, 5, 0));
    	closeSearch.setEnabled(T);
    	closeSearch.setPreferredSize(new Dimension(65, 25));
    	closeSearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				textField1.setText("Search products...");
				textField1.addKeyListener(new keyListen());
				button1_[0].doClick();
			}
    	});
    	
    	RoundedPanel pillShape = new RoundedPanel(25);
    	pillShape.setBackground(color.getInactiveButton());
    	pillShape.setBorder(new EmptyBorder(0, 0, 0, 0));
    	pillShape.setLayout(new GridLayout(1,1));
		closeSearch.add(pillShape);
		
		JLabel pSLabel = new JLabel();
		pSLabel.setText("close");
		pSLabel.setHorizontalAlignment(SwingConstants.CENTER);
		pSLabel.setFont(font.getFG3());
		pSLabel.setForeground(Color.GRAY);
		pillShape.add(pSLabel);
		closeSearch.add(pillShape);
    	
    	panel5s.add(searchResult, BorderLayout.WEST);
    	panel5s.add(closeSearch, BorderLayout.EAST);
    	
    	JPanel resultPanel = new JPanel();
    	resultPanel.setBackground(panel6.getBackground());
    	resultPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    	resultPanel.setLayout(new GridLayout(0, 1, 0, 0));
    	
		int size = searchResults.size();

    	JButton[] results = new JButton[size];
    	RoundedPanel[] resultsPane = new RoundedPanel[size];
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
    		
    		resultsPane[i] = new RoundedPanel(15);
    		resultsPane[i].setBackground(panelColor);
    		resultsPane[i].setBorder(new EmptyBorder(0, 40, 0, 40));
    		resultsPane[i].setLayout(new BorderLayout());
    		
    		results[i].add(resultsPane[i]);
    		resultPanel.add(results[i]);
    		
    		productCode4s[i] = new JLabel();
    		productCode4s[i].setText(result[0]);
    		productCode4s[i].setForeground(Color.GRAY);
    		productCode4s[i].setFont(font.getFG3());
    		productCode4s[i].setPreferredSize(new Dimension(55, 15));
    		
    		productName4s[i] = new JLabel();
    		productName4s[i].setText(result[1]);
    		productName4s[i].setForeground(Color.DARK_GRAY);
    		productName4s[i].setFont(font.getFG3());
    		productName4s[i].setPreferredSize(new Dimension(350, 15));
    		
    		productPrice4s[i] = new JLabel();
    		productPrice4s[i].setText("₱" + result[2] + ".00");
    		productPrice4s[i].setForeground(Color.GRAY);
    		productPrice4s[i].setFont(font.getFG3());
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
    	
    	panel6.add(scrollPane);
    	panelFinisher(panel4);
	}
	
	private class documentListen implements DocumentListener {
		@Override
		public void insertUpdate(DocumentEvent e) {
			whenSearched();
			Operations.searchProducts(inventoryMasterfile, textField1.getText());
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			whenSearched();
			Operations.searchProducts(inventoryMasterfile, textField1.getText());
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			whenSearched();
			Operations.searchProducts(inventoryMasterfile, textField1.getText());
		}
	}
	
	private class focusListen implements FocusListener {
		@Override
		public void focusGained(FocusEvent e) {
			textField1.setCaretPosition(0);
		}

		@Override
		public void focusLost(FocusEvent e) {
			textField1.addKeyListener(new keyListen());
			textField1.setForeground(Color.GRAY);
		}
	}

	public class keyListen implements KeyListener {
		@Override
		public void keyTyped(KeyEvent e) {
			textField1.setText("");
			textField1.removeKeyListener(this);
			textField1.setForeground(Color.DARK_GRAY);
			//addition: Recent Searches and Recommendations
		}

		@Override
		public void keyPressed(KeyEvent e) {
			if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_A) {
				textField1.setText("");
				textField1.setCaretPosition(0);
			}
			if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) {
				e.consume();
				textField1.setCaretPosition(0);
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			textField1.setText("Search products...");
			textField1.setCaretPosition(0);
			textField1.addKeyListener(this);
		}
	}




	public void panelFinisher(JPanel panel) {
		panel.revalidate();
		panel.repaint();
	}

	public void buttonColorReset(JPanel[] panel, int buttonIndex, Color a, Color b) {
		for (int i = 0; i < panel.length; i++) {
			panel[i].setBackground(a);
		}
		panel[buttonIndex].setBackground(b);
	}

	public void updateIndicator() {
		panel6.setLayout(new BorderLayout());
		
		JLabel notice = new JLabel();
		notice.setText("We're still working on this page :(");
		notice.setFont(font.getFG2());
		notice.setForeground(Color.LIGHT_GRAY);
    	notice.setHorizontalAlignment(SwingConstants.CENTER);
		
		panel6.add(notice, BorderLayout.CENTER);
	}

	public void inventoryButtonToggle() {
		panel5s.removeAll();
		panel6.removeAll();

		panel5s.setBackground(color.getCenterPiece());
    	panel5s.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
    	panel5s.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
    	
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
		pillShape = new RoundedPanel[size];
		pSLabel = new JLabel[size];
    	for (int j = 0; j < button2_.length; j++) {
    		button2_[j] = new JButton();
    		button2_[j].setBackground(panel5s.getBackground());
    		button2_[j].setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    		button2_[j].setEnabled(T);
    		button2_[j].setPreferredSize(new Dimension(75, 34));
    		button2_[j].addActionListener(new userInterface.menuTable(j, arrayLengths[j]));
    		
    		pillShape[j] = new RoundedPanel(25);
    		pillShape[j].setBackground(panel5s.getBackground());
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
    		pSLabel[j].setFont(font.getFG3());
    		pSLabel[j].setForeground(Color.GRAY);
    		
    		pillShape[j].add(pSLabel[j]);
    		
    		panel5s.add(button2_[j]);
    	}
    	
    	updateIndicator();

		int masterLength = inventoryMasterfile.length;
		for (int j = 0; j < masterLength; j++) {
			
		}
		
    	panelFinisher(panel4);
	}
	
	public void customersButtonToggle() {
		panel5s.removeAll();
		panel6.removeAll();
		
		updateIndicator();
		
    	panel5s.setBackground(Color.MAGENTA);
    	panelFinisher(panel4);
	}
	
	public void cashierButtonToggle() {
		panel5s.removeAll();
		panel6.removeAll();
		
		updateIndicator();
		
    	panel5s.setBackground(Color.BLUE);
    	panelFinisher(panel4);
	}
	
	public void salesButtonToggle() {
		panel5s.removeAll();
		panel6.removeAll();
		
		updateIndicator();
		
    	panel5s.setBackground(Color.ORANGE);
		panelFinisher(panel4);
	}
}
