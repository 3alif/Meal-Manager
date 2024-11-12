import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

public class ReportGenerator {
    public static void generateTotalExpenseReport() {
        double totalExpenses = MealManager.calculateTotalExpenses();
        System.out.println("Total Mess Expenses: " + totalExpenses);
    }

    public static void generateMemberMealReport() {
        String sql = "SELECT members.name, SUM(meals.meal_count) as total_meals "
                   + "FROM members INNER JOIN meals ON members.id = meals.member_id "
                   + "GROUP BY members.id";

        try (Connection conn = DatabaseConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("Member Meal Report:");
            while (rs.next()) {
                String name = rs.getString("name");
                int totalMeals = rs.getInt("total_meals");
                System.out.println(name + " has consumed " + totalMeals + " meals.");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void generateMemberBalanceReport() {
        String sql = "SELECT name, balance FROM members";

        try (Connection conn = DatabaseConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("Member Balance Report:");
            while (rs.next()) {
                String name = rs.getString("name");
                double balance = rs.getDouble("balance");
                System.out.println(name + " has a balance of: " + balance);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        generateTotalExpenseReport();
        generateMemberMealReport();
        generateMemberBalanceReport();
    }
}
