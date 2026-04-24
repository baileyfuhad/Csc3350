import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PayrollDAO {

    private Connection conn;

    public PayrollDAO() {
        this.conn = DBConnection.getConnection();
    }

    public List<String> getPayHistory(int empId) {
        List<String> list = new ArrayList<>();

        String sql = "SELECT pay_date, gross_pay, net_pay FROM payroll " +
                     "WHERE empID = ? ORDER BY pay_date DESC";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, empId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(
                    "Date: " + rs.getDate("pay_date") +
                    " | Gross: " + rs.getDouble("gross_pay") +
                    " | Net: " + rs.getDouble("net_pay")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
