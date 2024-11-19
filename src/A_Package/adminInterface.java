package A_Package;
import M_Package.Operations;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.LinkedHashMap;

public class adminInterface extends adminDefinitions {

    public adminInterface() {
        instantiate();

        JFrame mainFrame = new JFrame();
        mainFrame.setTitle("Tagaylo Store - Point-of-Sales System | CSCC 14.1 Final Project");
        mainFrame.setSize(1100, 765);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setResizable(T);

        JPanel topRibbonPanel;
        topRibbonPanel = createCustomPanel(1080, 60, color.getHeader(), null);
        topRibbonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JLabel titlePOS = createCustomLabel("Tagaylo Store POS", color.getTitleColor(), font.getTitleFont(), 0, 0, 0, 0, 0, 0, 0, 0, SwingConstants.RIGHT);

        JPanel javaJivePanel;
        javaJivePanel = createCustomPanel(650, 50, topRibbonPanel, null);
        javaJivePanel.setBorder(BorderFactory.createEmptyBorder(0, 25, 0, 0));

        //add layer
        javaJivePanel.add(titlePOS, BorderLayout.WEST);
        topRibbonPanel.add(javaJivePanel, BorderLayout.WEST);

        JPanel leftRibbonPanel;
        leftRibbonPanel = createCustomPanel(85, 670, color.getLeftSide(), null);
        leftRibbonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 8));

        JPanel centerContainerPanelUp;
        centerContainerPanelUp = createCustomPanel(0, 0, color.getCenterPane(), new BorderLayout());
        centerContainerPanelUp.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 0));

        mainFrame.getContentPane().setLayout(new BorderLayout());
        mainFrame.add(topRibbonPanel, BorderLayout.NORTH);
        mainFrame.add(leftRibbonPanel, BorderLayout.WEST);
        mainFrame.add(centerContainerPanelUp, BorderLayout.CENTER);
        mainFrame.setResizable(F);
        mainFrame.setVisible(T);
        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.out.println("Program is closing. Do cleanup or save data if needed.");
                Operations.clearCSVFile(masterfile);
            }
        });
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void instantiate() {
//        Operations.clearCSVFile(inventory);
        beveragesMenu = saveToDataArray(beverages);
        foodMenu = saveToDataArray(food);

        arrayOf2DArrays.put("Food", foodMenu);
        arrayOf2DArrays.put("Drinks", beveragesMenu);

        inventoryMasterfile = saveToDataArray(masterfile);

        writeArrayToCSV(foodMenu, masterfile);
        writeArrayToCSV(beveragesMenu, masterfile);
        writeArrayToCSV(inventoryMasterfile, inventory);

        processArray(cafeInventory, inventoryMasterfile);

        menuPrint(inventoryMasterfile);
    }

    public static void menuPrint(String[][] dataArray) {
        for (String[] strings : dataArray) {
            System.out.printf("+      " + "%-8s%-25s%-5s\n", strings[0], strings[1], strings[2] + "      +");
        }
    }

    public JPanel createCustomPanel (int width, int height, Object backgroundSource, LayoutManager layout) {
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

    public JLabel createCustomLabel(String text, Color foreground, Font font, int x, int y, int width, int height, int topBorder, int leftBorder, int bottomBorder, int rightBorder, int horizontalAlignment) {
        JLabel label = new JLabel();
        label.setText(text);
        label.setForeground(foreground);
        label.setFont(font);
        label.setBounds(x, y, width, height);
        label.setBorder(new EmptyBorder(topBorder, leftBorder, bottomBorder, rightBorder));
        label.setHorizontalAlignment(horizontalAlignment);
        return label;
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

    public static void inventoryPrinter(String[][] a) {
        for (String[] strings : a) {
            System.out.printf("+       " + "%-8s%-25s%-11s\n", strings[0], strings[2], strings[3] + "      +");
        }
    }
}
