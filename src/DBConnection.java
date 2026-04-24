import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/joshi_data";
    private static final String USER = "root"; 
    private static final String PASS = "your_password"; // Use your actual MySQL password

    public void connect() {
    try (Connection conn = getConnection()) {
        if (conn != null && !conn.isClosed()) {
            System.out.println("Successfully linked to Database: employeedata");
        }
    } catch (SQLException e) {
        System.out.println("Database link failed. Update your password in DBConnection.java");
    }
}

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}