package io.github.thehaleemanasir.repositories;

import io.github.thehaleemanasir.model.Property;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Archive {

    public static void archiveRecord(Connection connection, int id) throws SQLException {
        PropertyRepository propertyRepository = new PropertyBeanRepository(connection);

        Property property = propertyRepository.getPropertyById(id);
        if (property == null) {
            System.out.println("Record with ID  " + id + " not found in the database.");
            return;
        }
        property.setArchived(true);
        propertyRepository.archiveProperty(property);
        System.out.println("Record  with ID " + id + " archived into the table successfully.");
    }

    public static void unarchiveRecord(Connection connection, int id) {
        System.out.println("Trying to unarchive record with ID: " + id);

        String unarchiveQuery = "DELETE FROM archiveProperty WHERE id = ?";
        try (PreparedStatement unarchiveStatement = connection.prepareStatement(unarchiveQuery)) {
            unarchiveStatement.setInt(1, id);

            int rowsAffected = unarchiveStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Record with ID " + id + " un-archived successfully.");
            } else {
                System.out.println("Record with ID " + id + " not found in the archive.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
