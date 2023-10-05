package com.example.infosystems.web;

import com.example.infosystems.service.FileService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@AllArgsConstructor
@RequestMapping("/api/files")
public class FilesController {

    private final FileService fileService;

    @PostMapping(value = "/{fileType}/{recordId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> documentUpload(@RequestParam("documents") final MultipartFile[] documents,
                                                 @PathVariable final String fileType,
                                                 @PathVariable final Integer recordId) {

        final String fileNames = this.fileService.uploadFiles(documents, recordId, fileType);
        return ResponseEntity.ok("Sucesfull uploaded files with names: " + fileNames);
    }

    @DeleteMapping(value = "/{fileType}/{recordId}/{filename}")
    public ResponseEntity<String> documentUpload(@PathVariable("fileType") final String fileType,
                                                 @PathVariable("recordId") final Integer recordId,
                                                 @PathVariable("filename") final String fileName) {
        this.fileService.deleteFile(fileType, recordId, fileName);
        return ResponseEntity.ok("Successfully delete file with name: " + fileName);
    }

}
