package SWA.Filer.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
public class UserResponse {
    private int id;
    private String username;

    public UserResponse() {
    }

    public UserResponse(int id, String username) {
        this.id = id;
        this.username = username;
    }

    // Getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
