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
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDirectory(@PathVariable("id") int id) {

        try {
            // Get the database connection
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/filer", "root", "1234");

            // Prepare the SQL query
            String sql = "DELETE FROM directories WHERE id = ?";

            // Create a prepared statement and set the parameter
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            // Execute the query
            statement.executeUpdate();

            // Clean up resources
            statement.close();
            connection.close();

            return ResponseEntity.ok("Directory deleted successfully.");
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while deleting Directory: " + e.getMessage());
        }
    }
    @GetMapping("/listdirectories")
    public ResponseEntity<List<DirectoriesResponse>> getDirectories() {
        try {
            // Get the database connection
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/filer", "root", "1234");

            // Prepare the SQL query
            String sql = "SELECT id, name FROM directories";

            // Create a statement and execute the query
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            // Create a list to store the users
            List<DirectoriesResponse> directories = new ArrayList<>();

            // Iterate over the result set and create UserResponse objects
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");

                DirectoriesResponse directory = new DirectoriesResponse(id, name);
                directories.add(directory);
            }

            // Clean up resources
            resultSet.close();
            statement.close();
            connection.close();

            // Return the list of users
            return ResponseEntity.ok(directories);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<String> updateDirectory(@PathVariable("id") int id,
                                                  @RequestBody DirectoriesRequest directoriesRequest) {
        try {
            // Get the database connection
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/filer", "root", "1234");

            // Prepare the SQL query
            String sql = "UPDATE directories SET name = ? WHERE id = ?";

            // Create a prepared statement and set the parameters
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, directoriesRequest.getName());
            statement.setInt(2, id);

            // Execute the query
            statement.executeUpdate();

            // Clean up resources
            statement.close();
            connection.close();

            return ResponseEntity.ok("Directory updated successfully.");
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while updating Directory: " + e.getMessage());
        }
    }
}