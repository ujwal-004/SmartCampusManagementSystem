import java.util.Scanner;

public class MainMenu {
    public static void main(String[] args) {
        if (!Login.loginSystem()) {
            System.out.println("Program Closed");
            return;
        }

        Scanner scanner = ConsoleInput.scanner();
        StudentManagement student = new StudentManagement();
        FacultyManagement faculty = new FacultyManagement();
        AttendanceManagement attendance = new AttendanceManagement();
        ComplaintManagement complaint = new ComplaintManagement();
        EventManagement event = new EventManagement();

        while (true) {
            System.out.println("\n===== SMART CAMPUS MANAGEMENT SYSTEM =====");
            System.out.println("1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Delete Student");
            System.out.println("4. Add Faculty");
            System.out.println("5. Mark Attendance");
            System.out.println("6. Add Complaint");
            System.out.println("7. Add Event");
            System.out.println("8. Exit");
            System.out.print("Enter Your Choice: ");

            String input = scanner.nextLine().trim();
            int choice;
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException exception) {
                System.out.println("Please enter a number from 1 to 8.");
                continue;
            }

            switch (choice) {
                case 1 -> student.addStudent();
                case 2 -> student.viewStudents();
                case 3 -> student.deleteStudent();
                case 4 -> faculty.addFaculty();
                case 5 -> attendance.markAttendance();
                case 6 -> complaint.addComplaint();
                case 7 -> event.addEvent();
                case 8 -> {
                    System.out.println("Thank You");
                    return;
                }
                default -> System.out.println("Invalid Choice");
            }
        }
    }
}
