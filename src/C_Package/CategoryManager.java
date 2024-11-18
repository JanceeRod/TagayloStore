package C_Package;

import E_Package.Category;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CategoryManager {
    private static final String CSV_FILE_PATH = "categories.csv";
    private static List<Category> categories = new ArrayList<>();

    static {
        loadCategoriesFromCSV();
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
