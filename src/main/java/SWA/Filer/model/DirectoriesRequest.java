package SWA.Filer.model;

public class DirectoriesRequest {
    private String name;

    public DirectoriesRequest() {
    }

    public DirectoriesRequest(String name) {
        this.name = name;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

