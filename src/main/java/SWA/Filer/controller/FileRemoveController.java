package SWA.Filer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import SWA.Filer.model.DatabaseFile;
import SWA.Filer.service.DatabaseFileService;

@RestController
public class FileRemoveController {

    @Autowired
    private DatabaseFileService fileStorageService;

    /**
     * Removes a file from the database.
     *
     * @param id The ID of the file to be removed.
     * @return A message indicating the result of the operation.
     */
    @DeleteMapping("/files/{id}/remove")

    public String removeFile(@PathVariable String id) {
        // Get the file record from the database using the file storage service
        DatabaseFile databaseFile = fileStorageService.getFile(id);

        // If the file record is not found, it can be handled as an error
        if (databaseFile == null) {
            return "The file with the id: " + id + " does not exist in the database.";
        }

        // Delete the file using the file storage service
        fileStorageService.deleteFile(databaseFile);

        return "The file with the id: " + id + " has been removed from the database.";
    }
}
