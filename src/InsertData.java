import java.sql.Connection;
import java.sql.PreparedStatement;

public class InsertData {
    public static void insert(int member_id, int mealCount) {
        String sql = "INSERT INTO meals(member_name, meal_count) VALUES(?, ?)";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, member_id);
            pstmt.setInt(2, mealCount);
            pstmt.executeUpdate();
            System.out.println("Data inserted successfully.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        insert(1, 3); // Insert example data
    }
}
