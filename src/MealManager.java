import java.sql.Connection;
import java.sql.Statement;
import java.util.Scanner;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

public class MealManager {
    private static Scanner scanner = new Scanner(System.in);
    private static boolean isLoggedIn = false;

    public static void showMenu() {
        System.out.println("\n--- Mess Manager Menu ---");
        System.out.println("1. Register User");
        System.out.println("2. Login");
        System.out.println("3. Add Member");
        System.out.println("4. Add Meal");
        System.out.println("5. Add Deposit");
        System.out.println("6. Add Expense");
        System.out.println("7. Generate Reports");
        System.out.println("8. Exit");
        System.out.print("Choose an option: ");
    }

    public static void handleUserActions() {
        while (true) {
            showMenu();
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    System.out.print("Enter username: ");
                    String username = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String password = scanner.nextLine();
                    UserAuthentication.registerUser(username, password);
                    break;
                case 2:
                    System.out.print("Enter username: ");
                    String loginUsername = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String loginPassword = scanner.nextLine();
                    isLoggedIn = UserAuthentication.loginUser(loginUsername, loginPassword);
                    break;
                case 3:
                    if (isLoggedIn) {
                        System.out.print("Enter member name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter deposit amount: ");
                        double deposit = Double.parseDouble(scanner.nextLine());
                        Member.addMember(name, deposit);
                    } else {
                        System.out.println("Please login first.");
                    }
                    break;
                case 4:
                    if (isLoggedIn) {
                        System.out.print("Enter member ID: ");
                        int memberId = Integer.parseInt(scanner.nextLine());
                        System.out.print("Enter meal count: ");
                        int mealCount = Integer.parseInt(scanner.nextLine());
                        InsertData.insert(memberId, mealCount);
                    } else {
                        System.out.println("Please login first.");
                    }
                    break;
                case 5:
                    if (isLoggedIn) {
                        System.out.print("Enter member ID: ");
                        int memberDepositId = Integer.parseInt(scanner.nextLine());
                        System.out.print("Enter deposit amount: ");
                        double additionalDeposit = Double.parseDouble(scanner.nextLine());
                        Member.updateDeposit(memberDepositId, additionalDeposit);
                    } else {
                        System.out.println("Please login first.");
                    }
                    break;
                case 6:
                    if (isLoggedIn) {
                        System.out.print("Enter expense description: ");
                        String description = scanner.nextLine();
                        System.out.print("Enter expense amount: ");
                        double amount = Double.parseDouble(scanner.nextLine());
                        Expense.addExpense(description, amount);
                    } else {
                        System.out.println("Please login first.");
                    }
                    break;
                case 7:
                    if (isLoggedIn) {
                        ReportGenerator.generateTotalExpenseReport();
                        ReportGenerator.generateMemberMealReport();
                        ReportGenerator.generateMemberBalanceReport();
                    } else {
                        System.out.println("Please login first.");
                    }
                    break;
                case 8:
                    System.out.println("Exiting the application.");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public static double calculateTotalExpenses() {
        String sql = "SELECT SUM(amount) as total FROM expenses";
        double totalExpenses = 0;

        try (Connection conn = DatabaseConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                totalExpenses = rs.getDouble("total");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return totalExpenses;
    }

    public static int calculateTotalMeals() {
        String sql = "SELECT SUM(meal_count) as total FROM meals";
        int totalMeals = 0;

        try (Connection conn = DatabaseConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                totalMeals = rs.getInt("total");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return totalMeals;
    }

    public static void updateMemberBalances() {
        double totalExpenses = calculateTotalExpenses();
        int totalMeals = calculateTotalMeals();
        double costPerMeal = totalMeals > 0 ? totalExpenses / totalMeals : 0;

        String sql = "SELECT member_id, SUM(meal_count) as total_meals FROM meals GROUP BY member_id";

        try (Connection conn = DatabaseConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int memberId = rs.getInt("member_id");
                int memberMeals = rs.getInt("total_meals");
                double individualCost = memberMeals * costPerMeal;

                String updateBalanceSql = "UPDATE members SET balance = balance - ? WHERE id = ?";

                try (PreparedStatement pstmt = conn.prepareStatement(updateBalanceSql)) {
                    pstmt.setDouble(1, individualCost);
                    pstmt.setInt(2, memberId);
                    pstmt.executeUpdate();
                }
            }
            System.out.println("Member balances updated based on meal consumption.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        handleUserActions(); // Start the main menu
    }
}
