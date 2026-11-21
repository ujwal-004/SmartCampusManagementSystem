import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class ComplaintManagement {
    private final Scanner scanner = ConsoleInput.scanner();

    public void addComplaint() {
        try (Connection connection = DBConnection.getConnection()) {
            if (connection == null) return;

            System.out.print("Enter Student Name: ");
            String name = scanner.nextLine().trim();
            System.out.print("Enter Complaint: ");
            String complaint = scanner.nextLine().trim();

            if (name.isBlank() || complaint.isBlank()) {
                System.out.println("Student name and complaint cannot be empty.");
                return;
            }

            String sql = "INSERT INTO complaints (student_name, complaint_text, status) VALUES (?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, name);
                statement.setString(2, complaint);
                statement.setString(3, "Pending");
                statement.executeUpdate();
            }
            System.out.println("Complaint Submitted Successfully");
        } catch (Exception exception) {
            System.out.println("Unable to submit complaint. Check the database connection.");
        }
    }
}
