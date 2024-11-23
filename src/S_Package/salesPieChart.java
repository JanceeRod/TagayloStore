package S_Package;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class salesPieChart extends salesChartDefinitions {

    private final Map<String, Double> data;

    public salesPieChart(JPanel panel) {
        this.data = loadDataFromCSV(); // Load data from CSV into a map
        panel.setPreferredSize(new Dimension(400, 400)); // Adjust size as necessary
        panel.add(new JLabel("Pie Chart"));
    }

    private Map<String, Double> loadDataFromCSV() {
        Map<String, Double> dataMap = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("transactionHistory.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                String productName = fields[2].trim();
                double amount = Double.parseDouble(fields[3].trim());

                dataMap.put(productName, dataMap.getOrDefault(productName, 0.0) + amount);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return dataMap;
    }

    public void drawPieChart(Graphics g, JPanel panel) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Calculate total sales value
        double total = data.values().stream().mapToDouble(Double::doubleValue).sum();

        int x = 50, y = 50, diameter = 300;
        int startAngle = 0;
        for (Map.Entry<String, Double> entry : data.entrySet()) {
            int arcAngle = (int) (entry.getValue() / total * 360);
            g2d.setColor(color.getChoice());
            g2d.fillArc(x, y, diameter, diameter, startAngle, arcAngle);
            startAngle += arcAngle;
        }
    }

}
