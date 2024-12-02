package P_Package;

import B_Package.userActionManager;
import B_Package.userOperations;
import G_Package.customRoundedPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class paymentFunctions extends paymentDefinitions {

    static SpringLayout SL = new SpringLayout();

    public static void cashMethod() {
        afterOptions.removeAll();
        afterOptions.setLayout(SL);

        afterCashMethod();

        afterOptions.revalidate();
        afterOptions.repaint();
    }

    private static void afterCashMethod() {
        
        int textBoxWidth = afterOptions.getWidth() - 55;

        System.out.println("this is after cash method");

        JLabel customerName = new JLabel();
        customerName.setText("Customer:");
        customerName.setFont(font.getProductNameREGULAR());

        customRoundedPanel customer = userOperations.createCustomRoundedPanel(20, 0, 15, 1, 15, color.getSearch(), new BorderLayout());

        customerTextBox = getjTextField("", textBoxWidth, customer);
        customerTextBox.addFocusListener(new userActionManager.focusListen());

        customer.add(customerTextBox);


        JLabel amountInCash = new JLabel();
        amountInCash.setText("Amount in Cash:");
        amountInCash.setFont(font.getProductNameREGULAR());

        customRoundedPanel amount = userOperations.createCustomRoundedPanel(20, 0, 15, 1, 15, color.getSearch(), new BorderLayout());

        amountTextBox = getjTextField("PHP 0.00", textBoxWidth, amount);
//        amountTextBox.addKeyListener(new userActionManager.keyListen());
        amountTextBox.addFocusListener(new userActionManager.focusListen());
//        amountTextBox.getDocument().addDocumentListener(new userActionManager.documentListen());

        amount.add(amountTextBox);

        JLabel changeLabel = new JLabel();
        changeLabel.setText("Change:");
        changeLabel.setFont(font.getProductNameREGULAR());

        JLabel changeTextLabel = new JLabel();
        changeTextLabel.setText("PHP 0.00");
        changeTextLabel.setFont(font.getProductPriceBOLD());

        customRoundedPanel change = userOperations.createCustomRoundedPanel(20, 0, 15, 1, 15, color.getHeader(), new BorderLayout());

        change.add(changeTextLabel);

        JButton confirmPaymentButton = new JButton();
        confirmPaymentButton.setFont(font.getProductPriceBOLD());
        confirmPaymentButton.setBackground(afterOptions.getBackground());
        confirmPaymentButton.setBorder(new EmptyBorder(0,0,0,0));
        confirmPaymentButton.setLayout(new BorderLayout());
        confirmPaymentButton.setFocusPainted(F);
        confirmPaymentButton.addActionListener(new paymentActionManager.confirmPayment());

        customRoundedPanel confirmPayment = userOperations.createCustomRoundedPanel(20, 0, 15, 1, 15, color.getHeader(), new BorderLayout());
        confirmPayment.setPreferredSize(new Dimension(412, 50));

        JLabel confirmPaymentLabel = new JLabel();
        confirmPaymentLabel.setText("Confirm Payment");
        confirmPaymentLabel.setFont(font.getProductPriceBOLD());

        confirmPayment.add(confirmPaymentLabel, BorderLayout.CENTER);
        confirmPaymentButton.add(confirmPayment, BorderLayout.CENTER);

        applyNumericFilter(amountTextBox, changeTextLabel, total);

        afterOptions.add(customerName);
        afterOptions.add(customer);
        afterOptions.add(amountInCash);
        afterOptions.add(amount);
        afterOptions.add(changeLabel);
        afterOptions.add(change);
        afterOptions.add(confirmPaymentButton);

        SL.putConstraint(SpringLayout.WEST, customerName, 10, SpringLayout.WEST, afterOptions);
        SL.putConstraint(SpringLayout.NORTH, customerName, 10, SpringLayout.NORTH, afterOptions);

        SL.putConstraint(SpringLayout.WEST, customer, 10, SpringLayout.WEST, afterOptions);
        SL.putConstraint(SpringLayout.NORTH, customer, 5, SpringLayout.SOUTH, customerName);

        SL.putConstraint(SpringLayout.WEST, amountInCash, 10, SpringLayout.WEST, afterOptions);
        SL.putConstraint(SpringLayout.NORTH, amountInCash, 15, SpringLayout.SOUTH, customer);

        SL.putConstraint(SpringLayout.WEST, amount, 10, SpringLayout.WEST, afterOptions);
        SL.putConstraint(SpringLayout.NORTH, amount, 5, SpringLayout.SOUTH, amountInCash);

        SL.putConstraint(SpringLayout.WEST, changeLabel, 10, SpringLayout.WEST, afterOptions);
        SL.putConstraint(SpringLayout.NORTH, changeLabel, 15, SpringLayout.SOUTH, amount);

        SL.putConstraint(SpringLayout.WEST, change, 10, SpringLayout.WEST, afterOptions);
        SL.putConstraint(SpringLayout.NORTH, change, 5, SpringLayout.SOUTH, changeLabel);

        SL.putConstraint(SpringLayout.HORIZONTAL_CENTER, confirmPaymentButton, 0, SpringLayout.HORIZONTAL_CENTER, afterOptions);
        SL.putConstraint(SpringLayout.NORTH, confirmPaymentButton, 15, SpringLayout.SOUTH, change);
    }

    private static JTextField getjTextField(String text, int textBoxWidth, customRoundedPanel colorReference) {
        JTextField textBox = new JTextField(text);
        textBox.setPreferredSize(new Dimension(textBoxWidth, 29));
        textBox.setCaretPosition(0);
        textBox.setFont(font.getProductNameBOLD());
        textBox.setBackground(colorReference.getBackground());
        textBox.setForeground(Color.GRAY);
        textBox.setHorizontalAlignment(SwingConstants.LEFT);
        textBox.setBorder(BorderFactory.createEmptyBorder());
        return textBox;
    }

    private static void applyNumericFilter(JTextField textField, JLabel changeTextLabel, double totalAmount) {
        AbstractDocument doc = (AbstractDocument) textField.getDocument();
        doc.setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if (isValidInput(string)) {
                    super.insertString(fb, offset, string, attr);
                    updateChangeLabel();
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (isValidInput(text)) {
                    super.replace(fb, offset, length, text, attrs);
                    updateChangeLabel();
                }
            }

            @Override
            public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
                super.remove(fb, offset, length);
                updateChangeLabel();
            }

            private boolean isValidInput(String text) {
                return text.matches("\\d*\\.?\\d*"); // Allows numbers and one optional decimal point
            }

            private void updateChangeLabel() {
                String currentText = textField.getText();
                if (currentText.isEmpty() || !currentText.matches("\\d*\\.?\\d*")) {
                    changeTextLabel.setText("PHP 0.00");
                    return;
                }
                try {
                    double inputAmount = Double.parseDouble(currentText);
                    double changeAmount = inputAmount - totalAmount;
                    changeTextLabel.setText(String.format("PHP %.2f", changeAmount));
                } catch (NumberFormatException e) {
                    changeTextLabel.setText("PHP 0.00");
                }
            }
        });

        // Clear default text on focus and update change label on focus lost
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals("PHP 0.00")) {
                    textField.setText(""); // Clear the text
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setText("0.00");
                }
            }
        });

        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_TAB) {
                    // Check if the text is "0.00" and clear it
                    if (textField.getText().equals("PHP 0.00")) {
                        textField.setText(""); // Clear the text when tab is pressed
                    }
                }
            }
        });

        textField.setFocusable(true);
    }
}
