import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class EventManagement {
    private final Scanner scanner = ConsoleInput.scanner();

    public void addEvent() {
        try (Connection connection = DBConnection.getConnection()) {
            if (connection == null) return;

            System.out.print("Enter Event ID: ");
            int id = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("Enter Event Name: ");
            String name = scanner.nextLine().trim();
            System.out.print("Enter Event Date (YYYY-MM-DD): ");
            Date date = Date.valueOf(scanner.nextLine().trim());
            System.out.print("Enter Location: ");
            String location = scanner.nextLine().trim();

            if (id <= 0 || name.isBlank() || location.isBlank()) {
                System.out.println("Please enter valid event details.");
                return;
            }

            String sql = "INSERT INTO events (event_id, event_name, event_date, location) VALUES (?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, id);
                statement.setString(2, name);
                statement.setDate(3, date);
                statement.setString(4, location);
                statement.executeUpdate();
            }
            System.out.println("Event Added Successfully");
        } catch (IllegalArgumentException exception) {
            System.out.println("Use a valid date in YYYY-MM-DD format and a numeric event ID.");
        } catch (Exception exception) {
            System.out.println("Unable to add event. Check the entered details and database connection.");
        }
    }
}
