package com.castLabs.file;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Box {
    private String type;
    private int totalSize;
    private int contentSize;
    private List<Box> content;
}
