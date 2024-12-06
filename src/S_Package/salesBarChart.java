package S_Package;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static A_Package.adminDefinitions.color;
import static A_Package.adminDefinitions.productPrices;
import static T_Package.TransactionManager.calculateGrandTotal;

public class salesBarChart {
    private static JPanel panel;
    private static String filterType;

    // Customizable colors
    private static Color barColor = color.getHeader();         // Default bar color
    private static Color labelColor = Color.BLACK;      // Default label color
    private static Color axisColor = Color.DARK_GRAY;   // Default axis color
    private static Color backgroundColor = Color.WHITE; // Default background color

    public salesBarChart(JPanel mainPanelOnCenter, String filterType) {
        this.panel = mainPanelOnCenter;
        this.filterType = filterType;
    }

    public static void setBarColor(Color color) {
        barColor = color;
    }

    public static void setLabelColor(Color color) {
        labelColor = color;
    }

    public static void setAxisColor(Color color) {
        axisColor = color;
    }

    public static void setBackgroundColor(Color color) {
        backgroundColor = color;
    }

    public static void displaySalesChart() {
        Map<String, Double> salesData = loadSalesData();

        // Clear the existing panel
        panel.removeAll();

        // Create a custom chart panel and add it
        ChartPanel chartPanel = new ChartPanel(salesData, filterType);
        chartPanel.setPreferredSize(new Dimension(800, 600));

        panel.setLayout(new BorderLayout());
        panel.add(chartPanel, BorderLayout.CENTER);
        panel.revalidate();
        panel.repaint();
    }

    private static Map<String, Double> loadSalesData() {
        Map<String, Double> salesData = new HashMap<>();
        String csvFile = "transactionHistory.csv";
        String line;

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            // Temporary map to hold all data
            Map<String, Double> allData = new HashMap<>();

            while ((line = br.readLine()) != null) {
                String[] transaction = line.split(",");

                // Parse transaction data
                String date = transaction[1].split(" ")[0]; // Extract the date
                double totalAmount = calculateGrandTotal(transaction[0], productPrices);

                // Aggregate sales by date
                allData.put(date, allData.getOrDefault(date, 0.0) + totalAmount);
            }

            if ("last5Days".equals(filterType)) {
                // Sort dates in descending order and get the 5 most recent dates
                allData.keySet().stream()
                        .sorted((d1, d2) -> LocalDate.parse(d2).compareTo(LocalDate.parse(d1))) // Sort descending
                        .limit(5) // Get the top 5 recent dates
                        .forEach(date -> salesData.put(date, allData.get(date))); // Add to filtered sales data
            } else {
                // For other filters, handle as before
                for (Map.Entry<String, Double> entry : allData.entrySet()) {
                    String date = entry.getKey();
                    double totalAmount = entry.getValue();

                    switch (filterType) {
                        case "2024":
                        case "2023":
                        case "2022":
                            if (date.startsWith(filterType)) {
                                String yearMonth = date.substring(0, 7); // Extract "YYYY-MM"
                                salesData.put(yearMonth, salesData.getOrDefault(yearMonth, 0.0) + totalAmount);
                            }
                            break;
                        case "yearly":
                            String year = date.split("-")[0];
                            salesData.put(year, salesData.getOrDefault(year, 0.0) + totalAmount);
                            break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return salesData;
    }

    // Custom JPanel to draw the bar chart
    // Custom JPanel to draw the bar chart
    private static class ChartPanel extends JPanel {
        private final Map<String, Double> salesData;
        private final String filterType; // Title now tied to this specific instance

        public ChartPanel(Map<String, Double> salesData, String filterType) {
            this.salesData = salesData;
            this.filterType = filterType;
            setBackground(Color.WHITE); // Ensure background is white
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            int width = getWidth();
            int height = getHeight();

            // Set margins and title space
            int margin = 50;
            int titleSpace = 40; // Space reserved for the title
            int chartWidth = width - 2 * margin;
            int chartHeight = height - 2 * margin - titleSpace; // Deduct title space from chart height

            // Draw title
            String chartTitle = "Sales Data (" + this.filterType + ")"; // Use instance-specific filterType
            g2d.setFont(new Font("Arial", Font.BOLD, 16)); // Title font
            FontMetrics metrics = g2d.getFontMetrics();
            int titleWidth = metrics.stringWidth(chartTitle);
            g2d.setColor(Color.BLACK); // Title color
            g2d.drawString(chartTitle, (width - titleWidth) / 2, margin - 10); // Center the title

            // Draw axes
            g2d.setColor(Color.BLACK); // Axes color
            g2d.drawLine(margin, height - margin, width - margin, height - margin); // X-axis
            g2d.drawLine(margin, margin + titleSpace, margin, height - margin);     // Y-axis

            // Calculate bar widths and max value
            int barWidth = Math.max(chartWidth / Math.max(1, salesData.size()), 10);
            double maxValue = salesData.values().stream().max(Double::compareTo).orElse(1.0);

            // Set the font for labels
            g2d.setFont(new Font("Arial", Font.PLAIN, 12));

            // Draw bars
            int x = margin;
            for (Map.Entry<String, Double> entry : salesData.entrySet()) {
                String label = entry.getKey();
                double value = entry.getValue();

                int barHeight = (int) ((value / maxValue) * chartHeight);
                g2d.setColor(barColor); // Bar color
                g2d.fillRect(x, height - margin - barHeight, barWidth - 10, barHeight);

                // Add labels
                g2d.setColor(labelColor); // Label color
                g2d.drawString(label, x, height - margin + 15); // Label below the bar
                g2d.drawString(String.format("%.2f", value), x, height - margin - barHeight - 5); // Value above the bar

                x += barWidth;
            }
        }
    }
}