package P_Package;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import G_Package.customRoundedPanel;
import G_Package.customColorPallete;
import G_Package.customScrollBarUI;
import G_Package.customFontGallery;

import M_Package.Operations;


public class paymentWindow extends JFrame {
	customColorPallete color = new customColorPallete();
	customFontGallery font = new customFontGallery();
	
	boolean T = true, F = false;
	
	customRoundedPanel[] orders;
	JLabel[] quantityPrice6s;
	JPanel[] basePanel;
	
	String[] paymentMethods = {"Payment Method", "Cash", "Credit Card", "Debit Card", "GCash"};
	
	JPanel afterOptions;
	
	public paymentWindow(HashMap<String, Integer> hashMap, String[] calculations) {
		setTitle("Tagaylo Store POS - Payment Window");
		setSize(720, 490);
		setLocationRelativeTo(null);
		setResizable(T);
		
		JPanel panel1 = new JPanel(); // Panel on the top
    	panel1.setPreferredSize(new Dimension(1080, 50));
    	panel1.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
    	panel1.setBackground(color.getHeader());
    	panel1.setLayout(new BorderLayout());
    	
    	JLabel label1 = new JLabel();
    	label1.setText("Tagaylo Store POS - Payment Window");
    	label1.setFont(font.getTitleFont());
    	label1.setHorizontalAlignment(SwingConstants.CENTER);
    	label1.setForeground(color.getTitleColor());
    	
    	panel1.add(label1, BorderLayout.CENTER);
    	
    	JPanel panel2 = new JPanel(); // Panel on the bottom
    	panel2.setPreferredSize(new Dimension(1080, 80));
    	panel2.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
    	panel2.setBackground(color.getLeftSide());
    	panel2.setLayout(new BorderLayout());
    	
    	JPanel panel3 = new JPanel(); // Panel on the center
    	panel3.setBackground(Color.GREEN);
    	panel3.setLayout(new BorderLayout());
    	
    	JPanel panel3t = new JPanel();
    	panel3t.setBackground(color.getRightSide());
//    	panel3t.setBackground(Color.RED);
    	panel3t.setLayout(new BorderLayout());
    	
    	JLabel totalPriceLabel = new JLabel();
    	totalPriceLabel.setText("₱" + formatThousands(calculations[2]));
    	totalPriceLabel.setFont(font.getvFG());
    	totalPriceLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 7, 20));
    	totalPriceLabel.setHorizontalAlignment(SwingConstants.RIGHT);
    	totalPriceLabel.setForeground(Color.DARK_GRAY);
    	
    	int size = paymentMethods.length - 1;
    	JPanel optionPanel = new JPanel();
    	optionPanel.setBackground(panel3t.getBackground());
