package L_Package;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.*;
import java.util.List;

import A_Package.adminInterface;
import G_Package.customColorPallete;
import B_Package.userInterface;

public class logMain extends logDefinitions {
    public logMain() {
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

    public static void createLoginGUI() {
        JFrame frame = new JFrame("Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new GridBagLayout());
        frame.setLocationRelativeTo(null); // Center the frame on the screen
        frame.getContentPane().setBackground(color.getRightSide());

        // Create GridBagConstraints for layout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10); // Add padding

        // Title Image
        ImageIcon originalIcon = new ImageIcon("images/ui/logo.png");
        Image originalImage = originalIcon.getImage();
        Image resizedImage = originalImage.getScaledInstance(245, 100, Image.SCALE_SMOOTH); // Set desired width and height
        ImageIcon resizedIcon = new ImageIcon(resizedImage);
        JLabel lblTitle = new JLabel(resizedIcon, SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        frame.add(lblTitle, gbc);

        // Username Label
        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setFont(font.getProductPriceREGULAR());
        lblUsername.setForeground(customColorPallete.medyo_black);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        frame.add(lblUsername, gbc);

        // Username TextField
        JTextField txtUsername = new JTextField();
        txtUsername.setForeground(Color.GRAY);
        txtUsername.setFont(new Font("Arial", Font.PLAIN, 14));
        txtUsername.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        gbc.gridy = 1;
        frame.add(txtUsername, gbc);

        // Password Label
        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setFont(font.getProductPriceREGULAR());
        lblPassword.setForeground(customColorPallete.medyo_black);
        gbc.gridx = 0;
        gbc.gridy = 2;
        frame.add(lblPassword, gbc);

        // Password Field
        JPasswordField txtPassword = new JPasswordField();
        txtPassword.setForeground(Color.GRAY);
        txtPassword.setFont(new Font("Arial", Font.PLAIN, 14));
        txtPassword.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        gbc.gridy = 2;
        frame.add(txtPassword, gbc);

        // Login Button
        JButton btnLogin = new JButton("Login");
        btnLogin.setBackground(color.getHeader());
        btnLogin.setForeground(Color.WHITE);
        gbc.gridx = 1;
        gbc.gridy = 3;
        frame.add(btnLogin, gbc);

        // Message Label
        JLabel lblMessage = new JLabel("", SwingConstants.CENTER);
        lblMessage.setFont(new Font("Arial", Font.PLAIN, 14));
        lblMessage.setForeground(Color.RED);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        frame.add(lblMessage, gbc);

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
                            frame.setVisible(false);
                            frame.dispose();
                            String role = user.get("role");
                            if ("Admin".equalsIgnoreCase(role)) {
                                new adminInterface();
                            } else if ("Cashier".equalsIgnoreCase(role)) {
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