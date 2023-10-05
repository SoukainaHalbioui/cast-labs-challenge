package com.castLabs.file;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class FileController {

    @Autowired
    private FileService fileService;

    @GetMapping("/file/analysis")
    public ResponseEntity<?> analyzeFile(@RequestParam(value = "file_url") String fileUrl) {
        return ResponseEntity.ok(fileService.analyzeFile(fileUrl));
    }
}
