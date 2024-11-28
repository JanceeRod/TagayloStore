package L_Package;

import java.io.*;
import java.security.MessageDigest;
import java.util.*;

public class logOperations {

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
