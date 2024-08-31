package io.github.thehaleemanasir.repositories;

import io.github.thehaleemanasir.model.Property;
import io.github.thehaleemanasir.utils.DatabaseUtility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PropertyRepository {

    private final Connection connection;

    public PropertyRepository(Connection connection) {
        this.connection = connection;
    }

    /**
     * Create a new property record in the database
     *
     * @param property The property object to be created
     * @return The ID of the newly created property
     * @throws SQLException If an error occurs while creating the property
     */
    public int createProperty(Property property) throws SQLException {
        String sql = "INSERT INTO properties (Street, City, ListingNum, StyleId, TypeId, Bedrooms, Bathrooms, SquareFeet, " +
                "BerRating, Description, LotSize, GarageSize, GarageId, AgentId, Photo, Price, DateAdded) " +
                "VALUES (?, ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, property.getStreet());
            preparedStatement.setString(2, property.getCity());
            preparedStatement.setInt(3, property.getListingNum());
            preparedStatement.setInt(4, property.getStyleId());
            preparedStatement.setInt(5, property.getTypeId());
            preparedStatement.setInt(6, property.getBedrooms());
            preparedStatement.setFloat(7, property.getBathrooms());
            preparedStatement.setInt(8, property.getSquareFeet());
            preparedStatement.setString(9, property.getBerRating());
            preparedStatement.setString(10, property.getDescription());
            preparedStatement.setString(11, property.getLotSize());
            preparedStatement.setByte(12, property.getGarageSize());
            preparedStatement.setInt(13, property.getGarageId());
            preparedStatement.setInt(14, property.getAgentId());
            preparedStatement.setString(15, property.getPhoto());
            preparedStatement.setDouble(16, property.getPrice());
            preparedStatement.setDate(17, new java.sql.Date(property.getDateAdded().getTime()));
            preparedStatement.executeUpdate();

            int newId = DatabaseUtility.getNewId(preparedStatement);
            property.setId(newId);
            return newId;
        }
    }

    public boolean deleteProperty(Property property) throws SQLException {
        String sql = "DELETE FROM property WHERE Id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, property.getId());
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        }
    }

    public boolean deletePropertyById(int propertyId) throws SQLException {
        Property property = getPropertyById(propertyId);
        if (property == null) {
            throw new IllegalArgumentException("Property with ID " + propertyId + " does not exist");
        }
        return deleteProperty(property);
    }

    /**
     * Get all archived properties associated with a specific agent
     * @param agentId The ID of the agent
     * @return A list of properties that are archived for the agent
     * @throws SQLException If an error occurs while fetching the properties
     */
    public List<Property> getPropertyByAgentIdAndIsArchived(int agentId) throws SQLException {
        String query = "SELECT * FROM properties WHERE AgentId = ? AND archived = 1";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, agentId);
            try (ResultSet resultSet = statement.executeQuery()) {
                List<Property> properties = new ArrayList<>();
                while (resultSet.next()) {
                    properties.add(mapResultSetToProperty(resultSet));
                }
                return properties;
            }
        }
    }

    public Property getPropertyById(int propertyId) throws SQLException {
        String query = "SELECT * FROM properties WHERE Id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, propertyId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToProperty(resultSet);
                }
                return null;
            }
        }
    }


    private Property mapResultSetToProperty(ResultSet resultSet) throws SQLException {
        Property property = new Property();
        property.setId(resultSet.getInt("Id"));
        property.setStreet(resultSet.getString("Street"));
        property.setCity(resultSet.getString("City"));
        property.setListingNum(resultSet.getInt("ListingNum"));
        property.setStyleId(resultSet.getInt("StyleId"));
        property.setTypeId(resultSet.getInt("TypeId"));
        property.setBedrooms(resultSet.getInt("Bedrooms"));
        property.setBathrooms(resultSet.getFloat("Bathrooms"));
        property.setSquareFeet(resultSet.getInt("SquareFeet"));
        property.setBerRating(resultSet.getString("BerRating"));
        property.setDescription(resultSet.getString("Description"));
        property.setLotSize(resultSet.getString("LotSize"));
        property.setGarageSize(resultSet.getByte("GarageSize"));
        property.setGarageId(resultSet.getInt("GarageId"));
        property.setAgentId(resultSet.getInt("AgentId"));
        property.setPhoto(resultSet.getString("Photo"));
        property.setPrice(resultSet.getDouble("Price"));
        property.setDateAdded(new java.util.Date(resultSet.getDate("DateAdded").getTime()));
        return property;
    }

    public List<Property> getAllProperties() throws SQLException {
        String query = "SELECT * FROM properties";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                List<Property> properties = new ArrayList<>();
                while (resultSet.next()) {
                    properties.add(mapResultSetToProperty(resultSet));
                }
                return properties;
            }
        }
    }

    public void archiveProperty(Property property) throws SQLException {
        String sql = "UPDATE properties SET archived = 1 WHERE id = ?;";
        try (PreparedStatement archiveStatement = connection.prepareStatement(sql)) {
            archiveStatement.setInt(1, property.getId());
            archiveStatement.executeUpdate();
        }
    }

    public void updateProperty(Property property) throws SQLException {
        String sql = "UPDATE properties SET Street = ?, City = ?, ListingNum = ?, StyleId = ?, TypeId = ?, Bedrooms = ?, " +
                "Bathrooms = ?, SquareFeet = ?, BerRating = ?, Description = ?, LotSize = ?, GarageSize = ?, GarageId = ?, " +
                "AgentId = ?, Photo = ?, Price = ?, DateAdded = ? WHERE Id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, property.getStreet());
            preparedStatement.setString(2, property.getCity());
            preparedStatement.setInt(3, property.getListingNum());
            preparedStatement.setInt(4, property.getStyleId());
            preparedStatement.setInt(5, property.getTypeId());
            preparedStatement.setInt(6, property.getBedrooms());
            preparedStatement.setFloat(7, property.getBathrooms());
            preparedStatement.setInt(8, property.getSquareFeet());
            preparedStatement.setString(9, property.getBerRating());
            preparedStatement.setString(10, property.getDescription());
            preparedStatement.setString(11, property.getLotSize());
            preparedStatement.setByte(12, property.getGarageSize());
            preparedStatement.setInt(13, property.getGarageId());
            preparedStatement.setInt(14, property.getAgentId());
            preparedStatement.setString(15, property.getPhoto());
            preparedStatement.setDouble(16, property.getPrice());
            preparedStatement.setDate(17, new java.sql.Date(property.getDateAdded().getTime()));
            preparedStatement.setInt(18, property.getId());
            preparedStatement.executeUpdate();
        }

    }
}
