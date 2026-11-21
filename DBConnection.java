import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String dbUrl = System.getenv("DB_URL");
            if (dbUrl == null) dbUrl = "jdbc:mysql://localhost:3306/smart_campus";
            String dbUser = System.getenv("DB_USER");
            if (dbUser == null) dbUser = "root";
            String dbPassword = System.getenv("DB_PASSWORD");
            if (dbPassword == null) dbPassword = "";
            Connection con = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            System.out.println("Database Connected Successfully");
            return con;
        } catch (Exception e) {
            System.out.println("Database connection failed. Check DB_URL, DB_USER, and DB_PASSWORD environment variables and ensure MySQL is running.");
            return null;
        }
    }
}
