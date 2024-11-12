package mainPackage;

import E_Package.Transaction;
import G_Package.RoundedPanel;
import G_Package.colorPallete;
import G_Package.fontGallery;

import javax.swing.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class Definitions {

    public RoundedPanel[] panel5_;
    public RoundedPanel[] mainPanelOnCenter_;
    public RoundedPanel[] curved1;
    public RoundedPanel[] pillShape;
    public JButton[] forPanel5_;
    public JButton[] button1_;
    public JButton[] button2_;
    public JLabel[] productName;
    public JLabel[] productPrice;
    public JLabel[] label4_;
    public JLabel[] quantityLabel6s;
    public JLabel[] productName6s;
    public JLabel[] productPrice6s;
    public JLabel[] pSLabel;



    int number, panelWidth;
    boolean T = true, F = false;



    colorPallete color = new colorPallete();
    fontGallery font = new fontGallery();



    public LinkedHashMap<String, Integer> cafeInventory = new LinkedHashMap<>();
    public LinkedHashMap<String, String[][]> arrayOf2DArrays = new LinkedHashMap<>();
    public LinkedHashMap<String, Integer> orderRecord = new LinkedHashMap<>();
    public static List<Transaction> transactionHistory = new ArrayList<>();
    public static List<String[]> searchResults = new ArrayList<>();



    static String beverages = "beverages.csv";
    static String food = "food.csv";
    static String inventory = "cafeInventory.csv";
    static String transactions = "transactionHistory.csv";
    static String masterfile = "inventoryMasterfile.csv";
    static String[][] beveragesMenu;
    static String[][] foodMenu;
    static String[][] inventoryMasterfile;
    String[] calculations;



    public static LocalTime CT;
    public static LocalDate CD;
    public static DateTimeFormatter CTF, CTF2, CDF;
    public static String TM, TM2, DM;
}
