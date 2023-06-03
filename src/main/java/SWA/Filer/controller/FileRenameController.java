package SWA.Filer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import SWA.Filer.model.DatabaseFile;
import SWA.Filer.payload.Response;
import SWA.Filer.service.DatabaseFileService;

@RestController
public class FileRenameController {

    @Autowired
    private DatabaseFileService fileStorageService;

    // Endpoint to rename a file using a PUT request
    @PutMapping("/files/{id}/rename")
    public Response renameFile(@PathVariable String id, @RequestParam("newFileName") String newFileName) {

        // Get the file record from the database using the file storage service
        DatabaseFile databaseFile = fileStorageService.getFile(id);

        // If the file record is not found, it can be handled as an error
        if (databaseFile == null) {
            return null;
        }

        // Rename the file using the file storage service
        fileStorageService.renameFile(databaseFile, newFileName);

        // Create the download URI for the new file
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/files/download/")
                .path(newFileName)
                .toUriString();

        // Return a response object containing the new file name, download URI, file type, and size
        return new Response(newFileName, fileDownloadUri, databaseFile.getFileType(), databaseFile.getData().length);
    }
}
