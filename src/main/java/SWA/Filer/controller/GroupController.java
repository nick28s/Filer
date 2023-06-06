package SWA.Filer.controller;

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

@RestController
@RequestMapping("/groupp")
public class GroupController {
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin1234";

    @GetMapping("/listgroups")
    public ResponseEntity<List<GroupModel>> getGroups() {
        try {
            // Get the database connection
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/filer", "root", "1234");

            // Prepare the SQL query
            String sql = "SELECT id, name FROM groupp";

            // Create a statement and execute the query
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            // Create a list to store the groups
            List<GroupModel> groupModels = new ArrayList<>();

            // Iterate over the result set and create Group objects
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");

                GroupModel groupModel = new GroupModel(id, name);
                groupModels.add(groupModel);
            }

            // Clean up resources
            resultSet.close();
            statement.close();
            connection.close();

            // Return the list of groups
            return ResponseEntity.ok(groupModels);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<String> createGroup(@RequestHeader("username") String username,
                                              @RequestHeader("password") String password,
                                              @RequestBody GroupModel groupModel) {
        // Check if the user is admin
        if (!isAdmin(username, password)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Only admins can create groups.");
        }

        try {
            // Get the database connection
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/filer", "root", "1234");

            // Prepare the SQL query
            String sql = "INSERT INTO groupp (name) VALUES (?)";

            // Create a prepared statement and set the parameter
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, groupModel.getName());

            // Execute the query
            statement.executeUpdate();

            // Clean up resources
            statement.close();
            connection.close();

            return ResponseEntity.ok("Group created successfully.");
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while creating the group: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateGroup(@RequestHeader("username") String username,
                                              @RequestHeader("password") String password,
                                              @PathVariable("id") int id,
                                              @RequestBody GroupRequest groupRequest) {
        // Check if the user is admin
        if (!isAdmin(username, password)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Only admins can update groups.");
        }

        try {
            // Get the database connection
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/filer", "root", "1234");

            // Prepare the SQL query
            String sql = "UPDATE groupp SET name = ? WHERE id = ?";

            // Create a prepared statement and set the parameters
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, groupRequest.getName());
            statement.setInt(2, id);

            // Execute the query
            statement.executeUpdate();

            // Clean up resources
            statement.close();
            connection.close();

            return ResponseEntity.ok("Group updated successfully.");
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while updating the group: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGroup(@RequestHeader("username") String username,
                                              @RequestHeader("password") String password,
                                              @PathVariable("id") int id) {
        // Check if the user is admin
        if (!isAdmin(username, password)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Only admins can delete groups.");
        }

        try {
            // Get the database connection
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/filer", "root", "1234");

            // Prepare the SQL query
            String sql = "DELETE FROM groupp WHERE id = ?";

            // Create a prepared statement and set the parameter
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            // Execute the query
            statement.executeUpdate();

            // Clean up resources
            statement.close();
            connection.close();

            return ResponseEntity.ok("Group deleted successfully.");
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while deleting the group: " + e.getMessage());
        }
    }


    @PostMapping("/{groupId}/addUser/{userId}")
    public ResponseEntity<String> addUserToGroup(@RequestHeader("username") String username,
                                                 @RequestHeader("password") String password,
                                                 @PathVariable("groupId") int groupId,
                                                 @PathVariable("userId") int userId) {
        // Check if the user is admin
        if (!isAdmin(username, password)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Only admins can add users to groups.");
        }

        try {
            // Get the database connection
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/filer", "root", "1234");

            // Prepare the SQL query
            String sql = "INSERT INTO user_groups (user_id, group_id) VALUES (?, ?)";

            // Create a prepared statement and set the parameters
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);
            statement.setInt(2, groupId);

            // Execute the query
            statement.executeUpdate();

            // Clean up resources
            statement.close();
            connection.close();

            return ResponseEntity.ok("User added to group successfully.");
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while adding the user to the group: " + e.getMessage());
        }
    }

    @DeleteMapping("/{groupId}/removeUser/{userId}")
    public ResponseEntity<String> removeUserFromGroup(@RequestHeader("username") String username,
                                                      @RequestHeader("password") String password,
                                                      @PathVariable("groupId") int groupId,
                                                      @PathVariable("userId") int userId) {
        // Check if the user is admin
        if (!isAdmin(username, password)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Only admins can remove users from groups.");
        }

        try {
            // Get the database connection
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/filer", "root", "1234");

            // Prepare the SQL query
            String sql = "DELETE FROM user_groups WHERE user_id = ? AND group_id = ?";

            // Create a prepared statement and set the parameters
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);
            statement.setInt(2, groupId);

            // Execute the query
            statement.executeUpdate();

            // Clean up resources
            statement.close();
            connection.close();

            return ResponseEntity.ok("User removed from group successfully.");
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while removing the user from the group: " + e.getMessage());
        }
    }

    public boolean isAdmin(String username, String password) {
        return username.equals(ADMIN_USERNAME) && password.equals(ADMIN_PASSWORD);
    }
}
