package A_Package;

import C_Package.manageCategories;

import E_Package.Category;

import G_Package.customRoundedPanel;
import G_Package.customColorPallete;
import G_Package.customScrollBarUI;
import G_Package.customFontGallery;
import G_Package.customPopupMenu;

import T_Package.TransactionManager;

import javax.swing.*;
import java.util.*;

public class adminDefinitions {

    JFrame mainFrame;

    static Double defaultNo = 0.00;
    static Double salesTax = 5.25;
    static String formattedDefaultNo  = String.format("%.2f", defaultNo);

    public static customRoundedPanel[] mainPanelOnCenter_;
    public static customRoundedPanel[] pillShape;

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
    public static LinkedHashMap<String, Integer> orderRecord = new LinkedHashMap<>();
    public static List<String[]> searchResults = new ArrayList<>();
    public static List<Map<String, Integer>> parsedProducts;
    public static Map<String, Double> productPrices = new HashMap<>();


    //files
    static String inventory = "cafeInventory.csv";
    static String transactions = "transactionHistory.csv";
    static String purchases = "transactionPurchases.csv";
    static String masterfile = "inventoryMasterfile.csv";
    static String[] calculations;

    static TransactionManager manager;


    //new files
    static Map<String, String[][]> categoryDataMap;
    static List<Category> categories = manageCategories.getCategories();
    static String[][] globalInventory;
}