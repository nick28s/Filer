package SWA.Filer.controller;

import SWA.Filer.model.UserRequest;
import SWA.Filer.model.UserResponse;
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
@RequestMapping("/users")
public class UserController {
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin1234";
    @GetMapping("/listusers")
    public ResponseEntity<List<UserResponse>> getUsers() {
        try {
            // Get the database connection
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/filer", "root", "1234");

            // Prepare the SQL query
            String sql = "SELECT id, username FROM users";

            // Create a statement and execute the query
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            // Create a list to store the users
            List<UserResponse> users = new ArrayList<>();

            // Iterate over the result set and create UserResponse objects
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String username = resultSet.getString("username");

                UserResponse user = new UserResponse(id, username);
                users.add(user);
            }

            // Clean up resources
            resultSet.close();
            statement.close();
            connection.close();

            // Return the list of users
            return ResponseEntity.ok(users);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @PostMapping
    public ResponseEntity<String> createUser(@RequestHeader("username") String username,
                                             @RequestHeader("password") String password,
                                             @RequestBody UserRequest userRequest) {
        // Check if the user is admin
        if (!isAdmin(username, password)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Only admins can create users.");
        }

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
    public ResponseEntity<String> updateUser(@RequestHeader("username") String username,
                                             @RequestHeader("password") String password,
                                             @PathVariable("id") int id,
                                             @RequestBody UserRequest userRequest) {
        // Check if the user is admin
        if (!isAdmin(username, password)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Only admins can update users.");
        }

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
    public ResponseEntity<String> deleteUser(@RequestHeader("username") String username,
                                             @RequestHeader("password") String password,
                                             @PathVariable("id") int id) {
        // Check if the user is admin
        if (!isAdmin(username, password)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Only admins can delete users.");
        }

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


    private boolean isAdmin(String username, String password) {
        return username.equals(ADMIN_USERNAME) && password.equals(ADMIN_PASSWORD);
    }
}




/*package SWA.Filer.controller;

import SWA.Filer.model.UserRequest;
import SWA.Filer.model.UserResponse;
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
@RequestMapping("/users")
public class UserController {
    @GetMapping("/listusers")
    public ResponseEntity<List<UserResponse>> getUsers() {
        try {
            // Get the database connection
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/filer", "root", "1234");

            // Prepare the SQL query
            String sql = "SELECT id, username FROM users";

            // Create a statement and execute the query
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            // Create a list to store the users
            List<UserResponse> users = new ArrayList<>();

            // Iterate over the result set and create UserResponse objects
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String username = resultSet.getString("username");

                UserResponse user = new UserResponse(id, username);
                users.add(user);
            }

            // Clean up resources
            resultSet.close();
            statement.close();
            connection.close();

            // Return the list of users
            return ResponseEntity.ok(users);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


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
*/