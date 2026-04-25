import java.util.List;
import java.util.Scanner;

public class PayrollUI {

    public void showPayrollMenu() {
        Scanner sc = new Scanner(System.in);
        PayrollService service = new PayrollService();

        System.out.println("=== Payroll Menu ===");
        System.out.println("1. View Pay History");
        System.out.println("0. Back");
        System.out.print("Choose: ");

        int choice = sc.nextInt();

        if (choice == 1) {
            System.out.print("Enter your Employee ID: ");
            int id = sc.nextInt();

            List<String> history = service.getEmployeePayHistory(id);

            if (history.isEmpty()) {
                System.out.println("No payroll records found.");
            } else {
                System.out.println("=== Pay History ===");
                history.forEach(System.out::println);
            }
        }
    }
}
