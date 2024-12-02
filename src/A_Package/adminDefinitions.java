package A_Package;

import C_Package.manageCategories;
import E_Package.Category;
import E_Package.Transaction;
import G_Package.customRoundedPanel;
import G_Package.customColorPallete;
import G_Package.customScrollBarUI;
import G_Package.customFontGallery;
import G_Package.customPopupMenu;
import B_Package.userOperations;

import javax.swing.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class adminDefinitions {

    JFrame mainFrame;

    userOperations Operate = new userOperations();

    static Double defaultNo = 0.00;
    static Double salesTax = 5.25;
    static String formattedDefaultNo  = String.format("%.2f", defaultNo);

    public static customRoundedPanel[] mainPanelOnCenter_;
    public static customRoundedPanel[] pillShape;

    public static JButton[] button2_;
    public JLabel[] productName;
    public JLabel[] productPrice;
    public static JLabel[] quantityLabel6s;
    public static JLabel[] productName6s;
    public static JLabel[] productPrice6s;
    public static JLabel[] pSLabel;

    //Search
    static JLabel searchResult;
    static JButton closeSearch;


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
    static JTextField searchBox;

    //Button
    JButton profileButton;

    //Extras
    customPopupMenu profileButtonPop;



    //LEFT PANEL
    //Panel
    JPanel leftRibbonPanel;

    //Rounded Panel
    static customRoundedPanel[] sideRibbonRoundedPanels;

    //Label
    JLabel[] label2_;

    //Button
    static JButton[] sideRibbonButtons;

    //Extras
    String[] sideRibbonLabels;



    //RIGHT PANEL
    //Panel
    JPanel rightRibbonPanel;
    static JPanel orderPaneCen;
    JPanel orderPaneTop;
    JPanel orderPaneBot;
    public static JPanel centerPaneOnRightPanel;

    //Rounded Panel
    customRoundedPanel orderPaneTitleTab;
    static customRoundedPanel roundedPanelForCancelButton;
    static customRoundedPanel roundedPanelForProceedButton;

    //Label
    static JLabel[] cartLabelsNumbers;

    //Button
    static JButton cancelButton;
    static JButton proceedButton;

    //Extras
    int[] labelText1_;

    customScrollBarUI scrollBarForCart;


    //CENTER PANEL
    //Panel
    static JPanel centerContainerPanelUp;
    static JPanel centerContainerPanelDown;
    static JPanel mainPanelOnCenter;
    static JPanel mainPanelOnCenters;

    //Rounded Panel
    static customRoundedPanel centerPanelMainLayer;
    static customRoundedPanel[] perOrder;

    //Label
    static JLabel orderPaneLabel;

    //Button
    static JButton[] perOrderButtons;

    //Extras



    //BOOLEAN
    static boolean T = true;
    static boolean F = false;


    //graphics
    static customColorPallete color = new customColorPallete();
    static customFontGallery font = new customFontGallery();


    //data structures
    public static LinkedHashMap<String, Integer> cafeInventory = new LinkedHashMap<>();
    public LinkedHashMap<String, String[][]> arrayOf2DArrays = new LinkedHashMap<>();
    public static LinkedHashMap<String, Integer> orderRecord = new LinkedHashMap<>();
    public static List<Transaction> transactionHistory = new ArrayList<>();
    public static List<String[]> searchResults = new ArrayList<>();


    //files
    static String inventory = "cafeInventory.csv";
    static String transactions = "transactionHistory.csv";
    static String masterfile = "inventoryMasterfile.csv";
    static String[][] inventoryMasterfile;
    public static String[][] transactionHistory2D;
    static String[] calculations;


    //new files
    static Map<String, String[][]> categoryDataMap;
    static List<Category> categories = manageCategories.getCategories();
    static String[][] globalInventory;


    //time and date
    public static LocalTime CT;
    public static LocalDate CD;
    public static DateTimeFormatter CTF, CTF2, CDF;
    public static String TM, TM2, DM;
}