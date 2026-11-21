import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class FacultyManagement {
    private final Scanner scanner = ConsoleInput.scanner();

    public void addFaculty() {
        try (Connection connection = DBConnection.getConnection()) {
            if (connection == null) return;

            System.out.print("Enter Faculty ID: ");
            int id = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("Enter Faculty Name: ");
            String name = scanner.nextLine().trim();
            System.out.print("Enter Department: ");
            String department = scanner.nextLine().trim();
            System.out.print("Enter Email: ");
            String email = scanner.nextLine().trim();

            if (id <= 0 || name.isBlank() || department.isBlank() || email.isBlank()) {
                System.out.println("Please enter valid faculty details.");
                return;
            }

            String sql = "INSERT INTO faculty (faculty_id, faculty_name, department, email) VALUES (?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, id);
                statement.setString(2, name);
                statement.setString(3, department);
                statement.setString(4, email);
                statement.executeUpdate();
            }
            System.out.println("Faculty Added Successfully");
        } catch (NumberFormatException exception) {
            System.out.println("Faculty ID must be a valid number.");
        } catch (Exception exception) {
            System.out.println("Unable to add faculty. Check the entered details and database connection.");
        }
    }
}
