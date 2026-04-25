// Login module updated by Heavena

import java.util.Scanner;

public class Login {

    public User showLogin() {

        Scanner scanner = new Scanner(System.in);

        System.out.println("===== LOGIN SCREEN =====");

        System.out.print("Enter Employee ID: ");
        int empID = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        // Temporary login accounts
        if (empID == 1001 && password.equals("admin123")) {
            System.out.println("Login Successful!");
            return new User(1001, "Admin", "HR_ADMIN");
        }

        if (empID == 1002 && password.equals("user123")) {
            System.out.println("Login Successful!");
            return new User(1002, "Employee", "EMPLOYEE");
        }

        System.out.println("Invalid Login.");
        return null;
    }
}
