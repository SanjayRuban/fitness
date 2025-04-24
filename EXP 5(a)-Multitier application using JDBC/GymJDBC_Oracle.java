

import java.sql.*;
import java.util.Scanner;

public class GymJDBC_Oracle {

    private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe"; // Replace with your Oracle SID or service name
    private static final String USER = "SYSTEM";
    private static final String PASSWORD = "dbms123";

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {

            Class.forName("oracle.jdbc.driver.OracleDriver");

            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected to Oracle database!");

            while (true) {
                System.out.println("\nChoose an operation:");
                System.out.println("1. Insert Member");
                System.out.println("2. Update Member");
                System.out.println("3. Delete Member");
                System.out.println("4. View Members");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        System.out.print("Enter name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter age: ");
                        int age = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Enter membership type: ");
                        String membershipType = scanner.nextLine();
                        insertMember(conn, name, age, membershipType);
                        break;
                    case 2:
                        System.out.print("Enter ID to update: ");
                        int updateId = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Enter new membership type: ");
                        String newMembership = scanner.nextLine();
                        updateMember(conn, updateId, newMembership);
                        break;
                    case 3:
                        System.out.print("Enter ID to delete: ");
                        int deleteId = scanner.nextInt();
                        deleteMember(conn, deleteId);
                        break;
                    case 4:
                        viewMembers(conn);
                        break;
                    case 5:
                        System.out.println("Exiting...");
                        conn.close();
                        System.out.println("Connection closed.");
                        return;
                    default:
                        System.out.println("Invalid choice! Please try again.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void insertMember(Connection conn, String name, int age, String membershipType) throws SQLException {
        String sql = "INSERT INTO members (name, age, membership_type) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setInt(2, age);
            pstmt.setString(3, membershipType);
            int rows = pstmt.executeUpdate();
            System.out.println("Inserted " + rows + " member(s) successfully.");
        }
    }

    public static void updateMember(Connection conn, int id, String newMembership) throws SQLException {
        String sql = "UPDATE members SET membership_type = ? WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newMembership);
            pstmt.setInt(2, id);
            int rows = pstmt.executeUpdate();
            System.out.println("Updated " + rows + " member(s) successfully.");
        }
    }

    public static void deleteMember(Connection conn, int id) throws SQLException {
        String sql = "DELETE FROM members WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int rows = pstmt.executeUpdate();
            System.out.println("Deleted " + rows + " member(s) successfully.");
        }
    }

    public static void viewMembers(Connection conn) throws SQLException {
        String sql = "SELECT * FROM members";
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            System.out.println("\nGym Members:");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") +
                        ", Name: " + rs.getString("name") +
                        ", Age: " + rs.getInt("age") +
                        ", Membership: " + rs.getString("membership_type"));
            }
        }
    }
}
