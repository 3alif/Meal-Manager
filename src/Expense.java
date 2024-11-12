import java.sql.Connection;
import java.sql.PreparedStatement;

public class Expense {
    public static void addExpense(String description, double amount) {
        String sql = "INSERT INTO expenses(description, amount) VALUES(?, ?)";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, description);
            pstmt.setDouble(2, amount);
            pstmt.executeUpdate();
            System.out.println("Expense added successfully.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        addExpense("Grocery", 1000); // Example expense
    }
}
