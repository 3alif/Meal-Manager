import java.sql.Connection;
import java.sql.PreparedStatement;

public class Member {
    public static void addMember(String name, double deposit) {
        String sql = "INSERT INTO members(name, deposit, balance) VALUES(?, ?, ?)";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setDouble(2, deposit);
            pstmt.setDouble(3, deposit); // Initial balance is equal to the deposit
            pstmt.executeUpdate();
            System.out.println("Member added successfully.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void updateDeposit(int memberId, double additionalDeposit) {
        String sql = "UPDATE members SET deposit = deposit + ?, balance = balance + ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, additionalDeposit);
            pstmt.setDouble(2, additionalDeposit);
            pstmt.setInt(3, memberId);
            pstmt.executeUpdate();
            System.out.println("Deposit updated successfully.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        addMember("Alice", 500); // Add example member
        updateDeposit(1, 200); // Update deposit for member with ID 1
    }
}
