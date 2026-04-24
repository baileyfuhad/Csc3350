import java.sql.*;
import java.util.Scanner;

public class EmployeeDAO {
    private final Scanner scanner = new Scanner(System.in);

    public void addEmployee() {
        System.out.println("--- Adding New Employee ---");
        System.out.print("Enter First Name: ");
        String fName = scanner.nextLine();
        System.out.print("Enter Last Name: ");
        String lName = scanner.nextLine();
        System.out.print("Enter Salary: ");
        double salary = Double.parseDouble(scanner.nextLine());

        String sql = "INSERT INTO employees (Fname, Lname, Salary) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, fName);
            stmt.setString(2, lName);
            stmt.setDouble(3, salary);
            stmt.executeUpdate();
            System.out.println("Employee added to database successfully!");
        } catch (SQLException e) { 
            System.out.println("Error adding employee.");
            e.printStackTrace(); 
        }
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
                // Combining Fname and Lname to match Employee class constructor
                String fullName = rs.getString("Fname") + " " + rs.getString("Lname");
                Employee emp = new Employee(id, fullName, rs.getDouble("Salary"));
                
                System.out.println("Result: " + emp.getName() + " | Salary: $" + emp.getSalary());
            } else {
                System.out.println("Employee not found.");
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void updateEmployee() { 
        System.out.print("Enter Employee ID to update salary: ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter new Salary: ");
        double salary = Double.parseDouble(scanner.nextLine());

        String sql = "UPDATE employees SET Salary = ? WHERE empid = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, salary);
            stmt.setInt(2, id);
            int rows = stmt.executeUpdate();
            if (rows > 0) System.out.println("Salary updated successfully.");
            else System.out.println("Employee ID not found.");
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void deleteEmployee() { 
        System.out.print("Enter Employee ID to DELETE: ");
        int id = Integer.parseInt(scanner.nextLine());

        String sql = "DELETE FROM employees WHERE empid = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            if (rows > 0) System.out.println("Employee record deleted.");
            else System.out.println("Employee ID not found.");
        } catch (SQLException e) { e.printStackTrace(); }
    }
}
