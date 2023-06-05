package SWA.Filer.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import SWA.Filer.exception.FileNotFoundException;
import SWA.Filer.exception.FileStorageException;
import SWA.Filer.model.DatabaseFile;
import SWA.Filer.service.repository.DatabaseFileRepository;

@Service
public class DatabaseFileService {

    @Autowired
    private DatabaseFileRepository dbFileRepository;

    public DatabaseFile storeFile(MultipartFile file, int directoryID_, int userID_) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        int directoryID = directoryID_;
        int userID = userID_;


        try {
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            DatabaseFile dbFile = new DatabaseFile(fileName, file.getContentType(), file.getBytes(), directoryID,userID);
            return dbFileRepository.save(dbFile);
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public DatabaseFile getFile(String fileId) {
        return dbFileRepository.findById(fileId)
                .orElseThrow(() -> new FileNotFoundException("File not found with id " + fileId));
    }

    /*public void updateFile(DatabaseFile databaseFile) {
        dbFileRepository.save(databaseFile);
    }*/

    public void renameFile(DatabaseFile databaseFile, String newFileName) {
        // Used to rename the file
        databaseFile.setFileName(newFileName);
        // Used to save the updated file information in the database
        dbFileRepository.save(databaseFile);
    }

    public void deleteFile(DatabaseFile databaseFile) {
        // Perform the necessary operations to delete the file from the database
        dbFileRepository.delete(databaseFile);
    }

}
