import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class FetchData {
    public static void selectAll() {
        String sql = "SELECT * FROM meals";

        try (Connection conn = DatabaseConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            // Loop through the result set
            while (rs.next()) {
                System.out.println(rs.getInt("id") + "\t" +
                                   rs.getString("member_name") + "\t" +
                                   rs.getInt("meal_count"));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        selectAll(); // Fetch all data
    }
}
