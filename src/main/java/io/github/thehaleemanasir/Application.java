package io.github.thehaleemanasir;

import io.github.thehaleemanasir.utils.DatabaseUtility;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import static io.github.thehaleemanasir.repositories.Archive.archiveRecord;
import static io.github.thehaleemanasir.repositories.Archive.unarchiveRecord;

public class Application {
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
}
