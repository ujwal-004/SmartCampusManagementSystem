import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;
import org.mindrot.jbcrypt.BCrypt;

public class Login {
    public static boolean loginSystem() {
        try (Connection connection = DBConnection.getConnection()) {
            if (connection == null) {
                return false;
            }

            createAdminFromEnvironmentIfNeeded(connection);
            Scanner scanner = ConsoleInput.scanner();

            System.out.print("Enter Username: ");
            String username = scanner.nextLine().trim();

            System.out.print("Enter Password: ");
            String password = scanner.nextLine();

            String query = "SELECT password FROM users WHERE username = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, username);
                try (ResultSet result = statement.executeQuery()) {
                    if (result.next() && BCrypt.checkpw(password, result.getString("password"))) {
                        System.out.println("Login Successful");
                        return true;
                    }
                }
            }

            System.out.println("Invalid credentials.");
            return false;
        } catch (Exception exception) {
            System.out.println("Login could not be completed. Check the database schema and admin configuration.");
            return false;
        }
    }

    private static void createAdminFromEnvironmentIfNeeded(Connection connection) throws Exception {
        String username = AppConfig.get("DB_ADMIN_USERNAME", "");
        String password = AppConfig.get("DB_ADMIN_PASSWORD", "");
        if (username.isBlank() || password.isBlank()) {
            return;
        }
        if (password.length() < 12 || password.getBytes(java.nio.charset.StandardCharsets.UTF_8).length > 72) {
            throw new IllegalArgumentException("DB_ADMIN_PASSWORD must be 12–72 UTF-8 bytes.");
        }

        String lookup = "SELECT user_id FROM users WHERE username = ?";
        try (PreparedStatement statement = connection.prepareStatement(lookup)) {
            statement.setString(1, username);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    return;
                }
            }
        }

        String insert = "INSERT INTO users (username, password) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(insert)) {
            statement.setString(1, username);
            statement.setString(2, BCrypt.hashpw(password, BCrypt.gensalt(12)));
            statement.executeUpdate();
            System.out.println("Initial admin account created from DB_ADMIN_USERNAME / DB_ADMIN_PASSWORD.");
        }
    }
}
