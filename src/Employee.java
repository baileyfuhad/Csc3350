public class Employee {
    int empId;
    String name; // Combined Fname and Lname
    double salary;

    public Employee(int empId, String name, double salary) {
        this.empId = empId;
        this.name = name;
        this.salary = salary;
    }

    // Getters for the Reports class
    public int getEmpId() { return empId; }
    public String getName() { return name; }
    public double getSalary() { return salary; }
}
