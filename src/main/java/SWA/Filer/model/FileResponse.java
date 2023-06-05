package SWA.Filer.model;

public class FileResponse {
    private String id;
    private String fileName;
    private String fileType;
    private int directoryID;
    private int userID;

    public FileResponse() {
    }

    public FileResponse(String id, String fileName, String fileType, int directoryID, int userID) {
        this.id = id;
        this.fileName = fileName;
        this.fileType = fileType;
        this.directoryID = directoryID;
        this.userID = userID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public int getDirectoryID() {
        return directoryID;
    }

    public void setDirectoryID(int directoryID) {
        this.directoryID = directoryID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}
