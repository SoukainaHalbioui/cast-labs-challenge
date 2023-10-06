package com.castLabs.file;

import com.castLabs.file.dtos.StructuredFile;

public interface FileService {
    byte[] readFile(String fileUrl);

    StructuredFile analyzeFile(String fileUrl);

    StructuredFile analyzeFile(byte[] file, String fileUrl);
}
