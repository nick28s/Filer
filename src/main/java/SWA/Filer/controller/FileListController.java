package SWA.Filer.controller;

import SWA.Filer.model.DatabaseFile;
import SWA.Filer.model.GroupModel;
import SWA.Filer.model.GroupResponse;
import SWA.Filer.model.GroupRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;

public class FileListController {
    @GetMapping("files/{userID}/listfiles")
    public ResponseEntity<List<DatabaseFile>> getFiles(@PathVariable("userID") int id) {
        try {
            // Get the database connection
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/filer", "root", "1234");

            // Prepare the SQL query
            String sql = "SELECT id, fileName, directoryID, userID FROM files WHERE userID = ?";

            // Create a statement and execute the query
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            // Execute the query
            statement.executeUpdate();

            ResultSet resultSet = statement.executeQuery(sql);


            // Create a list to store the files
            List<DatabaseFile> databaseFiles = new ArrayList<>();

            // Iterate over the result set and create Group objects
            while (resultSet.next()) {
                String fileName = resultSet.getString("fileName");
                String fileType = resultSet.getString("fileType");
                byte[] data = resultSet.getBytes("data");
                int directoryID = resultSet.getInt("directoryID");
                int userID = resultSet.getInt("userID");

                DatabaseFile databaseFile = new DatabaseFile(fileName, fileType, data, directoryID, userID);
                databaseFiles.add(databaseFile);
            }

            // Clean up resources
            resultSet.close();
            statement.close();
            connection.close();

            // Return the list of groups
            return ResponseEntity.ok(databaseFiles);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
