package S_Package;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

public class salesBarChart {
    private static JPanel mainPanelOnCenter;

    public salesBarChart(JPanel mainPanelOnCenter) {
        this.mainPanelOnCenter = mainPanelOnCenter;
    }

    public static void displaySalesChart() {
        Map<String, Double> salesData = loadSalesData();

        // Clear the existing panel
        mainPanelOnCenter.removeAll();

        // Create a custom chart panel and add it
        ChartPanel chartPanel = new ChartPanel(salesData);
        chartPanel.setPreferredSize(new Dimension(800, 600));

        mainPanelOnCenter.setLayout(new BorderLayout());
        mainPanelOnCenter.add(chartPanel, BorderLayout.CENTER);
        mainPanelOnCenter.revalidate();
        mainPanelOnCenter.repaint();
    }

    private static Map<String, Double> loadSalesData() {
        Map<String, Double> salesData = new HashMap<>();
        String csvFile = "transactionHistory.csv";
        String line;
        String csvSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            // Skip the header line
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] transaction = line.split(csvSplitBy);

                // Parse transaction data
                String date = transaction[1].split(" ")[0]; // Extract the date
                double totalAmount = Double.parseDouble(transaction[5]); // Total amount

                // Aggregate sales by date
                salesData.put(date, salesData.getOrDefault(date, 0.0) + totalAmount);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return salesData;
    }

    // Custom JPanel to draw the bar chart
    private static class ChartPanel extends JPanel {
        private final Map<String, Double> salesData;

        public ChartPanel(Map<String, Double> salesData) {
            this.salesData = salesData;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            int width = getWidth();
            int height = getHeight();

            // Set margins
            int margin = 50;
            int chartWidth = width - 2 * margin;
            int chartHeight = height - 2 * margin;

            // Draw axes
            g2d.drawLine(margin, height - margin, width - margin, height - margin); // X-axis
            g2d.drawLine(margin, margin, margin, height - margin);                 // Y-axis

            // Calculate bar widths and max value
            int barWidth = chartWidth / salesData.size();
            double maxValue = salesData.values().stream().max(Double::compareTo).orElse(1.0);

            // Draw bars
            int x = margin;
            for (Map.Entry<String, Double> entry : salesData.entrySet()) {
                String date = entry.getKey();
                double value = entry.getValue();

                int barHeight = (int) ((value / maxValue) * chartHeight);
                g2d.setColor(Color.BLUE);
                g2d.fillRect(x, height - margin - barHeight, barWidth - 10, barHeight);

                // Add labels
                g2d.setColor(Color.BLACK);
                g2d.drawString(date, x + 5, height - margin + 15);  // Date label
                g2d.drawString(String.format("%.2f", value), x + 5, height - margin - barHeight - 5); // Value label

                x += barWidth;
            }
        }
    }
}
