package SWA.Filer.model;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import SWA.Filer.exception.FileStorageException;
import SWA.Filer.service.DatabaseFileService;

@Entity
@Table(name = "files")
public class DatabaseFile {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String fileName;

    private String fileType;

    @Lob
    private byte[] data;

    private int directoryID = 0;

    public DatabaseFile() {

    }

    public DatabaseFile(String fileName, String fileType, byte[] data, int directoryID) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.data = data;
        this.directoryID = directoryID;
    }

    public String getId() {
        return id;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public byte[] getData() {
        return data;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public void setDirectoryID(int directoryID) {
        this.directoryID = directoryID;
    }

    public int getDirectoryID() {
        return directoryID;
    }




}
