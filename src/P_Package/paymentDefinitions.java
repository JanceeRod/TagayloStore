package P_Package;

import E_Package.Transaction;
import G_Package.customColorPallete;
import G_Package.customFontGallery;
import G_Package.customRoundedPanel;

import javax.swing.*;
import java.util.HashMap;

public class paymentDefinitions {

    static Transaction transaction = new Transaction();

    static customColorPallete color = new customColorPallete();
    static customFontGallery font = new customFontGallery();

    static boolean T = true, F = false;

    public static customRoundedPanel[] orders;

    static protected HashMap<String, Integer> customerOrders;
    protected String[] calculations;

    public static JPanel afterOptions;

    static double total = 0;

    static JFrame frame;

    //top panel
    JPanel panel1;
    JLabel label1;

    //bottom panel
    JPanel panel2;

    static JTextField customerTextBox;
    static JTextField amountTextBox;


    //left panel
    JPanel panel3s;
    JPanel summaryPanel2s;
    JPanel summaryPanel;

    customRoundedPanel summaryPanel2v;

    JLabel summaryLabel;

    JLabel[] quantityLabel6s;
    JLabel[] productName6s;
    JLabel[] productPrice6s;

    static JLabel[] quantityPrice6s;
    static JPanel[] basePanel;


    //center panel
    static JPanel panel3;
    static JPanel panelForPurchaseTotal;
    static JPanel amountDueAndTotalPrice;
    static JPanel optionsPanel;

    static customRoundedPanel options;

    static JLabel totalPriceLabel;
    static JLabel amountDueLabel;
    static JLabel confirmOrderLabel;

    static JButton forOptions;

    //path
    static String transactionPath = "transactionHistory.csv";
}
