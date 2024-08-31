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

    void archiveProperty(Property property) throws SQLException;

    void updateProperty(Property property) throws SQLException;
}
