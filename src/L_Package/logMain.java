package L_Package;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.*;
import java.util.List;

import A_Package.adminInterface;
import M_Package.userInterface;

public class logMain extends logDefinitions {
    public static void main(String[] args) {
        try {
            List<Map<String, String>> users = new ArrayList<>();
            Map<String, String> admin = new HashMap<>();
            admin.put("username", "admin");
            admin.put("password", logDefinitions.hashPassword("admin123", "random_salt"));
            admin.put("role", "Admin");
            users.add(admin);

            Map<String, String> cashier = new HashMap<>();
            cashier.put("username", "cashier1");
            cashier.put("password", logDefinitions.hashPassword("cashier123", "random_salt"));
            cashier.put("role", "Cashier");
            users.add(cashier);

            logDefinitions.writeCredentials("credentials.txt", users);

            createLoginGUI();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createLoginGUI() {
        JFrame frame = new JFrame("Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(720, 490);
        frame.setLayout(null);

        // Title Label
        JLabel lblTitle = new JLabel("POS System Login");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24)); // Replace with your custom font if needed
        lblTitle.setBounds(550, 50, 300, 50);
        frame.add(lblTitle);

        // Username Label
        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setFont(new Font("Arial", Font.PLAIN, 16)); // Replace with your custom font
        lblUsername.setBounds(500, 200, 100, 30);
        frame.add(lblUsername);

        // Username TextField
        JTextField txtUsername = new JTextField();
        txtUsername.setBounds(600, 200, 200, 30);
        frame.add(txtUsername);

        // Password Label
        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setFont(new Font("Arial", Font.PLAIN, 16)); // Replace with your custom font
        lblPassword.setBounds(500, 250, 100, 30);
        frame.add(lblPassword);

        // Password Field
        JPasswordField txtPassword = new JPasswordField();
        txtPassword.setBounds(600, 250, 200, 30);
        frame.add(txtPassword);

        // Login Button
        JButton btnLogin = new JButton("Login");
        btnLogin.setBounds(600, 300, 100, 30);
        frame.add(btnLogin);

        // Message Label
        JLabel lblMessage = new JLabel("");
        lblMessage.setFont(new Font("Arial", Font.PLAIN, 14)); // Replace with your custom font
        lblMessage.setBounds(600, 350, 300, 30);
        lblMessage.setForeground(Color.RED);
        frame.add(lblMessage);

        // Login Button Action
        btnLogin.addActionListener((ActionEvent e) -> {
            String username = txtUsername.getText();
            String password = new String(txtPassword.getPassword());
            String salt = "random_salt"; // Use the same salt for hashing

            try {
                List<Map<String, String>> users = readCredentials("credentials.txt");
                boolean isValid = false;

                for (Map<String, String> user : users) {
                    if (user.get("username").equals(username)) {
                        isValid = logDefinitions.validatePassword(password, user.get("password"), salt);
                        if (isValid) {
                            String role = user.get("role");
                            if ("Admin".equalsIgnoreCase(role)) {
                                lblMessage.setText("Login successful as Admin.");
                                frame.dispose();
                                new adminInterface();
                            } else if ("Cashier".equalsIgnoreCase(role)) {
                                lblMessage.setText("Login successful as Cashier.");
                                frame.dispose();
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

        frame.setVisible(true);
    }

    public static List<Map<String, String>> readCredentials(String filePath) throws IOException {
        StringBuilder json = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                json.append(line.trim());
            }
        }
        return parseCredentials(json.toString());
    }

    private static List<Map<String, String>> parseCredentials(String json) {
        List<Map<String, String>> users = new ArrayList<>();
        json = json.substring(1, json.length() - 1).trim(); // Remove outer brackets
        String[] userEntries = json.split("},\\s*\\{");
        for (String entry : userEntries) {
            entry = entry.replace("{", "").replace("}", "").trim();
            String[] fields = entry.split(",");
            Map<String, String> user = new HashMap<>();
            for (String field : fields) {
                String[] keyValue = field.split(":");
                String key = keyValue[0].replace("\"", "").trim();
                String value = keyValue[1].replace("\"", "").trim();
                user.put(key, value);
            }
            users.add(user);
        }
        return users;
    }
}
