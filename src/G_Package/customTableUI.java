package G_Package;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class customTableUI extends JTable {

    public customTableUI(Object[][] data, String[] columnHeaders) {
        if (data == null || columnHeaders == null || columnHeaders.length == 0) {
            throw new IllegalArgumentException("Data and column headers cannot be empty");
        }

        DefaultTableModel tableModel = new DefaultTableModel(data, columnHeaders);
        setModel(tableModel);

        System.out.println("Column count: " + getColumnModel().getColumnCount());

        setFont(new Font("Arial", Font.PLAIN, 14));
        setRowHeight(25);

        JTableHeader header = getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 16));
        header.setBackground(new Color(70, 130, 180));
        header.setForeground(Color.RED);

        setDefaultRenderer(Object.class, new CustomCellRenderer());
    }

    public void setColumnAlignment(int columnIndex, int alignment) {
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(alignment);
        getColumnModel().getColumn(columnIndex).setCellRenderer(renderer);
    }

    static class CustomCellRenderer extends DefaultTableCellRenderer {
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            if (table.getColumnName(column).equals("Status")) {
                String status = (String) value;
                if ("Out of Stock".equals(status)) {
                    cell.setBackground(Color.RED);
                    cell.setForeground(Color.WHITE);
                } else if ("Low Stock".equals(status)) {
                    cell.setBackground(Color.ORANGE);
                    cell.setForeground(Color.BLACK);
                } else {
                    cell.setBackground(Color.WHITE);
                    cell.setForeground(Color.BLACK);
                }
            }

            if (isSelected) {
                cell.setBackground(new Color(184, 207, 229));
                cell.setForeground(Color.BLACK);
            }

            return cell;
        }
    }
}