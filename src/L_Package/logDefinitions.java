package L_Package;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import G_Package.customColorPallete;
import G_Package.customFontGallery;

public class logDefinitions {

    JFrame loginFrame;

    boolean T = true, F = false;

    static customFontGallery font = new customFontGallery();
    static customColorPallete color = new customColorPallete();

    public static void writeCredentials(String filePath, List<Map<String, String>> users) throws IOException {
        StringBuilder content = new StringBuilder("[\n");
        for (Map<String, String> user : users) {
            content.append("  {");
            content.append("\"username\":\"").append(user.get("username")).append("\",");
            content.append("\"password\":\"").append(user.get("password")).append("\",");
            content.append("\"role\":\"").append(user.get("role")).append("\"");
            content.append("},\n");
        }

        if (!users.isEmpty()) {
            content.setLength(content.length() - 2);
        }
        content.append("\n]");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(content.toString());
        }
    }

    public static String hashPassword(String password, String salt) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(salt.getBytes());
        byte[] hashedBytes = md.digest(password.getBytes());
        return Base64.getEncoder().encodeToString(hashedBytes);
    }

    public static boolean validatePassword(String inputPassword, String storedHash, String salt) throws Exception {
        String hashedInput = hashPassword(inputPassword, salt);
        return hashedInput.equals(storedHash);
    }
}
