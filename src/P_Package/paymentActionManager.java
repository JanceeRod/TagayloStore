package P_Package;

import G_Package.customRoundedPanel;
import T_Package.Transaction;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.util.HashMap;
import java.util.Map;

import static B_Package.userDefinitions.centerPaneOnRightPanel;
import static B_Package.userDefinitions.orderRecord;
import static B_Package.userOperations.numbersReset;
import static B_Package.userOperations.panelFinisher;

import static P_Package.paymentFunctions.cashMethod;

public class paymentActionManager extends paymentDefinitions {

    public static class confirmPayment implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("This is Confirm Payment Action Listener");

            double cashAmount = Double.parseDouble(amountTextBox.getText());

            transaction.setCustomer(customerTextBox.getText());
            transaction.setCashAmount(cashAmount);
            transaction.addCustomerOrders(customerOrders);
            transaction.saveToCSV(transactionPath);

            System.out.println(transaction.toCSVString());

            orderRecord.clear();

            centerPaneOnRightPanel.removeAll();

            panelFinisher(centerPaneOnRightPanel);

            centerPaneOnRightPanel.setPreferredSize(new Dimension(0, 0));

            numbersReset();
            transaction = new Transaction();

            frame.dispose();
        }
    }



    public static void printCustomerOrders(HashMap<String, Integer> customerOrders) {
        if (customerOrders.isEmpty()) {
            System.out.println("No customer orders available.");
            return;
        }

        System.out.println("Customer Orders:");
        for (Map.Entry<String, Integer> entry : customerOrders.entrySet()) {
            System.out.println("Product Code: " + entry.getKey() + ", Quantity: " + entry.getValue());
        }
    }

    public static class paymentListener implements ActionListener {
        private int index;

        public paymentListener(int index) {
            this.setIndex(index);
        }

        private void setIndex(int index) {
            this.index = index;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            switch (index) {
                case 0:
                    cashMethod();
                    break;
            }
        }
    }

    public static class mouseListen extends MouseAdapter {
        private customRoundedPanel panel;
        private JButton button;
        private int index;

        public mouseListen(int index, customRoundedPanel panel) {
            this.setIndex(index);
            this.setPanel(panel);
            this.setButton(button);
        }
        private void setIndex(int index) {
            this.index = index;
        }
        private void setButton(JButton button) {
            this.button = button;
        }
        @Override
        public void mouseEntered(MouseEvent e) {
            getPanel().setBackground(color.getChoice());
            basePanel[index].setBorder(new EmptyBorder(0, 0, 0, 0));
            orders[index].setBorder(new EmptyBorder(4, 20, 0, 10));
            quantityPrice6s[index].setVisible(T);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            getPanel().setBackground(Color.WHITE);
            basePanel[index].setBorder(new EmptyBorder(5, 5, 5, 5));
            orders[index].setBorder(new EmptyBorder(7, 10, 0, 10));
            quantityPrice6s[index].setVisible(F);
        }
        public JPanel getPanel() {
            return panel;
        }
        public void setPanel(customRoundedPanel panel) {
            this.panel = panel;
        }
    }
}
