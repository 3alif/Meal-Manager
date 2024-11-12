import java.sql.Connection;
import java.sql.PreparedStatement;

public class DeleteData {
    public static void delete(int id) {
        String sql = "DELETE FROM meals WHERE id = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Data deleted successfully.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        delete(1); // Delete member with ID 1
    }
}
