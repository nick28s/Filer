package SWA.Filer.payload;

public class Response {
    private String fileName;
    private String fileDownloadUri;
    private String fileType;
    private long size;

    private int directoryID;
    private int userID;

    public Response(String fileName, String fileDownloadUri, String fileType, long size, int directoryID, int userID) {
        this.fileName = fileName;
        this.fileDownloadUri = fileDownloadUri;
        this.fileType = fileType;
        this.size = size;
        this.directoryID = directoryID;
        this.userID = userID;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileDownloadUri() {
        return fileDownloadUri;
    }

    public void setFileDownloadUri(String fileDownloadUri) {
        this.fileDownloadUri = fileDownloadUri;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getDirectoryID() {
        return directoryID;
    }

    public void setDirectoryID(int directoryID) {
        this.directoryID = directoryID;
    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}
