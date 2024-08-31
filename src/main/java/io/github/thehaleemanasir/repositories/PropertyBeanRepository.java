package io.github.thehaleemanasir.repositories;

import io.github.thehaleemanasir.model.Property;
import io.github.thehaleemanasir.utils.DatabaseUtility;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

/**
 * Repository class using Java Beans for the Property model
 */
public class PropertyBeanRepository implements PropertyRepository {

    private final Connection connection;

    public PropertyBeanRepository(Connection connection) {
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
        String sql = "INSERT INTO properties (Street, City, ListingNum, StyleId, TypeId, Bedrooms, Bathrooms, SquareFeet, "
                +
                "BerRating, Description, LotSize, GarageSize, GarageId, AgentId, Photo, Price, DateAdded) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        Object[] params = {
                property.getStreet(),
                property.getCity(),
                property.getListingNum(),
                property.getStyleId(),
                property.getTypeId(),
                property.getBedrooms(),
                property.getBathrooms(),
                property.getSquareFeet(),
                property.getBerRating(),
                property.getDescription(),
                property.getLotSize(),
                property.getGarageSize(),
                property.getGarageId(),
                property.getAgentId(),
                property.getPhoto(),
                property.getPrice(),
                new java.sql.Date(property.getDateAdded().getTime())
        };

        final QueryRunner runner = new QueryRunner();
        runner.update(connection, sql, params);

        int newId = DatabaseUtility.getNewId(connection);
        property.setId(newId);
        return newId;
    }

    public boolean deleteProperty(Property property) throws SQLException {
        String sql = "DELETE FROM property WHERE Id = ?";
        final QueryRunner runner = new QueryRunner();
        return runner.update(connection, sql, property.getId()) > 0;
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
     * 
     * @param agentId The ID of the agent
     * @return A list of properties that are archived for the agent
     * @throws SQLException If an error occurs while fetching the properties
     */
    public List<Property> getPropertyByAgentIdAndIsArchived(int agentId) throws SQLException {
        String sql = "SELECT * FROM properties WHERE AgentId = ? AND archived = 1";
        final QueryRunner runner = new QueryRunner();
        final var listHandler = new BeanListHandler<>(Property.class);
        return runner.query(connection, sql, listHandler, agentId);
    }

    public Property getPropertyById(int propertyId) throws SQLException {
        String sql = "SELECT * FROM properties WHERE Id = ?";

        final QueryRunner runner = new QueryRunner();
        final var handler = new BeanHandler<>(Property.class);
        return runner.query(connection, sql, handler, propertyId);
    }

    public List<Property> getAllProperties() throws SQLException {
        String sql = "SELECT * FROM properties";
        final QueryRunner runner = new QueryRunner();
        final var listHandler = new BeanListHandler<>(Property.class);
        return runner.query(connection, sql, listHandler);
    }

    public boolean archiveProperty(Property property) throws SQLException {
        String sql = "UPDATE properties SET archived = 1 WHERE id = ?;";
        final QueryRunner runner = new QueryRunner();
        return runner.update(connection, sql, property.getId()) > 0;
    }

    public boolean updateProperty(Property property) throws SQLException {
        String sql = "UPDATE properties SET Street = ?, City = ?, ListingNum = ?, StyleId = ?, TypeId = ?, Bedrooms = ?, "
                +
                "Bathrooms = ?, SquareFeet = ?, BerRating = ?, Description = ?, LotSize = ?, GarageSize = ?, GarageId = ?, "
                +
                "AgentId = ?, Photo = ?, Price = ?, DateAdded = ? WHERE Id = ?";
        Object[] params = {
                property.getStreet(),
                property.getCity(),
                property.getListingNum(),
                property.getStyleId(),
                property.getTypeId(),
                property.getBedrooms(),
                property.getBathrooms(),
                property.getSquareFeet(),
                property.getBerRating(),
                property.getDescription(),
                property.getLotSize(),
                property.getGarageSize(),
                property.getGarageId(),
                property.getAgentId(),
                property.getPhoto(),
                property.getPrice(),
                new java.sql.Date(property.getDateAdded().getTime()),
                property.getId()
        };

        final QueryRunner runner = new QueryRunner();
        return runner.update(connection, sql, params) > 0;
    }
}
