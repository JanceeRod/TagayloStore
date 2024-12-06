package C_Package;

import E_Package.Category;

import java.io.*;
import java.util.*;

public class manageCategories {
    private static final String CSV_FILE_PATH = "categories.csv";
    private static List<Category> categories = new ArrayList<>();

    static {
        loadCategoriesFromCSV();
    }

    public static void saveInventoryToCSV(Map<String, String[][]> inventoryCategoryDataMap) {
        // Create a map of category codes to file names based on categories list
        Map<String, String> categoryFileMap = new HashMap<>();
        for (Category category : categories) {
            String fileNameWithoutExtension = category.getFileName().replace(".csv", "");
            categoryFileMap.put(fileNameWithoutExtension, category.getFileName());

            System.out.println(category.getFileName());
        }

        debugPrintCategoryFileMap(categoryFileMap);

        // Iterate over inventoryCategoryDataMap to write data to files
        for (Map.Entry<String, String[][]> entry : inventoryCategoryDataMap.entrySet()) {
            String category = entry.getKey();
            String[][] products = entry.getValue();

            String fileName = categoryFileMap.get(category);
            if (fileName == null) {
                System.out.println("No file mapped for category: " + category);
                continue; // Skip if no file is associated
            }

            // Write the products to the file
            File file = new File(category + ".csv");
            try (PrintWriter pw = new PrintWriter(new FileWriter(file))) {
                for (String[] product : products) {
                    pw.println(String.join(",", product));
                }
                System.out.println("Updated CSV file for category: " + category + " -> " + fileName);
            } catch (IOException e) {
                System.out.println("Error writing to file: " + file.getAbsolutePath());
                e.printStackTrace();
            }
        }
    }

    private static void debugPrintCategoryFileMap(Map<String, String> categoryFileMap) {
        System.out.println("Contents of categoryFileMap:");
        for (Map.Entry<String, String> entry : categoryFileMap.entrySet()) {
            System.out.println("  Category Code: " + entry.getKey() + " -> File Name: " + entry.getValue());
        }
    }



    public static void addProductToCategory(Map<String, String[][]> inventoryCategoryDataMap, String category, String[] newProduct) {
        if (!inventoryCategoryDataMap.containsKey(category)) {
            System.out.println("Category " + category + " does not exist. Adding a new category.");
            inventoryCategoryDataMap.put(category, new String[][] { newProduct });
        } else {
            String[][] currentData = inventoryCategoryDataMap.get(category);
            String[][] updatedData = Arrays.copyOf(currentData, currentData.length + 1);
            updatedData[currentData.length] = newProduct;
            inventoryCategoryDataMap.put(category, updatedData);
        }

        // Confirmation message
        System.out.println("Product added to category \"" + category + "\":");
        System.out.println("  - Code: " + newProduct[0]);
        System.out.println("  - Name: " + newProduct[1]);
        System.out.println("  - Price: " + newProduct[2]);
        System.out.println("  - Image Path: " + newProduct[3]);
    }

    public static void removeProductFromCategory(Map<String, String[][]> inventoryCategoryDataMap, String category, String productCode) {
        if (!inventoryCategoryDataMap.containsKey(category)) {
            System.out.println("Category " + category + " does not exist.");
            // Optionally, you can handle this case with a JOptionPane in the actionListener
        } else {
            String[][] currentData = inventoryCategoryDataMap.get(category);
            boolean productFound = false;
            int productIndex = -1;

            // Find the product by its code
            for (int i = 0; i < currentData.length; i++) {
                if (currentData[i][0].equals(productCode)) {
                    productIndex = i;
                    productFound = true;
                    break;
                }
            }

            if (productFound) {
                // Remove the product by creating a new array without the product
                String[][] updatedData = new String[currentData.length - 1][];
                System.arraycopy(currentData, 0, updatedData, 0, productIndex);  // Copy before the product
                System.arraycopy(currentData, productIndex + 1, updatedData, productIndex, currentData.length - productIndex - 1);  // Copy after the product
                inventoryCategoryDataMap.put(category, updatedData);

                // Confirmation message
                System.out.println("Product with code " + productCode + " removed from category \"" + category + "\".");
            } else {
                System.out.println("Product with code " + productCode + " not found in category \"" + category + "\".");
                // Optionally, you can handle this case with a JOptionPane in the actionListener
            }
        }
    }


    private static void loadCategoriesFromCSV() {
        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String code = parts[0].trim();
                    String fileName = parts[1].trim();
                    categories.add(new Category(code, fileName));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("\nCSV file not found. Creating a new one.");
            saveCategoriesToCSV();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void saveCategoriesToCSV() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(CSV_FILE_PATH))) {
            for (Category category : categories) {
                pw.println(category.getCode() + "," + category.getFileName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Category> getCategories() {
        return new ArrayList<>(categories);
    }

    public static void addCategory(String code, String fileName) {
        for (Category category : categories) {
            if (category.getCode().equals(code)) {
                System.out.println("\nCategory code already exists: " + code);
                return;
            }
        }
        categories.add(new Category(code, fileName));
        System.out.println("\nCategory added: " + code + " - " + fileName);
        sortCategories();
        saveCategoriesToCSV();
    }

    public static void removeCategory(String code) {
        Category toRemove = null;
        for (Category category : categories) {
            if (category.getCode().equals(code)) {
                toRemove = category;
                break;
            }
        }
        if (toRemove != null) {
            categories.remove(toRemove);
            System.out.println("\nCategory removed: " + code);
            sortCategories();
            saveCategoriesToCSV();
        } else {
            System.out.println("\nCategory not found: " + code);
        }
    }

    public static void displayCategories() {
        System.out.println("\nCategories:");
        for (Category category : categories) {
            System.out.println(category);
        }
    }

    public static void sortCategories() {
        categories.sort(Comparator.comparing(Category::getCode));
        System.out.println("\nCategories sorted.");
        saveCategoriesToCSV();
    }
}
