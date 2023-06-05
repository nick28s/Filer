package SWA.Filer.controller;

import SWA.Filer.model.*;

import SWA.Filer.payload.Response;
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

@RestController
@RequestMapping("/files")
public class FileListController {
    @GetMapping("/{userID}/listfiles")
    public ResponseEntity<List<FileResponse>> getFiles(@PathVariable("userID") int uid) {
        try {
            // Get the database connection
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/filer", "root", "1234");

            // Prepare the SQL query
            String sql = "SELECT id, fileName, fileType, directoryID,userID FROM files WHERE userID = ?";

            // Create a prepared statement and set the parameters
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, uid);

            //execute the query
            ResultSet resultSet = statement.executeQuery();

            // Create a list to store the files
            List<FileResponse> files = new ArrayList<>();

            // Iterate over the result
            while (resultSet.next()) {
                String id_ = resultSet.getString("id");
                String fileName = resultSet.getString("fileName");
                String fileType = resultSet.getString("fileType");
                int directoryID = resultSet.getInt("directoryID");
                int userID = resultSet.getInt("userID");


                FileResponse file = new FileResponse(id_, fileName, fileType, directoryID, userID);
                files.add(file);
            }

            // Clean up resources
            resultSet.close();
            statement.close();
            connection.close();

            // Return the list of groups
            return ResponseEntity.ok(files);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
