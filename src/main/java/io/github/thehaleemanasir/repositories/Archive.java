package io.github.thehaleemanasir.repositories;

import io.github.thehaleemanasir.model.Property;
import io.github.thehaleemanasir.utils.DatabaseUtility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Archive {



    public static void archiveRecord(Connection connection, int id) throws SQLException {
        PropertyRepository propertyRepository = new PropertyRepository(connection);

        Property property = propertyRepository.getPropertyById(id);
        if(property ==null) {
            System.out.println("Record with ID " + id + " not found in the database.");
            return;
        }
        property.setArchived(true);
        propertyRepository.archiveProperty(property);
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



    public static void unarchiveRecord(Connection connection, int id) {
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
