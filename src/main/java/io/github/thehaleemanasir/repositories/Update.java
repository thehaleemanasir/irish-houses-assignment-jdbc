package io.github.thehaleemanasir.repositories;

import io.github.thehaleemanasir.utils.DatabaseUtility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;


public class Update {



    public static void updateProperty() throws SQLException {
        try (Connection connection = DatabaseUtility.getConnection();
             Scanner scanner = new Scanner(System.in)) {

            System.out.print("Enter the ID of the property you wish to update: ");
            int propertyId = scanner.nextInt();
            scanner.nextLine();
///////////////////////////////////////
            String selectQuery = "SELECT * FROM properties WHERE id=?";
            String updateQuery = "UPDATE properties SET street=?, city=?, listingNum=?, styleId=?, typeId=?, bedrooms=?, " +
                    "bathrooms=?, squarefeet=?, berRating=?, description=?, lotsize=?, garagesize=?, garageId=?, " +
                    "agentId=?, photo=?, price=?, dateAdded=? WHERE id=?";

            try (PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
                 PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {

                selectStatement.setInt(1, propertyId);
                ResultSet resultSet = selectStatement.executeQuery();

                if (resultSet.next()) {

                    updateStatement.setInt(18, propertyId);
                    promptUpdateAttribute("Street", resultSet.getString("street"), updateStatement, 1, scanner);
                    promptUpdateAttribute("City", resultSet.getString("city"), updateStatement, 2, scanner);

                    promptUpdateAttribute("ListingNum", String.valueOf(resultSet.getInt("listingNum")), updateStatement, 3, scanner);
                    promptUpdateAttribute("StyleId", String.valueOf(resultSet.getInt("styleId")), updateStatement, 4, scanner);
                    promptUpdateAttribute("TypeId", String.valueOf(resultSet.getInt("typeId")), updateStatement, 5, scanner);
                    promptUpdateAttribute("Bedrooms", String.valueOf(resultSet.getInt("bedrooms")), updateStatement, 6, scanner);
                    promptUpdateAttribute("Bathrooms", String.valueOf(resultSet.getFloat("bathrooms")), updateStatement, 7, scanner);
                    promptUpdateAttribute("SquareFeet", String.valueOf(resultSet.getInt("squarefeet")), updateStatement, 8, scanner);
                    promptUpdateAttribute("BerRating", resultSet.getString("berRating"), updateStatement, 9, scanner);
                    promptUpdateAttribute("Description", resultSet.getString("description"), updateStatement, 10, scanner);
                    promptUpdateAttribute("LotSize", resultSet.getString("lotsize"), updateStatement, 11, scanner);
                    promptUpdateAttribute("GarageSize", String.valueOf(resultSet.getInt("garagesize")), updateStatement, 12, scanner);
                    promptUpdateAttribute("GarageId", String.valueOf(resultSet.getInt("garageId")), updateStatement, 13, scanner);
                    promptUpdateAttribute("AgentId", String.valueOf(resultSet.getInt("agentId")), updateStatement, 14, scanner);
                    promptUpdateAttribute("Photo", resultSet.getString("photo"), updateStatement, 15, scanner);
                    promptUpdateAttribute("Price", String.valueOf(resultSet.getDouble("price")), updateStatement, 16, scanner);
                    promptUpdateAttribute("DateAdded", resultSet.getDate("dateAdded").toString(), updateStatement, 17, scanner);



                    int rowsAffected = updateStatement.executeUpdate();
                    System.out.println(rowsAffected + " row(s) updated.");
                } else {
                    System.out.println("Property not found with ID: " + propertyId);
                }
            }
        }
    }

    private static PreparedStatement promptUpdateAttribute(String attributeName, String currentValue,
                                                              PreparedStatement updateStatement, int index, Scanner scanner) throws SQLException {
        System.out.print("Would you like to update " + attributeName + "? (Y/N): ");
        String updateAttribute = scanner.nextLine();
        if ("y".equalsIgnoreCase(updateAttribute)) {
            System.out.print("Enter new " + attributeName + ": ");
            String newValue = scanner.nextLine();
            updateStatement.setString(index, newValue);
        } else {
            System.out.println("Update of " + attributeName + " cancelled.");
            updateStatement.setString(index, currentValue);
        }
        return updateStatement;
    }

}
