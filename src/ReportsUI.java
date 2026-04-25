import java.util.List;
import java.util.Scanner;

public class ReportsUI {

    public void showReportsMenu() {
        Scanner sc = new Scanner(System.in);
        ReportsService service = new ReportsService();

        System.out.println("=== Reports Menu ===");
        System.out.println("1. Total Pay by Job Title");
        System.out.println("2. Total Pay by Division");
        System.out.println("3. New Hires in Date Range");
        System.out.println("0. Back");
        System.out.print("Choose: ");

        int choice = sc.nextInt();

        switch (choice) {
            case 1 -> {
                System.out.print("Enter month (YYYY-MM): ");
                String m = sc.next();
                List<String> list = service.getPayByJobTitle(m);
                list.forEach(System.out::println);
            }
            case 2 -> {
                System.out.print("Enter month (YYYY-MM): ");
                String m = sc.next();
                List<String> list = service.getPayByDivision(m);
                list.forEach(System.out::println);
            }
            case 3 -> {
                System.out.print("Start date (YYYY-MM-DD): ");
                String s = sc.next();
                System.out.print("End date (YYYY-MM-DD): ");
                String e = sc.next();
                List<String> list = service.getNewHires(s, e);
                list.forEach(System.out::println);
            }
        }
    }
}
