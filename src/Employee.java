public class Employee {
    int empId;
    String name; // We'll combine Fname and Lname here
    double salary;

    public Employee(int empId, String name, double salary) {
        this.empId = empId;
        this.name = name;
        this.salary = salary;
    }

    // Getters for the Reports class to use later
    public int getEmpId() { return empId; }
    public String getName() { return name; }
    public double getSalary() { return salary; }
}
