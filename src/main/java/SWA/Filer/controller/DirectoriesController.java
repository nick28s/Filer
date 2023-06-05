package SWA.Filer.controller;

import SWA.Filer.model.*;
import SWA.Filer.payload.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;
@RestController
@RequestMapping("/directories")
public class DirectoriesController {
    @PostMapping
    ResponseEntity<String> createDirectory(@RequestBody DirectoriesRequest directoriesRequest) throws SQLException {
        try {
            // Get the database connection
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/filer", "root", "1234");

            // Prepare the SQL query
            String sql = "INSERT INTO directories (name) VALUES (?)";

            // Create a prepared statement and set the parameters
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, directoriesRequest.getName());

            // Execute the query
            statement.executeUpdate();

            // Clean up resources
            statement.close();
            connection.close();

            return ResponseEntity.ok("Directory created successfully.");
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while creating a directory: " + e.getMessage());
        }
    }

}