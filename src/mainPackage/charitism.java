package mainPackage;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;

//import mainPackage.Transaction;
//import mainPackage.colorPallete;
//import mainPackage.fontGallery;

public class charitism extends Definitions {

	charitism() {

	}

    public int getQuantityByProductCode(String productCode) {
    	Integer quantity = orderRecord.get(productCode);
    	return quantity;
    }
    
    protected int getProductIndexByCode(HashMap<String, Integer> hashMap, String productCode) {
    	List<String> productCodes = new ArrayList<>(hashMap.keySet());
    	
    	for (int i = 0; i < productCodes.size(); i++) {
    		if (productCodes.get(i).equals(productCode)) {
    			return i;
    		}
    	}
    	return -1;
    }
    
    public String getProductCodeByIndex(int index) {
    	return new ArrayList<>(orderRecord.keySet()).get(index);
    }

	public int getIndexByProductCode(String productCode, LinkedHashMap<String, Integer> orderRecord) {
    	int index = 0;
    	
    	for (Map.Entry<String, Integer> entry : orderRecord.entrySet()) {
    		if (entry.getKey().equals(productCode)) {
    			return index;
    		}
    		index++;
    	}
    	return -1;
    }

	public static LinkedHashMap<String, Integer> readCafeInventory(String filename) {
		LinkedHashMap<String, Integer> inventory = new LinkedHashMap<>();
		
		try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split(",");
				if (parts.length == 2) {
					String productCode = parts[0];
					int quantity = Integer.parseInt(parts[1]);
					inventory.put(productCode, quantity);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return inventory;
	}

}
