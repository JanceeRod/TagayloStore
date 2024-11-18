package A_Package;

import C_Package.CategoryManager;
import E_Package.Category;
import E_Package.Transaction;
import G_Package.customRoundedPanel;
import G_Package.customColorPallete;
import G_Package.customScrollBarUI;
import G_Package.customFontGallery;
import G_Package.customPopupMenu;
import M_Package.Operations;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class adminDefinitions {

    JFrame mainFrame;

    Operations Operate = new Operations();

    GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

    Double defaultNo = 0.00;
    Double salesTax = 5.25;
    String formattedDefaultNo  = String.format("%.2f", defaultNo);

    public customRoundedPanel[] panel5_;
    public customRoundedPanel[] mainPanelOnCenter_;
    public customRoundedPanel[] pillShape;
    public JButton[] forPanel5_;

    public JButton[] button2_;
    public JLabel[] productName;
    public JLabel[] productPrice;
    public JLabel[] quantityLabel6s;
    public JLabel[] productName6s;
    public JLabel[] productPrice6s;
    public JLabel[] pSLabel;



    //TOP RIBBON
    //Panel
    JPanel topRibbonPanel;
    JPanel javaJivePanel;
    JPanel searchPanel;
    JPanel timePanel;
    JPanel profileButtonPanel;

    //Rounded Panel
    customRoundedPanel tfPanel;

    //Label
    JLabel titlePOS;
    JLabel TimeLabel;
    JLabel DateLabel;

    //Text field
    JTextField searchBox;

    //Button
    JButton profileButton;

    //Extras
    customPopupMenu profileButtonPop;



    //LEFT PANEL
    //Panel
    JPanel leftRibbonPanel;

    //Rounded Panel
    customRoundedPanel[] sideRibbonRoundedPanels;

    //Label
    JLabel[] label2_;

    //Button
    JButton[] sideRibbonButtons;

    //Extras
    String[] sideRibbonLabels;



    //RIGHT PANEL
    //Panel
    JPanel rightRibbonPanel;
    JPanel orderPaneCen;
    JPanel orderPaneTop;
    JPanel orderPaneBot;
    JPanel centerPaneOnRightPanel;

    //Rounded Panel
    customRoundedPanel orderPaneTitleTab;
    customRoundedPanel roundedPanelForCancelButton;
    customRoundedPanel roundedPanelForProceedButton;

    //Label
    JLabel cancelLabel;
    JLabel proceedLabel;

    JLabel[] cartLabelsText;
    JLabel[] cartLabelsNumbers;

    //Button
    JButton cancelButton;
    JButton proceedButton;

    //Extras
    String[] cartLabels;
    int[] labelText1_;

    customScrollBarUI scrollBarForCart;
    JScrollPane scrollPane;


    //CENTER PANEL
    //Panel
    JPanel centerContainerPanelUp;
    JPanel centerContainerPanelDown;
    JPanel mainPanelOnCenter;

    //Rounded Panel
    customRoundedPanel centerPanelMainLayer;

    //Label
    JLabel orderPaneLabel;

    //Button

    //Extras



    //BOOLEAN
    boolean T = true, F = false;


    //graphics
    customColorPallete color = new customColorPallete();
    customFontGallery font = new customFontGallery();


    //data structures
    public static LinkedHashMap<String, Integer> cafeInventory = new LinkedHashMap<>();
    public LinkedHashMap<String, String[][]> arrayOf2DArrays = new LinkedHashMap<>();
    public LinkedHashMap<String, Integer> orderRecord = new LinkedHashMap<>();
    public static List<Transaction> transactionHistory = new ArrayList<>();
    public static List<String[]> searchResults = new ArrayList<>();


    //files
    static String inventory = "cafeInventory.csv";
    static String transactions = "transactionHistory.csv";
    static String masterfile = "inventoryMasterfile.csv";
    static String[][] inventoryMasterfile;
    String[] calculations;


    //new files
    static Map<String, String[][]> categoryDataMap;
    static List<Category> categories = CategoryManager.getCategories();
    static String[][] globalInventory;


    //time and date
    public static LocalTime CT;
    public static LocalDate CD;
    public static DateTimeFormatter CTF, CTF2, CDF;
    public static String TM, TM2, DM;
}