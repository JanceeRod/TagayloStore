package mainPackage;

import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class Operations extends Definitions {

    /**
     * i have no idea what this function actually
     * does but i will pretend that i do for the sake
     * of this comment test hehe
     *
     * @param productsArray and searchQuery
     */
    public static void searchProducts(String[][] productsArray, String searchQuery) {
        searchResults.clear();
        boolean found = false;

        System.out.println("Query: " + searchQuery);
        for (String[] product : productsArray) {
            String productCode = product[0];
            String productName = product[1];
            String productPrice = product[2];

            if (productName.toLowerCase().contains(searchQuery.toLowerCase()) || productCode.contains(searchQuery) || productPrice.contains(searchQuery)) {
                System.out.println("Product found: " + productCode + "   " + productName + " - ₱" + productPrice);
                searchResults.add(product);
                found = true;
            }
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

    public static String getProductName(String productCode) {
        for (String[] product : beveragesMenu) {
            if (product[0].equals(productCode)) {
                return product[1];
            }
        }

        for (String[] product : foodMenu) {
            if (product[0].equals(productCode)) {
                return product[1];
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
        for (String[] product : beveragesMenu) {
            if (product[0].equals(productCode)) {
                return product[2];
            }
        }

        for (String[] product : foodMenu) {
            if (product[0].equals(productCode)) {
                return product[2];
            }
        }
        return "0.0";
    }

    public static String getProductCode(String productName) {
        for (String[] product : beveragesMenu) {
            if (product[1].equals(productName)) {
                return product[0];
            }
        }

        for (String[] product : foodMenu) {
            if (product[1].equals(productName)) {
                return product[0];
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
        for (int i = 0; i < dataArray.length; i++) {
            System.out.printf("+      "+ "%-8s%-25s%-5s\n", dataArray[i][0], dataArray[i][1], dataArray[i][2] + "      +");
        }
    }

    public static void inventoryPrinter(String[][] a) {
        for (int i = 0; i < a.length; i++) {
            System.out.printf("+       "+ "%-8s%-25s%-11s\n", a[i][0], a[i][2], a[i][3] + "      +");
        }
    }
}
