import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReportsDAO {

    public List<String> totalPayByJobTitle(String month) {
        List<String> list = new ArrayList<>();
        String sql =
            "SELECT jt.job_title, SUM(p.gross_pay) AS total " +
            "FROM payroll p " +
            "JOIN employee_job_titles ejt ON p.empID = ejt.empID " +
            "JOIN job_titles jt ON ejt.job_titleID = jt.job_titleID " +
            "WHERE DATE_FORMAT(p.pay_date, '%Y-%m') = ? " +
            "GROUP BY jt.job_title";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, month);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(rs.getString("job_title") + ": $" + rs.getDouble("total"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Job-title report failed: " + e.getMessage());
        }
        return list;
    }

    public List<String> totalPayByDivision(String month) {
        List<String> list = new ArrayList<>();
        String sql =
            "SELECT d.division_name, SUM(p.gross_pay) AS total " +
            "FROM payroll p " +
            "JOIN employee_division ed ON p.empID = ed.empID " +
            "JOIN division d ON ed.divID = d.divID " +
            "WHERE DATE_FORMAT(p.pay_date, '%Y-%m') = ? " +
            "GROUP BY d.division_name";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, month);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(rs.getString("division_name") + ": $" + rs.getDouble("total"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Division report failed: " + e.getMessage());
        }
        return list;
    }

    public List<String> newHires(String start, String end) {
        List<String> list = new ArrayList<>();
        String sql =
            "SELECT empID, first_name, last_name, hire_date " +
            "FROM employees " +
            "WHERE hire_date BETWEEN ? AND ? " +
            "ORDER BY hire_date DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, start);
            ps.setString(2, end);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(
                        rs.getInt("empID") + " | " +
                        rs.getString("first_name") + " " +
                        rs.getString("last_name") +
                        " | Hired: " + rs.getDate("hire_date")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("New-hires report failed: " + e.getMessage());
        }
        return list;
    }
}