//    	optionPanel.setBackground(Color.YELLOW);
    	optionPanel.setBorder(BorderFactory.createEmptyBorder(7, 7, 7, 7));
    	optionPanel.setPreferredSize(new Dimension(panel3t.getWidth(), 50));
    	optionPanel.setLayout(new GridLayout(1, size));
    	
    	JButton[] forOptions = new JButton[size];
    	customRoundedPanel[] options = new customRoundedPanel[size];
    	for (int i = 0; i < size; i++) {
    		forOptions[i] = new JButton();
    		forOptions[i].setBackground(optionPanel.getBackground());
    		forOptions[i].setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
    		forOptions[i].addActionListener(new paymentListener(i));
    		
    		options[i] = new customRoundedPanel(20);
    		options[i].setBackground(Color.GREEN);
    		
    		forOptions[i].add(options[i]);
    		optionPanel.add(forOptions[i]);
    	}
    	
    	afterOptions = new JPanel();
    	afterOptions.setBackground(Color.ORANGE);
    	afterOptions.setPreferredSize(new Dimension(panel3t.getWidth(), 270));
    	
    	panel3t.add(totalPriceLabel, BorderLayout.NORTH);
    	panel3t.add(afterOptions, BorderLayout.SOUTH);
    	panel3t.add(optionPanel, BorderLayout.CENTER); //option panel should be public 
    	
    	JPanel panel3s = new JPanel();
    	panel3s.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 0));
    	panel3s.setPreferredSize(new Dimension(270, panel3.getHeight()));
    	panel3s.setBackground(Color.WHITE);
    	panel3s.setLayout(new BorderLayout());
    	
    	JPanel summaryPanel2s = new JPanel();
    	summaryPanel2s.setBorder(BorderFactory.createEmptyBorder(0, 0, 7, 10));
    	summaryPanel2s.setPreferredSize(new Dimension(260, 40));
    	summaryPanel2s.setBackground(Color.WHITE);
    	summaryPanel2s.setLayout(new BorderLayout());
    	
    	customRoundedPanel summaryPanel2v = new customRoundedPanel(20);
    	summaryPanel2v.setBackground(panel3t.getBackground());
    	summaryPanel2v.setLayout(new BorderLayout());
    	
    	JLabel summaryLabel = new JLabel();
    	summaryLabel.setText("ORDER SUMMARY");
    	summaryLabel.setFont(font.getProductPriceBOLD());
    	summaryLabel.setHorizontalAlignment(SwingConstants.CENTER);
    	summaryLabel.setForeground(Color.BLACK);
    	
    	summaryPanel2v.add(summaryLabel);
    	summaryPanel2s.add(summaryPanel2v);
    	
    	JPanel summaryPanel = new JPanel();
    	summaryPanel.setBackground(Color.WHITE);
    	summaryPanel.setLayout(new GridLayout(0, 1));
    	
    	int number = 0;
    	int maxCount = 5; //modify here
    	if (hashMap.size() > maxCount) {
    		number = hashMap.size();
    	} else {
    		number = maxCount;
    	}
    	
    	JLabel[] quantityLabel6s = new JLabel[number];
    	JLabel[] productName6s = new JLabel[number];
    	JLabel[] productPrice6s = new JLabel[number];
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
    		orders[i].addMouseListener(new mouseListen(i, orders[i]));
    		
    		quantityLabel6s[i] = new JLabel();
    		quantityLabel6s[i].setText(null);
    		quantityLabel6s[i].setForeground(Color.GRAY);
    		quantityLabel6s[i].setFont(font.getpFG());
    		quantityLabel6s[i].setPreferredSize(new Dimension(23, 15));
    		
    		productName6s[i] = new JLabel();
    		productName6s[i].setText(null);
    		productName6s[i].setForeground(Color.DARK_GRAY);
    		productName6s[i].setFont(font.getpFG());
    		productName6s[i].setPreferredSize(new Dimension(110, 15));
    		
    		productPrice6s[i] = new JLabel();
    		productPrice6s[i].setText(null);
    		productPrice6s[i].setForeground(Color.GRAY);
    		productPrice6s[i].setFont(font.getpFG());
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
    	for (Map.Entry<String, Integer> entry : hashMap.entrySet()) {
    		i++;
    		productCode = entry.getKey();
    		quantity = entry.getValue();
    		
    		productName = Operations.getProductName(productCode);
    		productPrice = Operations.getProductPrice(productCode);
    		
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
    	panel3.add(panel3t, BorderLayout.CENTER);
    	
    	JComboBox<String> option = createComboBox(paymentMethods);
    	option.setForeground(Color.GRAY);
    	option.setBackground(Color.WHITE);
//    	option.setName("Choose a subject");
    	option.setPreferredSize(new Dimension(190, 22));
    	option.setRenderer(new DefaultListCellRenderer() {
		    @Override
		    public Component getListCellRendererComponent(JList<?> list, Object value,
		            int index, boolean isSelected, boolean cellHasFocus) {
		        Component c = super.getListCellRendererComponent(list, value, index,
		                isSelected, cellHasFocus);
		        if (index == 0) {
		            c.setEnabled(false);
		        }
		        return c;
		    }
		});
    	option.setFont(new Font("Verdana", Font.BOLD, 15));
//    	panel2.add(option);
		
		getContentPane().setLayout(new BorderLayout());
		add(panel1, BorderLayout.NORTH);
//		add(panel2, BorderLayout.SOUTH);
		add(panel3, BorderLayout.CENTER);
		setResizable(F);
		setVisible(T);
	}
	
	private class mouseListen extends MouseAdapter {
		private customRoundedPanel panel;
		private JButton button;
		private int index;
		
		public mouseListen(int index, customRoundedPanel panel) {
			this.setIndex(index);
			this.setPanel(panel);
			this.setButton(button);
		}
		private void setIndex(int index) {
			this.index = index;
		}
		private void setButton(JButton button) {
			this.button = button;
		}
		@Override
		public void mouseEntered(MouseEvent e) {
			getPanel().setBackground(color.getChoice());
    		basePanel[index].setBorder(new EmptyBorder(0, 0, 0, 0));
    		orders[index].setBorder(new EmptyBorder(4, 20, 0, 10));
    		quantityPrice6s[index].setVisible(T);
		}
		
		@Override
		public void mouseExited(MouseEvent e) {
			getPanel().setBackground(Color.WHITE);
    		basePanel[index].setBorder(new EmptyBorder(5, 5, 5, 5));
    		orders[index].setBorder(new EmptyBorder(7, 10, 0, 10));
    		quantityPrice6s[index].setVisible(F);
		}
		public JPanel getPanel() {
			return panel;
		}
		public void setPanel(customRoundedPanel panel) {
			this.panel = panel;
		}
	}
	
	private class paymentListener implements ActionListener {
		private int index;
		
		public paymentListener(int index) {
			this.setIndex(index);
		}
		
		private void setIndex(int index) {
			this.index = index;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			switch (index) {
				case 0:
					cashMethod();
					break;
				case 1:
					creditCardMethod();
					break;
				case 2:
					debitCardMethod();
					break;
				case 3:
					gCashMethod();
					break;
			}
		}
	}
	
	private void cashMethod() {
		afterOptions.setBackground(Color.GREEN);
	}
	
	private void creditCardMethod() {
		afterOptions.setBackground(Color.RED);
	}
	
	private void debitCardMethod() {
		afterOptions.setBackground(Color.BLUE);		
	}
	
	private void gCashMethod() {
		afterOptions.setBackground(Color.CYAN);
	}
	
	protected JComboBox<String> createComboBox(String[] options) {
		JComboBox<String> comboBox = new JComboBox<>(options);
		comboBox.setSelectedIndex(0);
		comboBox.addActionListener(e -> {
			String selectedOption = (String) comboBox.getSelectedItem();
			switch (selectedOption) {
				case "Cash":
					System.out.println(paymentMethods[1]);
					break;
				case "Credit Card":
					System.out.println(paymentMethods[2]);
					break;
				case "Debit Card":
					System.out.println(paymentMethods[3]);
					break;
				case "GCash":
					System.out.println(paymentMethods[4]);
					break;
				default:
					System.out.println("Unknown Payment Method");
			}
		});
		return comboBox;
	}
	
	private static String formatThousands(String number) {
		NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
		return numberFormat.format(Double.parseDouble(number));
	}
}
