package B_Package;

import E_Package.Category;
import G_Package.customRoundedPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class userOperations extends userDefinitions {

    public static void extractProductPrices(String[][] globalInventory, Map<String, Double> productPrices) {
        productPrices.clear();

        for (String[] row : globalInventory) {
            if (row.length > 2) {
                String productCode = row[0];
                try {
                    double price = Double.parseDouble(row[2]);
                    productPrices.put(productCode, price);
                } catch (NumberFormatException e) {
                    System.err.println("Invalid price format for product code: " + productCode);
                }
            }
        }
    }

    public static void extractProductNames(String[][] globalInventory, Map<String, String> productNames) {
        productNames.clear();

        for (String[] row : globalInventory) {
            if (row.length > 1) {
                String productCode = row[0];
                String productName = row[1];

                productNames.put(productCode, productName);
            }
        }
    }

    public static void updateIndicator() {
        mainPanelOnCenter.setLayout(new BorderLayout());

        JLabel notice = new JLabel();
        notice.setText("We're still working on this page :(");
        notice.setFont(font.getProductPriceBOLD());
        notice.setForeground(Color.LIGHT_GRAY);
        notice.setHorizontalAlignment(SwingConstants.CENTER);

        mainPanelOnCenter.add(notice, BorderLayout.CENTER);
    }

    public static void numbersReset() {
        for (int i = 0; i < 3; i++) {
            cartLabelsNumbers[i].setText(formattedDefaultNo);
        }
    }

    public static void panelFinisher(JPanel panel) {
        panel.revalidate();
        panel.repaint();
    }

    public static void buttonColorReset(JPanel[] panel, int buttonIndex, Color a, Color b) {
        for (JPanel jPanel : panel) {
            jPanel.setBackground(a);
        }
        panel[buttonIndex].setBackground(b);
    }

    //utility method
    public static String toTitleCase(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        String lowerCased = input.toLowerCase();
        return Character.toUpperCase(lowerCased.charAt(0)) + lowerCased.substring(1);
    }


    //To Array[][] conversion
    public static Map<String, String[][]> convertCategoriesToArrays(List<Category> categories) {
        Map<String, String[][]> dataArrays = new HashMap<>();

        for (Category category : categories) {
            String fileName = category.getFileName();
            String categoryName = fileName.substring(0, fileName.lastIndexOf('.'));

            String[][] dataArray = saveToDataArray(fileName);
            if (dataArray.length > 0) {
                System.out.println("Loaded data for category: " + categoryName);
            } else {
                System.out.println("No data found in: " + fileName);
            }
            dataArrays.put(categoryName, dataArray);
        }
        return dataArrays;
    }


    //for Categories
    public static void createFilesFromCategories(List<Category> categories) {
        for (Category category : categories) {
            String fileName = category.getFileName();
            File file = new File(fileName);

            if (!file.exists()) {
                try {
                    if (file.createNewFile()) {
                        System.out.println("File created: " + fileName);
                    } else {
                        System.out.println("File already exists: " + fileName);
                    }
                } catch (IOException e) {
                    System.out.println("An error occurred while creating the file: " + fileName);
                    e.printStackTrace();
                }
            } else {
                System.out.println("File already exists: " + fileName);
            }
        }
    }

    public static void deleteFiles(List<Category> categories) {
        for (Category category : categories) {
            String fileName = category.getFileName();
            File file = new File(fileName);

            if (file.exists()) {
                if (file.delete()) {
                    System.out.println("File deleted: " + fileName);
                } else {
                    System.out.println("Failed to delete file: " + fileName);
                }
            } else {
                System.out.println("File not found: " + fileName);
            }
        }
    }


    //Data Structure Modifiers
    public static void processArrayToHashMap(String[][] array, LinkedHashMap<String, Integer> resultMap) {
        for (String[] row : array) {
            if (row.length > 0) {
                String key = row[0];
                int value = 20;
                resultMap.put(key, value);
            }
        }
    }

    public static void writeAllArraysToMasterFile(Map<String, String[][]> dataArrays, String masterFilePath) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(masterFilePath))) {
            for (Map.Entry<String, String[][]> entry : dataArrays.entrySet()) {
                String categoryName = entry.getKey();
                String[][] dataArray = entry.getValue();
                writeArrayToCSV(dataArray, writer);
            }
            System.out.println("Master file updated successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeArrayToCSV(String[][] array, PrintWriter writer) {
        for (String[] row : array) {
            for (int i = 0; i < row.length - 1; i++) {
                writer.print(row[i] + ",");
            }
            writer.println(row[row.length - 1]);
        }
    }

    public static String[][] saveToDataArray(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            long lineCount = reader.lines().count();
            BufferedReader reader1 = new BufferedReader(new FileReader(fileName));

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

    public static void searchProducts(String searchQuery) {
        searchResults.clear();
        boolean found = false;

        System.out.println("Query: " + searchQuery);

        for (Map.Entry<String, String[][]> entry : categoryDataMap.entrySet()) {
            String[][] productsArray = entry.getValue();

            for (String[] product : productsArray) {
                String productCode = product[0];
                String productName = product[1];
                String productPrice = product[2];

                if (productName.toLowerCase().contains(searchQuery.toLowerCase()) ||
                        productCode.contains(searchQuery) ||
                        productPrice.contains(searchQuery)) {

                    System.out.println("Product found in " + entry.getKey() + ": " + productCode + "   " + productName + " - ₱" + productPrice);
                    searchResults.add(product);
                    found = true;
                }
            }
        }
        if (!found) {
            System.out.println("No products found for the query: " + searchQuery);
        }
    }

    public static void clearCSVFile(String filePath) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            writer.close();
            System.out.println("CSV file cleared successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //Inventory Info
    public static String getProductName(String productCode) {
        for (Map.Entry<String, String[][]> entry : categoryDataMap.entrySet()) {
            String[][] categoryArray = entry.getValue();

            for (String[] product : categoryArray) {
                if (product[0].equals(productCode)) {
                    return product[1];
                }
            }
        }
        return "Unknown Product";
    }

    public static String[] getPricesArray(String[] productCodes) {
        String[] pricesArray = new String[productCodes.length];

        for (int i = 0; i < productCodes.length; i++) {
            pricesArray[i] = getProductPrice(productCodes[i]);
        }

        return pricesArray;
    }

    public static String getProductPrice(String productCode) {
        for (Map.Entry<String, String[][]> entry : categoryDataMap.entrySet()) {
            String[][] categoryArray = entry.getValue();

            for (String[] product : categoryArray) {
                if (product[0].equals(productCode)) {
                    return product[2];
                }
            }
        }
        return "0.0";
    }

    public static String getProductCode(String productName) {
        for (Map.Entry<String, String[][]> entry : categoryDataMap.entrySet()) {
            String[][] categoryArray = entry.getValue();

            for (String[] product : categoryArray) {
                if (product[1].equalsIgnoreCase(productName)) {
                    return product[0];
                }
            }
        }
        return "Unknown Product";
    }

    public static void printHashMap(HashMap<String, Integer> hashMap) {
        System.out.println("\nHashMap Contents:");
        for (Map.Entry<String, Integer> entry : hashMap.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    public static void menuPrint(String[][] dataArray) {
        for (String[] strings : dataArray) {
            System.out.printf("+      " + "%-8s%-45s%-5s\n", strings[0], strings[1], strings[2]);
        }
    }
}