import java.sql.*;
import java.util.Scanner;

public class EmployeeDAO {
    private final Scanner scanner = new Scanner(System.in);

    public void addEmployee() {
        System.out.println("Add Employee logic executing...");
        // Add your INSERT logic here
    }

    // Matches Wacko's 'case 2'
    public void searchEmployee() {
        System.out.print("Enter Employee ID: ");
        String input = scanner.nextLine();
        int id = Integer.parseInt(input);

        String sql = "SELECT Fname, Lname, Salary FROM employees WHERE empid = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                System.out.println("Result: " + rs.getString("Fname") + " " + rs.getString("Lname"));
            } else {
                System.out.println("Employee not found.");
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void updateEmployee() { System.out.println("Update logic executing..."); }
    public void deleteEmployee() { System.out.println("Delete logic executing..."); }
}
