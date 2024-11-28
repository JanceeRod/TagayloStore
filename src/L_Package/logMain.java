package L_Package;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.*;
import java.util.List;

import A_Package.adminInterface;
import B_Package.userInterface;

public class logMain extends logDefinitions {

    public logMain() {
        try {
            List<Map<String, String>> users = new ArrayList<>();
            Map<String, String> admin = new HashMap<>();
            admin.put("username", "admin");
            admin.put("password", logOperations.hashPassword("admin123", "random_salt"));
            admin.put("role", "Admin");
            users.add(admin);

            Map<String, String> cashier = new HashMap<>();
            cashier.put("username", "cashier1");
            cashier.put("password", logOperations.hashPassword("cashier123", "random_salt"));
            cashier.put("role", "Cashier");
            users.add(cashier);

            logOperations.writeCredentials("credentials.txt", users);

            createLoginGUI();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void createLoginGUI() {
        loginFrame = new JFrame("Tagaylo Store Point-of-Sales | Login");
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setSize(720, 490);
        loginFrame.setLayout(new BorderLayout());
        loginFrame.setVisible(true);

        loginElements();
    }

    public static void loginElements() {
        JPanel mainContainer = new JPanel();
        mainContainer.setBackground(color.getHeader());
        mainContainer.setLayout(new BorderLayout());
        loginFrame.add(mainContainer, BorderLayout.CENTER);

        // Title Label
        JLabel lblTitle = new JLabel("Tagaylo Store Login");
        lblTitle.setFont(font.getTitleFont());
        loginFrame.add(lblTitle);

        // Username Label
        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setFont(new Font("Arial", Font.PLAIN, 16));
        loginFrame.add(lblUsername);

        // Username TextField
        JTextField txtUsername = new JTextField();
        txtUsername.setPreferredSize(new Dimension(200, 30));
        loginFrame.add(txtUsername);

        // Password Label
        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setFont(new Font("Arial", Font.PLAIN, 16));
        loginFrame.add(lblPassword);

        // Password Field
        JPasswordField txtPassword = new JPasswordField();
        txtPassword.setPreferredSize(new Dimension(200, 30));
        loginFrame.add(txtPassword);

        // Login Button
        JButton btnLogin = new JButton("Login");
        loginFrame.add(btnLogin);

        // Message Label
        JLabel lblMessage = new JLabel("");
        lblMessage.setFont(new Font("Arial", Font.PLAIN, 14));
        lblMessage.setForeground(Color.RED);
        loginFrame.add(lblMessage);

        // Login Button Action
        btnLogin.addActionListener((ActionEvent e) -> {
            String username = txtUsername.getText();
            String password = new String(txtPassword.getPassword());
            String salt = "random_salt"; // Use the same salt for hashing

            try {
                List<Map<String, String>> users = logOperations.readCredentials("credentials.txt");
                boolean isValid = false;

                for (Map<String, String> user : users) {
                    if (user.get("username").equals(username)) {
                        isValid = logOperations.validatePassword(password, user.get("password"), salt);
                        if (isValid) {
                            loginFrame.setVisible(false);
                            loginFrame.dispose();
                            String role = user.get("role");
                            if ("Admin".equalsIgnoreCase(role)) {
//                                lblMessage.setText("Login successful as Admin.");

                                System.out.println("Admin");
                                new adminInterface();
                            } else if ("Cashier".equalsIgnoreCase(role)) {
//                                lblMessage.setText("Login successful as Cashier.");

                                System.out.println("Cashier");
                                new userInterface();
                            }
                            return;
                        }
                    }
                }

                if (!isValid) {
                    lblMessage.setText("Invalid username or password.");
                }
            } catch (Exception ex) {
                lblMessage.setText("Error processing login.");
                ex.printStackTrace();
            }
        });

        // Constraints for components
        // Title
        SL.putConstraint(SpringLayout.HORIZONTAL_CENTER, lblTitle, 0, SpringLayout.HORIZONTAL_CENTER, loginFrame.getContentPane());
        SL.putConstraint(SpringLayout.NORTH, lblTitle, 30, SpringLayout.NORTH, loginFrame.getContentPane());

        // Username Label
        SL.putConstraint(SpringLayout.HORIZONTAL_CENTER, lblUsername, 0, SpringLayout.HORIZONTAL_CENTER, loginFrame.getContentPane());
        SL.putConstraint(SpringLayout.NORTH, lblUsername, 30, SpringLayout.SOUTH, lblTitle);

        // Username TextField
        SL.putConstraint(SpringLayout.HORIZONTAL_CENTER, txtUsername, 0, SpringLayout.HORIZONTAL_CENTER, loginFrame.getContentPane());
        SL.putConstraint(SpringLayout.NORTH, txtUsername, 10, SpringLayout.SOUTH, lblUsername);

        // Password Label
        SL.putConstraint(SpringLayout.HORIZONTAL_CENTER, lblPassword, 0, SpringLayout.HORIZONTAL_CENTER, loginFrame.getContentPane());
        SL.putConstraint(SpringLayout.NORTH, lblPassword, 20, SpringLayout.SOUTH, txtUsername);

        // Password TextField
        SL.putConstraint(SpringLayout.HORIZONTAL_CENTER, txtPassword, 0, SpringLayout.HORIZONTAL_CENTER, loginFrame.getContentPane());
        SL.putConstraint(SpringLayout.NORTH, txtPassword, 10, SpringLayout.SOUTH, lblPassword);

        // Login Button
        SL.putConstraint(SpringLayout.HORIZONTAL_CENTER, btnLogin, 0, SpringLayout.HORIZONTAL_CENTER, loginFrame.getContentPane());
        SL.putConstraint(SpringLayout.NORTH, btnLogin, 20, SpringLayout.SOUTH, txtPassword);
    }

}
