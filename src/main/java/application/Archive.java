package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import javax.xml.crypto.Data;

public class Archive {

    public static void main(String[] args) throws SQLException {
        try (Connection connection = DatabaseUtility.getConnection()) {

            try (Scanner scanner = new Scanner(System.in)) {
                System.out.print("Enter the ID of the record to archive or unarchive: ");
                int recordId = scanner.nextInt();

                System.out.print("Do you want to archive (A) or unarchive (U) the record? ");
                String action = scanner.next().toUpperCase();

                if (action.equals("A")) {
                    archiveRecord(connection, recordId);

                } else if (action.equals("U")) {
                    unarchiveRecord(connection, recordId);
                } else {
                    System.out.println("Invalid action. Please enter 'A' for archive or 'U' for unarchive.");
                }
            }
        }
    }

    private static void archiveRecord(Connection connection, int id) throws SQLException {
        if(!isPropertyInDB(connection, id)) {
            System.out.println("Record with ID " + id + " not found in the database.");
            return;
        }

        String sql = "UPDATE properties SET archived = 1 WHERE id = ?;";
        try (PreparedStatement archiveStatement = connection.prepareStatement(sql)) {
            archiveStatement.setInt(1, id);
            archiveStatement.executeUpdate();
        }
        System.out.println("Record with ID " + id + " archived into the table successfully.");
    }

    private static boolean isPropertyInDB(Connection connection, int id) throws SQLException {
        String query = "SELECT * FROM properties WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        }
    }



    private static void unarchiveRecord(Connection connection, int id) {
        System.out.println("Trying to unarchive record with ID: " + id);

        String unarchiveQuery = "DELETE FROM archiveproperty WHERE id = ?";
        try (PreparedStatement unarchiveStatement = connection.prepareStatement(unarchiveQuery)) {
            unarchiveStatement.setInt(1, id);

            int rowsAffected = unarchiveStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Record with ID " + id + " unarchived successfully.");
            } else {
                System.out.println("Record with ID " + id + " not found in the archive.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
