package SWA.Filer.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import SWA.Filer.model.DatabaseFile;
import SWA.Filer.payload.Response;
import SWA.Filer.service.DatabaseFileService;

@RestController
public class FileUploadController {

    @Autowired
    private DatabaseFileService fileStorageService;

    @PostMapping("/uploadFile")
    public Response uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("directory") int directoryID, @RequestParam("user") int userID) {
        DatabaseFile fileName = fileStorageService.storeFile(file,directoryID,userID);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName.getFileName())
                .toUriString();

        return new Response(fileName.getFileName(), fileDownloadUri, file.getContentType(), file.getSize(), directoryID, userID);
    }

    @PostMapping("/uploadMultipleFiles")
    public List<Response> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files, @RequestParam("directory") int directoryID, @RequestParam("user") int userID) {
        return Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file,directoryID,userID))
                .collect(Collectors.toList());
    }
}