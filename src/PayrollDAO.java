import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PayrollDAO {

    public List<String> getPayHistory(int empId) {
        List<String> list = new ArrayList<>();

        String sql = "SELECT pay_date, gross_pay, net_pay FROM payroll " +
                     "WHERE empID = ? ORDER BY pay_date DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, empId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(
                        "Date: " + rs.getDate("pay_date") +
                        " | Gross: " + rs.getDouble("gross_pay") +
                        " | Net: " + rs.getDouble("net_pay")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to load pay history: " + e.getMessage());
        }

        return list;
    }
}
