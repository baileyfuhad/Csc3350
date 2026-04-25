import java.util.List;
import java.util.Scanner;

public class PayrollUI {
    private final PayrollService service = new PayrollService();

    public void showPayrollMenu() {
        Scanner sc = new Scanner(System.in);
        System.out.println("=== Payroll Menu ===");
        System.out.println("1. View employee pay history");
        System.out.println("0. Back");
        System.out.print("Choose: ");

        int choice;
        try {
            choice = Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid choice.");
            return;
        }

        if (choice == 1) {
            System.out.print("Enter Employee ID: ");
            try {
                int id = Integer.parseInt(sc.nextLine().trim());
                showPayHistory(id);
            } catch (NumberFormatException e) {
                System.out.println("Invalid Employee ID.");
            }
        }
    }

    public void showPayHistory(int empID) {
        List<String> history = service.getEmployeePayHistory(empID);
        if (history.isEmpty()) {
            System.out.println("No payroll records found for empID " + empID + ".");
            return;
        }
        System.out.println("=== Pay History (most recent first) ===");
        history.forEach(System.out::println);
    }
}
