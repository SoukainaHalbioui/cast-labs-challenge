package com.castLabs.file;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FileController {

    @Autowired
    private FileService fileService;

    @GetMapping("/file/analysis")
    public ResponseEntity<StructuredFile> analyzeFile(@RequestParam(value = "file_url") String fileUrl) throws Exception {
        return ResponseEntity.ok(fileService.analyzeFile(fileUrl));
    }
}
