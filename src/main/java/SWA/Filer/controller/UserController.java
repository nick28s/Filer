package SWA.Filer.controller;

import SWA.Filer.model.UserRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@RestController
@RequestMapping("/users")
public class UserController {

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody UserRequest userRequest) {
        // User creation process

        try {
            // Get the database connection
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/filer", "root", "1234");

            // Prepare the SQL query
            String sql = "INSERT INTO users (username, password) VALUES (?, ?)";

            // Create a prepared statement and set the parameters
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, userRequest.getUsername());
            statement.setString(2, userRequest.getPassword());

            // Execute the query
            statement.executeUpdate();

            // Clean up resources
            statement.close();
            connection.close();

            return ResponseEntity.ok("User created successfully.");
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while creating the user: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable("id") int id, @RequestBody UserRequest userRequest) {
        // User update process

        try {
            // Get the database connection
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/filer", "root", "1234");

            // Prepare the SQL query
            String sql = "UPDATE users SET username = ?, password = ? WHERE id = ?";

            // Create a prepared statement and set the parameters
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, userRequest.getUsername());
            statement.setString(2, userRequest.getPassword());
            statement.setInt(3, id);

            // Execute the query
            statement.executeUpdate();

            // Clean up resources
            statement.close();
            connection.close();

            return ResponseEntity.ok("User updated successfully.");
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while updating the user: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") int id) {
        // User deletion process

        try {
            // Get the database connection
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/filer", "root", "1234");

            // Prepare the SQL query
            String sql = "DELETE FROM users WHERE id = ?";

            // Create a prepared statement and set the parameter
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            // Execute the query
            statement.executeUpdate();

            // Clean up resources
            statement.close();
            connection.close();

            return ResponseEntity.ok("User deleted successfully.");
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while deleting the user: " + e.getMessage());
        }
    }


}
