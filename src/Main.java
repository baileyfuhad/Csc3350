import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        PayrollUI payrollUI = new PayrollUI();
        ReportsUI reportsUI = new ReportsUI();

        while (true) {
            System.out.println("=== MAIN MENU ===");
            System.out.println("1. Payroll");
            System.out.println("2. Reports");
            System.out.println("0. Exit");
            System.out.print("Choose: ");

            int choice = sc.nextInt();

            switch (choice) {
                case 1 -> payrollUI.showPayrollMenu();
                case 2 -> reportsUI.showReportsMenu();
                case 0 -> {
                    System.out.println("Goodbye!");
                    return;
                }
            }
        }
    }
}
