package P_Package;

import java.awt.*;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import G_Package.customColorPallete;
import G_Package.customRoundedPanel;
import G_Package.customScrollBarUI;
import B_Package.userOperations;


public class paymentWindow extends paymentDefinitions {
	
	public paymentWindow(HashMap<String, Integer> hashMap, String[] calculations) {

		customerOrders = hashMap;
		this.calculations = calculations;

		frame = new JFrame();
		frame.setTitle("Tagaylo Store POS - Payment Window");
		frame.setSize(720, 490);
		frame.setLocationRelativeTo(null);
		frame.setAlwaysOnTop(T);

		total = Double.parseDouble(calculations[2]);
		
		topRibbon();
		bottomPanel();
		centerPanel();
		leftPanel();

		frame.getContentPane().setLayout(new BorderLayout());
		frame.add(panel1, BorderLayout.NORTH);
		frame.add(panel3, BorderLayout.CENTER);
		frame.setResizable(T);
		frame.setVisible(T);
	}

	private void leftPanel() {

		panel3s = new JPanel();
		panel3s.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 0));
		panel3s.setPreferredSize(new Dimension(270, panel3.getHeight()));
		panel3s.setBackground(Color.WHITE);
		panel3s.setLayout(new BorderLayout());

		summaryPanel2s = new JPanel();
		summaryPanel2s.setBorder(BorderFactory.createEmptyBorder(0, 0, 7, 10));
		summaryPanel2s.setPreferredSize(new Dimension(260, 40));
		summaryPanel2s.setBackground(Color.WHITE);
		summaryPanel2s.setLayout(new BorderLayout());

		summaryPanel2v = new customRoundedPanel(20);
		summaryPanel2v.setBackground(panelForPurchaseTotal.getBackground());
		summaryPanel2v.setLayout(new BorderLayout());

		summaryLabel = new JLabel();
		summaryLabel.setText("ORDER SUMMARY");
		summaryLabel.setFont(font.getProductPriceBOLD());
		summaryLabel.setHorizontalAlignment(SwingConstants.CENTER);
		summaryLabel.setForeground(Color.white);

		summaryPanel2v.add(summaryLabel);
		summaryPanel2s.add(summaryPanel2v);

		summaryPanel = new JPanel();
		summaryPanel.setBackground(Color.WHITE);
		summaryPanel.setLayout(new GridLayout(0, 1));

		int number = 0;
		int maxCount = 5; //modify here
		if (customerOrders.size() > maxCount) {
			number = customerOrders.size();
		} else {
			number = maxCount;
		}

		quantityLabel6s = new JLabel[number];
		productName6s = new JLabel[number];
		productPrice6s = new JLabel[number];
		quantityPrice6s = new JLabel[number];
		basePanel = new JPanel[number];
		orders = new customRoundedPanel[number];

		for (int i = 0; i < orders.length; i++) {
			basePanel[i] = new JPanel();
			basePanel[i].setBorder(new EmptyBorder(5, 5, 5, 5));
			basePanel[i].setBackground(Color.WHITE);
			basePanel[i].setPreferredSize(new Dimension(230, 40));
			basePanel[i].setLayout(new GridLayout(1, 1));

			orders[i] = new customRoundedPanel(25);
			orders[i].setBorder(new EmptyBorder(7, 10, 0, 10));
			orders[i].setBackground(Color.WHITE);
			orders[i].setLayout(new FlowLayout(FlowLayout.LEFT, 0, 1));
			orders[i].setVisible(F);
			orders[i].addMouseListener(new paymentActionManager.mouseListen(i, orders[i]));

			quantityLabel6s[i] = new JLabel();
			quantityLabel6s[i].setText(null);
			quantityLabel6s[i].setForeground(Color.GRAY);
			quantityLabel6s[i].setFont(font.getProductNameBOLD());
			quantityLabel6s[i].setPreferredSize(new Dimension(23, 15));

			productName6s[i] = new JLabel();
			productName6s[i].setText(null);
			productName6s[i].setForeground(Color.DARK_GRAY);
			productName6s[i].setFont(font.getProductNameBOLD());
			productName6s[i].setPreferredSize(new Dimension(110, 15));

			productPrice6s[i] = new JLabel();
			productPrice6s[i].setText(null);
			productPrice6s[i].setForeground(Color.GRAY);
			productPrice6s[i].setFont(font.getProductNameREGULAR());
			productPrice6s[i].setHorizontalAlignment(SwingConstants.RIGHT);
			productPrice6s[i].setPreferredSize(new Dimension(57, 15));

			quantityPrice6s[i] = new JLabel();
			quantityPrice6s[i].setText(null);
			quantityPrice6s[i].setBorder(new EmptyBorder(0, 0, 0, 25));
			quantityPrice6s[i].setFont(font.getpFG());
			quantityPrice6s[i].setHorizontalAlignment(SwingConstants.RIGHT);
			quantityPrice6s[i].setPreferredSize(new Dimension(215, 15));
			quantityPrice6s[i].setVisible(F);

			orders[i].add(quantityLabel6s[i]);
			orders[i].add(productName6s[i]);
			orders[i].add(productPrice6s[i]);
			orders[i].add(quantityPrice6s[i]);

			basePanel[i].add(orders[i]);
			summaryPanel.add(basePanel[i]);
		}

		String productCode;
		String productName;
		String productPrice;
		int quantity;

		int i = -1;
		for (Map.Entry<String, Integer> entry : customerOrders.entrySet()) {
			i++;
			productCode = entry.getKey();
			quantity = entry.getValue();

			productName = userOperations.getProductName(productCode);
			productPrice = userOperations.getProductPrice(productCode);

			orders[i].setBackground(summaryPanel.getBackground());
			orders[i].setVisible(T);

			quantityLabel6s[i].setText(String.valueOf(quantity) + "  x");
			productName6s[i].setText(productName);
			productPrice6s[i].setText("₱" + productPrice + ".00");
			quantityPrice6s[i].setText("= ₱" + (quantity * Integer.parseInt(productPrice)) + ".00");
			quantityPrice6s[i].setForeground(Color.BLACK);
		}

		customScrollBarUI scrollBarUI2 = new customScrollBarUI();
		JScrollPane scrollPane = new JScrollPane(summaryPanel);

		scrollPane.setViewportView(summaryPanel);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		scrollPane.getVerticalScrollBar().setUI(scrollBarUI2);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		scrollBarUI2.setCustomUI(Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.WHITE);

		panel3s.add(summaryPanel2s, BorderLayout.NORTH);
		panel3s.add(scrollPane, BorderLayout.CENTER);

		panel3.add(panel3s, BorderLayout.WEST);
		panel3.add(panelForPurchaseTotal, BorderLayout.CENTER);

	}

	private void topRibbon() {

		panel1 = new JPanel(); // Panel on the top
		panel1.setPreferredSize(new Dimension(1080, 50));
		panel1.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
		panel1.setBackground(color.getHeader());
		panel1.setLayout(new BorderLayout());

		label1 = new JLabel();
		label1.setText("Tagaylo Store POS - Payment Window");
		label1.setFont(font.getTitleFont());
		label1.setHorizontalAlignment(SwingConstants.CENTER);
		label1.setForeground(color.getTitleColor());

		panel1.add(label1, BorderLayout.CENTER);

	}

	private void bottomPanel() {

		panel2 = new JPanel(); // Panel on the bottom
		panel2.setPreferredSize(new Dimension(1080, 80));
		panel2.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
		panel2.setBackground(color.getLeftSide());
		panel2.setLayout(new BorderLayout());

	}

	private void centerPanel() {

		panel3 = new JPanel(); // Panel on the center
		panel3.setBackground(color.getRightSide());
		panel3.setLayout(new BorderLayout());

		panelForPurchaseTotal = new JPanel();
		panelForPurchaseTotal.setBackground(color.getCenterPiece());
		panelForPurchaseTotal.setLayout(new BorderLayout());

		totalPriceLabel = new JLabel();
		totalPriceLabel.setText("₱" + formatThousands(calculations[2]));
		totalPriceLabel.setFont(font.getvFG());
		totalPriceLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
		totalPriceLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		totalPriceLabel.setForeground(Color.DARK_GRAY);

		amountDueLabel = new JLabel();
		amountDueLabel.setText("Amount due:");
		amountDueLabel.setFont(font.getTitleFont());
		amountDueLabel.setBorder(BorderFactory.createEmptyBorder(17, 0, 0, 10));
		amountDueLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		amountDueLabel.setForeground(Color.DARK_GRAY);

		amountDueAndTotalPrice = new JPanel();
		amountDueAndTotalPrice.setBackground(color.getRightSide());
		amountDueAndTotalPrice.setLayout(new BorderLayout());
		amountDueAndTotalPrice.add(totalPriceLabel, BorderLayout.EAST);
		amountDueAndTotalPrice.add(amountDueLabel, BorderLayout.CENTER);

		optionsPanel = new JPanel();
		optionsPanel.setBackground(color.getRightSide());
		optionsPanel.setBorder(BorderFactory.createEmptyBorder(7, 7, 7, 7));
		optionsPanel.setPreferredSize(new Dimension(panelForPurchaseTotal.getWidth(), 50));
		optionsPanel.setLayout(new BorderLayout());

		forOptions = new JButton();
		forOptions.setBackground(optionsPanel.getBackground());
		forOptions.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
		forOptions.addActionListener(new paymentActionManager.paymentListener(0));

		options = new customRoundedPanel(20);
		options.setBackground(color.getCenterPiece());
		options.setLayout(new BorderLayout());

		confirmOrderLabel = new JLabel();
		confirmOrderLabel.setText("Confirm Order");
		confirmOrderLabel.setFont(font.getProductPriceBOLD());
		confirmOrderLabel.setHorizontalAlignment(SwingConstants.CENTER);
		confirmOrderLabel.setVerticalAlignment(SwingConstants.CENTER);
		confirmOrderLabel.setForeground(Color.WHITE);

		options.add(confirmOrderLabel, BorderLayout.CENTER);
		forOptions.add(options);
		optionsPanel.add(forOptions);

		afterOptions = new JPanel();
		afterOptions.setBackground(color.getCenterPane());
		afterOptions.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
		afterOptions.setPreferredSize(new Dimension(panelForPurchaseTotal.getWidth(), 270));

		panelForPurchaseTotal.add(amountDueAndTotalPrice, BorderLayout.NORTH);
		panelForPurchaseTotal.add(afterOptions, BorderLayout.SOUTH);
		panelForPurchaseTotal.add(optionsPanel, BorderLayout.CENTER); //option panel should be public

	}

	private static String formatThousands(String number) {
		NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
		return numberFormat.format(Double.parseDouble(number));
	}
}
