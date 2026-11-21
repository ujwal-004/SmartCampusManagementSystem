import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class AttendanceManagement {
    private final Scanner scanner = ConsoleInput.scanner();

    public void markAttendance() {
        try (Connection connection = DBConnection.getConnection()) {
            if (connection == null) return;

            System.out.print("Enter Student ID: ");
            int studentId = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("Enter Subject Name: ");
            String subject = scanner.nextLine().trim();
            System.out.print("Enter Attendance Status (Present/Absent): ");
            String status = scanner.nextLine().trim();

            if (studentId <= 0 || subject.isBlank()
                    || !(status.equalsIgnoreCase("Present") || status.equalsIgnoreCase("Absent"))) {
                System.out.println("Enter a valid student ID, subject and Present/Absent status.");
                return;
            }

            String sql = "INSERT INTO attendance (student_id, subject_name, attendance_date, status) VALUES (?, ?, CURDATE(), ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, studentId);
                statement.setString(2, subject);
                statement.setString(3, status.substring(0, 1).toUpperCase() + status.substring(1).toLowerCase());
                statement.executeUpdate();
            }
            System.out.println("Attendance Marked Successfully");
        } catch (NumberFormatException exception) {
            System.out.println("Student ID must be a valid number.");
        } catch (Exception exception) {
            System.out.println("Unable to mark attendance. Confirm the student exists and check the database.");
        }
    }
}
