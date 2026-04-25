import java.sql.*;
import java.util.Scanner;

public class EmployeeDAO {
    private final Scanner scanner = new Scanner(System.in);

    private static final String SELECT_BASE =
        "SELECT e.empID, e.first_name, e.last_name, e.email, e.hire_date, e.salary, e.SSN, " +
        "       a.DOB, a.phone, a.street, c.city_name, s.state_abbrev, a.zip " +
        "FROM employees e " +
        "LEFT JOIN addresses a ON e.addressID = a.addressID " +
        "LEFT JOIN cities    c ON a.cityID    = c.cityID " +
        "LEFT JOIN states    s ON a.stateID   = s.stateID";

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
        System.out.println("--- Search Employee (Edit) ---");
        System.out.println("1. By empID");
        System.out.println("2. By name");
        System.out.println("3. By DOB (YYYY-MM-DD)");
        System.out.println("4. By SSN");
        System.out.print("Choose: ");
        String choice = scanner.nextLine().trim();
        switch (choice) {
            case "1": searchByEmpID(); break;
            case "2": searchByName();  break;
            case "3": searchByDOB();   break;
            case "4": searchBySSN();   break;
            default: System.out.println("Invalid option.");
        }
    }

    private void searchByEmpID() {
        System.out.print("empID: ");
        int id;
        try { id = Integer.parseInt(scanner.nextLine().trim()); }
        catch (NumberFormatException e) { System.out.println("Invalid empID."); return; }
        runQuery(SELECT_BASE + " WHERE e.empID = ?", ps -> ps.setInt(1, id));
    }

    private void searchByName() {
        System.out.print("Name (first or last, partial OK): ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) { System.out.println("Name required."); return; }
        String like = "%" + name + "%";
        runQuery(SELECT_BASE + " WHERE e.first_name LIKE ? OR e.last_name LIKE ?", ps -> {
            ps.setString(1, like);
            ps.setString(2, like);
        });
    }

    private void searchByDOB() {
        System.out.print("DOB (YYYY-MM-DD): ");
        String dob = scanner.nextLine().trim();
        try {
            Date d = Date.valueOf(dob);
            runQuery(SELECT_BASE + " WHERE a.DOB = ?", ps -> ps.setDate(1, d));
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid date format.");
        }
    }

    private void searchBySSN() {
        System.out.print("SSN: ");
        String ssn = scanner.nextLine().trim();
        runQuery(SELECT_BASE + " WHERE e.SSN = ?", ps -> ps.setString(1, ssn));
    }

    public void viewEmployee(int empID) {
        runQuery(SELECT_BASE + " WHERE e.empID = ?", ps -> ps.setInt(1, empID));
    }

    public void updateEmployee() {
        System.out.print("Enter empID to update: ");
        int id;
        try { id = Integer.parseInt(scanner.nextLine().trim()); }
        catch (NumberFormatException e) { System.out.println("Invalid empID."); return; }

        String fName, lName, email, ssn;
        Date hireDate;
        double salary;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement find = conn.prepareStatement(
                 "SELECT first_name, last_name, email, hire_date, salary, SSN FROM employees WHERE empID = ?")) {
            find.setInt(1, id);
            try (ResultSet rs = find.executeQuery()) {
                if (!rs.next()) { System.out.println("No match for empID " + id + "."); return; }
                fName    = rs.getString("first_name");
                lName    = rs.getString("last_name");
                email    = rs.getString("email");
                hireDate = rs.getDate("hire_date");
                salary   = rs.getDouble("salary");
                ssn      = rs.getString("SSN");
            }
        } catch (SQLException e) {
            System.out.println("Lookup failed: " + e.getMessage());
            return;
        }

        System.out.println("Press Enter to keep the current value.");
        fName    = promptString("First name [" + fName + "]: ", fName);
        lName    = promptString("Last name ["  + lName + "]: ", lName);
        email    = promptString("Email ["      + email + "]: ", email);

        String hireStr = hireDate == null ? "" : hireDate.toString();
        String newHire = promptString("Hire date YYYY-MM-DD [" + hireStr + "]: ", hireStr);
        Date newHireDate;
        try { newHireDate = newHire.isEmpty() ? null : Date.valueOf(newHire); }
        catch (IllegalArgumentException e) { System.out.println("Invalid date; aborting."); return; }

        String salStr = promptString("Salary [" + salary + "]: ", String.valueOf(salary));
        try { salary = Double.parseDouble(salStr); }
        catch (NumberFormatException e) { System.out.println("Invalid salary; aborting."); return; }

        ssn = promptString("SSN [" + ssn + "]: ", ssn);

        String sql = "UPDATE employees SET first_name=?, last_name=?, email=?, hire_date=?, salary=?, SSN=? WHERE empID=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, fName);
            stmt.setString(2, lName);
            stmt.setString(3, email);
            if (newHireDate == null) stmt.setNull(4, Types.DATE); else stmt.setDate(4, newHireDate);
            stmt.setDouble(5, salary);
            stmt.setString(6, ssn);
            stmt.setInt(7, id);
            int rows = stmt.executeUpdate();
            System.out.println(rows > 0 ? "Employee updated." : "Update failed.");
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

    @FunctionalInterface
    private interface Binder { void bind(PreparedStatement ps) throws SQLException; }

    private void runQuery(String sql, Binder binder) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            binder.bind(stmt);
            try (ResultSet rs = stmt.executeQuery()) {
                int count = 0;
                while (rs.next()) {
                    if (count > 0) System.out.println("---");
                    printEmployeeRow(rs);
                    count++;
                }
                if (count == 0) System.out.println("No matching employee found.");
                else System.out.println("(" + count + " result" + (count == 1 ? "" : "s") + ")");
            }
        } catch (SQLException e) {
            System.out.println("Search failed: " + e.getMessage());
        }
    }

    private String promptString(String prompt, String current) {
        System.out.print(prompt);
        String input = scanner.nextLine();
        return input.isEmpty() ? current : input;
    }

    private void printEmployeeRow(ResultSet rs) throws SQLException {
        System.out.println("empID:    " + rs.getInt("empID"));
        System.out.println("Name:     " + rs.getString("first_name") + " " + rs.getString("last_name"));
        System.out.println("Email:    " + rs.getString("email"));
        System.out.println("Hired:    " + rs.getDate("hire_date"));
        System.out.println("Salary:   $" + rs.getBigDecimal("salary"));
        System.out.println("SSN:      " + rs.getString("SSN"));
        Date dob = rs.getDate("DOB");
        if (dob != null) System.out.println("DOB:      " + dob);
        String phone = rs.getString("phone");
        if (phone != null) System.out.println("Phone:    " + phone);
        String street = rs.getString("street");
        if (street != null) {
            System.out.println("Address:  " + street + ", " +
                rs.getString("city_name") + ", " +
                rs.getString("state_abbrev") + " " + rs.getString("zip"));
        }
    }
}
