import java.util.Scanner;

public class Main {
    private final Scanner scanner;
    private final DBConnection dbConnection;
    private final Login login;
    private final EmployeeDAO employeeDAO;
    private final Reports reports;

    public Main() {
        scanner = new Scanner(System.in);
        dbConnection = new DBConnection();
        login = new Login();
        employeeDAO = new EmployeeDAO();
        reports = new Reports();
    }

    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }

    private void start() {
        System.out.println("==================================");
        System.out.println(" Company Z Employee Management");
        System.out.println("==================================");

        dbConnection.connect();
        login.showLogin();

        boolean running = true;
        while (running) {
            printMainMenu();
            int choice = readInt("Choose an option: ");

            switch (choice) {
                case 1:
                    employeeDAO.addEmployee();
                    break;
                case 2:
                    employeeDAO.searchEmployee();
                    break;
                case 3:
                    employeeDAO.updateEmployee();
                    break;
                case 4:
                    employeeDAO.deleteEmployee();
                    break;
                case 5:
                    showPayrollMenu();
                    break;
                case 6:
                    showReportsMenu();
                    break;
                case 0:
                    running = false;
                    System.out.println("Exiting system.");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }

            System.out.println();
        }

        scanner.close();
    }

    private void printMainMenu() {
        System.out.println("Main Menu");
        System.out.println("1. Add employee");
        System.out.println("2. Search employee");
        System.out.println("3. Update employee");
        System.out.println("4. Delete employee");
        System.out.println("5. Payroll");
        System.out.println("6. Reports");
        System.out.println("0. Exit");
    }

    private void showPayrollMenu() {
        System.out.println("Payroll Menu");
        System.out.println("1. Create sample payroll record");
        System.out.println("0. Back");

        int choice = readInt("Choose an option: ");
        if (choice == 1) {
            int empId = readInt("Enter employee ID: ");
            double amount = readDouble("Enter payroll amount: ");
            Payroll payroll = new Payroll(empId, amount);
            System.out.println("Payroll record prepared for employee " + payroll.empId
                    + " with amount $" + String.format("%.2f", payroll.amount));
        }
    }

    private void showReportsMenu() {
        System.out.println("Reports Menu");
        System.out.println("1. Monthly pay report");
        System.out.println("2. Division report");
        System.out.println("0. Back");

        int choice = readInt("Choose an option: ");
        switch (choice) {
            case 1:
                reports.monthlyPayReport();
                break;
            case 2:
                reports.divisionReport();
                break;
            case 0:
                break;
            default:
                System.out.println("Invalid option. Returning to main menu.");
        }
    }

    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException ex) {
                System.out.println("Please enter a valid whole number.");
            }
        }
    }

    private double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException ex) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
}
