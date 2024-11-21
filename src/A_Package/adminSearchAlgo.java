package A_Package;

import G_Package.customRoundedPanel;
import G_Package.customScrollBarUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.SwingConstants.CENTER;

public class adminSearchAlgo extends adminDefinitions {

    public static void whenSearched() {
        centerPanelMainLayer.removeAll();
        mainPanelOnCenter.removeAll();

        centerPanelMainLayer.setLayout(new BorderLayout());
        centerPanelMainLayer.setBorder(BorderFactory.createEmptyBorder(0, 15, 1, 10));

        searchResult = new JLabel();
        searchResult.setText("Search Results: ");
        searchResult.setHorizontalAlignment(SwingConstants.LEFT);
        searchResult.setFont(font.getProductNameREGULAR());
        searchResult.setForeground(Color.GRAY);

        closeSearch = new JButton();
        closeSearch.setBackground(centerPanelMainLayer.getBackground());
        closeSearch.setForeground(Color.GRAY);
        closeSearch.setBorder(BorderFactory.createEmptyBorder());
        closeSearch.setBorder(new EmptyBorder(5, 0, 5, 0));
        closeSearch.setEnabled(T);
        closeSearch.setPreferredSize(new Dimension(65, 25));
        closeSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchBox.setText("Search products...");
                searchBox.addKeyListener(new adminActionManager.keyListen());
                sideRibbonButtons[0].doClick();
            }
        });

        customRoundedPanel pillShape = new customRoundedPanel(25);
        pillShape.setBackground(color.getInactiveButton());
        pillShape.setBorder(new EmptyBorder(0, 0, 0, 0));
        pillShape.setLayout(new GridLayout(1,1));
        closeSearch.add(pillShape);

        JLabel pSLabel = new JLabel();
        pSLabel.setText("close");
        pSLabel.setHorizontalAlignment(CENTER);
        pSLabel.setFont(font.getProductNameREGULAR());
        pSLabel.setForeground(Color.GRAY);
        pillShape.add(pSLabel);
        closeSearch.add(pillShape);

        centerPanelMainLayer.add(searchResult, BorderLayout.WEST);
        centerPanelMainLayer.add(closeSearch, BorderLayout.EAST);

        JPanel resultPanel = new JPanel();
        resultPanel.setBackground(mainPanelOnCenter.getBackground());
        resultPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        resultPanel.setLayout(new GridLayout(0, 1, 0, 0));

        int size = searchResults.size();

        JButton[] results = new JButton[size];
        customRoundedPanel[] resultsPane = new customRoundedPanel[size];
        JLabel[] productCode4s = new JLabel[size];
        JLabel[] productName4s = new JLabel[size];
        JLabel[] productPrice4s = new JLabel[size];

        for (int i = 0; i < size; i++) {
            String[] result = searchResults.get(i);

            Color panelColor = (i % 2 == 0) ? resultPanel.getBackground() : color.getOrderPane();

            results[i] = new JButton();
            results[i].setBackground(resultPanel.getBackground());
            results[i].setBorder(new EmptyBorder(0, 10, 0, 10));
            results[i].setPreferredSize(new Dimension(605, 45));
            results[i].setLayout(new GridLayout(1, 1));
            results[i].addActionListener(new adminActionManager.menuButtons(i, null, result[0]));

            resultsPane[i] = new customRoundedPanel(15);
            resultsPane[i].setBackground(panelColor);
            resultsPane[i].setBorder(new EmptyBorder(0, 40, 0, 40));
            resultsPane[i].setLayout(new BorderLayout());

            results[i].add(resultsPane[i]);
            resultPanel.add(results[i]);

            productCode4s[i] = new JLabel();
            productCode4s[i].setText(result[0]);
            productCode4s[i].setForeground(Color.GRAY);
            productCode4s[i].setFont(font.getProductNameREGULAR());
            productCode4s[i].setPreferredSize(new Dimension(55, 15));

            productName4s[i] = new JLabel();
            productName4s[i].setText(result[1]);
            productName4s[i].setForeground(Color.DARK_GRAY);
            productName4s[i].setFont(font.getProductNameREGULAR());
            productName4s[i].setPreferredSize(new Dimension(350, 15));

            productPrice4s[i] = new JLabel();
            productPrice4s[i].setText("₱" + result[2] + ".00");
            productPrice4s[i].setForeground(Color.GRAY);
            productPrice4s[i].setFont(font.getProductNameREGULAR());
            productPrice4s[i].setHorizontalAlignment(SwingConstants.RIGHT);
            productPrice4s[i].setPreferredSize(new Dimension(60, 15));

            resultsPane[i].add(productCode4s[i], BorderLayout.WEST);
            resultsPane[i].add(productPrice4s[i], BorderLayout.EAST);
            resultsPane[i].add(productName4s[i], BorderLayout.CENTER);
        }

        searchResult.setText("Search Results:   " + size);

        JScrollPane scrollPane = new JScrollPane(resultPanel);
        customScrollBarUI scrollBarUI3 = new customScrollBarUI();

        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUI(scrollBarUI3);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        scrollBarUI3.setCustomUI(Color.GRAY, Color.DARK_GRAY, Color.GREEN);

        mainPanelOnCenter.add(scrollPane);
        adminOperations.panelFinisher(centerContainerPanelUp);
    }
}
