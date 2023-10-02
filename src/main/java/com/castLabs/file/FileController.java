package com.castLabs.file;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FileController {

    @GetMapping("/file/analysis")
    public void analyzeFile(@RequestParam(value = "file_url") String fileUrl) {
        // to do
    }
}
