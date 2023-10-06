package com.castLabs.file;

import com.castLabs.file.dtos.StructuredFile;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FileServiceTest {
    @Autowired
    private FileService fileService;

    @Test
    public void analyzeSimpleFileWithOneBox() {
        // given
        String testFileUrl = "some-file.mp3";
        byte[] testFileBytes = {0, 0, 0, 16, 109, 102, 104, 100, 0, 0, 0, 0, 0, 15, -29, -77};
        Box box = Box.builder().type("MFHD").contentSize(8).totalSize(16).content(new ArrayList<>()).build();
        StructuredFile expected = new StructuredFile(testFileUrl, List.of(box));

        // when
        StructuredFile actual = fileService.analyzeFile(testFileBytes, testFileUrl);

        // then
        assertEquals(expected, actual);
    }

    @Test
    public void analyzeFileWithNestedBoxes() {
        // given
        String testFileUrl = "some-file.mp3";
        byte[] testFileBytes = {0, 0, 0, 16, 109, 102, 104, 100, 0, 0, 0, 0, 0, 15, -29, -77, 0, 0, 0, 24, 116, 114, 97, 102, 0, 0, 0, 16, 116, 102, 104, 100, 0, 0, 0, 0, 0, 15, -29, -77};
        Box box1 = Box.builder().type("MFHD").contentSize(8).totalSize(16).content(new ArrayList<>()).build();
        Box box2 = Box.builder().type("TFHD").contentSize(8).totalSize(16).content(new ArrayList<>()).build();
        Box box3 = Box.builder().type("TRAF").contentSize(16).totalSize(24).content(List.of(box2)).build();
        StructuredFile expected = new StructuredFile(testFileUrl, List.of(box1, box3));

        // when
        StructuredFile actual = fileService.analyzeFile(testFileBytes, testFileUrl);

        // then
        assertEquals(expected, actual);
    }
}
