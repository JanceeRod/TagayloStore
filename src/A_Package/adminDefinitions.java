package A_Package;

import E_Package.Transaction;
import G_Package.customColorPallete;
import G_Package.customFontGallery;
import G_Package.customPopupMenu;
import G_Package.customRoundedPanel;

import javax.swing.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class adminDefinitions {



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

    //bool
    boolean T = true, F = false;

    //graphics
    static customColorPallete color = new customColorPallete();
    static customFontGallery font = new customFontGallery();

    //data structures
    public LinkedHashMap<String, Integer> cafeInventory = new LinkedHashMap<>();
    public LinkedHashMap<String, String[][]> arrayOf2DArrays = new LinkedHashMap<>();
    public static List<Transaction> transactionHistory = new ArrayList<>();
    public static List<String[]> searchResults = new ArrayList<>();

    //files
    static String beverages = "beverages.csv";
    static String food = "food.csv";
    static String inventory = "cafeInventory.csv";
    static String transactions = "transactionHistory.csv";
    static String masterfile = "inventoryMasterfile.csv";

    //2D arrays
    static String[] inventoryHeaders = {"ID", "Product Name", "Price"};
    static String[][] beveragesMenu;
    static String[][] foodMenu;
    static String[][] inventoryMasterfile;
    String[] calculations;

    //time
    public static LocalTime CT;
    public static LocalDate CD;
    public static DateTimeFormatter CTF, CTF2, CDF;
    public static String TM, TM2, DM;
}
