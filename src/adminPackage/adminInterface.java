package adminPackage;
import G_Package.customPopupMenu;
import G_Package.customTableUI;
import mainPackage.Operations;
import mainPackage.userInterface;

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

        instantiate();

        topRibbon();

        mainFrame.getContentPane().setLayout(new BorderLayout());
        mainFrame.add(topRibbonPanel, BorderLayout.NORTH);
//        mainFrame.add(leftRibbonPanel, BorderLayout.WEST);
//        mainFrame.add(centerContainerPanelUp, BorderLayout.CENTER);
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

    }

    public void topRibbon() {

        topRibbonPanel = createCustomPanel(1080, 60, color.getHeader(), null);
        topRibbonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 10));

        javaJivePanel = createCustomPanel(650, 50, topRibbonPanel, null);
        javaJivePanel.setBorder(BorderFactory.createEmptyBorder(0, 25, 0, 0));

        titlePOS = createCustomLabel("Tagaylo Store POS", color.getTitleColor(), font.getTitleFont(), 0, 0, 0, 0, 0, 0, 0, 20, SwingConstants.LEFT);

        tfPanel = createCustomRoundedPanel(20, 0, 15, 1, 15, color.getSearch(), new BorderLayout());

        searchBox = new JTextField("Search products...");
        searchBox.setPreferredSize(new Dimension(425, 29));
        searchBox.setCaretPosition(0);
        searchBox.setFont(font.getFG4());
        searchBox.setBackground(tfPanel.getBackground());
        searchBox.setForeground(Color.GRAY);
        searchBox.setHorizontalAlignment(SwingConstants.LEFT);
        searchBox.setBorder(BorderFactory.createEmptyBorder());
        searchBox.addKeyListener(new userInterface.keyListen());
        searchBox.addFocusListener(new userInterface.focusListen());
        searchBox.getDocument().addDocumentListener(new userInterface.documentListen());
        tfPanel.add(searchBox);

        searchPanel = createCustomPanel(300, 50, topRibbonPanel, new BorderLayout());
        searchPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

        timePanel = createCustomPanel(500, 50, topRibbonPanel, new BorderLayout());
        timePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 25));

        profileButtonPanel = createCustomPanel(50, 50, Color.RED, new BorderLayout());
        profileButtonPanel.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));

        profileButton = new JButton();
        profileButton.setBackground(Color.BLUE);
        profileButton.setBorder(BorderFactory.createEmptyBorder());
        profileButton.setLayout(new GridLayout(1,1));
        profileButton.setEnabled(T);
//		profileButton.addActionListener(new menuButtons(-1, null, null));

        profileButtonPop = new customPopupMenu();
        profileButtonPop.addMenuItem("Settings", e -> JOptionPane.showMessageDialog(mainFrame, "This is Settings"));
        profileButtonPop.addMenuItem("About Us?", e -> JOptionPane.showMessageDialog(mainFrame, "This is about us!"));
        profileButtonPop.addMenuItem("Log Out", e -> JOptionPane.showMessageDialog(mainFrame, "Logged out"));
        profileButtonPop.addMenuItem("Help", e -> JOptionPane.showMessageDialog(mainFrame, "Help"));

        profileButton.addActionListener(e -> {
            int x = profileButton.getWidth() - profileButtonPop.getPreferredSize().width;
            int y = profileButton.getHeight();
            profileButtonPop.show(profileButton, x, y);
        });

        profileButtonPanel.add(profileButton, BorderLayout.CENTER);

        TimeLabel = createCustomLabel(null, Color.GRAY, font.gettCF(), 0, 0, 0, 0, 0, 0, 0, 0, SwingConstants.LEFT);
        DateLabel = createCustomLabel(null, Color.GRAY, font.getdCF(), 0, 0, 0, 0, 0, 0, 0, 0, SwingConstants.LEFT);

        searchPanel.add(tfPanel);
        timePanel.add(TimeLabel, BorderLayout.EAST); //timePanel.add(DateLabel, BorderLayout.CENTER);

        javaJivePanel.add(titlePOS, BorderLayout.WEST);
        javaJivePanel.add(searchPanel, BorderLayout.CENTER);

        topRibbonPanel.add(javaJivePanel, BorderLayout.WEST);
//		topRibbonPanel.add(timePanel, BorderLayout.EAST);
        topRibbonPanel.add(profileButtonPanel, BorderLayout.EAST);

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
