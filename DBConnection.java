import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = AppConfig.get("DB_URL", "jdbc:mysql://localhost:3306/smart_campus");
            String user = AppConfig.get("DB_USER", "root");
            String password = AppConfig.get("DB_PASSWORD", "");
            return DriverManager.getConnection(url, user, password);
        } catch (Exception exception) {
            System.out.println("Database connection failed. Check the DB_* settings and ensure MySQL is running.");
            return null;
        }
    }
}
