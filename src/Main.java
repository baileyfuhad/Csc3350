import java.util.Scanner;

public class Main {
    private final Scanner scanner;
    private final DBConnection dbConnection;
    private final Login login;
    private final EmployeeDAO employeeDAO;
    private final PayrollUI payrollUI;
    private final ReportsUI reportsUI;

    public Main() {
        scanner = new Scanner(System.in);
        dbConnection = new DBConnection();
        login = new Login();
        employeeDAO = new EmployeeDAO();
        payrollUI = new PayrollUI();
        reportsUI = new ReportsUI();
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

        User user = login.showLogin();
        if (user == null) {
            System.out.println("Exiting system.");
            scanner.close();
            return;
        }

        boolean running = true;
        while (running) {
            printMainMenu(user);
            int choice = readInt("Choose an option: ");

            if (user.isAdmin()) {
                running = handleAdminChoice(choice);
            } else {
                running = handleEmployeeChoice(choice, user);
            }
            System.out.println();
        }

        scanner.close();
    }

    private void printMainMenu(User user) {
        System.out.println("Main Menu (" + user.getRole() + ")");
        if (user.isAdmin()) {
            System.out.println("1. Add employee");
            System.out.println("2. Search employee (edit)");
            System.out.println("3. Update employee");
            System.out.println("4. Delete employee");
            System.out.println("5. Increase salary by % within range");
            System.out.println("6. Payroll");
            System.out.println("7. Reports");
        } else {
            System.out.println("1. View my information");
            System.out.println("2. View my pay history");
        }
        System.out.println("0. Exit");
    }

    private boolean handleAdminChoice(int choice) {
        switch (choice) {
            case 1: employeeDAO.addEmployee(); return true;
            case 2: employeeDAO.searchEmployee(); return true;
            case 3: employeeDAO.updateEmployee(); return true;
            case 4: employeeDAO.deleteEmployee(); return true;
            case 5: employeeDAO.increaseSalaryByPercentInRange(); return true;
            case 6: payrollUI.showPayrollMenu(); return true;
            case 7: reportsUI.showReportsMenu(); return true;
            case 0:
                System.out.println("Exiting system.");
                return false;
            default:
                System.out.println("Invalid option. Please try again.");
                return true;
        }
    }

    private boolean handleEmployeeChoice(int choice, User user) {
        switch (choice) {
            case 1:
                employeeDAO.viewEmployee(user.getEmpID());
                return true;
            case 2:
                payrollUI.showPayHistory(user.getEmpID());
                return true;
            case 0:
                System.out.println("Exiting system.");
                return false;
            default:
                System.out.println("Invalid option. Please try again.");
                return true;
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
}
