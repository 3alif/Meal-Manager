import java.sql.Connection;
import java.sql.Statement;

public class CreateUserTable {
    public static void createUserTable() {
        String createUserTable = "CREATE TABLE IF NOT EXISTS users (\n"
                + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + " username TEXT NOT NULL UNIQUE,\n"
                + " password TEXT NOT NULL\n"
                + ");";

        try (Connection conn = DatabaseConnection.connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(createUserTable);
            System.out.println("User table created successfully.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        createUserTable(); // Create the user table
    }
}
