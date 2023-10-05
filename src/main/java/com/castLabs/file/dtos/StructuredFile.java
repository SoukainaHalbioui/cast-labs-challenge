package com.castLabs.file.dtos;

import com.castLabs.file.Box;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class StructuredFile {
    private String path;
    private List<Box> content;
}
