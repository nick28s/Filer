package SWA.Filer.model;

public class DirectoriesResponse {
    private int id;
    private String name;

    public DirectoriesResponse() {
    }

    public DirectoriesResponse(int id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String username) {
        this.name = username;
    }
}
