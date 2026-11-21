import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class StudentManagement {
    private final Scanner scanner = ConsoleInput.scanner();

    public void addStudent() {
        try (Connection connection = DBConnection.getConnection()) {
            if (connection == null) return;

            System.out.print("Enter Student ID: ");
            int id = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("Enter Student Name: ");
            String name = scanner.nextLine().trim();
            System.out.print("Enter Course: ");
            String course = scanner.nextLine().trim();
            System.out.print("Enter Year: ");
            int year = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("Enter Email: ");
            String email = scanner.nextLine().trim();

            if (id <= 0 || name.isBlank() || course.isBlank() || year < 1 || year > 8 || email.isBlank()) {
                System.out.println("Please enter valid student details.");
                return;
            }

            String sql = "INSERT INTO students (student_id, student_name, course, year, email) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, id);
                statement.setString(2, name);
                statement.setString(3, course);
                statement.setInt(4, year);
                statement.setString(5, email);
                statement.executeUpdate();
            }
            System.out.println("Student Added Successfully");
        } catch (NumberFormatException exception) {
            System.out.println("Student ID and year must be valid numbers.");
        } catch (Exception exception) {
            System.out.println("Unable to add student. Check the entered details and database connection.");
        }
    }

    public void viewStudents() {
        String sql = "SELECT student_id, student_name, course, year, email FROM students ORDER BY student_id";
        try (Connection connection = DBConnection.getConnection()) {
            if (connection == null) return;
            try (Statement statement = connection.createStatement();
                 ResultSet result = statement.executeQuery(sql)) {
                boolean found = false;
                while (result.next()) {
                    found = true;
                    System.out.printf("%d  %s  %s  %d  %s%n",
                            result.getInt("student_id"),
                            result.getString("student_name"),
                            result.getString("course"),
                            result.getInt("year"),
                            result.getString("email"));
                }
                if (!found) System.out.println("No students found.");
            }
        } catch (Exception exception) {
            System.out.println("Unable to retrieve students. Check the database connection.");
        }
    }

    public void deleteStudent() {
        try (Connection connection = DBConnection.getConnection()) {
            if (connection == null) return;
            System.out.print("Enter Student ID to Delete: ");
            int id = Integer.parseInt(scanner.nextLine().trim());
            String sql = "DELETE FROM students WHERE student_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, id);
                int changed = statement.executeUpdate();
                System.out.println(changed == 1 ? "Student Deleted Successfully" : "Student ID not found.");
            }
        } catch (NumberFormatException exception) {
            System.out.println("Student ID must be a valid number.");
        } catch (Exception exception) {
            System.out.println("Unable to delete student. Check linked records and database connection.");
        }
    }
}
