import java.sql.*;
import java.util.Scanner;

public class EmployeeDAO {
    private final Scanner scanner = new Scanner(System.in);

    public void addEmployee() {
        System.out.println("--- Add New Employee ---");
        System.out.print("First name: ");
        String fName = scanner.nextLine();
        System.out.print("Last name: ");
        String lName = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Hire date (YYYY-MM-DD): ");
        String hire = scanner.nextLine();
        System.out.print("Salary: ");
        double salary = Double.parseDouble(scanner.nextLine());
        System.out.print("SSN: ");
        String ssn = scanner.nextLine();

        String sql = "INSERT INTO employees (first_name, last_name, email, hire_date, salary, SSN) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, fName);
            stmt.setString(2, lName);
            stmt.setString(3, email);
            stmt.setDate(4, Date.valueOf(hire));
            stmt.setDouble(5, salary);
            stmt.setString(6, ssn);
            stmt.executeUpdate();
            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    System.out.println("Employee added with empID = " + keys.getInt(1));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error adding employee: " + e.getMessage());
        }
    }

    public void searchEmployee() {
        System.out.print("Enter Employee ID: ");
        int id = Integer.parseInt(scanner.nextLine());

        String sql = "SELECT empID, first_name, last_name, email, hire_date, salary, SSN FROM employees WHERE empID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    printEmployeeRow(rs);
                } else {
                    System.out.println("No match for employee " + id + ".");
                }
            }
        } catch (SQLException e) {
            System.out.println("Search failed: " + e.getMessage());
        }
    }

    public void viewEmployee(int empID) {
        String sql = "SELECT empID, first_name, last_name, email, hire_date, salary, SSN FROM employees WHERE empID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, empID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    printEmployeeRow(rs);
                } else {
                    System.out.println("Your employee record was not found.");
                }
            }
        } catch (SQLException e) {
            System.out.println("View failed: " + e.getMessage());
        }
    }

    public void updateEmployee() {
        System.out.print("Enter Employee ID to update: ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.print("New salary: ");
        double salary = Double.parseDouble(scanner.nextLine());

        String sql = "UPDATE employees SET salary = ? WHERE empID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, salary);
            stmt.setInt(2, id);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Salary updated.");
            } else {
                System.out.println("Employee ID not found.");
            }
        } catch (SQLException e) {
            System.out.println("Update failed: " + e.getMessage());
        }
    }

    public void deleteEmployee() {
        System.out.print("Enter Employee ID to delete: ");
        int id = Integer.parseInt(scanner.nextLine());

        String findSql = "SELECT first_name, last_name FROM employees WHERE empID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement find = conn.prepareStatement(findSql)) {
            find.setInt(1, id);
            try (ResultSet rs = find.executeQuery()) {
                if (!rs.next()) {
                    System.out.println("No match for employee " + id + ".");
                    return;
                }
                System.out.print("Delete " + rs.getString("first_name") + " " + rs.getString("last_name") + "? (y/N): ");
            }
            String confirm = scanner.nextLine().trim();
            if (!confirm.equalsIgnoreCase("y")) {
                System.out.println("Delete cancelled.");
                return;
            }
            try (PreparedStatement del = conn.prepareStatement("DELETE FROM employees WHERE empID = ?")) {
                del.setInt(1, id);
                int rows = del.executeUpdate();
                System.out.println(rows > 0 ? "Employee deleted." : "Delete failed.");
            }
        } catch (SQLException e) {
            System.out.println("Delete failed: " + e.getMessage());
        }
    }

    public void increaseSalaryByPercentInRange() {
        System.out.print("Minimum current salary: ");
        double min = Double.parseDouble(scanner.nextLine());
        System.out.print("Maximum current salary: ");
        double max = Double.parseDouble(scanner.nextLine());
        System.out.print("Percent increase (e.g. 5 for 5%): ");
        double pct = Double.parseDouble(scanner.nextLine());

        String sql = "UPDATE employees SET salary = salary * (1 + ? / 100.0) WHERE salary BETWEEN ? AND ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, pct);
            stmt.setDouble(2, min);
            stmt.setDouble(3, max);
            int rows = stmt.executeUpdate();
            System.out.println(rows + " employee salaries updated.");
        } catch (SQLException e) {
            System.out.println("Bulk update failed: " + e.getMessage());
        }
    }

    private void printEmployeeRow(ResultSet rs) throws SQLException {
        System.out.println("empID:    " + rs.getInt("empID"));
        System.out.println("Name:     " + rs.getString("first_name") + " " + rs.getString("last_name"));
        System.out.println("Email:    " + rs.getString("email"));
        System.out.println("Hired:    " + rs.getDate("hire_date"));
        System.out.println("Salary:   $" + rs.getBigDecimal("salary"));
        System.out.println("SSN:      " + rs.getString("SSN"));
    }
}
