public class User {
    private int empID;
    private String username;
    private String role;

    public User(int empID, String username, String role) {
        this.empID = empID;
        this.username = username;
        this.role = role;
    }

    public int getEmpID() {
        return empID;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }

    public boolean isAdmin() {
        return role.equalsIgnoreCase("HR_ADMIN");
    }

    public boolean isEmployee() {
        return role.equalsIgnoreCase("EMPLOYEE");
    }
}