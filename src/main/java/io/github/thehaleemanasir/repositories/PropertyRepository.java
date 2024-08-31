package io.github.thehaleemanasir.repositories;

import java.sql.SQLException;
import java.util.List;

import io.github.thehaleemanasir.model.Property;

public interface PropertyRepository {

    int createProperty(Property property) throws SQLException;

    boolean deleteProperty(Property property) throws SQLException;

    boolean deletePropertyById(int propertyId) throws SQLException;

    List<Property> getPropertyByAgentIdAndIsArchived(int agentId) throws SQLException;

    Property getPropertyById(int propertyId) throws SQLException;

    List<Property> getAllProperties() throws SQLException;

    boolean archiveProperty(Property property) throws SQLException;

    boolean updateProperty(Property property) throws SQLException;
}
